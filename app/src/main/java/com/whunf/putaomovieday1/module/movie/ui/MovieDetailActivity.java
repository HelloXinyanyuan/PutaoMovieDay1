package com.whunf.putaomovieday1.module.movie.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.core.PMApplication;
import com.whunf.putaomovieday1.common.util.BitmapCache;
import com.whunf.putaomovieday1.common.widget.HeaderLayout;
import com.whunf.putaomovieday1.common.widget.MyScrollView;
import com.whunf.putaomovieday1.module.movie.adapter.StillAdapter;
import com.whunf.putaomovieday1.module.movie.resp.entity.Movie;
import com.whunf.putaomovieday1.module.movie.resp.MovieDetailResp;
import com.whunf.putaomovieday1.module.movie.util.CinemaConstants;
import com.whunf.putaomovieday1.module.movie.util.MovieBitmapUtils;

import java.text.SimpleDateFormat;

/**
 * 影片详情
 */
public class MovieDetailActivity extends BaseActivity implements View.OnClickListener, MyScrollView.ScrollChangedListener {

    private static final String TAG = "MovieDetailActivity";
    public int threholdScroll = 200;//滑动阀值
    private HeaderLayout mHeaderLayout;

    private Movie mPassMovie;//从上一个界面传递过来的数据
    private ImageView mMovieLogo;
    private RatingBar mRatingbar;
    private TextView movieMark;
    private TextView movieType;
    private TextView movieLanguage;
    private TextView movieLength;
    private TextView movieReleaseDate;
    private RecyclerView mRvStills;
    private TextView movieDirector;
    private TextView movieActors;
    private TextView movieContent;
    private TextView movieComment;
    private ImageLoader imageLoader;
    private String[] mStills;//剧照的URL地址
    private View movieTopLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        imageLoader = new ImageLoader(PMApplication.getInstance().getRequestQueue(), new BitmapCache());
        parseIntent(getIntent());//第一次进来时
    }

    @Override
    protected void onNewIntent(Intent intent) {//处理singleTask情况
        super.onNewIntent(intent);
        parseIntent(intent);//之后又被启动了
    }


    private void parseIntent(Intent intent){
        mPassMovie = (Movie) intent.getSerializableExtra(CinemaConstants.EXTRA_MOVIE_DETAIL);
        initView();
        initData();
    }

    private void initData() {
        String url = "http://api.putao.so/sbiz/movie/detail?movieid=" + mPassMovie.getMovieid();
        StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MovieDetailResp mresp = JSONObject.parseObject(response, MovieDetailResp.class);
                if (mresp != null && mresp.getRet_code().equals("0000")) {
                    Movie data = mresp.getData();
                    refreshAnotherUi(data);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        PMApplication.getInstance().getRequestQueue().add(sr);

        imageLoader.get(mPassMovie.getLogo(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {//加载图片成功返回

                Bitmap bitmap = response.getBitmap();
                if (bitmap != null) {
                    mMovieLogo.setImageBitmap(bitmap);//电影海报
                    //先做缩放
                    Bitmap preBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 4, bitmap.getHeight() / 4, true);
                    Canvas canvas = new Canvas(preBitmap);
                    canvas.drawColor(Color.parseColor("#88000000"));

                    //高斯模糊处理
                    Bitmap gaosiBg = MovieBitmapUtils.fastblur(preBitmap, 2);
                    movieTopLayout.setBackgroundDrawable(new BitmapDrawable(gaosiBg));
                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {//加载图片失败

            }
        });
    }

    private void refreshAnotherUi(Movie data) {
        movieContent.setText(data.getContent());
        if (!TextUtils.isEmpty(data.getStill())) {
            mStills = data.getStill().split(",");
            mRvStills.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            StillAdapter stillAdapter = new StillAdapter(imageLoader, mStills);
            stillAdapter.setOnItemClickListener(new StillAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(MovieDetailActivity.this, ShowStillActivity.class);
                    intent.putExtra("currentIndex", position);//当前剧照的位置
                    intent.putExtra("stills", mStills);//所有剧照的地址
                    intent.putExtra("movieName", mPassMovie.getMoviename());//影片名字
                    startActivity(intent);
                }
            });
            mRvStills.setAdapter(stillAdapter);

        }
    }

    private void initView() {
        mHeaderLayout = (HeaderLayout) findViewById(R.id.header);
        mHeaderLayout.setAlpha(0);//默认为透明
        mHeaderLayout.setTitleTxt(mPassMovie.getMoviename());//将影片名字设置到标题上
        mMovieLogo = (ImageView) findViewById(R.id.movie_logo);//电影海报
        mRatingbar = (RatingBar) findViewById(R.id.movie_star);//影片得分ratingbar表现
        movieMark = (TextView) findViewById(R.id.movie_general_mark);//影片分数
        movieType = (TextView) findViewById(R.id.movie_type);//影片类型
        movieLanguage = (TextView) findViewById(R.id.movie_language);//电影语言
        movieReleaseDate = (TextView) findViewById(R.id.movie_release_date);//上映日期
        movieLength = (TextView) findViewById(R.id.movie_length_state);//时长
        movieComment = (TextView) findViewById(R.id.movie_highlight);//一句话影评
        mRvStills = (RecyclerView) findViewById(R.id.movie_dtl_still_list);//剧照
        movieDirector = (TextView) findViewById(R.id.movie_director);//导演
        movieActors = (TextView) findViewById(R.id.movie_actors);//演员
        movieContent = (TextView) findViewById(R.id.movie_content);//剧情
        movieTopLayout = findViewById(R.id.movie_detail_header);//高斯模糊背景布局
        MyScrollView myScrollView = (MyScrollView) findViewById(R.id.movie_scroll_view);
        myScrollView.setmScrollChangedListener(this);

        findViewById(R.id.btn_play_video).setOnClickListener(this);
        findViewById(R.id.movie_select_seat).setOnClickListener(this);

        //设置数据到视图
        //拿到得分
        if (!TextUtils.isEmpty(mPassMovie.getGeneralmark())) {
            float genmark = Float.parseFloat(mPassMovie.getGeneralmark());
            mRatingbar.setRating(genmark);
        }
        movieMark.setText(mPassMovie.getGeneralmark());
        movieLanguage.setText(mPassMovie.getLanguage());
        movieType.setText(mPassMovie.getType());
        movieReleaseDate.setText(new SimpleDateFormat("yyyy年MM月dd").format(mPassMovie.getReleasedate()) + "上映");
        movieLength.setText(mPassMovie.getLength() + "分钟");
        movieComment.setText(mPassMovie.getHighlight());
        movieDirector.setText(mPassMovie.getDirector());
        movieActors.setText(mPassMovie.getActors());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play_video://播放预告片
                Intent intent = new Intent(this, PlayVideoActivity.class);
                intent.putExtra("videoUrl", mPassMovie.getVideourl());
                intent.putExtra("movieName", mPassMovie.getMoviename());
                startActivity(intent);
                break;

            case R.id.movie_select_seat://选影院购票
                intent=new Intent(this, CinemaListActivity.class);
                intent.putExtra("movieName",mPassMovie.getMoviename());
                intent.putExtra("movieId",mPassMovie.getMovieid());
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onScrollChanged(int yDistance, int yPos) {
        if (yPos < threholdScroll) {
            threholdScroll = movieTopLayout.getHeight() - mHeaderLayout.getHeight();//

            //设置头部的透明度
            mHeaderLayout.setAlpha((float) yPos / threholdScroll);
            int alpha = yPos * 255 / threholdScroll;
            Log.d(TAG, "onScrollChanged() called with: " + "yDistance = [" + yDistance + "], yPos = [" + yPos + "]" + alpha);
        }
    }
}
