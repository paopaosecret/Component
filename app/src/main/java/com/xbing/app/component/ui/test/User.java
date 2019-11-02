package com.xbing.app.component.ui.test;

import java.io.Serializable;

public class User implements Serializable{
    private int id;
    private int age;
    private String name;

    @BindUser(id=1,name="xiaoming",age=10)
    public User(){

    }

    public User(String name, int age, int id){
        this.name = name;
        this.age = age;
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    public static void main(String args[]){
        User user = new User();
        UserProcesss.init(user);
        System.out.println(user.toString());
    }
}
