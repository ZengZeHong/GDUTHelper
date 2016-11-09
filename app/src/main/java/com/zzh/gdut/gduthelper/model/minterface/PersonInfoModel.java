package com.zzh.gdut.gduthelper.model.minterface;

import com.zzh.gdut.gduthelper.bean.PersonInfo;
import com.zzh.gdut.gduthelper.networkutil.callback.ByteListener;
import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;

/**
 * Created by ZengZeHong on 2016/9/26.
 */

public interface PersonInfoModel {
    void submitData(ResultListener resultListener , PersonInfo personInfo,String userName , String userNumber);

    void getPersonInfoData(ResultListener resultListener,String userName , String userNumber);

    void getUserImageHead(ByteListener byteListener, String userNumber);
}
