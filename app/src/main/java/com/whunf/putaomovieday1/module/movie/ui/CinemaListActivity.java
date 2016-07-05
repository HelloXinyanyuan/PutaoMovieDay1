package com.whunf.putaomovieday1.module.movie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.widget.HeaderLayout;

/**
 * 影院列表窗口
 */
public class CinemaListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_list);
        Intent intent = getIntent();
        String movieName = intent.getStringExtra("movieName");
        HeaderLayout headerLayout = (HeaderLayout) findViewById(R.id.header);
        headerLayout.setTitleTxt(movieName);


        //内容填充CinemaListFragment
        CinemaListFragment cinemaListFragment = new CinemaListFragment();
        cinemaListFragment.setLoadQuickLoad(true);//立即加载数据

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.cinemalist_content, cinemaListFragment);
        ft.commit();
    }
}
