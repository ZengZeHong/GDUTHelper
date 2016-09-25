package com.zzh.gdut.gduthelper.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.zzh.gdut.gduthelper.util.DisplayUtil;

/**
 * Created by ZengZeHong on 2016/9/25.
 */

public class LoginRelativeLayout extends RelativeLayout {
    private static final String TAG = "LoginRelativeLayout";
    private Context context;
    public LoginRelativeLayout(Context context) {
        super(context);
        this.context = context;
    }

    public LoginRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public LoginRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //根据屏幕宽度来确定自身宽度
        Log.e(TAG, "onLayout: " + DisplayUtil.getScreenWidth(context));
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) getLayoutParams();
        Log.e(TAG, "onLayout: " + lp.width );
        lp.width = DisplayUtil.getScreenWidth(context) * 3 / 4;
        setLayoutParams(lp);
    }
}
