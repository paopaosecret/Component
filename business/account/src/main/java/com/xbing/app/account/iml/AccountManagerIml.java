package com.xbing.app.account.iml;

import android.util.Log;

import com.google.gson.Gson;
import com.xbing.app.account.AppUrl;
import com.xbing.app.account.IAccountManager;
import com.xbing.app.account.RequestCallback;
import com.xbing.app.account.entity.Patient;
import com.xbing.app.account.result.LoginResult;
import com.xbing.app.account.result.RequestResult;
import com.xbing.app.net.common.OkHttpUtils;
import com.xbing.app.net.common.callback.StringCallback;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;

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
        OkHttpUtils.get().url(url).headers(headers).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e(TAG, "login.onResponse error.", e);
                LoginResult result = null;
                result = new LoginResult();
                result.setResultCode("-1");
                callBack.onRequestComplete(result);
            }

            @Override
            public void onResponse(String response) {
                Log.i(TAG, "login.onResponse: " + response.toString());

                LoginResult result = null;
                try
                {
                    result = gson.fromJson(response.toString(),
                            LoginResult.class);
                    result.setResponseString(response);
                }
                catch (Exception e)
                {
                    Log.e(TAG, "login.onResponse error.", e);
                }

                callBack.onRequestComplete(result);
            }
        });

    }

    @Override
    public void login() {

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

        OkHttpUtils.postString()
                .content(body.toString())
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .url(url)
                .headers(headers)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                        RequestResult result = null;
                        result = new RequestResult();
                        result.setResultCode("-1");
                        callBack.onRequestComplete(result);
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "addPatient.onResponse: " + response.toString());

                        RequestResult result = null;
                        try
                        {
                            result = gson.fromJson(response.toString(),
                                    RequestResult.class);
                        }
                        catch (Exception e)
                        {
                            Log.e(TAG, "addPatient.onResponse.", e);
                        }
                        callBack.onRequestComplete(result);
                    }
                });
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

        OkHttpUtils.put()
                .requestBody(body.toString())
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .url(url)
                .headers(headers)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        RequestResult result = null;
                        result = new RequestResult();
                        result.setResultCode("-1");
                        callBack.onRequestComplete(result);
                    }

                    @Override
                    public void onResponse(String response) {

                        RequestResult result = null;
                        try
                        {
                            result = gson.fromJson(response.toString(),
                                    RequestResult.class);
                        }
                        catch (Exception e)
                        {
                            Log.e(TAG, "setDefaultPatient.onResponse.", e);
                        }
                        callBack.onRequestComplete(result);
                    }
                });
    }

    @Override
    public void getUsers(final RequestCallback callback) {
        String url = AppUrl.GET_USER_LIST;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        headers.put("charset", "utf-8");
        Log.i(TAG,"url:"+url);
        OkHttpUtils.get().url(url).headers(headers).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e(TAG, "getUsers.onResponse error.", e);
                LoginResult result = null;
                result = new LoginResult();
                result.setResultCode("-1");
                callback.onRequestComplete(result);
            }

            @Override
            public void onResponse(String response) {
                Log.i(TAG, "getUsers.onResponse: " + response);
                RequestResult result = new RequestResult();
                result.setResultMsg(response);
                result.setResultCode("200");
                callback.onRequestComplete(result);
            }
        });

    }
}
