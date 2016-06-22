package com.whunf.putaomovieday1.module.movie.resp;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class MovieResp extends BaseResp {

    private List<Movie> data;

    public List<Movie> getData() {
        return data;
    }

    public void setData(List<Movie> data) {
        this.data = data;
    }
}
