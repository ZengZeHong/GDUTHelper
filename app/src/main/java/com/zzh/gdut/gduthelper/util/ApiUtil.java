package com.zzh.gdut.gduthelper.util;

/**
 * Created by ZengZeHong on 2016/9/26.
 */

public class ApiUtil {
    //登陆后类似token的令牌信息 eg :
    public static String LOGIN_TOKEN = null;
    //TODO 要存进数据库读取，不然内存回收会crash，所有静态变量会被重置
    //登陆成功的学号
    public static String USER_NUMBER = null;
    //用户名字eg:曾泽洪
    public static String USER_NAME = null;
    //教务处主页
    public static final String URL_HOST_ONE = "http://jwgl.gdut.edu.cn";
    public static final String HOST = "jwgldx.gdut.edu.cn";
    public static final String URL_HOST_TWO = "http://jwgldx.gdut.edu.cn";
    public static final String VIEWSTATE = "dDwyODE2NTM0OTg7Oz7QqY3yg91iEh+CrEbxxVUHRHuTxg==";
    //每个功能上的附加连接地址
    //个人信息
    public static final String URL_HREF_PERSON_INFO = "xsgrxx.aspx?xh=3114005890&xm=曾泽洪&gnmkdm=N121501";
    //修改密码
    public static final String URL_HREF_MODIFY_PASSWORD = "mmxg.aspx?xh=3114005890&gnmkdm=N121502";
    //学生个人课表
    public static final String URL_HREF_PERSON_CLASS = URL_HOST_TWO + "/xskbcx.aspx";
    //考试查询
    public static final String URL_HREF_SEARCH_EXAM = URL_HOST_TWO + "/xskscx.aspx";
    //成绩查询
    public static final String URL_HREF_SEARCH_SCORE = URL_HOST_TWO + "/xscj.aspx";
    //密码修改
    public static final String URL_HREF_CHANGE_PASSWORD = URL_HOST_TWO + "/mmxg.aspx";
    //教室查询
    public static final String URL_HREF_SEARCH_CLASS = "xxjsjy.aspx?xh=3114005890&xm=曾泽洪&gnmkdm=N121611";
    //教学评价 #a
    public static final String URL_HREF_TEACHING_EVALUATION = "#a";
    //获取验证码
    public static final String URL_LOGIN_IMAGE_CODE = URL_HOST_TWO + "/CheckCode.aspx?";
    //等一次录入登陆信息 post='/zdy.htm?aspxerrorpath=/default2.aspx
    public static final String URL_LOGIN_FIRST = URL_HOST_TWO + "/default2.aspx";
    //第二次登陆 get 后面加入对应的路径，由上一次登陆得来的结果录入 + 3114005890
    public static final String URL_LOGIN_SECOND = URL_HOST_TWO + "/xs_main.aspx?xh=3114005890";
    //获取个人信息 后面参数 ?xh=3114005890&xm曾泽洪&gnmkdm=N121501
    public static final String URL_GET_PERSON_INFO = URL_HOST_TWO + "/xsgrxx.aspx";
    //获取个人头像 后面参数 ?xh=3114005890 加个学号就行
    public static final String URL_GET_IMAGE_HEAD = URL_HOST_TWO + "/readimagexs.aspx?xh=";
}
