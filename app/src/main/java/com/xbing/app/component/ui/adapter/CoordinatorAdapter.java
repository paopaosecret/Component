package com.xbing.app.component.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xbing.app.component.R;

import java.util.ArrayList;
import java.util.List;

public class CoordinatorAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_TEXT = 1;
    private static final int VIEW_TYPE_TAB = 2;

    private Context mContext;
    private List<String> mDatas;
    private List<String> mTabDatas = new ArrayList<>();

    public CoordinatorAdapter(Context context, List<String> datas){
        mContext = context;
        mDatas = datas;
        for(int i= 0;i<10;i++){
            mTabDatas.add("TAB"+ i);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View commonView = LayoutInflater.from(mContext).inflate(R.layout.draggridview_item, parent, false);
        View recycleView = LayoutInflater.from(mContext).inflate(R.layout.adapter_item_recycleview, parent, false);
        if(viewType == VIEW_TYPE_TAB){
            return new RecycleHolder(recycleView);
        }else{
            return new CommonHolder(commonView);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  CommonHolder){
            ((CommonHolder)holder).tvTitle.setText(mDatas.get(position));
        }else if(holder instanceof RecycleHolder){
            ((RecycleHolder)holder).rvTab.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            TabAdapter tabAdapter = new TabAdapter(mContext, mTabDatas);
            ((RecycleHolder)holder).rvTab.setAdapter(tabAdapter);
            ((RecycleHolder)holder).rvTab.setTag("TabRecycleView");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 13){
            return VIEW_TYPE_TAB;
        }else{
            return VIEW_TYPE_TEXT;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class CommonHolder extends RecyclerView.ViewHolder {
        private View mView;
        private TextView tvTitle;
        private ImageView ivRemove;
        private ImageView ivIcon;

        public CommonHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_text);
            ivRemove = (ImageView) itemView.findViewById(R.id.iv_operator);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_logo);
        }
    }

    class RecycleHolder extends RecyclerView.ViewHolder{
        private RecyclerView rvTab;

        public RecycleHolder(View itemView) {
            super(itemView);
            rvTab = (RecyclerView) itemView.findViewById(R.id.rv_tab);
        }
    }
}
