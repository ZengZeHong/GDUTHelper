package com.zzh.gdut.gduthelper.util;

import android.util.Log;

import com.zzh.gdut.gduthelper.util.callback.Callback;
import com.zzh.gdut.gduthelper.util.callback.ByteListener;
import com.zzh.gdut.gduthelper.util.callback.ResultListener;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZengZeHong on 2016/9/22.
 */

public class NetworkConnection implements Callback {
    private static final String TAG = "NetworkConnection";
    //只列出一些常用的功能
    private int connectTimeOut;
    private int readTimeOut;
    private boolean doInput;
    private boolean doOutput;
    private boolean useCaches;
    //请求头
    private List<String> keys = new ArrayList<>();
    private List<String> values = new ArrayList<>();
    public NetworkConnection(int connectTimeOut, int readTimeOut, boolean doInput, boolean doOutput, boolean useCaches, List<String> keys, List<String> values) {
        this.connectTimeOut = connectTimeOut;
        this.readTimeOut = readTimeOut;
        this.doInput = doInput;
        this.doOutput = doOutput;
        this.useCaches = useCaches;
        this.keys = keys;
        this.values = values;
    }

    /**
     * 创建一个通用的HttpURLConnection
     *
     * @param imgUrl 请求地址
     * @param method Get,Post方法
     * @return
     * @throws IOException
     */
    private HttpURLConnection createConnection(String imgUrl, String method) throws IOException {
        URL url = new URL(imgUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(method);
        if (connectTimeOut != -1)
            httpURLConnection.setConnectTimeout(connectTimeOut);
        if (readTimeOut != -1)
            httpURLConnection.setReadTimeout(readTimeOut);
        //默认要true,false,false
        httpURLConnection.setDoInput(doInput);
        httpURLConnection.setDoOutput(doOutput);
        httpURLConnection.setUseCaches(useCaches);
        //添加请求头
        for (int i = 0; i < keys.size(); i++) {
            httpURLConnection.addRequestProperty(keys.get(i), values.get(i));
        }
        return httpURLConnection;
    }

    /**
     * 普通的文本请求
     *
     * @param url            请求地址
     * @param resultListener 接口监听
     */
    @Override
    public void get(final String url, final ResultListener resultListener) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.e(TAG, "run: ");
                InputStream in = null;
                HttpURLConnection httpURLConnection = null;
                try {
                    httpURLConnection = createConnection(url, "GET");
                    httpURLConnection.connect();
                    Log.e(TAG, "get: getResponseCode " + httpURLConnection.getResponseCode());
                    Log.e(TAG, "get: getResponseMessage " + httpURLConnection.getResponseMessage());
                    in = httpURLConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String inputLine = "";
                    StringBuffer sb = new StringBuffer();
                    while ((inputLine = br.readLine()) != null) {
                        sb.append(inputLine).append("\n");
                    }
                    resultListener.onResultSuccess(sb.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.e(TAG, "throw MalformedURLException");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "throw IOException");
                } finally {
                    try {
                        if (in != null)
                            in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (httpURLConnection != null)
                        httpURLConnection.disconnect();
                }
            }
        }.start();
    }

    /**
     * 请求数据流，比如获取图片，以数组形式传递
     *
     * @param url          请求地址
     * @param byteListener 数据监听
     */
    @Override
    public void get(final String url, final ByteListener byteListener) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.e(TAG, "run: ");
                InputStream in = null;
                HttpURLConnection httpURLConnection = null;
                try {
                    httpURLConnection = createConnection(url, "GET");
                    httpURLConnection.connect();
                    Log.e(TAG, "get: getResponseCode " + httpURLConnection.getResponseCode());
                    Log.e(TAG, "get: getResponseMessage " + httpURLConnection.getResponseMessage());
                    //下载成功
                    if (httpURLConnection.getResponseCode() == 200) {
                        in = httpURLConnection.getInputStream();
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        byte[] buff = new byte[1024];
                        int len;
                        while ((len = in.read(buff)) != -1) {
                            out.write(buff, 0, len);
                        }
                        out.flush();
                        out.close();
                        Log.e(TAG, "run: get image");
                        byteListener.setBytesSuccess(out.toByteArray());
                    } else
                        //下载失败
                        byteListener.setBytesFail(httpURLConnection.getResponseCode() + ">>" + httpURLConnection.getResponseMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (in != null)
                            in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (httpURLConnection != null)
                        httpURLConnection.disconnect();
                }
            }
        }.start();
    }

    /**
     * 普通post请求，只支持存文本
     *
     * @param url            请求地址
     * @param postBody       请求参数体
     * @param resultListener 返回监听
     */
    @Override
    public void post(final String url, final PostBody postBody, final ResultListener resultListener) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.e(TAG, "run: ");
                InputStream in = null;
                HttpURLConnection httpURLConnection = null;
                try {
                    httpURLConnection = createConnection(url, "POST");
                    Log.e(TAG, "run: size" + postBody.size());
                    if (postBody.size() != 0) {
                        //写入请求参数
                        OutputStream out = httpURLConnection.getOutputStream();
                        StringBuffer sbParams = new StringBuffer();
                        sbParams.append(postBody.getKeys().get(0) + "=").append(postBody.getValues().get(0));
                        for (int i = 1; i < postBody.size(); i++) {
                            sbParams.append("&" + postBody.getKeys().get(i) + "=").append(postBody.getValues().get(i));
                        }
                        out.write(sbParams.toString().getBytes());
                        out.flush();
                        out.close();
                    }
                    Log.e(TAG, "post: getResponseCode " + httpURLConnection.getResponseCode());
                    Log.e(TAG, "post: getResponseMessage " + httpURLConnection.getResponseMessage());
                    //成功响应
                    if (httpURLConnection.getResponseCode() == 200) {
                        in = httpURLConnection.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        String inputLine = "";
                        StringBuffer sb = new StringBuffer();
                        while ((inputLine = br.readLine()) != null) {
                            sb.append(inputLine).append("\n");
                        }
                        resultListener.onResultSuccess(sb.toString());
                    } else {
                        resultListener.onResultFail(httpURLConnection.getContentEncoding() + ">>" + httpURLConnection.getResponseMessage());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.e(TAG, "throw MalformedURLException");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "throw IOException");

                } finally {
                    try {
                        if (in != null)
                            in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (httpURLConnection != null)
                        httpURLConnection.disconnect();
                }
            }
        }.start();
    }

    /**
     * 请求数据流，比如获取图片，以数组形式传递
     *
     * @param url          请求地址
     * @param postBody     请求参数提
     * @param byteListener 返回监听
     */
    @Override
    public void post(final String url, final PostBody postBody, final ByteListener byteListener) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.e(TAG, "run: ");
                InputStream in = null;
                HttpURLConnection httpURLConnection = null;
                try {
                    httpURLConnection = createConnection(url, "POST");
                    //写入请求参数
                    if (postBody.size() != 0) {
                        OutputStream out = httpURLConnection.getOutputStream();
                        StringBuffer sbParams = new StringBuffer();
                        sbParams.append(postBody.getKeys().get(0) + "=").append(postBody.getValues().get(0));
                        for (int i = 1; i < postBody.size(); i++) {
                            sbParams.append("&" + postBody.getKeys().get(i) + "=").append(postBody.getValues().get(i));
                        }
                        out.write(sbParams.toString().getBytes());
                        out.flush();
                        out.close();
                    }
                    Log.e(TAG, "get: getResponseCode " + httpURLConnection.getResponseCode());
                    Log.e(TAG, "get: getResponseMessage " + httpURLConnection.getResponseMessage());
                    if (httpURLConnection.getResponseCode() == 200) {
                        in = httpURLConnection.getInputStream();
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        byte[] buff = new byte[1024];
                        int len;
                        while ((len = in.read(buff)) != -1) {
                            out.write(buff, 0, len);
                        }
                        out.flush();
                        out.close();
                        Log.e(TAG, "run: get image");
                        byteListener.setBytesSuccess(out.toByteArray());
                    } else
                        //失败
                        byteListener.setBytesFail(httpURLConnection.getResponseCode() + ">>" + httpURLConnection.getResponseMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (in != null)
                            in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (httpURLConnection != null)
                        httpURLConnection.disconnect();
                }
            }
        }.start();
    }

    public static final class Builder {
        //只列出一些常用的功能，默认如下
        private int connectTimeOut = -1;
        private int readTimeOut = -1;
        private boolean doInput = true;
        private boolean doOutput = false;
        private boolean useCaches = false;
        //请求头
        private final List<String> keys = new ArrayList<>();
        private final List<String> values = new ArrayList<>();

        public Builder() {
        }

        public Builder addHeader(String key, String value) {
            if (key == null || value == null)
                throw new NullPointerException();
            if (key != null && value != null) {
                keys.add(key);
                values.add(value);
            }
            return this;
        }

        public Builder connectTimeOut(int connectTimeOut) {
            this.connectTimeOut = connectTimeOut;
            return this;
        }

        public Builder readTimeOut(int readTimeOut) {
            this.readTimeOut = readTimeOut;
            return this;
        }

        public Builder doInput(boolean doInput) {
            this.doInput = doInput;
            return this;
        }

        public Builder doOutput(boolean doOutput) {
            this.doOutput = doOutput;
            return this;
        }

        public Builder useCaches(boolean useCaches) {
            this.useCaches = useCaches;
            return this;
        }

        public NetworkConnection build() {
            return new NetworkConnection(connectTimeOut, readTimeOut, doInput, doOutput, useCaches, keys, values);
        }
    }
}
