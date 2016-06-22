package com.whunf.putaomovieday1.module.movie.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.PMApplication;
import com.whunf.putaomovieday1.common.util.BitmapCache;
import com.whunf.putaomovieday1.common.util.T;
import com.whunf.putaomovieday1.module.movie.resp.Movie;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class MovieListAdapter extends BaseAdapter {
    List<Movie> datas;
    private ImageLoader imageLoader;

    public MovieListAdapter() {

    }

    public MovieListAdapter(List<Movie> datas) {
        this.datas = datas;
        //初始化imageloader
        imageLoader = new ImageLoader(PMApplication.getInstance().getRequestQueue(), new BitmapCache());
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.movie_list_item, null);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Movie movie = datas.get(position);

        holder.ratingTxt.setText(movie.getGeneralmark());
        holder.ratingBar.setRating(Float.parseFloat(movie.getGeneralmark()) / 2);
        holder.posterNiv.setImageUrl(movie.getLogo(), imageLoader);
        holder.gceditionTxt.setText(movie.getGcedition());
        holder.nameTxt.setText(movie.getMoviename());
        holder.buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(v.getContext(), position + "buy btn");
            }
        });

        return convertView;
    }


    final class ViewHolder {

        NetworkImageView posterNiv;
        TextView nameTxt;
        TextView ratingTxt;
        RatingBar ratingBar;
        TextView gceditionTxt;
        TextView buyBtn;

        public ViewHolder(View v) {
            posterNiv = (NetworkImageView) v.findViewById(R.id.movie_poster);
            nameTxt = (TextView) v.findViewById(R.id.movie_name);
            ratingTxt = (TextView) v.findViewById(R.id.rating_txt);
            ratingBar = (RatingBar) v.findViewById(R.id.rating_rb);
            gceditionTxt = (TextView) v.findViewById(R.id.movie_gcedition);
            buyBtn = (TextView) v.findViewById(R.id.movie_buy_btn);
            v.setTag(this);

        }


    }


}
