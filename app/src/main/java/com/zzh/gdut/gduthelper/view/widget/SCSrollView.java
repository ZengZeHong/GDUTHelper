package com.zzh.gdut.gduthelper.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by ZengZeHong on 2016/10/15.
 */

public class SCSrollView extends ScrollView {
    private static final String TAG = "SCSrollView";
    public SCSrollView(Context context) {
        super(context);
    }

    public SCSrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SCSrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

}
