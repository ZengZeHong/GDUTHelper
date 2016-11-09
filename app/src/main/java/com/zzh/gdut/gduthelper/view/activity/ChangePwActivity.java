package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseActivity;
import com.zzh.gdut.gduthelper.presenter.ChangePwPresenter;
import com.zzh.gdut.gduthelper.util.AppConstants;
import com.zzh.gdut.gduthelper.util.JsoupUtil;
import com.zzh.gdut.gduthelper.util.ToastUtil;
import com.zzh.gdut.gduthelper.view.vinterface.ChangePwInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZengZeHong on 2016/10/13.
 */

public class ChangePwActivity extends BaseActivity<ChangePwInterface, ChangePwPresenter> implements ChangePwInterface {
    private static final String TAG = "ChangePwActivity";
    private String userName;
    private String userNumber;
    @BindView(R.id.et_password_before)
    MaterialEditText etBefore;
    @BindView(R.id.et_password_new)
    MaterialEditText etNew;
    @BindView(R.id.et_password_confirm)
    MaterialEditText etConfirm;
    @BindView(R.id.bt_confirm)
    Button btConfirm;

    @Override
    protected ChangePwPresenter createPresenter() {
        return new ChangePwPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initViews() {
        mPresenter.initData(userName , userNumber);
    }

    private void addOnTextChangeListener(MaterialEditText et) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                isError();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
    }

    @Override
    protected void initAttributes() {
        ButterKnife.bind(ChangePwActivity.this);
        showToolbarAndShowNavigation("密码修改", true);

    }

    @Override
    protected void getIntentData(Intent intent) {
        userName = intent.getStringExtra(AppConstants.TAG_USER_NAME);
        userNumber = intent.getStringExtra(AppConstants.TAG_USER_NUMBER);
    }

    @OnClick({R.id.bt_confirm})
    public void onClick(View v) {
        if (v.getId() == R.id.bt_confirm) {
            if (!isError()) {
                showProgressDialog("正在修改中...");
                mPresenter.changePassword(etBefore.getText().toString(), etNew.getText().toString(), etConfirm.getText().toString());
            } else {
                addOnTextChangeListener(etBefore);
                addOnTextChangeListener(etNew);
                addOnTextChangeListener(etConfirm);
            }
        }
    }

    private boolean isError() {
        if (etBefore.getText().toString().equals(""))
            etBefore.setHelperText("输入不能为空");
        else
            etBefore.setHelperText("");
        if (etNew.getText().toString().equals(""))
            etNew.setHelperText("输入不能为空");
        else if (etNew.getText().toString().length() < 6)
            etNew.setHelperText("密码长度不能小于6位");
        else
            etNew.setHelperText("");
        if (etConfirm.getText().toString().equals(""))
            etConfirm.setHelperText("输入不能为空");
        else if (etConfirm.getText().toString().length() < 6)
            etConfirm.setHelperText("密码长度不能小于6位");
        else
            etConfirm.setHelperText("");
        if (!etNew.getText().toString().equals(etConfirm.getText().toString()))
            etConfirm.setHelperText("两次输入不一致");
        if (etBefore.getHelperText().equals("") && etNew.getHelperText().equals("") && etConfirm.getHelperText().equals(""))
            return false;
        else return true;
    }

    @Override
    public void changeSuccess(String success) {
        dismissProgressDialog();
        ToastUtil.showToast(ChangePwActivity.this, JsoupUtil.parseSubmitPassword(success));
        Log.e(TAG, "changeSuccess: " + success);
    }

    @Override
    public void changeFail(String fail) {
        dismissProgressDialog();
        ToastUtil.showToast(ChangePwActivity.this, fail);
        Log.e(TAG, "changeFail: " + fail);
    }

}
