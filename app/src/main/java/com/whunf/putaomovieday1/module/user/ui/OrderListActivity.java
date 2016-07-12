package com.whunf.putaomovieday1.module.user.ui;

import android.os.Bundle;
import android.util.Log;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.core.UrlConfig;
import com.whunf.putaomovieday1.common.parser.CommParserTask;
import com.whunf.putaomovieday1.common.parser.FullTaskListener;
import com.whunf.putaomovieday1.common.storage.db.entity.PTOrderBean;
import com.whunf.putaomovieday1.common.util.location.CommUtil;
import com.whunf.putaomovieday1.common.widget.HeaderLayout;
import com.whunf.putaomovieday1.common.widget.xlistview.XListView;
import com.whunf.putaomovieday1.module.user.adapter.OrderListAdapter;
import com.whunf.putaomovieday1.module.user.resp.OrderListResp;

import java.util.List;

/**
 * 订单列表
 */
public class OrderListActivity extends BaseActivity implements XListView.IXListViewListener {
    private static final String TAG = "OrderListActivity";
    private XListView listview;
    private OrderListAdapter mOrderListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        HeaderLayout header = (HeaderLayout) findViewById(R.id.header);
        header.setTitleTxt("我的订单");

        listview=(XListView) findViewById(R.id.listview);
        listview.setPullLoadEnable(true);
        listview.setXListViewListener(this);
        mOrderListAdapter = new OrderListAdapter(this);
        listview.setAdapter(mOrderListAdapter);

        initData();

    }
private CommParserTask<OrderListResp> mOrderListTask;

//    http://api.putao.so/spay/pay/order/list?timestamp=1468308025831&channel_no=wandoujia&page_size=20&product_type=0&order_timestamp=0&page_no=1&dev_no=208bb3c7cc0da3223edbb99b40eb46a6&

    private void initData() {
        String cookie=CommUtil.getCookie();
        Log.d(TAG, "initData() called with: " + "cookie:"+cookie);
        mOrderListTask=new CommParserTask.Builder<OrderListResp>(UrlConfig.UserPath.ORDER_LIST,OrderListResp.class)
                .setMethod(CommParserTask.RequstMethod.GET)
                .putHeader("Cookie", cookie)
                .putParams("page_size","20")
                .putParams("page_no","1")
                .putParams("product_type","0")
                .putParams("order_timestamp","0")
                .setTaskStatusListener(new FullTaskListener<OrderListResp>(this){
                    @Override
                    public void onTaskSuccess(OrderListResp response) {

                        if ("0000".equals(response.getRet_code())){
                            List<PTOrderBean> result = response.getData().getResult();
                            mOrderListAdapter.addAll(result);
                        }
                        Log.d(TAG, "onTaskSuccess() called with: " + "response = [" + response + "]");
                    }
                })
                .createTask();
        mOrderListTask.asyncParse();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
