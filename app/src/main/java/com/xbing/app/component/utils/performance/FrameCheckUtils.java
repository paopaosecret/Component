package com.xbing.app.component.utils.performance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

public class FrameCheckUtils {
    //清空之前采样的数据，防止统计重复的时间
    private static String clearCommand = "dumpsys SurfaceFlinger --latency-clear";
    private static long jumpingFrames = 0; //jank次数，跳帧数
    private static long totalFrames = 0;  //统计的总帧数
    private static float lostFrameRate = 0; //丢帧率
    private static float fps; //fps值

    public static String getFrameInfo(String packageName) {
        String gfxCMD = "dumpsys gfxinfo " + packageName;
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
        return sb.toString();
    }
}
