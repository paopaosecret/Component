package com.xbing.app.component.ui.activity.layer2;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.xbing.app.component.R;
import com.xbing.app.component.bean.LiveRoomInfo;
import com.xbing.app.component.ui.adapter.live.recycleview.OnPagerListener;
import com.xbing.app.component.ui.adapter.live.recycleview.PagerLayoutManager;
import com.xbing.app.component.ui.adapter.live.recycleview.RoomRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class LivePageActivity extends AppCompatActivity {

    protected List<LiveRoomInfo>      mVideos = new ArrayList<>();
    protected RoomRecyclerViewAdapter mAdapter;
    protected RecyclerView            mRecyclerView;
    protected PagerLayoutManager      mLinearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_pager);

        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rv_recycle_view);
        mLinearLayoutManager= new PagerLayoutManager(this, OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new RoomRecyclerViewAdapter(mVideos);

        mRecyclerView.setAdapter(mAdapter);
        mLinearLayoutManager.setOnViewPagerListener(new OnPagerListener() {
            @Override
            public void onInitComplete() {
                System.out.println("OnPagerListener---onInitComplete--"+"初始化完成");
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                System.out.println("OnPagerListener---onPageRelease--"+position+"-----"+isNext);
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                System.out.println("OnPagerListener---onPageSelected--"+position+"-----"+isBottom);

            }
        });

        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {

            }
        });
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
