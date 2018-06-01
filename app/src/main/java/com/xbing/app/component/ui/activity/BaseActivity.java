package com.xbing.app.component.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.xbing.app.component.R;

/**
 * Created by Administrator on 2017/9/8.
 */

public class BaseActivity extends FragmentActivity implements View.OnClickListener {

    /** 是否沉浸状态栏 **/
    private boolean isSetStatusBar = true;
    /** 是否允许全屏 **/
    private boolean mAllowFullScreen = false;
    /** 是否禁止旋转屏幕 **/
    private boolean isAllowScreenRoate = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (mAllowFullScreen) {
                this.getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
            }

            if (isSetStatusBar) {
                steepStatusBar();
            }
            if (!isAllowScreenRoate) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public void onClick(View view) {

    }

    public void setTitle(String str){
        TextView title = (TextView) findViewById(R.id.tv_title);
        if(title != null){
            title.setText(str);
        }
    }

    public void setRightTitle(String str){
        TextView rightTitle = (TextView) findViewById(R.id.tv_right);
        if(rightTitle != null){
            rightTitle.setText(str);
        }
    }

    public void setBackVisible(Boolean flag){
        ImageView back = (ImageView) findViewById(R.id.iv_back);
        if(back != null){
            if(flag){
                back.setVisibility(View.VISIBLE);
            }else{
                back.setVisibility(View.INVISIBLE);
            }

        }
    }
}
