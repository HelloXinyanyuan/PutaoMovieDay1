package com.whunf.putaomovieday1.module.user.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.storage.db.entity.PTOrderBean;

import java.text.SimpleDateFormat;

/**
 * 订单列表适配器
 */
public class OrderListAdapter extends ArrayAdapter<PTOrderBean>
{


    public OrderListAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.order_list_item, null);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PTOrderBean item=getItem(position);
        holder.oName.setText(item.getTitle());
        holder.oTimeTxt.setText(   SDF.format(item.getC_time()));
        holder.oPrice.setText(String.valueOf(item.getPrice()/100));
        holder.oStatus.setText(item.getStatus());

        return convertView;
    }

    static final SimpleDateFormat SDF=new SimpleDateFormat("MM-dd");
    private final class ViewHolder {

        TextView oName;
        TextView oTimeTxt;
        TextView oPrice;
        TextView oStatus;

        public ViewHolder(View v) {
            oName = (TextView) v.findViewById(R.id.order_name);
            oTimeTxt = (TextView) v.findViewById(R.id.order_date);
            oPrice = (TextView) v.findViewById(R.id.order_price);
            oStatus = (TextView) v.findViewById(R.id.order_status);
            v.setTag(this);
        }

    }
}
