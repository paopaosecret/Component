package com.xbing.app.component.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.layer2.AddressPickerActivity;
import com.xbing.app.component.ui.activity.layer2.CacheTestActivity;
import com.xbing.app.component.ui.activity.layer2.ClassLoderActivity;
import com.xbing.app.component.ui.activity.layer2.CoordinatorActivity;
import com.xbing.app.component.ui.activity.layer2.DragGridViewActivity;
import com.xbing.app.component.ui.activity.layer2.HybridActivity;
import com.xbing.app.component.ui.activity.layer2.RouterActivity;
import com.xbing.app.component.ui.activity.layer2.RxJavaActivity;
import com.xbing.app.component.ui.activity.layer2.TestViewActivity;
import com.xbing.app.component.utils.webview.WebviewProxy;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


/**
 * Created by zhaobing on 2016/10/25.
 */

public class Tab2Fragment extends Fragment implements View.OnClickListener{

    private final static String TAG = Tab2Fragment.class.getSimpleName();
    private FragmentManager mFragmentManager;

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
        mFragmentManager = getActivity().getSupportFragmentManager();
        View rootView = inflater.inflate(R.layout.tab2_fragment,container,false);

        rootView.findViewById(R.id.btn_loadfragment).setOnClickListener(this);
        rootView.findViewById(R.id.btn_handler).setOnClickListener(this);
        rootView.findViewById(R.id.btn_address_picker_view).setOnClickListener(this);
        rootView.findViewById(R.id.btn_cache).setOnClickListener(this);
        rootView.findViewById(R.id.btn_rx_java).setOnClickListener(this);
        rootView.findViewById(R.id.btn_initwebview).setOnClickListener(this);
        rootView.findViewById(R.id.btn_hybrid).setOnClickListener(this);
        rootView.findViewById(R.id.btn_draggridview).setOnClickListener(this);
        rootView.findViewById(R.id.btn_coordinatorview).setOnClickListener(this);
        rootView.findViewById(R.id.btn_test_view).setOnClickListener(this);
        rootView.findViewById(R.id.btn_test_classloader).setOnClickListener(this);
        rootView.findViewById(R.id.btn_test_router).setOnClickListener(this);
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
            case R.id.btn_loadfragment:
                int a = 100;
                AddFragment fragment = new AddFragment();
                Log.e("debugger TAG", "切换 AddFragment");
                mFragmentManager.beginTransaction()
                        .add(R.id.fl_content,fragment)
                        .hide(this)
                        .show(fragment)
                        .addToBackStack(null)
                        .commit();
                break;
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
                gotoRxJavaActivity();
                break;

            case R.id.btn_initwebview:
                initWebview();
                break;
            case R.id.btn_hybrid:
                gotoHybridActivity();
                break;
            case R.id.btn_draggridview:
                gotoDragGridViewActivity();
                break;

            case R.id.btn_coordinatorview:
                gotoCoordinatorActivity();
                break;
            case R.id.btn_test_view:
                gotoTestViewActivity();
                break;
            case R.id.btn_test_classloader:
                gotoClassLoaderActivity();
                break;
            case R.id.btn_test_router:
                gotoRouterActivity();
                break;
        }
    }

    private void gotoRouterActivity() {
        Intent intent = new Intent(this.getContext(), RouterActivity.class);
        startActivity(intent);
    }

    private void gotoClassLoaderActivity() {
        Intent intent = new Intent(this.getContext(), ClassLoderActivity.class);
        startActivity(intent);
    }

    private void gotoTestViewActivity() {
        Intent intent = new Intent(this.getContext(), TestViewActivity.class);
        startActivity(intent);
    }

    private void initWebview() {
        WebviewProxy.getInstatnce().load("https://hyapp.58.com/app/school/open/articles/tohome");
    }

    private void gotoHybridActivity() {
        Intent intent = new Intent(this.getContext(), HybridActivity.class);
        Log.e("WebviewTest","启动开始时间：" + System.currentTimeMillis());
        startActivity(intent);
    }

    private void gotoCoordinatorActivity(){
        Intent intent = new Intent(this.getContext(), CoordinatorActivity.class);
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

    private void gotoDragGridViewActivity() {
        Intent intent = new Intent(this.getContext(), DragGridViewActivity.class);
        startActivity(intent);
    }

    interface HandlerWhat{
        int HANDLER_WHAT_TEST = 100;
    }

}
