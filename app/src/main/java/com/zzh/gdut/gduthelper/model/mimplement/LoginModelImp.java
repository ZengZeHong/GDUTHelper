package com.zzh.gdut.gduthelper.model.mimplement;

import android.util.Log;

import com.zzh.gdut.gduthelper.bean.StudentInfo;
import com.zzh.gdut.gduthelper.model.minterface.LoginModel;
import com.zzh.gdut.gduthelper.networkutil.NetworkConnection;
import com.zzh.gdut.gduthelper.networkutil.NetworkUtil;
import com.zzh.gdut.gduthelper.networkutil.PostBody;
import com.zzh.gdut.gduthelper.networkutil.callback.ByteListener;
import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;
import com.zzh.gdut.gduthelper.util.ApiUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ZengZeHong on 2016/9/24.
 * 登陆接口实现类
 */

public class LoginModelImp implements LoginModel {
    private static final String TAG = "LoginModelImp";
    @Override
    public void login(StudentInfo studentInfo, ResultListener resultListener) {
        try {
            //添加登陆的请求参数
            PostBody postBody = new PostBody.Builder().
                    addParams("__VIEWSTATE", URLEncoder.encode("dDwyODE2NTM0OTg7Oz7QqY3yg91iEh+CrEbxxVUHRHuTxg==", NetworkConnection.STRING_CODE)).
                    addParams("txtUserName", studentInfo.getAccount()).
                    addParams("TextBox2", studentInfo.getPassword()).
                    addParams("txtSecretCode", studentInfo.getImgCode()).
                    addParams("RadioButtonList1", URLEncoder.encode("学生", "GBK")).
                    addParams("Button1", "").
                    addParams("lbLanguage", "").
                    addParams("hidPdrs", "").
                    addParams("hidsc", "").
                    build();
            NetworkUtil.getInstance().post(ApiUtil.URL_LOGIN_FIRST, postBody, resultListener);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void getInfo(String path, ResultListener resultListener) {
        Log.e(TAG, "getInfo: " + ApiUtil.URL_HOST_TWO + path );
        NetworkUtil.getInstance().get(ApiUtil.URL_HOST_TWO + path, resultListener);
    }

    @Override
    public void getImageCode(ByteListener byteListener) {
        NetworkUtil.getInstance().get(ApiUtil.URL_LOGIN_IMAGE_CODE, byteListener);
    }
}
