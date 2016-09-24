package com.zzh.gdut.gduthelper.bean;

/**
 * Created by ZengZeHong on 2016/9/24.
 * 学生基本信息
 */

public class StudentInfo {
    //登陆学号
    private String account;
    //密码
    private String password;
    //验证码
    private String imgCode;

    public StudentInfo(String account, String password, String imgCode) {
        this.account = account;
        this.password = password;
        this.imgCode = imgCode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
    }
}
