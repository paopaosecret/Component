package com.xbing.app.component.ui.activity.layer2;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.api.InjectHelper;
import com.example.lib_annotation.BindView;
import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;

/**
 * Created by zhaobing04 on 2018/6/2.
 */

public class ExceptionActivity extends BaseActivity {

    private static final String TAG = "ExceptionActivity";

    @BindView(R.id.btn_test_exception)
    public Button btnTestException;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception);
        InjectHelper.inject(this);
        btnTestException.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_test_exception:
                List<String> list = new ArrayList<>();
                list.add("hello");
                list.add("word");
                try{
                    String indexException = list.get(2);

                }catch (ArrayIndexOutOfBoundsException e){
                    Toast.makeText(ExceptionActivity.this,"ArrayIndexOutOfBoundsException",Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"ExceptionActivity:ArrayIndexOutOfBoundsException");
                }catch (IndexOutOfBoundsException e){
                    Toast.makeText(ExceptionActivity.this,"IndexOutOfBoundsException",Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"ExceptionActivity:IndexOutOfBoundsException");
                }catch (Exception e){
                    Toast.makeText(ExceptionActivity.this,"Exception:" + e.toString(),Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"Exception:" + e.toString());
                }finally {
                    Log.d(TAG,"finally");
                }

                break;
        }
    }
}
