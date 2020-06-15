package com.xbing.app.component.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xbing.app.component.R;

import java.util.List;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> mDatas;
    private ItemTouchHelper mItemHelper;

    public MyAdapter(Context mContext, List<String> mDatas, ItemTouchHelper itemHelper) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mItemHelper = itemHelper;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.draggridview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvTitle.setText(mDatas.get(position));
        holder.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (position > 30) {
                    mItemHelper.startDrag(holder);
                }

                return false;
            }
        });

        holder.ivRemove.setTag(mDatas.get(position));
        holder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = (String)v.getTag();
                mDatas.remove(str);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private TextView tvTitle;
        private ImageView ivRemove;
        private ImageView ivIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_text);
            ivRemove = (ImageView) itemView.findViewById(R.id.iv_operator);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_logo);
        }
    }
}
