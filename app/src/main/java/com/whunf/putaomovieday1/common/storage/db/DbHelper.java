package com.whunf.putaomovieday1.common.storage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.whunf.putaomovieday1.common.core.AppConfig;
import com.whunf.putaomovieday1.common.util.LogUtil;

/**
 * Created by Administrator on 2016/7/8.
 * 数据库管理类
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "PTMovie.db";
    public static final int DB_VERSION = AppConfig.DB_VERSION;
    private static final String TAG = "DbHelper";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String cityTableSql = "CREATE TABLE " + CityTable.TABLE_NAME + "("
//        +CityTable.Col._ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + CityTable.Col.CITY_NAME + " VARCHAR(20),"
                + CityTable.Col.CITY_PY + " VARCHAR(20),"
                + CityTable.Col.CITY_SPY + " VARCHAR(20),"
                + CityTable.Col.SELF_ID + " INTEGER,"
                + CityTable.Col.PARENT_ID + " INTEGER,"
                + CityTable.Col.CITY_TYPE + " INTEGER,"
                + CityTable.Col.DISTRICT_CODE + " VARCHAR(20),"
                + CityTable.Col.CITY_HOT + " INTEGER"
                + ")";
        LogUtil.d(TAG, "onCreate() called with: " + "cityTableSql = [" + cityTableSql + "]");
        db.execSQL(cityTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
