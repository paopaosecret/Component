package com.xbing.app.component.ui.activity.layer2;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.xbing.app.component.R;
import com.xbing.app.component.bean.LiveRoomInfo;
import com.xbing.app.component.ui.adapter.live.viewpager.VerticalFragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 使用 ViewPager2 + FragmentStateAdapter 实现垂直滑动
 */
public class LivePage3Activity extends FragmentActivity {
    private static final String TAG = "LivePage3Activity";

    private   ViewPager2         mViewPager;
    protected List<LiveRoomInfo> mVideos = new ArrayList<>();
    protected VerticalFragmentStateAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_pager3);

        initView();
    }

    private void initView() {
        mViewPager = findViewById(R.id.view_pager2);
        mAdapter = new VerticalFragmentStateAdapter(LivePage3Activity.this.getSupportFragmentManager(),
                LivePage3Activity.this.getLifecycle());
        mViewPager.setAdapter(mAdapter);

        mViewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                // 检查滚动比例是否大于等于 0.5
                if (positionOffset >= 0.5f) {
                    Log.i(TAG, "Scrolled to position = " + position + ", position = " + positionOffsetPixels +
                            " Fragment more than half");
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

}
