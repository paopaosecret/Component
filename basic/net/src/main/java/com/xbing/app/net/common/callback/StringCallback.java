package com.xbing.app.net.common.callback;

import com.xbing.app.net.okhttp3.Response;

import java.io.IOException;

/**
 * Created by zhaobing  15/12/14.
 */
public abstract class StringCallback extends Callback<String>
{
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException
    {
        return response.body().string();
    }
}
