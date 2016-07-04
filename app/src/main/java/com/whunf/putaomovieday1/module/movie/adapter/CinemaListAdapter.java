package com.whunf.putaomovieday1.module.movie.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.module.movie.resp.Cinema;

import java.text.DecimalFormat;

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
        String stepPriceStr = cinema.getStepPrice() + "";
        holder.stepPriceTxt.setText(stepPriceStr);
        holder.distanceTxt.setText(getDistanceStr(cinema.getDistance()));
        holder.cpCountTxt.setText(cinema.getCpcount() + "");

        return convertView;
    }

    private DecimalFormat decimalFormat = new DecimalFormat(".##");//保留两位小数格式化工具

    /**
     * 获得距离的字符串形式表示
     * @param distance
     * @return
     */
    private String getDistanceStr(double distance) {
        String distanceStr = null;
        if (distance < 1000) {
            distanceStr = decimalFormat.format(distance) + "M";
        } else {
            distanceStr = decimalFormat.format(distance / 1000) + "KM";
        }
        return distanceStr;
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
