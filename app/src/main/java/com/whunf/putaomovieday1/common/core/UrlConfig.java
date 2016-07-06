package com.whunf.putaomovieday1.common.core;

/**
 * Created by Administrator on 2016/7/5.
 * 请求参数配置类
 */
public class UrlConfig {

    private static final String BASE_PATH = "http://api.putao.so/sbiz";

    /**
     * 电影业务相关的路径
     */
    public interface MoviePath {
        /**
         * 影片列表接口地址
         */
        String MOVIE_LIST = BASE_PATH + "/movie/list";
        /**
         * 影院列表接口地址
         */
        String CINEMA_LIST = BASE_PATH + "/movie/cinema/list";

        /** 电影详情 */
        public static final String MOVIE_DETAIL = BASE_PATH + "/movie/detail";

        /** 电影影评 */
        public static final String MOVIE_REVIEWS = BASE_PATH + "/movie/reviews";

        /** 播放日期列表 */
        public static final String PLAYDATE_LIST = BASE_PATH + "/movie/playdate";

        /** 场次列表 */
        public static final String OPI_LIST = BASE_PATH + "/movie/showtime";

        /** 座位信息 */
        public static final String OPI_SEAT_INFO = BASE_PATH + "/movie/get_seat_info";

        /** 电影下单 */
        public static final String ADD_TICKET_ORDER = BASE_PATH + "/movie/create_order";

        /** 电影取消订单 */
        public static final String CANCEL_ORDER = BASE_PATH + "/movie/cancel_order";

        /** 城市列表 */
        public static final String CITY_LIST = BASE_PATH + "/movie/city";

        /** 区县列表 */
        public static final String COUNTY_LIST = BASE_PATH + "/movie/county";

        /** 通兑券列表 */
        public static final String COMM_TICKET_LIST = BASE_PATH + "/movie/cinema/ticket/list";

        /** 创建通兑券订单 */
        public static final String COMM_TICKET_ORDER_CREATE = BASE_PATH + "/movie/cinema/ticket/create_order";

        /** 播放日期列表——过滤影院 */
        public static final String PLAYDATE_LIST_FILTERCINEMA = BASE_PATH + "/movie/playdate/list";
    }


}
