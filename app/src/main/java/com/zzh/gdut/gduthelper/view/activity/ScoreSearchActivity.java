package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseActivity;
import com.zzh.gdut.gduthelper.model.mimplement.ScoreModelImp;
import com.zzh.gdut.gduthelper.presenter.ScorePresenter;
import com.zzh.gdut.gduthelper.util.ToastUtil;
import com.zzh.gdut.gduthelper.view.vinterface.ScoreInfoInterface;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zzh.gdut.gduthelper.R.id.cb_term;

/**
 * 成绩查询
 * Created by ZengZeHong on 2016/10/8.
 */

public class ScoreSearchActivity extends BaseActivity<ScoreInfoInterface, ScorePresenter> implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "ScoreSearchActivity";
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.rb_1)
    RadioButton rbOne;
    @BindView(R.id.rb_2)
    RadioButton rbTwo;
    @BindView(R.id.rb_3)
    RadioButton rbThree;
    @BindView(cb_term)
    CheckBox cbTerm;
    @BindView(R.id.cb_year)
    CheckBox cbYear;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.fab_search)
    FloatingActionButton fabSearch;
    private String[] strings;
    private int currentYear;
    private String selectYear;
    private String selectTerm;
    private String searchWay;

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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, strings);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    protected void initAttributes() {
        ButterKnife.bind(ScoreSearchActivity.this);
        showToolbarAndShowNavigation("成绩查询");
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        strings = new String[currentYear - 2010];
        for (int i = 0; i <= currentYear - 2011; i++)
            strings[i] = (2011 + i) + "-" + (2011 + i + 1);

    }

    @Override
    protected void getIntentData(Intent intent) {

    }

    @OnClick({R.id.rb_1, R.id.rb_2, R.id.rb_3, cb_term, R.id.cb_year, R.id.cb_all, R.id.fab_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_1: {
                selectTerm = "1";
            }
            break;
            case R.id.rb_2: {
                selectTerm = "2";
            }
            break;
            case R.id.rb_3: {
                selectTerm = "3";
            }
            break;
            case cb_term: {
                cbYear.setChecked(false);
                cbAll.setChecked(false);
                searchWay = ScoreModelImp.TAG_TERM;
            }
            break;
            case R.id.cb_year: {
                cbTerm.setChecked(false);
                cbAll.setChecked(false);
                searchWay = ScoreModelImp.TAG_YEAR;
            }
            break;
            case R.id.cb_all: {
                cbYear.setChecked(false);
                cbTerm.setChecked(false);
                searchWay = ScoreModelImp.TAG_ALL;
            }
            break;
            case R.id.fab_search: {
                ToastUtil.showToast(this , searchWay + ">>" + selectTerm + ">>" + selectYear);
            }
            break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectYear = strings[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
