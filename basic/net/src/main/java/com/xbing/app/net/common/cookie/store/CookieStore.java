package com.xbing.app.net.common.cookie.store;

import com.xbing.app.net.okhttp3.Cookie;
import com.xbing.app.net.okhttp3.HttpUrl;

import java.util.List;


public interface CookieStore
{

    void add(HttpUrl uri, List<Cookie> cookie);

    List<Cookie> get(HttpUrl uri);

    List<Cookie> getCookies();

    boolean remove(HttpUrl uri, Cookie cookie);

    boolean removeAll();

}
