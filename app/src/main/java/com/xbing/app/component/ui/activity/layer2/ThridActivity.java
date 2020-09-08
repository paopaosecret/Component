package com.xbing.app.component.ui.activity.layer2;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.common.StringUtils;
import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.BaseActivity;

import androidx.annotation.RequiresApi;

public class ThridActivity   extends BaseActivity {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thrid);

        findViewById(R.id.btn_jump_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpWechat();
            }
        });
    }

    private void jumpWechat() {
        try {
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
            startActivity(intent);
        } catch (Exception e) { }
        finish();
    }
}

