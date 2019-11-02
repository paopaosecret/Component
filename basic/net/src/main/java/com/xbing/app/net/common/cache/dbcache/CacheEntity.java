package com.xbing.app.net.common.cache.dbcache;

import com.xbing.app.net.common.entity.HttpHeaders;

import java.io.Serializable;

public class CacheEntity<T> implements Serializable{

    /**缓存永不过期 */
    public static final long CACHE_NEVER_EXPIRE = -1;

    /** 该变量不必保存到数据库,程序运行起来后会动态计算 */
    private boolean isExpire;

    private long id;

    private String key;

    private HttpHeaders headers;

    private T data;

    private long localExpire;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getLocalExpire() {
        return localExpire;
    }

    public void setLocalExpire(long localExpire) {
        this.localExpire = localExpire;
    }

    public boolean isExpire() {
        return isExpire;
    }

    public void setExpire(boolean expire) {
        isExpire = expire;
    }

    /**
     *
     * @param cacheMode  自定义的缓存模式
     * @param cacheTime  允许的缓存时间
     * @param baseTime   基准时间，小于当前时间视为过期
     *
     * @return 判断缓存是否过期
     */
    public boolean checkExpire(CacheMode cacheMode, long cacheTime, long baseTime){
        if(cacheMode == CacheMode.DEFAULT){
            return getLocalExpire() < baseTime;
        }

        if(cacheTime == CACHE_NEVER_EXPIRE){
            return false;
        }
        return getLocalExpire() + cacheTime < baseTime;
    }

    @Override
    public String toString() {
        return "CacheEntity{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", responseHeaders=" + headers +
                ", data=" + data +
                ", localExpire=" + localExpire +
                '}';
    }
}
