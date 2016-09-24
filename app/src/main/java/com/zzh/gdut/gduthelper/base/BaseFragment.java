package com.zzh.gdut.gduthelper.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * 基础Fragment类
 * 带Presenter和View Interface的BaseFragment
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>> extends BaseNormalFragment implements View.OnClickListener {
    protected T mPresenter;

    protected abstract T createPresenter();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }
}
