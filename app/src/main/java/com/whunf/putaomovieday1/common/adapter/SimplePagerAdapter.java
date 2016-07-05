package com.whunf.putaomovieday1.common.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 公共的简单的PagerAdapter实现类
 * Created by Administrator on 2016/6/30.
 */
public class SimplePagerAdapter extends PagerAdapter {

    protected List<View> views;

    public SimplePagerAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views == null ? 0 : views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {//即将在ViewPager中显示时调用
        View view = views.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {//在ViewPager不显示，不缓存情况调用
        View view = views.get(position);
        container.removeView(view);
    }
}
