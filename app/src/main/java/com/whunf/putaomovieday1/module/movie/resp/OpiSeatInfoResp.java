package com.whunf.putaomovieday1.module.movie.resp;

import com.whunf.putaomovieday1.module.movie.resp.entity.OpiSeatInfo;

/**
 * 座位信息
 * 
 * @author lxh
 */
public class OpiSeatInfoResp extends BaseResp
{

    private OpiSeatInfo data;

    public OpiSeatInfo getData()
    {
        return data;
    }

    public void setData(OpiSeatInfo data)
    {
        this.data = data;
    }


    @Override
    public String toString()
    {
        return "OpiSeatInfoResp [data=" + data + "]";
    }

}
