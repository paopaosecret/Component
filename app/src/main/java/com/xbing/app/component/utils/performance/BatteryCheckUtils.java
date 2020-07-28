package com.xbing.app.component.utils.performance;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xbing.app.basic.common.DateUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import androidx.annotation.NonNull;

public class BatteryCheckUtils {
    private static final String TAG = BatteryCheckUtils.class.getSimpleName();
    private final static String PACKAGE_NAME_UNKNOWN = "unknown";

    public static boolean checkAppUsagePermission(Context context) {
        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        if(usageStatsManager == null) {
            return false;
        }
        long currentTime = System.currentTimeMillis();
        // try to get app usage state in last 1 min
        List<UsageStats> stats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, currentTime - 60 * 1000, currentTime);
        if (stats.size() == 0) {
            return false;
        }

        return true;
    }

    public static void requestAppUsagePermission(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.i(TAG,"Start usage access settings activity fail!");
        }
    }

    public static String getUsageInfo(@NonNull Context context) {
        final UsageStatsManager usageStatsManager = (UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);
        if(usageStatsManager == null) {
            return PACKAGE_NAME_UNKNOWN;
        }

        String topActivityPackageName = PACKAGE_NAME_UNKNOWN;
        long time = System.currentTimeMillis();
        // 查询最后十秒钟使用应用统计数据
        List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, DateUtils.getDayBegin(), time);
        // 以最后使用时间为标准进行排序
        StringBuffer sb = new StringBuffer("");
        if(usageStatsList != null) {
            SortedMap<Long,UsageStats> sortedMap = new TreeMap<Long,UsageStats>();
            for (UsageStats usageStats : usageStatsList) {
                sortedMap.put(usageStats.getLastTimeUsed(),usageStats);
                if(usageStats.getPackageName().equals(context.getPackageName())){
                    Log.d(TAG, JSON.toJSONString(usageStats));
                    sb.append(usageStats.getPackageName() + "\n");
                    int appLaunchCount = (Integer) getHideField("mLaunchCount", usageStats);
                    sb.append("启动次数：" + appLaunchCount + "\n");
                    sb.append("前台显示时间：" + usageStats.getTotalTimeInForeground()/1000 + "\n");

                }
            }
            if(sortedMap.size() != 0) {
                topActivityPackageName =  sortedMap.get(sortedMap.lastKey()).getPackageName();
                Log.d(TAG,"Top activity package name = " + topActivityPackageName);
            }
        }

        return sb.toString();
    }

    public static Object getHideField(String fieldName, UsageStats usageStats){
        Object object = null;
        try {
            Field field = UsageStats.class.getField(fieldName);
            field.setAccessible(true);
            object = field.get(usageStats);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }
}
