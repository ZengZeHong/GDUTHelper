package com.zzh.gdut.gduthelper.model.minterface;

import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;

/**
 * Created by ZengZeHong on 2016/10/15.
 */

public interface ScheduleModel {
    void getSchedule(String year, String term, ResultListener resultListener);

    void getSchedule(ResultListener resultListener);

}
