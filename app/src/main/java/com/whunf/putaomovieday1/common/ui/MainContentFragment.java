package com.whunf.putaomovieday1.common.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseFragment;
import com.whunf.putaomovieday1.common.util.CityMgr;
import com.whunf.putaomovieday1.common.util.location.LocationMgr;
import com.whunf.putaomovieday1.module.movie.adapter.SimpleFragmentVpAdapter;
import com.whunf.putaomovieday1.module.movie.ui.CinemaListFragment;
import com.whunf.putaomovieday1.module.movie.ui.DiscoverFragment;
import com.whunf.putaomovieday1.module.movie.ui.MovieListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面的碎片
 */
public class MainContentFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "MainContentFragment";
    private ViewPager mViewPager;
    private RadioGroup mNavTabGroup;
    private int[] mTabIds = {R.id.btn_movie, R.id.btn_cinema, R.id.btn_discover};
    private String[] mTabsTxt;
    private TextView mTvHomeTitle;
    private TextView mTvHomeCity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_content, container, false);
        //初始化tab文字数组
        mTabsTxt = getActivity().getResources().getStringArray(R.array.home_tabs_txt);
        //处理头部相关视图
        view.findViewById(R.id.btn_open_menu).setOnClickListener(this);
        mTvHomeTitle = (TextView) view.findViewById(R.id.tv_home_title);
        //设置城市信息
        mTvHomeCity = (TextView) view.findViewById(R.id.tv_home_city);
        mTvHomeCity.setText(CityMgr.getInstance().loadCity());

        //处理导航的tab相关视图逻辑
        mNavTabGroup = (RadioGroup) view.findViewById(R.id.nav_tab_group);
        mNavTabGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int pageIndex = 0;
                for (int i = 0; i < mTabIds.length; i++) {
                    if (mTabIds[i] == checkedId) {//找到了位置
                        pageIndex = i;
                        break;
                    }
                }
                mViewPager.setCurrentItem(pageIndex);
            }
        });

        mViewPager = (ViewPager) view.findViewById(R.id.movie_vp);
        //创建好Fragment
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new MovieListFragment());
        fragments.add(new CinemaListFragment());
        fragments.add(new DiscoverFragment());
        //创建适配器，将fragments传入适配器中
        PagerAdapter adapter = new SimpleFragmentVpAdapter(getChildFragmentManager(), fragments);
        //将适配器与ViewPager关联
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int checkedId = mTabIds[position];
                mNavTabGroup.check(checkedId);
                //根据位置设置标题
                mTvHomeTitle.setText(mTabsTxt[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        LocationMgr.getInstance().addListener(mLocationListener);
        LocationMgr.getInstance().startLocation();
        return view;
    }

    @Override
    public void onDestroyView() {
        LocationMgr.getInstance().removeListener(mLocationListener);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        LocationMgr.getInstance().destroy();
        super.onDestroy();
    }

    private LocationMgr.MovieLocationListener mLocationListener = new LocationMgr.MovieLocationListener() {
        @Override
        public void onLocationSuccess(final String city, String address) {
            Log.d(TAG, "onLocationSuccess() called with: " + "city = [" + city + "], address = [" + address + "]");
            String currentCity = CityMgr.getInstance().loadCity();
            if (!TextUtils.isEmpty(city) && !currentCity.equals(city)) {
                TextView textview = new TextView(getActivity());
                textview.setText("当前城市是：【" + currentCity + "】定位的是：【" + city + "】是否切换");

                new AlertDialog.Builder(getActivity()).setTitle("定位提示").setView(textview).setPositiveButton("切换", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CityMgr.getInstance().saveCity(city);
                    }
                }).setNegativeButton("不切换", null).create().show();
            }

        }

        @Override
        public void onLocationFailed() {

        }
    };


    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open_menu:
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).getSlidingMenu().toggle(true);//开关slidingmenu
                }
                break;
        }
    }
}
