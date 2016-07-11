package com.whunf.putaomovieday1.module.movie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.parser.CommParserTask;
import com.whunf.putaomovieday1.common.parser.FullTaskListener;
import com.whunf.putaomovieday1.common.util.T;
import com.whunf.putaomovieday1.common.util.UserInfoUtil;
import com.whunf.putaomovieday1.module.movie.resp.AddTicketOrderResp;
import com.whunf.putaomovieday1.module.movie.util.CinemaConstants;
import com.whunf.putaomovieday1.module.user.entity.UserInfo;
import com.whunf.putaomovieday1.module.user.req.BaseReqJson;
import com.whunf.putaomovieday1.module.user.ui.LoginByPasswordActivity;
import com.whunf.putaomovieday1.module.user.util.MD5;

/**
 * 创建订单窗口
 */
public class CreateOrderActivity extends BaseActivity {

    String creatOrder="https://ssl-api.putao.cn/spay/pay/order_new";
    String alipayParam="https://ssl-api.putao.cn/spay/pay/order/alipay";

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
        //验证登录
        UserInfo userInfo = UserInfoUtil.getInstance().loadUserInfo();
if (userInfo==null){
    startActivity(new Intent(this, LoginByPasswordActivity.class));
}
        String pttoken=userInfo.getPt_token();
String cookie="app_id=10;channel=putao_live_01;version=4.3.0;new_app_version=4.3.0;baseline_version=4.3.0;dev_no=208bb3c7cc0da3223edbb99b40eb46a6;band=DOOV_D910T;city=%E6%B7%B1%E5%9C%B3;location=22.531184,113.937719;district=%E5%8D%97%E5%B1%B1%E5%8C%BA;pt_androidid=6a051d357feba5b2;pt_imei=352284040670808;pt_mac=00:FF:C3:08:2C:BB;pt_token="+pttoken;
        String mobile = mEtPhone.getText().toString();
//        app_version	4.3.0
//        sub_obj["cinema_name"]
//        city	深圳
//        timestamp	1468221290549
//        dev_no	208bb3c7cc0da3223edbb99b40eb46a6
//        baseline_version	4.3.0
//        district	南山区
//        sub_obj["EXCHANGE_TYPE_MAIZUO"]	8
//        new_app_version	4.3.0
//        sub_obj["room_name"]	巨蟹厅
//        local_sign	290f16ba84c94c1702fc52db08055f72
//        sub_obj["pt_status"]	0
//        sub_obj["EXCHANGE_TYPE_THREE"]	3
//        pt_token	34cbe1be38904122b9648680d662eab1
//        product_type	6
//        sub_obj["EXCHANGE_TYPE_ONE"]	1
//        sub_obj["quantity"]	1
//        sign	1aa2e2b11f5e1b2ba9929f15eadc4c29
//        sys_version	4.4.2
//        sub_obj["movie_id"]	0
//        device_no	208bb3c7cc0da3223edbb99b40eb46a6
//        device	DOOV_D910T
//        channel_no	putao_live_01
//        sub_obj["cinemaAddress"]	南山书城７楼，海雅百货对面，茂业百货旁边。
//        sub_obj["exchange_type"]	-1

        long current= System.currentTimeMillis();
        int unitPrice=getIntent().getIntExtra("unitPrice",0);
        mCreateOrderTask = new CommParserTask.Builder<AddTicketOrderResp>(creatOrder, AddTicketOrderResp.class)
                .setMethod(CommParserTask.RequstMethod.POST)
                .putHeader("Cookie", cookie)
                .putParams("product_id", 5)
                .putParams("product_type", 6)
                .putParams("sub_obj[\"movie_photo_url\"]", "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png")
                .putParams("sub_obj[\"movie_name\"]", "致青春·原来你还在这里")
                .putParams("sub_obj[\"EXCHANGE_TYPE_TWO\"]", 2)
                .putParams("sub_obj[\"order_title\"]", "华夏星光国际影城电影票")
                .putParams("sub_obj[\"cinemaAddress\"]", "南山书城７楼，海雅百货对面，茂业百货旁边")
                .putParams("sub_obj[\"amount\"]",unitPrice)
                .putParams("new_app_version", "4.3.0")
                .putParams("baseline_version", "4.3.0")
                .putParams("pay_price",unitPrice)
                .putParams("pt_token",pttoken)
                .putParams("timestamp",current )
                .putParams("dev_no","208bb3c7cc0da3223edbb99b40eb46a6" )
                .putParams("local_sign", MD5.toMD5(current+"208bb3c7cc0da3223edbb99b40eb46a6"+unitPrice+ BaseReqJson.PT_KEY))
                .putParams("channel_no", "putao_live_01")
                .putParams("app_id", 10)
                .putParams("sub_obj[\"play_time\"]", "2016-07-11 17:35:00")
                .putParams("device", "DOOV_D910T")

                .putParams("sub_obj[\"quantity\"]", 1)

                .putParams("sub_obj[\"cp_id\"]", cpid)
                .putParams("sub_obj[\"mp_id\"]", mpid)
                .putParams("cpparam", cpparam)
                .putParams("sub_obj[\"seat\"]", seatLabel)
                .putParams("sub_obj[\"mobile\"]", mobile)
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

    private void afterCreate() {
    }

}
