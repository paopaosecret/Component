package com.xbing.app.component.ui.activity.layer2;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.xbing.app.component.R;
import com.xbing.app.component.bean.AppInfo;
import com.xbing.app.component.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PackageManagerActivity  extends BaseActivity {

    private TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_manager);

        tvResult = findViewById(R.id.tv_result);
        findViewById(R.id.btn_runing).setOnClickListener(this);
        findViewById(R.id.btn_all).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_runing:
                getRunningAppInfo();
                break;

            case R.id.btn_all:
                getAllAppInfo();
                break;
        }
    }

    private void getAllAppInfo() {
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        List<AppInfo> appInfos = new ArrayList<>();
        for (int i = 0; i < packages.size(); i++) {

            PackageInfo packageInfo = packages.get(i);

            AppInfo tmpInfo = new AppInfo();

            tmpInfo.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();

            tmpInfo.packageName = packageInfo.packageName;

            tmpInfo.versionName = packageInfo.versionName;

            tmpInfo.versionCode = packageInfo.versionCode;

            appInfos.add(tmpInfo);
            Log.d(PackageManagerActivity.class.getSimpleName(), JSON.toJSONString(tmpInfo));
        }
    }

    private void getRunningAppInfo() {
        PackageManager localPackageManager = getPackageManager();
        List localList = localPackageManager.getInstalledPackages(0);
        for (int i = 0; i < localList.size(); i++) {
            PackageInfo localPackageInfo1 = (PackageInfo) localList.get(i);
            String str1 = localPackageInfo1.packageName.split(":")[0];
            if (((ApplicationInfo.FLAG_SYSTEM & localPackageInfo1.applicationInfo.flags) == 0) && ((ApplicationInfo.FLAG_UPDATED_SYSTEM_APP & localPackageInfo1.applicationInfo.flags) == 0) && ((ApplicationInfo.FLAG_STOPPED & localPackageInfo1.applicationInfo.flags) == 0)) {
                Log.e(PackageManagerActivity.class.getSimpleName(), str1);
            }
        }

    }


}

