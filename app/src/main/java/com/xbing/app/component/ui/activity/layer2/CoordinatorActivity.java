package com.xbing.app.component.ui.activity.layer2;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.api.InjectHelper;
import com.example.lib_annotation.BindView;
import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.BaseActivity;
import com.xbing.app.component.ui.adapter.CoordinatorAdapter;
import com.xbing.app.component.ui.adapter.TabAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 协调者布局测试类
 *
 *
 */
public class CoordinatorActivity extends BaseActivity{

    @BindView(R.id.rv_tab)
    RecyclerView rvTab;

    @BindView(R.id.rv_list)
    public RecyclerView recyclerview;

    @BindView(R.id.btn_open)
    Button openBT;

    @BindView(R.id.cl_layout)
    CoordinatorLayout clLayout;

    @BindView(R.id.tv_test)
    TextView tvTest;

    private List<String> mDatas = new ArrayList<>();
    private List<String> mTabDatas = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        InjectHelper.inject(this);
        for(int i= 0;i<50;i++){
            if(i<10){
                mTabDatas.add("TAB"+ i);
            }
            mDatas.add("加油" + i);
        }
        initViews();
    }


    protected void initViews() {
        clLayout.setTag("CoordinatorLayout");
        tvTest.setTag("TextView");

        rvTab.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        TabAdapter tabAdapter = new TabAdapter(this, mTabDatas);
        rvTab.setAdapter(tabAdapter);
        rvTab.setTag("TabRecycleView");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(linearLayoutManager);

        CoordinatorAdapter myDemoAdapter = new CoordinatorAdapter(this, mDatas, rvTab);
        recyclerview.setAdapter(myDemoAdapter);
        recyclerview.setTag("ListRecycleView");

        openBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked();
            }
        });
    }


    public void onViewClicked() {

    }
}
