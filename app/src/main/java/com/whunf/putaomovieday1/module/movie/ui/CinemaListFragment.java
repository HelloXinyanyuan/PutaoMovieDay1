package com.whunf.putaomovieday1.module.movie.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseFragment;
import com.whunf.putaomovieday1.common.core.PMApplication;
import com.whunf.putaomovieday1.common.util.CityMgr;
import com.whunf.putaomovieday1.common.util.T;
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
public class CinemaListFragment extends BaseFragment {


    private ListView mCinemaListLv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = View.inflate(getActivity(), R.layout.fragment_cinema_list, null);
        mCinemaListLv = (ListView) inflate.findViewById(R.id.cinema_list_lv);

        //初始化数据
        initData();

        return inflate;
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
}
