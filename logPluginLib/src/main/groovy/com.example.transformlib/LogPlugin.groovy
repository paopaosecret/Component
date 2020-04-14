package com.example.transformlib

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 创建插件Groovy类,
 */
public class LogPlugin implements Plugin<Project>{
    //重写此方法，对工程代码做插桩处理
    void apply(Project project){
        System.out.println("==LogPlugin plugin registering LogTransform==")

        def android = project.extensions.getByType(AppExtension)

        //为插件注册Transform,遍历设置相关规则的文件
        LogTransform transform = new LogTransform()
        android.registerTransform(transform)
    }
}