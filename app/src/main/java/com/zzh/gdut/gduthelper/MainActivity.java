package com.zzh.gdut.gduthelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zzh.gdut.gduthelper.networkutil.NetworkUtil;
import com.zzh.gdut.gduthelper.networkutil.PostBody;
import com.zzh.gdut.gduthelper.networkutil.callback.ByteListener;
import com.zzh.gdut.gduthelper.networkutil.callback.ProgressListener;
import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageView imgCode;
    private EditText et;
    private String sessionId;
    private Bitmap bitmap;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            imgCode.setImageBitmap(bitmap);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgCode = (ImageView) findViewById(R.id.img_code);
        et = (EditText) findViewById(R.id.et);
        Log.e(TAG, "onCreate: ");
    }

    public void getCode(View view) {
       /* new Thread() {
            @Override
            public void run() {
                super.run();
                getImageCode();
            }
        }.start();*/
        Log.e(TAG, "getCode: ");
        PostBody postBody = new PostBody.Builder().addParams("userId", "1").addParams("password", "111111").build();
        NetworkUtil.getInstance().post("http://115.28.64.167/spg/user/login", postBody, new ResultListener() {
            @Override
            public void onResultSuccess(String success) {
                Log.e(TAG, "onResultSuccess: " + success);
            }

            @Override
            public void onResultFail(String fail) {
                Log.e(TAG, "onResultFail: " + fail);
            }
        });
        /*NetworkConnection connection = new NetworkConnection.Builder().doInput(true).doOutput(false).useCaches(false).cookieManager(new CookieManager()).connectTimeOut(5000).readTimeOut(5000).build();
        PostBody postBody = new PostBody.Builder().build();
        connection.post("http://115.28.64.167/spg/user/login", postBody, new ResultListener() {
            @Override
            public void onResultSuccess(String success) {
                Log.e(TAG, "onResultSuccess: " + success );
            }

            @Override
            public void onResultFail(String fail) {

            }
        });*/
    }

    private void getImageCode() {
        URL url = null;
        try {
            url = new URL("http://jwgl.gdut.edu.cn/CheckCode.aspx");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            sessionId = connection.getHeaderField("Set-Cookie");
            //截取sessionId字段
            sessionId = sessionId.substring(0, sessionId.indexOf(";"));
            Log.e(TAG, "getImageCode: " + sessionId);
            connection.connect();
            InputStream is = connection.getInputStream();   //获取输入流，此时才真正建立链接
            bitmap = BitmapFactory.decodeStream(is);
            handler.sendEmptyMessage(0x123);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login(View view) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                login();
            }
        }.start();
    }

    private void login() {
        URL url = null;
        String resultData = "";
        try {
            Log.e(TAG, "login: 1");
            url = new URL("http://jwgl.gdut.edu.cn/default2.aspx");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.e(TAG, "login: 2");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Cookie", sessionId);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            //发送参数
            OutputStream out = conn.getOutputStream();
            Log.e(TAG, "login: 3");
            StringBuffer sbParams = new StringBuffer();
            sbParams.append("__VIEWSTATE=").append(URLEncoder.encode("dDwyODE2NTM0OTg7Oz4I/BV0f3bU3Jxu0+rIKYuntoTTNg==", "GBK"));
            sbParams.append("&txtUserName=").append("3114005890");
            sbParams.append("&TextBox2=").append("a6585086s");
            sbParams.append("&txtSecretCode=").append(et.getText().toString());
            sbParams.append("&RadioButtonList1=").append(URLEncoder.encode("学生", "GBK"));
            sbParams.append("&Button1=").append("");
            sbParams.append("&lbLanguage=").append("");
            sbParams.append("&hidPdrs=").append("");
            sbParams.append("&hidsc=").append("");
            out.write(sbParams.toString().getBytes());
            out.flush();
            out.close();

            InputStream is = conn.getInputStream();   //获取输入流，此时才真正建立链接
            InputStreamReader isr = new InputStreamReader(is, "GBK");
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine = "";
            Log.e(TAG, "login: location" + conn.getHeaderField("Location"));
            while ((inputLine = bufferReader.readLine()) != null) {
                resultData += inputLine + "\n";
            }
            is.close();
            Log.e(TAG, "getURLResponse: " + resultData);
        } catch (Exception e) {
        }
    }


    /**
     * 获取指定URL的响应字符串
     *
     * @param urlString
     * @return
     */
    private String getURLResponse(String urlString) {
        HttpURLConnection conn = null; //连接对象
        InputStream is = null;
        String resultData = "";
        try {
            URL url = new URL(urlString); //URL对象
            conn = (HttpURLConnection) url.openConnection(); //使用URL打开一个链接
            conn.setDoInput(true); //允许输入流，即允许下载
            conn.setDoOutput(true); //允许输出流，即允许上传
            conn.setUseCaches(false); //不使用缓冲
            conn.setRequestMethod("GET"); //使用get请求
            conn.setRequestProperty("Cookie", sessionId);
            conn.setRequestProperty("HOST", "jwgl.gdut.edu.cn");
            conn.setRequestProperty("Referer", "http://jwgl.gdut.edu.cn/xs_main.aspx?xh=3114005890");

            is = conn.getInputStream();   //获取输入流，此时才真正建立链接
            InputStreamReader isr = new InputStreamReader(is, "GBK");
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine = "";
            while ((inputLine = bufferReader.readLine()) != null) {
                resultData += inputLine + "\n";
            }
            Log.e(TAG, "getURLResponse: " + resultData);

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return resultData;
    }

    public void get(View view) {
        NetworkUtil.getInstance().get("http://img.my.csdn.net/uploads/201609/04/1472992660_6905.jpg", new ByteListener() {
            @Override
            public void setBytesSuccess(byte[] bytes) {
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                handler.sendEmptyMessage(0x123);
            }

            @Override
            public void setBytesFail(String fail) {

            }
        }, new ProgressListener() {
            @Override
            public void onUpdate(long bytesRead, long contentLength, boolean done) {
                Log.e(TAG, "onUpdate: " + bytesRead + ">>" + contentLength + ">>" + done);
                Log.e(TAG, "progress: " + (bytesRead * 100) / contentLength);
            }
        });
   /*     new Thread() {
            @Override
            public void run() {
                super.run();
                getURLResponse("http://jwgl.gdut.edu.cn/xs_main.aspx?xh=3114005890");
            }
        }.start();*/
    }
}
