package com.whunf.putaomovieday1.common.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.util.LogUtil;

import java.io.File;
import java.net.URI;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

/**
 *
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnShareSdk;
    private Button mBtnShareNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        // 初始化ShareSDK
        ShareSDK.initSDK(this);
        //手机自带分享
        mBtnShareNormal = (Button) findViewById(R.id.btn_share_content);
        mBtnShareNormal.setOnClickListener(this);
        //share sdk
        mBtnShareSdk = (Button) findViewById(R.id.btn_share_sdk);
        mBtnShareSdk.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_share_sdk:
                shareByThirdPartySdk();
                break;
            case R.id.btn_share_content:
//                Intent intent=new Intent(Intent.ACTION_SEND_MULTIPLE);
//                intent.setType("text/plain");
////      intent.setPackage("com.sina.weibo");
//                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
//                intent.putExtra(Intent.EXTRA_TEXT, "你好这是分享 ");
//                intent.putExtra(Intent.EXTRA_TITLE, "我是标题");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                startActivity(Intent.createChooser(intent, "请选择"));

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                URI uri=new File(Environment.getExternalStorageDirectory(),"abcefg.png").toURI();
                LogUtil.i("Pmovie",uri.toString());
                shareIntent.putExtra(Intent.EXTRA_STREAM,uri );
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent,"请选择谁"));
                break;
        }
    }

    /**
     * 通过第三方的分享工具分享
     */
    private void shareByThirdPartySdk() {
        OnekeyShare oks = new OnekeyShare();
//        oks.setSilent(!showContentEdit);
//        if (platformToShare != null) {
//            oks.setPlatform(platformToShare);
//        }
        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        //oks.setAddress("12345678901"); //分享短信的号码和邮件的地址
        oks.setTitle("ShareSDK--Title");
        oks.setTitleUrl("http://mob.com");
        oks.setText("ShareSDK--文本");
        //oks.setImagePath("/sdcard/test-pic.jpg");  //分享sdcard目录下的图片
        oks.setImageUrl("https://mmbiz.qlogo.cn/mmbiz/5loes2l9T0n1CWxd1ycsUVRFianLOD2ibRX9635orS6mUwyNkjMkHAmDTV2b8AtD0nlmPl30ojb2UqVJkXN0fQyg/0");
        oks.setUrl("http://www.mob.com"); //微信不绕过审核分享链接
        //oks.setFilePath("/sdcard/test-pic.jpg");  //filePath是待分享应用程序的本地路劲，仅在微信（易信）好友和Dropbox中使用，否则可以不提供
        oks.setComment("分享"); //我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
        oks.setSite("ShareSDK");  //QZone分享完之后返回应用时提示框上显示的名称
        oks.setSiteUrl("http://mob.com");//QZone分享参数
        oks.setVenueName("ShareSDK");
        oks.setVenueDescription("This is a beautiful place!");
        // 将快捷分享的操作结果将通过OneKeyShareCallback回调
        //oks.setCallback(new OneKeyShareCallback());
        // 去自定义不同平台的字段内容
        //oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
        // 在九宫格设置自定义的图标
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        String label = "ShareSDK";
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {

            }
        };
        oks.setCustomerLogo(logo, label, listener);

        // 为EditPage设置一个背景的View
        //oks.setEditPageBackground(getPage());
        // 隐藏九宫格中的新浪微博
        // oks.addHiddenPlatform(SinaWeibo.NAME);

        // String[] AVATARS = {
        // 		"http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
        // 		"http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg" };
        // oks.setImageArray(AVATARS);              //腾讯微博和twitter用此方法分享多张图片，其他平台不可以

        // 启动分享
        oks.show(this);

    }
}
