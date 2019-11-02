package com.xbing.app.component.ui.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.layer2.AddressPickerActivity;
import com.xbing.app.component.ui.activity.layer2.CacheTestActivity;
import com.xbing.app.component.ui.activity.layer2.HybridActivity;
import com.xbing.app.component.ui.activity.layer2.RxJavaActivity;
import com.xbing.app.net.common.cache.memcache.WebResourceCacheManager;


/**
 * Created by zhaobing on 2016/10/25.
 */

public class Tab2Fragment extends Fragment implements View.OnClickListener{

    private final static String TAG = Tab2Fragment.class.getSimpleName();

    @Override
    public void onAttach(Context context) {
        Log.i(TAG,TAG +"->onAttach");
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG,TAG +"->onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG,TAG +"->onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Log.i(TAG,TAG +"->onDetach");
        super.onDetach();
    }

    @Override
    public void onPause() {
        Log.i(TAG,TAG +"->onPause");
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.i(TAG,TAG +"->onResume");
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.i(TAG,TAG +"->onStart");
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.i(TAG,TAG +"->onStop");
        super.onStop();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG,TAG +"->onCreateView");
        View rootView = inflater.inflate(R.layout.tab2_fragment,container,false);
        rootView.findViewById(R.id.btn_handler).setOnClickListener(this);
        rootView.findViewById(R.id.btn_address_picker_view).setOnClickListener(this);
        rootView.findViewById(R.id.btn_cache).setOnClickListener(this);
        rootView.findViewById(R.id.btn_rx_java).setOnClickListener(this);
        rootView.findViewById(R.id.btn_hybrid).setOnClickListener(this);
        return rootView;
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == HandlerWhat.HANDLER_WHAT_TEST){
                Toast.makeText(getActivity(),"Handler callback",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_handler:
                mHandler.sendEmptyMessage(HandlerWhat.HANDLER_WHAT_TEST);
                break;
            case R.id.btn_address_picker_view:
                gotoAddressPickerActivity();
                break;
            case R.id.btn_cache:
                gotoCacheActivity();
                break;
            case R.id.btn_rx_java:
//                gotoRxJavaActivity();
                WebResourceCacheManager.getInstance().init();
                break;
            case R.id.btn_hybrid:
                gotoHybridActivity();
                break;
        }
    }

    private void gotoHybridActivity() {
        Intent intent = new Intent(this.getContext(), HybridActivity.class);
        Log.e("WebviewTest","启动开始时间：" + System.currentTimeMillis());
        startActivity(intent);
    }

    private void gotoRxJavaActivity() {
        Intent intent = new Intent(this.getContext(), RxJavaActivity.class);
        startActivity(intent);
    }

    private void gotoCacheActivity() {
        Intent intent = new Intent(this.getContext(), CacheTestActivity.class);
        startActivity(intent);
    }

    private void gotoAddressPickerActivity() {
        Intent intent = new Intent(this.getContext(), AddressPickerActivity.class);
        startActivity(intent);
    }

    interface HandlerWhat{
        int HANDLER_WHAT_TEST = 100;
    }

}
