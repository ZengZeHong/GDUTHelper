package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseActivity;
import com.zzh.gdut.gduthelper.bean.ScheduleInfo;
import com.zzh.gdut.gduthelper.presenter.SchedulePresenter;
import com.zzh.gdut.gduthelper.util.JsoupUtil;
import com.zzh.gdut.gduthelper.view.adapter.GalleryAdapter;
import com.zzh.gdut.gduthelper.view.vinterface.ScheduleInterface;
import com.zzh.gdut.gduthelper.view.widget.SCGallery;
import com.zzh.gdut.gduthelper.view.widget.SchduleTop;
import com.zzh.gdut.gduthelper.view.widget.Schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZengZeHong on 2016/10/13.
 */

public class ScheduleActivity extends BaseActivity<ScheduleInterface, SchedulePresenter> implements ScheduleInterface, Schedule.OnItemClickListener {
    private static final String TAG = "ScheduleActivity";
    private List<Map<String, List<ScheduleInfo>>> list;
    private GalleryAdapter adapter;
    @BindView(R.id.schedule)
    Schedule schedule;
    @BindView(R.id.schedule_top)
    SchduleTop schduleTop;
    @BindView(R.id.gallery)
    SCGallery gallery;
    @BindView(R.id.rl_shadow)
    RelativeLayout rlShadow;

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
        showToolbarAndShowNavigation("课表信息", true);
        showProgressDialog("正在获取中...");
        mPresenter.getSchedule();
        schedule.setOnItemClickListener(this);
    }

    @Override
    protected void initAttributes() {
        ButterKnife.bind(ScheduleActivity.this);
    }

    @Override
    protected void getIntentData(Intent intent) {

    }

    @OnClick({R.id.rl_shadow})
    public void onClick(View v) {
        if (v.getId() == R.id.rl_shadow) {
            rlShadow.setVisibility(View.GONE);
        }
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
        //如果有多门课程存在的情况
        if (schedule.isFode(list)) {
            Intent intent = new Intent(ScheduleActivity.this, ScheduleShowActivity.class);
            intent.putParcelableArrayListExtra(ScheduleShowActivity.LIST_TAG, (ArrayList<? extends Parcelable>) list);
            startActivity(intent);
            overridePendingTransition(R.anim.alpha_in, R.anim.translate_not_move);
        } else {
            //普通点击
            Intent intent = new Intent(ScheduleActivity.this , ScheduleItemActivity.class);
            intent.putExtra(ScheduleItemActivity.TAG_ITEM , list.get(0));
            startActivity(intent);
        }
    }

}
