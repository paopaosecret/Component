package com.xbing.app.component.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xbing.app.component.R;
import com.xbing.app.component.bean.LiveRoomInfo;


/**
 * Created by bing.zhao on 2016/12/6.
 */

public class ColorFragment extends Fragment {
    private final static String TAG = ColorFragment.class.getSimpleName();

    private TextView mTextTitle;
    private LiveRoomInfo mRoomInfo;

    public ColorFragment(LiveRoomInfo roomInfo) {
        mRoomInfo = roomInfo;
    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG,TAG +"->onAttach" + mRoomInfo.roomId);
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG,TAG +"->onDestroy" + mRoomInfo.roomId);
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG,TAG +"->onDestroyView" + mRoomInfo.roomId);
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Log.i(TAG,TAG +"->onDetach" + mRoomInfo.roomId);
        super.onDetach();
    }

    @Override
    public void onPause() {
        Log.i(TAG,TAG +"->onPause" + mRoomInfo.roomId);
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.i(TAG,TAG +"->onResume" + mRoomInfo.roomId);
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.i(TAG,TAG +"->onStart" + mRoomInfo.roomId);
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.i(TAG,TAG +"->onStop" + mRoomInfo.roomId);
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.color_fragment,container,false);
        mTextTitle = contentView.findViewById(R.id.tv_title);
        mTextTitle.setText(mRoomInfo.roomId);
        mTextTitle.setBackgroundColor(mRoomInfo.backgroundColor);
        return contentView;
    }
}
