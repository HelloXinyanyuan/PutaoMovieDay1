package com.whunf.putaomovieday1.module.movie.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseFragment;
import com.whunf.putaomovieday1.common.core.PMApplication;
import com.whunf.putaomovieday1.common.util.CityMgr;
import com.whunf.putaomovieday1.common.util.T;
import com.whunf.putaomovieday1.module.movie.adapter.MovieListAdapter;
import com.whunf.putaomovieday1.module.movie.resp.Movie;
import com.whunf.putaomovieday1.module.movie.resp.MovieResp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 影片列表
 * Created by Administrator on 2016/6/22.
 */
public class MovieListFragment extends BaseFragment {
    private GridView mMovieListGv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = View.inflate(getActivity(), R.layout.fragment_movie_list, null);
        mMovieListGv = (GridView) inflate.findViewById(R.id.movie_list_gv);

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
        }
        //创建request
        String url = "http://api.putao.so/sbiz/movie/list?citycode=" + citycode;
        request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//处理成功的String返回
                //将返回结果转成对象
                MovieResp movieResp = JSONObject.parseObject(response, MovieResp.class);
                List<Movie> data = movieResp.getData();

                if (data != null) {
                    MovieListAdapter movieListAdapter = new MovieListAdapter(data);
                    //将适配器与GridView关联
                    mMovieListGv.setAdapter(movieListAdapter);
                } else {
                    T.showShort(getActivity(), "没有影片数据");
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
