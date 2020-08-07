package com.xbing.app.component.ui.activity.layer2;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.xbing.app.basic.router.HyRouter;
import com.xbing.app.basic.router.callback.CallBack;
import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.BaseActivity;

public class RouterActivity extends BaseActivity {

    private EditText mEtUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router);

        mEtUri = findViewById(R.id.et_url);
        findViewById(R.id.btn_router).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_router:
                String str = mEtUri.getText().toString();
                HyRouter.transfer(RouterActivity.this, Uri.parse(str), new CallBack() {
                    @Override
                    public void onResult(Object o) {
                        Log.d("HyRouter", "onResult: " + (o != null ? JSON.toJSONString(o) : null));
                    }
                });

                break;
        }
    }
}
