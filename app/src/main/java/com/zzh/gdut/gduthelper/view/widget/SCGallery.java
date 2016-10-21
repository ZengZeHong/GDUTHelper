package com.zzh.gdut.gduthelper.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;

/**
 * Created by ZengZeHong on 2016/10/20.
 *
 */

public class SCGallery extends Gallery {
    private static final String TAG = "SCGallery";

    public SCGallery(Context context) {
        super(context);
    }

    public SCGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 启动getChildStaticTransformation，4.1后要开启硬件加速，这里要判断
        if (android.os.Build.VERSION.SDK_INT <= 15) {
            setStaticTransformationsEnabled(true);
        }
    }

    public SCGallery(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        // TODO Auto-generated method stub
        if (android.os.Build.VERSION.SDK_INT > 15) {
            return false;
        } else {
            t.clear();
            t.setTransformationType(Transformation.TYPE_MATRIX);
            float offset = calculateOffsetOfCenter(child);
            setAnimation(child, offset);
            return true;
        }

    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean ret;
        //Android SDK 4.1
        if (android.os.Build.VERSION.SDK_INT > 15) {
            float offset = calculateOffsetOfCenter(child);
            setAnimation(child, offset);
            ret = super.drawChild(canvas, child, drawingTime);
        } else {
            ret = super.drawChild(canvas, child, drawingTime);
        }
        return ret;
    }


    //获取父控件中心点 X 的位置
    protected int getCenterOfCoverflow() {
        return ((getWidth() - getPaddingLeft() - getPaddingRight()) >> 1) + getPaddingLeft();
    }

    //获取 child 中心点 X 的位置
    protected int getCenterOfView(View view) {
        return view.getLeft() + (view.getWidth() >> 1);
    }

    //计算 child 偏离 父控件中心的 offset 值， -1 <= offset <= 1
    protected float calculateOffsetOfCenter(View view) {
        final int pCenter = getCenterOfCoverflow();
        final int cCenter = getCenterOfView(view);

        float offset = (cCenter - pCenter) / (pCenter * 1.0f);
        offset = Math.min(offset, 1.0f);
        offset = Math.max(offset, -1.0f);

        return offset;
    }

    /**
     * 设置动画
     * @param child
     * @param race
     */
    private void setAnimation(View child, float race) {
        child.setRotationY(-30 * ((race)));
        child.setAlpha((float) (1 - 0.5 * Math.abs(race)));
        child.setScaleX((float) (1 - 0.45 * Math.abs(race)));
        child.setScaleY((float) (1 - 0.45 * Math.abs(race)));
    }
}
