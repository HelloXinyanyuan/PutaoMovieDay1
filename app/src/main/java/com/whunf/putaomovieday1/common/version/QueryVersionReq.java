package com.whunf.putaomovieday1.common.version;

import com.whunf.putaomovieday1.module.user.req.BaseReqJson;

/**
 * Created by Administrator on 2016/7/15.
 *
 * 查询版本的json请求内容
 */
public class QueryVersionReq extends BaseReqJson {
    public QueryVersionReq() {
        super("00002");
    }
}
