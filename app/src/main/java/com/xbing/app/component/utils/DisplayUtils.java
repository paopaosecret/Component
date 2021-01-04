package com.xbing.app.component.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by 58 on 2016/11/29.
 */

public class DisplayUtils {
    public static String testSize(Context context) {
        StringBuffer sb = new StringBuffer("");
        sb.append("hasNavigationBar: " + hasNavigationBar(context) + "\n");
        sb.append("getScreenHeight: "+getScreenHeight(context) + "\n");
        sb.append("getScreenHeight2: "+getScreenHeight2(context) + "\n");
        sb.append("getScreenHeight3: "+getScreenHeight3(context) + "\n");
        sb.append("getNavigationBarHeight :  "+getNavigationBarHeight(context) + "\n");
        sb.append("getStatusBarHeight:  "+ getStatusBarHeight(context) + "\n");
        sb.append("getScreenWidth: "+getScreenWidth(context) + "\n");
        sb.append("getScreenWidth2: "+getScreenWidth2(context) + "\n");
        sb.append("getScreenWidth3: "+getScreenWidth3(context) + "\n");

        Log.i("android.bing", "WindowManager hasNavigationBar():: " + hasNavigationBar(context));
        Log.i("android.bing", "getResources().getDisplayMetrics():: heightPixels: "+getScreenHeight(context));
        Log.i("android.bing", "WindowManager:: heightPixels: "+getScreenHeight2(context));
        Log.i("android.bing", "WindowManager getRealSize() :: heightPixels: "+getScreenHeight3(context));
        Log.i("android.bing", "getResources() :  "+getNavigationBarHeight(context));
        Log.i("android.bing", "getResources() getStatusBarHeight:  "+ getStatusBarHeight(context));

        Log.i("android.bing", "getResources().getDisplayMetrics():: widthPixels: "+getScreenWidth(context));
        Log.i("android.bing", "WindowManager::navigation_bar_height widthPixels: "+getScreenWidth2(context));
        Log.i("android.bing", "WindowManager getRealSize() :: widthPixels: "+getScreenWidth3(context));

        return sb.toString();
    }

    /**
     * 竖屏时不包含虚拟导航栏高度和状态栏高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 横屏时不包含导航栏
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 同{@link DisplayUtils#getScreenHeight(Context)}
     * @param context
     * @return
     */
    public static int getScreenHeight2(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 同{@link DisplayUtils#getScreenWidth(Context)}
     * @param context
     * @return
     */
    public static int getScreenWidth2(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 横屏时包含导航栏和状态栏高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth3(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        defaultDisplay.getRealSize(outPoint);
        return outPoint.x;
    }

    /**
     * 包含虚拟导航栏高度和状态栏高度
     * @param context
     * @return
     */
    public static int getScreenHeight3(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        defaultDisplay.getRealSize(outPoint);
        return outPoint.y;
    }


    /**
     * 判断是否横屏
     * @param context
     * @return
     */
    public static boolean isHengping(Context context){
        Configuration mConfiguration = context.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        boolean flag = false;
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            //横屏
            flag = true;
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            //竖屏
            flag = false;
        }
        return flag;
    }

    /**
     * 切换横竖屏
     * @param context
     */
    public static void qieHuan(Activity context){
        Configuration mConfiguration = context.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        boolean flag = false;
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }


    /**
     * Convert Dp to Pixel
     */
    public static int dpToPx(float dp, Context context) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return (int) px;
    }

    public static int spToPx(float sp, Context context) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
        return (int) px;
    }

    /**
     * 应用是否在前台
     *
     * @param context
     * @return
     */
    public static boolean isForeground(Context context) {
        ActivityManager mActivityManager = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        String mPackageName = context.getPackageName();
        List<ActivityManager.RunningTaskInfo> tasksInfo = mActivityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            // 应用程序位于堆栈的顶层
            if (mPackageName.equals(tasksInfo.get(0).topActivity
                    .getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static int getWindowWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getWindowHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    private static WindowManager getWindowManager(Context context){
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    /**
     * 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getNavigationBarHeight(Context context) {
        if (hasNavigationBar(context)) {
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            return resources.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    private static boolean hasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources resources = context.getResources();
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = resources.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;
    }

}