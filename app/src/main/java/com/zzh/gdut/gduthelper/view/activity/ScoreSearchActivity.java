package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.view.View;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseActivity;
import com.zzh.gdut.gduthelper.presenter.ScorePresenter;
import com.zzh.gdut.gduthelper.view.vinterface.ScoreInfoInterface;

/**
 * 成绩查询
 * Created by ZengZeHong on 2016/10/8.
 */

public class ScoreSearchActivity extends BaseActivity<ScoreInfoInterface , ScorePresenter>{
    @Override
    protected ScorePresenter createPresenter() {
        return new ScorePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_score_search;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initAttributes() {
        showToolbarAndShowNavigation("成绩查询");

    }

    @Override
    protected void getIntentData(Intent intent) {

    }

    @Override
    public void onClick(View v) {

    }
}
