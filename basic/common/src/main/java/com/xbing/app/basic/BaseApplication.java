package com.xbing.app.basic;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.xbing.app.basic.common.DimenUtil;
import com.xbing.app.basic.common.LogUtil;

/**
 * Created by Administrator on 2017/7/12.
 */

public class BaseApplication extends MultiDexApplication {
    private static final String TAG = BaseApplication.class.getSimpleName();
    private static BaseApplication instance;

    public static BaseApplication getInstance(){
        return instance;
    }

    @Override
    protected void attachBaseContext(android.content.Context base) {
        super.attachBaseContext(base);
        android.support.multidex.MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        instance = this;
        LogUtil.i(TAG,TAG + "：onCreate()" + "--> dimen:" + DimenUtil.dip2px(instance,30));
    }
}
