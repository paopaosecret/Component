package com.xbing.app.basic.router;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.xbing.app.basic.router.action.FlutterPageAction;
import com.xbing.app.basic.router.action.NativeFunctionAction;
import com.xbing.app.basic.router.action.NativePageAction;
import com.xbing.app.basic.router.action.WebPageAction;
import com.xbing.app.basic.router.callback.CallBack;
import com.xbing.app.basic.router.core.DispatchCenter;
import com.xbing.app.basic.router.entity.TransferEntity;
import com.xbing.app.basic.router.parse.TransferEntityParse;

/**
 *
 * @作者 zhaobing04
 *
 * @创建日期 2020/7/30 10:41
 */
public class HyRouter {

    public static boolean transfer(Context context, Uri uri, CallBack callBack) {
        Log.d("HyRouter","transfer");
        TransferEntity entity = TransferEntityParse.parseEntity(uri);
        if(entity == null){
            return false;
        }
        return DispatchCenter.dispatch(context, entity, callBack);
    }

    public static void init(){
        FlutterPageAction.getInstance();
        NativePageAction.getInstance();
        WebPageAction.getInstance();
        NativeFunctionAction.getInstance();
    }

    public static void unInit(){
        FlutterPageAction.getInstance().unInit();
        NativePageAction.getInstance().unInit();
        WebPageAction.getInstance().unInit();
        NativeFunctionAction.getInstance().unInit();
    }
}
