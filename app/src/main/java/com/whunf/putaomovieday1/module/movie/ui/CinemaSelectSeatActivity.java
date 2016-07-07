package com.whunf.putaomovieday1.module.movie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.core.UrlConfig;
import com.whunf.putaomovieday1.common.parser.CommParserTask;
import com.whunf.putaomovieday1.common.parser.FullTaskListener;
import com.whunf.putaomovieday1.common.parser.ParserTaskUtils;
import com.whunf.putaomovieday1.common.util.GraphicUtil;
import com.whunf.putaomovieday1.common.util.T;
import com.whunf.putaomovieday1.common.widget.HeaderLayout;
import com.whunf.putaomovieday1.module.movie.resp.OpiSeatInfoResp;
import com.whunf.putaomovieday1.module.movie.resp.entity.OpiSeatInfo;
import com.whunf.putaomovieday1.module.movie.resp.entity.Seat;
import com.whunf.putaomovieday1.module.movie.util.CinemaConstants;
import com.whunf.putaomovieday1.module.movie.widget.SelectSeatView;

import java.util.HashMap;
import java.util.Map;

/**
 * 影院选座界面
 */
public class CinemaSelectSeatActivity extends BaseActivity implements View.OnClickListener, SelectSeatView.SeatSelectedChangeListener {

    //传入参数
    private long mCpid;//内容提供商id
    private long mMpid;//场次id
    private long mPlaytime;
    private int mUnitPrice;
    private String mCinemaAddress;
    private String mCinemaName;
    private String mMovieLogo;

    //视图控件
    private TextView mTvCinemaName;
    private Button mBtnCreateOrder;
    private SelectSeatView mSelectSeatView;

    //异步任务
    private CommParserTask<OpiSeatInfoResp> mSeatTask;
    private LinearLayout mLLSeatsInfo;
    private HeaderLayout mHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_select_seat);
        parseIntent();
        initView();
        initData();
    }


    private void parseIntent() {
        Intent intent = getIntent();
        mCpid = intent.getLongExtra(CinemaConstants.EXTRA_CPID, 0);
        mMpid = intent.getLongExtra(CinemaConstants.EXTRA_MPID, 0);
        mPlaytime = intent.getLongExtra(CinemaConstants.EXTRA_MOVIE_PLAYTIME, 0);
        mUnitPrice = intent.getIntExtra(CinemaConstants.MOVIE_SEAT_UNITPRICE, 0);
        mCinemaName = intent.getStringExtra(CinemaConstants.EXTRA_CINEMA_NAME);
        mCinemaAddress = intent.getStringExtra(CinemaConstants.EXTRA_CINEMA_ADDRESS);
        mMovieLogo = intent.getStringExtra(CinemaConstants.EXTRA_MOVIE_PHOTO_URL);
    }

    private void initView() {
        mHeader = (HeaderLayout) findViewById(R.id.header);
        mTvCinemaName = (TextView) findViewById(R.id.tv_cinema_name);
        mBtnCreateOrder = (Button) findViewById(R.id.btn_create_order);
        mBtnCreateOrder.setOnClickListener(this);
        mSelectSeatView = (SelectSeatView) findViewById(R.id.ssv_select_seat);
        mSelectSeatView.setSeatSelectedChangeListener(this);
        mLLSeatsInfo = (LinearLayout) findViewById(R.id.ll_seats_info);
        mTvCinemaName.setText(mCinemaName);

    }

    private void initData() {
        //配置参数和请求路径
        String reqPath = null;
        reqPath = UrlConfig.MoviePath.OPI_SEAT_INFO;
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("cpid", mCpid + "");
        reqParams.put("mpid", mMpid + "");
        reqPath = ParserTaskUtils.buildPath(reqParams, reqPath);
        //创建请求任务
        mSeatTask = new CommParserTask<OpiSeatInfoResp>(reqPath, OpiSeatInfoResp.class);
        mSeatTask.setTaskListener(new FullTaskListener<OpiSeatInfoResp>(this) {
            @Override
            public void onTaskSuccess(OpiSeatInfoResp response) {
                OpiSeatInfo data = response.getData();
                mSelectSeatView.setSeatData(data);
                mHeader.setTitleTxt(data.getMoviename());
            }
        });
        mSeatTask.asyncParse();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_order:
                T.showShort(this,mSelectSeatView.getSelectedSeatLable());
                break;

        }
    }

    @Override
    public void onSeatSelectedChangeListener(Seat s) {
        TextView textView = (TextView) mLLSeatsInfo.findViewWithTag(s);
        if (textView == null) {
            textView = new TextView(this);
            textView.setText(s.getRowNumber() + "排" + s.getColNumber() + "座位");
            textView.setTag(s);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.rightMargin = GraphicUtil.dip2px(this, 10);
            mLLSeatsInfo.addView(textView, lp);
        } else {
            mLLSeatsInfo.removeView(textView);
        }
    }
}
