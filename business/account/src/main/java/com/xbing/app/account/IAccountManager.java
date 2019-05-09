package com.xbing.app.account;

import com.xbing.app.account.entity.MovieObject;
import com.xbing.app.account.entity.Patient;

import java.lang.annotation.Documented;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhaobing on 2016/7/1.
 */
public interface IAccountManager {

    /**
     * 登录接口
     * @param name 帐号
     * @param pwd 密码
     * @param callBack 请求结果回调
     */
    void login(String name, String pwd, RequestCallback callBack);

    /**
     * 旧版本登录接口
     */
    @SuppressWarnings("旧版本")
    @Deprecated
    void login();

    /**
     * 添加患者
     * @param patient
     * @param callback
     */
    void addPatient(Patient patient, RequestCallback callback);

    /**
     * 设置默认患者
     * @param patientId
     * @param callback
     */
    void setDefaultPatient(Long patientId, RequestCallback callback);

    /**
     * 获取用户信息
     * @param callback
     */
    void getUsers(RequestCallback callback);

}
