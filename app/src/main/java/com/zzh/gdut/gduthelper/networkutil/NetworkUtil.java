package com.zzh.gdut.gduthelper.networkutil;

import android.util.Log;

import com.zzh.gdut.gduthelper.networkutil.callback.ByteListener;
import com.zzh.gdut.gduthelper.networkutil.callback.ProgressListener;
import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;
import com.zzh.gdut.gduthelper.util.ApiUtil;

import java.io.IOException;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * Created by ZengZeHong on 2016/9/21.
 * 网络请求类，对NetworkConnection进行二次封装
 */

public class NetworkUtil {
    private static final String TAG = "NetworkUtil";
    private static NetworkUtil instance = null;
    //Cookie自动管理
    private static CookieJar cookieJar = new CookieJar() {
        /** 保存Cookie
         * @param url
         * @param httpURLConnection
         */
        @Override
        void setCookies(String url, HttpURLConnection httpURLConnection) {
            try {
                URI uri = new URI(new URL(url).getHost());
                Log.e(TAG, "run: uri " + new URL(url).getHost());
                this.put(new URI(ApiUtil.URL_HOST_TWO), httpURLConnection.getHeaderFields());
                CookieStore cookieStore = this.getCookieStore();
                Log.e(TAG, "run: store size " + cookieStore.getCookies().size());
                for (HttpCookie httpCookie : cookieStore.getCookies()) {
                    Log.e(TAG, "httpCookie: domain  " + httpCookie.getDomain());
                    Log.e(TAG, "httpCookie: value  " + httpCookie.getValue());
                    Log.e(TAG, "httpCookie: path  " + httpCookie.getPath());
                    Log.e(TAG, "httpCookie: name  " + httpCookie.getName());
                    Log.e(TAG, "httpCookie: toString  " + httpCookie.toString());
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 获取Cookie
         * @param url
         * @return
         */
        @Override
        List<HttpCookie> getCookies(String url) {
            //获取之前CookieManager保存的Cookie;
            CookieStore cookieStore = getCookieStore();
            try {
                Log.e(TAG, "getCookies: " + new URL(url).getHost());
                return cookieStore.get(new URI(ApiUtil.URL_HOST_TWO));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }
    };

    /**
     * 获取单例
     *
     * @return
     */
    public static NetworkUtil getInstance() {
        if (instance == null) {
            synchronized (NetworkUtil.class) {
                if (instance == null) {
                    instance = new NetworkUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 普通的Post请求
     *
     * @param url
     * @param postBody
     * @param resultListener
     */
    public void post(final String url, final PostBody postBody, final ResultListener resultListener) {
        NetworkConnection.Builder builder = new NetworkConnection.Builder().doInput(true).doOutput(true).useCaches(false).connectTimeOut(10000).readTimeOut(10000);
        if (cookieJar != null)
            builder.cookieJar(cookieJar);
        NetworkConnection connection = builder.build();
        connection.post(url, postBody, resultListener);
    }

    /**
     * 发送请求表单
     *
     * @param url
     * @param postBody
     * @param resultListener
     */
    public void postMultiPart(final String url, final PostBody postBody, ResultListener resultListener) {
        NetworkConnection.Builder builder = new NetworkConnection.Builder().
                addHeader("Accept-Charset", NetworkConnection.STRING_CODE).
                addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4").
                addHeader("HOST", ApiUtil.URL_HOST_TWO).
                addHeader("Origin", ApiUtil.URL_HOST_TWO).
                addHeader("Referer", url).
                doInput(true).
                doOutput(true).
                useCaches(false).
                connectTimeOut(10000).
                readTimeOut(10000);
        if (cookieJar != null)
            builder.cookieJar(cookieJar);
        NetworkConnection connection = builder.build();
        connection.postMultiPart(url, postBody, resultListener);
    }

    /**
     * 普通的Post请求
     *
     * @param url
     * @param postBody
     * @param byteListener
     */
    public void post(final String url, final PostBody postBody, final ByteListener byteListener) {
        NetworkConnection.Builder builder = new NetworkConnection.Builder().doInput(true).doOutput(true).useCaches(false).connectTimeOut(10000).readTimeOut(10000);
        if (cookieJar != null)
            builder.cookieJar(cookieJar);
        NetworkConnection connection = builder.build();
        connection.post(url, postBody, byteListener);
    }

    /**
     * 带有进度的下载文件Post请求
     *
     * @param url
     * @param postBody
     * @param byteListener     文件的byte形式
     * @param progressListener 下载进度
     */
    public void post(final String url, final PostBody postBody, final ByteListener byteListener, ProgressListener progressListener) {
        NetworkConnection.Builder builder = new NetworkConnection.Builder().doInput(true).doOutput(true).useCaches(false).connectTimeOut(10000).readTimeOut(10000);
        if (cookieJar != null)
            builder.cookieJar(cookieJar);
        NetworkConnection connection = builder.build();
        connection.post(url, postBody, byteListener);
        connection.setProgressListener(progressListener);
    }

    /**
     * 普通get请求
     * 教务系统登陆后，后续操作都需要设置Reference 和 Host
     *
     * @param url
     * @param resultListener
     */
    public void get(final String url, final ResultListener resultListener) {
        NetworkConnection.Builder builder = new NetworkConnection.Builder().addHeader("HOST", ApiUtil.HOST).addHeader("Referer", ApiUtil.URL_HOST_TWO).doInput(true).doOutput(false).useCaches(false).connectTimeOut(10000).readTimeOut(10000);
        if (cookieJar != null)
            builder.cookieJar(cookieJar);
        NetworkConnection connection = builder.build();
        connection.get(url, resultListener);
    }

    /**
     * 下载文件get请求，比如加载图片
     *
     * @param url
     * @param byteListener
     */
    public void get(final String url, final ByteListener byteListener) {
        NetworkConnection.Builder builder = new NetworkConnection.Builder().doInput(true).doOutput(false).useCaches(false).connectTimeOut(10000).readTimeOut(10000);
        if (cookieJar != null)
            builder.cookieJar(cookieJar);
        NetworkConnection connection = builder.build();
        connection.get(url, byteListener);
    }

    /**
     * 带有进度的get请求，可以用来下载文件图片
     *
     * @param url
     * @param byteListener     文件的byte信息
     * @param progressListener 下载进度监听
     */
    public void get(final String url, final ByteListener byteListener, ProgressListener progressListener) {
        NetworkConnection.Builder builder = new NetworkConnection.Builder().doInput(true).doOutput(false).useCaches(false).connectTimeOut(10000).readTimeOut(10000);
        if (cookieJar != null)
            builder.cookieJar(cookieJar);
        NetworkConnection connection = builder.build();
        connection.get(url, byteListener);
        connection.setProgressListener(progressListener);
    }


}
