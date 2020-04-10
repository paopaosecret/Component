package com.xbing.app.component;

import android.content.Context;

import com.xbing.app.basic.BaseApplication;
import com.xbing.app.basic.common.LogUtil;
import com.xbing.app.net.common.OkHttpUtils;
import com.xbing.app.net.common.cache.memcache.ThreadPoolManager;
import com.xbing.app.net.common.cache.memcache.WebResourceCacheManager;

/**
 * Created by Administrator on 2017/7/12.
 */

public class MyApplication extends BaseApplication {

    private static final String TAG = MyApplication.class.getSimpleName();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG,TAG + "：onCreate()");
        OkHttpUtils.init(this);
        initWebResourceCacheManager();
    }

    private void initWebResourceCacheManager() {
        ThreadPoolManager.newInstance().addExecuteTask(new Runnable() {
            @Override
            public void run() {
                WebResourceCacheManager.getInstance().init();
            }
        });
    }
}
