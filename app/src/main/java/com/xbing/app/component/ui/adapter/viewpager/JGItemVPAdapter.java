package com.xbing.app.component.ui.adapter.viewpager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.xbing.app.component.R;

import java.util.List;

/**
 * Created by zhaobing04 on 2018/4/3.
 */

public class JGItemVPAdapter extends BaseAdapter {
    private List<HeaderViewBean> mDatas;
    private LayoutInflater mLayoutInflater;


    public JGItemVPAdapter(Context context, List<HeaderViewBean> mDatas, int mIndex) {
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？mDatas.size() > (mIndex+1)*mPageSize,
     * 如果够，则直接返回每一页显示的最大条目个数mPageSize,
     * 如果不够，则有几项返回几,(mDatas.size() - mIndex * mPageSize);
     */
    @Override
    public int getCount() {
        return mDatas.size();

    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("TAG", "position:" + position);
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_gridview_header, parent, false);
            vh = new ViewHolder();
            vh.tv = (TextView) convertView.findViewById(R.id.textView);
            vh.iv = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + mIndex * mPageSize，
         */
        vh.tv.setText(mDatas.get(position).name);
        vh.iv.setImageResource(mDatas.get(position).iconRes);
        return convertView;
    }


    class ViewHolder {
        public TextView tv;
        public ImageView iv;
    }
}
