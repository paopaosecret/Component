package com.xbing.app.net.common.cookie.store;


import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;


public interface CookieStore
{

    /**
     * 保存url对应所有的cookie
     * @param uri
     * @param cookie
     */
    void add(HttpUrl uri, List<Cookie> cookie);

    /**
     * 获取url对应的所有的cookie
     * @param uri
     * @return
     */
    List<Cookie> get(HttpUrl uri);

    /**
     * 获取当前所有保存的Cookies
     */
    List<Cookie> getALL();

    /**
     * 删除url下指定的cookie
     * @param uri
     * @param cookie
     * @return
     */
    boolean remove(HttpUrl uri, Cookie cookie);

    /**
     * 删除url下全部的cookie
     * @param uri
     * @return
     */
    boolean remove(HttpUrl uri);

    /**
     * 删除所有的cookie
     * @return
     */
    boolean removeAll();

}
