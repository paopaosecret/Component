package com.xbing.app.component.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Choreographer;

import com.alibaba.fastjson.JSON;
import com.didichuxing.doraemonkit.kit.health.AppHealthInfoUtil;
import com.didichuxing.doraemonkit.kit.health.model.AppHealthInfo;
import com.didichuxing.doraemonkit.kit.performance.PerformanceDataManager;
import com.xbing.app.component.utils.performance.AppUtils;
import com.xbing.app.component.utils.performance.CpuInfoCheckUtils;
import com.xbing.app.component.utils.performance.MemoryChekcUtils;
import com.xbing.app.component.utils.performance.PhoneInfoutils;
import com.xbing.app.component.utils.performance.entity.DeviceInfo;
import com.xbing.app.component.utils.performance.entity.SysInfo;
import com.xbing.app.component.utils.performance.entity.UserInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AppInfoService  extends Service {
    private boolean isRunning;
    private Timer timer;
    private TimerTask task;
    final Handler handler = new ReportHandler();
    final int WHAT = 102;
    final int PERIOD = 5000;
    /**
     * fps 采集时间
     */
    private static final int FPS_SAMPLING_TIME = 1000;
    private static final int MAX_FRAME_RATE = 60;
    private FrameRateRunnable mRateRunnable = new FrameRateRunnable();

    /**
     * 当前的帧率
     */
    private int mLastFrameRate = MAX_FRAME_RATE;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("AppInfoService","onCreate()");

        isRunning = true;
        cereatTimer();
        startFrameInfo();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("AppInfoService","onStartCommand()");
        isRunning = true;
        Log.d("AppInfoService", "AppInfoService");
        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("AppInfoService","onBind()");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("AppInfoService","onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("AppInfoService","onDestroy()");
        stopFrameInfo();
        isRunning = false;
    }


    private void cereatTimer() {
        task = new ReportTimeTask();
        timer = new Timer();
        timer.schedule(task, 0, PERIOD);
    }

    private class ReportTimeTask extends TimerTask {
        @Override
        public void run() {
            setMessage();
        }
    }

    private class ReportHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT:
                    if(isRunning){
                        reportInfo();
                    }
                    break;
            }
        }
    }

    /**
     * 收集app使用信息
     */
    private void reportInfo() {
        DeviceInfo deviceInfo = new DeviceInfo();
        SysInfo sysInfo = new SysInfo();
        UserInfo userInfo = new UserInfo();

        userInfo.setNetWork(PhoneInfoutils.getSimeName(this));
        //设置电话号码
        //设置用户id

        deviceInfo.setPlatform(1);
        deviceInfo.setPhoneType(PhoneInfoutils.getSystemModel());
        deviceInfo.setOs(PhoneInfoutils.getSystemVersion());
        deviceInfo.setAppVersion(AppUtils.getVersionName(this));

        sysInfo.setActivity(PhoneInfoutils.getCurrentActivity(this));
        sysInfo.setCpuInfo(CpuInfoCheckUtils.getCpuRate(AppUtils.getPackageName(this)));
        sysInfo.setMemInfo(MemoryChekcUtils.getMemoryRate(this));
        sysInfo.setFps(mLastFrameRate + "");
        sysInfo.setTimeStamp(System.currentTimeMillis() + "");

        Map<String, Object> map = new HashMap<>();
        map.put("deviceInfo", deviceInfo);
        map.put("userInfo", userInfo);
        map.put("sysInfo", Arrays.asList(sysInfo));

        Log.d("AppInfoService", JSON.toJSONString(map));
    }

    private void setMessage() {
        Message message = new Message();
        message.what = WHAT;
        message.obj = System.currentTimeMillis();
        handler.sendMessage(message);
    }

    /**
     * 开启帧率检测
     */
    private void startFrameInfo() {
        handler.postDelayed(mRateRunnable, FPS_SAMPLING_TIME);
        Choreographer.getInstance().postFrameCallback(mRateRunnable);
    }

    /**
     * 关闭帧率检测
     */
    private void stopFrameInfo() {
        Choreographer.getInstance().removeFrameCallback(mRateRunnable);
        handler.removeCallbacks(mRateRunnable);
    }


    /**
     * 读取fps的线程
     */
    private class FrameRateRunnable implements Runnable, Choreographer.FrameCallback {
        private int totalFramesPerSecond;

        @Override
        public void run() {
            mLastFrameRate = totalFramesPerSecond;
            if (mLastFrameRate > MAX_FRAME_RATE) {
                mLastFrameRate = MAX_FRAME_RATE;
            }
            //保存fps数据
            totalFramesPerSecond = 0;
            //1s中统计一次
            handler.postDelayed(this, FPS_SAMPLING_TIME);
        }

        //
        @Override
        public void doFrame(long frameTimeNanos) {
            totalFramesPerSecond++;
            Choreographer.getInstance().postFrameCallback(this);
        }

    }
}
