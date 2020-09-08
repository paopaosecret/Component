package com.xbing.app.component.utils.performance.entity;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private int uid;        //用户id
    private String phone;   //用户手机号
    private String netWork; // 网络运营商

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNetWork() {
        return netWork;
    }

    public void setNetWork(String netWork) {
        this.netWork = netWork;
    }
}
