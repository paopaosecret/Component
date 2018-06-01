package com.xbing.app.account;

import com.xbing.app.account.entity.Patient;

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
     * 添加新患者
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

}
