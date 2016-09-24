package com.zzh.gdut.gduthelper.model.minterface;

import com.zzh.gdut.gduthelper.bean.StudentInfo;
import com.zzh.gdut.gduthelper.networkutil.callback.ByteListener;
import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;

/**
 * Created by ZengZeHong on 2016/9/24.
 */

public interface LoginModel {
    //登陆提交信息
    void login(StudentInfo studentInfo, ResultListener resultListener);

    void getInfo(String path , ResultListener resultListener);

    //获取验证码
    void getImageCode(ByteListener byteListener);
}
