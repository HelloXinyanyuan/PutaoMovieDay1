package com.whunf.putaomovieday1.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.util.GraphicUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/7/8.
 */
public class IndexBar extends View {

    private List<String> mIndexs;

    private IndexBarChangeListener mIndexBarChangeListener;

    private PopupWindow mPopupWindow;


    public void setIndexBarChangeListener(IndexBar.IndexBarChangeListener indexBarChangeListener) {
        this.mIndexBarChangeListener = indexBarChangeListener;
    }

    public void setIndexs(List<String> indexs) {
        this.mIndexs = indexs;
        invalidate();
    }

    public IndexBar(Context context) {
        super(context);
    }

    public IndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIndexs == null) {
            return;
        }

        canvas.drawColor(mBgColor);

        int itemHeight = getItemHeight();
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.parseColor("#a2a2a2"));

        float textsize = getResources().getDimension(R.dimen.putao_btn_text_size);
        p.setTextSize(textsize);

        for (int i = 0; i < mIndexs.size(); i++) {
            String str = mIndexs.get(i);
            float x = getMeasuredWidth() / 2;//水平方向居中
            float y = i * itemHeight + itemHeight / 2;//垂直方向居中
            canvas.drawText(str, x, y, p);
        }

    }

    private int getItemHeight() {
        return getMeasuredHeight() / mIndexs.size();
    }


    private int mBgColor = Color.WHITE;

    private TextView mTvTip;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mBgColor = Color.BLACK;
                int index = Math.round(event.getY() / getItemHeight());

                if (!(index < 0 || index >= mIndexs.size())) {//有效点击/移动

                    //弹出文字提示框
                    showTipWindow(index);

                    if (mIndexBarChangeListener != null) {//通知给监听者
                        mIndexBarChangeListener.onIndexChange(mIndexs.get(index), index);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mBgColor = Color.WHITE;
                dismissTipWindow();
                break;
        }
        invalidate();
        return true;
    }

    private void dismissTipWindow() {
        mPopupWindow.dismiss();
    }

    /**
     * 显示提示框
     * @param index
     */
    private void showTipWindow(int index) {
        if (mPopupWindow == null) {
            mTvTip = new TextView(getContext());
            mTvTip.setTextSize(50);
            mTvTip.setGravity(Gravity.CENTER);
            mPopupWindow = new PopupWindow(mTvTip, GraphicUtil.dip2px(getContext(), 200), GraphicUtil.dip2px(getContext(), 200), false);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }

        mTvTip.setText(mIndexs.get(index));
        //显示在父布局的中间位置
        mPopupWindow.showAtLocation((View) getParent(), Gravity.CENTER, 0, 0);
    }

    public interface IndexBarChangeListener {
        void onIndexChange(String indexStr, int pos);
    }


}
