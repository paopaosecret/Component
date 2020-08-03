package com.xbing.app.component.ui.activity.layer2;

import android.os.Bundle;
import android.view.View;

import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.BaseActivity;
import com.xbing.app.component.ui.customview.CustomDialog;

public class DialogActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        findViewById(R.id.btn_showdialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CustomDialog customDialog = new CustomDialog(view.getContext());
                customDialog.showDialog("title", "are you speak english?", "ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });
            }
        });

        findViewById(R.id.btn_showdialog2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomDialog customDialog = new CustomDialog(v.getContext());
                customDialog.showDialog("title", "are you speak english are you speak english are you speak englishare you speak english are you speak english are you speak english are you speak english are you speak english are you speak english",
                        "ok ok ok ok ok ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        }, "cancel cancel cancel cancel cancel", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                customDialog.dismiss();
                            }
                        });
            }
        });

        findViewById(R.id.btn_showdialog3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomDialog customDialog = new CustomDialog(v.getContext());
                customDialog.showDialog("are you speak english are you speak english are you speak englishare you speak english are you speak english are you speak english are you speak english are you speak english are you speak english",
                        "ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        }, "cancel", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                customDialog.dismiss();
                            }
                        });
            }
        });

    }
}
