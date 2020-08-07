package com.xbing.app.basic.router.parse;

import android.net.Uri;
import android.text.TextUtils;
import com.xbing.app.basic.router.entity.TransferEntity;
import com.xbing.app.basic.router.protocel.HyRouterConstant;


/**
 *
 * @作者 zhaobing04
 *
 * @创建日期 2020/7/30 10:42
 */
public class TransferEntityParse {

    private static boolean checkEntityLegal(TransferEntity entity) {
        if(entity == null){
            return false;
        }

        if(!TextUtils.isEmpty(entity.getScheme()) && HyRouterConstant.HYROUTER_SCHEME.equals(entity.getScheme())
                && !TextUtils.isEmpty(entity.getPlatform())
                && (HyRouterConstant.HYROUTER_HOST_FLUTTER.equals(entity.getPlatform()) || HyRouterConstant.HYROUTER_HOST_WEB.equals(entity.getPlatform()) || HyRouterConstant.HYROUTER_HOST_NATIVE.equals(entity.getPlatform()))
                && !TextUtils.isEmpty(entity.getType())
                && (HyRouterConstant.HYROUTER_NATIVE_TYPE_PAGE.equals(entity.getType()) || HyRouterConstant.HYROUTER_NATIVE_TYPE_FUNCTION.equals(entity.getType()))
                && !TextUtils.isEmpty(entity.getBusiness())
                && HyRouterConstant.HYROUTER_NATIVE_BUSINESS_SHANGJIATONG.equals(entity.getBusiness())){
            return true;
        }else{
            return false;
        }
    }

    public static TransferEntity parseEntity(Uri uri) {
        if(uri == null){
            return null;
        }else{
            TransferEntity entity = new TransferEntity();
            entity.setScheme(uri.getScheme());
            entity.setPlatform(uri.getHost());
            if(TextUtils.isEmpty(uri.getPath())){
                return null;
            }
            String[] pathArray = uri.getPath().split("/");
            if(pathArray.length == 4){
                entity.setType(pathArray[1]);
                entity.setBusiness(pathArray[2]);
                entity.setKey(pathArray[3]);
            }else{
                return null;
            }
            String params = uri.getQueryParameter("params");
            entity.setParams(params);

            if(checkEntityLegal(entity)){
                return entity;
            }
            return null;
        }
    }
}
