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

    // ���ͬһ��Class�µ�����ע��
    Map<String, List<VariableInfo>> classMap = new HashMap<>();

    // ���Class��Ӧ��TypeElement
    Map<String, TypeElement> classTypeElement = new HashMap<>();

    private Filer filer;    //ע�⴦��������java�ļ���Ŀ¼
    Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        System.out.println("ViewInjectProcessor init()");
        filer = processingEnv.getFiler();           //ʹ��ϵͳĬ��apt���Ŀ¼���ļ�
        elementUtils = processingEnv.getElementUtils();
    }

    /**
     *  �˷����ռ���Ҫ�������ע�⼯��
     *
     *  ʵ��һ��̶�ֻ��Ҫ��set�����������Ҫ�������ע�⣬������ϻᱻ�����̽�������{@link ViewInjectProcessor#process}ȥ����
     *
     */
    @Override
    public Set<String> getSupportedAnnotationTypes(){
        System.out.println("ViewInjectProcessor getSupportedAnnotationTypes()");
        Set<String> annotationTypes = new LinkedHashSet<String>();

        //��Ҫȫ�޶����������� + ������
        annotationTypes.add(BindView.class.getCanonicalName());
        return annotationTypes;
    }

    /**
     * ����֧�ֵ�sdk�汾
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion(){
        System.out.println("ViewInjectProcessor getSupportedSourceVersion()");
        return SourceVersion.RELEASE_8;
    }
    /**
     * �����߼�
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("ViewInjectProcessor process()");
        //��һ�����ռ�ע������
        collectInfo(roundEnvironment);

        //�ڶ��������ռ������ݱ�д.java �ļ�
        writeToFile();
        return true;
    }

    void collectInfo(RoundEnvironment roundEnvironment) {
        classMap.clear();
        classTypeElement.clear();

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        for (Element element : elements) {
            // ��ȡBindView��ע���ֵ���磺R.id.tv_show��
            int viewId = element.getAnnotation(BindView.class).value();

            // ����ע���Ԫ��   variableElement:mTVResult
            VariableElement variableElement = (VariableElement) element;
            System.out.println("variableElement:" + variableElement.getSimpleName());

            // ��ע��Ԫ�����ڵ�Class   typeElement:MainActivity
            TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
            System.out.println("typeElement:" + typeElement.getSimpleName());

            // Class������·��   //classFullName:com.example.zhaobing04.okhttpsource.MainActivity
            String classFullName = typeElement.getQualifiedName().toString();

            // �ռ�Class�б�ע��Ԫ�ص�������Ϣ
            List<VariableInfo> variableList = classMap.get(classFullName);

            if (variableList == null) {
                variableList = new ArrayList<>();
                classMap.put(classFullName, variableList);

                // ����Class��ӦҪ�أ����ơ�����·���ȣ�
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

                // ʹ�ù��캯��������
                MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(ParameterSpec.builder(TypeName.get(typeElement.asType()), "activity").build());
                List<VariableInfo> variableList = classMap.get(classFullName);
                for (VariableInfo variableInfo : variableList) {
                    VariableElement variableElement = variableInfo.getVariableElement();
                    // ��������(���磺TextView tv �� tv)
                    String variableName = variableElement.getSimpleName().toString();
                    // �������͵�������·�������磺android.widget.TextView��
                    String variableFullName = variableElement.asType().toString();
                    // �ڹ��췽�������Ӹ�ֵ��䣬���磺activity.tv = (android.widget.TextView)activity.findViewById(215334);
                    constructor.addStatement("activity.$L=($L)activity.findViewById($L)", variableName, variableFullName, variableInfo.getViewId());
                }

                // ����Class
                TypeSpec typeSpec = TypeSpec.classBuilder(typeElement.getSimpleName() + "$$ViewInjector")
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(constructor.build())
                        .build();

                // ��Ŀ��Class����ͬһ�����£����Class���ԵĿɷ�����
                String packageFullName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
                JavaFile javaFile = JavaFile.builder(packageFullName, typeSpec)
                        .build();
                // ����.java �ļ�
                javaFile.writeTo(filer);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
