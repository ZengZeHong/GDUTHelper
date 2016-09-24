package com.zzh.gdut.gduthelper.view.vinterface;

/**
 * Created by ZengZeHong on 2016/9/24.
 */

public interface LoginInterface {
    void loginSuccess(String result);

    void loginFail(String fail);

    void getImageCodeSuccess(byte[] bytes);

    void getImageCodeFail(String fail);

    void getInfoSuccess(String result);

    void getInfoFail(String fail);
}
