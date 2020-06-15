package com.xbing.app.component.ui.activity.layer2;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.api.InjectHelper;
import com.example.lib_annotation.BindView;
import com.xbing.app.component.R;
import com.xbing.app.component.utils.DisplayUtils;

import java.lang.reflect.Method;

import androidx.annotation.Nullable;

public class ScreenActivity extends Activity {

    @BindView(R.id.tv_screen_show)
    public TextView tvShow;

    @BindView(R.id.btn_test)
    public Button btnTest;

    @BindView(R.id.btn_test1)
    public Button btnTest1;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;

        // 获得当前窗体对象
        Window window = ScreenActivity.this.getWindow();
        // 设置当前窗体为全屏显示
        window.setFlags(flag, flag);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        InjectHelper.inject(this);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                int[] location1= new int[2];
                tvShow.getLocationOnScreen(location);
                tvShow.getLocationInWindow(location1);

                Rect rect = new Rect();
                tvShow.getGlobalVisibleRect(rect);
                StringBuffer sb = new StringBuffer("");
                sb.append("onScreen x:" + location[0] + ",tvShow y:" + location[1] + "\n");
                sb.append("onWindow x:" + location1[0] + ",tvShow y:" + location1[1] + "\n");
                sb.append("rect  left:" + rect.left + ",rect.top:" + rect.top + "\n");
                sb.append(DisplayUtils.testSize(ScreenActivity.this));
                tvShow.setText(sb.toString());
            }
        });

        btnTest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayUtils.qieHuan(ScreenActivity.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initScreenSize();
    }

    private void initScreenSize() {
        StringBuffer sb = new StringBuffer("");
        //获取屏幕区域的宽高等尺寸获取
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        sb.append("获取屏幕区域的宽高等尺寸获取:");
        sb.append("\n");

        sb.append("screenWidth = " + screenWidth);
        sb.append("\n");
        sb.append("screenHeight = " + screenHeight);
        sb.append("\n");
        sb.append("\n");

        View view = getWindow().getDecorView().findViewById(android.R.id.content);
        int statusBarHeight = view.getTop();
        sb.append("状态栏的高度:" + statusBarHeight);
        sb.append("\n");
        sb.append("\n");

        //View布局区域宽高等尺寸获取
        View rectContent = getWindow().getDecorView().findViewById(android.R.id.content);
        int contentWidth = rectContent.getWidth();
        int contentHeight = rectContent.getHeight();
        sb.append("Content 的宽高:");
        sb.append("\n");
        sb.append("contentWidth:" + contentWidth);
        sb.append("\n");
        sb.append("contentHeight:" + contentHeight);
        sb.append("\n");

        if(checkDeviceHasNavigationBar()){
            sb.append("\n");
            sb.append("导航栏的高度：\n");
            sb.append("navigationHeight:" + getNavigationBarHeight());
        }

        tvShow.setText(sb.toString());

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
}
