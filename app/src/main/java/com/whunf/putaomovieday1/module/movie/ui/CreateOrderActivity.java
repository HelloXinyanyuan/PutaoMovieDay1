package com.whunf.putaomovieday1.module.movie.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alipay.sdk.app.PayTask;
import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.msg.OrderEvent;
import com.whunf.putaomovieday1.common.parser.CommParserTask;
import com.whunf.putaomovieday1.common.parser.FullTaskListener;
import com.whunf.putaomovieday1.common.parser.ParserTask;
import com.whunf.putaomovieday1.common.parser.ParserTaskUtils;
import com.whunf.putaomovieday1.common.util.LogUtil;
import com.whunf.putaomovieday1.common.util.T;
import com.whunf.putaomovieday1.common.util.location.CommUtil;
import com.whunf.putaomovieday1.module.movie.req.DetailMovieOrder;
import com.whunf.putaomovieday1.module.movie.req.NormalOrderRequestData;
import com.whunf.putaomovieday1.module.movie.req.OrderEntity;
import com.whunf.putaomovieday1.module.movie.resp.order.AddTicketOrderResp;
import com.whunf.putaomovieday1.module.movie.resp.order.AlipayResp;
import com.whunf.putaomovieday1.module.movie.resp.order.SimpleDataResp;
import com.whunf.putaomovieday1.module.movie.util.CinemaConstants;
import com.whunf.putaomovieday1.module.user.util.PayResult;

import org.greenrobot.eventbus.EventBus;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * 创建订单窗口
 */
public class CreateOrderActivity extends BaseActivity {

    private static final String TAG = "CreateOrderActivity";
    String creatOrder = "http://api.putao.so/sbiz/movie/create_order";
    String postOrder = "http://api.putao.so/spay/pay/order";
    String createAlipayParamUrl = "http://api.putao.so/spay/pay/order/alipay";

    private ParserTask mCreateOrderTask;
//传送订单的任务
    private ParserTask mPostOrderTask;
    private ParserTask mAlipayParamTask;
    private long cpid;
    private long mpid;
    private String cpparam;
    private String seatLabel;
    private EditText mEtPhone;
    private Button mBtnCreateOrder;
    private DetailMovieOrder mDetailMovieOrder;

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
        final String cookie = getCookie();
        if (TextUtils.isEmpty(cookie)) {//为空代表未登录
            return;
        }

        String mobile = mEtPhone.getText().toString();

//        long current = System.currentTimeMillis();
//        int unitPrice = getIntent().getIntExtra("unitPrice", 0);

//        Map<String ,String> header=new HashMap<>();
//        header.put("Cookie", cookie);
//        StringRequest stringRequest=new StringRequest("",null,null){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                return header;
//            }
//        };

        mCreateOrderTask = new CommParserTask.Builder<AddTicketOrderResp>(creatOrder, AddTicketOrderResp.class)
                .setMethod(CommParserTask.RequstMethod.POST)
                .putHeader("Cookie", cookie)
                .putParams("cpid", cpid)
                .putParams("mpid", mpid)
                .putParams("cpparam", cpparam)
                .putParams("seatLabel", seatLabel)
                .putParams("mobile", mobile)
                .setTaskStatusListener(new FullTaskListener<AddTicketOrderResp>(this) {
                    @Override
                    public void onTaskSuccess(AddTicketOrderResp response) {
                        if (response.getRet_code().equals("0000")) {
                            T.showShort(CreateOrderActivity.this, "占座成功！开始传送订单" + response.toString());
                            postOrderToBackend(response);
                        }
                    }
                })
                .createTask();
        mCreateOrderTask.asyncParse();


    }

    String getCookie() {
        //验证登录
        return CommUtil.getCookie();
    }



    /**
     * 将订单信息传送给后台保存
     */
    private void postOrderToBackend(AddTicketOrderResp orderResp) {
        mDetailMovieOrder = new DetailMovieOrder();
        SimpleDateFormat sdf = new SimpleDateFormat(CinemaConstants.DATE_PATTERN_YDMHMS);
        mDetailMovieOrder.setMovie_name(orderResp.getData().getMoviename());
        mDetailMovieOrder.setCinema_name(orderResp.getData().getCinemaname());
        mDetailMovieOrder.setMobile(orderResp.getData().getMobile());
        mDetailMovieOrder.setValid_time(sdf.format(orderResp.getData().getValidtime()));
        mDetailMovieOrder.setAmount(orderResp.getData().getAmount());// 葡萄后台以分为单位
        mDetailMovieOrder.setQuantity(orderResp.getData().getQuantity());
        mDetailMovieOrder.setAdd_time(sdf.format(orderResp.getData().getAddtime()));
        mDetailMovieOrder.setSeat(orderResp.getData().getSeat());
        // 只需要传送cpid
        mDetailMovieOrder.setMp_id(orderResp.getData().getMpid());
        mDetailMovieOrder.setCp_id(orderResp.getData().getCpid());
        mDetailMovieOrder.setOrder_title("MouDianYingYuan电影票");
        mDetailMovieOrder.setMovie_photo_url("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        mDetailMovieOrder.setCinemaAddress("科苑路888");

//        mDetailMovieOrder.setOrder_title("深圳百川国际影城电影票");
//        mDetailMovieOrder.setMovie_photo_url("http://img.putao.so/movie/logo_2685394733.jpg");
//        mDetailMovieOrder.setCinemaAddress("南新路欢乐颂购物中心5楼");

//        local_sign	1cb35b686e8aff559416d40eecf198f3
//        sub_obj["pt_status"]	0
//        product_id	5
//        sub_obj["movie_photo_url"]	http://img.putao.so/movie/logo_2685394733.jpg
//        sub_obj["mp_id"]	9936813
//        sub_obj["movie_name"]	致青春·原来你还在这里
//        product_type	6
//        sub_obj["seat"]	1:1
//        sub_obj["amount"]	3400
//        sub_obj["quantity"]	1
//        timestamp	1468294545155
//        sub_obj["movie_id"]	0
//        sub_obj["valid_time"]	2016-07-12 03:50:44
//        dev_no	20f10f4175287e8c5b80f6a150a64e70
//        channel_no	wandoujia
//        sub_obj["cinemaAddress"]	南新路欢乐颂购物中心5楼
//        sub_obj["serialVersionUID"]	1
//        sub_obj["cp_id"]	116
//        sub_obj["add_time"]	2016-07-12 03:35:44
//        pay_price	3400
//        sub_obj["order_title"]	深圳百川国际影城电影票
//        sub_obj["mobile"]	13116558641


        String reqPath = postOrder;
        final NormalOrderRequestData requestData = new NormalOrderRequestData(null, mDetailMovieOrder.getAmount(),
                5, 6, mDetailMovieOrder,
                DetailMovieOrder.class);
        final Map<String, String> reqParams = requestData.getParams();
        final String cookie = getCookie();
        mPostOrderTask = new CommParserTask.Builder<SimpleDataResp>(reqPath,
                SimpleDataResp.class)
                .setMethod(CommParserTask.RequstMethod.POST)
                .setParams(reqParams)
                .putHeader("Cookie", cookie)
                .setTaskStatusListener(new FullTaskListener<SimpleDataResp>(this) {
                    @Override
                    public void onTaskSuccess(SimpleDataResp response) {
                        if (response.getRet_code().equals("0000") && !TextUtils.isEmpty(response.getData())) {// 回传订单成功
                            mDetailMovieOrder.setOrder_no(response.getData());

                            Map<String, String> stringStringMap = OrderEntity.convertToMap(mDetailMovieOrder, DetailMovieOrder.class);
                            OrderEntity oe = new OrderEntity();
                            oe.setPriceInCents(mDetailMovieOrder.getAmount());
                            oe.setOrderNo(mDetailMovieOrder.getOrder_no());
                            oe.setProductId(5);
                            oe.setProductType(6);
                            oe.setSubObjMap(stringStringMap);

                            String requstBody = oe.toQueryString();
                            //去服务端获取支付宝支付参数
                            createAlipayOrderParams(requstBody);

                            Log.d(TAG, "订单产生成功！onTaskSuccess() called with: " + "response = [" + response + "]");
                            //发送广播，要求前台更新打点数量
//                            sendBroadcast(new Intent("com.whunf.putaomovieday1.update_order_count"));

                            //新建消息内容
                            OrderEvent orderEvent=new OrderEvent();
                            orderEvent.count=3;
                            //发消息
                            EventBus.getDefault().post(orderEvent);
                        }
                    }
                })
                .createTask();
        mPostOrderTask.asyncParse();
    }

    /**
     * 从服务端获得支付宝需要的参数
     */
    private void createAlipayOrderParams(String requstBody) {
        final String cookie = getCookie();
        mAlipayParamTask = new CommParserTask.Builder<AlipayResp>(createAlipayParamUrl, AlipayResp.class).setMethod(CommParserTask.RequstMethod.POST)
                .setContent(requstBody)
                .putHeader("Cookie", cookie)
                .setTaskStatusListener(new FullTaskListener<AlipayResp>(this) {
                    @Override
                    public void onTaskSuccess(AlipayResp response) {
                        if (response.getRet_code().equals("0000")) {
                            T.showShort(CreateOrderActivity.this, response.toString());
                            Log.d(TAG, "onTaskSuccess() called with: " + "response = [" + response + "]");
                            //拉起支付宝支付
                            startAlipay(response.getData());
                        }
                    }
                })
                .createTask();
        mAlipayParamTask.asyncParse();
    }


    @Override
    protected void onDestroy() {
        //退出时取消请求任务
        ParserTaskUtils.cancelParserTask(mCreateOrderTask);
        ParserTaskUtils.cancelParserTask(mAlipayParamTask);
        ParserTaskUtils.cancelParserTask(mPostOrderTask);
        super.onDestroy();
    }

    /**
     * 拉起支付宝支付
     */
    private void startAlipay(final AlipayResp.AlipayBean bean) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //新建一个支付任务
                PayTask alipay = new PayTask(CreateOrderActivity.this);
                String param = createAlipayUrl(bean);

                LogUtil.d(TAG, "alipay param=" + param);
                String alipayResult = alipay.pay(param);//阻塞
                LogUtil.d(TAG, "alipay result=" + alipayResult);

                //处理结果发送到主线程
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = alipayResult;
                mHandler.sendMessage(msg);

            }
        }).start();
    }


    private String createAlipayUrl(AlipayResp.AlipayBean bean) {
        StringBuilder sb = new StringBuilder();
        sb.append("partner=\"");
        sb.append(bean.getPartner());
        sb.append("\"&out_trade_no=\"");
        sb.append(bean.getOut_trade_no());
        sb.append("\"&subject=\"");
        sb.append(bean.getSubject());
        sb.append("\"&body=\"");
        sb.append(bean.getBody());
        sb.append("\"&total_fee=\"");
        sb.append(bean.getTotal_fee());
        sb.append("\"&notify_url=\"");
        // 网址需要做URL编码
        sb.append(URLEncoder.encode(bean.getNotify_url()));
        sb.append("\"&service=\"");
        sb.append(bean.getService());
        sb.append("\"&_input_charset=\"utf-8");
        sb.append("\"&return_url=\"" + URLEncoder.encode("http://m.alipay.com"));
        sb.append("\"&payment_type=\"");
        sb.append(bean.getPayment_type());
        sb.append("\"&seller_id=\"");
        sb.append(bean.getSeller_id());
        sb.append("\"&it_b_pay=\"");
        sb.append("30m");// 订单超时时间，默认0.5小时
        sb.append("\"&sign=\"");
        sb.append(URLEncoder.encode(bean.getSign()));
        sb.append("\"&sign_type=\"RSA\"");
        return sb.toString();
    }

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        T.showShort(CreateOrderActivity.this,"支付成功");
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            T.showShort(CreateOrderActivity.this,"支付结果确认中");

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            T.showShort(CreateOrderActivity.this,"支付失败");

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

}
