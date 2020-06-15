package com.xbing.app.component.ui.activity.layer2;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.api.InjectHelper;
import com.example.lib_annotation.BindView;
import com.xbing.app.basic.common.rxbus.RxBus;
import com.xbing.app.component.R;
import com.xbing.app.component.event.NewEvent;
import com.xbing.app.component.ui.activity.BaseActivity;

import androidx.annotation.RequiresApi;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class RxJavaActivity extends BaseActivity {

    @BindView(R.id.btn_create)
    public Button mBtnGet;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setOrientation(); //设置为横屏
        setContentView(R.layout.activity_rx_java);
        doSubscribe();
        InjectHelper.inject(this);
        findViewById(R.id.btn_create).setOnClickListener(this);
    }

    private void doSubscribe() {
        Subscription subscription = RxBus.getInstance()
                .tObservable(NewEvent.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewEvent>() {
                    @Override
                    public void call(NewEvent newEvent) {
                        switch (newEvent.getType()){
                            case "SEND":
                                Log.e("rxbus", "msg:" + newEvent.getMsg());
                                break;
                            default:
                                break;
                        }
                    }
                });

        RxBus.getInstance().addSubscription(this, subscription);
    }

    @Override
    protected void onDestroy() {
        RxBus.getInstance().unSubscribe(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.btn_create:
                RxBus.getInstance().post(new NewEvent("SEND", "this is message"));
                break;
        }
    }

    private void setOrientation() {
        //隐藏标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        if (getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

}
