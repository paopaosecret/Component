package com.xbing.app.basic.router.entity;

import java.io.Serializable;

/**
 *
 * @作者 zhaobing04
 *
 * @创建日期 2020/7/30 10:42
 */
public class TransferEntity implements Serializable {

    private String scheme;

    private String platform;

    private String type;

    private String business;

    private String key;

    private String params;

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
