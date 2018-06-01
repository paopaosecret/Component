package com.xbing.app.account.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by zhaobing on 2016/8/16.
 */
public class Patient implements Serializable {

    @SerializedName("id")
    private Long patientId;
    private String name;

    private String sex;

    private String mobile;

    private Long birthday;

    private String boold;

    private Double height;

    private Double weight;

    private Boolean married;

    private String location;

    @SerializedName("is_default")
    private Boolean isDefault;

    public Boolean getLastEval() {
        return isLastEval;
    }

    public void setLastEval(Boolean lastEval) {
        isLastEval = lastEval;
    }

    @SerializedName("is_last_eval")
    private Boolean isLastEval;

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Boolean getDefault() {
        if(isDefault == null){
            isDefault = false;
        }
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getMarried() {
        return married;
    }

    public void setMarried(Boolean married) {
        this.married = married;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getBoold() {
        return boold;
    }

    public void setBoold(String boold) {
        this.boold = boold;
    }
}
