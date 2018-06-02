package com.xbing.app.net.common.builder;

import java.util.Map;

/**
 * Created by zhaobing 18/06/02.
 */
public interface HasParamsable
{
    OkHttpRequestBuilder params(Map<String, String> params);
    OkHttpRequestBuilder addParams(String key, String val);
}
