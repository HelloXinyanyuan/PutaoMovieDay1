package com.whunf.putaomovieday1.module.user.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.parser.CommParserTask;
import com.whunf.putaomovieday1.module.user.resp.LoginByPasswordResp;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码登录
 */
public class LoginByPasswordActivity extends BaseActivity  implements View.OnClickListener {


    private static final String TAG = "LoginBySms";
    private CommParserTask<LoginByPasswordResp> mGetSmscodeTask;
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
     * 密码加密,一次md5一次sha-1再一次md5 并且加密过程不转换成16进制,直接再次加密
     *
     * @author zj
     * @since 2015-5-16
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     */
    private String encodePassword(String password) throws NoSuchAlgorithmException
    {
        byte[] utf8Bytes = password.getBytes(Charset.forName("UTF-8"));
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(utf8Bytes);
        byte[] digests = digest.digest();

        digest = MessageDigest.getInstance("SHA-1");
        digest.update(digests);
        digests = digest.digest();

        digest = MessageDigest.getInstance("MD5");
        digest.update(digests);
        digests = digest.digest();
        return bytes2HexStr(digests);
    }

    private String bytes2HexStr(byte[] digests) {

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < digests.length; i++) {
            int val = ((int) digests[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    @Override
    public void onClick(View view) {

    }
}
