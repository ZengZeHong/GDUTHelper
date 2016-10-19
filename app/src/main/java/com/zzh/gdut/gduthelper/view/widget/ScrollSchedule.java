package com.zzh.gdut.gduthelper.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.zzh.gdut.gduthelper.util.DisplayUtil;

/**
 * Created by ZengZeHong on 2016/10/19.
 * 自定义可滑动缩放布局
 */

public class ScrollSchedule extends LinearLayout {
    private static final String TAG = "ScrollSchedule";
    private int viewWidth;
    private int viewHeight;
    private int itemPadding;

    public ScrollSchedule(Context context) {
        super(context);
        initData(context);
    }

    public ScrollSchedule(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        initAttrs(context, attrs);

    }

    public ScrollSchedule(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //默认高度则为子View的高度
        viewHeight = getChildAt(0).getMeasuredHeight();
        Log.e(TAG, "onMeasure: viewHeight " + viewHeight);
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            //要调用这个方法，不要再onLayout里面无法获取子View的宽高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }

        itemPadding = viewHeight / 8;
        Log.e(TAG, "onMeasure: padding " + itemPadding);
        //设置ViewGroup的宽度，多出两个空白位置
        setMeasuredDimension(viewHeight + (viewHeight - itemPadding) * (getChildCount() + 1)
                , viewHeight);
    }


    private void initData(Context context) {
        //默认宽度为屏幕的宽度
        viewWidth = DisplayUtil.getScreenWidth(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
      /*  TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScrollSchedule);
        itemPadding = ta.getDimension(R.styleable.ScrollSchedule_itemOffest, 10);
        ta.recycle();*/
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // super.onLayout(changed, l, t, r, b);
        for (int i = 0; i < getChildCount(); i++) {
            //子view显示位置
            ScheduleRelativeLayout childView = (ScheduleRelativeLayout) getChildAt(i);
            Log.e(TAG, "onLayout: " + childView.getRight() + ">>" + childView.getMeasuredWidth());
            int width = childView.getMeasuredWidth();
            int height = childView.getMeasuredHeight();
            childView.layout((width - itemPadding) * (i+1), 0, width + (width - itemPadding) * (i+1), height);
        }
    }
}
