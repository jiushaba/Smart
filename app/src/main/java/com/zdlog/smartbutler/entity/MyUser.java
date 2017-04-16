package com.zdlog.smartbutler.entity;

import cn.bmob.v3.BmobUser;

/**
 * 项目名：SmartButler
 * 包名 ： com.zdlog.smartbutler.entity
 * 文件名：MyUser
 * 创建者：jiushaba
 * 创建时间：2017/4/10 0010下午 7:05
 * 描述： 用户属性
 */
public class MyUser extends BmobUser{
    private int age;
    private boolean sex;
    private String desc;
    private String photoPath;

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
