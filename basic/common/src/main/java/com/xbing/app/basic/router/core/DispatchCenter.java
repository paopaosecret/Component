package com.xbing.app.basic.router.core;

import android.content.Context;
import android.util.Log;
import com.xbing.app.basic.router.callback.CallBack;
import com.xbing.app.basic.router.entity.TransferEntity;
import com.xbing.app.basic.router.protocel.HyRouterConstant;


/**
 *
 * @作者 zhaobing04
 *
 * @创建日期 2020/7/30 10:43
 */
public class DispatchCenter {

    public static boolean dispatch(Context context, TransferEntity entity, CallBack callBack){
        Log.d("HyRouter","DispatchCenter dispatch");
        IHandler handler = null;
        switch (entity.getPlatform()){
            case HyRouterConstant.HYROUTER_HOST_NATIVE:
                handler = HandlerFactory.createNativeHandler();
                break;

            case HyRouterConstant.HYROUTER_HOST_FLUTTER:
                handler = HandlerFactory.createFlutterHandler();
                break;

            case HyRouterConstant.HYROUTER_HOST_WEB:
                handler = HandlerFactory.createWebHandler();
                break;

            default:
                return false;
        }
        if(handler != null){
            return handler.hanlde(context, entity, callBack);
        }else{
            return false;
        }
    }

}
