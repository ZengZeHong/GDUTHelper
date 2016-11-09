package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseNormalActivity;
import com.zzh.gdut.gduthelper.util.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GDUTMainActivity extends BaseNormalActivity {
    private static final String TAG = "GDUTMainActivity";
    private String userName;
    private String userNumber;
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
        return R.layout.activity_gdut_main;
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initAttributes() {
        ButterKnife.bind(GDUTMainActivity.this);
    }

    @Override
    protected void getIntentData(Intent intent) {
        userName = intent.getStringExtra(AppConstants.TAG_USER_NAME);
        userNumber = intent.getStringExtra(AppConstants.TAG_USER_NUMBER);
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
            Intent intent = new Intent(GDUTMainActivity.this, cls);
            intent.putExtra(AppConstants.TAG_USER_NUMBER, userNumber);
            intent.putExtra(AppConstants.TAG_USER_NAME, userName );
            startActivity(intent);
            setPendingTransition(AppConstants.OPEN_OVERPENDINGTRANSITION);

        }
    }
}
