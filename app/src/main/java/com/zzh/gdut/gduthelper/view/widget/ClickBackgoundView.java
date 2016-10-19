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

public class ClickBackgoundView extends View {
    private static final String TAG = "ClickBackgoundView";
    private int viewWidth;
    private int viewHeight;
    private Paint mPaint;
    //圆角
    private int circleCorner = 10;
    private boolean isClick = false;
    private int currentClickX = -1;
    private int currentClickY = -1;
    private int clickX , clickY;
    private int colorId = Color.GREEN;
    private OnItemClick onItemClick;

    public interface OnItemClick {
        void onClick();
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public ClickBackgoundView(Context context) {
        super(context);
        initData(context);
    }

    public ClickBackgoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    public ClickBackgoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }


    private void initData(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        viewWidth = DisplayUtil.getScreenWidth(context) * 4 / 11 + circleCorner;
        viewHeight = viewWidth ;
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
        final RectF rectF = new RectF(0, 0, viewWidth - circleCorner, viewHeight - circleCorner);
        canvas.drawRoundRect(rectF, circleCorner, circleCorner, mPaint);
        if (isClick) {
            mPaint.setColor(Color.BLACK);
            mPaint.setAlpha(30);
            canvas.drawRoundRect(rectF, circleCorner, circleCorner, mPaint);
            mPaint.setAlpha(255);
            isClick = false;
        }
    }


    public void setBackground(int color) {
        colorId = getResources().getColor(color);
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                getParent().requestDisallowInterceptTouchEvent(true);
                clickX = (int) event.getX();
                clickY = (int) event.getY();
                isClick = true;
                invalidate();
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                currentClickX = (int) event.getX();
                currentClickY = (int) event.getY();
                if(clickX != currentClickX && clickY != currentClickY){
                    isClick = false;
                    invalidate();
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
            /*    if (currentClickX < 0 || currentClickX > viewWidth || currentClickY < 0 || currentClickY > viewHeight) {
                    isClick = false;
                    invalidate();
                }*/
                Log.e(TAG, "onTouchEvent: " + currentClickX + ">>" + currentClickY);
            }
            break;
            case MotionEvent.ACTION_UP: {
                if(clickX == currentClickX && clickY == currentClickY){
                    Log.e(TAG, "onTouchEvent: click ");
                    if (onItemClick != null)
                        onItemClick.onClick();
                    isClick = false;
                    invalidate();
                }
            }
            case MotionEvent.ACTION_CANCEL:{
                Log.e(TAG, "onTouchEvent: cancel" );
                isClick = false;
                invalidate();
            }
            break;
        }
        return super.onTouchEvent(event);
    }
}
