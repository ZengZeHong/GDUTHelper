package com.zzh.gdut.gduthelper.model.mimplement;

import com.zzh.gdut.gduthelper.bean.StudentInfo;
import com.zzh.gdut.gduthelper.model.minterface.LoginModel;
import com.zzh.gdut.gduthelper.networkutil.NetworkUtil;
import com.zzh.gdut.gduthelper.networkutil.PostBody;
import com.zzh.gdut.gduthelper.networkutil.callback.ByteListener;
import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;
import com.zzh.gdut.gduthelper.util.AppConstants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ZengZeHong on 2016/9/24.
 * 登陆接口实现类
 */

public class LoginModelImp implements LoginModel {

    @Override
    public void login(StudentInfo studentInfo, ResultListener resultListener) {
        try {
            //添加登陆的请求参数
            PostBody postBody = new PostBody.Builder().
                    addParams("__VIEWSTATE", URLEncoder.encode("dDwyODE2NTM0OTg7Oz5ymfdcUjEae97aD7oAp0vekoeicw==", "GBK")).
                    addParams("txtUserName", studentInfo.getAccount()).
                    addParams("TextBox2", studentInfo.getPassword()).
                    addParams("txtSecretCode", studentInfo.getImgCode()).
                    addParams("RadioButtonList1", URLEncoder.encode("学生", "GBK")).
                    addParams("Button1", "").
                    addParams("lbLanguage", "").
                    addParams("hidPdrs", "").
                    addParams("hidsc", "").
                    build();
            NetworkUtil.getInstance().post(AppConstants.URL_LOGIN_FIRST, postBody, resultListener);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getInfo(String path, ResultListener resultListener) {
        NetworkUtil.getInstance().get(AppConstants.URL_HOST + path, resultListener);
    }

    @Override
    public void getImageCode(ByteListener byteListener) {
        NetworkUtil.getInstance().get(AppConstants.URL_LOGIN_IMAGE_CODE, byteListener);
    }
}
