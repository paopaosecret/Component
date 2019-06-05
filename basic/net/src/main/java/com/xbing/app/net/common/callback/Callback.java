package com.xbing.app.net.common.callback;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 回调接口
 * @param <T>
 */
public abstract class Callback<T>
{
    /**
     * 请求网络开始之前，在UI线程中执行
     *
     * @param request
     */
    public void onBefore(Request request) {}

    /**
     * 服务器返回response后，将数据转换成需要的数据格式，子线程中执行，可以是耗时操作
     *
     * @param response
     */
    public abstract T parseNetworkResponse(Response response) throws Exception;

    /**
     * 对返回数据进行操作的回调， UI线程
     * @param response
     */
    public abstract void onResponse(T response);

    /**
     * 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程
     *
     */
    public abstract void onError(Call call, Exception e);

    /**
     *  请求网络结束后，UI线程
     *
     * @param
     */
    public void onAfter() {}

    /**
     * UI Thread
     *
     * @param progress
     */
    public void inProgress(float progress, long total) {}

    /**
     * if you parse reponse code in parseNetworkResponse, you should make this method return true.
     *
     * @param response
     * @return
     */
    public boolean validateReponse(Response response)
    {
        return response.isSuccessful();
    }




    public static Callback CALLBACK_DEFAULT = new Callback()
    {

        @Override
        public Object parseNetworkResponse(Response response) throws Exception
        {
            return response;
        }

        @Override
        public void onError(Call call, Exception e)
        {

        }

        @Override
        public void onResponse(Object response)
        {

        }
    };

}