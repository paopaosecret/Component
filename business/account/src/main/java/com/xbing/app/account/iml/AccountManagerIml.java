package com.xbing.app.account.iml;

import android.util.Log;

import com.google.gson.Gson;
import com.xbing.app.account.AppUrl;
import com.xbing.app.account.IAccountManager;
import com.xbing.app.account.RequestCallback;
import com.xbing.app.account.entity.Patient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaobing on 2016/7/1.
 */
public class AccountManagerIml implements IAccountManager {

    protected Gson gson;//json解析工具

    public AccountManagerIml()
    {
        gson = new Gson();
    }

    private String TAG = IAccountManager.class.getSimpleName();

    @Override
    public void login(String name, String pwd, final RequestCallback callBack) {
        String url = "http://172.27.35.19:8080/login?username=" + name + "&password=" + pwd;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        headers.put("charset", "utf-8");
        Log.i(TAG,"url:"+url);

    }

    @Override
    public void login() {
        return;
    }

    @Override
    public void addPatient(Patient patient, final RequestCallback callBack) {
        String url = "";
        Log.i(TAG,"url:"+url);

        JSONObject body = new JSONObject();
        try {
            body.put("token", "");
            body.put("name", patient.getName());
            body.put("sex", patient.getSex());
            body.put("birthday", patient.getBirthday());
            body.put("height", patient.getHeight());
            body.put("weight", patient.getWeight());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        headers.put("charset", "utf-8");
    }

    @Override
    public void setDefaultPatient(Long patientId, final RequestCallback callBack) {
        String url = "";
        Log.i(TAG,"url:"+url);

        JSONObject body = new JSONObject();
        try {
            body.put("token", "");
            body.put("patient_id", patientId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        headers.put("charset", "utf-8");
    }

    @Override
    public void getUsers(final RequestCallback callback) {
        String url = AppUrl.GET_USER_LIST;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        headers.put("charset", "utf-8");
        Log.i(TAG,"url:"+url);

    }
}
