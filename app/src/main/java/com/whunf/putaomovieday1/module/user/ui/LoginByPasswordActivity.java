package com.whunf.putaomovieday1.module.user.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.core.UrlConfig;
import com.whunf.putaomovieday1.common.msg.LoginEvent;
import com.whunf.putaomovieday1.common.parser.CommParserTask;
import com.whunf.putaomovieday1.common.parser.FullTaskListener;
import com.whunf.putaomovieday1.common.util.T;
import com.whunf.putaomovieday1.common.util.UserInfoUtil;
import com.whunf.putaomovieday1.module.user.entity.UserInfo;
import com.whunf.putaomovieday1.module.user.req.LoginByPasswordReqJson;
import com.whunf.putaomovieday1.module.user.resp.LoginByPasswordResp;

import org.greenrobot.eventbus.EventBus;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码登录
 */
public class LoginByPasswordActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginBySms";
    private CommParserTask<LoginByPasswordResp> mLoginByPwdTask;
    private EditText mEtUsername;
    private EditText mEtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_password);

        findViewById(R.id.putao_confirm_bt).setOnClickListener(this);
        findViewById(R.id.putao_login_captchar_tv).setOnClickListener(this);//切换到验证码登录

        mEtUsername = (EditText) findViewById(R.id.putao_username_et);
        mEtPwd = (EditText) findViewById(R.id.putao_password_et);

    }

    /**
     * 密码摘要处理,一次md5一次sha-1再一次md5 并且摘要过程不转换成16进制,直接再次摘要
     *
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @author zj
     * @since 2015-5-16
     */
    private String encodePassword(String password) throws NoSuchAlgorithmException {
        byte[] utf8Bytes = password.getBytes(Charset.forName("UTF-8"));
        MessageDigest digest = MessageDigest.getInstance("MD5");
//        digest.update(utf8Bytes);
//        byte[] digests = digest.digest();
        byte[] digests= digest.digest(utf8Bytes);

        digest = MessageDigest.getInstance("SHA-1");
        digest.update(digests);
        digests = digest.digest();

        digest = MessageDigest.getInstance("MD5");
        digest.update(digests);
        digests = digest.digest();
        return bytes2HexStr(digests);
    }

    /**
     * 字节数组转16进制字符串
     * @param digests
     * @return
     */
    private String bytes2HexStr(byte[] digests) {
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < digests.length; i++) {
            int val = ((int) digests[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val).toUpperCase());
        }
        return hexValue.toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.putao_confirm_bt://登录
                String accName=mEtUsername.getText().toString();//账户名
                String pwd=mEtPwd.getText().toString();
                try {
                    pwd=encodePassword(pwd);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                String jsonStr=new LoginByPasswordReqJson(accName,pwd).getJsonString();

                mLoginByPwdTask=new CommParserTask.Builder<LoginByPasswordResp>(UrlConfig.UserPath.LOGIN,LoginByPasswordResp.class)
                       .setMethod(CommParserTask.RequstMethod.POST).setContent(jsonStr).setContentType(CommParserTask.ContentType.JSON)
                        .setTaskStatusListener(new FullTaskListener<LoginByPasswordResp>(this){
                           @Override
                           public void onTaskSuccess(LoginByPasswordResp response) {
                               if (response.getRet_code().equals("0000")){

                                   LoginByPasswordResp.RelateUsers[] relateUsers = response.getRelateUsers();
                                   if (relateUsers != null) {
                                       String accName= response.getRelateUsers()[0].getAccName();
                                       String ptToken= response.getPt_token();
                                       //将用户信息保存在本地
                                       UserInfo userInfo=new UserInfo(ptToken,accName);
                                       UserInfoUtil.getInstance().saveUserInfo(userInfo);
                                       T.showShort(LoginByPasswordActivity.this,"登录成功！");

                                       //如果登录成功，发送一个消息
                                       EventBus.getDefault().post(new LoginEvent(true));
                                       finish();
                                   }else{
                                       T.showShort(LoginByPasswordActivity.this,"密码错误");
                                   }

                               }else
                               {
                                   T.showShort(LoginByPasswordActivity.this,"登录失败");
                               }
                           }
                       })
                        .createTask();
                mLoginByPwdTask.asyncParse();

                break;

        }
    }
}
