package com.whunf.putaomovieday1.module.movie.resp;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/22.
 */
public class Movie implements Serializable {

    /**
     * 影片id Y
     */
    private Long movieid;
    /**
     * 影片名称 Y
     */
    private String moviename;
    /**
     * 影片英文名称 N
     */
    private String englishname;
    /**
     * 语言 N 　 中文
     */
    private String language;
    /**
     * 影片类型 N
     */
    private String type;
    /**
     * 出产地区 N
     */
    private String state;
    /**
     * 导演 N
     */
    private String director;
    /**
     * 主演 N
     */
    private String actors;
    /**
     * 片长 N 　 用于计算影片退场时间
     */
    private String length;
    /**
     * 一句话影评 N
     */
    private String highlight;
    /**
     * 电影的首映日期 N 　　11位的long数据如：1426176000000
     */
    private long releasedate;
    /**
     * 影片logo Y
     */
    private String logo;
    /**
     * 影片评分 N 　 　注意：不是IMDB评分
     */
    private String generalmark;
    /**
     * 影片版本 N 　 IMAX3D
     */
    private String gcedition;
    /**
     * 剧情内容
     */
    private String  content;
    /**
     * 剧照
     */
    private String still;
    /**
     * 视频的url地址
     */
    private String videourl;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStill() {
        return still;
    }

    public void setStill(String still) {
        this.still = still;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public Long getMovieid() {
        return movieid;
    }

    public void setMovieid(Long movieid) {
        this.movieid = movieid;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getEnglishname() {
        return englishname;
    }

    public void setEnglishname(String englishname) {
        this.englishname = englishname;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public long getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(long releasedate) {
        this.releasedate = releasedate;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getGeneralmark() {
        return generalmark;
    }

    public void setGeneralmark(String generalmark) {
        this.generalmark = generalmark;
    }

    public String getGcedition() {
        return gcedition;
    }

    public void setGcedition(String gcedition) {
        this.gcedition = gcedition;
    }
}
