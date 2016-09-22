package com.zzh.gdut.gduthelper.util;

/**
 * Created by ZengZeHong on 2016/9/21.
 * 网络请求类，对NetworkConnection进行二次封装
 */

public class NetworkUtil {
    private static NetworkUtil instance = null;

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

}
