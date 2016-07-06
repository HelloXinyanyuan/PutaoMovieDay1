package com.whunf.putaomovieday1.module.movie.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.whunf.putaomovieday1.module.movie.resp.entity.Playdate;

import java.util.List;

/**
 * 购票日期
 *
 * @author lxh
 */
public class PlaydateListResp extends BaseResp {
    /**
     * Playdate集合
     */
    @JSONField(name="data")
    private List<Playdate> playdateList;

    /**
     * 获得Playdate 集合
     */
    public List<Playdate> getPlaydateList() {
        return playdateList;
    }

    /**
     * 设置Playdate集合
     */
    public void setPlaydateList(List<Playdate> playdateList) {
        this.playdateList = playdateList;
    }


    @Override
    public String toString() {
        return "PlaydateListResp [playdateList=" + playdateList + "]";
    }

}