package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseActivity;
import com.zzh.gdut.gduthelper.bean.ExamInfo;
import com.zzh.gdut.gduthelper.presenter.ExamPresenter;
import com.zzh.gdut.gduthelper.util.JsoupUtil;
import com.zzh.gdut.gduthelper.view.adapter.ExamSearchAdapter;
import com.zzh.gdut.gduthelper.view.vinterface.ExamInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZengZeHong on 2016/10/6.
 */

public class ExamSearchActivity extends BaseActivity<ExamInterface , ExamPresenter> implements ExamInterface{
    private static final String TAG = "ExamSearchActivity";
    private ExamSearchAdapter adapter;
    private List<ExamInfo> lists;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_exam_search;
    }

    @Override
    protected void initViews() {
        showProgressDialog("正在获取中...");
        mPresenter.searchExam();
    }

    @Override
    protected void initAttributes() {
        ButterKnife.bind(ExamSearchActivity.this);
        showToolbarAndShowNavigation("考试查询");
    }

    @Override
    protected void getIntentData(Intent intent) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected ExamPresenter createPresenter() {
        return new ExamPresenter(this);
    }

    @Override
    public void searchSuccess(String success) {
        dismissProgressDialog();
        lists = JsoupUtil.searchExamInfo(success);
        adapter = new ExamSearchAdapter(this);
        adapter.setListData(lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        Log.e(TAG, "searchSuccess: "+ success );
    }

    @Override
    public void searchFail(String fail) {
        dismissProgressDialog();
        Log.e(TAG, "searchFail: " + fail );
    }
}
