package com.whunf.putaomovieday1.module.movie.resp;

import com.whunf.putaomovieday1.module.movie.resp.entity.AddTicketOrder;

/**
 * 创建订单
 * 
 * @author lxh
 */
public class AddTicketOrderResp extends BaseResp
{

    private AddTicketOrder data;

    public AddTicketOrder getData()
    {
        return data;
    }

    public void setData(AddTicketOrder data)
    {
        this.data = data;
    }



    @Override
    public String toString()
    {
        return "AddTicketOrderResp [data=" + data + "]";
    }

}
