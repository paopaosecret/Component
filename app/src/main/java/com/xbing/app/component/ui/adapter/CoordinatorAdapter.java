package com.xbing.app.component.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.layer2.CoordinatorActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CoordinatorAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_TEXT = 1;
    private static final int VIEW_TYPE_TAB = 2;

    private CoordinatorActivity mContext;
    private List<String> mDatas;
    private List<String> mTabDatas = new ArrayList<>();
    private RecyclerView tabChild;

    public CoordinatorAdapter(CoordinatorActivity context, List<String> datas, RecyclerView rvTabChild){
        mContext = context;
        mDatas = datas;
        this.tabChild = rvTabChild;
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof  CommonHolder){
            ((CommonHolder)holder).tvTitle.setText(mDatas.get(position));
            ((CommonHolder)holder).ivIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position > 13 && tabChild.getVisibility() != View.VISIBLE){
                        moveToPosition(mContext.recyclerview, 13);
                    }
                }
            });
        }else if(holder instanceof RecycleHolder){
            ((RecycleHolder)holder).rvTab.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            TabAdapter tabAdapter = new TabAdapter(mContext, mTabDatas);
            ((RecycleHolder)holder).rvTab.setAdapter(tabAdapter);
            ((RecycleHolder)holder).rvTab.setTag("TabRecycleView");
            ((RecycleHolder)holder).rvTab.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    Log.e("TabBehavior", "child onScrolled: dx = " + dx + ", dy = " + dy);
                    if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE){
                        tabChild.scrollBy(dx, dy);
                    }
                }
            });
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

    /**
     * 缓慢滑动
     *
     *   当指定位置位于第一个可见位置之上时，可以滚动，利用smoothScrollToPosition实现
     *   当指定位置位于可视位置之间时，得到距离顶部的距离，然后smoothScrollBy向上滚动固定的距离
     *   当指定的位置位于最后一个可见位置之下时，可以滚动，利用利用smoothScrollToPosition实现实现
     */
    public void moveToPosition(RecyclerView mRecyclerView, int position) {
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem||position>lastItem) {
            mRecyclerView.smoothScrollToPosition(position);
        } else {
            int movePosition = position - firstItem;
            int top = mRecyclerView.getChildAt(movePosition).getTop();
            mRecyclerView.smoothScrollBy(0, top);
        }
    }
}
