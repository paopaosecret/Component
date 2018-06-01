package com.xbing.app.account.result;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhaobing on 2016/9/23.
 */
public class RequestResult {

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public String responseString;

    @SerializedName("code")
    public String resultCode;

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    @SerializedName("msg")
    public String resultMsg;

    public boolean isResultOk(){
        return  "0".equals(resultCode);
    }

    @Override
    public String toString() {
        return "msg:" + getResultMsg() +
               ",code:" + getResultMsg();
    }
}
