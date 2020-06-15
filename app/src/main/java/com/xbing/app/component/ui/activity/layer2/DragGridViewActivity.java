package com.xbing.app.component.ui.activity.layer2;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.BaseActivity;
import com.xbing.app.component.ui.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class DragGridViewActivity extends BaseActivity {
    private static volatile int index = 0;
    private RecyclerView mRecyView;
    private MyAdapter mAdapter;
    private ArrayList mDatas;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draggridview);

        mRecyView = (RecyclerView) findViewById(R.id.rv_root);
        mRecyView.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        mDatas = new ArrayList<>();
        for(int i= 0;i<9;i++){
            mDatas.add("加油" + (index++));
        }
        ItemTouchHelper mItemHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                Log.e("hsjkkk", "getMovementFlags()");
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Log.e("hsjkkk", "onMove()");
                //得到当拖拽的viewHolder的Position
                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mDatas, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mDatas, i, i - 1);
                    }
                }
                mAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                Toast.makeText(MainActivity.this, "拖拽完成 方向" + direction, Toast.LENGTH_SHORT).show();
                Log.e("hsjkkk", "拖拽完成 方向" + direction);

            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                Log.e("hsjkkk", "onSelectedChanged()");
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }

            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                Log.e("hsjkkk", "clearView()");
                viewHolder.itemView.setBackgroundColor(Color.rgb(240,240,240));

            }

            //重写拖拽不可用
            @Override
            public boolean isLongPressDragEnabled() {
                Log.e("hsjkkk", "isLongPressDragEnabled()");
                return true;
            }


        });
        mItemHelper.attachToRecyclerView(mRecyView);
        mAdapter = new MyAdapter(this, mDatas, mItemHelper);
        mRecyView.setAdapter(mAdapter);

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.add("加油" + (index++));
                mAdapter.notifyDataSetChanged();
            }
        });

        findViewById(R.id.btn_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.remove(0);
                mAdapter.notifyDataSetChanged();
            }
        });

    }

//
//    private ArrayList<DragIconInfo> initAllOriginalInfo() {
//        iconInfoList.add(new DragIconInfo(1, "第一单独", com.xbing.app.view.R.drawable.fangkezuji, DragIconInfo.CATEGORY_ONLY, new ArrayList<DargChildInfo>()));
//        iconInfoList.add(new DragIconInfo(2, "第二单独", com.xbing.app.view.R.drawable.fangkezuji, DragIconInfo.CATEGORY_ONLY, new ArrayList<DargChildInfo>()));
//        iconInfoList.add(new DragIconInfo(3, "第三单独", com.xbing.app.view.R.drawable.fangkezuji, DragIconInfo.CATEGORY_ONLY, new ArrayList<DargChildInfo>()));
//        iconInfoList.add(new DragIconInfo(4, "第一展开", com.xbing.app.view.R.drawable.fangkezuji, DragIconInfo.CATEGORY_ONLY, new ArrayList<DargChildInfo>()));
//        iconInfoList.add(new DragIconInfo(5, "第二展开", com.xbing.app.view.R.drawable.fangkezuji, DragIconInfo.CATEGORY_ONLY, new ArrayList<DargChildInfo>()));
//        iconInfoList.add(new DragIconInfo(6, "第三展开", com.xbing.app.view.R.drawable.fangkezuji, DragIconInfo.CATEGORY_ONLY, new ArrayList<DargChildInfo>()));
//        return iconInfoList;
//    }



}
