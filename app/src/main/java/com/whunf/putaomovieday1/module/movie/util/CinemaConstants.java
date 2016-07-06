package com.whunf.putaomovieday1.module.movie.util;

/**
 * 
 ************************************************ <br>
 * 文件名称: CinemaConstants.java <br>
 * 版权声明: <b>深圳市葡萄信息技术有限公司</b> 版权所有 <br>
 * 创建人员: lxh <br>
 * 文件描述: 电影业务相关常量<br>
 * 修改时间: 2015-6-25 下午5:11:25 <br>
 * 修改历史: 2015-6-25 1.00 初始版本 <br>
 ************************************************* 
 */
public class CinemaConstants
{
    /** 格式化日期的格式 年月日 */
    public static final String DATE_PATTERN_YDM = "yyyy-MM-dd";

    /** 格式化日期的格式 年月日时分秒 */
    public static final String DATE_PATTERN_YDMHMS = "yyyy-MM-dd HH:mm:ss";

    /** 格式化日期的格式 年月日时分 */
    public static final String DATE_PATTERN_YDMHM = "yyyy-MM-dd HH:mm";

    /** 电影订单详情的格式化日期的格式 月日时分 */
    public static final String DATE_PATTERN_MORDER = "MM月dd日 HH:mm";

    /** 电影订单详情的格式化日期的格式 年月日 */
    public static final String DATE_PATTERN_YDM_CN = "yyyy年MM月dd日";

    /** 影院纬度 */
    public static final String EXTRA_CINEMA_LAT = "cinema_lat";

    /** 影院经度 */
    public static final String EXTRA_CINEMA_LNG = "cinema_lng";

    /** 影院经纬度坐标系 */
    public static final String EXTRA_CINEMA_CS = "cinema_cs";

    /** 影院选中排期 */
    public static final String EXTRA_CINEMA_SELECT_PLAYDATE = "cinema_select_playdate";

    /** 影院名字 */
    public static final String EXTRA_CINEMA_NAME = "cinema_name";

    /** 通兑券 */
    public static final String EXTRA_COMM_TICKET_ORDER = "comm_ticket_order";

    /** 影院地址 */
    public static final String EXTRA_CINEMA_ADDRESS = "cinema_address";

    /** 场次id */
    public static final String EXTRA_MPID = "mpid";

    /** 葡萄提供的影院id */
    public static final String EXTRA_CINEMA_ID = "cinema_id";

    // /** cp提供的影院id */
    // public static final String EXTRA_CINEMAID = "cinemaid";

    // /** 葡萄提供的电影id */
    // public static final String EXTRA_MOVIE_ID = "movie_id";

    /** cp提供的电影id */
    public static final String EXTRA_MOVIEID = "movieid";

    /** 电影详情 */
    public static final String EXTRA_MOVIE_DETAIL = "movie_detail";

    /** 电影名字 */
    public static final String EXTRA_MOVIE_NAME = "movie_name";

    /** 城市名称 */
    public static final String EXTRA_CITYNAME = "cityname";

    /** cpid号 */
    public static final String EXTRA_CPID = "cpid";

    /** cp定制的参数 */
    public static final String EXTRA_CPPARAM = "cpparam";

    public static final String EXTRA_MOVIE_PHOTO_URL = "movie_photo_url";

    public static final String EXTRA_MOVIE_PLAYTIME = "movie_playtime";

    public static final String MOVIE_ORDER_DETAIL = "movie_order_detail";

    public static final String MOVIE_ORDER_SEAT = "movie_order_seat";
    
    /** 座位单价 */
    public static final String MOVIE_SEAT_UNITPRICE = "movie_seat_unitprice";

}
