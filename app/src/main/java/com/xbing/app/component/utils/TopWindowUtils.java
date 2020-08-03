package com.xbing.app.component.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xbing.app.component.MyApplication;
import com.xbing.app.component.R;

public class TopWindowUtils {

    private static PopupWindow popupWindow;

    public static void show(Activity activit) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow();
            LayoutInflater inflater = (LayoutInflater) MyApplication.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayout = new LinearLayout(MyApplication.getContext());
            View viewContent = inflater.inflate(R.layout.layout_toast, linearLayout);
            popupWindow.setContentView(viewContent);
            popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(R.style.PopupTopAnim);
        }
        popupWindow.showAtLocation(activit.getWindow().getDecorView(), Gravity.TOP, 0, 0);

        final TextView tv = ((TextView) popupWindow.getContentView().findViewById(R.id.tv));
        tv.setText("aaaaaaaaaaaaaaaaaaaaaa");
        popupWindow.getContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(tv.getText().toString());
            }
        });


//        handler.removeMessages(1);
//        handler.sendEmptyMessageDelayed(1, 5000);
    }

    public static void dismiss(){
        if(popupWindow != null){
            popupWindow.dismiss();
        }
    }

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            popupWindow.dismiss();
        }
    };

    /**
     * 从dp单位转换为px
     *
     * @param dp dp值
     * @return 返回转换后的px值
     */
    private static int dp2px(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}
