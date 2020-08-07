package com.xbing.app.basic.router.action.event;

import android.content.Context;

import com.xbing.app.basic.router.action.base.BaseEvent;
import com.xbing.app.basic.router.callback.CallBack;
import com.xbing.app.basic.router.entity.TransferEntity;

/**
 *
 * @作者 zhaobing04
 *
 * @创建日期 2020/7/30 11:03
 */
public class NativeFunctionEvent extends BaseEvent {

    public NativeFunctionEvent(Context context, TransferEntity transferEntity, CallBack callBack) {
        super(context, transferEntity, callBack);
    }
}
