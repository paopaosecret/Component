package com.xbing.app.net.common.builder;

import com.xbing.app.net.common.request.PostStringRequest;
import com.xbing.app.net.common.request.RequestCall;
import com.xbing.app.net.okhttp3.MediaType;


/**
 * Created by zhaobing  15/12/14.
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder>
{
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content)
    {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostStringRequest(url, tag, params, headers, content, mediaType,id).build();
    }


}
