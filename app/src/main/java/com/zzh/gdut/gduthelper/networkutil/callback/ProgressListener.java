package com.zzh.gdut.gduthelper.networkutil.callback;

/**
 * Created by ZengZeHong on 2016/9/23.
 * 进度接口
 */

public interface ProgressListener {
    void onUpdate(long bytesRead, long contentLength, boolean done);
}
