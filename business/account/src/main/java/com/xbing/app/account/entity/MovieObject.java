package com.xbing.app.account.entity;

public class MovieObject {
    private String msg;
    private int code;
    private String request;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String toString(){
        return "msg:" + msg
                + ",code:" + code
                + ",request:" + request;
    }
}
