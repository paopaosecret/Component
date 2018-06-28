package com.xbing.app.view.addresspickerview;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhaobing04 on 2018/6/8.
 */

public class LocationBean {
    public static void main(String args[]){

    }

    private List<AddressItemBean> list;

    public List<AddressItemBean> getList() {
        return list;
    }

    public void setList(List<AddressItemBean> list) {
        this.list = list;
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
