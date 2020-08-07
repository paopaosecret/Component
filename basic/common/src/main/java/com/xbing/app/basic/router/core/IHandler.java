package com.xbing.app.basic.router.core;

import android.content.Context;

import com.xbing.app.basic.router.callback.CallBack;
import com.xbing.app.basic.router.entity.TransferEntity;

/**
 * 处理各个平台的请求接口
 *
 * @作者 zhaobing04
 *
 * @创建日期 2020/7/30 9:07
 */
public interface IHandler {
    boolean hanlde(Context context, TransferEntity entity, CallBack callBack);
}
