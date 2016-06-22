package com.whunf.putaomovieday1.module.movie.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.whunf.putaomovieday1.common.core.BaseFragment;

import java.util.List;

/**
 * 简单通用的ViewPager装Fragment的适配器
 * Created by Administrator on 2016/6/22.
 */
public class SimpleFragmentVpAdapter extends FragmentPagerAdapter {

    List<BaseFragment> fragments;

    public SimpleFragmentVpAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
