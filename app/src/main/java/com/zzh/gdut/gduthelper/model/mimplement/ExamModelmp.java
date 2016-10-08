package com.zzh.gdut.gduthelper.model.mimplement;

import com.zzh.gdut.gduthelper.model.minterface.ExamModel;
import com.zzh.gdut.gduthelper.networkutil.NetworkConnection;
import com.zzh.gdut.gduthelper.networkutil.NetworkUtil;
import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;
import com.zzh.gdut.gduthelper.util.ApiUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ZengZeHong on 2016/10/8.
 */

public class ExamModelmp implements ExamModel {
    @Override
    public void searchExam(ResultListener resultListener) {
        try {
            String url = ApiUtil.URL_HREF_SEARCH_EXAM +
                    "?xh=" + ApiUtil.USER_NUMBER +
                    "&xm=" + URLEncoder.encode(ApiUtil.USER_NAME, NetworkConnection.STRING_CODE) +
                    "&gnmkdm=N121501";
            NetworkUtil.getInstance().get(url , resultListener);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
