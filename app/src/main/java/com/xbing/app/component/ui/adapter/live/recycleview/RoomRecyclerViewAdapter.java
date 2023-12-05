package com.xbing.app.component.ui.adapter.live.recycleview;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xbing.app.component.R;
import com.xbing.app.component.bean.LiveRoomInfo;

import java.util.List;

public class RoomRecyclerViewAdapter extends RecyclerView.Adapter<RoomRecyclerViewAdapter.ViewHolder> {

    private List<LiveRoomInfo> videos;

    public RoomRecyclerViewAdapter(List<LiveRoomInfo> videos) {
        this.videos = videos;
    }

    @Override
    @NonNull
    public RoomRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_live, parent, false);
        return new RoomRecyclerViewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomRecyclerViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        LiveRoomInfo videoBean = videos.get(position);
        holder.mTextTitle.setText(videoBean.roomId);
        holder.mImageBackground.setBackgroundColor(videoBean.backgroundColor);
        holder.mPosition = position;
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void addData(List<LiveRoomInfo> videoList) {
        int size = videos.size();
        videos.addAll(videoList);
        //使用此方法添加数据，使用notifyDataSetChanged会导致正在播放的视频中断
        notifyItemRangeChanged(size, videos.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public int mPosition;
        public TextView mTextTitle;
        public ImageView mImageBackground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextTitle = itemView.findViewById(R.id.tv_title);
            mImageBackground = itemView.findViewById(R.id.iv_background);
        }
    }
}