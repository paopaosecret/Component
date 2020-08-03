package com.xbing.app.component.ui.activity.layer2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.BaseActivity;
import com.xbing.app.component.ui.activity.layer3.StickyActivity;
import com.xbing.app.component.ui.activity.layer3.TabRecyclerActivity;
import com.xbing.app.component.ui.activity.layer3.TabScrollActivity;

import androidx.annotation.Nullable;

public class MaoDianActivity extends BaseActivity implements View.OnClickListener{
    private Button btnScroll;
    private Button btnRecycler;
    private Button btnSticky;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maodian);
        btnScroll = findViewById(R.id.scroll);
        btnRecycler = findViewById(R.id.recycler);
        btnSticky = findViewById(R.id.sticky);
        btnScroll.setOnClickListener(this);
        btnRecycler.setOnClickListener(this);
        btnSticky.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scroll:
                Intent intent1 = new Intent(this, TabScrollActivity.class);
                startActivity(intent1);
                break;
            case R.id.recycler:
                Intent intent2 = new Intent(this, TabRecyclerActivity.class);
                startActivity(intent2);
                break;
            case R.id.sticky:
                Intent intent3 = new Intent(this, StickyActivity.class);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }
}
