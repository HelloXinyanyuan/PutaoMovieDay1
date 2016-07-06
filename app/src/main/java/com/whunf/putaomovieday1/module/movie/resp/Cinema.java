package com.whunf.putaomovieday1.module.movie.resp;

/**
 * Created by Administrator on 2016/6/22.
 */
public class Cinema {

    /**
     * 葡萄影院id Y
     */
    private Long id;
    /**
     * 影院id Y
     */
    private String cinemaid;
    /**
     * 影院名称 Y
     */
    private String cinemaname;
    /**
     * 影院英文名称 N
     */
    private String englishname;
    /**
     * 影院Logo Y
     */
    private String logo;
    /**
     * 城市编码 Y
     */
    private String citycode;
    /**
     * 城市名称 Y
     */
    private String cityname;
    /**
     * 区县代码 N
     */
    private String countycode;
    /**
     * 区县名称 N 　 黄浦区
     */
    private String countyname;
    /**
     * 影院详细地址 Y
     */
    private String address;
    /**
     * 经度 N
     */
    private String longitude;
    /**
     * 纬度 N
     */
    private String latitude;
    /**
     * 坐标系 N 1代表谷歌（高德、soso等通用坐标系），2代表百度，3代表mapbar，传空代表无坐标
     */
    private String cs;
    /**
     * 影院评分 N
     */
    private String generalmark;
    /**
     * 爆米花套餐 N 　1表示有0表示没有
     */
    private String popcorn;
    /**
     * 电影、场次描述 N  放映10部余88场
     */
    private String countdes;
    /**
     * cp数目 N 支持的比价的cp数目
     */
    private int cpcount;
    /**
     * 价格区间 N 用-分割开，最低价放前面，最高价放后面 33-43
     */
    private String pricerange;

    /**
     * 计算当前的影院对象与用户的距离
     */
    private double distance;
    /**
     * 影院底价-最低价
     */
    private int stepPrice;
    /**
     * 当前影院最低价格的影片id
     */
    private long lowmovieid;

    public long getLowmovieid() {
        return lowmovieid;
    }

    public void setLowmovieid(long lowmovieid) {
        this.lowmovieid = lowmovieid;
    }

    public int getStepPrice() {
        return stepPrice;
    }

    public void setStepPrice(int stepPrice) {
        this.stepPrice = stepPrice;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCinemaid() {
        return cinemaid;
    }

    public void setCinemaid(String cinemaid) {
        this.cinemaid = cinemaid;
    }

    public String getCinemaname() {
        return cinemaname;
    }

    public void setCinemaname(String cinemaname) {
        this.cinemaname = cinemaname;
    }

    public String getEnglishname() {
        return englishname;
    }

    public void setEnglishname(String englishname) {
        this.englishname = englishname;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCountycode() {
        return countycode;
    }

    public void setCountycode(String countycode) {
        this.countycode = countycode;
    }

    public String getCountyname() {
        return countyname;
    }

    public void setCountyname(String countyname) {
        this.countyname = countyname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getGeneralmark() {
        return generalmark;
    }

    public void setGeneralmark(String generalmark) {
        this.generalmark = generalmark;
    }

    public String getPopcorn() {
        return popcorn;
    }

    public void setPopcorn(String popcorn) {
        this.popcorn = popcorn;
    }

    public String getCountdes() {
        return countdes;
    }

    public void setCountdes(String countdes) {
        this.countdes = countdes;
    }

    public int getCpcount() {
        return cpcount;
    }

    public void setCpcount(int cpcount) {
        this.cpcount = cpcount;
    }

    public String getPricerange() {
        return pricerange;
    }

    public void setPricerange(String pricerange) {
        this.pricerange = pricerange;
    }
}
