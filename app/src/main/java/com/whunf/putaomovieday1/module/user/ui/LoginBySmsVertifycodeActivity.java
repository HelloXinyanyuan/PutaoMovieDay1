package com.whunf.putaomovieday1.module.user.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.core.UrlConfig;
import com.whunf.putaomovieday1.common.parser.CommParserTask;
import com.whunf.putaomovieday1.common.parser.FullTaskListener;
import com.whunf.putaomovieday1.module.user.req.GetSmscodeReqJson;
import com.whunf.putaomovieday1.module.user.resp.GetSmscodeResp;
import com.whunf.putaomovieday1.module.user.resp.LoginSmscodeResp;


/**
 * 短信验证码登录
 */
public class LoginBySmsVertifycodeActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = "LoginBySms";
    private CommParserTask<GetSmscodeResp> mGetSmscodeTask;
    private CommParserTask<LoginSmscodeResp> mLoginBySmscodeTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_sms_vertifycode);

        findViewById(R.id.putao_get_captchar_bt).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.putao_get_captchar_bt:
                Log.d(TAG, "onClick() called with: " + "view = [" + view + "]");
                //创建异步请求任务
                String jsonStr = new GetSmscodeReqJson("13116558419").getJsonString();//此处注意fastjson的坑
                mGetSmscodeTask = new CommParserTask.Builder<GetSmscodeResp>(UrlConfig.UserPath.LOGIN, GetSmscodeResp.class)
                        .setMethod(CommParserTask.RequstMethod.POST).setContent(jsonStr)//设置为post请求方式以及请求体的内容
                       .setContentType(CommParserTask.ContentType.JSON)
                        .setTaskStatusListener(new FullTaskListener<GetSmscodeResp>(this) {
                            @Override
                            public void onTaskSuccess(GetSmscodeResp response) {
                                Log.d(TAG, "onTaskSuccess() called with: " + "response = [" + response + "]");
                                super.onTaskSuccess(response);
                            }
                        })
                        .createTask();
                //开启解析
                mGetSmscodeTask.asyncParse();
                break;
        }
    }
}
