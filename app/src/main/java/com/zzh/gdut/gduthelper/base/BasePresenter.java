package com.zzh.gdut.gduthelper.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by ZengZeHong on 2016/7/15.
 * 主要解决绑定Activity或者Fragment时引起的内容泄露，默认是强引用，所以要改为弱引用的形式
 */
public abstract class BasePresenter<V> {
    //View接口的弱引用
    protected Reference<V> mViewRef;

    //建立关联
    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView() {
        return mViewRef.get();
    }

    //判断是否与View建立关联
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    //解除关联
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

}
