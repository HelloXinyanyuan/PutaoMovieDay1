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
import com.whunf.putaomovieday1.module.movie.adapter.CinemaListAdapter;
import com.whunf.putaomovieday1.module.movie.resp.CinemaResp;

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

    @Override
    public void initData() {

        //创建request
        String url = "http://api.putao.so/sbiz/movie/cinema/list?citycode=%E6%B7%B1%E5%9C%B3";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//处理成功的String返回
                //将返回结果转成对象
                CinemaResp cinemaResp = JSONObject.parseObject(response, CinemaResp.class);
                //将适配器与GridView关联
                CinemaListAdapter cinemaListAdapter = new CinemaListAdapter(getActivity());
                cinemaListAdapter.addAll(cinemaResp.getData());
                mCinemaListLv.setAdapter(cinemaListAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {//处理异常

            }
        });

        //添加到请求队列中
        PMApplication.getInstance().getRequestQueue().add(request);


    }
}
