package com.whunf.putaomovieday1.module.movie.resp;

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

    public static class AddTicketOrder
    {

        /** 内容提供商id【必选】 说明：如代表格瓦拉，中影等。在此处确定cp */
        private long cpid;

        /** 场次id【必选】 */
        private long mpid;

        /** 电影id【必选】 */
        private long movieid;

        /** 影院id【必选】 */
        private String cinemaid;

        /** 影片名称【必选】 */
        private String moviename;

        /** 影院名称【必选】 */
        private String cinemaname;

        /** 订单号【必选】 */
        private String tradeno;

        /** 手机号【必选】 */
        private String mobile;

        /** 订单有效期【必选】 说明：过了有效期，如用户未支付，锁定的座位将释放,11位long类型数据，如1426744200000 */
        private long validtime;

        /** 需支付的金额【必选】 */
        private int amount;

        /** 座位数量【必选】 */
        private int quantity;

        /** 下单时间【必选】 说明：11位long类型数据，如1426744200000 */
        private long addtime;

        /** 影厅名称【必选】 */
        private String roomname;

        /** 放映时间【必选】 说明：11位long类型数据，如1426744200000 */
        private String playtime;

        /** 座位信息【必选】 */
        private String seat;

        /** 获得内容提供商id【必选】 说明：如代表格瓦拉，中影等。在此处确定cp */
        public long getCpid()
        {
            return cpid;
        }

        /** 设置内容提供商id */
        public void setCpid(long cpid)
        {
            this.cpid = cpid;
        }

        /** 获得场次id【必选】 */
        public long getMpid()
        {
            return mpid;
        }

        /** 设置场次id */
        public void setMpid(long mpid)
        {
            this.mpid = mpid;
        }

        /** 获得电影id【必选】 */
        public long getMovieid()
        {
            return movieid;
        }

        /** 设置电影id */
        public void setMovieid(long movieid)
        {
            this.movieid = movieid;
        }

        /** 获得影院id【必选】 */
        public String getCinemaid()
        {
            return cinemaid;
        }

        /** 设置影院id */
        public void setCinemaid(String cinemaid)
        {
            this.cinemaid = cinemaid;
        }

        /** 获得影片名称【必选】 */
        public String getMoviename()
        {
            return moviename;
        }

        /** 设置影片名称 */
        public void setMoviename(String moviename)
        {
            this.moviename = moviename;
        }

        /** 获得影院名称【必选】 */
        public String getCinemaname()
        {
            return cinemaname;
        }

        /** 设置影院名称 */
        public void setCinemaname(String cinemaname)
        {
            this.cinemaname = cinemaname;
        }

        /** 获得订单号【必选】 */
        public String getTradeno()
        {
            return tradeno;
        }

        /** 设置订单号 */
        public void setTradeno(String tradeno)
        {
            this.tradeno = tradeno;
        }

        /** 获得手机号【必选】 */
        public String getMobile()
        {
            return mobile;
        }

        /** 设置手机号 */
        public void setMobile(String mobile)
        {
            this.mobile = mobile;
        }

        /** 获得订单有效期【必选】 说明：过了有效期，如用户未支付，锁定的座位将释放, 11位long类型数据，如1426744200000 */
        public long getValidtime()
        {
            return validtime;
        }

        /** 设置订单有效期 */
        public void setValidtime(long validtime)
        {
            this.validtime = validtime;
        }

        /** 获得需支付的金额【必选】以分为单位 */
        public int getAmount()
        {
            return amount;
        }

        /** 设置需支付的金额 */
        public void setAmount(int amount)
        {
            this.amount = amount;
        }

        /** 获得座位数量【必选】 */
        public int getQuantity()
        {
            return quantity;
        }

        /** 设置座位数量 */
        public void setQuantity(int quantity)
        {
            this.quantity = quantity;
        }

        /** 获得下单时间【必选】说明：11位long类型数据，如1426744200000 */
        public long getAddtime()
        {
            return addtime;
        }

        /** 设置下单时间 */
        public void setAddtime(long addtime)
        {
            this.addtime = addtime;
        }

        /** 获得影厅名称【必选】 */
        public String getRoomname()
        {
            return roomname;
        }

        /** 设置影厅名称 */
        public void setRoomname(String roomname)
        {
            this.roomname = roomname;
        }

        /** 获得放映时间【必选】说明：11位long类型数据，如1426744200000 */
        public String getPlaytime()
        {
            return playtime;
        }

        /** 设置放映时间 */
        public void setPlaytime(String playtime)
        {
            this.playtime = playtime;
        }

        /** 获得座位信息【必选】 */
        public String getSeat()
        {
            return seat;
        }

        /** 设置座位信息 */
        public void setSeat(String seat)
        {
            this.seat = seat;
        }

        @Override
        public String toString()
        {
            return "AddTicketOrder [cpid=" + cpid + ", mpid=" + mpid + ", movieid=" + movieid + ", cinemaid="
                    + cinemaid + ", moviename=" + moviename + ", cinemaname=" + cinemaname + ", tradeno=" + tradeno
                    + ", mobile=" + mobile + ", validtime=" + validtime + ", amount=" + amount + ", quantity="
                    + quantity + ", addtime=" + addtime + ", roomname=" + roomname + ", playtime=" + playtime
                    + ", seat=" + seat + "]";
        }

    }

    @Override
    public String toString()
    {
        return "AddTicketOrderResp [data=" + data + "]";
    }

}
