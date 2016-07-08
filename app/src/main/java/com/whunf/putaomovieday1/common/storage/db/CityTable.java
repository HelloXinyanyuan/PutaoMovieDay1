package com.whunf.putaomovieday1.common.storage.db;

/**
 * Created by Administrator on 2016/7/8.
 * 城市表的描述类
 */
public class CityTable {
    public static final class Col{
        public static final String CITY_NAME = "cityName";
        public static final String CITY_PY = "cityPy";//全拼，比如深圳的全拼是shenzhen
        public static final String CITY_SPY = "citySpy";//简拼，比如深圳的简拼是sz
        public static final String SELF_ID = "selfId";//自己的id
        public static final String PARENT_ID = "parentId";//父id
        public static final String CITY_TYPE = "cityType";//城市类别，一级城市二级城市
        public static final String DISTRICT_CODE = "districtCode";//城市唯一编码
        public static final String CITY_HOT = "cityHot";//是否是热点城市
    }

    public static final String TABLE_NAME = "CityTable";


}
