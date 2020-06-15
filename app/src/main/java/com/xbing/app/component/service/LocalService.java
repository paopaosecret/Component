package com.xbing.app.component.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

/**
 * Created by zhaobing04 on 2018/6/7.
 */

public class LocalService extends Service {

    /**
     * 当使用bindService（）启动服务，给客户端提供通信的Binder对象
     */
    private final IBinder mBinder = new LocalBinder();

    /**
     * 随机生成一个数返回给客户端
     */
    private final Random mGenerator = new Random();

    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }

    /**
     * 创建一个Binder类，持有当前Service对象
     */
    public class LocalBinder extends Binder {
        public LocalService getService() {
            return LocalService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TestLocalServiceActivi","onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("TestLocalServiceActivi","onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("TestLocalServiceActivi","onBind()");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("TestLocalServiceActivi","onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TestLocalServiceActivi","onDestroy()");
    }
}
