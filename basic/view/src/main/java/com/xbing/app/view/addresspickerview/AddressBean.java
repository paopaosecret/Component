package com.xbing.app.view.addresspickerview;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhaobing04 on 2018/6/8.
 */

public class AddressBean implements Serializable {

    private List<AddressItemBean> province;    //省
    private List<AddressItemBean> city;        //市
    private List<AddressItemBean> district;    //区、县

    public List<AddressItemBean> getProvince() {
        return province;
    }

    public void setProvince(List<AddressItemBean> province) {
        this.province = province;
    }

    public List<AddressItemBean> getCity() {
        return city;
    }

    public void setCity(List<AddressItemBean> city) {
        this.city = city;
    }

    public List<AddressItemBean> getDistrict() {
        return district;
    }

    public void setDistrict(List<AddressItemBean> district) {
        this.district = district;
    }

    public static class AddressItemBean implements Serializable {
        private String i;     //address code
        private String n;     //address name
        private String p;     //address parent code

        public String getI() {
            return i;
        }

        public void setI(String i) {
            this.i = i;
        }

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }
    }
}
