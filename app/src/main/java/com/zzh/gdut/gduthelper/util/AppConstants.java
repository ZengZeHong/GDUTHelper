package com.zzh.gdut.gduthelper.util;


/**
 * Created by ZengZeHong on 2016/7/15.
 */

public class AppConstants {
    private static final String TAG = "AppConstants";

    public static final int OPEN_OVERPENDINGTRANSITION = 0;
    public static final int OUT_OVERPENDINGTRANSITION = 1;

    //获取验证码
    public static final String URL_LOGIN_IMAGE_CODE = "http://jwgldx.gdut.edu.cn/CheckCode.aspx?";
    //教务处主页
    public static final String URL_HOST = "http://jwgl.gdut.edu.cn/";
    //等一次录入登陆信息 post
    public static final String URL_LOGIN_FIRST = URL_HOST + "default2.aspx";
    //第二次登陆 get 后面加入对应的路径，由上一次登陆得来的结果录入
    public static final String URL_LOGIN_SECOND = URL_HOST + "xs_main.aspx?xh=3114005890";
}
