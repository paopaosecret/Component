package com.xbing.app.component.ui.activity.layer2;

import android.os.Bundle;

import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.BaseActivity;

public class PageAnimationInActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_animation_in);
        findViewById(R.id.btn_back).setOnClickListener(view -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.return_enter_animation, R.anim.return_exit_animation);
    }
}
