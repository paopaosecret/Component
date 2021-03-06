package com.xbing.app.component.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xbing.app.component.R;
import com.xbing.app.component.jetpack.JetpackActivity;
import com.xbing.app.component.ui.activity.layer2.ToolsActivity;
import com.xbing.app.component.ui.activity.layer2.TrtcActivity;
import com.xbing.app.component.ui.zxing.qrcode.QrcodeActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * Created by zhaobing on 2016/10/25.
 */

public class Tab3Fragment extends Fragment {

    private final static String TAG = Tab3Fragment.class.getSimpleName();

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
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG,TAG +"->onCreateView");
        View rootView = inflater.inflate(R.layout.tab3_fragment,container,false);

        Button btn = (Button) rootView.findViewById(R.id.btn_call);
        Button jetpack = rootView.findViewById(R.id.btn_jetpack);
        Button tools = rootView.findViewById(R.id.btn_tools);
        Button trtc = rootView.findViewById(R.id.btn_trtc);
        textView = (TextView) rootView.findViewById(R.id.tv_result);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("TAB3");
            }
        });
        jetpack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), JetpackActivity.class);
                startActivity(intent);
            }
        });

        rootView.findViewById(R.id.btn_qrcode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QrcodeActivity.class);
                startActivity(intent);
            }
        });

        tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ToolsActivity.class);
                startActivity(intent);
            }
        });

        trtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TrtcActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

}
