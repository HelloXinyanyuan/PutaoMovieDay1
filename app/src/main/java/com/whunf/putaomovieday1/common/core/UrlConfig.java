package com.whunf.putaomovieday1.common.core;

/**
 * Created by Administrator on 2016/7/5.
 * 请求参数配置类
 */
public class UrlConfig {

    public static final String BASE_PATH = "http://api.putao.so/sbiz/";

    /**
     * 电影业务相关的路径
     */
    public interface MoviePath {
        /**
         * 影片列表接口地址
         */
        String MOVIE_LIST = BASE_PATH + "movie/list";
        /**
         * 影院列表接口地址
         */
        String CINEMA_LIST = BASE_PATH + "movie/cinema/list";
    }


}
