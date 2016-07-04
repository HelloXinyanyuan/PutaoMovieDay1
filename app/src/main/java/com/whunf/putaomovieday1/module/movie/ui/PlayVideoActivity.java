package com.whunf.putaomovieday1.module.movie.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.widget.HeaderLayout;

/**
 * 播放预告片视频
 */
public class PlayVideoActivity extends BaseActivity {

    private static final String TAG = "PlayVideoActivity";
    private WebView webView;
    private HeaderLayout headerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        //提取数据
        String videoUrl = getIntent().getStringExtra("videoUrl");
        String movieName = getIntent().getStringExtra("movieName");
        //设置头布局信息
        headerLayout = (HeaderLayout) findViewById(R.id.header);
        headerLayout.setTitleTxt(movieName);
        //初始化视图
        webView = (WebView) findViewById(R.id.wv_video_play);
        webView.loadUrl(videoUrl);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onLoadResource(WebView view, String url) {
                if (url.contains(".mp4?")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);//隐式intent启动系统自带视频播放器
                    intent.setDataAndType(Uri.parse(url), "video/mp4");
                    startActivity(intent);
                    finish();
                    return;
                }
                Log.d(TAG, "onLoadResource() called with: " + "view = [" + view + "], url = [" + url + "]");
                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }
}
