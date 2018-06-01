package com.xbing.app.account;

import com.xbing.app.account.result.RequestResult;

/**
 * Created by zhaobing on 2016/9/24.
 */
public interface RequestCallback {
    void onRequestComplete(RequestResult result);
}
