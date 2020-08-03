package com.xbing.app.component.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.xbing.app.component.MyApplication;


/**
 * Created by SongYongmeng on 2016/11/24.
 */

public class ToastUtils extends Toast{

    private static Toast toast;

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public ToastUtils(Context context) {
        super(context);
    }

    public static void show(String msg){
        Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

//    public static void showToast(){
//        //先创建一个Toast对象
//        if(toast == null){
//            toast = new Toast(MyApplication.getContext());
//
//        }
//        toast.setGravity(Gravity.TOP, 0, 0);
//        View toastView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.layout_toast, null);
//        toast.setView(toastView);
//            //设置Toast信息提示框显示的位置（在屏幕顶部水平居中显示）
//        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,100);
//        try {
//            //从Toast对象中获得mTN变量
//            Field field= toast.getClass().getDeclaredField("mTN");
//            field.setAccessible(true);
//            Object obj=field.get(toast);
//            //TN对象中获得了show方法
//            Method method = obj.getClass().getDeclaredMethod("show",null);
//            //调用show方法来显示Toast信息提示框
//            method.invoke(obj,null);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    public static void hideToast(){
//        try {
//            //需要将前面代码中的obj变量变成类变量。这样在多个地方就都可以访问了
//            Method method = toast.getClass().getDeclaredMethod("hide",null);
//            method.invoke(toast,null);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
}
