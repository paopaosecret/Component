package com.xbing.app.component.ui.activity.layer2;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

import com.example.api.InjectHelper;
import com.example.lib_annotation.BindView;
import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.BaseActivity;


public class RxJavaActivity extends BaseActivity {

    @BindView(R.id.btn_create)
    public Button mBtnGet;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        InjectHelper.inject(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.btn_create:

                break;
        }
    }
}
