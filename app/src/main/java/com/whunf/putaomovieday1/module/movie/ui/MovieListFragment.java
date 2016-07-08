package com.whunf.putaomovieday1.module.movie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.toolbox.StringRequest;
import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseFragment;
import com.whunf.putaomovieday1.common.core.UrlConfig;
import com.whunf.putaomovieday1.common.parser.CommParserTask;
import com.whunf.putaomovieday1.common.parser.FullTaskListener;
import com.whunf.putaomovieday1.common.util.CityMgr;
import com.whunf.putaomovieday1.common.util.T;
import com.whunf.putaomovieday1.module.movie.adapter.MovieListAdapter;
import com.whunf.putaomovieday1.module.movie.resp.MovieListResp;
import com.whunf.putaomovieday1.module.movie.resp.entity.Movie;
import com.whunf.putaomovieday1.module.movie.util.CinemaConstants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 影片列表
 * Created by Administrator on 2016/6/22.
 */
public class MovieListFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private GridView mMovieListGv;
    //所有影片
    private List<Movie> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = View.inflate(getActivity(), R.layout.fragment_movie_list, null);
        mMovieListGv = (GridView) inflate.findViewById(R.id.movie_list_gv);
        mMovieListGv.setOnItemClickListener(this);
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
//        request = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {//处理成功的String返回
//                //将返回结果转成对象
//                MovieListResp movieResp = JSONObject.parseObject(response, MovieListResp.class);
//              data = movieResp.getData();
//
//                if (data != null) {
//                    MovieListAdapter movieListAdapter = new MovieListAdapter(data);
//                    //将适配器与GridView关联
//                    mMovieListGv.setAdapter(movieListAdapter);
//                } else {
//                    T.showShort(getActivity(), "没有影片数据");
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {//处理异常
//
//            }
//        });
//
//        //添加到请求队列中
//        PMApplication.getInstance().getRequestQueue().add(request);


        url= UrlConfig.MoviePath.MOVIE_LIST+"?citycode=" + citycode;
        CommParserTask<MovieListResp> task=new CommParserTask<MovieListResp>(url, MovieListResp.class);
        FullTaskListener fullTaskListener= new FullTaskListener<MovieListResp>(getActivity()) {
            @Override
            public void onTaskSuccess(MovieListResp movieListResp) {
                data = movieListResp.getData();
                if (data != null) {
                    MovieListAdapter movieListAdapter = new MovieListAdapter(data);
                    //将适配器与GridView关联
                    mMovieListGv.setAdapter(movieListAdapter);
                } else {
                    T.showShort(getActivity(), "没有影片数据");
                }
            }
        };
        task.setTaskListener(fullTaskListener);
        task.asyncParse();
    }



    @Override
    public void onDestroy() {
        if (request != null) {
            request.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       Movie movie= data.get(position);
        Intent intent=new Intent(getActivity(),MovieDetailActivity.class);
        intent.putExtra(CinemaConstants.EXTRA_MOVIE_DETAIL,movie);
        startActivity(intent);

    }
}
