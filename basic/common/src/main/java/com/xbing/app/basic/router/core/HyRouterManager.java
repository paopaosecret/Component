package com.xbing.app.basic.router.core;

import java.util.Map;

public class HyRouterManager {

    private static volatile HyRouterManager mInstance;
    private Map<String, String> FlutterPagerMapping;
    private Map<String, String> NativePageMapping;
    private Map<String, String> WebPageMapping;
    private Map<String, String> NativeFunctionMapping;
    private Map<String, String> allMapping;

    public void registerFlutterPagerMapping(Map<String, String> map){

    }

    public void registerNativePageMapping(Map<String, String> map){

    }

    public void registerWebPageMapping(Map<String, String> map){

    }

    public void registerNativeFunctionMapping(Map<String, String> map){

    }

    private HyRouterManager(){

    }

    public static HyRouterManager getInstance() {
        if (mInstance == null) {
            synchronized (HyRouterManager.class) {
                if (mInstance == null) {
                    mInstance = new HyRouterManager();
                }
            }
        }
        return mInstance;
    }


}
