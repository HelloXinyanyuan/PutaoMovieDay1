package com.whunf.putaomovieday1.module.movie.ui;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.core.BaseFragment;
import com.whunf.putaomovieday1.module.movie.adapter.SimpleFragmentVpAdapter;

import java.util.ArrayList;
import java.util.List;

public class MovieMainActivity extends BaseActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_main);
        mViewPager = (ViewPager) findViewById(R.id.movie_vp);

        //创建好Fragment
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new MovieListFragment());
        fragments.add(new CinemaListFragment());
        //创建适配器，将fragments传入适配器中
        PagerAdapter adapter = new SimpleFragmentVpAdapter(getSupportFragmentManager(), fragments);
        //将适配器与ViewPager关联
        mViewPager.setAdapter(adapter);

    }


}
