package com.xbing.app.account.result;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhaobing on 2016/9/24.
 */
public class LoginResult extends RequestResult {

    private String token;
    private String uuid;
    //过期时间
    private String expire;

    @SerializedName("account_id")
    private String accountId;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @SerializedName("trace_id")

    private String traceId;
}
