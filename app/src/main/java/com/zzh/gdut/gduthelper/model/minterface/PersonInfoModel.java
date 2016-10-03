package com.zzh.gdut.gduthelper.model.minterface;

import com.zzh.gdut.gduthelper.bean.PersonInfo;
import com.zzh.gdut.gduthelper.networkutil.callback.ByteListener;
import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;

/**
 * Created by ZengZeHong on 2016/9/26.
 */

public interface PersonInfoModel {
    void submitData(ResultListener resultListener , PersonInfo personInfo);

    void getPersonInfoData(ResultListener resultListener);

    void getUserImageHead(ByteListener byteListener);
}
