package com.whunf.putaomovieday1.module.movie.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.module.movie.resp.Cinema;

/**
 * Created by Administrator on 2016/6/22.
 */
public class CinemaListAdapter extends ArrayAdapter<Cinema> {

    public CinemaListAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.cinema_list_item, null);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Cinema cinema = getItem(position);

        holder.nameTxt.setText(cinema.getCinemaname());
        holder.addrTxt.setText(cinema.getAddress());
        holder.remainCountTxt.setText(cinema.getCountdes());
        String stepPriceStr = Integer.parseInt(cinema.getPricerange().split("-")[0]) / 100 + "";
        holder.stepPriceTxt.setText(stepPriceStr);
//        holder.distanceTxt.setText(cinema.getCinemaname());
        holder.cpCountTxt.setText(cinema.getCpcount() + "");


        return convertView;
    }


    private final class ViewHolder {

        TextView nameTxt;
        TextView addrTxt;
        TextView remainCountTxt;
        TextView stepPriceTxt;
        TextView distanceTxt;
        TextView cpCountTxt;

        public ViewHolder(View v) {
            nameTxt = (TextView) v.findViewById(R.id.cinema_mame);
            addrTxt = (TextView) v.findViewById(R.id.cinema_addr);
            remainCountTxt = (TextView) v.findViewById(R.id.cinema_remain_count);
            stepPriceTxt = (TextView) v.findViewById(R.id.cinema_step_price);
            distanceTxt = (TextView) v.findViewById(R.id.cinema_distance);
            cpCountTxt = (TextView) v.findViewById(R.id.cinema_cp_count);
            v.setTag(this);
        }


    }


}
