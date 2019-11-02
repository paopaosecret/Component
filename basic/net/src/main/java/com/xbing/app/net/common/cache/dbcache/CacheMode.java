package com.xbing.app.net.common.cache.dbcache;

/**
 * 缓存的几种模式
 */
public enum CacheMode {

    /** 按照HTTP协议的默认缓存规则，例如有304响应头时缓存    返回一次  */
    DEFAULT,

    /** 不使用缓存                                返回一次    */
    NO_CACHE,

    /** 网络请求失败后，读取缓存                   返回一次  */
    REQUEST_FAILED_READ_CACHE,

    /** 如果缓存不存在才请求网络，否则使用缓存      返回一次*/
    IF_NONE_CACHE_REQUEST,

    /** 先使用缓存返回，然后请求网络返回           返回两次*/
    FIRST_CACHE_THEN_REQUEST
}
