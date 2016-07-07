package com.whunf.putaomovieday1.module.movie.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.DistanceUtil;
import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseFragment;
import com.whunf.putaomovieday1.common.core.PMApplication;
import com.whunf.putaomovieday1.common.storage.PreferenceUtil;
import com.whunf.putaomovieday1.common.ui.MainActivity;
import com.whunf.putaomovieday1.common.util.CityMgr;
import com.whunf.putaomovieday1.common.util.NumberUtil;
import com.whunf.putaomovieday1.common.util.T;
import com.whunf.putaomovieday1.common.util.UserInfoUtil;
import com.whunf.putaomovieday1.common.util.location.LocationMgr;
import com.whunf.putaomovieday1.common.util.location.LocationPostion;
import com.whunf.putaomovieday1.module.movie.adapter.CinemaListAdapter;
import com.whunf.putaomovieday1.module.movie.resp.entity.Cinema;
import com.whunf.putaomovieday1.module.movie.resp.CinemaListResp;
import com.whunf.putaomovieday1.module.movie.util.CinemaConstants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * 影院列表
 * Created by Administrator on 2016/6/22.
 */
public class CinemaListFragment extends BaseFragment implements View.OnClickListener, LocationMgr.MovieLocationListener,
        AdapterView.OnItemClickListener {


    private ListView mCinemaListLv;
    private TextView tvUserLocation;

    private boolean hasLoadData = false;
    private TextView mTvArea;
    private TextView mTvSort;

    private long mMovieId;

    /**
     * 所有的影院数据
     */
    private List<Cinema> mAllCinemaDatas = new ArrayList<>();
    /**
     * 当前的影院集合
     */
    private List<Cinema> mCurrentCinemaDatas = new ArrayList<>();
    /**
     * 影院列表适配器
     */
    private CinemaListAdapter mCinemaListAdapter;
    /**
     * 区域列表的适配器
     */
    private ArrayAdapter<String> mAreaAdapter;
    /**
     * 区域集合数据
     */
    private HashSet<String> mAreaSet;
    //影院筛选的弹框
    private PopupWindow mAreaPopwindow;
    /**
     * 影院排序的弹框
     */
    private PopupWindow mSortPopwindow;

    /**
     * 当前Fragment是否立即加载数据显示ui
     */
    private boolean loadQuickLoad = false;


    public void setLoadQuickLoad(boolean loadQuickLoad) {
        this.loadQuickLoad = loadQuickLoad;
    }

    //区域筛选列表的item点击
    private AdapterView.OnItemClickListener mAreaItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            List<Cinema> filterCinemas = new ArrayList<>();
            if (position == 0) {//“全部”
                filterCinemas.addAll(mAllCinemaDatas);
            } else {
                String areaName = mAreaAdapter.getItem(position);
                for (Cinema cinema : mAllCinemaDatas) {
                    if (areaName.equals(cinema.getCountyname())) {//如果名字匹配，就放入集合
                        filterCinemas.add(cinema);
                    }
                }
            }

            mCinemaListAdapter.clear();
            mCinemaListAdapter.addAll(filterCinemas);
            mAreaPopwindow.dismiss();//点击item后隐藏popupwindow

            mCurrentCinemaDatas = filterCinemas;
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = View.inflate(getActivity(), R.layout.fragment_cinema_list, null);
        mCinemaListLv = (ListView) inflate.findViewById(R.id.cinema_list_lv);
        mCinemaListLv.setOnItemClickListener(this);
        tvUserLocation = (TextView) inflate.findViewById(R.id.tv_location);
        mTvArea = (TextView) inflate.findViewById(R.id.tv_cinema_area);
        mTvArea.setOnClickListener(this);
        mTvSort = (TextView) inflate.findViewById(R.id.tv_cinema_sort);
        mTvSort.setOnClickListener(this);

        if (!isFromHome()) {//如果来自首页窗口，隐藏定位栏
            ((View) tvUserLocation.getParent()).setVisibility(View.GONE);
        }

        inflate.findViewById(R.id.btn_relocation).setOnClickListener(this);

        if (loadQuickLoad) {//如果设置了立即加载数据
            refreshUI();
        }
        return inflate;
    }

    /**
     *
     */
    public void lazyLoadData() {
        if (!hasLoadData) {
            refreshUI();
            hasLoadData = true;
        }
    }

    private void refreshUI() {
        //初始化数据
        initData();
        tvUserLocation.setText(loadUserAddre());
    }


    private long getMovieId() {
        long mid = getActivity().getIntent().getLongExtra("movieId", 0);
        return mid;
    }

    @Override
    public void onDestroyView() {
        LocationMgr.getInstance().removeListener(this);
        super.onDestroyView();
    }

    private String loadUserAddre() {
        String address = null;

        try {
            String jsonStr = PreferenceUtil.loadString(PreferenceUtil.KEY_USER_POS, "");
            LocationPostion locationPostion = JSONObject.parseObject(jsonStr, LocationPostion.class);
            if (locationPostion != null) {
                address = locationPostion.getAddress();
            }
        } catch (Exception e) {
        }
        return address;
    }

    private StringRequest request;

    @Override
    public void initData() {
        String citycode = null;
        try {
            citycode = URLEncoder.encode(CityMgr.getInstance().loadCity(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //创建request
        String url = "http://api.putao.so/sbiz/movie/cinema/list?citycode=" + citycode;
        long mid = getMovieId();
        if (mid != 0) {
            url = url + "&movieid=" + mid;
        }


        request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//处理成功的String返回
                //将返回结果转成对象
                CinemaListResp cinemaResp = JSONObject.parseObject(response, CinemaListResp.class);
                //将适配器与GridView关联
                mCinemaListAdapter = new CinemaListAdapter(getActivity());
                List<Cinema> data = cinemaResp.getData();
                initCinemaDatas(data);//对原始数据加工
                mCurrentCinemaDatas = data;
                mAllCinemaDatas.addAll(data);
                if (data != null) {
                    mCinemaListAdapter.addAll(data);
                    mCinemaListLv.setAdapter(mCinemaListAdapter);
                } else {
                    T.showShort(getActivity(), "没有影院数据");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {//处理异常

            }
        });
        //添加到请求队列中
        PMApplication.getInstance().getRequestQueue().add(request);


    }

    private boolean isFromHome() {
        return getActivity() instanceof MainActivity;
    }


    /**
     * 对影院列表集合数据二次加工
     */
    private void initCinemaDatas(List<Cinema> datas) {
        //先排序
        sortByDistancePriority(datas);

        mAreaSet = new HashSet<String>();
        LocationPostion usePos = UserInfoUtil.getInstance().getLastPos();
        if (usePos != null) {
            //加工影院列表数据——计算当前用户与指定影院的距离
            //构建当前用户位置
            LatLng userPos = new LatLng(usePos.getLatitude(), usePos.getLongitude());
            for (Cinema cinema : datas) {
                //构建影院位置
                LatLng cinemaPos = new LatLng(Double.parseDouble(cinema.getLatitude()), Double.parseDouble(cinema.getLongitude()));

                if (cinema.getCs().equals("1")) {//当他是高德坐标系时需要转换成百度坐标系，不然有偏差
                    CoordinateConverter convert = new CoordinateConverter();
                    convert.coord(cinemaPos);
                    convert.from(CoordinateConverter.CoordType.COMMON);
                    cinemaPos = convert.convert();
                }

                double distance = DistanceUtil.getDistance(cinemaPos, userPos);//计算两点间距离
                cinema.setDistance(distance);//将计算后的距离存储在cinema对象中
                //将每一家影院的区域加入集合，集合有去重功能
                mAreaSet.add(cinema.getCountyname());
                //设置影院的底价
                cinema.setStepPrice(Integer.parseInt(cinema.getPricerange().split("-")[0]) / 100);
            }
        }


    }

    /**
     * 影院列表集合数据进行距离优先排序
     *
     * @param datas
     */
    private void sortByDistancePriority(List<Cinema> datas) {
        Collections.sort(datas, new Comparator<Cinema>() {
            @Override
            public int compare(Cinema lhs, Cinema rhs) {
                int distancResult = Double.compare(lhs.getDistance(), rhs.getDistance());
                if (distancResult == 0) {//当距离相同时价格最低的排在前面
                    distancResult = lhs.getStepPrice() - rhs.getStepPrice();
                }
                return distancResult;
            }
        });
    }

    @Override
    public void onDestroy() {
        if (request != null) {
            request.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_relocation:
                tvUserLocation.setText("正在定位中");
                LocationMgr.getInstance().addListener(this);
                LocationMgr.getInstance().startLocation();
                break;
            case R.id.tv_cinema_area://区域筛选
                doAreaFilter();
                break;
            case R.id.tv_cinema_sort://排序
                doSortCinema();
                break;
        }
    }

    /**
     * 执行影院排序
     */
    private void doSortCinema() {

        if (mSortPopwindow == null) {
            //将Set数据转为List数据
            List<String> areaData = new ArrayList<>();
            areaData.add("离我最近");
            areaData.add("起价最低");
            ListView listView = new ListView(getActivity());
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            sortByDistancePriority(mCurrentCinemaDatas);
                            break;
                        case 1:
                            Collections.sort(mCurrentCinemaDatas, new Comparator<Cinema>() {
                                @Override
                                public int compare(Cinema lhs, Cinema rhs) {
                                    int stepResult = lhs.getStepPrice() - rhs.getStepPrice();
                                    if (stepResult == 0) {//当价格相同时距离最近的排在前面
                                        stepResult = Double.compare(lhs.getDistance(), rhs.getDistance());
                                    }
                                    return stepResult;
                                }
                            });
                            break;
                    }
                    //刷新适配器
                    mCinemaListAdapter.clear();
                    mCinemaListAdapter.addAll(mCurrentCinemaDatas);
                    mSortPopwindow.dismiss();
                }
            });
            mAreaAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, areaData);
            listView.setAdapter(mAreaAdapter);
            mSortPopwindow = new PopupWindow(listView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
            mSortPopwindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));//设置成白色背景，响应返回键必须设置的步骤
        }
        mSortPopwindow.showAsDropDown(mTvArea);


    }


    /**
     * 执行区域筛选
     */
    private void doAreaFilter() {
        if (mAreaSet == null) {
            return;
        }

        if (mAreaPopwindow == null) {
            //将Set数据转为List数据
            List<String> areaData = new ArrayList<>();
            areaData.add("全部");
            for (String area : mAreaSet) {
                areaData.add(area);
            }
            ListView listView = new ListView(getActivity());
            listView.setOnItemClickListener(mAreaItemClick);
            mAreaAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, areaData);
            listView.setAdapter(mAreaAdapter);
            mAreaPopwindow = new PopupWindow(listView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
            mAreaPopwindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));//设置成白色背景，响应返回键必须设置的步骤
        }
        mAreaPopwindow.showAsDropDown(mTvArea);
    }

//    @Override
//    public void onLocationSuccess(String city, String address) {
//        if (!TextUtils.isEmpty(address)) {
//            PreferenceUtil.save(PreferenceUtil.KEY_USER_POS,address);
//            tvUserLocation.setText(address);
//        }
//    }

    @Override
    public void onLocationSuccess(LocationPostion locationPostion) {
        if (locationPostion != null) {
            String address = locationPostion.getAddress();
            PreferenceUtil.save(PreferenceUtil.KEY_USER_POS, address);
            tvUserLocation.setText(address);
            //将用户定位到的位置信息保存起来
            UserInfoUtil.getInstance().savePos(locationPostion);
        }

        LocationMgr.getInstance().removeListener(this);
        LocationMgr.getInstance().destroy();
    }

    @Override
    public void onLocationFailed() {
        tvUserLocation.setText("定位失败");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Cinema cinema = (Cinema) adapterView.getAdapter().getItem(i);
        if (cinema == null) {
            return;
        }
        Intent intent = new Intent(getActivity(), CinemaDetailActivity.class);
        intent.putExtra(CinemaConstants.EXTRA_CINEMA_ID, cinema.getId());
        intent.putExtra(CinemaConstants.EXTRA_MOVIEID, getMovieId() != 0 ? getMovieId() : cinema.getLowmovieid());
        intent.putExtra(CinemaConstants.EXTRA_CINEMA_NAME, cinema.getCinemaname());
        intent.putExtra(CinemaConstants.EXTRA_CINEMA_ADDRESS, cinema.getAddress());
        intent.putExtra(CinemaConstants.EXTRA_CINEMA_LAT, NumberUtil.parseDoubleSafe(cinema.getLatitude()));
        intent.putExtra(CinemaConstants.EXTRA_CINEMA_LNG, NumberUtil.parseDoubleSafe(cinema.getLongitude()));
        intent.putExtra(CinemaConstants.EXTRA_CINEMA_CS, cinema.getCs());
        String movieName = getActivity().getIntent().getStringExtra(CinemaConstants.EXTRA_MOVIE_NAME);
        intent.putExtra(CinemaConstants.EXTRA_MOVIE_NAME, movieName);

        startActivity(intent);
    }
}
