package com.xbing.app.component.utils.performance.entity;

import java.io.Serializable;

public class DeviceInfo implements Serializable {

    private int platform;       //设备类型 1-安卓 2-iOS
    private String phoneType;   //设备型号：iphonex，huawei P30
    private String os;          // 操作系统版本
    private String appVersion;  //app版本号

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}
