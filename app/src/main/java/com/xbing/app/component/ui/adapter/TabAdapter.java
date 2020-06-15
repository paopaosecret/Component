package com.xbing.app.component.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xbing.app.component.R;
import com.xbing.app.component.utils.ToastUtils;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class TabAdapter  extends RecyclerView.Adapter<TabAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> mDatas;

    public TabAdapter(Context context, List<String> datas){
        mContext = context;
        mDatas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_coordinator_tab_item,parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvTitle.setText(mDatas.get(position));
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show("位置:" +position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_text);

        }
    }
}
