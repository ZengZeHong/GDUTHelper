package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseActivity;
import com.zzh.gdut.gduthelper.presenter.LoginPresenter;
import com.zzh.gdut.gduthelper.view.vinterface.LoginInterface;

/**
 * Created by ZengZeHong on 2016/9/24.
 */

public class GDUTLoginActivity extends BaseActivity<LoginInterface, LoginPresenter> implements LoginInterface {
    private static final String TAG = "GDUTLoginActivity";
    private EditText et;
    private Bitmap bitmap;
    private ImageView image;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123)
                image.setImageBitmap(bitmap);
        }
    };

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gdut_login;
    }

    @Override
    protected void initViews() {
        showToolbarAndShowNavigation("LoginActivity");
        et = (EditText) findViewById(R.id.et);
        image = (ImageView) findViewById(R.id.image);
    }

    @Override
    protected void initAttributes() {

    }

    @Override
    protected void getIntentData(Intent intent) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void loginSuccess(String result) {
        Log.e(TAG, "loginSuccess: " + result);

    }

    @Override
    public void loginFail(String fail) {
        Log.e(TAG, "loginFail: " + fail);
    }

    @Override
    public void getImageCodeSuccess(byte[] bytes) {
        Log.e(TAG, "getImageCodeSuccess: " + bytes.length );
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        handler.sendEmptyMessage(0x123);
    }

    @Override
    public void getImageCodeFail(String fail) {
        Log.e(TAG, "getImageCodeFail: " + fail );
    }

    @Override
    public void getInfoSuccess(String result) {
        Log.e(TAG, "getInfoSuccess: " + result );
    }

    @Override
    public void getInfoFail(String fail) {
        Log.e(TAG, "getInfoFail: " + fail );
    }

    public void first(View view) {
        mPresenter.login("3114005890", "a6585086", et.getText().toString());
    }

    public void image(View view) {
        mPresenter.getImageCode();
    }

    public void second(View view) {
        mPresenter.getInfo("xs_main.aspx?xh=3114005890");
    }
}
