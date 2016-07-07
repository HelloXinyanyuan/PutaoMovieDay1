package com.whunf.putaomovieday1.module.movie.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.util.T;
import com.whunf.putaomovieday1.module.movie.resp.entity.OpiSeatInfo;
import com.whunf.putaomovieday1.module.movie.resp.entity.Seat;
import com.whunf.putaomovieday1.module.movie.resp.entity.SeatRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/7.
 * 选座视图
 */
public class SelectSeatView extends View {

    private static final String TAG = "SelectSeatView";
    private Bitmap mLockBitmap;//已经被系统锁定的座位
    private Bitmap mOkBitmap;//当前可以被用户选中的Bitmap
    private Bitmap mXZBitmap;//当前已经被用户选中的Bitmap
    private float translateX;//用于画布x轴方向平移
    private float translateY;//用于画布y轴方向平移

    private GestureDetector mGestureDetector;//手势探测器

    public SelectSeatView(Context context) {
        super(context);
        init();
    }


    public SelectSeatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mLockBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.putao_seat_lock);
        mOkBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.putao_seat_normal);
        mXZBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.putao_seat_checked);
        Seat.sLockBmp = mLockBitmap;
        Seat.sOkBmp = mOkBitmap;
        Seat.sXZBmp = mXZBitmap;
        mGestureDetector = new GestureDetector(getContext(), new MyGenstureListener());//创建一个手势探测器
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(translateX, translateY);//画布平移
        //绘制座位信息
        drawSeatInfo(canvas);
    }

    /**
     * 绘制座位信息
     *
     * @param canvas
     */
    private void drawSeatInfo(Canvas canvas) {
        for (ArrayList<Seat> rowSeats : mAllSeats) {//遍历绘制每一个座位
            for (Seat seat : rowSeats) {
                seat.drawMySlef(canvas);
            }
        }
    }

    /**
     * 设置座位信息
     *
     * @param data
     */
    public void setSeatData(OpiSeatInfo data) {
        List<SeatRow> seatrows = data.getSeatRowList();
        for (int i = 0; i < seatrows.size(); i++) {
            SeatRow sr = seatrows.get(i);
            String[] seatOfSa = sr.getColumns().split(",");

            Seat.sSeatWidth = getMeasuredWidth() / seatOfSa.length;//控件的宽度/列数
            Seat.sSeatHeight = Seat.sSeatWidth;

            ArrayList<Seat> seats = new ArrayList<>();
            for (int j = 0; j < seatOfSa.length; j++) {

                String seatStr = seatOfSa[j];
                Seat.SeatStatus status;
                String colNum = "";

                if (seatStr.equals("LK")) {
                    status = Seat.SeatStatus.LOCK;
                } else if (seatStr.equals("ZL")) {
                    status = Seat.SeatStatus.ZL;
                } else {
                    status = Seat.SeatStatus.OK;
                    colNum = seatStr;
                }
                //构建seat对象
                Seat seat = new Seat(status, sr.getRowid(), colNum, i, j);

                seats.add(seat);
            }
            mAllSeats.add(seats);
        }
        invalidate();//强制刷新UI
    }

    private ArrayList<ArrayList<Seat>> mAllSeats = new ArrayList<>();

    private float mPriousDistance = -1;//上一次两指滑动的距离

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent() called with: " + "event = [" + event + "]");

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mPriousDistance = -1;
        }

        if (event.getPointerCount() == 1) {//单指触摸
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                int colIndex = (int) (event.getX() / Seat.sSeatWidth);//求得列数
                int rowIndex = (int) (event.getY() / Seat.sSeatHeight);//求得行数
                Seat seat = mAllSeats.get(rowIndex).get(colIndex);
                seat.performClick();
                invalidate(seat.getArea());
                //当位置变化的时候，通知监听器
                if (mSeatSelectedChangeListener != null) {
                    mSeatSelectedChangeListener.onSeatSelectedChangeListener(seat);
                }

            }

            //使手势探测器生效
            mGestureDetector.onTouchEvent(event);
        } else {//多指

            if (event.getAction() == MotionEvent.ACTION_MOVE) {//双指移动时
                float distance = getTwoPointerDistance(event);
                if (mPriousDistance == -1) {
                    mPriousDistance = distance;
                } else {
                    float rate = distance / mPriousDistance;
                    Seat.sSeatWidth = (int) (Seat.sSeatWidth * rate + 0.5);
                    Seat.sSeatHeight = Seat.sSeatWidth;
                    for (int i = 0; i < mAllSeats.size(); i++) {
                        for (int j = 0; j < mAllSeats.get(i).size(); j++) {
                            mAllSeats.get(i).get(j).resetArea();
                        }
                    }
                    invalidate();
                    mPriousDistance = distance;
                }
            }

        }

        return true;
    }

    /**
     * 获得两指之间的距离
     *
     * @param event
     * @return
     */
    private float getTwoPointerDistance(MotionEvent event) {
        float xDistance = event.getX(0) - event.getX(1);
        float yDistance = event.getY(0) - event.getY(1);
        float distance = (float) Math.sqrt(xDistance * xDistance + yDistance * yDistance);
        return distance;
    }


    /**
     * 手势探测监听
     */
    class MyGenstureListener extends GestureDetector.SimpleOnGestureListener {

        public boolean onSingleTapUp(MotionEvent e) {

            return false;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            translateX -= distanceX;

            translateY -= distanceY;

            invalidate();
            return true;
        }
    }

    /**
     * 得到服务器端需要的座位拼接string
     *
     * @return
     */
    public String getSelectedSeatLable() {
        StringBuilder seatLableSb = new StringBuilder();
        for (int i = 0; i < mAllSeats.size(); i++) {
            for (int j = 0; j < mAllSeats.get(i).size(); j++) {
                Seat seat = mAllSeats.get(i).get(j);
                if (seat.isSelected()) {
                    seatLableSb.append(seat.getRowNumber());
                    seatLableSb.append(":");
                    seatLableSb.append(seat.getColNumber());
                    seatLableSb.append(",");
                }
            }
        }
        if (seatLableSb.length() > 0) {
            seatLableSb.deleteCharAt(seatLableSb.length() - 1);
        }
        return seatLableSb.toString();
    }


    private SeatSelectedChangeListener mSeatSelectedChangeListener;

    public SelectSeatView.SeatSelectedChangeListener getSeatSelectedChangeListener() {
        return mSeatSelectedChangeListener;
    }

    /**
     * 设置座位变化监听
     *
     * @param seatSelectedChangeListener
     */
    public void setSeatSelectedChangeListener(SelectSeatView.SeatSelectedChangeListener seatSelectedChangeListener) {
        mSeatSelectedChangeListener = seatSelectedChangeListener;
    }

    /**
     * 座位变化监听器
     */
    public interface SeatSelectedChangeListener {
        void onSeatSelectedChangeListener(Seat s);
    }

}
