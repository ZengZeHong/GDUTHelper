package com.zzh.gdut.gduthelper.view.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ZengZeHong on 2016/10/9.
 */

public class DependentBehavior extends CoordinatorLayout.Behavior<View> {
    private static final String TAG = "DependentBehavior";

    public DependentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 选定关心的对象
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //设置伸缩情况
        float rate = 1 - Math.abs(dependency.getTop()) / (float) dependency.getHeight();
        child.setAlpha(rate);
        child.setScaleX(rate);
        child.setScaleY(rate);
        return true;
    }
}
