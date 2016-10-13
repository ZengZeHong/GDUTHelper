package com.zzh.gdut.gduthelper.model.mimplement;

import com.zzh.gdut.gduthelper.model.minterface.ChangePwModel;
import com.zzh.gdut.gduthelper.networkutil.NetworkConnection;
import com.zzh.gdut.gduthelper.networkutil.NetworkUtil;
import com.zzh.gdut.gduthelper.networkutil.PostBody;
import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;
import com.zzh.gdut.gduthelper.util.ApiUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ZengZeHong on 2016/10/13.
 */
public class ChangePwModelImp implements ChangePwModel {
    @Override
    public void changePassword(String originalPw, String newPw, String confirmPw, ResultListener resultListener) {
        String url;
        try {
            url = ApiUtil.URL_HREF_CHANGE_PASSWORD +
                    "?xh=" + ApiUtil.USER_NUMBER +
                    "&gnmkdm=N121502";
            PostBody postBody = new PostBody.Builder().
                    addParams("__VIEWSTATE", URLEncoder.encode("dDwxMDIyOTMyNDk0Ozs+3Mw2oW6uW7Iko3ZeO3k8EC8moSk=", NetworkConnection.STRING_CODE)).
                    addParams("TextBox2", originalPw).
                    addParams("TextBox3", newPw).
                    addParams("TextBox4", confirmPw).
                    addParams("Button1", URLEncoder.encode("修  改", NetworkConnection.STRING_CODE)).build();
            NetworkUtil.getInstance().post(url, postBody, resultListener);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
