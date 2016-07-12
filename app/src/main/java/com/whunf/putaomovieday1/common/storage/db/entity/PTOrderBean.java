package com.whunf.putaomovieday1.common.storage.db.entity;

import java.io.Serializable;

/**
 * 消息中心数据接口
 * 
 * @author putao_lhq
 */
public class PTOrderBean implements Serializable
{

    private static final long serialVersionUID = 1L;

    public static final int NOT_COMMENT = 0;

    public static final int COMMENTED = 1;

    /** 订单主题 */
    private String title;

    /** 订单状态,非订单业务不可使用此字段 */
    private String status;

    /** 订单状态码 */
    private int status_code;

    /** 订单价格,单位分 */
    private int price;

    /**
     * 商品标价，单位分 add by zjh 2015-04-15
     */
    private int mark_price;

    /** 订单更改时间戳 */
    private long m_time;

    /** 订单创建时间戳 */
    private long c_time;

    /** 订单号 */
    private String order_no;

    /** 业务类型 */
    private int product_type;

    /** 业务产品id */
    private int product_id;

    /** 支付方式,详见PaymentDesc */
    private int payment_type;

    /** 之前用来对应view的查看状态。3.4开始仅对应全托管订单是否已评论 0:未评论 1:已评论 */
    private int view_status;

    /** 扩展参数 */
    private String expand;

    /** 1表示从提醒进入.此字段不保存在数据库中,只在getNotifyView的时候有值 */
    private int entry;

    /** 优惠券列表 */
    private String coupon_ids;

    /**
     * 优惠券价格 [单位分] add by zjh 2015-04-15
     */
    private long favo_price;

    private long cp_id; // cp_id add by lxh 2015-3-27

    private String cp_name; // cp名称 add by hyl 2015-3-27

    private String cp_number; // cp号码 add by hyl 2015-3-27

    private String cp_note; // cp备注信息 add by hyl 2015-3-27

    private String cp_pic_url; // cp图片 add by jsy 2015-11-19

    /** 全托管订单的cp图标 */
    private String img_url;// （此字段不保存在数据库中,只在getOrderView的时候有值）

    /** 开放平台订单跳转H5订单的url */
    private String order_url;// （此字段不保存在数据库中,只在getOrderView的时候有值）


    /** 不同订单对应的expand携带的信息 */
    private Object orderDetail;// （此字段不保存在数据库中,只在getOrderView的时候有值）

    public long getCp_id()
    {
        return cp_id;
    }

    public void setCp_id(long cp_id)
    {
        this.cp_id = cp_id;
    }

    public String getCp_name()
    {
        return cp_name;
    }

    public void setCp_name(String cp_name)
    {
        this.cp_name = cp_name;
    }

    public String getCp_number()
    {
        return cp_number;
    }

    public void setCp_number(String cp_number)
    {
        this.cp_number = cp_number;
    }

    public String getCp_note()
    {
        return cp_note;
    }

    public void setCp_note(String cp_note)
    {
        this.cp_note = cp_note;
    }

    public String getCoupon_ids()
    {
        return coupon_ids;
    }

    public void setCoupon_ids(String coupon_ids)
    {
        this.coupon_ids = coupon_ids;
    }

    public long getFavo_price()
    {
        return favo_price;
    }

    public void setFavo_price(long favo_price)
    {
        this.favo_price = favo_price;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public int getStatus_code()
    {
        return status_code;
    }

    public void setStatus_code(int status_code)
    {
        this.status_code = status_code;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public int getMark_price()
    {
        return mark_price;
    }

    public void setMark_price(int mark_price)
    {
        this.mark_price = mark_price;
    }

    public long getM_time()
    {
        return m_time;
    }

    public void setM_time(long m_time)
    {
        this.m_time = m_time;
    }

    public String getOrder_no()
    {
        return order_no;
    }

    public void setOrder_no(String order_no)
    {
        this.order_no = order_no;
    }

    public int getProduct_type()
    {
        return product_type;
    }

    public void setProduct_type(int product_type)
    {
        this.product_type = product_type;
    }

    public int getProduct_id()
    {
        return product_id;
    }

    public void setProduct_id(int product_id)
    {
        this.product_id = product_id;
    }

    public String getExpand()
    {
        return expand;
    }

    public void setExpand(String expand)
    {
        this.expand = expand;
    }

    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

    public int getPayment_type()
    {
        return payment_type;
    }

    public void setPayment_type(int payment_type)
    {
        this.payment_type = payment_type;
    }

    public int getView_status()
    {
        return view_status;
    }

    public void setView_status(int view_status)
    {
        this.view_status = view_status;
    }

    @Override
    public String toString()
    {
        return "PTOrderBean [title=" + title + ", status=" + status + ", status_code=" + status_code + ", price="
                + price + ", m_time=" + m_time + ", order_no=" + order_no + ", product_type=" + product_type
                + ", product_id=" + product_id + ", payment_type=" + payment_type + ", view_status=" + view_status
                + ", expand=" + expand + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expand == null) ? 0 : expand.hashCode());
        result = prime * result + (int) (m_time ^ (m_time >>> 32));
        result = prime * result + ((order_no == null) ? 0 : order_no.hashCode());
        result = prime * result + payment_type;
        result = prime * result + price;
        result = prime * result + product_id;
        result = prime * result + product_type;
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + status_code;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + view_status;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PTOrderBean other = (PTOrderBean) obj;
        if (expand == null)
        {
            if (other.expand != null)
                return false;
        }
        else if (!expand.equals(other.expand))
            return false;
        if (m_time != other.m_time)
            return false;
        if (order_no == null)
        {
            if (other.order_no != null)
                return false;
        }
        else if (!order_no.equals(other.order_no))
            return false;
        if (payment_type != other.payment_type)
            return false;
        if (price != other.price)
            return false;
        if (product_id != other.product_id)
            return false;
        if (product_type != other.product_type)
            return false;
        if (status == null)
        {
            if (other.status != null)
                return false;
        }
        else if (!status.equals(other.status))
            return false;
        if (status_code != other.status_code)
            return false;
        if (title == null)
        {
            if (other.title != null)
                return false;
        }
        else if (!title.equals(other.title))
            return false;
        if (view_status != other.view_status)
            return false;
        return true;
    }

    public int getEntry()
    {
        return entry;
    }

    public void setEntry(int entry)
    {
        this.entry = entry;
    }

    public String getImg_url()
    {
        return img_url;
    }

    public void setImg_url(String img_url)
    {
        this.img_url = img_url;
    }

    public String getOrder_url()
    {
        return order_url;
    }

    public void setOrder_url(String order_url)
    {
        this.order_url = order_url;
    }


    public Object getOrderDetail()
    {
        return orderDetail;
    }

    public void setOrderDetail(Object orderDetail)
    {
        this.orderDetail = orderDetail;
    }

    public String getCp_pic_url()
    {
        return cp_pic_url;
    }

    public void setCp_pic_url(String cp_pic_url)
    {
        this.cp_pic_url = cp_pic_url;
    }

    public long getC_time()
    {
        return c_time;
    }

    public void setC_time(long c_time)
    {
        this.c_time = c_time;
    }
}
