package com.xbing.app.component.utils.performance;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CpuInfoCheckUtils {
    private static final String TAG = CpuInfoCheckUtils.class.getSimpleName();

    /**
     * 获取cpu使用率
     * @return
     */
    public static String getProcessCpuRate(String packageName) {
        String rate = "";
        try {
            String line;
            Process p;
            p = Runtime.getRuntime().exec("top -n 1");
            StringBuffer sb = new StringBuffer("");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = br.readLine()) != null) {
                if (line.trim().length() < 1) {
                    continue;
                } else {
                    // 以空格区分正则
                    if(line.contains(packageName.substring(0,10))){
                        String[] cpuInfo = line.split(" +");
                        if(cpuInfo != null && cpuInfo.length > 10){
                            sb.append("进程ID:" + cpuInfo[0] + "\n");
                            sb.append("用户:" + cpuInfo[1] + "\n");
                            sb.append("CPU使用率：" + cpuInfo[9] + "\n");
                            break;
                        }
                    }
                }
            }
            rate ="CPU使用情况：\n" + sb.toString();
            Log.e(TAG, sb.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rate;
    }
}
