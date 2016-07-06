package com.whunf.putaomovieday1.module.movie.resp.entity;

import com.whunf.putaomovieday1.module.movie.util.CinemaConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Playdate implements Comparable<Playdate> {
    /**
     * 日期【必选】 说明：yyyy-MM-dd
     */
    private String playdate;

    private Date playdateInDate;

    /**
     * 获得日期【必选】 说明：yyyy-MM-dd
     */
    public String getPlaydate() {
        return playdate;
    }

    static final SimpleDateFormat SIMPLE_DATE_FORMAT=new SimpleDateFormat(CinemaConstants.DATE_PATTERN_YDM);

    /**
     * 设置日期
     */
    public void setPlaydate(String playdate) {
        this.playdate = playdate;
    }

    public Date getPlaydateInDate() {
        if (playdateInDate == null) {
            try {
                playdateInDate = SIMPLE_DATE_FORMAT.parse(getPlaydate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return playdateInDate;
    }

    @Override
    public int compareTo(Playdate another) {
        if (getPlaydate() == null) {
            return -1;
        }
        if (another.getPlaydateInDate() == null) {
            return -1;
        }
        return getPlaydateInDate().compareTo(another.getPlaydateInDate());
    }

    @Override
    public String toString() {
        return "Playdate [playdate=" + playdate + ", playdateInDate=" + playdateInDate + "]";
    }

}