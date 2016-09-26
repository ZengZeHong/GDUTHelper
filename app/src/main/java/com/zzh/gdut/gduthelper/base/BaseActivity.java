package com.zzh.gdut.gduthelper.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;


/**
 * Created by ZengZeHong on 2016/7/15.
 * 带Presenter和View Interface的BaseActivity
 *
 * @param <V>View接口
 * @param <T>Presenter
 */

public abstract class BaseActivity<V, T extends BasePresenter<V>> extends BaseNormalActivity implements View.OnClickListener {
    //Presenter对象
    protected T mPresenter;

    //获取Presenter;
    protected abstract T createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (mPresenter != null)
            mPresenter.attachView((V) this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

}
