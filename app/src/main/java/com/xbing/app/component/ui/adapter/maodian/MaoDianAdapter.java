package com.xbing.app.component.ui.adapter.maodian;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xbing.app.component.R;
import com.xbing.app.component.ui.customview.maodian.AnchorView;

import androidx.recyclerview.widget.RecyclerView;

public class MaoDianAdapter extends RecyclerView.Adapter<MaoDianAdapter.MyViewHolder> {

    private Context context;
    private String[] tabTxt;
    private int lastH;

    public MaoDianAdapter(Context context, String[] tabTxt, int lastH) {
        this.context = context;
        this.tabTxt = tabTxt;
        this.lastH = lastH;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_view, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.anchorView.setContentTxt(tabTxt[position]);
        holder.anchorView.setAnchorTxt(tabTxt[position]);
        //判断最后一个view
        if (position == tabTxt.length - 1) {
            if (holder.anchorView.getHeight() < lastH) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.height = lastH;
                holder.anchorView.setLayoutParams(params);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private AnchorView anchorView;

        public MyViewHolder(View itemView) {
            super(itemView);
            anchorView = itemView.findViewById(R.id.anchorView);
        }
    }


}
