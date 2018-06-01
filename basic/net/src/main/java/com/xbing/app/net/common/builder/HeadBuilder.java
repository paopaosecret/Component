package com.xbing.app.net.common.builder;

import com.xbing.app.net.common.OkHttpUtils;
import com.xbing.app.net.common.request.OtherRequest;
import com.xbing.app.net.common.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
