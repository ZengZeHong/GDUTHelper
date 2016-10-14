package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseNormalActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseNormalActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.cv_info)
    CardView cvInfo;
    @BindView(R.id.cv_score)
    CardView cvScore;
    @BindView(R.id.cv_class)
    CardView cvClass;
    @BindView(R.id.cv_exam)
    CardView cvExam;
    @BindView(R.id.cv_evaluate)
    CardView cvEvaluate;
    @BindView(R.id.cv_password)
    CardView cvPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initAttributes() {
        ButterKnife.bind(MainActivity.this);
    }

    @Override
    protected void getIntentData(Intent intent) {

    }

    @OnClick({R.id.cv_info, R.id.cv_exam, R.id.cv_class, R.id.cv_evaluate, R.id.cv_score, R.id.cv_password})
    public void onClick(View v) {
        Class<?> cls = null;
        switch (v.getId()) {
            case R.id.cv_info: {
                cls = PersonInfoActivity.class;
            }
            break;
            case R.id.cv_score: {
                cls = ScoreSearchActivity.class;
            }
            break;
            case R.id.cv_class: {
                cls = ScheduleActivity.class;
            }
            break;
            case R.id.cv_exam: {
                cls = ExamSearchActivity.class;
            }
            break;
            case R.id.cv_evaluate: {
            }
            break;
            case R.id.cv_password: {
                cls = ChangePwActivity.class;
            }
            break;
        }
        if (cls != null) {
            Intent intent = new Intent(MainActivity.this, cls);
            startActivity(intent);
        }
    }
}
