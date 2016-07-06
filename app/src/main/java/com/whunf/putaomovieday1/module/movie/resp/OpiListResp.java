package com.whunf.putaomovieday1.module.movie.resp;


import com.alibaba.fastjson.annotation.JSONField;
import com.whunf.putaomovieday1.module.movie.resp.entity.Opi;

import java.util.List;


/**
 * 场次列表
 *
 * @author lxh
 */
public class OpiListResp extends BaseResp {

    /**
     * Opi集合
     */
    @JSONField(name = "data")
    private List<Opi> opiList;

    /**
     * 获得Opi 集合
     */
    public List<Opi> getOpiList() {
        return opiList;
    }

    /**
     * 设置Opi集合
     */
    public void setOpiList(List<Opi> opiList) {
        this.opiList = opiList;
    }


    @Override
    public String toString() {
        return "OpiListResp [opiList=" + opiList + "]";
    }

}
