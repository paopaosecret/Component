package com.example.transformlib

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils
import groovy.io.FileType
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

/**
 * Transform 主要作用是检索项目编译过程中的所有文件。通过实现其抽象方法，设置一些遍历规则
 */
public class LogTransform extends Transform {

    /**
     * 设置自定义Transform对应的task名称，Gradle在编译时，会将这个名称显示在控制台上
     * 比如Task:app:transformClassesWithLogTransformForDebug
     */
    @Override
    String getName() {
        return "LogTransform"
    }

    /**
     * 项目中有各种格式的文件，通过此方法可以设置Transform接受的文件类型
     *
     * @return ContentType只有两种取值：
     * CLASSES:代表检索.class文件   RESOURCES:代表检索java标准资源文件
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 规定Transform检索的范围，Scope取值如下
     * PROJECT(0x01),                  只有项目内容
     * SUB_PROJECTS(0x04)              只有子项目
     * EXTERNAL_LIBRARIES(0x10)        只有外部库
     * TESTED_CODE(0x20)               当前变量（包括依赖项）的测试的代码
     * PROVIDED_ONLY(0x40)             只提供本地或者远程依赖项
     * PROJECT_LOCAL_DEPS(0x02)        只有项目的本地依赖项（本地jar）
     * SUB_PROJECTS_LOCAL_DEPS(0x08);  只有子项目的本地依赖项（本地jar）
     * @return
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.PROJECT_ONLY
    }

    /**
     * 标识当前Transform是否支持增加编译
     *
     * @return true:支持 false:不支持
     */
    @Override
    boolean isIncremental() {
        return false
    }

    /**
     * 最重要的方法
     * @param transformInvocation 这个参数可以获取两个数据的流向
     *              inputs:inputs为传过来的数据流，有两种格式，一种jar包格式,一种目录格式
     *              outputProvider:outputProvider获取到输出目录，最后将修改的文件复制到输出目录
     *                              这一步必须做，否则编译会报错
     *
     * 测试代码只是打印了下所有的.class文件
     * @throws TransformException
     * @throws InterruptedException
     * @throws IOException
     */
    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        //拿到所有的class文件
        Collection<TransformInput> transformInputs = transformInvocation.inputs
        TransformOutputProvider outputProvider = transformInvocation.outputProvider
        if (outputProvider != null) {
            outputProvider.deleteAll()
        }

        transformInputs.each { TransformInput transformInput ->
            // 遍历directoryInputs(文件夹中的class文件) directoryInputs代表着以源码方式参与项目编译的所有目录结构及其目录下的源码文件
            // 比如我们手写的类以及R.class、BuildConfig.class以及MainActivity.class等
            transformInput.directoryInputs.each { DirectoryInput directoryInput ->
                File dir = directoryInput.file
                if (dir) {
                    dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { File file ->
                        System.out.println("find class: " + file.name)
                        //对class文件进行读取与解析
                        ClassReader classReader = new ClassReader(file.bytes)
                        //对class文件的写入
                        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                        //访问class文件相应的内容，解析到某一个结构就会通知到ClassVisitor的相应方法
                        ClassVisitor classVisitor = new LogClassVisitor(classWriter)
                        //依次调用 ClassVisitor接口的各个方法
                        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
                        //toByteArray方法会将最终修改的字节码以 byte 数组形式返回。
                        byte[] bytes = classWriter.toByteArray()

                        //通过文件流写入方式覆盖掉原先的内容，实现class文件的改写。
                        //FileOutputStream outputStream = new FileOutputStream( file.parentFile.absolutePath + File.separator + fileName)
                        FileOutputStream outputStream = new FileOutputStream(file.path)
                        outputStream.write(bytes)
                        outputStream.close()
                    }
                }

                //处理完输入文件后把输出传给下一个文件
                def dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes,
                        directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }
    }
}