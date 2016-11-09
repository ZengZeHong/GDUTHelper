package com.zzh.gdut.gduthelper.model.mimplement;

import com.zzh.gdut.gduthelper.model.minterface.ScheduleModel;
import com.zzh.gdut.gduthelper.networkutil.NetworkConnection;
import com.zzh.gdut.gduthelper.networkutil.NetworkUtil;
import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;
import com.zzh.gdut.gduthelper.util.ApiUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ZengZeHong on 2016/10/15.
 */

public class ScheduleModelImp implements ScheduleModel {
    @Override
    public void getSchedule(String year, String term, ResultListener resultListener) {

    }

    @Override
    public void getSchedule(ResultListener resultListener ,  String userName , String userNumber) {
        String url;
        try {
            url = ApiUtil.URL_HREF_PERSON_CLASS +
                    "?xh=" + userNumber +
                    "&xm=" + URLEncoder.encode(userName, NetworkConnection.STRING_CODE) +
                    "&gnmkdm=N121603";
            NetworkUtil.getInstance().get(url, resultListener);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
