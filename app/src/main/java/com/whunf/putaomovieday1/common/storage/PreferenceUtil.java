package com.whunf.putaomovieday1.common.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.whunf.putaomovieday1.common.core.PMApplication;

/**
 * Created by Administrator on 2016/6/30.
 * SharedPreferences工具类，便于储存应用中的数据
 */
public class PreferenceUtil {

    private static final String SP_NAME = "movie_sp";
    /**
     * 引导页版本号的key
     */
    public static final String KEY_SPLASH_LOAD_VERSION = "KEY_SPLASH_LOAD_VERSION";
    /**
     * 首页城市
     */
    public static final String KEY_HOME_CITY = "KEY_HOME_CITY";

    public static void save(String key, String value) {
        Context context = PMApplication.getInstance();
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    /**
     * 保存boolean类型数据
     *
     * @param key
     * @param value
     */
    public static void save(String key, boolean value) {
        Context context = PMApplication.getInstance();
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 存储int类型的数据
     *
     * @param key
     * @param value
     */
    public static void save(String key, int value) {
        Context context = PMApplication.getInstance();
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }


    /**
     * 加载一个Boolean的配置数据
     *
     * @param key 指定key
     * @return
     */
    public static boolean loadBoolean(String key) {
        return loadBoolean(key, false);
    }

    /**
     * 加载一个Boolean的配置数据
     *
     * @param key      指定key
     * @param defValue 如果没有值，给的默认值
     * @return
     */
    public static boolean loadBoolean(String key, boolean defValue) {
        Context context = PMApplication.getInstance();
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 加载一个int类型的数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public static int loadInt(String key, int defValue) {
        Context context = PMApplication.getInstance();
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    /**
     * 加载一个String类型的数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public static String loadString(String key, String defValue) {
        Context context = PMApplication.getInstance();
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }
}
