package com.xbing.app.net.common.request;

import android.text.TextUtils;

import com.xbing.app.net.common.OkHttpUtils;
import com.xbing.app.net.common.utils.Exceptions;
import com.xbing.app.net.okhttp3.MediaType;
import com.xbing.app.net.okhttp3.Request;
import com.xbing.app.net.okhttp3.RequestBody;
import com.xbing.app.net.okhttp3.internal.http.HttpMethod;

import java.util.Map;
/**
 * Created by zhaobing  16/2/23.
 */
public class OtherRequest extends OkHttpRequest
{
    private MediaType mediaType;

    private RequestBody requestBody;
    private String method;
    private String content;

    public OtherRequest(RequestBody requestBody, String content, MediaType mediaType, String method, String url, Object tag, Map<String, String> params, Map<String, String> headers, int id)
    {
        super(url, tag, params, headers,id);
        this.requestBody = requestBody;
        this.method = method;
        this.content = content;
        this.mediaType = mediaType;
    }

    @Override
    protected RequestBody buildRequestBody()
    {
        if (requestBody == null && TextUtils.isEmpty(content) && HttpMethod.requiresRequestBody(method))
        {
            Exceptions.illegalArgument("requestBody and content can not be null in method:" + method);
        }

        if (requestBody == null && !TextUtils.isEmpty(content))
        {
            requestBody = RequestBody.create(mediaType, content);
        }

        return requestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody)
    {
        if (method.equals(OkHttpUtils.METHOD.PUT))
        {
            builder.put(requestBody);
        } else if (method.equals(OkHttpUtils.METHOD.DELETE))
        {
            if (requestBody == null)
                builder.delete();
            else
                builder.delete(requestBody);
        } else if (method.equals(OkHttpUtils.METHOD.HEAD))
        {
            builder.head();
        } else if (method.equals(OkHttpUtils.METHOD.PATCH))
        {
            builder.patch(requestBody);
        }

        return builder.build();
    }

}
