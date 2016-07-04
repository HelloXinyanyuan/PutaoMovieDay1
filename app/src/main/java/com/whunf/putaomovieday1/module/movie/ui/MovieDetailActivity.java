package com.whunf.putaomovieday1.module.movie.ui;

import android.os.Bundle;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.widget.HeaderLayout;
import com.whunf.putaomovieday1.module.movie.resp.Movie;

/**
 * 影片详情
 */
public class MovieDetailActivity extends BaseActivity {

    private  HeaderLayout mHeaderLayout;

    private Movie mPassMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        mPassMovie = (Movie) getIntent().getSerializableExtra("movie");
        initView();
    }

    private void initView() {
        mHeaderLayout = (HeaderLayout) findViewById(R.id.header);
        mHeaderLayout.setTitleTxt(mPassMovie.getMoviename());
    }


}
