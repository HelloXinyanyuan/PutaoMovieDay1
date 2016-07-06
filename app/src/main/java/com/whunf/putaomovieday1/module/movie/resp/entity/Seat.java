package com.whunf.putaomovieday1.module.movie.resp.entity;

/**
 * 选座中的单个座位
 */
public class Seat {
    /**
     * 序号，当为走道时 为"ZL",当为锁定时为LK
     */
    private String n = null;

    /**
     * 序号代表的状态值
     */
    private int condition;

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "Seat [n=" + n + ", condition=" + condition + "]";
    }

}