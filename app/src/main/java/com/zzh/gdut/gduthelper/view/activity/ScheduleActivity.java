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

public class ScheduleActivity extends BaseActivity<ScheduleInterface, SchedulePresenter> implements ScheduleInterface {
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
        list = JsoupUtil.getSchedule(success);
        //第一周
        schedule.setListData(list);
        Log.e(TAG, "getScheduleSuccess: " + success);
    }

  /*

    /**
     * 筛选对应周下的日期
     *
     * @param week
     *//*
    private List<ScheduleInfo> getSelectSchedule(int week) {
        for (ScheduleInfo scheduleInfo : listAll) {
            String[] times = scheduleInfo.getScheduleTime().split("@");
            for (String time : times) {
                String timeWeek = time.substring(time.indexOf("{") + 1, time.indexOf("}"));
                String[] range = timeWeek.substring(timeWeek.indexOf("第") + 1, timeWeek.indexOf("周")).split("-");
                Log.e(TAG, "getSelectSchedule: " + scheduleInfo.getScheduleName() + ">>"+ range[0] + ">>" + range[1] + ">>" + time + ">>" + timeWeek );
                if (week >= Integer.parseInt(range[0]) && week <= Integer.parseInt(range[1])) {
                    //把满足指定周的课程添加到需要显示的List中去
                    //   listSelect.add(scheduleInfo);
                    scheduleInfo.setCurrentWeek(true);
                }
            }
        }
        return listAll;
    }
*/
    @Override
    public void getScheduleFail(String fail) {
        dismissProgressDialog();
        Log.e(TAG, "getScheduleFai: " + fail);

    }
}
