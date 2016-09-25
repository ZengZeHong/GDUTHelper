package com.zzh.gdut.gduthelper.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.util.AppConstants;
import com.zzh.gdut.gduthelper.util.ToastUtil;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by ZengZeHong on 2016/9/24.
 * 不带Presenter和View Interface的BaseActivity
 */

public abstract class BaseNormalActivity extends AppCompatActivity implements View.OnClickListener {
    //返回时间
    private long exitTime = 0;
    //是否要按两下退出，默认否i
    protected boolean isDoubleBackDestory = false;
    protected Toolbar toolbar;
    protected MaterialDialog materialDialog;
    protected ProgressDialog progressDialog;

    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected abstract void initAttributes();

    //获取传递过来的Intent信息
    protected abstract void getIntentData(Intent intent);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        getIntentData(getIntent());
        initAttributes();
        initViews();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按下的是返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isDoubleBackDestory)
                exit();
            else
                //返回动画
                backAnimation();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    protected void backAnimation() {
        finish();
        overridePendingTransition(R.anim.translate_not_move, R.anim.translate_right_out);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtil.showToast(getApplicationContext(), "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    /**
     * 设置是否按两下退出，默认否
     *
     * @param doubleBackDestory
     */
    public void setDoubleBackDestory(boolean doubleBackDestory) {
        isDoubleBackDestory = doubleBackDestory;
    }


    /**
     * @param cls
     * @param bundle
     */
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.translate_right_in,
                R.anim.translate_not_move);
    }

    /**
     * @param cls
     * @param bundle
     * @param requestCode 请求码
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.translate_right_in,
                R.anim.translate_not_move);
    }

    /**
     * @param msg
     */
    public void showAlertDialog(String msg) {
        if (materialDialog == null) {
            materialDialog = new MaterialDialog(this);
        }
        materialDialog.setMessage(msg);
        materialDialog.setCanceledOnTouchOutside(true);
        materialDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
            }
        });
        materialDialog.show();
    }

    /**
     * @param msg
     * @param isCancelable     是否可以取消
     * @param positiveListener
     */
    public void showAlertDialog(String title, String msg, boolean isCancelable, boolean isShowNegativeButton, View.OnClickListener positiveListener) {
        if (materialDialog == null) {
            materialDialog = new MaterialDialog(this);
        }
        //是否显示取消
        if (isShowNegativeButton) {
            materialDialog.setNegativeButton("取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        if (title != null)
            materialDialog.setTitle(title);
        if (msg != null)
            materialDialog.setMessage(msg);
        //设置不可以取消
        materialDialog.setCanceledOnTouchOutside(isCancelable);
        materialDialog.setPositiveButton("确定", positiveListener);
        materialDialog.show();
    }

    public void dismissAlertDialog() {
        if (materialDialog != null)
            materialDialog.dismiss();
    }

    /**
     * 进度对话框
     *
     * @param msg
     */
    public void showProgressDialog(String msg) {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 隐藏ToolBar
     */
    public void hideToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    public void setToolbarTitle(String title) {
        if (toolbar != null) {
            toolbar.setTitle(title);
            toolbar.setTitleTextColor(0xFFFFFFFF);
        }
    }


    public void setToolbarIcon(int iconId) {
        if (toolbar != null) {
            toolbar.setLogo(iconId);
        }
    }

    public void setToolbarTextColor(int colorId) {
        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(colorId));
        }
    }

    /**
     * 是否隐藏ToolBar返回按钮
     *
     * @param show
     */
    public void showOrHideToolBarNavigation(boolean show) {
        if (show) {
            //设置返回键
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    setPendingTransition(AppConstants.OUT_OVERPENDINGTRANSITION);
                }
            });
        }
    }

    /**
     * @param title 直接显示返回箭头的toolbar
     */
    public void showToolbarAndShowNavigation(String title) {
        initToolBar();
        setToolbarTitle(title);
        showToolBar();
        showOrHideToolBarNavigation(true);
    }

    //退出动画
    public void setPendingTransition(int TYPE) {
        if (TYPE == AppConstants.OPEN_OVERPENDINGTRANSITION) {
            overridePendingTransition(R.anim.translate_right_in,
                    R.anim.translate_not_move);
        } else if (TYPE == AppConstants.OUT_OVERPENDINGTRANSITION) {
            overridePendingTransition(R.anim.translate_not_move,
                    R.anim.translate_right_out);
        }
    }

    /**
     * 显示ToolBar
     */
    public void showToolBar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
        }
    }


    /**
     * 初始化ToolBar
     */
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }


}
