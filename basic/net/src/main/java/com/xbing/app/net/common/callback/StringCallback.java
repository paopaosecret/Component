package com.xbing.app.net.common.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zhaobing  15/12/14.
 */
public abstract class StringCallback extends Callback<String>
{
    @Override
    public String parseNetworkResponse(Response response) throws IOException
    {
        return response.body().string();
    }
}
