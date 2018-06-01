package com.xbing.app.component.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xbing.app.component.R;
import com.xbing.app.component.ui.customview.CustomDialog;


/**
 * Created by zhaobing on 2016/10/25.
 */

public class Tab1Fragment extends Fragment implements View.OnClickListener{

    private final static String TAG = Tab1Fragment.class.getSimpleName();

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
        }
    }
}