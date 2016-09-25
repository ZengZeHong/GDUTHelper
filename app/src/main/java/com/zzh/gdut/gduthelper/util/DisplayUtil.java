package com.zzh.gdut.gduthelper.util;

import android.content.Context;

/**
 * 尺寸工具
 */
public class DisplayUtil {
    private static int screenHeight;
    private static int screenWidth;

    /**
     * 将dp单位转化为sp单位
     */
    public static int transferDp2Px(Context context, int dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5);
    }

    /**
     * 将sp单位转化为dp单位
     */
    public static int transferPx2Dp(Context context, int pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5);
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        if (screenHeight == 0) {
            screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        }
        return screenHeight;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        if (screenWidth == 0) {
            screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        }
        return screenWidth;
    }

}
