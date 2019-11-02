package com.xbing.app.component.ui.activity.layer2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xbing.app.component.R;
import com.xbing.app.component.service.LocalService;
import com.xbing.app.component.ui.activity.BaseActivity;

/**
 * Created by zhaobing04 on 2018/6/7.
 */
public class TestLocalServiceActivity extends BaseActivity {

    private static final String TAG = TestLocalServiceActivity.class.getSimpleName();

    private TextView mTvResult;
    private LocalService mService;
    private boolean mBound = false;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,IBinder service) {
            Log.e(TAG,"onServiceConnection");
            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.e(TAG,"onServiceDisconnected");
            mBound = false;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_service);
        Log.e(TAG,"onCreate");
        mTvResult = (TextView) findViewById(R.id.tv_result);
        findViewById(R.id.btn_getNumber).setOnClickListener(this);
        findViewById(R.id.btn_bindService).setOnClickListener(this);
        findViewById(R.id.btn_unbindService).setOnClickListener(this);
        findViewById(R.id.btn_startService).setOnClickListener(this);
        findViewById(R.id.btn_stopService).setOnClickListener(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }


    @Override
    public void onClick(View view) {
        Log.e(TAG,"onClick");
        switch (view.getId()){
            case R.id.btn_getNumber:
                getNumber();
                break;
            case R.id.btn_bindService:
                bindLocalService();
                break;
            case R.id.btn_unbindService:
                unBindLocalService();
                break;
            case R.id.btn_startService:
                startLocalService();
                break;
            case R.id.btn_stopService:
                stopLocalService();
                break;
        }
    }


    /**onCreate()、onStartCommand()  */
    private void startLocalService() {
        Intent intent = new Intent(this, LocalService.class);
        startService(intent);
    }

    /**onDestory() */
    private void stopLocalService() {
        Intent intent = new Intent(this, LocalService.class);
        stopService(intent);
    }



    /**onCreate()、onBind() */
    private void bindLocalService() {
        Intent intent = new Intent(this, LocalService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    /**onUnbind() onDestory() */
    private void unBindLocalService() {
        unbindService(mConnection);
    }


    public void getNumber() {
        if(mBound){
            int num = mService.getRandomNumber();
            mTvResult.setText("service return num:" + num);
        }
    }
}
