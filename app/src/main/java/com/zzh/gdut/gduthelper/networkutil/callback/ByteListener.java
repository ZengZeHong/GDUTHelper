package com.zzh.gdut.gduthelper.networkutil.callback;

/**
 * Created by ZengZeHong on 2016/9/22.
 */

public interface ByteListener {
    void setBytesSuccess(byte[] bytes);

    void setBytesFail(String fail);
}
