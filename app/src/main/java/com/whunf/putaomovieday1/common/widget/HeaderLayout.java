package com.whunf.putaomovieday1.common.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whunf.putaomovieday1.R;

/**
 * Created by Administrator on 2016/7/4.
 * 公共的头布局
 */
public class HeaderLayout extends LinearLayout {

    private View mBtnLeft;//左边的button
    private TextView mTvTitle;//中间的标题
    private TextView mTvSubTitle;//中间的子标题
    private View mBtnRight;//右边的button
    private ImageView mImvRight;//右边的ImageView

    private Drawable mBgDrawable;

    public HeaderLayout(Context context) {
        super(context);
    }

    public HeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //将布局加入当前HeaderLayout布局
        View.inflate(getContext(), R.layout.header_layout, this);
        //找到其中的子布局
        mBtnLeft = findViewById(R.id.btn_header_left);
        mTvTitle = (TextView) findViewById(R.id.tv_header_title);
        mBtnRight = findViewById(R.id.btn_header_right);
        mImvRight = (ImageView) findViewById(R.id.imv_header_right);
        mTvSubTitle = (TextView) findViewById(R.id.tv_header_subtitle);
        //取出自定义布局
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderLayoutAttr);
        String title = typedArray.getString(R.styleable.HeaderLayoutAttr_title);
        Drawable rightBtnSrc = typedArray.getDrawable(R.styleable.HeaderLayoutAttr_right_btn_src);
        typedArray.recycle();//回收释放TypedArray对象
        //根据自定义属性设置值

        mBtnLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
        mTvTitle.setText(title);

        if (rightBtnSrc != null) {
            mBtnRight.setBackgroundDrawable(rightBtnSrc);
            mBtnRight.setVisibility(View.VISIBLE);
        }
        //默认设置白色背景
        setBackgroundColor(Color.WHITE);
    }

    /**
     * 设置头布局的标题
     *
     * @param txt
     */
    public void setTitleTxt(CharSequence txt) {
        mTvTitle.setText(txt);
    }

    /**
     * 设置头布局的标题
     *
     * @param txtResId
     */
    public void setTitleTxt(int txtResId) {
        mTvTitle.setText(txtResId);
    }

    /**
     * 设置头布局的子标题
     *
     * @param txt
     */
    public void setSubTitleTxt(CharSequence txt) {
        mTvSubTitle.setText(txt);
    }

    /**
     * 设置头布局的子标题
     *
     * @param txtResId
     */
    public void setSubTitleTxt(int txtResId) {
        mTvSubTitle.setText(txtResId);
    }

    /**
     * 设置右边的button点击
     *
     * @param listener
     */
    public void setRightBtnClick(OnClickListener listener) {
        mBtnRight.setOnClickListener(listener);
        mBtnRight.setVisibility(VISIBLE);
    }

    /**
     * 设置右边button的图片
     *
     * @param resId
     */
    public void setRightBtnImg(int resId) {
        mImvRight.setImageResource(resId);
    }


}
