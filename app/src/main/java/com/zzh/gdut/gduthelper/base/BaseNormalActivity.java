package com.zzh.gdut.gduthelper.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.util.AppConstants;
import com.zzh.gdut.gduthelper.util.ToastUtil;

/**
 * Created by ZengZeHong on 2016/9/24.
 * 不带Presenter和View Interface的BaseActivity
 */

public abstract class BaseNormalActivity extends AppCompatActivity implements View.OnClickListener {
    //返回时间
    private long exitTime = 0;
    //是否要按两下退出，默认否i
    private boolean isDoubleBackDestory = false;
    protected Toolbar toolbar;
    protected AlertDialog.Builder mAlertDialogBuilder;
    protected AlertDialog mAlertDialog;


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
        mAlertDialogBuilder = new AlertDialog.Builder(this);
        mAlertDialogBuilder.setMessage(msg);
        mAlertDialog = mAlertDialogBuilder.show();
    }

    /**
     * @param msg
     * @param isCancelable     是否可以取消
     * @param positiveListener
     */
    public void showAlertDialog(String title, String msg, boolean isCancelable, boolean isShowNegativeButton, DialogInterface.OnClickListener positiveListener) {
        if (mAlertDialogBuilder == null) {
            mAlertDialogBuilder = new AlertDialog.Builder(this);
        }
        //是否显示取消
        if (isShowNegativeButton) {
            mAlertDialogBuilder.setTitle(title).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        if (title != null)
            mAlertDialogBuilder.setTitle(title);
        if (msg != null)
            mAlertDialogBuilder.setMessage(msg);
        //设置不可以取消
        mAlertDialogBuilder.setCancelable(isCancelable);
        mAlertDialogBuilder.setPositiveButton("确定", positiveListener);
        mAlertDialog = mAlertDialogBuilder.show();
    }

    public void dismissAlertDialog() {
        if (mAlertDialog != null && mAlertDialog.isShowing())
            mAlertDialog.dismiss();
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
