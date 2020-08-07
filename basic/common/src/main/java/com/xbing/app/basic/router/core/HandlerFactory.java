package com.xbing.app.basic.router.core;

/**
 *
 * @作者 zhaobing04
 *
 * @创建日期 2020/7/30 10:42
 */
public class HandlerFactory {
    public static IHandler createFlutterHandler(){
        return new FlutterHandler();
    }

    public static IHandler createNativeHandler(){
        return new NativeHandler();
    }

    public static IHandler createWebHandler(){
        return new WebHandler();
    }
}
