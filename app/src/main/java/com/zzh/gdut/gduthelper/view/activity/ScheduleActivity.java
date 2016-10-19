package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseActivity;
import com.zzh.gdut.gduthelper.bean.ScheduleInfo;
import com.zzh.gdut.gduthelper.presenter.SchedulePresenter;
import com.zzh.gdut.gduthelper.util.JsoupUtil;
import com.zzh.gdut.gduthelper.view.vinterface.ScheduleInterface;
import com.zzh.gdut.gduthelper.view.widget.SchduleTop;
import com.zzh.gdut.gduthelper.view.widget.Schedule;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZengZeHong on 2016/10/13.
 */

public class ScheduleActivity extends BaseActivity<ScheduleInterface, SchedulePresenter> implements ScheduleInterface, Schedule.OnItemClickListener {
    private static final String TAG = "ScheduleActivity";
    private List<Map<String, List<ScheduleInfo>>> list;
    @BindView(R.id.schedule)
    Schedule schedule;
    @BindView(R.id.schedule_top)
    SchduleTop schduleTop;
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
        showToolbarAndShowNavigation("测试", false);
        //  showProgressDialog("正在获取中...");
        //   mPresenter.getSchedule();
        //   schedule.setOnItemClickListener(this);
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
        list = JsoupUtil.getSchedule(success);
        //第一周
        schedule.setListData(list);
        Log.e(TAG, "getScheduleSuccess: " + success);
    }


    @Override
    public void getScheduleFail(String fail) {
        dismissProgressDialog();
        Log.e(TAG, "getScheduleFai: " + fail);

    }

    @Override
    public void onItemClick(List<ScheduleInfo> list) {
        Log.e(TAG, "onItemClick: " + list.toString());
    }

}
