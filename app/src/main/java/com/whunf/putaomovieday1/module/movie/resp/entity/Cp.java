package com.whunf.putaomovieday1.module.movie.resp.entity;

/**
 * 电影内容提供商CP——ContentProvider
 */
public class Cp {

    /**
     * 场次id【必选】
     */
    private long mpid;

    /**
     * 葡萄售价【必选】 说明：以分为单位。是葡萄针对某个cop提供的价格，也是最终价格，ptprice=cpprice-葡萄折扣
     */
    private int ptprice;

     /** cp提供的线上售价【必选】 说明：以分为单位 */
     private int cpprice;

    /**
     * 内容提供商id【必选】 说明：如代表格瓦拉，中影等。在此处确定cp
     */
    private long cpid;

    /**
     * 内容提供商名称【必选】
     */
    private String cpname;

    private String cplogo;

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
     * 获得葡萄售价【必选】 说明：以分为单位。是葡萄针对某个cp提供的价格，也是最终价格，ptprice=cpprice-葡萄折扣
     */
    public int getPtprice() {
        return ptprice;
    }

    /**
     * 设置葡萄售价
     */
    public void setPtprice(int ptprice) {
        this.ptprice = ptprice;
    }


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
     * 获得内容提供商名称【必选】
     */
    public String getCpname() {
        return cpname;
    }

    /**
     * 设置内容提供商名称
     */
    public void setCpname(String cpname) {
        this.cpname = cpname;
    }

    public String getCplogo() {
        return cplogo;
    }

    public void setCplogo(String cplogo) {
        this.cplogo = cplogo;
    }

    @Override
    public String toString() {
        return "Cp{" +
                "mpid=" + mpid +
                ", ptprice=" + ptprice +
                ", cpprice=" + cpprice +
                ", cpid=" + cpid +
                ", cpname='" + cpname + '\'' +
                ", cplogo='" + cplogo + '\'' +
                '}';
    }
}

