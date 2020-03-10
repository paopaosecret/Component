package com.xbing.app.component.event;

public class NewEvent {
    private String type;
    private String msg;

    public NewEvent(String type, String msg){
        this.type = type;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
