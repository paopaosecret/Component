package com.xbing.app.component.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xbing.app.account.IAccountManager;
import com.xbing.app.account.iml.AccountManagerIml;
import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.layer2.ExceptionActivity;
import com.xbing.app.component.ui.activity.layer2.JavaJsInteractiveActivity;
import com.xbing.app.component.ui.activity.layer2.ScreenActivity;
import com.xbing.app.component.ui.activity.layer2.TestLocalServiceActivity;
import com.xbing.app.component.ui.customview.CustomDialog;


/**
 * Created by zhaobing on 2016/10/25.
 */

public class Tab1Fragment extends Fragment implements View.OnClickListener{

    private final static String TAG = Tab1Fragment.class.getSimpleName();

    private FragmentManager mFragmentManager;

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

        mFragmentManager = getActivity().getSupportFragmentManager();
        mAccountManager = new AccountManagerIml();
        View contentView = inflater.inflate(R.layout.tab1_fragment,container,false);
        contentView.findViewById(R.id.btn_showdialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CustomDialog customDialog = new CustomDialog(view.getContext());
                customDialog.showDialog("title", "are you speak english?", "ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });
            }
        });

        contentView.findViewById(R.id.btn_showdialog2).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  final CustomDialog customDialog = new CustomDialog(v.getContext());
                  customDialog.showDialog("title", "are you speak english are you speak english are you speak englishare you speak english are you speak english are you speak english are you speak english are you speak english are you speak english",
                          "ok ok ok ok ok ok", new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          customDialog.dismiss();
                      }
                  }, "cancel cancel cancel cancel cancel", new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          customDialog.dismiss();
                      }
                  });
              }
          });

        contentView.findViewById(R.id.btn_showdialog3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomDialog customDialog = new CustomDialog(v.getContext());
                customDialog.showDialog("are you speak english are you speak english are you speak englishare you speak english are you speak english are you speak english are you speak english are you speak english are you speak english",
                        "ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        }, "cancel", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                customDialog.dismiss();
                            }
                        });
            }
        });


        contentView.findViewById(R.id.btn_loadfragment).setOnClickListener(this);
        contentView.findViewById(R.id.btn_test_http).setOnClickListener(this);
        contentView.findViewById(R.id.btn_go_to_exception).setOnClickListener(this);
        contentView.findViewById(R.id.btn_go_to_local_service).setOnClickListener(this);
        contentView.findViewById(R.id.btn_go_to_js).setOnClickListener(this);
        contentView.findViewById(R.id.btn_go_to_screen_test).setOnClickListener(this);
        return  contentView;

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_loadfragment:
                AddFragment fragment = new AddFragment();
                mFragmentManager.beginTransaction()
                        .add(R.id.fl_content,fragment)
                        .hide(this)
                        .show(fragment)
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.btn_test_http:
//                mAccountManager.getUsers(new RequestCallback() {
//                    @Override
//                    public void onRequestComplete(RequestResult result) {
//                        if(result != null){
//                            Log.d("http",result.toString()) ;
//                        }
//
//                    }
//                });
                getTopMovie();
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
        }
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
//        MovieService  movieService = retrofit.create(MovieService.class);
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
