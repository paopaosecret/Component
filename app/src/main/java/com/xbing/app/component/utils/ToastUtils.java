package com.xbing.app.component.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xbing.app.component.MyApplication;


/**
 * Created by SongYongmeng on 2016/11/24.
 */

public class ToastUtils extends Toast {
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link}
     *                or {@link Activity} object.
     */
    public ToastUtils(Context context) {
        super(context);
    }

    public static void show(String msg){
        Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }
}
