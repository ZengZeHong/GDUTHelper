package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseNormalActivity;
import com.zzh.gdut.gduthelper.bean.ScheduleInfo;
import com.zzh.gdut.gduthelper.view.widget.Schedule;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZengZeHong on 2016/10/28.
 */

public class ScheduleItemActivity extends BaseNormalActivity {
    public static final String TAG_ITEM = "schedule";
    private ScheduleInfo scheduleInfo;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.tv_teacher)
    TextView tvTeacher;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_schedule_item;
    }

    @Override
    protected void initViews() {
        if(scheduleInfo != null){
            tvClass.setText(scheduleInfo.getScheduleName());
            tvPlace.setText(scheduleInfo.getSchedulePlace());
            tvTeacher.setText(scheduleInfo.getScheduleTeacher());
            tvTime.setText(scheduleInfo.getScheduleTime());
            tvWeek.setText(scheduleInfo.getScheduleWeek());
        }
    }

    @Override
    protected void initAttributes() {
        ButterKnife.bind(ScheduleItemActivity.this);
        showToolbarAndShowNavigation("课程信息", true);

    }

    @Override
    protected void getIntentData(Intent intent) {
        scheduleInfo = intent.getParcelableExtra(TAG_ITEM);
        String scheduleTime = scheduleInfo.getScheduleTime();
        String[] times;
        String timeSection = "", timeWeek = "";
        if (scheduleTime.contains(Schedule.TIME_SEPARATOR)) {
            times = scheduleTime.split(Schedule.TIME_SEPARATOR);
        } else
            times = new String[]{scheduleTime};

        for (int i = 0; i < times.length; i++) {
            String time = times[i];
            timeSection = time.substring(0 , time.indexOf("{"));
            timeWeek += time.substring(time.indexOf("{") + 1, time.indexOf("}")) + " ";
        }
        String[] weeks = timeWeek.split(" ");
        Arrays.sort(weeks);
        timeWeek = "";
        for(String s : weeks){
            timeWeek += s + " ";
        }
        scheduleInfo.setScheduleTime(timeSection);
        scheduleInfo.setScheduleWeek(timeWeek);
    }

    @Override
    public void onClick(View v) {

    }
}
