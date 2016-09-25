package com.zzh.gdut.gduthelper.util;


/**
 * Created by ZengZeHong on 2016/7/15.
 */

public class AppConstants {
    private static final String TAG = "AppConstants";
    public static final String KEY_COOKIE = "GDUT";
    public static final int OPEN_OVERPENDINGTRANSITION = 0;
    public static final int OUT_OVERPENDINGTRANSITION = 1;
    //登陆者的学号
    public static final String USER_NUMBER = null;
    //教务处主页
    public static final String URL_HOST_ONE = "http://jwgl.gdut.edu.cn/";
    public static final String URL_HOST_TWO = "http://jwgldx.gdut.edu.cn/";
    //获取验证码
    public static final String URL_LOGIN_IMAGE_CODE = URL_HOST_TWO + "CheckCode.aspx?";
    //等一次录入登陆信息 post='/zdy.htm?aspxerrorpath=/default2.aspx
    public static final String URL_LOGIN_FIRST = URL_HOST_TWO + "default2.aspx";
    //第二次登陆 get 后面加入对应的路径，由上一次登陆得来的结果录入 + 3114005890
    public static final String URL_LOGIN_SECOND = URL_HOST_TWO + "xs_main.aspx?xh=";
}
