package com.xbing.app.component.utils.performance.entity;

import java.io.Serializable;

public class SysInfo implements Serializable {
    private String activity; //页面名称
    private String cpuInfo;  //cpu信息
    private String memInfo;  //内存信息
    private String fps; //流畅率
    private String timeStamp;  //时间戳

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getCpuInfo() {
        return cpuInfo;
    }

    public void setCpuInfo(String cpuInfo) {
        this.cpuInfo = cpuInfo;
    }

    public String getMemInfo() {
        return memInfo;
    }

    public void setMemInfo(String memInfo) {
        this.memInfo = memInfo;
    }

    public String getFps() {
        return fps;
    }

    public void setFps(String fps) {
        this.fps = fps;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
