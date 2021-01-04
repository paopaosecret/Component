package com.xbing.app.component.utils.performance;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.xbing.app.basic.common.DateUtils;
import com.xbing.app.component.BuildConfig;

import java.lang.reflect.Field;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class StartCheckUtils {
    private static final String TAG = StartCheckUtils.class.getSimpleName();
    private final static String PACKAGE_NAME_UNKNOWN = "unknown";


    public static boolean checkAppUsagePermission(Context context) {
        getTaskInfos1(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            if(usageStatsManager == null) {
                return false;
            }
            long currentTime = System.currentTimeMillis();

            List<UsageStats> stats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, currentTime - 60 * 1000, currentTime);
            if (stats.size() <= 0) {
                return false;
            }
            return true;
        }else{
            return true;
        }
    }

    public static void requestAppUsagePermission(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.i(TAG,"Start usage access settings activity fail!");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
                sb.append(usageStats.getPackageName() + "\n");
                int appLaunchCount = (Integer) getHideField("mLaunchCount", usageStats);
                sb.append("启动次数：" + appLaunchCount + "\n");
                sb.append("前台显示时间：" + usageStats.getTotalTimeInForeground()/1000 + "\n");
                Log.d(TAG,"Top activity package name = " + sb.toString());
            }
            if(sortedMap.size() != 0) {
                topActivityPackageName =  sortedMap.get(sortedMap.lastKey()).getPackageName();
                Log.d(TAG,"Top activity package name = " + topActivityPackageName);
            }
        }

        return sb.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

    /**
     * 获取系统运行的进程信息
     *
     * @param context
     * @return
     */
    public static void getTaskInfos1(Context context) {
        PackageManager localPackageManager = context.getPackageManager();

        List localList = localPackageManager.getInstalledPackages(0);

        for (int i =0; i < localList.size(); i++) {

            PackageInfo localPackageInfo =(PackageInfo) localList.get(i);

            String packageStr = localPackageInfo.packageName.split(":")[0];
            Log.e("getTaskInfos1TAG1", packageStr);
            if (((ApplicationInfo.FLAG_SYSTEM & localPackageInfo.applicationInfo.flags) ==0)

                    &&((ApplicationInfo.FLAG_UPDATED_SYSTEM_APP & localPackageInfo.applicationInfo.flags) ==0)

                    &&((ApplicationInfo.FLAG_STOPPED & localPackageInfo.applicationInfo.flags) ==0)) {

                Log.e("getTaskInfos1TAG2", packageStr);
            }

        }
    }
}
