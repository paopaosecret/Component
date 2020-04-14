package com.xbing.app.component.ui.activity.layer3;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.xbing.app.component.R;
import com.xbing.app.component.ui.customview.testdispatch.MyLayout;

public class ViewDispatchActivity extends Activity {
    private String TAG = ViewDispatchActivity.class.getSimpleName();
    private MyLayout mainIconItem;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_view);
        mainIconItem = (MyLayout)findViewById(R.id.test_view);
        mainIconItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.e("MainIconItem", "onTouch: MotionEvent= ACTION_DOWN");
                        break;

                    case MotionEvent.ACTION_MOVE:
                        Log.e("MainIconItem", "onTouch: MotionEvent= ACTION_MOVE");
                        break;

                    case MotionEvent.ACTION_UP:
                        Log.e("MainIconItem", "onTouch: MotionEvent= ACTION_UP");
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        return super.onTouchEvent(event);
    }
}
