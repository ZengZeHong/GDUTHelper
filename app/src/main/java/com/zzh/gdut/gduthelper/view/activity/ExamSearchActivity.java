package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseNormalActivity;
import com.zzh.gdut.gduthelper.bean.ExamInfo;
import com.zzh.gdut.gduthelper.view.adapter.ExamSearchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZengZeHong on 2016/10/6.
 */

public class ExamSearchActivity extends BaseNormalActivity {
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
        lists = new ArrayList<>();
        for(int i = 0 ; i <  5 ; i++)
            lists.add(new ExamInfo("离散数学" , "第18周周1-28)第8,9节" , "3", "4"));
        adapter = new ExamSearchAdapter(this);
        adapter.setListData(lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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
}
