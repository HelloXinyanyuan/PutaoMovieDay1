package com.whunf.putaomovieday1.module.movie.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import com.whunf.putaomovieday1.common.util.CityMgr;
import com.whunf.putaomovieday1.common.util.T;
import com.whunf.putaomovieday1.common.util.location.LocationMgr;
import com.whunf.putaomovieday1.common.util.location.LocationPostion;
import com.whunf.putaomovieday1.module.movie.adapter.CinemaListAdapter;
import com.whunf.putaomovieday1.module.movie.resp.Cinema;
import com.whunf.putaomovieday1.module.movie.resp.CinemaResp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 影院列表
 * Created by Administrator on 2016/6/22.
 */
public class CinemaListFragment extends BaseFragment implements View.OnClickListener, LocationMgr.MovieLocationListener {


    private ListView mCinemaListLv;
    private TextView tvUserLocation;

    private boolean hasLoadData = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = View.inflate(getActivity(), R.layout.fragment_cinema_list, null);
        mCinemaListLv = (ListView) inflate.findViewById(R.id.cinema_list_lv);
        tvUserLocation = (TextView) inflate.findViewById(R.id.tv_location);
        inflate.findViewById(R.id.btn_relocation).setOnClickListener(this);

        return inflate;
    }

    /**
     *
     */
    public void lazyLoadData() {
        if (!hasLoadData) {
            //初始化数据
            initData();
            tvUserLocation.setText(loadUserAddre());
            hasLoadData = true;
        }
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


    /**
     * 加载用户当前位置
     *
     * @return
     */
    private LocationPostion loadUserPos() {
        LocationPostion locationPostion = null;
        try {
            String jsonStr = PreferenceUtil.loadString(PreferenceUtil.KEY_USER_POS, "");
            locationPostion = JSONObject.parseObject(jsonStr, LocationPostion.class);
        } catch (Exception e) {
        }
        return locationPostion;
    }


    StringRequest request;

    @Override
    public void initData() {
        String citycode = null;
        try {
            citycode = URLEncoder.encode(CityMgr.getInstance().loadCity(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }        //创建request
        String url = "http://api.putao.so/sbiz/movie/cinema/list?citycode=" + citycode;
        request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//处理成功的String返回
                //将返回结果转成对象
                CinemaResp cinemaResp = JSONObject.parseObject(response, CinemaResp.class);
                //将适配器与GridView关联
                CinemaListAdapter cinemaListAdapter = new CinemaListAdapter(getActivity());
                List<Cinema> data = cinemaResp.getData();
                LocationPostion usePos = loadUserPos();

                if (usePos != null) {
                    //加工影院列表数据——计算当前用户与指定影院的距离
                    //构建当前用户位置
                    LatLng userPos = new LatLng(usePos.getLatitude(), usePos.getLongitude());
                    for (Cinema cinema : data) {
                        //构建影院位置
                        LatLng cinemaPos = new LatLng(Double.parseDouble(cinema.getLatitude()), Double.parseDouble(cinema.getLongitude()));

                        if (cinema.getCs().equals("1")){//当他是高德坐标系时需要转换成百度坐标系，不然有偏差
                            CoordinateConverter convert=new CoordinateConverter();
                            convert.coord(cinemaPos);
                            convert.from(CoordinateConverter.CoordType.COMMON);
                            cinemaPos=convert.convert();
                        }

                        double distance = DistanceUtil.getDistance(cinemaPos, userPos);//计算两点间距离
                        cinema.setDistance(distance);//将计算后的距离存储在cinema对象中
                    }
                }


                if (data != null) {
                    cinemaListAdapter.addAll(data);
                    mCinemaListLv.setAdapter(cinemaListAdapter);
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
        }
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
            String locationJsonStr = JSONObject.toJSONString(locationPostion);
            PreferenceUtil.save(PreferenceUtil.KEY_USER_POS, locationJsonStr);
        }

        LocationMgr.getInstance().removeListener(this);
        LocationMgr.getInstance().destroy();
    }

    @Override
    public void onLocationFailed() {
        tvUserLocation.setText("定位失败");
    }
}
