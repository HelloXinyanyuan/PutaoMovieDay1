package com.whunf.putaomovieday1.common.util;

import android.content.Context;

/**
 * 图像操作类
 */
public class GraphicUtil {
    private static final String TAG = "GraphicUtil";

    /**
     * dip转为 px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px 转为 dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获得屏幕的宽
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        final int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        return widthPixels;
    }

    /**
     * 获得android设备屏幕高
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        final int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        return heightPixels;
    }

}
