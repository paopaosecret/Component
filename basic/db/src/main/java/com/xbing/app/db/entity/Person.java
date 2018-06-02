package com.xbing.app.db.entity;


import com.xbing.app.db.annotion.DbFiled;
import com.xbing.app.db.annotion.DbTable;

/**
 * Created by zhaobing04 on 2018/5/19.
 */

@DbTable(value = "tb_person3")
public class Person {

    @DbFiled("tb_name")
    public String name;

    @DbFiled("tb_password")
    public String password;

    @DbFiled("tb_head")
    public byte[] head;

    public byte[] getHead() {
        return head;
    }

    public void setHead(byte[] head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
