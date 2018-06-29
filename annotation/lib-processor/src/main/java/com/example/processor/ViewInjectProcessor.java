package com.example.processor;

import com.example.lib_annotation.BindView;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by zhaobing04 on 2018/5/28.
 */
@AutoService(Processor.class)
public class ViewInjectProcessor extends AbstractProcessor {

    // 存放同一个Class下的所有注解
    Map<String, List<VariableInfo>> classMap = new HashMap<>();

    // 存放Class对应的TypeElement
    Map<String, TypeElement> classTypeElement = new HashMap<>();

    private Filer filer;    //注解处理器生成java文件的目录
    Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        System.out.println("ViewInjectProcessor init()");
        filer = processingEnv.getFiler();           //使用系统默认apt输出目录的文件
        elementUtils = processingEnv.getElementUtils();
    }

    /**
     *  此方法收集需要被处理的注解集合
     *
     *  实现一般固定只需要向set集合中添加需要被处理的注解，这个集合会被按流程交给方法{@link ViewInjectProcessor#process}去处理
     *
     */
    @Override
    public Set<String> getSupportedAnnotationTypes(){
        System.out.println("ViewInjectProcessor getSupportedAnnotationTypes()");
        Set<String> annotationTypes = new LinkedHashSet<String>();

        //需要全限定类名（包名 + 类名）
        annotationTypes.add(BindView.class.getCanonicalName());
        return annotationTypes;
    }

    /**
     * 设置支持的sdk版本
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion(){
        System.out.println("ViewInjectProcessor getSupportedSourceVersion()");
        return SourceVersion.RELEASE_8;
    }
    /**
     * 处理逻辑
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("ViewInjectProcessor process()");
        //第一步：收集注解数据
        collectInfo(roundEnvironment);

        //第二步：按收集的数据编写.java 文件
        writeToFile();
        return true;
    }

    void collectInfo(RoundEnvironment roundEnvironment) {
        classMap.clear();
        classTypeElement.clear();

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        for (Element element : elements) {
            // 获取BindView被注解的值（如：R.id.tv_show）
            int viewId = element.getAnnotation(BindView.class).value();

            // 代表被注解的元素   variableElement:mTVResult
            VariableElement variableElement = (VariableElement) element;
            System.out.println("variableElement:" + variableElement.getSimpleName());

            // 被注解元素所在的Class   typeElement:MainActivity
            TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
            System.out.println("typeElement:" + typeElement.getSimpleName());

            // Class的完整路径   //classFullName:com.example.zhaobing04.okhttpsource.MainActivity
            String classFullName = typeElement.getQualifiedName().toString();

            // 收集Class中被注解元素的所有信息
            List<VariableInfo> variableList = classMap.get(classFullName);

            if (variableList == null) {
                variableList = new ArrayList<>();
                classMap.put(classFullName, variableList);

                // 保存Class对应要素（名称、完整路径等）
                classTypeElement.put(classFullName, typeElement);
            }
            VariableInfo variableInfo = new VariableInfo();
            variableInfo.setVariableElement(variableElement);
            variableInfo.setViewId(viewId);
            variableList.add(variableInfo);
        }
    }

    void writeToFile() {
        try {
            for (String classFullName : classMap.keySet()) {
                TypeElement typeElement = classTypeElement.get(classFullName);

                // 使用构造函数绑定数据
                MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(ParameterSpec.builder(TypeName.get(typeElement.asType()), "activity").build());
                List<VariableInfo> variableList = classMap.get(classFullName);
                for (VariableInfo variableInfo : variableList) {
                    VariableElement variableElement = variableInfo.getVariableElement();
                    // 变量名称(比如：TextView tv 的 tv)
                    String variableName = variableElement.getSimpleName().toString();
                    // 变量类型的完整类路径（比如：android.widget.TextView）
                    String variableFullName = variableElement.asType().toString();
                    // 在构造方法中增加赋值语句，例如：activity.tv = (android.widget.TextView)activity.findViewById(215334);
                    constructor.addStatement("activity.$L=($L)activity.findViewById($L)", variableName, variableFullName, variableInfo.getViewId());
                }

                // 构建Class
                TypeSpec typeSpec = TypeSpec.classBuilder(typeElement.getSimpleName() + "$$ViewInjector")
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(constructor.build())
                        .build();

                // 与目标Class放在同一个包下，解决Class属性的可访问性
                String packageFullName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
                JavaFile javaFile = JavaFile.builder(packageFullName, typeSpec)
                        .build();
                // 生成.java 文件
                javaFile.writeTo(filer);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
