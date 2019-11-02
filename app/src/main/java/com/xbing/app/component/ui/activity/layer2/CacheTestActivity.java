package com.xbing.app.component.ui.activity.layer2;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.api.InjectHelper;
import com.example.lib_annotation.BindView;
import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.BaseActivity;
import com.xbing.app.component.ui.test.User;
import com.xbing.app.net.common.cache.dbcache.CacheEntity;
import com.xbing.app.net.common.cache.dbcache.CacheManager;
import com.xbing.app.net.common.entity.HttpHeaders;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CacheTestActivity extends BaseActivity {


    @BindView(R.id.btn_replace)
    public Button mBtnReplace;

    @BindView(R.id.btn_delete)
    public Button mBtnDelete;

    @BindView(R.id.btn_get)
    public Button mBtnGet;

    @BindView(R.id.tv_show)
    public TextView mTvShow;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_test);
        InjectHelper.inject(this);
        mBtnReplace.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mBtnGet.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_replace:
                replace();
                break;

            case R.id.btn_delete:
                delete();
                break;

            case R.id.btn_get:
                get();
                break;
        }
    }

    private void replace() {
        CacheEntity cacheEntity = new CacheEntity<>();
        cacheEntity.setLocalExpire(System.currentTimeMillis());
        cacheEntity.setHeaders(new HttpHeaders());
        cacheEntity.setKey("http://test");
        cacheEntity.setData(new User("zhangsan", 1, 1));

        CacheManager.INSTANCE.replace("http://test", cacheEntity);
        mTvShow.setText("插入成功");
    }

    private void delete() {
        CacheManager.INSTANCE.remove("http://test");
        mTvShow.setText("删除成功");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void get() {
        CacheEntity cacheEntity = CacheManager.INSTANCE.get("http://test");
        if(cacheEntity == null){
            mTvShow.setText("当前key对应的数据不存在");
            return;
        }
        User user = (User) cacheEntity.getData();
        mTvShow.setText(user.toString());

//        TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
////            String imsi1 = getIMSI(CacheTestActivity.this);
////
////            Log.e("IMSI", imsi1);
////            mTvShow.setText("IMSI = " +  imsi1 );
//            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
//            String imei1 = getIMEI(CacheTestActivity.this,0);
//            String imei2 = getIMEI(CacheTestActivity.this,1);
//
//            String imsi1 = getIMSI(CacheTestActivity.this,0);
//            String imsi2 = getIMSI(CacheTestActivity.this,1);
//
//            String phone1 = getMSISDN(CacheTestActivity.this,0);
//            String phone2 = getMSISDN(CacheTestActivity.this,1);
//            mTvShow.setText("imei1 = " +  imei1 + "\nimei2 = " +  imei2 + "\nimsi1 = " +  imsi1 + "\nimsi2 = " +  imsi2
//                    + "\nphone1 = " +  phone1 + "\nphone2 = " +  phone2);
//            return;
//        }else{
//            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
//            String imei1 = getIMEI(CacheTestActivity.this,0);
//            String imei2 = getIMEI(CacheTestActivity.this,1);
//
//            String imsi1 = getIMSI(CacheTestActivity.this,0);
//            String imsi2 = getIMSI(CacheTestActivity.this,1);
//            String phone1 = getMSISDN(CacheTestActivity.this,0);
//            String phone2 = getMSISDN(CacheTestActivity.this,1);
//            mTvShow.setText("imei1 = " +  imei1 + "\nimei2 = " +  imei2 + "\nimsi1 = " +  imsi1 + "\nimsi2 = " +  imsi2
//                    + "\nphone1 = " +  phone1 + "\nphone2 = " +  phone2);
//            return;
//        }

    }

    /**
     * @param slotId  slotId为卡槽Id，它的值为 0、1；
     * @return
     */
    public static String getIMEI(Context context, int slotId) {
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Method method = manager.getClass().getMethod("getImei", int.class);
            String imei = (String) method.invoke(manager, slotId);
            return imei;
        } catch (Exception e) {
            return "";
        }
    }



    /**
     * 获取手机MSISDN号
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public String getIMSI(Context context, int id) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
        Method method = null;
        try {
            method = telephonyManager.getClass().getMethod("getSubscriberId", int.class);
            String imsi = (String) method.invoke(telephonyManager, id);
            return imsi;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return "";
    }

    @TargetApi(Build.VERSION_CODES.M)
    public String getMSISDN(Context context, int id){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
        try {
            return telephonyManager.getLine1Number() + "\n" + telephonyManager.getGroupIdLevel1();
        } catch (Exception e){

        }

        return "";
    }

//    public void testRxJava(){
//        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                Log.d("observable", "=========================currentThread name: " + Thread.currentThread().getName());
//                e.onNext(1);
//                e.onNext(2);
//                e.onNext(3);
//                e.onComplete();
//            }
//        });
//    }

}

