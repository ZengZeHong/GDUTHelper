package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseActivity;
import com.zzh.gdut.gduthelper.presenter.SchedulePresenter;
import com.zzh.gdut.gduthelper.view.vinterface.ScheduleInterface;

import butterknife.ButterKnife;

/**
 * Created by ZengZeHong on 2016/10/13.
 */

public class ScheduleActivity extends BaseActivity<ScheduleInterface, SchedulePresenter> implements ScheduleInterface {
    private static final String TAG = "ScheduleActivity";

    @Override
    protected SchedulePresenter createPresenter() {
        return new SchedulePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_schedule;
    }

    @Override
    protected void initViews() {
        showProgressDialog("正在获取中...");
        mPresenter.getSchedule();
    }

    @Override
    protected void initAttributes() {
        ButterKnife.bind(ScheduleActivity.this);
    }

    @Override
    protected void getIntentData(Intent intent) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void getScheduleSuccess(String success) {
        dismissProgressDialog();
        Log.e(TAG, "getScheduleSuccess: " + success);
    }

    @Override
    public void getScheduleFai(String fail) {
        dismissProgressDialog();
        Log.e(TAG, "getScheduleFai: " + fail);

    }
}
