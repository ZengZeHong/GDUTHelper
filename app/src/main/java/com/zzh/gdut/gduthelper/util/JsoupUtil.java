package com.zzh.gdut.gduthelper.util;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by ZengZeHong on 2016/9/26.
 * Jsoup工具类
 */

public class JsoupUtil {
    private static final String TAG = "JsoupUtil";

    /**
     * 获取网页上ViewState状态
     *
     * @return
     */
    public static String getViewState() {
        try {
            Document document = Jsoup.connect(ApiUtil.URL_HOST_TWO).get();
            Elements elements = document.getElementsByTag("input");
            for (Element element : elements) {
                if (element.attr("name").equals("__VIEWSTATE")) {
                    Log.e(TAG, "getViewState: " + element.attr("value"));
                    return element.attr("value");
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 登陆成功后解析出重定向的地址
     *
     * @param result
     * @return
     */
    public static String getLoginHref(String result) {
      /*  String html = "<html><head><title>Object moved</title></head>"
                + "<body><h2>Object moved to <a href='/xs_main.aspx?xh=3114005890'>here</a>.</h2>"
                + "</body></html>";*/
        Document document = Jsoup.parse(result);
        Elements elements = document.getElementsByTag("a");
        for (Element element : elements) {
            Log.e(TAG, "getLoginHref: " + element.attr("href"));
            return element.attr("href");
        }
        return null;
    }

    /**
     * 登陆失败，解析出指定错误信息
     *
     * @param result
     * @return
     */
    public static String getLoginError(String result) {
        Document document = Jsoup.parse(result);
        Elements elements = document.getElementsByTag("script");
        Log.e(TAG, "getLoginError: elements " + elements.size());
        for (Element element : elements) {
            Log.e(TAG, "getLoginError: data " + element.data());
        }
        if (elements.get(1) != null) {
            Element element = elements.get(1);
            String data = element.data();
            if (data.contains("验证码不正确"))
                return "验证码错误，请重新输入";
            else if (data.contains("密码错误"))
                return "密码错误，请重新输入";
            else if (data.contains("用户名不存在或未按照要求参加教学活动"))
                return "当前学号不存在";
        }
        return null;
    }

    /**
     * 获取当前学号对应同学的名字
     * @param result
     */
    public static void getUserName(String result) {
        Document document = Jsoup.parse(result);
        Elements elements = document.getElementsByTag("span");
        String data = elements.get(1).data();
        if (data.contains("同学")) {
            data = data.substring(0, data.indexOf("同学"));
            Log.e(TAG, "getUserName: " + data);
            ApiUtil.USER_NAME = data;
        }

    }
}
