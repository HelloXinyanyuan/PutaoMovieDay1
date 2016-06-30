package com.whunf.putaomovieday1.common.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.adapter.SimplePagerAdapter;
import com.whunf.putaomovieday1.common.core.AppConfig;
import com.whunf.putaomovieday1.common.storage.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户功能的引导页
 */
public class SplashActivity extends AppCompatActivity {

    private int[] imgRes = {
            R.drawable.start_i1,
            R.drawable.start_i2,
            R.drawable.start_i3,
            R.drawable.start_i4,
    };

    private ViewPager mVpGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int loadVersion = PreferenceUtil.loadInt(PreferenceUtil.KEY_SPLASH_LOAD_VERSION, 0);
        if (loadVersion == AppConfig.SPLASH_VERSION) {//如果之前加载过，直接跳入到主界面
            jumpToMain();
            return;
        }

        mVpGuide = (ViewPager) findViewById(R.id.vp_guide);
        int pageCount = 4;

        //创建并且初始化view的集合，用于在ViewPager中显示
        List<View> views = new ArrayList<>(pageCount);
        for (int i = 0; i < pageCount; i++) {
            ImageView imv = new ImageView(this);
            imv.setImageResource(imgRes[i]);
            imv.setScaleType(ImageView.ScaleType.FIT_XY);
            //加入集合
            views.add(imv);
        }
        //让最后一个视图有点击后进入主界面的效果
        views.get(pageCount - 1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存，标记引导页已经加载
                PreferenceUtil.save(PreferenceUtil.KEY_SPLASH_LOAD_VERSION, AppConfig.SPLASH_VERSION);
                jumpToMain();
            }
        });
        mVpGuide.setAdapter(new SimplePagerAdapter(views));
    }

    /**
     * 跳入到主界面
     */
    private void jumpToMain() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

}
