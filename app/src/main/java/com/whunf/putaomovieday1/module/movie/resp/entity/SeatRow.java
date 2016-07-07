package com.whunf.putaomovieday1.module.movie.resp.entity;

/**
 * 座位的排
 */
public class SeatRow {

    /**
     * 座位行编号
     */
    private String rownum;

    /**
     * 影院座位行编号
     */
    private String rowid;

    /**
     * 影院列编号
     */
    private String columns;

    /**
     * 获得座位行编号
     */
    public String getRownum() {
        return rownum;
    }

    /**
     * 设置座位行编号
     */
    public void setRownum(String rownum) {
        this.rownum = rownum;
    }

    /**
     * 获得影院座位行编号
     */
    public String getRowid() {
        return rowid;
    }

    /**
     * 设置影院座位行编号
     */
    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

    /**
     * 获得影院列编号
     */
    public String getColumns() {
        return columns;
    }

    /**
     * 设置影院列编号
     */
    public void setColumns(String columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "SeatRow{" +
                "rownum='" + rownum + '\'' +
                ", rowid='" + rowid + '\'' +
                ", columns='" + columns + '\'' +
                '}';
    }
}