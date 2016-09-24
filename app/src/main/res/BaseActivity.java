package com.rdc.spg.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.rdc.spg.R;
import com.rdc.spg.utils.AppConstants;
import com.rdc.spg.utils.ToastUtil;

/**
 * Created by ZengZeHong on 2016/7/15.
 *
 * @param <V>View接口
 * @param <T>Presenter
 */

public abstract class BaseActivity<V, T extends com.rdc.spg.base.BasePresenter<V>> extends AppCompatActivity implements View.OnClickListener {
    //Presenter对象
    protected T mPresenter;
    //返回时间
    private long exitTime = 0;
    //是否要按两下退出，默认否i
    private boolean isDoubleBackDestory = false;
    private ProgressDialog mProgressDialog;
    protected SharedPreferences mSharePreferences;
    protected SharedPreferences.Editor mEditor;
    protected Toolbar toolbar;
    protected AlertDialog.Builder mAlertDialogBuilder;
    protected AlertDialog mAlertDialog;

    //获取Presenter;
    protected abstract T createPresenter();

    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected abstract void initAttributes();

    //获取传递过来的Intent信息
    protected abstract void getIntentData(Intent intent);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
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
       //     System.exit(0);
        }
    }

    /**
     * @param doubleBackDestory
     */
    //设置是否按两下退出，默认否
    public void setDoubleBackDestory(boolean doubleBackDestory) {
        isDoubleBackDestory = doubleBackDestory;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    /**
     * @param cls
     */
    public void startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
        overridePendingTransition(R.anim.translate_right_in,
                R.anim.translate_not_move);
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
     * @param isCancelable 是否可以取消
     * @param positiveListener
     */
    public void showAlertDialog(String title ,String msg,boolean isCancelable , DialogInterface.OnClickListener positiveListener) {
        if (mAlertDialogBuilder == null) {
            mAlertDialogBuilder = new AlertDialog.Builder(this);
        }
        if(title != null) {
            mAlertDialogBuilder.setTitle(title).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        //设置不可以取消
        mAlertDialogBuilder.setCancelable(isCancelable);
        mAlertDialogBuilder.setMessage(msg).setPositiveButton("确定", positiveListener);
        mAlertDialog = mAlertDialogBuilder.show();
    }

    /**
     * @param msg
     * @param isCancelable
     * @param positiveListener
     * @param negativeListener
     */
    public void showAlertDialog(String msg,boolean isCancelable , DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        if (mAlertDialogBuilder == null) {
            mAlertDialogBuilder = new AlertDialog.Builder(this);
        }
        mAlertDialogBuilder.setCancelable(isCancelable);
        mAlertDialogBuilder.setTitle(null).setMessage(msg).setPositiveButton("确定", positiveListener).setNegativeButton("取消", negativeListener);
        mAlertDialog = mAlertDialogBuilder.show();
    }

    /**
     * @param msg
     * @param positiveListener
     */
    public void showAlertDialogWithButton(String msg, DialogInterface.OnClickListener positiveListener) {
        if (mAlertDialogBuilder == null) {
            mAlertDialogBuilder = new AlertDialog.Builder(this);
        }
        mAlertDialogBuilder.setTitle(null).setMessage(msg).setPositiveButton("确定", positiveListener).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        mAlertDialog = mAlertDialogBuilder.show();
    }

    public void dismissAlertDialog() {
        if (mAlertDialog != null && mAlertDialog.isShowing())
            mAlertDialog.dismiss();
    }

    /**
     * @param msg 消息
     */
    public void showProgressDialog(String msg) {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(msg);
        //可取消
        mProgressDialog.setCancelable(true);
        //不显示进度
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    //初始化SharePreferences
    public void initSharePreferences(String name) {
        mSharePreferences = getSharedPreferences(name, MODE_PRIVATE);
        mEditor = mSharePreferences.edit();
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
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
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
