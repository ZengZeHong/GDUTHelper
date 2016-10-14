package com.zzh.gdut.gduthelper.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zzh.gdut.gduthelper.R;

import butterknife.ButterKnife;

/**
 * Created by ZengZeHong on 2016/10/13.
 */

public class ScheduleActivity extends AppCompatActivity {
    private static final String TAG = "ScheduleActivity";
   /* @BindView(R.id.et)
    EditText et;
    @BindView(R.id.schedule)
    SchduleTop schedule;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        ButterKnife.bind(ScheduleActivity.this);
    }

}
