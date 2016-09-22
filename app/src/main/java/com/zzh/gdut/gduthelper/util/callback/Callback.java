package com.zzh.gdut.gduthelper.util.callback;

import com.zzh.gdut.gduthelper.util.PostBody;

/**
 * Created by ZengZeHong on 2016/9/22.
 */

public interface Callback {
    void get(String url, ResultListener resultListener);

    void get(String url, ByteListener imageListener);

    void post(String url, PostBody postBody, ResultListener resultListener);

    void post(String url, PostBody postBody, ByteListener resultListener);

}
