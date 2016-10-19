package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseNormalActivity;
import com.zzh.gdut.gduthelper.view.widget.ClickBackgoundView;
import com.zzh.gdut.gduthelper.view.widget.ScheduleRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZengZeHong on 2016/10/19.
 */

public class ScheduleShowActivity extends BaseNormalActivity {
    private static final String TAG = "ScheduleShowActivity";
    @BindView(R.id.rl_schedule)
    ScheduleRelativeLayout rlSchedule;
    @BindView(R.id.click_view)
    ClickBackgoundView clickBackgoundView;

    @BindView(R.id.rl_schedule1)
    ScheduleRelativeLayout rlSchedule1;
    @BindView(R.id.click_view1)
    ClickBackgoundView clickBackgoundView1;
    int k = 0;
    float scale = 0;
    private int x, y, lastX, lastY;
    float width = 394;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_schedule_show;
    }

    @Override
    protected void initViews() {
        clickBackgoundView.setBackground(R.color.schedulePurple);
        clickBackgoundView1.setBackground(R.color.schedulePurple);
        float rate = (float) 0.8;
        rlSchedule1.setRotationY(30);
        rlSchedule1.setScaleX(rate);
        rlSchedule1.setScaleY(rate);
        clickBackgoundView.setOnItemClick(new ClickBackgoundView.OnItemClick() {
            @Override
            public void onClick() {
                if (k != 30) {
                    k = k + 5;
                    scale += 0.033;
                    rlSchedule1.setRotationY(k);
                    Log.e(TAG, "onClick: " + (1 - scale));
                    rlSchedule1.setScaleX(1 - scale);
                    rlSchedule1.setScaleY(1 - scale);
                }
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                x = (int) event.getX();
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                lastX = (int) event.getX();
                int offestX = lastX - x;
                if (offestX > 0) {
                    //右滑动时-30
                    float rate = Math.abs(offestX) / width;
                    Log.e(TAG, "onTouchEvent: rate " + rate);
                    Log.e(TAG, "onTouchEvent: setRotationY " + (rate * 30));
                    if (rate <= 1) {
                        //显示缩放操作
                        rlSchedule1.setRotationY(-(rate * 30));
                        rlSchedule1.setScaleX((float) (1 - (rate * 0.2)));
                        rlSchedule1.setScaleY((float) (1 - (rate * 0.2)));
                        rlSchedule1.setAlpha((float) (1 - (rate) * 0.5));
                    }
                } else {
                    //右滑动时-30
                    float rate = Math.abs(offestX) / width;
                    Log.e(TAG, "onTouchEvent: rate " + rate);
                    Log.e(TAG, "onTouchEvent: setRotationY " + (rate * 30));
                    if (rate <= 1) {
                        //显示缩放操作
                        rlSchedule1.setRotationY((rate * 30) - 30);
                        rlSchedule1.setScaleX((float) (0.8 + (rate * 0.2)));
                        rlSchedule1.setScaleY((float) (0.8 + (rate * 0.2)));
                        rlSchedule1.setAlpha((float) (0.8 + (rate) * 0.2));
                    }
                }
            }
            break;
            case MotionEvent.ACTION_UP: {
            }
            break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void initAttributes() {
        ButterKnife.bind(ScheduleShowActivity.this);
    }

    @Override
    protected void getIntentData(Intent intent) {

    }

    @Override
    public void onClick(View v) {

    }

}
