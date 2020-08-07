package com.xbing.app.basic.router.action.base;

import android.content.Context;
import com.xbing.app.basic.router.callback.CallBack;
import com.xbing.app.basic.router.entity.TransferEntity;
import java.io.Serializable;

/**
 *
 * @作者 zhaobing04
 *
 * @创建日期 2020/7/30 11:25
 */
public class BaseEvent implements Serializable {
    private TransferEntity transferEntity;
    private Context context;
    private CallBack callBack;

    protected BaseEvent(Context context, TransferEntity transferEntity, CallBack callBack){
        this.transferEntity = transferEntity;
        this.context = context;
        this.callBack = callBack;
    }

    public TransferEntity getTransferEntity() {
        return transferEntity;
    }

    public void setTransferEntity(TransferEntity transferEntity) {
        this.transferEntity = transferEntity;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
