package com.xbing.app.component;

import android.content.Context;

import com.didichuxing.doraemonkit.DoraemonKit;
import com.xbing.app.basic.BaseApplication;
import com.xbing.app.basic.common.LogUtil;
import com.xbing.app.basic.router.HyRouter;
import com.xbing.app.net.common.OkHttpUtils;
import com.xbing.app.net.common.cache.memcache.ThreadPoolManager;
import com.xbing.app.net.common.cache.memcache.WebResourceCacheManager;

/**
 * Created by Administrator on 2017/7/12.
 */
public class MyApplication extends BaseApplication {

    private static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication application;

    public static Context getContext(){
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        LogUtil.i(TAG,TAG + "：onCreate()");
        OkHttpUtils.init(this);
        initWebResourceCacheManager();
        HyRouter.init();
        DoraemonKit.install(this);
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
