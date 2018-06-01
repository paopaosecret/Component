package com.xbing.app.basic;

import android.app.Application;

import com.xbing.app.basic.common.DimenUtil;
import com.xbing.app.basic.common.LogUtil;

/**
 * Created by Administrator on 2017/7/12.
 */

public class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getSimpleName();
    private static BaseApplication instance;

    public static BaseApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LogUtil.i(TAG,TAG + "：onCreate()" + "--> dimen:" + DimenUtil.dip2px(instance,30));
    }
}
