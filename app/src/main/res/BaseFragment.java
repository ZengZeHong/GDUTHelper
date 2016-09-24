package com.rdc.spg.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

/**
 * 基础Fragment类
 * startActivity , AlertDialog
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment implements View.OnClickListener {
    protected T mPresenter;

    protected abstract T createPresenter();

    //Fragment所属的Activity
    protected BaseActivity mActivity;

    protected Gson gson = new Gson();

    protected abstract void initAttributes();

    protected abstract int getLayoutId();

    protected abstract void initView(View view, Bundle savedInstanceState);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        initAttributes();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view, savedInstanceState);
        return view;
    }

    /**
     * @param context 当一个Fragment第一次被附加到Activity上时显示，先调用onAttach再调用onCreate
     *                在这里获取到Fragment所属的Activity的引用，之后直接使用mActivity而不使用getActivity()
     *                getActivity()有时会在fragment销毁时返回null，造成NullPointException
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }
}
