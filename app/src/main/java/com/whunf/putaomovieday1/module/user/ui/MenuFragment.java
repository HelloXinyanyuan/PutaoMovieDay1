package com.whunf.putaomovieday1.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseFragment;
import com.whunf.putaomovieday1.common.ui.SettingActivity;
import com.whunf.putaomovieday1.common.util.T;

/**
 *菜单页
 */
public class MenuFragment extends BaseFragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        view.findViewById(R.id.layout_login).setOnClickListener(this);
        view.findViewById(R.id.tv_order_weixiaofei).setOnClickListener(this);
        view.findViewById(R.id.tv_order_daifukuan).setOnClickListener(this);
        view.findViewById(R.id.tv_order_daipingjia).setOnClickListener(this);
        view.findViewById(R.id.tv_order_tuikuan).setOnClickListener(this);
        view.findViewById(R.id.btn_my_msg).setOnClickListener(this);
        view.findViewById(R.id.btn_setting).setOnClickListener(this);


        return view;
    }


    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()) {
            case R.id.layout_login:
                //去登录
                T.showShort(getActivity(), "登录");
                intent.setClass(getActivity(),LoginBySmsVertifycodeActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_order_weixiaofei:
                intent.setClass(getActivity(),OrderListActivity.class);
                startActivity(intent);
                //未消费
                T.showShort(getActivity(), "未消费");
                break;
            case R.id.tv_order_daifukuan:
                //待付款
                T.showShort(getActivity(), "待付款");
                break;

            case R.id.tv_order_daipingjia:
                //待评价
                T.showShort(getActivity(), "待评价");
                break;
            case R.id.tv_order_tuikuan:
                //退款
                T.showShort(getActivity(), "退款");
                break;
            case R.id.btn_my_msg:
                //我的消息
                T.showShort(getActivity(), "我的消息");
                break;

            case R.id.btn_setting:
                //设置
                intent.setClass(getActivity(),SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
