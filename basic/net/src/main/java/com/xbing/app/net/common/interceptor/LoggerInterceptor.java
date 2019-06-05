package com.xbing.app.net.common.interceptor;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * 拦截网络请求的日志
 */
public class LoggerInterceptor implements Interceptor
{
    public static final String TAG = "OkHttpUtils";
    private String tag;
    private boolean showResponse;

    public LoggerInterceptor(String tag, boolean showResponse)
    {
        if (TextUtils.isEmpty(tag))
        {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    public LoggerInterceptor(String tag)
    {
        this(tag, false);
    }

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    /**
     * 打印网络请求内容
     *
     * @param request
     */
    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            Log.e(tag, "========request'log=======");
            Log.e(tag, "method : " + request.method());
            Log.e(tag, "url : " + url);
            if (headers != null && headers.size() > 0) {
                Log.e(tag, "headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    Log.e(tag, "requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType))
                    {
                        Log.e(tag, "requestBody's content : " + bodyToString(request));
                    } else
                    {
                        Log.e(tag, "requestBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            Log.e(tag, "========request'log=======end");
        } catch (Exception e) {}
    }

    /**
     * 打印请求响应的内容
     * @param response
     * @return
     */
    private Response logForResponse(Response response) {
        try {
            Log.e(tag, "========response'log=======");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            Log.e(tag, "url : " + clone.request().url());
            Log.e(tag, "code : " + clone.code());
            Log.e(tag, "protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message())){
                Log.e(tag, "message : " + clone.message());
            }

            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        Log.e(tag, "responseBody's contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();
                            Log.e(tag, "responseBody's content : " + resp);

                            body = ResponseBody.create(mediaType, resp);
                            Log.e(tag, "========response'log=======end");
                            return response.newBuilder().body(body).build();
                        } else {
                            Log.e(tag, "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }
            Log.e(tag, "========response'log=======end");
        } catch (Exception e) {}

        return response;
    }


    /**
     * 判断请求体类型是否为文本类型
     *
     * @param mediaType
     * @return
     */
    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    /**
     * 请求体转换为String
     *
     * @param request
     * @return
     */
    private String bodyToString(final Request request) {
        try {
            final Request req = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            req.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
