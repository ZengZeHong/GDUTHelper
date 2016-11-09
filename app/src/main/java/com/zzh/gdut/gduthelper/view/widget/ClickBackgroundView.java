package com.zzh.gdut.gduthelper.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.zzh.gdut.gduthelper.util.DisplayUtil;

/**
 * Created by ZengZeHong on 2016/10/19.
 * 圆角背景View，动态颜色点击
 */

public class ClickBackgroundView extends View {
    private static final String TAG = "ClickBackgoundView";
    private int viewWidth;
    private int viewHeight;
    private Paint mPaint;
    //圆角
    private int circleCorner = 20;
    private boolean isClick = false;
    private int colorId = Color.GREEN;

    public ClickBackgroundView(Context context) {
        super(context);
        initData(context);
    }

    public ClickBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    public ClickBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }


    private void initData(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        viewWidth = DisplayUtil.getScreenWidth(context) * 4 / 10 ;
        viewHeight = DisplayUtil.getScreenWidth(context) * 4 / 10 - 10 ;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureLength(widthMeasureSpec, viewWidth), measureLength(heightMeasureSpec, viewHeight));
    }

    /**
     * 设置默认宽高
     *
     * @param lengthMeasureSpec
     * @param length
     * @return
     */
    private int measureLength(int lengthMeasureSpec, int length) {
        int size;
        int specMode = MeasureSpec.getMode(lengthMeasureSpec);
        int specSize = MeasureSpec.getSize(lengthMeasureSpec);
        if (specMode == MeasureSpec.UNSPECIFIED)
            size = length;
        else
            size = specSize;
        return size;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (colorId != -1)
            mPaint.setColor(colorId);
        final RectF rectF = new RectF(0, 5, viewWidth , viewHeight - 5);
        canvas.drawRoundRect(rectF, circleCorner, circleCorner, mPaint);
        //设置点击背景
        if (isClick) {
            mPaint.setColor(Color.BLACK);
            mPaint.setAlpha(30);
            canvas.drawRoundRect(rectF, circleCorner, circleCorner, mPaint);
            mPaint.setAlpha(255);
            isClick = false;
        }
    }


    /**
     * 设置点击背景改变
     * @param isClick
     */
    public void setClick(boolean isClick){
        this.isClick = isClick;
        invalidate();
    }


    /**
     * 设置背景颜色
     * @param color
     */
    public void setBackground(int color) {
        colorId = color;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                isClick = true;
                invalidate();
                Log.e(TAG, "onTouchEvent: down");
            }break;
        }
        return false;
    }
}
