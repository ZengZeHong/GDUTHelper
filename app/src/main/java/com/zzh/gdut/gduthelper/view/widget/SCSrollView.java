package com.zzh.gdut.gduthelper.view.widget;

import android.content.Context;
import android.util.AttributeSet;
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

/*    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Log.e(TAG, "onInterceptTouchEvent: ACTION_DOWN" );
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                Log.e(TAG, "onInterceptTouchEvent: ACTION_MOVE" );
                return true;
            }
            case MotionEvent.ACTION_UP: {
                Log.e(TAG, "onInterceptTouchEvent: DOWN" );
            }
            break;
        }
        return false;
    }*/
}
