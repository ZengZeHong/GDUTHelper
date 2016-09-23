package com.zzh.gdut.gduthelper.util;

import android.util.Log;

import java.net.HttpURLConnection;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ZengZeHong on 2016/9/23.
 * 监听器监听网络连接情况，超过指定时间则按超时处理
 */

public class ConnectionTimer {
    private static final String TAG = "ConnectionTimer";
    private Timer timer;
    private static final int OVER_TIME = 10; //5秒超时
    private int currentTime = 0;
    private HttpURLConnection httpURLConnection;

    public ConnectionTimer(HttpURLConnection httpURLConnection) {
        this.httpURLConnection = httpURLConnection;
    }

    /**
     * 重新定时置为
     */
    public void reset() {
        currentTime = 0;
    }

    /**
     * 开始定时
     */
    public void start() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentTime++;
                Log.e(TAG, "run: " + currentTime);
                if (currentTime == 10) {
                    httpURLConnection.disconnect();
                    timer.cancel();
                }
            }
        }, 0 , 1000);
    }
}
