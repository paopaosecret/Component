package com.xbing.app.component.ui.activity.layer2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.layer3.CustomViewActivity;
import com.xbing.app.component.ui.activity.layer3.ViewDispatchActivity;
import com.xbing.app.component.utils.ToastUtils;

public class TestViewActivity  extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);

        findViewById(R.id.btn_fenfa).setOnClickListener(this);
        findViewById(R.id.btn_custom).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_fenfa:
                gotoViewDispatchActivity();
                break;
            case R.id.btn_custom:
                gotoCustomViewActivity();
                break;
        }
    }

    private void gotoCustomViewActivity() {
        Intent intent = new Intent(this, CustomViewActivity.class);
        startActivity(intent);
    }

    private void gotoViewDispatchActivity() {
        Intent intent = new Intent(this, ViewDispatchActivity.class);
        startActivity(intent);
    }
}