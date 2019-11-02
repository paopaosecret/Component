package com.xbing.app.net.common.cookie;

import com.xbing.app.net.common.cookie.store.CookieStore;
import com.xbing.app.net.common.utils.Exceptions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by zhaobing 18/06/020.
 */
public class CookieJarImpl implements CookieJar
{
    private CookieStore cookieStore;

    private Map<String, Set<Cookie>> cookies = new HashMap<>();  //用户手动添加的Cookie

    public void addCookies(List<Cookie> cookieList) {
        for (Cookie cookie : cookieList) {
            String domain = cookie.domain();
            Set<Cookie> domainCookies = cookies.get(domain);
            if (domainCookies == null) {
                domainCookies = new HashSet<>();
                cookies.put(domain, domainCookies);
            }
            domainCookies.add(cookie);
        }
    }

    public CookieJarImpl(CookieStore cookieStore)
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
