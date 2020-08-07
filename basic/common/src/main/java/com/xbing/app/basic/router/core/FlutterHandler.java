package com.xbing.app.basic.router.core;

import android.content.Context;
import android.util.Log;
import com.xbing.app.basic.common.rxbus.RxBus;
import com.xbing.app.basic.router.action.event.FlutterPageEvent;
import com.xbing.app.basic.router.callback.CallBack;
import com.xbing.app.basic.router.entity.TransferEntity;
import com.xbing.app.basic.router.protocel.HyRouterConstant;

/**
 * 处理目标页为Flutter的请求
 *
 * @作者 zhaobing04
 *
 * @创建日期 2020/7/30 9:16
 */
public class FlutterHandler implements IHandler {


    @Override
    public boolean hanlde(Context context, TransferEntity entity, CallBack callBack) {
        Log.d("HyRouter","FlutterHandler hanlde");
        switch (entity.getType()){
            case HyRouterConstant.HYROUTER_NATIVE_TYPE_PAGE:
                RxBus.getInstance().post(new FlutterPageEvent(context, entity, callBack));
                break;

            case HyRouterConstant.HYROUTER_NATIVE_TYPE_FUNCTION:

            default:
                return false;
        }
        return false;
    }
}
