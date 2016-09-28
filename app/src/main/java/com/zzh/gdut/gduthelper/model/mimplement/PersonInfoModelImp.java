package com.zzh.gdut.gduthelper.model.mimplement;

import android.util.Log;

import com.zzh.gdut.gduthelper.model.minterface.PersonInfoModel;
import com.zzh.gdut.gduthelper.networkutil.NetworkConnection;
import com.zzh.gdut.gduthelper.networkutil.NetworkUtil;
import com.zzh.gdut.gduthelper.networkutil.callback.ByteListener;
import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;
import com.zzh.gdut.gduthelper.util.ApiUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ZengZeHong on 2016/9/26.
 */

public class PersonInfoModelImp implements PersonInfoModel {
    private static final String TAG = "PersonInfoModelImp";

    /**
     * 提交用户个人的修改信息
     *
     * @param path
     * @param resultListener
     */
    @Override
    public void submitData(String path, ResultListener resultListener) {
        NetworkUtil.getInstance().get(ApiUtil.URL_HOST_TWO + path , resultListener);
    }

    /**
     * 获取用户的个人信息
     */
    @Override
    public void getPersonInfoData(ResultListener resultListener) {
        try {
            String url = ApiUtil.URL_GET_PERSON_INFO +
                    "?xh=" + ApiUtil.USER_NUMBER +
                    "&xm=" + URLEncoder.encode(ApiUtil.USER_NAME, NetworkConnection.STRING_CODE) +
                    "&gnmkdm=N121501";
            Log.e(TAG, "getPersonInfoData:  " + url);
            NetworkUtil.getInstance().get(url, resultListener);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户的头像
     */
    @Override
    public void getUserImageHead(ByteListener byteListener) {
        NetworkUtil.getInstance().get(ApiUtil.URL_GET_IMAGE_HEAD + ApiUtil.USER_NUMBER, byteListener);
    }
}
