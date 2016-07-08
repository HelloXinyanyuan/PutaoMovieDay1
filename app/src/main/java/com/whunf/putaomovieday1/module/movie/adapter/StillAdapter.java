package com.whunf.putaomovieday1.module.movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.whunf.putaomovieday1.R;

/**
 * 剧照适配器
 */
public class StillAdapter extends RecyclerView.Adapter<StillViewHolder> {


    private String[] stills;

    private ImageLoader imageLoader;

    public StillAdapter(ImageLoader imageLoader, String[] split) {
        this.stills = split;
        this.imageLoader = imageLoader;
    }

    @Override
    public StillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        return new StillViewHolder(View.inflate(context, R.layout.movie_dtl_stills_item, null));
    }

    @Override
    public void onBindViewHolder(StillViewHolder holder, final int position) {
        String stillUrl = stills[position];
        holder.imv.setImageUrl(stillUrl, imageLoader);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });
    }

    OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return stills.length;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }




}

class StillViewHolder extends RecyclerView.ViewHolder {

    NetworkImageView imv;

    public StillViewHolder(View itemView) {
        super(itemView);
        imv = (NetworkImageView) itemView.findViewById(R.id.img_movie_dtl_stills);
    }
}




