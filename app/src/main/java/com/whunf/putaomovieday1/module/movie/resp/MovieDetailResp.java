package com.whunf.putaomovieday1.module.movie.resp;

import com.whunf.putaomovieday1.module.movie.resp.entity.Movie;

/**
 * Created by Administrator on 2016/7/4.
 * 电影详情的返回
 */
public class MovieDetailResp extends  BaseResp {

    private Movie data;

    public Movie getData() {
        return data;
    }

    public void setData(Movie data) {
        this.data = data;
    }
}
