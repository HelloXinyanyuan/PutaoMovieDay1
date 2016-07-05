package com.whunf.putaomovieday1.module.movie.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.adapter.SimplePagerAdapter;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.core.PMApplication;
import com.whunf.putaomovieday1.common.util.BitmapCache;
import com.whunf.putaomovieday1.common.widget.HeaderLayout;
import com.whunf.putaomovieday1.common.widget.TouchImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 剧照大图显示
 */
public class ShowStillActivity extends BaseActivity {

    private ViewPager mVpStills;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_still);
        //提取上一个activity的数据
        String[] stills = getIntent().getStringArrayExtra("stills");
        int currentIndex = getIntent().getIntExtra("currentIndex", 0);
        String movieName = getIntent().getStringExtra("movieName");
        imageLoader = new ImageLoader(PMApplication.getInstance().getRequestQueue(), new BitmapCache());

        //将数据设置到视图
        HeaderLayout headerLayout = (HeaderLayout) findViewById(R.id.header);
        headerLayout.setTitleTxt(movieName);

        List<View> views = new ArrayList<>();
        for (int i = 0; i < stills.length; i++) {
            View v = View.inflate(this, R.layout.still_list_item, null);
            views.add(v);
        }

        StillPageAdapter adapter = new StillPageAdapter(views, stills);
        mVpStills = (ViewPager) findViewById(R.id.vp_stills);
        mVpStills.setAdapter(adapter);//绑定适配器
        mVpStills.setCurrentItem(currentIndex);
    }


    class StillPageAdapter extends SimplePagerAdapter {
        String[] stills;

        public StillPageAdapter(List<View> views, String[] stills) {
            super(views);
            this.stills = stills;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = views.get(position);
            TouchImageView tiv = (TouchImageView) view.findViewById(R.id.tiv_still);
            //第一个参数是图片地址，第二个参数是监听器
            imageLoader.get(stills[position], ImageLoader.getImageListener(tiv, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
            container.addView(view);
            return view;
        }
    }

}
