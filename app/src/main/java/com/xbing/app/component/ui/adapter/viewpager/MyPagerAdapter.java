package com.xbing.app.component.ui.adapter.viewpager;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

public class MyPagerAdapter extends PagerAdapter {
    private ArrayList<View> viewLists;
    private int count = 0;

    List<String> tilteList;
    public MyPagerAdapter() {
    }

    public MyPagerAdapter(ArrayList<View> viewLists, int count, List<String> tilteList) {
        super();
        this.viewLists = viewLists;
        this.count = count;
        this.tilteList = tilteList;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tilteList.get(position);
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewLists.get(position));
        return viewLists.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewLists.get(position));
    }
}