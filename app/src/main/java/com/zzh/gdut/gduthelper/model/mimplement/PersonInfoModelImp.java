package com.zzh.gdut.gduthelper.model.mimplement;

import android.util.Log;

import com.zzh.gdut.gduthelper.bean.PersonInfo;
import com.zzh.gdut.gduthelper.model.minterface.PersonInfoModel;
import com.zzh.gdut.gduthelper.networkutil.NetworkConnection;
import com.zzh.gdut.gduthelper.networkutil.NetworkUtil;
import com.zzh.gdut.gduthelper.networkutil.PostBody;
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
     * @param resultListener
     */
    @Override
    public void submitData(ResultListener resultListener, PersonInfo personInfo) {
        try {
            String url = ApiUtil.URL_GET_PERSON_INFO +
                    "?xh=" + ApiUtil.USER_NUMBER +
                    "&xm=" + URLEncoder.encode(ApiUtil.USER_NAME, NetworkConnection.STRING_CODE) +
                    "&gnmkdm=N121501";
            PostBody postBody = new PostBody.Builder().
                    addParams("TELNUMBER", personInfo.getPhoneNumber()).
                    addParams("jtyb", personInfo.getFamilyCode()). //家庭邮编
                    addParams("jtdh", personInfo.getFamilyPhoneNumber()). //家庭电话
                    addParams("byzx", URLEncoder.encode(personInfo.getGradurate(), NetworkConnection.STRING_CODE)).  //毕业中学
                    addParams("fqxm", URLEncoder.encode(personInfo.getFather(), NetworkConnection.STRING_CODE)).  //父亲姓名
                    addParams("ssh", URLEncoder.encode(personInfo.getDormitory(), NetworkConnection.STRING_CODE)). //宿舍号
                    addParams("fqdw", URLEncoder.encode(personInfo.getFatherWork(), NetworkConnection.STRING_CODE)). //父亲单位
                    addParams("txtjg", URLEncoder.encode(personInfo.getNativePlace(), NetworkConnection.STRING_CODE)). //籍贯
                    addParams("jg", URLEncoder.encode(personInfo.getNativePlace(), NetworkConnection.STRING_CODE)).
                    addParams("dzyxdz", personInfo.getEmail()).  //电子邮箱
                    addParams("fqdwyb", personInfo.getFatherWorkCode()). //父亲单位邮编
                    addParams("zzmm", URLEncoder.encode(personInfo.getPolity(), NetworkConnection.STRING_CODE)). //政治面貌
                    addParams("lxdh", personInfo.getContractPhoneNumber()). //联系电话
                    addParams("mqxm", URLEncoder.encode(personInfo.getMother(), NetworkConnection.STRING_CODE)). //母亲姓名
                    addParams("yzbm", personInfo.getPostCode()). //邮政编码
                    addParams("mqdw", URLEncoder.encode(personInfo.getMotherWork(), NetworkConnection.STRING_CODE)). //母亲单位
                    addParams("fqdwdh", personInfo.getFatherPhoneNumber()). //父亲单位电话
                    addParams("jkzk", URLEncoder.encode(personInfo.getHeathy(), NetworkConnection.STRING_CODE)). //健康状况
                    addParams("mqdwdh", personInfo.getMothePhoneNumber()). //母亲单位电话
                    addParams("jtdz", URLEncoder.encode(personInfo.getAddress(), NetworkConnection.STRING_CODE)). //家庭地址
                    addParams("jtszd", URLEncoder.encode(personInfo.getFamilyLocation(), NetworkConnection.STRING_CODE)). //家庭所在地
                    addParams("RDSJ", personInfo.getTimeToPolity()). //入党时间
                    addParams("ccqj", URLEncoder.encode(personInfo.getStationEnd(), NetworkConnection.STRING_CODE)). //火车终点站
                    addParams("Button1", URLEncoder.encode("提 交", NetworkConnection.STRING_CODE)).
                    build();
              NetworkUtil.getInstance().postMultiPart(url , postBody , resultListener);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
