package com.whunf.putaomovieday1.module.movie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.core.UrlConfig;
import com.whunf.putaomovieday1.common.parser.CommParserTask;
import com.whunf.putaomovieday1.common.parser.FullTaskListener;
import com.whunf.putaomovieday1.common.util.T;
import com.whunf.putaomovieday1.module.movie.resp.AddTicketOrderResp;
import com.whunf.putaomovieday1.module.movie.util.CinemaConstants;

/**
 * 创建订单窗口
 */
public class CreateOrderActivity extends BaseActivity {

    private CommParserTask<AddTicketOrderResp> mCreateOrderTask;
    private long cpid;
    private long mpid;
    private String cpparam;
    private String seatLabel;
    private EditText mEtPhone;
    private Button mBtnCreateOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        parseIntent();
        initView();
    }


    @Override
    protected void parseIntent() {
        Intent intent = getIntent();
        cpid = intent.getLongExtra(CinemaConstants.EXTRA_CPID, 0);
        mpid = intent.getLongExtra(CinemaConstants.EXTRA_MPID, 0);
        cpparam = intent.getStringExtra(CinemaConstants.EXTRA_CPPARAM);
        seatLabel = intent.getStringExtra(CinemaConstants.MOVIE_ORDER_SEAT);
    }

    private void initView() {
        mEtPhone = (EditText) findViewById(R.id.movie_orderpay_head_phone);
        mBtnCreateOrder = (Button) findViewById(R.id.confirm);
        mBtnCreateOrder.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createOrder();
                    }
                }
        );
    }


    /**
     * 创建订单
     */
    private void createOrder() {
        String mobile = mEtPhone.getText().toString();
        mCreateOrderTask = new CommParserTask.Builder<AddTicketOrderResp>(UrlConfig.MoviePath.ADD_TICKET_ORDER, AddTicketOrderResp.class)
                .putHeader("Cookie", "app_id=10;channel=putao_live_01;version=4.3.0;new_app_version=4.3.0;baseline_version=4.3.0;dev_no=e783aaad228c19080e682a89179f81e2;band=Mi-4c;city=%E6%B7%B1%E5%9C%B3;location=22.570525,113.951177;district=%E5%8D%97%E5%B1%B1%E5%8C%BA;pt_androidid=f917eaec9d96e232;pt_imei=99000672330592;pt_mac=fc:64:ba:92:28:43;pt_token=23bc5ad532694652b504eee425c4904f")
                .putParams("cpid", cpid)
                .putParams("mpid", mpid)
                .putParams("cpparam", cpparam)
                .putParams("seatLabel", seatLabel)
                .putParams("mobile", mobile)
                .setTaskStatusListener(new FullTaskListener<AddTicketOrderResp>(this) {
                    @Override
                    public void onTaskSuccess(AddTicketOrderResp response) {
                        T.showShort(CreateOrderActivity.this, "" + response.toString());
                        super.onTaskSuccess(response);
                    }
                })
                .createTask();
        mCreateOrderTask.asyncParse();
    }

    private void prepareCreate() {//登录操作

    }

    private void afterCreate() {
    }

}
