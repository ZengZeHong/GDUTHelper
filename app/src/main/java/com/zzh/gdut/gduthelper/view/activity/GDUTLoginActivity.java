package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.view.View;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseActivity;
import com.zzh.gdut.gduthelper.presenter.LoginPresenter;
import com.zzh.gdut.gduthelper.view.vinterface.LoginInterface;

/**
 * Created by ZengZeHong on 2016/9/24.
 */

public class GDUTLoginActivity extends BaseActivity<LoginInterface, LoginPresenter> implements LoginInterface {

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

    }

    @Override
    public void loginFail(String fail) {

    }

    @Override
    public void getImageCodeSuccess(byte[] bytes) {

    }

    @Override
    public void getImageCodeFail(String fail) {

    }

    @Override
    public void getInfoSuccess(String result) {

    }

    @Override
    public void getInfoFail(String fail) {

    }
}
