package com.xbing.app.component.ui.activity.layer2;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.google.android.material.tabs.TabLayout;
import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.BaseActivity;
import com.xbing.app.component.ui.adapter.viewpager.JGItemVPAdapter;
import com.xbing.app.component.ui.adapter.viewpager.HeaderViewBean;
import com.xbing.app.component.ui.adapter.viewpager.MyPagerAdapter;
import com.xbing.app.component.ui.customview.IndicatorView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

public class ViewPageActivity extends BaseActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    IndicatorView indicatorView;

    List<String> tabDataList = Arrays.asList(new String[]{"常用", "推广", "经营", "工具", "店铺", "帮助"});
    List<HeaderViewBean> mDatas = null;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        indicatorView = findViewById(R.id.indicator_z);

        adapterView();
    }

    private void adapterView() {
        LayoutInflater li = getLayoutInflater();
        ArrayList<View> aList = new ArrayList<View>();


        for (int i = 0; i < tabDataList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabDataList.get(i)));

            View  view1 = li.inflate(R.layout.viewpager_one,null,false);
            GridView gridView1 = (GridView) view1.findViewById(R.id.gridview1);
            initDatas(8);
            JGItemVPAdapter adapter1 = new JGItemVPAdapter(this, mDatas,0);
            gridView1.setAdapter(adapter1);
            aList.add(view1);
        }

        MyPagerAdapter mAdapter = new MyPagerAdapter(aList, tabDataList.size(), tabDataList);
        viewPager.setAdapter(mAdapter);

        tabLayout.setupWithViewPager(viewPager);

        indicatorView.setMarginLeft(5);
        indicatorView.initIndicator(tabDataList.size(), R.drawable.bg_circle_red, R.drawable.bg_circle_gary);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int oldPagerPos = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicatorView.playByStartPointToNext(oldPagerPos, position);
                oldPagerPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(0);
        indicatorView.playByStartPointToNext(1,0);
    }
    public void initDatas(int size){
        mDatas = new ArrayList<HeaderViewBean>();
        for(int i = 0; i< size; i++){
            mDatas.add(new HeaderViewBean("帅哥" + i, R.mipmap.ic_launcher));
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

    }
}
