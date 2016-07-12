package com.whunf.putaomovieday1.module.movie.req;

import java.io.Serializable;

/**
 * 订单详情页的been
 *
 * @author peku
 */
public class DetailMovieOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    private long mp_id; // 场次ID


    private String movie_name; // 影片名称

    private String cinema_name; // 影院名称

    private String mobile; // 手机号

    private String valid_time; // 订单有效期


    private int amount; // 需支付的金额,单位分

    private int quantity; // 座位数量

    private String movie_photo_url;// 电影的图片

    private String add_time; // 下单时间

    private String seat; // 座位信息

    private String order_title; // 订单标题


    private String order_no; // 我们的订单号


    /**
     * 下单使用的cp id
     */
    private long cp_id;

    /**
     * 影院地址
     */
    private String cinemaAddress;


    /**
     * 获得内容提供商id【必选】 说明：如代表格瓦拉，中影等。在此处确定cp
     */
    public long getCp_id() {
        return cp_id;
    }

    /**
     * 设置内容提供商id
     */
    public void setCp_id(long cp_id) {
        this.cp_id = cp_id;
    }


    public long getMp_id() {
        return mp_id;
    }

    public void setMp_id(long mp_id) {
        this.mp_id = mp_id;
    }


    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getCinema_name() {
        return cinema_name;
    }

    public void setCinema_name(String cinema_name) {
        this.cinema_name = cinema_name;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getValid_time() {
        return valid_time;
    }

    public void setValid_time(String valid_time) {
        this.valid_time = valid_time;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }


    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }


    public String getOrder_title() {
        return order_title;
    }

    public void setOrder_title(String order_title) {
        this.order_title = order_title;
    }


    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getMovie_photo_url() {
        return movie_photo_url;
    }

    public void setMovie_photo_url(String movie_photo_url) {
        this.movie_photo_url = movie_photo_url;
    }

    public String getCinemaAddress() {
        return cinemaAddress;
    }

    public void setCinemaAddress(String cinemaAddress) {
        this.cinemaAddress = cinemaAddress;
    }


    private int pt_status;
    private int movie_id;

    public int getPt_status() {
        return pt_status;
    }

    public void setPt_status(int pt_status) {
        this.pt_status = pt_status;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }
}
