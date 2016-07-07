package com.whunf.putaomovieday1.module.movie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.module.movie.resp.entity.Movie;

import java.util.List;

/**
 * 影院详情中影片列表适配器
 */
public class ImageAdapter extends BaseAdapter {

    /**
     * 当前时间，为yyyy-MM-dd 00:00:00:000格式
     */
    private long mCurrentTime;

    private int selectItem;

    private List<Movie> mMovieList;

    private ImageLoader mLoader;

    public ImageAdapter(List<Movie> cinemaList, ImageLoader loader) {
        this.mMovieList = cinemaList;
        this.mLoader = loader;
        mCurrentTime = System.currentTimeMillis();
    }

    @Override
    public int getCount() {
        return getMovieList() == null ? 0 : getMovieList().size();
    }

    @Override
    public Movie getItem(int position) {
        if (getMovieList() == null || getMovieList().size() <= position) {
            return null;
        }
        return getMovieList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        Movie movie = getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.putao_open_play_list_item_mlstitem, null);
            holder = new ViewHolder();
            holder.movie_poster = (NetworkImageView) convertView.findViewById(R.id.movie_poster);
            holder.movie_presell = convertView.findViewById(R.id.movie_presell);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (selectItem == position) {
            // Animation animation = AnimationUtils.loadAnimation(context,
            // R.anim.my_scale_action); //实现动画效果
            int width = context.getResources().getDimensionPixelOffset(
                    R.dimen.putao_movie_opi_gallery_highlight_width);
            int height = context.getResources().getDimensionPixelOffset(
                    R.dimen.putao_movie_opi_gallery_highlight_height);
            holder.movie_poster.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
            // imageView.startAnimation(animation); //选中时，这是设置的比较大
        } else {
            int width = context.getResources().getDimensionPixelOffset(
                    R.dimen.putao_movie_opi_gallery_normall_width);
            int height = context.getResources().getDimensionPixelOffset(
                    R.dimen.putao_movie_opi_gallery_normall_height);
            holder.movie_poster.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
            // 未选中
        }
        holder.movie_poster.setImageUrl(movie.getLogo(), mLoader);

        long releasedate = movie.getReleasedate();
        if (releasedate > mCurrentTime) {
            holder.movie_presell.setVisibility(View.VISIBLE);
        } else {
            holder.movie_presell.setVisibility(View.GONE);
        }
        return convertView;
    }

    public List<Movie> getMovieList() {
        return mMovieList;
    }

    public void setSelectItem(int selectItem) {

        if (this.selectItem != selectItem) {
            this.selectItem = selectItem;
            notifyDataSetChanged();
        }
    }

    private final class ViewHolder {

        /**
         * 海报图
         */
        NetworkImageView movie_poster;

        /**
         * 预售
         */
        View movie_presell;
    }

}