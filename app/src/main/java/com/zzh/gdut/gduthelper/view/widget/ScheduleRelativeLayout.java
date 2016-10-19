package com.zzh.gdut.gduthelper.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zzh.gdut.gduthelper.util.DisplayUtil;

/**
 * Created by ZengZeHong on 2016/10/19.
 */

public class ScheduleRelativeLayout extends RelativeLayout {
    private static final String TAG = "LoginRelativeLayout";
    private Context context;
    private int offest = 0;
    public ScheduleRelativeLayout(Context context) {
        super(context);
        this.context = context;
    }

    public ScheduleRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ScheduleRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width =  DisplayUtil.getScreenWidth(context) * 4 / 11 + offest;
        params.height = params.width;
        setLayoutParams(params);
    }
}
