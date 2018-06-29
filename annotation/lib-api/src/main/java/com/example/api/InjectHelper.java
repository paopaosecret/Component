package com.example.api;

import android.app.Activity;

import java.lang.reflect.Constructor;

/**
 * @author hiphonezhu@gmail.com
 * @version [CompilerAnnotation, 17/6/20 11:28]
 */

public class InjectHelper {
    public static void inject(Activity host) {
        String classFullName = host.getClass().getName() + "$$ViewInjector";
        try {
            Class proxy = Class.forName(classFullName);   //获取注解处理器生成的类
            Constructor constructor = proxy.getConstructor(host.getClass());   //获取这个生成类的有参构造器（参数为对应的Activity）
            constructor.newInstance(host);   //生成该类实例，在构造器中为activity的View通过绑定id去初始化
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
