package com.zzh.gdut.gduthelper.model.minterface;

import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;

/**
 * Created by ZengZeHong on 2016/10/13.
 */

public interface ChangePwModel {
    void changePassword(String userNumber , String originalPw , String newPw , String confirmPw , ResultListener resultListener);
}
