package com.whunf.putaomovieday1.module.movie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.LongSparseArray;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;
import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.core.PMApplication;
import com.whunf.putaomovieday1.common.core.UrlConfig;
import com.whunf.putaomovieday1.common.parser.CommParserTask;
import com.whunf.putaomovieday1.common.parser.FullTaskListener;
import com.whunf.putaomovieday1.common.parser.ParserTaskUtils;
import com.whunf.putaomovieday1.common.util.BitmapCache;
import com.whunf.putaomovieday1.common.util.CityMgr;
import com.whunf.putaomovieday1.common.util.GraphicUtil;
import com.whunf.putaomovieday1.common.util.LogUtil;
import com.whunf.putaomovieday1.common.util.NumberUtil;
import com.whunf.putaomovieday1.common.util.T;
import com.whunf.putaomovieday1.common.widget.HeaderLayout;
import com.whunf.putaomovieday1.module.movie.adapter.ImageAdapter;
import com.whunf.putaomovieday1.module.movie.adapter.OpiListAdapter;
import com.whunf.putaomovieday1.module.movie.resp.Movie;
import com.whunf.putaomovieday1.module.movie.resp.MovieListResp;
import com.whunf.putaomovieday1.module.movie.resp.OpiListResp;
import com.whunf.putaomovieday1.module.movie.resp.PlaydateListResp;
import com.whunf.putaomovieday1.module.movie.resp.entity.Cp;
import com.whunf.putaomovieday1.module.movie.resp.entity.Opi;
import com.whunf.putaomovieday1.module.movie.resp.entity.Playdate;
import com.whunf.putaomovieday1.module.movie.util.CinemaConstants;
import com.whunf.putaomovieday1.module.movie.util.CinemaUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 影院详情（显示当前影院下的影片列表、排期、场次等）
 */
public class CinemaDetailActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    private static final String TAG = "CinemaDetailActivity";

    /******外传递参数开始******/
    //最后的选中的排期
    private String mFirstSelectPlaydateStr;
    // 用于匹配第一次选中的电影
    private long mFirstMovieId;
    // 用于匹配第一次选中的电影
    private String mFirstMovieName;
    //影院id
    private long mCinemaid;
    private String mCinemaName;
    private String mCinemaAddress;
    //影院纬度
    private double mCinemaLat;
    //影院经度
    private double mCinemaLng;
    //影院经纬度使用的坐标系
    private String mCinemaCs;
    private String mCityName;
    /******外传递参数结束******/

    private OpiListAdapter mOpiListAdapter;

    private LinearLayout mPlaydateContainer;

    private Movie mSelectedMovie;


    private CommParserTask<MovieListResp> mMovieListTask;

    private CommParserTask<PlaydateListResp> mPlaydateListTask;

    private CommParserTask<OpiListResp> mOpiListTask;


    private Gallery mMoviesGallery;

    private ImageAdapter mMoviesAdapter;

    private TextView mMovieNameTv;

    private TextView mMovieMarkTv;

    private TextView mMovieOtherInfoTv;

    private String mLastSelectPlaydateStr;

    /**
     * 场次缓存集合
     */
    private LongSparseArray<HashMap<String, List<Opi>>> mOpiListCache = new LongSparseArray<HashMap<String, List<Opi>>>();

    /**
     * 播放日期缓存集合
     */
    private LongSparseArray<List<Playdate>> mPlaydateListCache = new LongSparseArray<List<Playdate>>();

    private ImageLoader mLoader;

 /**
     * 标题栏文本
     */
    private HeaderLayout mHeaderLayout;

    private View mHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_detail);
        parseIntent();
        initViews();
        initData();
        LogUtil.i(TAG, "SpeedLog onCreate end=" + System.currentTimeMillis());
    }

    /**
     * 解析上一个界面传入的数据
     */
    protected void parseIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mCinemaid = intent.getLongExtra(CinemaConstants.EXTRA_CINEMA_ID, 0);
            mFirstMovieId = intent.getLongExtra(CinemaConstants.EXTRA_MOVIEID, 0);
            mCinemaName = intent.getStringExtra(CinemaConstants.EXTRA_CINEMA_NAME);
            mCinemaAddress = intent.getStringExtra(CinemaConstants.EXTRA_CINEMA_ADDRESS);
            mCinemaLat = intent.getDoubleExtra(CinemaConstants.EXTRA_CINEMA_LAT, 0.0);
            mCinemaLng = intent.getDoubleExtra(CinemaConstants.EXTRA_CINEMA_LNG, 0.0);
            mCinemaCs = intent.getStringExtra(CinemaConstants.EXTRA_CINEMA_CS);
            mCityName = intent.getStringExtra(CinemaConstants.EXTRA_CITYNAME);
            //匹配选中的电影
            mFirstMovieName = intent.getStringExtra(CinemaConstants.EXTRA_MOVIE_NAME);
            mFirstSelectPlaydateStr = intent.getStringExtra(CinemaConstants.EXTRA_CINEMA_SELECT_PLAYDATE);
            if (TextUtils.isEmpty(mCityName)) {// 如果没传递就用本地的
                mCityName = CityMgr.getInstance().loadCity();
            }
        }
    }

    private void initViews() {

        ListView opiListView = (ListView) findViewById(R.id.opi_list);
        opiListView.setOnItemClickListener(this);
        mOpiListAdapter = new OpiListAdapter(mLoader);
        // 添加头部view
        mHeaderView = View.inflate(this, R.layout.putao_open_play_list_item_head, null);
        mMovieNameTv = (TextView) mHeaderView.findViewById(R.id.movie_name);
        mMovieMarkTv = (TextView) mHeaderView.findViewById(R.id.movie_general_mark);
        mMovieOtherInfoTv = (TextView) mHeaderView.findViewById(R.id.movie_other_info);

        mHeaderView.findViewById(R.id.movie_dtl).setOnClickListener(this);
        mPlaydateContainer = (LinearLayout) mHeaderView.findViewById(R.id.playdate_container);
        opiListView.addHeaderView(mHeaderView, null, false);
//        // 添加底部view
//        View ibottom = View.inflate(this, R.layout.putao_movie_bottom_layout, null);
//        opiListView.addFooterView(ibottom, null, false);
        // 必须在addHeaderView和addFooterView之前setAdapter
        opiListView.setAdapter(mOpiListAdapter);
        mLoader = new ImageLoader(PMApplication.getInstance().getRequestQueue(), new BitmapCache());
        mMoviesAdapter = new ImageAdapter(new ArrayList<Movie>(), mLoader);
        mMoviesGallery = (Gallery) findViewById(R.id.opi_movie_list);
        mMoviesGallery.setAdapter(mMoviesAdapter);
        mMoviesGallery.setSpacing(45);//px
        mMoviesGallery.setGravity(Gravity.CENTER_VERTICAL);
        // ecoGallery.setFlingable(false);
        mMoviesGallery.setUnselectedAlpha(0.5f);
    }

    private void initData() {
        loadMovieListData();
    }

    /**
     * 加载gallery的电影数据
     */
    private void loadMovieListData() {
        //配置参数和请求路径
        String reqPath = null;
        reqPath = UrlConfig.MoviePath.MOVIE_LIST;
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("cinemaid", mCinemaid + "");
        reqParams.put("citycode", mCityName);
        reqPath = ParserTaskUtils.buildPath(reqParams);
        //创建请求任务
        mMovieListTask = new CommParserTask<MovieListResp>(reqPath, MovieListResp.class);
        mMovieListTask.setTaskListener(new FullTaskListener<MovieListResp>(this) {
            @Override
            public void onTaskSuccess(MovieListResp response) {
                mMoviesAdapter.getMovieList().clear();
                List<Movie> movies = response.getData();
                if (null != movies && !movies.isEmpty()) {
                    refreshMovieListUi(movies);
                } else {
                }
            }
        });
        mMovieListTask.asyncParse();
    }

    private int getGallerySelection(List<Movie> movies, long movieId, String movieName) {
        int findIndex = -1;
        // 优先考虑用id匹配
        for (int i = 0, size = movies.size(); i < size; i++) {
            if (movies.get(i).getMovieid() == movieId) {
                findIndex = i;
                break;
            }
        }
        if (findIndex == -1 && !TextUtils.isEmpty(movieName)) {// 如果id没匹配到就用名字匹配
            for (int i = 0, size = movies.size(); i < size; i++) {
                if (!TextUtils.isEmpty(movies.get(i).getMoviename()) && movies.get(i).getMoviename().equals(movieName)) {
                    findIndex = i;
                    break;
                }
            }
        }
        // 最终没匹配到就用默认0
        return findIndex == -1 ? 0 : findIndex;
    }

    private void refreshMovieListUi(List<Movie> movies) {
        mMoviesAdapter.getMovieList().addAll(movies);
        mMoviesAdapter.notifyDataSetChanged();
        int firstSelectMovie = getGallerySelection(movies, mFirstMovieId, mFirstMovieName);
        mMoviesGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMoviesAdapter.setSelectItem(position);
                mLastSelectPlaydateStr = "";
                Movie movie = (Movie) parent.getAdapter().getItem(position);
                refreshSelectMovieUi(movie);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mMoviesGallery.setSelection(firstSelectMovie);
    }

    private void refreshSelectMovieUi(Movie movie) {
        mSelectedMovie = movie;
        int movieLen =  NumberUtil.parseIntSafe(movie.getLength());
        mOpiListAdapter.setMovieLength(movieLen);
        mMovieNameTv.setText(movie.getMoviename());
        String markStr = movie.getGeneralmark();
        if (!TextUtils.isEmpty(markStr)) {
            float mark = NumberUtil.parseFloatSafe(movie.getGeneralmark());
            markStr = getString(R.string.putao_movie_dtl_rating, "" + mark);
            SpannableString markSpanText = new SpannableString(markStr);
            markSpanText.setSpan(new AbsoluteSizeSpan(10, true), markStr.length() - 1, markStr.length(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            mMovieMarkTv.setText(markSpanText);
        }
        //排期
        loadPlaydateList(mCinemaid, mCinemaName, movie.getMovieid(), movie.getMoviename());
    }

    /**
     * 加载排期列表数据
     *
     * @param cinemaid
     * @param cinemaname
     * @param movieid
     * @param moviename
     * @author lxh
     * @since 2015-6-25
     */
    private void loadPlaydateList(final long cinemaid, final String cinemaname, final long movieid,
                                  final String moviename) {
        // 加载播放日期数据需先清空老的场次列表，确保场次listitem为空
        clearOpiListview();
        List<Playdate> playdates = mPlaydateListCache.get(movieid);
        if (playdates != null) {// 如果内存中有就从内存中取
            refreshPlaydateUi(playdates);
            return;
        }

        //配置参数和请求路径
        String reqPath = null;
        reqPath = UrlConfig.MoviePath.PLAYDATE_LIST;
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("movieid", movieid+"");
        reqParams.put("moviename", moviename);
        reqParams.put("cinemaid", cinemaid+"");
        reqParams.put("cinemaname", cinemaname);
        reqPath = ParserTaskUtils.buildPath(reqParams);

        //创建请求任务
        mPlaydateListTask = new CommParserTask<PlaydateListResp>(reqPath, PlaydateListResp.class);
        mPlaydateListTask.setTaskListener(new FullTaskListener<PlaydateListResp>(this) {
            @Override
            public void onTaskSuccess(PlaydateListResp response) {

                List<Playdate> playdates = response.getPlaydateList();
                if (playdates != null && !playdates.isEmpty()) {
                    // 按照时间排序升序
                    Collections.sort(playdates);
                    /** 保存播放日期数据 */
                    savePlaydateList(playdates);
                    /** 更新播放日期button UI */
                    refreshPlaydateUi(playdates);
                } else {
                    // 无排期数据
                    // 移除之前的排期button
                    mPlaydateContainer.removeAllViews();
                }
            }
            /** 保存播放日期数据 */
            private void savePlaydateList(List<Playdate> playdateList) {
                if (playdateList != null) {// 如果不为null就保存在内存中
                    mPlaydateListCache.put(movieid, playdateList);
                }
            }
        });
        mPlaydateListTask.asyncParse();
    }

    /**
     * 过滤排期
     *
     * @param playdateList
     * @return
     * @author lxh
     * @since 2015-5-18
     */
    private List<Playdate> filterPlaydateList(List<Playdate> playdateList) {
        List<Playdate> filterList = new ArrayList<Playdate>();
        if (null != playdateList && !playdateList.isEmpty()) {
            final Calendar today = CinemaUtils.getFormatedToday();
            // 过滤播放日历列表中无日期或今天之前的无意义的（昨天）票
            for (int i = 0, len = playdateList.size(); i < len; i++) {
                final Playdate playdate = playdateList.get(i);
                Date date = playdate.getPlaydateInDate();
                if (date != null && !date.before(today.getTime())) {
                    filterList.add(playdate);
                }
            }
        }
        return filterList;
    }

    /**
     * 更新播放日期button UI
     *
     * @param playdateList
     */
    private void refreshPlaydateUi(List<Playdate> playdateList) {
        // 移除之前的排期button
        mPlaydateContainer.removeAllViews();
        final Calendar today = CinemaUtils.getFormatedToday();
        for (int i = 0, len = playdateList.size(); i < len; i++) {
            final Playdate playdate = playdateList.get(i);
            Button button = (Button)View.inflate(this,R.layout.cinema_detail_playdate_btn,null);
            button.setTag(playdate.getPlaydate());
            button.setText(CinemaUtils.getPlaydateStr(today, playdate.getPlaydateInDate()));
            button.setOnClickListener(playdateButtonClickListener);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(GraphicUtil.dip2px(this, 103),
                    GraphicUtil.dip2px(this, 48));
            if (i < (len - 1)) {
                layoutParams.rightMargin = GraphicUtil.dip2px(this, 9);
            }
            mPlaydateContainer.addView(button, layoutParams);
        }

        /**
         * modified by wcy 2015-5-21 start for BUG #4918 old code:selectedButton
         * = playdateContainer.getChildAt(0);
         */
        if (!TextUtils.isEmpty(mFirstSelectPlaydateStr)) {
            mLastSelectPlaydateStr = mFirstSelectPlaydateStr;
        }
        View selectedButton = null;
        if (TextUtils.isEmpty(mLastSelectPlaydateStr)) {
            selectedButton = mPlaydateContainer.getChildAt(0);
        } else {
            int childCount = mPlaydateContainer.getChildCount();
            for (int i = 0; i < childCount; i++) {
                Button button = (Button) mPlaydateContainer.getChildAt(i);
                String dateStr = (String) button.getTag();
                if (dateStr.equals(mLastSelectPlaydateStr)) {
                    selectedButton = button;
                    break;
                }
            }
            if (selectedButton == null) {
                selectedButton = mPlaydateContainer.getChildAt(0);
            }
        }
        /** modified by wcy 2015-5-21 end */
        selectedButton.performClick();
    }

    private View.OnClickListener playdateButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CinemaUtils.doViewSelected(v);
            String playdateStr = (String) v.getTag();
            mLastSelectPlaydateStr = playdateStr;// 保存加载的播放日期
            loadOpiList(mSelectedMovie.getMovieid(), mSelectedMovie.getMoviename(), mLastSelectPlaydateStr);
        }
    };

    /**
     * 加载场次数据
     *
     * @param playdate
     */
    private void loadOpiList(final long movieid, final String moviename, final String playdate) {
        // 点击播放日期后先清空老的场次列表
        clearOpiListview();
        HashMap<String, List<Opi>> selectMovieOpiList = mOpiListCache.get(movieid);
        if (selectMovieOpiList != null) {
            List<Opi> cacheOpiList = selectMovieOpiList.get(playdate);
            if (cacheOpiList != null) {// 如果memory中有就从其中取
                refreshOpiListUi(cacheOpiList);
                return;
            }
        }

        //配置参数和请求路径
        String reqPath = null;
        reqPath = UrlConfig.MoviePath.OPI_LIST;
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("cinemaid", mCinemaid+"");
        reqParams.put("cinemaname", mCinemaName);
        reqParams.put("playdate", playdate);
        reqParams.put("movieid", movieid+"");
        reqParams.put("moviename", moviename);
        reqPath = ParserTaskUtils.buildPath(reqParams);

        //创建请求任务
        mOpiListTask = new CommParserTask<OpiListResp>(reqPath, OpiListResp.class);
        mOpiListTask.setTaskListener(new FullTaskListener<OpiListResp>(this) {
            @Override
            public void onTaskSuccess(OpiListResp response) {
                if (response.getOpiList() != null && !response.getOpiList().isEmpty()) {
                    saveOpiList(playdate, response);
                    refreshOpiListUi(response.getOpiList());
                } else {
                    // 无排期数据
                    // 移除之前的排期button
                    mPlaydateContainer.removeAllViews();
                }
            }
            private void saveOpiList(final String playdate, OpiListResp response) {
                HashMap<String, List<Opi>> selectMovieOpiList = mOpiListCache.get(movieid);
                if (selectMovieOpiList != null) {
                    selectMovieOpiList.put(playdate, response.getOpiList());
                } else {
                    selectMovieOpiList = new HashMap<String, List<Opi>>();
                    selectMovieOpiList.put(playdate, response.getOpiList());
                    mOpiListCache.put(movieid, selectMovieOpiList);
                }
            }
        });
        mOpiListTask.asyncParse();

    }

    private void clearOpiListview() {
        mOpiListAdapter.getOpiList().clear();
        mOpiListAdapter.notifyDataSetInvalidated();
    }

    private void refreshOpiListUi(List<Opi> opiList) {
        // 数据全数加载完毕，关闭加载框
        dismissLoadingDialog();
        if (opiList != null && !opiList.isEmpty()) {
            mOpiListAdapter.getOpiList().addAll(opiList);
        }
        mOpiListAdapter.notifyDataSetChanged();
    }

    /**
     * 打开地图
     */
    private void openMap() {
        if (mCinemaLat == 0.0 || mCinemaLng == 0.0) {
            T.showShort(this, "");
        } else {
            LatLng userPos = new LatLng(mCinemaLat, mCinemaLng);
            LatLng cinemaPos = new LatLng(mCinemaLat, mCinemaLng);
            RouteParaOption rpo = new RouteParaOption().startPoint(userPos).startName("我的位置").endPoint(cinemaPos).endName(mCinemaName);
            try {
//                BaiduMapRoutePlan.openBaiduMapWalkingRoute(rpo,this);//步行
                BaiduMapRoutePlan.openBaiduMapDrivingRoute(rpo, this);//驾车驶
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.movie_dtl: {
                if (mSelectedMovie != null) {
                    Intent intent = new Intent(this, MovieDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra(CinemaConstants.EXTRA_MOVIE_DETAIL, mSelectedMovie);
                    startActivity(intent);
                }
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Opi item = (Opi) parent.getAdapter().getItem(position);
        int cplistSize = item.getCpList().size();
        if (cplistSize == 1)// 只有一家cp时直接选座界面
        {
            Cp cp = item.getCpList().get(0);
            startSelectSeat(cp.getCpid(), cp.getMpid(), item.getCinemaname(), item.getPlaytime(), cp.getPtprice());
        } else {
            List<Opi> list = mOpiListAdapter.getOpiList();
            for (Opi opi : list) {
                if (opi == item) {
                    // 展开或者收缩cplist
                    item.setExpand(!item.isExpand());
                } else {
                    opi.setExpand(false);
                }
            }
            mOpiListAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 启动选座界面
     *
     * @param cpid
     * @param mpid
     * @param cinemaname
     * @author lxh
     * @since 2015-11-5
     */
    public void startSelectSeat(long cpid, long mpid, String cinemaname, long playtime, int unitPrice) {
        Intent intent = new Intent(this, CinemaSelectSeatActivity.class);
        intent.putExtra(CinemaConstants.EXTRA_MPID, mpid);
        intent.putExtra(CinemaConstants.EXTRA_CINEMA_NAME, cinemaname);
        intent.putExtra(CinemaConstants.EXTRA_MOVIE_PHOTO_URL, mSelectedMovie.getLogo());
        intent.putExtra(CinemaConstants.EXTRA_MOVIE_PLAYTIME, playtime);
        intent.putExtra(CinemaConstants.EXTRA_CINEMA_ADDRESS, mCinemaAddress);
        intent.putExtra(CinemaConstants.EXTRA_CPID, cpid);
        intent.putExtra(CinemaConstants.EXTRA_CINEMA_NAME, mCinemaName);
        intent.putExtra(CinemaConstants.MOVIE_SEAT_UNITPRICE, unitPrice);
        startActivity(intent);
    }

}
