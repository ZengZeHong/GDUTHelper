package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseActivity;
import com.zzh.gdut.gduthelper.bean.ScheduleInfo;
import com.zzh.gdut.gduthelper.presenter.SchedulePresenter;
import com.zzh.gdut.gduthelper.util.AppConstants;
import com.zzh.gdut.gduthelper.util.CalendarUtil;
import com.zzh.gdut.gduthelper.util.JsoupUtil;
import com.zzh.gdut.gduthelper.view.adapter.PopupWindowAdapter;
import com.zzh.gdut.gduthelper.view.vinterface.RecyclerItemClick;
import com.zzh.gdut.gduthelper.view.vinterface.ScheduleInterface;
import com.zzh.gdut.gduthelper.view.widget.Schedule;
import com.zzh.gdut.gduthelper.view.widget.ScheduleTop;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZengZeHong on 2016/10/13.
 */

public class ScheduleActivity extends BaseActivity<ScheduleInterface, SchedulePresenter> implements ScheduleInterface, Schedule.OnItemClickListener , RecyclerItemClick{
    private static final String TAG = "ScheduleActivity";
    private List<Map<String, List<ScheduleInfo>>> list;
    private List<String> listWindows ;
    private PopupWindow mPopupWindow;
    private int currentPosition = 0;
    private String userName;
    private String userNumber;
    @BindView(R.id.schedule)
    Schedule schedule;
    @BindView(R.id.schedule_top)
    ScheduleTop schduleTop;
    @BindView(R.id.rl_drop)
    RelativeLayout rlDrop;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.img_drop)
    ImageView imgDrop;
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
        mPresenter.initData(userName , userNumber);
        mPresenter.getSchedule();
        schedule.setOnItemClickListener(this);
        tvText.setText(listWindows.get(currentPosition));
        schduleTop.setWeek(currentPosition +1);
        schedule.setCurrentWeek(currentPosition + 1);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void initAttributes() {
        ButterKnife.bind(ScheduleActivity.this);
        listWindows = new ArrayList<>();
        for(int i = 1 ; i <= 25; i++){
            listWindows.add("第" + i + "周");
        }
        int currentYear = CalendarUtil.getCurrentYear();
        int currentMonth = CalendarUtil.getCurrentMonth();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        Log.e(TAG, "initAttributes:123 " +  Integer.parseInt(simpleDateFormat.format(new Date())));
        int baseWeek = CalendarUtil.getWeekFromDay(currentYear, 9, 1);
        int currentWeek = CalendarUtil.getWeekFromDay(currentYear , currentMonth , Integer.parseInt(simpleDateFormat.format(new Date())));
        currentPosition  = currentWeek - baseWeek;
        if(currentPosition < 0)
            currentPosition = 0;
        Log.e(TAG, "initAttributes: " + baseWeek + ">>" + currentWeek );
    }

    @Override
    protected void getIntentData(Intent intent) {
        userName = intent.getStringExtra(AppConstants.TAG_USER_NAME);
        userNumber = intent.getStringExtra(AppConstants.TAG_USER_NUMBER);
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
            overridePendingTransition(R.anim.translate_right_in, R.anim.translate_not_move);

        }
    }

    @OnClick({R.id.rl_drop})
    public void onClick(View v) {
        if(v.getId() == R.id.rl_drop){
                if (mPopupWindow == null) {
                    LayoutInflater layoutInflater = LayoutInflater.from(ScheduleActivity.this);
                    View popupWindow = layoutInflater.inflate(R.layout.popup_window, null);
                    RecyclerView recyclerView = (RecyclerView) popupWindow.findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ScheduleActivity.this));
                    PopupWindowAdapter mPopupWindowAdapter = new PopupWindowAdapter(ScheduleActivity.this, listWindows, currentPosition);
                    recyclerView.setAdapter(mPopupWindowAdapter);
                    if (currentPosition >= 2)
                        recyclerView.scrollToPosition(currentPosition - 2);
                    else if (currentPosition == 1)
                        recyclerView.scrollToPosition(currentPosition - 1);
                    mPopupWindowAdapter.setRecyclerItemClick(this);
                    mPopupWindow = new PopupWindow(popupWindow, v.getWidth() + 50, v.getWidth() * 2);
                    mPopupWindow.showAsDropDown(v, 0, 10);
                }
            else {
                    mPopupWindow.dismiss();
                    mPopupWindow = null;
                }
        }
    }

    @Override
    public void onItemClick(int position) {
        currentPosition = position;
        schduleTop.setWeek(position +1);
        schedule.setCurrentWeek(position + 1);
        tvText.setText(listWindows.get(position));
        mPopupWindow.dismiss();
        mPopupWindow = null;
    }
}
