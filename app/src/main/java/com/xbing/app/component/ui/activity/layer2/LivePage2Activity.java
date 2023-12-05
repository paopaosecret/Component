package com.xbing.app.component.ui.activity.layer2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.xbing.app.component.R;
import com.xbing.app.component.bean.LiveRoomInfo;
import com.xbing.app.component.ui.adapter.live.viewpager.VerticalPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class LivePage2Activity extends AppCompatActivity {
    private   ViewPager2         mViewPager;
    protected List<LiveRoomInfo> mVideos = new ArrayList<>();
    protected VerticalPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_pager2);

        initView();
        initData();
    }

    private void initView() {
        mViewPager = findViewById(R.id.view_pager2);
        mAdapter = new VerticalPagerAdapter(mVideos);
        mViewPager.setAdapter(mAdapter);

        mViewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
    }

    private void initData() {
        List<LiveRoomInfo> roomList = new ArrayList<>();
        roomList.add(new LiveRoomInfo("1", 0xFFFF0000));
        roomList.add(new LiveRoomInfo("2", 0xFF00FF00));
        roomList.add(new LiveRoomInfo("3", 0xFF0000FF));
        roomList.add(new LiveRoomInfo("4", 0xFFFFFF00));
        roomList.add(new LiveRoomInfo("5", 0xFFFF00FF));
        roomList.add(new LiveRoomInfo("6", 0xFF00FFFF));
        mVideos.addAll(roomList);
        mAdapter.notifyDataSetChanged();
    }
}
