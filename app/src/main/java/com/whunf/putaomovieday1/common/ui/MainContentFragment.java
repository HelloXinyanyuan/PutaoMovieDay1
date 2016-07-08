package com.whunf.putaomovieday1.common.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.whunf.putaomovieday1.common.util.T;
import com.whunf.putaomovieday1.common.util.UserInfoUtil;
import com.whunf.putaomovieday1.common.util.location.LocationMgr;
import com.whunf.putaomovieday1.common.util.location.LocationPostion;
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
    private CinemaListFragment mCinemaListFragment;
    private MovieListFragment mMovieListFragment;

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
        mTvHomeCity.setOnClickListener(this);
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

        mCinemaListFragment = new CinemaListFragment();
        mMovieListFragment = new MovieListFragment();
        fragments.add(mMovieListFragment);
        fragments.add(mCinemaListFragment);
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
                if (position == 1) {
                    mCinemaListFragment.lazyLoadData();
                }
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
//        public void onLocationSuccess(final String city, String address) {
//            Log.d(TAG, "onLocationSuccess() called with: " + "city = [" + city + "], address = [" + address + "]");
//            String currentCity = CityMgr.getInstance().loadCity();
//            if (!TextUtils.isEmpty(city) && !currentCity.equals(city)) {
//                TextView textview = new TextView(getActivity());
//                textview.setText("当前城市是：【" + currentCity + "】定位的是：【" + city + "】是否切换");
//
//                new AlertDialog.Builder(getActivity()).setTitle("定位提示").setView(textview).setPositiveButton("切换", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        CityMgr.getInstance().saveCity(city);
//                    }
//                }).setNegativeButton("不切换", null).create().show();
//            }
//        }

        @Override
        public void onLocationSuccess(final LocationPostion locationPostion) {

            Log.d(TAG, "onLocationSuccess() called with: " + "locationPostion = [" + locationPostion + "]");
            String currentCity = CityMgr.getInstance().loadCity();
            if (locationPostion == null) {//空判断，防止空指针
                return;
            }
            //将用户定位到的位置信息保存起来
            UserInfoUtil.getInstance().savePos(locationPostion);

            final String city = locationPostion.getCity();
            if (!TextUtils.isEmpty(city) && !currentCity.equals(city)) {
                TextView textview = new TextView(getActivity());
                textview.setText("当前城市是：【" + currentCity + "】定位的是：【" + city + "】是否切换");

                new AlertDialog.Builder(getActivity()).setTitle("定位提示").setView(textview).setPositiveButton("切换", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switchCityAction(city);
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
            case R.id.tv_home_city:
                startActivityForResult(new Intent(getActivity(), CitySelectActivity.class), 1);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String city = CityMgr.getInstance().loadCity();
            switchCityAction(city);
            T.showShort(getActivity(), "city:" + city);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 响应切换城市后的动作
     * @param city
     */
    private void switchCityAction(String city){
        mTvHomeCity.setText(city);
        mMovieListFragment.initData();//重新加载影片列表
        mCinemaListFragment.setHasLoadData(false);//加载影院列表
    }
}
