package com.xbing.app.component.ui.activity.layer2;

import android.content.Intent;
import android.os.Bundle;

import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.BaseActivity;


/**
 * 页面转场动画测试
 * 实现方式：
 * 1、overridePendingTransition 方法 （设置单个页面的转场动画）
 * 2、定义Application 的 style AppTheme 中的 windowAnimationStyle 属性 （全局配置转场动画）
 *
 * 方法一：说明：overridePendingTransition() 是一个用于在 Android 应用中自定义 Activity 切换动画的方法。当你从一个 Activity 启动另一个 Activity
 * 时，可以使用此方法来覆盖默认的过渡动画。
 *
 * 使用：在启动新 Activity 之后立即调用 overridePendingTransition() 方法
 *
 * 示例：
 *    Intent intent = new Intent(PageAnimationOutActivity.this, PageAnimationInActivity.class);
 *    startActivity(intent);
 *    overridePendingTransition(R.animator.enter_animation, R.animator.exit_animation);
 *
 * 如果从 PageAnimationInActivity 页面 返回到 PageAnimationOutActivity 页面 可以用相反的动画，让效果更流畅
 * 示例： 重写 PageAnimationInActivity 页面的 onBackPressed方法
 *     @Override
 *     public void onBackPressed() {
 *         super.onBackPressed();
 *         overridePendingTransition(R.animator.return_enter_animation, R.animator.return_exit_animation);
 *     }
 *
 *  方法二：定义Application的style
 *  <application
 *      ...
 *      Android:theme="@style/AppTheme">
 *
 *     <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
 *         <!-- Customize your theme here. -->
 *         <item name="colorPrimary">@color/colorPrimary</item>    <!--  标题栏颜色 -->
 *         <item name="colorPrimaryDark">@color/colorPrimaryDark</item>  <!-- 状态栏颜色 -->
 *         <item name="colorAccent">@color/colorAccent</item>
 *
 *         <!-- 配置全局转场动画 设置细节 -->
 *         <item name="android:windowAnimationStyle">@style/activityAnim</item>
 *     </style>
 *
 *      <!-- 配置全局转场动画 设置细节 -->
 *     <style name="activityAnim">
 *         <item name="android:activityOpenEnterAnimation">@anim/enter_animation</item>
 *         <item name="android:activityOpenExitAnimation">@anim/exit_animation</item>
 *         <item name="android:activityCloseEnterAnimation">@anim/return_enter_animation</item>
 *         <item name="android:activityCloseExitAnimation">@anim/return_exit_animation</item>
 *     </style>
 */
public class PageAnimationOutActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_animation_out);

        findViewById(R.id.btn_jump_page).setOnClickListener(view -> {
            Intent intent = new Intent(PageAnimationOutActivity.this, PageAnimationInActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation);
        });
    }
}
