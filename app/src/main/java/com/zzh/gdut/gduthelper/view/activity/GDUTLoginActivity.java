package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseActivity;
import com.zzh.gdut.gduthelper.presenter.LoginPresenter;
import com.zzh.gdut.gduthelper.util.ToastUtil;
import com.zzh.gdut.gduthelper.view.vinterface.LoginInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZengZeHong on 2016/9/24.
 */

public class GDUTLoginActivity extends BaseActivity<LoginInterface, LoginPresenter> implements LoginInterface {
    private static final String TAG = "GDUTLoginActivity";
    @BindView(R.id.et_account)
    MaterialEditText etAccount;
    @BindView(R.id.et_password)
    MaterialEditText etPassword;
    @BindView(R.id.et_code)
    MaterialEditText etCode;
    @BindView(R.id.img_code)
    ImageView imgCode;
    @BindView(R.id.bt_login)
    Button btLogin;

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
        showToolbarAndShowNavigation("教务系统");
        //一开始就获取验证码
        mPresenter.getImageCode();
        ToastUtil.showToast(GDUTLoginActivity.this, "正在获取验证码..");
    }

    @Override
    protected void initAttributes() {
        ButterKnife.bind(this);
    }

    @Override
    protected void getIntentData(Intent intent) {

    }

    @OnClick({R.id.bt_login, R.id.img_code})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_code: {
                mPresenter.getImageCode();
            }
            break;
            case R.id.bt_login: {
                if (!isError()) {
                    //开始登陆
                    showProgressDialog("正在登陆中...");
                    mPresenter.login(etAccount.getText().toString(), etPassword.getText().toString(), etCode.getText().toString());
                }
            }
            break;
        }
    }

    /**
     * 判断当前输入是否有错误
     *
     * @return
     */
    private boolean isError() {
        String errorLine = null;
        if (etAccount.getText().toString().equals(""))
            errorLine = "学号不能为空";
        else if (etPassword.getText().toString().equals(""))
            errorLine = "密码不能为空";
        else if (etCode.getText().toString().equals(""))
            errorLine = "验证码不能为空";
        else if (etAccount.getText().length() != 10)
            errorLine = "学号长度不正确";
        else if (etCode.getText().length() != 4)
            errorLine = "验证码长度为4位";
        if (errorLine == null)
            return false;
        else {
            showAlertDialog(errorLine);
            return true;
        }
    }

    @Override
    public void loginSuccess(String result) {
        Log.e(TAG, "loginSuccess: " + result);
        if (result.contains("xs_main.aspx?xh=3114005890"))
            mPresenter.getInfo("xs_main.aspx?xh=3114005890");
    }

    @Override
    public void loginFail(String fail) {
        Log.e(TAG, "loginFail: " + fail);
        mPresenter.getImageCode();
        dismissProgressDialog();
    }

    @Override
    public void getImageCodeSuccess(byte[] bytes) {
        Log.e(TAG, "getImageCodeSuccess: " + bytes.length);
        imgCode.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
    }

    @Override
    public void getImageCodeFail(String fail) {
        Log.e(TAG, "getImageCodeFail: " + fail);
    }

    @Override
    public void getInfoSuccess(String result) {
        Log.e(TAG, "getInfoSuccess: " + result);
        dismissProgressDialog();
    }

    @Override
    public void getInfoFail(String fail) {
        Log.e(TAG, "getInfoFail: " + fail);
        dismissProgressDialog();
    }
}