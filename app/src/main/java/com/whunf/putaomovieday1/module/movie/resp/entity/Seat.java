package com.whunf.putaomovieday1.module.movie.resp.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * 选座中的单个座位
 */
public class Seat {


    public enum SeatStatus {
        LOCK, ZL, OK, XZ
    }

    public static int sSeatWidth;
    public static int sSeatHeight;

    public static Bitmap sLockBmp;
    public static Bitmap sOkBmp;
    public static Bitmap sXZBmp;

    private SeatStatus status;//座位状态
    private String rowNumber;//座位行号
    private String colNumber;//座位列号


    private Rect mArea;//座位大小
    private int rowIndex;//行下标
    private int colIndex;//列下标

    public Seat(SeatStatus status, String rowNumber, String colNumber, int rowIndex, int colIndex) {
        this.status = status;
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getColNumber() {
        return colNumber;
    }

    public void setColNumber(String colNumber) {
        this.colNumber = colNumber;
    }

    /**
     * 获得座位的区域边界
     * @return
     */
    public Rect getArea() {
        if (mArea == null) {
            mArea = new Rect(Seat.sSeatWidth * colIndex, Seat.sSeatHeight * rowIndex, Seat.sSeatWidth * (colIndex + 1), Seat.sSeatHeight * (rowIndex + 1));
        }
        return mArea;
    }

    /**
     * 重置座位大小
     */
    public void resetArea() {
        this.mArea = null;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }


    /**
     * 根据当前状态选中图片
     *
     * @return
     */
    private Bitmap getCurrentBitmap() {
        Bitmap bitmap = null;
        switch (status) {
            case LOCK:
                bitmap = sLockBmp;
                break;
            case OK:
                bitmap = sOkBmp;
                break;
            case XZ:
                bitmap = sXZBmp;
                break;
            case ZL:
                bitmap = null;
                break;
        }
        return bitmap;
    }

    /**
     * 绘制自己
     *
     * @param canvas
     */
    public void drawMySlef(Canvas canvas) {
        Bitmap bitmap = getCurrentBitmap();
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, null, getArea(), null);
        }
    }

    public void performClick() {
        switch (status) {
            case LOCK:
                break;
            case OK:
                status = SeatStatus.XZ;
                break;
            case XZ:
                status = SeatStatus.OK;
                break;
            case ZL:
                break;
        }

    }

    public boolean isSelected() {
        return status.ordinal() == SeatStatus.XZ.ordinal();
    }

    @Override
    public String toString() {
        return "Seat{" +
                "status=" + status +
                ", rowNumber='" + rowNumber + '\'' +
                ", colNumber='" + colNumber + '\'' +
                ", mArea=" + mArea +
                ", rowIndex=" + rowIndex +
                ", colIndex=" + colIndex +
                '}';
    }
}