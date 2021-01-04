package com.xbing.app.component.utils.performance;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryChekcUtils {
    private static final String TAG = MemoryChekcUtils.class.getSimpleName();

    /**
     * 获取运行时应用内存情况
     *
     * @param context
     * @return
     */
    public static String getMemoryInfo(Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获得系统里正在运行的所有进程 
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessesList = mActivityManager.getRunningAppProcesses();
        StringBuffer sb = new StringBuffer("");
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessesList) {
            sb.append("进程名=" + runningAppProcessInfo.processName + "\n");
            sb.append("进程ID=" + runningAppProcessInfo.pid + "\n");
            sb.append("用户ID=" + runningAppProcessInfo.uid + "\n");

            // 占用的内存 
            int[] pids = new int[]{runningAppProcessInfo.pid};
            Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(pids);
            Log.d(TAG, JSON.toJSONString(memoryInfo));
            Map<String, String> stats = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                stats = memoryInfo[0].getMemoryStats();
                sb.append("java堆    =" + Double.parseDouble(stats.get("summary.java-heap"))/1024.0 + "MB\n");
                sb.append("native堆  =" + Double.parseDouble(stats.get("summary.native-heap"))/1024.0 + "MB\n");
                sb.append("Code区    =" + Double.parseDouble(stats.get("summary.code"))/1024.0 + "MB\n");
                sb.append("栈        =" + Double.parseDouble(stats.get("summary.stack"))/1024.0 + "MB\n");
                sb.append("graphics  =" + Double.parseDouble(stats.get("summary.graphics"))/1024.0 + "MB\n");
                sb.append("Others    =" + Double.parseDouble(stats.get("summary.private-other"))/1024.0 + "MB\n");
                sb.append("系统      =" + Double.parseDouble(stats.get("summary.system"))/1024.0 + "MB\n");
                sb.append("total-pss =" + Double.parseDouble(stats.get("summary.total-pss"))/1024.0 + "MB\n");
                sb.append("total-swap=" + Double.parseDouble(stats.get("summary.total-swap"))/1024.0 + "MB\n");

                //反射获取Totaluss
                try {
                    Method method = Debug.MemoryInfo.class.getMethod("getTotalUss");
                    int totalUss = (Integer)(method.invoke(memoryInfo[0], null));
                    sb.append("total-uss =" + (double)totalUss/1024.0 + "MB\n");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }else{
                sb.append("6.0之下暂不支持");
            }

        }

        return sb.toString();
    }

    /**
     * 获取运行时应用内存情况
     *
     * @param context
     * @return
     */
    public static String getMemoryRate(Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获得系统里正在运行的所有进程 
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessesList = mActivityManager.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessesList) {
            // 占用的内存 
            int[] pids = new int[]{runningAppProcessInfo.pid};
            Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(pids);
            Log.d(TAG, JSON.toJSONString(memoryInfo));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    Method method = Debug.MemoryInfo.class.getMethod("getTotalUss");
                    int totalUss = (Integer)(method.invoke(memoryInfo[0], null));
                    return (double)totalUss/1024.0 + "MB";
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return "0";
    }
}
