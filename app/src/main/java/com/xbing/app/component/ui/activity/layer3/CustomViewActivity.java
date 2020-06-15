package com.xbing.app.component.ui.activity.layer3;

import android.app.Activity;
import android.os.Bundle;

import com.xbing.app.component.R;

import androidx.annotation.Nullable;

public class CustomViewActivity  extends Activity {
    private String TAG = CustomViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

    }

}

