package com.xbing.app.component.ui.activity;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.xbing.app.component.R;

import java.lang.reflect.Method;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/9/8.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    /** 是否沉浸状态栏 **/
    private boolean isSetStatusBar = true;

    /** 是否允许全屏 **/
    private boolean mAllowFullScreen = true;

    /** 是否禁止旋转屏幕 **/
    private boolean isAllowScreenRoate = true;
    /** 是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值 **/
    boolean useThemestatusBarColor = true;
    /** 是否使用状态栏文字和图标为暗色，如果状态栏采用了白色系，则需要使状态栏和图标为暗色，android6.0以上可以设置 */
    boolean withoutUseStatusBarColor = false;

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
                setStatusBar();
            }
//            if (!isAllowScreenRoate) {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            } else {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            }
//
//            if(checkDeviceHasNavigationBar()){
//                setNavigationHeght();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 设置导航栏高度 **/
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setNavigationHeght() {
        // 透明导航栏
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationBarColor));    //设置导航栏颜色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);     //设置decorview 延伸到导航栏
            getWindow().getDecorView().findViewById(android.R.id.content).setPadding(0, 0, 0, getNavigationBarHeight());  //设置decorview边界底部在导航栏上方
        }
    }

    /**
     * [沉浸状态栏]
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));  //设置状态栏颜色
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
            rightTitle.setOnClickListener(this);
        }
    }

    public void setLeftVisible(Boolean flag){
        ImageView back = (ImageView) findViewById(R.id.iv_back);
        if(back != null){
            if(flag){
                back.setVisibility(View.VISIBLE);
            }else{
                back.setVisibility(View.INVISIBLE);
            }

        }
    }

    public void setLeftImage(int id){
        ImageView back = (ImageView) findViewById(R.id.iv_back);
        if(back != null){
            back.setImageResource(id);
        }
    }

    /**
     * 获取底部导航栏高度
     * @return
     */
    public int getNavigationBarHeight() {
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        int navigationHeight = getResources().getDimensionPixelSize(resourceId);
        return navigationHeight;
    }

    //判断是否存在NavigationBar
    public boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }
}
