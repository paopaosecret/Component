package com.xbing.app.component;

import android.util.Log;

import com.xbing.app.basic.BaseApplication;
import com.xbing.app.basic.common.LogUtil;

/**
 * Created by Administrator on 2017/7/12.
 */

public class MyApplication extends BaseApplication {

    private static final String TAG = MyApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG,TAG + "：onCreate()");
    }
}
