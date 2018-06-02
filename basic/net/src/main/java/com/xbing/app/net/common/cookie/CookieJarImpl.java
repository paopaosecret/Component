package com.xbing.app.net.common.cookie;

import com.xbing.app.net.common.cookie.store.CookieStore;
import com.xbing.app.net.common.cookie.store.MemoryCookieStore;
import com.xbing.app.net.common.utils.Exceptions;
import com.xbing.app.net.okhttp3.Cookie;
import com.xbing.app.net.okhttp3.CookieJar;
import com.xbing.app.net.okhttp3.HttpUrl;

import java.util.List;

/**
 * Created by zhaobing 18/06/020.
 */
public class CookieJarImpl implements CookieJar
{
    private CookieStore cookieStore;

    public CookieJarImpl(MemoryCookieStore cookieStore)
    {
        if (cookieStore == null) Exceptions.illegalArgument("cookieStore can not be null.");
        this.cookieStore = cookieStore;
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies)
    {
        cookieStore.add(url, cookies);
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url)
    {
        return cookieStore.get(url);
    }

    public CookieStore getCookieStore()
    {
        return cookieStore;
    }
}
