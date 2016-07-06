package com.whunf.putaomovieday1.module.movie.resp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 场次item
 */
public class Opi {

    /**
     * 影片名称【必选】
     */
    private String moviename;

    /**
     * 影院名称【必选】
     */
    private String cinemaname;

    /**
     * 放映时间【必选】 说明：11位long类型数据，如1426744200000
     */
    private long playtime;

    /**
     * 服务费【必选】
     */
    private int servicefee;

    /**
     * 语言【必选】 说明：中文、英语……
     */
    private String language;

    /**
     * 版本【必选】 说明：3D、4D、IMAX、2D、双机3D、IMAX3D
     */
    private String edition;

    /**
     * 影厅名称【必选】
     */
    private String roomname;

    /**
     * 关闭购票时间【必选】 说明：11位long类型数据，如1426744200000
     */
    private long closetime;

    /**
     * 影院原价【必选】 说明：以分为单位
     */
    private int originalprice;

    /**
     * 是否展开cplist
     */
    private boolean expand;

    /**
     * 获得影片名称【必选】
     */
    public String getMoviename() {
        return moviename;
    }

    /**
     * 设置影片名称
     */
    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    /**
     * 获得影院名称【必选】
     */
    public String getCinemaname() {
        return cinemaname;
    }

    /**
     * 设置影院名称
     */
    public void setCinemaname(String cinemaname) {
        this.cinemaname = cinemaname;
    }

    /**
     * 获得放映时间【必选】 说明：11位long类型数据，如1426744200000
     */
    public long getPlaytime() {
        return playtime;
    }

    /**
     * 设置放映时间
     */
    public void setPlaytime(long playtime) {
        this.playtime = playtime;
    }

    /**
     * 获得服务费【必选】
     */
    public int getServicefee() {
        return servicefee;
    }

    /**
     * 设置服务费
     */
    public void setServicefee(int servicefee) {
        this.servicefee = servicefee;
    }

    /**
     * 获得语言【必选】 说明：中文、英语……
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 设置语言
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * 获得版本【必选】 说明：3D、4D、IMAX、2D、双机3D、IMAX3D
     */
    public String getEdition() {
        return edition;
    }

    /**
     * 设置版本
     */
    public void setEdition(String edition) {
        this.edition = edition;
    }

    /**
     * 获得影厅名称【必选】
     */
    public String getRoomname() {
        return roomname;
    }

    /**
     * 设置影厅名称
     */
    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    /**
     * 获得关闭购票时间【必选】 说明：11位long类型数据，如1426744200000
     */
    public long getClosetime() {
        return closetime;
    }

    /**
     * 设置关闭购票时间
     */
    public void setClosetime(long closetime) {
        this.closetime = closetime;
    }

    /**
     * 获得影院原价【必选】 说明：以分为单位
     */
    public int getOriginalprice() {
        return originalprice;
    }

    /**
     * 设置影院原价
     */
    public void setOriginalprice(int originalprice) {
        this.originalprice = originalprice;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    /**
     * Cp集合
     */
    private List<Cp> cpList = new ArrayList<Cp>();

    /**
     * 获得Cp 集合
     */
    public List<Cp> getCpList() {
        return cpList;
    }

    /**
     * 设置Cp集合
     */
    public void setCpList(List<Cp> cpList) {
        this.cpList = cpList;
    }

    @Override
    public String toString() {
        return "Opi [moviename=" + moviename + ", cinemaname=" + cinemaname + ", playtime=" + playtime
                + ", servicefee=" + servicefee + ", language=" + language + ", edition=" + edition + ", roomname="
                + roomname + ", closetime=" + closetime + ", originalprice=" + originalprice + ", expand=" + expand
                + ", cpList=" + cpList + "]";
    }

}