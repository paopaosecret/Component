package com.xbing.app.net.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.xbing.app.net.common.cache.dbcache.CacheMode;
import com.xbing.app.net.common.callback.Callback;
import com.xbing.app.net.common.cookie.CookieJarImpl;
import com.xbing.app.net.common.cookie.store.CookieStore;
import com.xbing.app.net.common.cookie.store.MemoryCookieStore;
import com.xbing.app.net.common.entity.HttpHeaders;
import com.xbing.app.net.common.entity.HttpParams;
import com.xbing.app.net.common.https.HttpsUtils;
import com.xbing.app.net.common.interceptor.LoggerInterceptor;
import com.xbing.app.net.common.request.BaseRequest;
import com.xbing.app.net.common.utils.Platform;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by zhaobing  15/8/17.
 */
public class OkHttpUtils
{
    public static final long DEFAULT_MILLISECONDS = 10_000L;
    private volatile static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;
    private HttpParams mCommonParams;                     //全局公共请求参数
    private HttpHeaders mCommonHeaders;                   //全局公共请求头
    private CacheMode mCacheMode;
    private long mCacheTime;

    public Handler getmHandler() {
        return mHandler;
    }

    public void setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    private Handler mHandler;                            //用于在主线程执行的调度器


    public CookieJarImpl getCookieJar() {
        return cookieJar;
    }

    public void setCookieJar(CookieJarImpl cookieJar) {
        this.cookieJar = cookieJar;
    }

    private CookieJarImpl cookieJar;                      //全局 Cookie 实例

    public long getmCacheTime() {
        return mCacheTime;
    }

    public void setmCacheTime(long mCacheTime) {
        this.mCacheTime = mCacheTime;
    }

    public CacheMode getmCacheMode() {
        return mCacheMode;
    }

    public void setmCacheMode(CacheMode mCacheMode) {
        this.mCacheMode = mCacheMode;
    }

    public HttpParams getmCommonParams() {
        return mCommonParams;
    }

   public void setmCommonParams(HttpParams mCommonParams) {
        this.mCommonParams = mCommonParams;
    }

    public HttpHeaders getmCommonHeaders() {
        return mCommonHeaders;
    }

    public void setmCommonHeaders(HttpHeaders mCommonHeaders) {
        this.mCommonHeaders = mCommonHeaders;
    }

    /** 全局cookie存取规则 */
    public OkHttpUtils setCookieStore(CookieStore cookieStore) {
        cookieJar = new CookieJarImpl(cookieStore);
        return this;
    }

    private static Application context;                   //全局上下文

    /** 必须在全局Application先调用，获取context上下文，否则缓存无法使用 */
    public static void init(Application app) {
        context = app;
    }

    public static Context getContext(){
        if(context == null){
            throw new IllegalStateException("请为http组件设置上下文环境，之后可使用缓存服务");
        }

        return context;
    }


    public OkHttpUtils(OkHttpClient okHttpClient)
    {
        if (okHttpClient == null)
        {
            mOkHttpClient = new OkHttpClient();
        } else
        {
            mOkHttpClient = okHttpClient;
        }

        mPlatform = Platform.get();
    }

     static{
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient okHttpClient = new OkHttpClient.Builder().hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }).connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("app_business_http_api",true))
                .cookieJar(cookieJar1)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
         
        initClient(okHttpClient);
    }


    public static OkHttpUtils initClient(OkHttpClient okHttpClient)
    {
        if (mInstance == null)
        {
            synchronized (OkHttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance()
    {
        return initClient(null);
    }


    public Executor getDelivery()
    {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient()
    {
        return mOkHttpClient;
    }


    public void execute(final BaseRequest requestCall, Callback callback)
    {

    }


    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback)
    {

    }

    public void sendSuccessResultCallback(final Object object, final Callback callback)
    {
    }

    public void cancelTag(Object tag)
    {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
    }

    public static class METHOD
    {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }
}

