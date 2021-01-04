package com.xbing.app.component.flutter;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.xbing.app.component.ui.activity.BaseActivity;

public class FlutterActivity  extends BaseActivity {

    private EditText mEtUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        View FlutterView = Flutter.createView(this, getLifecycle(), "defaultRoute"); //传入路由标识符 setContentView(FlutterView);//用FlutterView替代Activity的ContentView

//        setContentView(FlutterView);
    }


}
