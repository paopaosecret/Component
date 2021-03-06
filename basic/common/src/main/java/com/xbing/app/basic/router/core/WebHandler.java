package com.xbing.app.basic.router.core;

import android.content.Context;
import android.util.Log;
import com.xbing.app.basic.common.rxbus.RxBus;
import com.xbing.app.basic.router.action.event.WebPageEvent;
import com.xbing.app.basic.router.callback.CallBack;
import com.xbing.app.basic.router.entity.TransferEntity;
import com.xbing.app.basic.router.protocel.HyRouterConstant;

/**
 * 处理目标页为Web的请求
 *
 * @作者 zhaobing04
 *
 * @创建日期 2020/7/30 9:16
 */
public class WebHandler implements IHandler {


    @Override
    public boolean hanlde(Context context, TransferEntity entity, CallBack callBack) {
        Log.d("HyRouter","NativeHandler hanlde");
        switch (entity.getType()){
            case HyRouterConstant.HYROUTER_NATIVE_TYPE_PAGE:
                RxBus.getInstance().post(new WebPageEvent(context, entity, callBack));
                break;

            case HyRouterConstant.HYROUTER_NATIVE_TYPE_FUNCTION:

            default:
                return false;
        }
        return false;
    }
}
