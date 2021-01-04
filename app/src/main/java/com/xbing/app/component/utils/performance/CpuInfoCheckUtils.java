package com.xbing.app.component.utils.performance;

import android.os.Process;
import android.text.TextUtils;
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
            java.lang.Process p;
            p = Runtime.getRuntime().exec("top -n 1");
            StringBuffer sb = new StringBuffer("");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int cpuIndex = -1;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() < 1) {
                    continue;
                } else {
                    // 以空格区分正则
                    line = line.trim();
                    if (TextUtils.isEmpty(line)) {
                        continue;
                    }
                    int tempIndex = getCPUIndex(line);
                    if (tempIndex != -1) {
                        cpuIndex = tempIndex;
                        continue;
                    }
                    if (line.startsWith(String.valueOf(Process.myPid()))) {
                        if (cpuIndex == -1) {
                            continue;
                        }
                        String[] param = line.split("\\s+");
                        if (param.length <= cpuIndex) {
                            continue;
                        }
                        String cpu = param[cpuIndex];
                        if (cpu.endsWith("%")) {
                            cpu = cpu.substring(0, cpu.lastIndexOf("%"));
                        }
                        rate = Float.parseFloat(cpu) / Runtime.getRuntime().availableProcessors() + "";
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


    private static int getCPUIndex(String line) {
        if (line.contains("CPU")) {
            String[] titles = line.split("\\s+");
            for (int i = 0; i < titles.length; i++) {
                if (titles[i].contains("CPU")) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static String getCpuRate(String packageName) {
        String rate = "";
        try {
            String line;
            java.lang.Process p;
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
                            return cpuInfo[9] + "%";
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
        return "0";
    }
}
