package com.xbing.app.component.ui.activity.layer3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.xbing.app.component.R;
import com.xbing.app.component.ui.customview.testcustom.HorizontalView;

import java.util.Random;

public class CustomViewActivity extends Activity {
    private String TAG = CustomViewActivity.class.getSimpleName();

    private HorizontalView mLayoutHorizontal;
    private int            index = -1;

    private View mFirstView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        mLayoutHorizontal = findViewById(R.id.hv_horizontal);
        mLayoutHorizontal.setAnimationCacheEnabled(true);
        findViewById(R.id.btn_add).setOnClickListener(view -> {
            addView();
        });

        findViewById(R.id.btn_remove).setOnClickListener(view -> {
            removeView();
        });

        findViewById(R.id.btn_switch).setOnClickListener(view -> {
            mLayoutHorizontal.swapView(1, 2);
        });
    }

    private void removeView() {
        mLayoutHorizontal.removeViewAt(index);
        index--;
    }

    private void addView() {
        index++;
        View tempView = new View(CustomViewActivity.this);

        tempView.setBackgroundColor(getRandomColor());
        tempView.setOnClickListener((view -> {
            int index = mLayoutHorizontal.indexOfChild(view);
            if (index < mLayoutHorizontal.getChildCount()-1) {
                View nextView = mLayoutHorizontal.getChildAt(index + 1);
                mLayoutHorizontal.swapViewWithAnimation(view, nextView);
            } else {
                View nextView = mLayoutHorizontal.getChildAt(0);
                mLayoutHorizontal.swapViewWithAnimation(view, nextView);
            }
        }));
        mLayoutHorizontal.addView(tempView, index);
    }


    public int getRandomColor() {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        return Color.rgb(red, green, blue);
    }
}

