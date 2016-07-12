package com.whunf.putaomovieday1.module.user.resp;

import com.whunf.putaomovieday1.module.movie.resp.BaseResp;
import com.whunf.putaomovieday1.module.user.entity.OrderQueryData;

/**
 * Created by Administrator on 2016/7/12.
 */
public class OrderListResp extends BaseResp {

private    OrderQueryData data;

    public OrderQueryData getData() {
        return data;
    }

    public void setData(OrderQueryData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OrderListResp{" +
                "data=" + data +
                '}';
    }
}
