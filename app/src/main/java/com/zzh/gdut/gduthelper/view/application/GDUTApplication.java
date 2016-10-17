package com.zzh.gdut.gduthelper.view.application;

import android.app.Application;

import im.fir.sdk.FIR;

/**
 * Created by ZengZeHong on 2016/10/17.
 */

public class GDUTApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FIR.init(this);
    }
}
