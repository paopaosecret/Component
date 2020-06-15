package com.xbing.app.component.ui.activity.layer2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.xbing.app.component.R;
import com.xbing.app.component.ui.test.dexclassloader.IMath;
import com.xbing.app.component.ui.test.dexclassloader.MathImpl;
import com.xbing.app.component.utils.ToastUtils;

import java.io.File;

import dalvik.system.DexClassLoader;

public class ClassLoderActivity  extends Activity implements View.OnClickListener {
    private static final String TAG = ClassLoderActivity.class.getSimpleName();

    IMath math;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classloader);

        math = new MathImpl();

        findViewById(R.id.btn_test).setOnClickListener(this);
        findViewById(R.id.btn_repair).setOnClickListener(this);

        ClassLoader classLoader = ClassLoderActivity.class.getClassLoader();
        Log.e(TAG, classLoader.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_test:
                ToastUtils.makeText(ClassLoderActivity.this, math.divide(), ToastUtils.LENGTH_SHORT).show();
                break;
            case R.id.btn_repair:
                getStoragePermission();
                break;
        }
    }

    /**
     * 加载手机存储器上的dex文件的压缩文件
     */
    private void fix() {
        final File dexFile = new File(Environment.getExternalStorageDirectory().getPath() +
                File.separator + "math_hotfix.jar");
        if(!dexFile.exists()){
            ToastUtils.makeText(ClassLoderActivity.this, "文件不存在", ToastUtils.LENGTH_SHORT).show();
        }else{
            DexClassLoader dexClassLoader = new DexClassLoader(dexFile.getAbsolutePath(), getExternalCacheDir().getAbsolutePath(),null, getClassLoader());
            try{
                Class clazz = dexClassLoader.loadClass("com.xbing.app.component.ui.test.dexclassloader.MathImplHotFix");
                IMath m = (IMath) clazz.newInstance();
                ToastUtils.makeText(ClassLoderActivity.this, m.divide(), ToastUtils.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static final int CODE_WRITE_STORAGE = 129;

    /**
     * 获取SD卡写权限
     */
    public void getStoragePermission() {
        boolean findMethod = true;
        try {
            ContextCompat.class.getMethod("checkSelfPermission", Context.class, String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            findMethod = false;
        }

        if (findMethod && ContextCompat.checkSelfPermission(ClassLoderActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(ClassLoderActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(ClassLoderActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_STORAGE);
                return;
            }

            ActivityCompat.requestPermissions(ClassLoderActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_STORAGE);
            return;

        } else {
            fix();
        }

    }

}
