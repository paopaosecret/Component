package com.xbing.app.component.ui.activity.layer2;

import android.os.Build;
import android.os.Bundle;

import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.BaseActivity;

import androidx.annotation.RequiresApi;

/**
 * Created by zhaobing04 on 2018/6/2.
 */

public class SettingActivity extends BaseActivity {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
}
