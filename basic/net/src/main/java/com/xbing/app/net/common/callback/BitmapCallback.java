package com.xbing.app.net.common.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.xbing.app.net.okhttp3.Response;

/**
 * Created by zhaobing  15/12/14.
 */
public abstract class BitmapCallback extends Callback<Bitmap>
{
    @Override
    public Bitmap parseNetworkResponse(Response response , int id) throws Exception
    {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }

}
