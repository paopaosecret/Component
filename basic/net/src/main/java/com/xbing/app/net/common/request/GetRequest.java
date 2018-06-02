package com.xbing.app.net.common.request;

import com.xbing.app.net.okhttp3.Request;
import com.xbing.app.net.okhttp3.RequestBody;

import java.util.Map;

/**
 * Created by zhaobing  15/12/14.
 */
public class GetRequest extends OkHttpRequest
{
    public GetRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id)
    {
        super(url, tag, params, headers,id);
    }

    @Override
    protected RequestBody buildRequestBody()
    {
        return null;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody)
    {
        return builder.get().build();
    }


}
