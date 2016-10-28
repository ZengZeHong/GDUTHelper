package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseNormalActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZengZeHong on 2016/10/28.
 */

public class ScheduleItemActivity extends BaseNormalActivity {
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

    }

    @Override
    protected void initAttributes() {
        ButterKnife.bind(ScheduleItemActivity.this);
        showToolbarAndShowNavigation("课程信息", true);

    }

    @Override
    protected void getIntentData(Intent intent) {

    }

    @Override
    public void onClick(View v) {

    }
}
