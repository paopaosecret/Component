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

    // 收集被注解的元素信息 key为全限定类名   value被注解的元素
    Map<String, List<VariableInfo>> classMap = new HashMap<>();

    // 收集被注解的元素的类信息  key为全限定类名   value被注解的元素的类类型信息
    Map<String, TypeElement> classTypeElement = new HashMap<>();

    private Filer filer;

    Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        System.err.println("ViewInjectProcessor init()");
        filer = processingEnv.getFiler();           //默认android生成文件目录：build\generated\source\apt
        System.out.println(filer.toString());
        elementUtils = processingEnv.getElementUtils();
    }

    /**
     *  设置可以被处理的注解类型
     *
     *  {@link ViewInjectProcessor#process}
     *
     */
    @Override
    public Set<String> getSupportedAnnotationTypes(){
        System.err.println("ViewInjectProcessor getSupportedAnnotationTypes()");
        Set<String> annotationTypes = new LinkedHashSet<String>();

        //增加支持的注解
        annotationTypes.add(BindView.class.getCanonicalName());
        return annotationTypes;
    }

    /**
     * 设置支持的jdk版本
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion(){
        System.err.println("ViewInjectProcessor getSupportedSourceVersion()");
        return SourceVersion.RELEASE_8;
    }
    /**
     * 处理
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.err.println("ViewInjectProcessor process()");
        //收集注解的 元素信息
        collectInfo(roundEnvironment);

        //根据收集的信息生成java文件
        writeToFile();
        return true;
    }

    void collectInfo(RoundEnvironment roundEnvironment) {
        classMap.clear();
        classTypeElement.clear();

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        for (Element element : elements) {
            // R.id.tv_show在R文件中的值
            int viewId = element.getAnnotation(BindView.class).value();
            System.out.println("viewId:" + viewId);

            // variableElement:mTVResult  获取被注解元素的变量元素，该对象可获取变量名
            VariableElement variableElement = (VariableElement) element;
            System.out.println("variableElement:" + variableElement.getSimpleName());

            // typeElement:MainActivity  获取注解元素所在的类类型信息， 该对象可获取类名
            TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
            System.out.println("typeElement:" + typeElement.getSimpleName());

            // 获取全限定类名（包名+类名）  //classFullName:com.example.zhaobing04.okhttpsource.MainActivity
            String classFullName = typeElement.getQualifiedName().toString();

            // 存放类中被注解的元素信息
            List<VariableInfo> variableList = classMap.get(classFullName);

            if (variableList == null) {
                variableList = new ArrayList<>();
                classMap.put(classFullName, variableList);

                classTypeElement.put(classFullName, typeElement);
            }
            VariableInfo variableInfo = new VariableInfo();
            variableInfo.setVariableElement(variableElement);
            variableInfo.setViewId(viewId);
            variableList.add(variableInfo);
        }
    }

    //利用javapoet生成java文件
    void writeToFile() {
        try {
            for (String classFullName : classMap.keySet()) {
                TypeElement typeElement = classTypeElement.get(classFullName);

                MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(ParameterSpec.builder(TypeName.get(typeElement.asType()), "activity").build());

                List<VariableInfo> variableList = classMap.get(classFullName);
                for (VariableInfo variableInfo : variableList) {
                    VariableElement variableElement = variableInfo.getVariableElement();
                    //
                    String variableName = variableElement.getSimpleName().toString();
                    //
                    String variableFullName = variableElement.asType().toString();
                    // 例如：activity.tv = (android.widget.TextView)activity.findViewById(215334);
                    constructor.addStatement("activity.$L=($L)activity.findViewById($L)", variableName, variableFullName, variableInfo.getViewId());
                }

                // ????Class
                TypeSpec typeSpec = TypeSpec.classBuilder(typeElement.getSimpleName() + "$$ViewInjector")
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(constructor.build())
                        .build();

                // ?????Class????????????????Class???????????
                String packageFullName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
                JavaFile javaFile = JavaFile.builder(packageFullName, typeSpec)
                        .build();
                // 生成。java文件
                javaFile.writeTo(filer);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
