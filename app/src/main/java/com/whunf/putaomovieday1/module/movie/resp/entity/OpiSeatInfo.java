package com.whunf.putaomovieday1.module.movie.resp.entity;

import java.util.List;

public class OpiSeatInfo {
    /**
     * 内容提供商id【必选】 说明：如代表格瓦拉，中影等。在此处确定cp
     */
    private long cpid;

    /**
     * 场次id【必选】
     */
    private long mpid;

    /**
     * 影片id【必选】
     */
    private long movieid;

    /**
     * 影院id【必选】
     */
    private String cinemaid;

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
     * 售价【必选】
     */
    private int price;

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
     * 座位锁定时间【必选】 说明：当用户下单以后，座位会为用户锁住一定的时间，用户在lockminute内不支付，则座位会被释放
     */
    private int lockminute;

    /**
     * 最多买座位数【必选】 说明：每次最多选择座位的数量
     */
    private int maxseat;

    /**
     * cp定制的参数
     */
    private String cpparam;

    /**
     * 座位行信息
     */
    private List<SeatRow> seatRowList;

    /**
     * 获得内容提供商id【必选】 说明：如代表格瓦拉，中影等。在此处确定cp
     */
    public long getCpid() {
        return cpid;
    }

    /**
     * 设置内容提供商id
     */
    public void setCpid(long cpid) {
        this.cpid = cpid;
    }

    /**
     * 获得场次id【必选】
     */
    public long getMpid() {
        return mpid;
    }

    /**
     * 设置场次id
     */
    public void setMpid(long mpid) {
        this.mpid = mpid;
    }

    /**
     * 获得影片id【必选】
     */
    public long getMovieid() {
        return movieid;
    }

    /**
     * 设置影片id
     */
    public void setMovieid(long movieid) {
        this.movieid = movieid;
    }

    /**
     * 获得影院id【必选】
     */
    public String getCinemaid() {
        return cinemaid;
    }

    /**
     * 设置影院id
     */
    public void setCinemaid(String cinemaid) {
        this.cinemaid = cinemaid;
    }

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
     * 获得售价【必选】
     */
    public int getPrice() {
        return price;
    }

    /**
     * 设置售价
     */
    public void setPrice(int price) {
        this.price = price;
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
     * 获得座位锁定时间【必选】 说明：当用户下单以后，座位会为用户锁住一定的时间，用户在lockminute内不支付，则座位会被释放
     */
    public int getLockminute() {
        return lockminute;
    }

    /**
     * 设置座位锁定时间
     */
    public void setLockminute(int lockminute) {
        this.lockminute = lockminute;
    }

    /**
     * 获得最多买座位数【必选】 说明：每次最多选择座位的数量
     */
    public int getMaxseat() {
        // add by lxh 2015年4月18日.如果没有给最大座位数就直接写死4个
        return (maxseat <= 0 || maxseat > 4) ? 4 : maxseat;
    }

    /**
     * 设置最多买座位数
     */
    public void setMaxseat(int maxseat) {
        this.maxseat = maxseat;
    }

    public String getCpparam() {
        return cpparam;
    }

    public void setCpparam(String cpparam) {
        this.cpparam = cpparam;
    }

    /**
     * 获得影厅排集合
     */
    public List<SeatRow> getSeatRowList() {
        return seatRowList;
    }

    /**
     * 设置影厅排集合
     */
    public void setSeatRowList(List<SeatRow> seatRowList) {
        this.seatRowList = seatRowList;
    }



    @Override
    public String toString() {
        return "OpiSeatInfo [cpid=" + cpid + ", mpid=" + mpid + ", movieid=" + movieid + ", cinemaid=" + cinemaid
                + ", moviename=" + moviename + ", cinemaname=" + cinemaname + ", playtime=" + playtime + ", price="
                + price + ", servicefee=" + servicefee + ", language=" + language + ", edition=" + edition
                + ", roomname=" + roomname + ", lockminute=" + lockminute + ", maxseat=" + maxseat + ", cpparam="
                + cpparam + ", seatRowList=" + seatRowList + "]";
    }

}