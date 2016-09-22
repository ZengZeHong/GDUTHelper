package com.zzh.gdut.gduthelper.util.callback;

/**
 * Created by ZengZeHong on 2016/9/22.
 */

public interface ResultListener {
    void onResultSuccess(String success);

    void onResultFail(String fail);
}
