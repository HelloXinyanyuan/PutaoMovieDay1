package com.whunf.putaomovieday1.common.storage.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.whunf.putaomovieday1.common.storage.db.entity.CityBean;
import com.whunf.putaomovieday1.common.util.NumberUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/8.
 * <p/>
 * 专门用于管理城市表数据库的类
 */
public class CityTableHand {
    private DbHelper dbHelper;
    private Context mContext;

    public CityTableHand(Context context) {
        dbHelper = new DbHelper(context);
        this.mContext = context;
    }

    public void init() {
        if (!cityIsLoad()) {
            List<CityBean> citys = new ArrayList<>();
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(mContext.getAssets().open("city_list.csv"), "UTF-8"));
                String lineStr = null;
                while ((lineStr = br.readLine()) != null) {
                    String[] strArr = lineStr.split(",");
                    //提取全拼、简拼数据
                    StringBuilder quanping = new StringBuilder();
                    StringBuilder jianping = new StringBuilder();
                    String[] pyArr = strArr[1].split(" ");//根据空格拆分
                    for (int i = 0; i < pyArr.length; i++) {
                        if (i % 2 == 0) {
                            quanping.append(pyArr[i]);
                            jianping.append(pyArr[i].charAt(0));
                        }
                    }
                    CityBean cityBean = new CityBean(strArr[0], quanping.toString(), jianping.toString(),
                            NumberUtil.parseIntSafe(strArr[2]), NumberUtil.parseIntSafe(strArr[3]), NumberUtil.parseIntSafe(strArr[4]),
                            strArr[5], NumberUtil.parseIntSafe(strArr[6]));
                    citys.add(cityBean);
                }
                //将城市集合保存
                insertCitys(citys);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    /**
     * 数据是否已经加载过，如果加载过查询的结果有数据
     *
     * @return
     */
    public boolean cityIsLoad() {
        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        Cursor query = readableDatabase.query(CityTable.TABLE_NAME, new String[]{CityTable.Col.CITY_NAME}, null, null, null, null, null);
        if (query != null) {
            try {
                if (query.moveToNext()) {
                    query.close();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                query.close();
            }
        }
        return false;
    }

    /**
     * 插入所有城市
     *
     * @param citys
     * @return
     */
    public boolean insertCitys(List<CityBean> citys) {
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        for (CityBean city : citys) {
            ContentValues values = new ContentValues();
            values.put(CityTable.Col.CITY_NAME, city.getCityName());
            values.put(CityTable.Col.CITY_PY, city.getCityPy());
            values.put(CityTable.Col.CITY_SPY, city.getCitySpy());
            values.put(CityTable.Col.SELF_ID, city.getSelfId());
            values.put(CityTable.Col.PARENT_ID, city.getParentId());
            values.put(CityTable.Col.CITY_TYPE, city.getCityType());
            values.put(CityTable.Col.DISTRICT_CODE, city.getDistrictCode());
            values.put(CityTable.Col.CITY_HOT, city.getCityHot());
            writableDatabase.insert(CityTable.TABLE_NAME, null, values);
        }
        return true;
    }

    public List<CityBean> getHotCitys() {
        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        //查询热点城市
        Cursor query = readableDatabase.query(CityTable.TABLE_NAME, null, CityTable.Col.CITY_HOT + "=1", null, null, null, null);
        List<CityBean> citys = getCityBeenList(query);
        return citys;
    }

    @NonNull
    private List<CityBean> getCityBeenList(Cursor query) {
        List<CityBean> citys = new ArrayList<>();
        try {
            if (query != null) {
                while (query.moveToNext()) {
                    String cityname = query.getString(query.getColumnIndex(CityTable.Col.CITY_NAME));
                    if (cityname.endsWith("市")){//去掉市
                        cityname=cityname.substring(0,cityname.length()-1);
                    }
                    String py = query.getString(query.getColumnIndex(CityTable.Col.CITY_PY));
                    String spy = query.getString(query.getColumnIndex(CityTable.Col.CITY_SPY));
                    int sid = query.getInt(query.getColumnIndex(CityTable.Col.SELF_ID));
                    int pid = query.getInt(query.getColumnIndex(CityTable.Col.PARENT_ID));
                    int ctype = query.getInt(query.getColumnIndex(CityTable.Col.CITY_TYPE));
                    String distrcode = query.getString(query.getColumnIndex(CityTable.Col.DISTRICT_CODE));
                    int chot = query.getInt(query.getColumnIndex(CityTable.Col.CITY_HOT));
                    CityBean cityBean = new CityBean(cityname, py, spy, sid, pid, ctype, distrcode, chot);
                    citys.add(cityBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (query != null) {
                query.close();
            }
        }
        return citys;
    }

    public List<CityBean> getYijiCitys() {
        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        //查询热点城市
        Cursor query = readableDatabase.query(CityTable.TABLE_NAME, null, CityTable.Col.CITY_TYPE + "=2", null, null, null, null);
        List<CityBean> citys = getCityBeenList(query);
        return citys;
    }


}
