package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseActivity;
import com.zzh.gdut.gduthelper.bean.PersonInfo;
import com.zzh.gdut.gduthelper.presenter.PersonInfoPresenter;
import com.zzh.gdut.gduthelper.util.ApiUtil;
import com.zzh.gdut.gduthelper.util.JsoupUtil;
import com.zzh.gdut.gduthelper.util.ToastUtil;
import com.zzh.gdut.gduthelper.view.vinterface.PersonInfoInterface;
import com.zzh.gdut.gduthelper.view.widget.EditDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;

import static com.zzh.gdut.gduthelper.util.AppConstants.SERVICE_BUSY;

/**
 * 个人信息
 * Created by ZengZeHong on 2016/9/26.
 */

public class PersonInfoActivity extends BaseActivity<PersonInfoInterface, PersonInfoPresenter> implements PersonInfoInterface {
    private static final String TAG = "PersonInfoActivity";
    private PersonInfo personInfo;
    @BindView(R.id.rl_dormitory)
    RelativeLayout rlDormitory;
    @BindView(R.id.rl_polity)
    RelativeLayout rlPolity;
    @BindView(R.id.rl_phone_number)
    RelativeLayout rlPhoneNumber;
    @BindView(R.id.rl_email)
    RelativeLayout rlEmail;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.rl_graduate)
    RelativeLayout rlGraduate;
    @BindView(R.id.bt_commit)
    Button btCommit;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.tv_number_tag)
    TextView tvNumberTag;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_nation_tag)
    TextView tvNationTag;
    @BindView(R.id.tv_nation)
    TextView tvNation;
    @BindView(R.id.tv_dormitory_tag)
    TextView tvDormitoryTag;
    @BindView(R.id.tv_dormitory)
    TextView tvDormitory;
    @BindView(R.id.tv_polity_tag)
    TextView tvPolityTag;
    @BindView(R.id.tv_polity)
    TextView tvPolity;
    @BindView(R.id.tv_date_to_school_tag)
    TextView tvDateSchoolTag;
    @BindView(R.id.tv_date_to_school)
    TextView tvDateSchool;
    @BindView(R.id.tv_date_to_birth_tag)
    TextView tvDateBirthTag;
    @BindView(R.id.tv_date_to_birth)
    TextView tvDateBirth;
    @BindView(R.id.tv_phone_number_tag)
    TextView tvPhoneNumberTag;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_email_tag)
    TextView tvEmailTag;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_address_tag)
    TextView tvAddressTag;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_major_direction_tag)
    TextView tvMajorTag;
    @BindView(R.id.tv_major_direction)
    TextView tvMajor;
    @BindView(R.id.tv_graduate_tag)
    TextView tvGraduateTag;
    @BindView(R.id.tv_graduate)
    TextView tvGraduate;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123) {
                if (personInfo != null) {
                    updateData();
                }
                dismissProgressDialog();
            }
        }
    };

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
        showToolbarAndShowNavigation(ApiUtil.USER_NAME , true);
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

    @OnClick({R.id.rl_dormitory, R.id.rl_polity, R.id.rl_phone_number, R.id.rl_email, R.id.rl_address, R.id.rl_graduate, R.id.bt_commit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_dormitory: {
                showEditDialog(tvDormitoryTag, tvDormitory, EditDialog.TAG_DORMITORY);
            }
            break;
            case R.id.rl_polity: {
                showListDialog();
            }
            break;
            case R.id.rl_phone_number: {
                showEditDialog(tvPhoneNumberTag, tvPhoneNumber, EditDialog.TAG_PHONE_NUMBER);
            }
            break;
            case R.id.rl_email: {
                showEditDialog(tvEmailTag, tvEmail, EditDialog.TAG_EMAIL);
            }
            break;
            case R.id.rl_address: {
                showEditDialog(tvAddressTag, tvAddress, EditDialog.TAG_ADDRESS);
            }
            break;
            case R.id.rl_graduate: {
                showEditDialog(tvGraduateTag, tvGraduate, EditDialog.TAG_GRATUATE);
            }
            break;
            case R.id.bt_commit: {
                showProgressDialog("正在提交中...");
                personInfo.setDormitory(tvDormitory.getText().toString());
                personInfo.setAddress(tvAddress.getText().toString());
                personInfo.setPolity(tvPolity.getText().toString());
                personInfo.setPhoneNumber(tvPhoneNumber.getText().toString());
                personInfo.setEmail(tvEmail.getText().toString());
                personInfo.setGradurate(tvGraduate.getText().toString());
                mPresenter.submitData(personInfo);
            }
            break;
        }
    }

    private void showListDialog() {
        final MaterialDialog dialog = new MaterialDialog(PersonInfoActivity.this);
        dialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_listview, null);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.dialog_listview_item, personInfo.getPolicyList()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                tvPolity.setText(personInfo.getPolicyList().get(position));
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    private void showEditDialog(TextView tvTag, final TextView tv, int tag) {
        new EditDialog(PersonInfoActivity.this).
                setFloatHint(tvTag.getText().toString()).
                setHint(tv.getText().toString()).
                setTag(tag).
                show(new EditDialog.OnPositiveClick() {
                    @Override
                    public void onPositiveClick(String result) {
                        if (!result.equals(""))
                            tv.setText(result);

                    }
                });
    }

    @Override
    public void submitSuccess(String success) {
        Log.e(TAG, "submitSuccess: " + success);
        if (!success.contains(SERVICE_BUSY)) {
            ToastUtil.showToast(PersonInfoActivity.this, JsoupUtil.parseSubmitResult(success));
        } else
            ToastUtil.showToast(PersonInfoActivity.this, "提交数据失败");

        dismissProgressDialog();
    }

    @Override
    public void submitFail(String fail) {
        dismissProgressDialog();
        Log.e(TAG, "submitFail: " + fail);
    }

    @Override
    public void getInfoSuccess(final String success) {
        Log.e(TAG, "getInfoSuccess: " + success);
        new Thread() {
            @Override
            public void run() {
                super.run();
                personInfo = JsoupUtil.getPersonInfo(success);
                handler.sendEmptyMessage(0x123);
            }
        }.start();
    }

    @Override
    public void getInfoFail(String fail) {
        Log.e(TAG, "getInfoFail: " + fail);
        dismissProgressDialog();
    }

    @Override
    public void getImageHeadSuccess(byte[] bytes) {
        Log.e(TAG, "getImageHeadSuccess: " + bytes.length);
        imgHead.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
    }

    @Override
    public void getImageHeadFail(String fail) {
        Log.e(TAG, "getImageHeadFail:  " + fail);
    }

    /**
     * 设置数据
     */
    private void updateData() {
        tvNumberTag.setText(personInfo.getNumberTag());
        tvNumber.setText(personInfo.getNumber());
        tvNationTag.setText(personInfo.getNationTag());
        tvNation.setText(personInfo.getNation());
        tvDormitoryTag.setText(personInfo.getDormitoryTag());
        tvDormitory.setText(personInfo.getDormitory());
        tvPolityTag.setText(personInfo.getPolityTag());
        tvPolity.setText(personInfo.getPolity());
        tvDateSchoolTag.setText(personInfo.getDateToSchoolTag());
        tvDateSchool.setText(personInfo.getDateToSchool());
        tvDateBirthTag.setText(personInfo.getDateToBirthTag());
        tvDateBirth.setText(personInfo.getDateToBirth());
        tvPhoneNumberTag.setText(personInfo.getPhoneNumberTag());
        tvPhoneNumber.setText(personInfo.getPhoneNumber());
        tvEmailTag.setText(personInfo.getEmailTag());
        tvEmail.setText(personInfo.getEmail());
        tvAddressTag.setText(personInfo.getAddressTag());
        tvAddress.setText(personInfo.getAddress());
        tvMajorTag.setText(personInfo.getMajorTag());
        tvMajor.setText(personInfo.getMajor());
        tvGraduateTag.setText(personInfo.getGraduateTag());
        tvGraduate.setText(personInfo.getGradurate());

    }

}
