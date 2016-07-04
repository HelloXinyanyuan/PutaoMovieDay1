package com.whunf.putaomovieday1.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/7/4.
 * 定制ScrollView,可监听垂直方向变化
 */
public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollChangedListener != null) {
            mScrollChangedListener.onScrollChanged(t - oldt, t);//t=getScrollY()当前已经滑了多少
        }
    }

    private ScrollChangedListener mScrollChangedListener;

    public MyScrollView.ScrollChangedListener getmScrollChangedListener() {
        return mScrollChangedListener;
    }

    public void setmScrollChangedListener(MyScrollView.ScrollChangedListener mScrollChangedListener) {
        this.mScrollChangedListener = mScrollChangedListener;
    }

    public interface ScrollChangedListener {
        void onScrollChanged(int yDistance, int yPos);
    }


}
