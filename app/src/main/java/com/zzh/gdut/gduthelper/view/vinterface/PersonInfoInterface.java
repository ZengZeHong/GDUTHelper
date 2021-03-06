package com.zzh.gdut.gduthelper.view.vinterface;

/**
 * Created by ZengZeHong on 2016/9/26.
 * 个人信息提交
 */

public interface PersonInfoInterface {
    void submitSuccess(String success);
    void submitFail(String fail);
    void getInfoSuccess(String success);
    void getInfoFail(String fail);
    void getImageHeadSuccess(byte[] bytes);
    void getImageHeadFail(String fail);
}
