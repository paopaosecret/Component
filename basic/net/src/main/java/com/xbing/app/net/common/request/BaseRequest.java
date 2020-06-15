package com.xbing.app.net.common.request;

import com.xbing.app.net.BuildConfig;
import com.xbing.app.net.common.OkHttpUtils;
import com.xbing.app.net.common.cache.dbcache.CacheEntity;
import com.xbing.app.net.common.cache.dbcache.CacheManager;
import com.xbing.app.net.common.cache.dbcache.CacheMode;
import com.xbing.app.net.common.callback.Callback;
import com.xbing.app.net.common.entity.HttpHeaders;
import com.xbing.app.net.common.entity.HttpParams;
import com.xbing.app.net.common.https.HttpsUtils;
import com.xbing.app.net.common.utils.BusinessErrorException;
import com.xbing.app.net.common.utils.HeaderParser;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class BaseRequest<R extends BaseRequest> {

    protected String url;
    protected long readTimeOut;
    protected long writeTimeOut;
    protected long connectTimeOut;
    protected CacheMode cacheMode;
    protected String cacheKey;
    /** 默认的缓存时长*/
    protected long cacheTime = CacheEntity.CACHE_NEVER_EXPIRE;
    /**
     * 请求添加的头
     */
    protected HttpHeaders headers = new HttpHeaders();
    /**
     * 请求添加的参数
     */
    protected HttpParams params = new HttpParams();
    /**
     * 添加额外的拦截器
     */
    protected List<Interceptor> interceptors = new ArrayList<>();

    /**
     * 手动为每个请求注入cookie
     */
    protected List<Cookie> cookies = new ArrayList<>();

    /**
     * 异步请求时设置的回调
     */
    private Callback callBack;

    /**
     * 自定义缓存管理
     * 缓存使用sqlite，缓存数据在表中
     */
    private CacheManager cacheManager;

    /**
     * okhttp中封装的url
     */
    private HttpUrl httpUrl;

    private BaseRequest(String url){
        this.url = url;
        httpUrl = HttpUrl.parse(url);
        OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
        cacheManager = CacheManager.INSTANCE;
        if(okHttpUtils.getmCommonHeaders() != null){
            headers.put(okHttpUtils.getmCommonHeaders());
        }
        if(okHttpUtils.getmCommonParams() != null){
            params.put(okHttpUtils.getmCommonParams());
        }
        if(okHttpUtils.getmCacheMode() != null){
            cacheMode = okHttpUtils.getmCacheMode();
        }
        cacheTime = okHttpUtils.getmCacheTime();
    }

    public R url(@NonNull String url){
        this.url = url;
        return (R)this;
    }

    public R readiTimeOut(long readTimeOut){
        this.readTimeOut = readTimeOut;
        return (R)this;
    }

    public R writeTimeOut(long writeTimeOut){
        this.writeTimeOut = writeTimeOut;
        return (R)this;
    }

    public R connTimeOut(long connectTimeOut){
        this.connectTimeOut = connectTimeOut;
        return (R)this;
    }

    public R cacheMode(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
        return (R) this;
    }

    public R cacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
        return (R) this;
    }

    /**
     * 传入 -1 表示永久有效,默认值即为 -1
     */
    public R cacheTime(long cacheTime) {
        if (cacheTime <= -1) {
            cacheTime = CacheEntity.CACHE_NEVER_EXPIRE;
        }
        this.cacheTime = cacheTime;
        return (R) this;
    }

    public R headers(HttpHeaders headers) {
        this.headers.put(headers);
        return (R) this;
    }

    public R headers(String key, String value) {
        headers.put(key, value);
        return (R) this;
    }

    public R params(HttpParams params) {
        this.params.put(params);
        return (R) this;
    }

    public R params(String key, String value) {
        params.put(key, value);
        return (R) this;
    }

    public R params(String key, File file) {
        params.put(key, file);
        return (R) this;
    }

    public R addUrlParams(String key, List<String> values) {
        params.putUrlParams(key, values);
        return (R) this;
    }

    public R addFileParams(String key, List<File> files) {
        params.putFileParams(key, files);
        return (R) this;
    }

    public R addFileWrapperParams(String key, List<HttpParams.FileWrapper> fileWrappers) {
        params.putFileWrapperParams(key, fileWrappers);
        return (R) this;
    }

    public R params(String key, File file, String fileName) {
        params.put(key, file, fileName);
        return (R) this;
    }

    public R params(String key, File file, String fileName, MediaType contentType) {
        params.put(key, file, fileName, contentType);
        return (R) this;
    }

    public R addCookie(@NonNull String name, @NonNull String value) {
        Cookie.Builder builder = new Cookie.Builder();
        Cookie cookie = builder.name(name).value(value).domain(httpUrl.host()).build();
        cookies.add(cookie);
        return (R) this;
    }

    public R addCookie(@NonNull Cookie cookie) {
        cookies.add(cookie);
        return (R) this;
    }

    public R addCookies(@NonNull List<Cookie> cookies) {
        cookies.addAll(cookies);
        return (R) this;
    }

    public R setCallback(Callback callback) {
        this.callBack = callback;
        return (R) this;
    }

    public R addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
        return (R) this;
    }

    protected String createUrlFromParams(String url, Map<String, List<String>> params) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            if (url.indexOf('&') > 0 || url.indexOf('?') > 0){
                sb.append("&");
            } else{
                sb.append("?");
            }
            for (Map.Entry<String, List<String>> urlParams : params.entrySet()) {
                List<String> urlValues = urlParams.getValue();
                for (String value : urlValues) {
                    String urlValue = URLEncoder.encode(value, "UTF-8");
                    sb.append(urlParams.getKey()).append("=").append(urlValue).append("&");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    protected Request.Builder appendHeaders(Request.Builder requestBuilder) {
        Headers.Builder headerBuilder = new Headers.Builder();
        ConcurrentHashMap<String, String> headerMap = headers.headersMap;
        if (headerMap.isEmpty()){
            return requestBuilder;
        }
        for (String key : headerMap.keySet()) {
            headerBuilder.add(key, headerMap.get(key));
        }
        requestBuilder.headers(headerBuilder.build());
        return requestBuilder;
    }

    /**
     * 生成类是表单的请求体
     */
    protected RequestBody generateMultipartRequestBody() {
        if (params.fileParamsMap.isEmpty()) {
            //表单提交，没有文件
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            for (String key : params.urlParamsMap.keySet()) {
                List<String> urlValues = params.urlParamsMap.get(key);
                for (String value : urlValues) {
                    bodyBuilder.add(key, value);
                }
            }
            return bodyBuilder.build();
        } else {
            //表单提交，有文件
            MultipartBody.Builder multipartBodybuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            //拼接键值对
            if (!params.urlParamsMap.isEmpty()) {
                for (Map.Entry<String, List<String>> entry : params.urlParamsMap.entrySet()) {
                    List<String> urlValues = entry.getValue();
                    for (String value : urlValues) {
                        multipartBodybuilder.addFormDataPart(entry.getKey(), value);
                    }
                }
            }
            //拼接文件
            for (Map.Entry<String, List<HttpParams.FileWrapper>> entry : params.fileParamsMap.entrySet()) {
                List<HttpParams.FileWrapper> fileValues = entry.getValue();
                for (HttpParams.FileWrapper fileWrapper : fileValues) {
                    RequestBody fileBody = RequestBody.create(fileWrapper.contentType, fileWrapper.file);
                    multipartBodybuilder.addFormDataPart(entry.getKey(), fileWrapper.fileName, fileBody);
                }
            }
            return multipartBodybuilder.build();
        }
    }

    /**
     * 根据当前的请求参数，生成对应的 Call 任务
     */
    protected Call generateCall(Request request) {
        if (!BuildConfig.DEBUG && readTimeOut <= 0 && writeTimeOut <= 0 && connectTimeOut <= 0 && cookies.size() == 0) {
            return OkHttpUtils.getInstance().getOkHttpClient().newCall(request);
        } else {
            OkHttpClient.Builder newClientBuilder = OkHttpUtils.getInstance().getOkHttpClient().newBuilder();
            if (readTimeOut > 0) {
                newClientBuilder.readTimeout(readTimeOut, TimeUnit.MILLISECONDS);
            }
            if (writeTimeOut > 0) {
                newClientBuilder.writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS);
            }
            if (connectTimeOut > 0) {
                newClientBuilder.connectTimeout(connectTimeOut, TimeUnit.MILLISECONDS);
            }
            if (BuildConfig.DEBUG) {
                //在Debug模式下，信任所有证书
                try {
                    SSLContext sc = SSLContext.getInstance("TLS");
                    sc.init(null, new TrustManager[]{new HttpsUtils.UnSafeTrustManager()}, new SecureRandom());
                    newClientBuilder.sslSocketFactory(sc.getSocketFactory());
                    newClientBuilder.hostnameVerifier(new HttpsUtils.UnSafeHostnameVerifier());
                } catch (NoSuchAlgorithmException | KeyManagementException e) {
                    e.printStackTrace();
                }
            }

            if (cookies.size() > 0) {
                OkHttpUtils.getInstance().getCookieJar().addCookies(cookies);
            }
            if (interceptors.size() > 0) {
                for (Interceptor interceptor : interceptors) {
                    newClientBuilder.addInterceptor(interceptor);
                }
            }
            return newClientBuilder.build().newCall(request);
        }
    }

    protected RequestBody wrapRequestBody(RequestBody requestBody) {
        ProgressRequestBody progressRequestBody = new ProgressRequestBody(requestBody);
//        progressRequestBody.setListener(new ProgressRequestBody.Listener() {
//            @Override
//            public void onRequestProgress(final long bytesWritten, final long contentLength, final long networkSpeed) {
//                OkHttpUtils.getInstance().getDelivery().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (callBack != null)
//                            callBack.inProgress(bytesWritten, contentLength);
//                    }
//                });
//            }
//        });
        return progressRequestBody;
    }

    /**
     * 阻塞方法，同步请求执行
     */
    public Response execute() throws IOException {
        //添加缓存头和其他的公共头，同步请求不做缓存，缓存为空
        HeaderParser.addDefaultHeaders(this, null, null);

        //构建请求体，同步阻塞请求
        RequestBody requestBody = generateRequestBody();
        final Request request = generateRequest(wrapRequestBody(requestBody));
        Call call = generateCall(request);
        return call.execute();
    }

    /**
     * 非阻塞方法，异步请求，但是回调在子线程中执行
     */
    @SuppressWarnings("unchecked")
    public <T> void execute(Callback<T> callback) {
        callBack = callback;
        if (callBack == null) {
            callBack = callback;
        }

        //请求之前获取缓存信息，添加缓存头和其他的公共头
        if (cacheKey == null) {
            cacheKey = createUrlFromParams(url, params.urlParamsMap);
        }
        if (cacheMode == null) {
            cacheMode = CacheMode.DEFAULT;
        }
        final CacheEntity<T> cacheEntity = (CacheEntity<T>) cacheManager.get(cacheKey);
        //检查缓存的有效时间,判断缓存是否已经过期
        if (cacheEntity != null && cacheEntity.checkExpire(cacheMode, cacheTime, System.currentTimeMillis())) {
            cacheEntity.setExpire(true);
        }
        HeaderParser.addDefaultHeaders(this, cacheEntity, cacheMode);
        //请求执行前UI线程调用
        callBack.onBefore(this);
        //构建请求
        RequestBody requestBody = generateRequestBody();
        Request request = generateRequest(wrapRequestBody(requestBody));
        Call call = generateCall(request);

        if (cacheMode == CacheMode.IF_NONE_CACHE_REQUEST) {
            //如果没有缓存，或者缓存过期,就请求网络，否者直接使用缓存
            if (cacheEntity != null && !cacheEntity.isExpire()) {
                T data = cacheEntity.getData();
                sendSuccessResultCallback(true, data, call, null, callBack);
                return;//返回即不请求网络
            } else {
                sendFailResultCallback(true, call, null, new IllegalStateException("没有获取到缓存,或者缓存已经过期!"), callBack);
            }
        } else if (cacheMode == CacheMode.FIRST_CACHE_THEN_REQUEST) {
            //先使用缓存，不管是否存在，仍然请求网络
            if (cacheEntity != null && !cacheEntity.isExpire()) {
                T data = cacheEntity.getData();
                sendSuccessResultCallback(true, data, call, null, callBack);
            } else {
//                sendFailResultCallback(true, call, null, new IllegalStateException("没有获取到缓存,或者缓存已经过期!"), mCallback);
            }
        }

        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败，一般为url地址错误，网络错误等
                sendFailResultCallback(false, call, null, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int responseCode = response.code();
                //304缓存数据
                if (responseCode == 304 && cacheMode == CacheMode.DEFAULT) {
                    if (cacheEntity == null) {
                        sendFailResultCallback(true, call, response, new IllegalStateException("服务器响应码304，但是客户端没有缓存！"), callBack);
                    } else {
                        T data = cacheEntity.getData();
                        sendSuccessResultCallback(true, data, call, response, callBack);
                    }
                    return;
                }
                //响应失败，一般为服务器内部错误，或者找不到页面等
                if (responseCode >= 400 && responseCode <= 599) {
                    sendFailResultCallback(false, call, response, null, callBack);
                    return;
                }

                try {
                    T data = (T) callBack.parseNetworkResponse(response);
                    sendSuccessResultCallback(false, data, call, response, callBack);
                    //网络请求成功，保存缓存数据
                    handleCache(response.headers(), data);
                } catch (BusinessErrorException e) {
                    //一般为服务器响应成功，但是数据解析错误
                    callBack.onBusinessError(e.getMessage());
                } catch (Exception e) {
                    //一般为服务器响应成功，但是数据解析错误
                    sendFailResultCallback(false, call, response, e, callBack);
                }
            }
        });
    }

    /**
     * 请求成功后根据缓存模式，更新缓存数据
     *
     * @param headers 响应头
     * @param data    响应数据
     */
    @SuppressWarnings("unchecked")
    private <T> void handleCache(Headers headers, T data) {
        //不需要缓存,直接返回
        if (cacheMode == CacheMode.NO_CACHE) {
            return;
        }

        CacheEntity<T> cache = HeaderParser.createCacheEntity(headers, data, cacheMode, cacheKey);
        if (cache == null) {
            //服务器不需要缓存，移除本地缓存
            cacheManager.remove(cacheKey);
        } else {
            //缓存命中，更新缓存
            cacheManager.replace(cacheKey, (CacheEntity<Object>) cache);
        }
    }

    /**
     * 失败回调，发送到主线程
     */
    @SuppressWarnings("unchecked")
    private <T> void sendFailResultCallback(final boolean isFromCache, final Call call,//
                                            final Response response, final Exception e, final Callback<T> callback) {

        OkHttpUtils.getInstance().getmHandler().post(new Runnable() {
            @Override
            public void run() {
                callback.onError(isFromCache, call, response, e);         //请求失败回调 （UI线程）
                callback.onAfter(isFromCache, null, call, response, e);   //请求结束回调 （UI线程）
            }
        });

        //不同的缓存模式，可能会导致该失败进入两次，一次缓存失败，一次网络请求失败
        if (!isFromCache && cacheMode == CacheMode.REQUEST_FAILED_READ_CACHE) {
            CacheEntity<T> cacheEntity = (CacheEntity<T>) cacheManager.get(cacheKey);
            if (cacheEntity != null) {
                T data = cacheEntity.getData();
                sendSuccessResultCallback(true, data, call, response, callback);
            } else {
                sendFailResultCallback(true, call, response, new IllegalStateException("请求网络失败后，无法读取缓存或者缓存不存在！"), callback);
            }
        }
    }

    /**
     * 成功回调，发送到主线程
     */
    private <T> void sendSuccessResultCallback(final boolean isFromCache, final T t, //
                                               final Call call, final Response response, final Callback<T> callback) {
        OkHttpUtils.getInstance().getmHandler().post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(isFromCache, t, call.request(), response);                         //请求成功回调 （UI线程）
                callback.onAfter(isFromCache, t, call, response, null);      //请求结束回调 （UI线程）
            }
        });
    }

    /**
     * 根据不同的请求方式，生成对应的RequestBody
     * @return
     */
    protected abstract RequestBody generateRequestBody();

    /**
     * 根据不同的请求方式，将RequestBody转换成Request对象
     */
    protected abstract Request generateRequest(RequestBody requestBody);


}
