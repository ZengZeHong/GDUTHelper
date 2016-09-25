package com.zzh.gdut.gduthelper.networkutil;

import com.zzh.gdut.gduthelper.util.AppConstants;

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by ZengZeHong on 2016/9/23.
 * 抽象CookieJar，二次封装CookieManager，作为静态类使用
 */

public abstract class CookieJar extends CookieManager {
    private static final String TAG = "CookieJar";

    //设置Cookie
    abstract void setCookies(String url, HttpURLConnection httpURLConnection);

    //获取Cookie
    abstract List<HttpCookie> getCookies(String url);

    /**
     * 判断当前指定的url已经获取过Cookie
     *
     * @param url
     * @return
     */
    public boolean isCookieStoreNull(String url) {
        CookieStore cookieStore = getCookieStore();
        try {
            if (cookieStore.get(new URI(AppConstants.URL_HOST_TWO)).size() == 0)
                return true;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }
}
