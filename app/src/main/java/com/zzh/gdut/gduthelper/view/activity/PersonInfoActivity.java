package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseActivity;
import com.zzh.gdut.gduthelper.presenter.PersonInfoPresenter;
import com.zzh.gdut.gduthelper.view.vinterface.PersonInfoInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZengZeHong on 2016/9/26.
 */

public class PersonInfoActivity extends BaseActivity<PersonInfoInterface, PersonInfoPresenter> implements PersonInfoInterface {
    private static final String TAG = "PersonInfoActivity";
    @BindView(R.id.ll_number)
    LinearLayout llNumber;
    @BindView(R.id.ll_nation)
    LinearLayout llNation;
    @BindView(R.id.ll_dormitory)
    LinearLayout llDormitory;
    @BindView(R.id.ll_polity)
    LinearLayout llPolity;
    @BindView(R.id.ll_date_to_school)
    LinearLayout llDateToScholl;
    @BindView(R.id.ll_date_to_birth)
    LinearLayout llDateToBirth;
    @BindView(R.id.ll_phone_number)
    LinearLayout llPhoneNumber;
    @BindView(R.id.ll_email)
    LinearLayout llEmail;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.ll_major_direction)
    LinearLayout llMajorDirection;
    @BindView(R.id.ll_graduate)
    LinearLayout llGraduate;
    @BindView(R.id.bt_commit)
    Button btCommit;
    @BindView(R.id.img_head)
    ImageView imgHead;

    @Override
    protected PersonInfoPresenter createPresenter() {
        return new PersonInfoPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gdut_person_info;
    }

    @Override
    protected void initViews() {
        setToolbar();
        showProgressDialog("正在获取信息中...");
        //获取头像
        mPresenter.getImageHead();
        //获取个人信息
        mPresenter.getPersonInfoData();
    }

    /**
     * 设置Toolbar字体颜色
     */
    private void setToolbar() {
        showToolbarAndShowNavigation("曾泽洪");
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
    }

    @Override
    protected void initAttributes() {
        ButterKnife.bind(this);
    }

    protected void getIntentData(Intent intent) {

    }

    @OnClick({R.id.ll_number, R.id.ll_nation, R.id.ll_dormitory, R.id.ll_polity, R.id.ll_date_to_school, R.id.ll_date_to_birth,
            R.id.ll_phone_number, R.id.ll_email, R.id.ll_address, R.id.ll_major_direction, R.id.ll_graduate, R.id.bt_commit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_number: {
            }
            break;
            case R.id.ll_nation: {
            }
            break;
            case R.id.ll_dormitory: {
            }
            break;
            case R.id.ll_polity: {
            }
            break;
            case R.id.ll_date_to_school: {
            }
            break;
            case R.id.ll_date_to_birth: {
            }
            break;
            case R.id.ll_phone_number: {
            }
            break;
            case R.id.ll_email: {
            }
            break;
            case R.id.ll_address: {
            }
            break;
            case R.id.ll_major_direction: {
            }
            break;
            case R.id.ll_graduate: {
            }
            break;
            case R.id.bt_commit: {
            }
            break;
        }
    }

    @Override
    public void submitSuccess(String success) {

    }

    @Override
    public void submitFail(String fail) {

    }

    @Override
    public void getInfoSuccess(String success) {
        Log.e(TAG, "getInfoSuccess: " + success);
        dismissProgressDialog();
    }

    @Override
    public void getInfoFail(String fail) {
        Log.e(TAG, "getInfoFail: " + fail);
        dismissProgressDialog();
    }

    @Override
    public void getImageHeadSuccess(byte[] bytes) {
        dismissProgressDialog();
        Log.e(TAG, "getImageHeadSuccess: " + bytes.length);
        imgHead.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
    }

    @Override
    public void getImageHeadFail(String fail) {
        Log.e(TAG, "getImageHeadFail:  " + fail);
        dismissProgressDialog();
    }
}
