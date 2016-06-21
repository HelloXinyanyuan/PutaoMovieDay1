package com.whunf.putaomovieday1.common.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 * Created by Administrator on 2016/6/21.
 */
public class T {

    /**
     * 显示一个时间短的toast
     * @param context
     * @param text
     */
    public  static void  showShort(Context context, String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
