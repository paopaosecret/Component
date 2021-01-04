package com.xbing.app.component.ui.activity.layer2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xbing.app.component.R;
import com.xbing.app.component.service.AppInfoService;
import com.xbing.app.component.ui.activity.BaseActivity;
import com.xbing.app.component.utils.ClipboardUtils;
import com.xbing.app.component.utils.performance.AppUtils;
import com.xbing.app.component.utils.performance.StartCheckUtils;
import com.xbing.app.component.utils.performance.CpuInfoCheckUtils;
import com.xbing.app.component.utils.performance.FrameCheckUtils;
import com.xbing.app.component.utils.performance.MemoryChekcUtils;
import com.xbing.app.component.utils.performance.NetworkCheckUtils;

import androidx.annotation.RequiresApi;

public class ToolsActivity extends BaseActivity{

    public int lastBattery = -1;

    TextView tvResult;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        tvResult = findViewById(R.id.tv_result);
        findViewById(R.id.btn_cpu).setOnClickListener(this);
        findViewById(R.id.btn_memory).setOnClickListener(this);
        findViewById(R.id.btn_network).setOnClickListener(this);
        findViewById(R.id.btn_battery).setOnClickListener(this);
        findViewById(R.id.btn_framerate).setOnClickListener(this);
        findViewById(R.id.btn_service).setOnClickListener(this);
        findViewById(R.id.btn_process_name).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cpu:
                tvResult.setText(CpuInfoCheckUtils.getProcessCpuRate(getPackageName()));
                break;
            case R.id.btn_memory:
                tvResult.setText(MemoryChekcUtils.getMemoryInfo(view.getContext()));
                break;
            case R.id.btn_network:
                NetworkCheckUtils.getNetworkInfo(tvResult);
                break;
            case R.id.btn_battery:
//                tvResult.setText("电池功能未上线");
                tvResult.setText("");
//                registerReceiver(mBatInfoReveiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                if (StartCheckUtils.checkAppUsagePermission(view.getContext())) {
                    StringBuffer sb = new StringBuffer(tvResult.getText());
                    sb.append("\n" + StartCheckUtils.getUsageInfo(view.getContext()));
                    tvResult.setText(sb.toString());
                } else {
                    StartCheckUtils.requestAppUsagePermission(view.getContext());
                }

                break;
            case R.id.btn_framerate:
                Log.d("ToolsActivity", ClipboardUtils.getCopy(this));
                tvResult.setText(FrameCheckUtils.getVsyncRate(ToolsActivity.this));
                break;

            case R.id.btn_service:
                ClipboardUtils.copy(this, "abcdefghijklmnopqrstuvwxyz");
                startService();
                break;

            case R.id.btn_process_name:
                tvResult.setText(AppUtils.getProcessName());
                break;
        }
    }


    private void startService() {
        // OPPO手机自动熄屏一段时间后，会启用系统自带的电量优化管理，禁止一切自启动的APP（用户设置的自启动白名单除外），需要try catch
        try {
            Intent getServiceTimeIntent = new Intent(this, AppInfoService.class);
            startService(getServiceTimeIntent);
        } catch (Exception e) {

        }
    }

    private BroadcastReceiver mBatInfoReveiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //如果捕捉到的Action是ACTION_BATTERY_CHANGED则运行onBatteryInforECEIVER()
            if(Intent.ACTION_BATTERY_CHANGED.equals(action))
            {
                //获得当前电量
                int intLevel = intent.getIntExtra("level",0);
                if(lastBattery != intLevel){
                    //获得手机总电量
                    int intScale = intent.getIntExtra("scale",100);
                    // 在下面会定义这个函数，显示手机当前电量
                    StringBuffer stringBuffer = new StringBuffer(tvResult.getText());
                    stringBuffer.append("\n" +"当前电量百分比：" + intLevel * 100/intScale);
                    tvResult.setText(stringBuffer.toString());
                    lastBattery = intLevel;
                }

            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        try {
            unregisterReceiver(mBatInfoReveiver);
        }catch (Exception e){}

    }
}
