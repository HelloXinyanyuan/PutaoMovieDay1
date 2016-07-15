package com.whunf.putaomovieday1.common.version;

import com.whunf.putaomovieday1.module.movie.resp.BaseResp;

/**
 * Created by Administrator on 2016/7/15.
 */
public class QueryVersionResp extends BaseResp {
    public String remark;
    public String down_url;

    @Override
    public String toString() {
        return "QueryVersionResp{" +
                "remark='" + remark + '\'' +
                ", down_url='" + down_url + '\'' +
                '}';
    }
}
