package com.whunf.putaomovieday1.module.user.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseFragment;
import com.whunf.putaomovieday1.common.msg.LoginEvent;
import com.whunf.putaomovieday1.common.ui.SettingActivity;
import com.whunf.putaomovieday1.common.util.GraphicUtil;
import com.whunf.putaomovieday1.common.util.T;
import com.whunf.putaomovieday1.common.util.UserInfoUtil;
import com.whunf.putaomovieday1.common.zxing.CaptureActivity;
import com.whunf.putaomovieday1.common.zxing.QrCodeUtil;
import com.whunf.putaomovieday1.module.user.entity.UserInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 菜单页
 */
public class MenuFragment extends BaseFragment implements View.OnClickListener {


    private TextView mTvAccName;
    private View view;
    private View mLayoutLogged;
    private View mLayoutLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        mLayoutLogin = view.findViewById(R.id.layout_login);//未登录布局
        mLayoutLogin.setOnClickListener(this);
        mLayoutLogged = view.findViewById(R.id.layout_logged);//已经登录后的布局
        mLayoutLogged.setOnClickListener(this);
        mTvAccName = (TextView) view.findViewById(R.id.tv_username);
        updateUserInfoUI();

        view.findViewById(R.id.tv_order_weixiaofei).setOnClickListener(this);
        view.findViewById(R.id.tv_order_daifukuan).setOnClickListener(this);
        view.findViewById(R.id.tv_order_daipingjia).setOnClickListener(this);
        view.findViewById(R.id.tv_order_tuikuan).setOnClickListener(this);
        view.findViewById(R.id.btn_my_msg).setOnClickListener(this);
        view.findViewById(R.id.btn_setting).setOnClickListener(this);

        EventBus.getDefault().register(this);
        return view;
    }

    @Subscribe
    public void onEventMainThread(LoginEvent le) {
        updateUserInfoUI();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    /**
     * 根据账户的状态，显示不同的UI效果
     */
    private void updateUserInfoUI() {
        UserInfo userInfo = UserInfoUtil.getInstance().loadUserInfo();
        if (userInfo == null) {
            mLayoutLogin.setVisibility(View.VISIBLE);
            mLayoutLogged.setVisibility(View.GONE);
        } else {//如果已经登录成功
            mLayoutLogged.setVisibility(View.VISIBLE);
            mLayoutLogin.setVisibility(View.GONE);
            mTvAccName.setText(userInfo.getAccName());
        }
    }


    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.layout_login:
                //去登录
                T.showShort(getActivity(), "登录");
                intent.setClass(getActivity(), LoginBySmsVertifycodeActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_logged:
                //去显示登录用户的二维码
                showMyQrcode();
                break;
            case R.id.tv_order_weixiaofei:
                intent.setClass(getActivity(), OrderListActivity.class);
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
                //扫描二维码
                T.showShort(getActivity(), "我的消息");
                intent.setClass(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, 1);
                break;

            case R.id.btn_setting:
                //设置
                intent.setClass(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    private AlertDialog mQrcodeDialog;

    /**
     * 显示我的二维码
     */
    private void showMyQrcode() {
        if (mQrcodeDialog == null) {
            ImageView imv = new ImageView(getActivity());
            Bitmap bitmap = QrCodeUtil.generateQrCode(mTvAccName.getText().toString(), GraphicUtil.dip2px(getActivity(), 150), GraphicUtil.dip2px(getActivity(), 150));
            imv.setImageBitmap(bitmap);
            mQrcodeDialog = new AlertDialog.Builder(getActivity()).setTitle("我的二维码").setView(imv).create();
        }
        mQrcodeDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String dataOfStr = data.getStringExtra("data");
            T.showShort(getActivity(), dataOfStr);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
