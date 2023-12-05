package com.xbing.app.component.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xbing.app.account.IAccountManager;
import com.xbing.app.account.iml.AccountManagerIml;
import com.xbing.app.account.iml.MovieService;
import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.layer2.ChronometerActivity;
import com.xbing.app.component.ui.activity.layer2.DialogActivity;
import com.xbing.app.component.ui.activity.layer2.ExceptionActivity;
import com.xbing.app.component.ui.activity.layer2.JavaJsInteractiveActivity;
import com.xbing.app.component.ui.activity.layer2.LivePage2Activity;
import com.xbing.app.component.ui.activity.layer2.LivePage3Activity;
import com.xbing.app.component.ui.activity.layer2.LivePageActivity;
import com.xbing.app.component.ui.activity.layer2.MaoDianActivity;
import com.xbing.app.component.ui.activity.layer2.ScreenActivity;
import com.xbing.app.component.ui.activity.layer2.TestLocalServiceActivity;
import com.xbing.app.component.ui.activity.layer2.ViewPageActivity;
import com.xbing.app.component.ui.activity.layer3.ViewDispatchActivity;
import com.xbing.app.component.ui.customview.CustomDialog;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * Created by zhaobing on 2016/10/25.
 */

public class Tab1Fragment extends Fragment implements View.OnClickListener{

    private final static String TAG = Tab1Fragment.class.getSimpleName();

    private IAccountManager mAccountManager;

    public static final String BASE_URL = "https://api.douban.com/v2/movie/";


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
        mAccountManager = new AccountManagerIml();
        View contentView = inflater.inflate(R.layout.tab1_fragment,container,false);

        contentView.findViewById(R.id.btn_showdialog).setOnClickListener(this::onClick);
        contentView.findViewById(R.id.btn_chronometer).setOnClickListener(this::onClick);

        contentView.findViewById(R.id.btn_maodian).setOnClickListener(this);
        contentView.findViewById(R.id.btn_go_to_exception).setOnClickListener(this);
        contentView.findViewById(R.id.btn_go_to_local_service).setOnClickListener(this);
        contentView.findViewById(R.id.btn_go_to_js).setOnClickListener(this);
        contentView.findViewById(R.id.btn_go_to_screen_test).setOnClickListener(this);
        contentView.findViewById(R.id.btn_go_to_screen_test).setOnClickListener(this);
        contentView.findViewById(R.id.btn_go_to_view_test).setOnClickListener(this);
        contentView.findViewById(R.id.btn_go_to_viewpage).setOnClickListener(this);
        contentView.findViewById(R.id.btn_go_to_live_page).setOnClickListener(this);
        contentView.findViewById(R.id.btn_go_to_live_page2).setOnClickListener(this);
        contentView.findViewById(R.id.btn_go_to_live_page3).setOnClickListener(this);
        return  contentView;

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_showdialog:
                gotoDialogActivity();
                break;
            case R.id.btn_chronometer:
                gotoChronometerActivity();
                break;


            case R.id.btn_maodian:
                goToMaoDianActivity();
                break;

            case R.id.btn_go_to_exception:
                gotoExceptionActivity();
                break;

            case R.id.btn_go_to_local_service:
                gotoLocalServiceActivity();
                break;

            case R.id.btn_go_to_js:
                gotoJsActivity();
                break;
            case R.id.btn_go_to_screen_test:
                gotoScreenActivity();
                break;

            case R.id.btn_go_to_view_test:
                gotoViewActivity();
                break;

            case R.id.btn_go_to_viewpage:
                gotoViewPageActivity();
                break;
            case R.id.btn_go_to_live_page:
                gotoLivePageActivity();
                break;
            case R.id.btn_go_to_live_page2:
                gotoLivePage2Activity();
                break;
            case R.id.btn_go_to_live_page3:
                gotoLivePage3Activity();
                break;
        }
    }

    private void gotoLivePage3Activity() {
        Intent intent = new Intent(getActivity(), LivePage3Activity.class);
        startActivity(intent);
    }

    private void gotoLivePage2Activity() {
        Intent intent = new Intent(getActivity(), LivePage2Activity.class);
        startActivity(intent);
    }

    private void gotoLivePageActivity() {
        Intent intent = new Intent(getActivity(), LivePageActivity.class);
        startActivity(intent);
    }

    private void gotoViewPageActivity() {
        Intent intent = new Intent(getActivity(), ViewPageActivity.class);
        startActivity(intent);
    }

    private void goToMaoDianActivity() {
        Intent intent = new Intent(getActivity(), MaoDianActivity.class);
        startActivity(intent);
    }

    private void gotoChronometerActivity() {
        Intent intent = new Intent(getActivity(), ChronometerActivity.class);
        startActivity(intent);
    }

    private void gotoDialogActivity() {
        Intent intent = new Intent(getActivity(), DialogActivity.class);
        startActivity(intent);
    }

    private void gotoViewActivity() {
        Intent intent = new Intent(getActivity(), ViewDispatchActivity.class);
        startActivity(intent);
    }

    private void gotoScreenActivity() {
        Intent intent = new Intent(getActivity(), ScreenActivity.class);
        startActivity(intent);
    }

    private void gotoJsActivity() {
        Intent intent = new Intent(getActivity(), JavaJsInteractiveActivity.class);
        startActivity(intent);
    }

    private void gotoExceptionActivity() {
        Intent intent = new Intent(getActivity(), ExceptionActivity.class);
        startActivity(intent);
    }

    private void gotoLocalServiceActivity() {
        Intent intent = new Intent(getActivity(), TestLocalServiceActivity.class);
        startActivity(intent);
    }

    private void getTopMovie(){
        //获取接口实例
//        MovieService movieService = retrofit.create(MovieService.class);
//        //调用方法得到一个Call
//        Call<MovieObject> call = movieService.getTop250(0,20);
//        //进行网络请求
//        call.enqueue(new Callback<MovieObject>() {
//            @Override
//            public void onResponse(Call<MovieObject> call, Response<MovieObject> response) {
//                Log.d(TAG,"response:" + response.body().toString());
//            }
//            @Override
//            public void onFailure(Call<MovieObject> call, Throwable t) {
//                Log.d(TAG,"response:" + t.toString());
//            }
//        });

    }
}
