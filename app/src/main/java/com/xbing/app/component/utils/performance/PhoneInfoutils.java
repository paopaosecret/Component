package com.xbing.app.component.utils.performance;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * @author zhaobing04
 */
public class PhoneInfoutils {

    /**
     * 获取运营商名字
     * @param context
     * @return
     */
    public static String getSimeName(Context context){
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String name = telephonyManager.getNetworkOperatorName();
            Log.d("PhoneInfoutils",name);
            return name;
        }catch (Exception e){}
        return "";
    }

    /**
     * 获取当前Acitivty名称
     *
     * @param context
     * @return
     */
    public static String getCurrentActivity(Context context){
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if(am != null){
                ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
                Log.d("PhoneInfoutils", "pkg:"+cn.getPackageName());//包名
                Log.d("PhoneInfoutils", "cls:"+cn.getClassName());//包名加类名
                if(cn != null){
                    return cn.getClassName();
                }
            }
        }catch (Exception e){}
        return "";
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        Log.d("PhoneInfoutils", android.os.Build.VERSION.RELEASE);
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        Log.d("PhoneInfoutils", android.os.Build.MODEL);
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        Log.d("PhoneInfoutils", android.os.Build.BRAND);
        return android.os.Build.BRAND;
    }
}
