package com.xbing.app.component.ui.activity.layer2;

import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.BaseActivity;
import com.xbing.app.component.ui.customview.countDownView.CountDownView;

import androidx.annotation.RequiresApi;

/**
 * 测试倒计时View
 *
 * @作者 zhaobing04
 *
 * @创建日期 2020/7/31 17:21
 */
public class ChronometerActivity extends BaseActivity implements View.OnClickListener {
    private CountDownView mChronometer;

    private int initTime=120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer);
        mChronometer = findViewById(R.id.chronometer);
        mChronometer.initTime(initTime);
        mChronometer.setOnTimeCompleteListener(()->{
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "倒计时结束", Toast.LENGTH_LONG).show();
        });
        // Watch for button clicks.
        findViewById(R.id.reset).setOnClickListener(this);
        findViewById(R.id.pause).setOnClickListener(this);
        findViewById(R.id.resume).setOnClickListener(this);

        findViewById(R.id.stop).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reset:
                mChronometer.reStart();
                break;
            case R.id.pause:
                mChronometer.onPause();
                break;
            case R.id.resume:
                mChronometer.onResume();
                break;

            case R.id.stop:
                mChronometer.stop();
                mChronometer.initTime(initTime);
                break;
        }

    }
}
