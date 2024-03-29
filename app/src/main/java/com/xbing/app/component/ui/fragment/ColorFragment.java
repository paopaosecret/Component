package com.xbing.app.component.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

    //TODO 当 Fragment 与 Activity 关联时调用。
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG,TAG +"->onAttach" + mRoomInfo.roomId);
    }

    //TODO 当 Fragment 被创建时调用。
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,TAG +"->onCreate" + mRoomInfo.roomId);
    }

    //TODO 当 Fragment 需要创建视图时调用。
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG,TAG +"->onCreateView" + mRoomInfo.roomId);
        View contentView = inflater.inflate(R.layout.color_fragment,container,false);
        mTextTitle = contentView.findViewById(R.id.tv_title);
        mTextTitle.setText(mRoomInfo.roomId);
        mTextTitle.setBackgroundColor(mRoomInfo.backgroundColor);
        return contentView;
    }

    //TODO 当视图被创建后调用。
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG,TAG +"->onViewCreated" + mRoomInfo.roomId);
    }

    //TODO 当 Fragment 变为可见时调用。
    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,TAG +"->onStart" + mRoomInfo.roomId);
    }

    //TODO 当 Fragment 变为可交互时调用。
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,TAG +"->onResume" + mRoomInfo.roomId);
    }

    //TODO 当 Fragment 被暂停时调用。
    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,TAG +"->onPause" + mRoomInfo.roomId);
    }

    //TODO 当 Fragment 不再可见时调用。
    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,TAG +"->onStop" + mRoomInfo.roomId);
    }

    //TODO 当 Fragment 的视图被销毁时调用。
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG,TAG +"->onDestroyView" + mRoomInfo.roomId);
    }

    //TODO 当 Fragment 被销毁时调用。
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,TAG +"->onDestroy" + mRoomInfo.roomId);
    }

    //TODO  当 Fragment 与 Activity 分离时调用。
    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG,TAG +"->onDetach" + mRoomInfo.roomId);
    }


}
