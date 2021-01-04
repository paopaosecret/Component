package com.xbing.app.component.utils.performance;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Choreographer;
import android.view.Display;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class FrameCheckUtils {
    //清空之前采样的数据，防止统计重复的时间
    private static String clearCommand = "dumpsys SurfaceFlinger --latency-clear";
    private static long jumpingFrames = 0; //jank次数，跳帧数
    private static long totalFrames = 0;  //统计的总帧数
    private static float lostFrameRate = 0; //丢帧率
    private static float fps; //fps值


    public static String getFrameInfo(String packageName) {
        String gfxCMD = "dumpsys SurfaceFlinger --latency com.xbing.app.component/com.xbing.app.component.ui.activity.layer2.CoordinatorActivity#0";
        StringBuffer sb = new StringBuffer("");
        try {
            Process process = Runtime.getRuntime().exec(gfxCMD);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    sb.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        test1();
        return sb.toString();
    }
    private static final int UPDATE_RATE = 30;  // Frames per second (fps)
    public static void test1(){
        String gfxCMD = "dumpsys gfxinfo com.xbing.app.component";
//        String gfxCMD = "dumpsys -l";
        StringBuffer sb = new StringBuffer("");
        StringBuffer errorsb = new StringBuffer("");
        try {
            Process process = Runtime.getRuntime().exec(gfxCMD);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    sb.append(line);
                }
            }
            Log.d("FrameCheckUtils", sb.toString());
            BufferedReader errorreader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorreader.readLine()) != null) {
                if (line.length() > 0) {
                    errorsb.append(line);
                }
            }

            Log.d("FrameCheckUtils", errorsb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }


    /**
     * 获取默认Vsync 刷新频率
     * @param context
     * @return
     */
    public static String getVsyncRate(Activity context){
        Display display = context.getWindowManager().getDefaultDisplay();
        float refreshRate = display.getRefreshRate();
        Log.d("FrameCheckUtils", "refreshRate:" + refreshRate);
        return refreshRate + "";
    }

    public static void test3(String packageName){
        String gfxCMD = "dumpsys batterystats --charged";
        StringBuffer sb = new StringBuffer("");
        StringBuffer errorsb = new StringBuffer("");
        try {
            Process process = Runtime.getRuntime().exec(gfxCMD);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    sb.append(line);
                }
            }
            Log.d("FrameCheckUtils", sb.toString());
            BufferedReader errorreader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorreader.readLine()) != null) {
                if (line.length() > 0) {
                    errorsb.append(line);
                }
            }

            Log.d("FrameCheckUtils", errorsb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
}
