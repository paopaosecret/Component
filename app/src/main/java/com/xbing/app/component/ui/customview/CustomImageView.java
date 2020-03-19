package com.xbing.app.component.ui.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class CustomImageView extends ImageView {
    private String TAG = CustomImageView.class.getSimpleName();
    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "dispatchTouchEvent: MotionEvent= ACTION_DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "dispatchTouchEvent: MotionEvent= ACTION_MOVE");
                break;

            case MotionEvent.ACTION_UP:
                Log.e(TAG, "dispatchTouchEvent: MotionEvent= ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onTouchEvent: MotionEvent= ACTION_DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onTouchEvent: MotionEvent= ACTION_MOVE");
                break;

            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onTouchEvent: MotionEvent= ACTION_UP");
                break;
        }
        return true;
//        return super.onTouchEvent(event);
    }

}
