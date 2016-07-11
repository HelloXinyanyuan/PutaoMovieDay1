package com.whunf.putaomovieday1.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.core.PMApplication;
import com.whunf.putaomovieday1.common.core.UrlConfig;
import com.whunf.putaomovieday1.common.parser.CommParserTask;
import com.whunf.putaomovieday1.common.util.T;
import com.whunf.putaomovieday1.common.util.UserInfoUtil;
import com.whunf.putaomovieday1.module.user.entity.UserInfo;
import com.whunf.putaomovieday1.module.user.req.GetSmscodeReqJson;
import com.whunf.putaomovieday1.module.user.req.LoginSmscodeReqJson;
import com.whunf.putaomovieday1.module.user.resp.GetSmscodeResp;
import com.whunf.putaomovieday1.module.user.resp.LoginSmscodeResp;


/**
 * 短信验证码登录
 */
public class LoginBySmsVertifycodeActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = "LoginBySms";
    private CommParserTask<GetSmscodeResp> mGetSmscodeTask;
    private CommParserTask<LoginSmscodeResp> mLoginBySmscodeTask;
    private EditText mEtUsername;
    private EditText mEtVertifycode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_sms_vertifycode);

        findViewById(R.id.putao_get_captchar_bt).setOnClickListener(this);
        findViewById(R.id.putao_confirm_bt).setOnClickListener(this);
        findViewById(R.id.putao_login_password_tv).setOnClickListener(this);//切换到密码登录

        mEtUsername = (EditText) findViewById(R.id.putao_username_et);
        mEtVertifycode = (EditText) findViewById(R.id.putao_password_et);
    }

    @Override
    public void onClick(View view) {
        String phone = mEtUsername.getText().toString();
        String vertifyCode = mEtVertifycode.getText().toString();
        String reqPath = UrlConfig.UserPath.LOGIN;

        switch (view.getId()) {
            case R.id.putao_get_captchar_bt:
                //创建异步请求任务
                final String jsonStr = new GetSmscodeReqJson(phone).getJsonString();//此处注意fastjson的坑

                StringRequest sr = new StringRequest(Request.Method.POST, reqPath, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse() called with: " + "response = [" + response + "]");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        byte[] body = jsonStr.getBytes();
                        return body;
                    }
                };

                PMApplication.getInstance().getRequestQueue().add(sr);


                Log.d(TAG, "onClick() called with: " + "view = [" + view + "]" + jsonStr);
//                mGetSmscodeTask = new CommParserTask.Builder<GetSmscodeResp>(UrlConfig.UserPath.LOGIN, GetSmscodeResp.class)
//                        .setMethod(CommParserTask.RequstMethod.POST).setContent(jsonStr)//设置为post请求方式以及请求体的内容
//                       .setContentType(CommParserTask.ContentType.JSON)
//                        .setTaskStatusListener(new FullTaskListener<GetSmscodeResp>(this) {
//                            @Override
//                            public void onTaskSuccess(GetSmscodeResp response) {
//                                Log.d(TAG, "onTaskSuccess() called with: " + "response = [" + response + "]");
//                                super.onTaskSuccess(response);
//                            }
//                        })
//                        .createTask();
                //开启解析
//                mGetSmscodeTask.asyncParse();
                break;

            case R.id.putao_confirm_bt:
                final String smsLoginJson = new LoginSmscodeReqJson(vertifyCode, phone).getJsonString();
                StringRequest ssr = new StringRequest(Request.Method.POST, reqPath, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse() called with: " + "response = [" + response + "]");
                        try {
                            LoginSmscodeResp resp = JSONObject.parseObject(response, LoginSmscodeResp.class);
                            if (resp.getRet_code().equals("0000")) {
                                doLoginSuccess(response, resp);
                            }else{
                                T.showShort(LoginBySmsVertifycodeActivity.this, "验证码错误");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            T.showShort(LoginBySmsVertifycodeActivity.this, "验证码错误");
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        return smsLoginJson.getBytes();
                    }
                };
                //将网路请求加入到请求队列中
                PMApplication.getInstance().getRequestQueue().add(ssr);

                break;

            case R.id.putao_login_password_tv://密码登录
                Intent intent=new Intent(this,LoginByPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void doLoginSuccess(String response, LoginSmscodeResp resp) {
        String pt_token = resp.getPt_token();
        String accName = resp.getRelateUsers()[0].getAccName();

        UserInfo userInfo = new UserInfo(pt_token, accName);
        UserInfoUtil.getInstance().saveUserInfo(userInfo);//保存到本地的SharePreference

        T.showShort(LoginBySmsVertifycodeActivity.this, "登录成功！");
        Log.d(TAG, "onResponse() called with: " + "response = [" + response + "]");

        finish();
    }


}
