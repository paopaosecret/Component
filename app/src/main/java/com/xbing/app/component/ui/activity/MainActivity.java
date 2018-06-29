package com.xbing.app.component.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.api.InjectHelper;
import com.example.lib_annotation.BindView;
import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.layer2.SettingActivity;
import com.xbing.app.component.ui.fragment.Tab1Fragment;
import com.xbing.app.component.ui.fragment.Tab2Fragment;
import com.xbing.app.component.ui.fragment.Tab3Fragment;

import java.util.List;

public class MainActivity  extends BaseActivity implements View.OnTouchListener{

    private Tab1Fragment mTabFragment1;
    private Tab2Fragment mTabFragment2;
    private Tab3Fragment mTabFragment3;

    private int mCurrentIndexTab = 1;

    private android.support.v4.app.FragmentManager mFragmentManager;
    private FrameLayout mViewGroup;

    @BindView(R.id.rl_tab1)
    public View mRltab1;

    @BindView(R.id.rl_tab2)
    public View mRltab2;

    @BindView(R.id.rl_tab3)
    public View mRltab3;

    private ImageView mIVtab1,mIVtab2,mIVtab3;

    public TextView mTVtab1,mTVtab2,mTVtab3;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InjectHelper.inject(this);
        setTitle("MainActivity");
        setRightTitle("Setting");
        setLeftVisible(true);
        setLeftImage(R.mipmap.ic_launcher);
        mFragmentManager = getSupportFragmentManager();
        initViews();
        setTabSelection(mCurrentIndexTab);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 通过传入的index ,选中当前的tab项
     * @param index
     */
    private void setTabSelection(int index) {

        clearSelection();
        //开启一个Fragment事务
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        //隐藏所有的Fragment
        hideFragments(transaction);
        switch(index){
            case 1:
                mIVtab1.setImageResource(R.drawable.book2);
                mTVtab1.setTextColor(Color.parseColor("#00ccd4"));
                if (mTabFragment1 == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    mTabFragment1 = new Tab1Fragment();
                    transaction.add(R.id.fl_content, mTabFragment1);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(mTabFragment1);
                }
                break;
            case 2:
                mIVtab2.setImageResource(R.drawable.biji2);
                mTVtab2.setTextColor(Color.parseColor("#00ccd4"));
                if (mTabFragment2 == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    mTabFragment2 = new Tab2Fragment();
                    transaction.add(R.id.fl_content, mTabFragment2);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(mTabFragment2);
                }
                break;
            case 3:
                mIVtab3.setImageResource(R.drawable.tool2);
                mTVtab3.setTextColor(Color.parseColor("#00ccd4"));
                if (mTabFragment3 == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    mTabFragment3 = new Tab3Fragment();
                    transaction.add(R.id.fl_content, mTabFragment3);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(mTabFragment3);
                }
                break;
        }
        mCurrentIndexTab = index;
        transaction.commit();
    }

    private void clearSelection() {
        mTVtab1.setTextColor(Color.parseColor("#ffffff"));
        mTVtab2.setTextColor(Color.parseColor("#ffffff"));
        mTVtab3.setTextColor(Color.parseColor("#ffffff"));

        mIVtab1.setImageResource(R.drawable.book);
        mIVtab2.setImageResource(R.drawable.biji);
        mIVtab3.setImageResource(R.drawable.tool);
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     * 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {

        List<Fragment> fragmentList = mFragmentManager.getFragments();
        if(fragmentList != null){
            for(Fragment fragment : fragmentList){
                if(fragment !=null && fragment.isVisible()){
                    transaction.hide(fragment);
                }
            }
        }

    }

    /**
     *
     */
    private void initViews() {
        mIVtab1 = (ImageView)findViewById(R.id.iv_tab1);
        mIVtab2 = (ImageView)findViewById(R.id.iv_tab2);
        mIVtab3 = (ImageView)findViewById(R.id.iv_tab3);

        mTVtab1 = (TextView)findViewById(R.id.tv_tab1);
        mTVtab2 = (TextView)findViewById(R.id.tv_tab2);
        mTVtab3 = (TextView)findViewById(R.id.tv_tab3);

        mRltab1.setOnClickListener(this);
        mRltab2.setOnClickListener(this);
        mRltab3.setOnClickListener(this);

        mViewGroup = (FrameLayout) findViewById(R.id.fl_content);
        mViewGroup.setOnTouchListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_tab1:
                setTabSelection(1);
                break;
            case R.id.rl_tab2:
                setTabSelection(2);
                break;
            case R.id.rl_tab3:
                setTabSelection(3);
                break;
            case R.id.tv_right:
                gotoSettingActivity();
                break;
        }

    }

    private void gotoSettingActivity() {
        Intent intent = new Intent(MainActivity.this,SettingActivity.class);
        startActivity(intent);
    }


    int startX = 0;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        int endX = 0;
        switch(motionEvent.getAction()){

            case MotionEvent.ACTION_DOWN:
                startX = (int)motionEvent.getX();
                Log.i("onTouch","onTouch startX=" + startX );
            case MotionEvent.ACTION_UP:
                endX = (int) motionEvent.getX();
                Log.i("onTouch","onTouch endx=" + endX );
                int offset = endX - startX;
                if(offset>200){
                    mCurrentIndexTab--;
                    if(mCurrentIndexTab < 1){
                        mCurrentIndexTab = 3;
                    }
                    Log.i("onTouch","onTouch offset=" + offset + ",mCurrentTab=" +mCurrentIndexTab );
                    setTabSelection(mCurrentIndexTab);
                }else if(offset < -200){
                    mCurrentIndexTab++;
                    if(mCurrentIndexTab > 3){
                        mCurrentIndexTab = 1;
                    }
                    Log.i("onTouch","onTouch offset=" + offset + ",mCurrentTab=" +mCurrentIndexTab );
                    setTabSelection(mCurrentIndexTab);
                }

                break;
        }
        return true;
    }
}
