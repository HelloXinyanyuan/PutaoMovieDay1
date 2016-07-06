package com.whunf.putaomovieday1.module.movie.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.util.LogUtil;
import com.whunf.putaomovieday1.module.movie.resp.entity.Cp;
import com.whunf.putaomovieday1.module.movie.resp.entity.Opi;
import com.whunf.putaomovieday1.module.movie.ui.CinemaDetailActivity;
import com.whunf.putaomovieday1.module.movie.util.CinemaUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 电影场次列表适配器
 *
 * @author lixiaohui
 */
public class OpiListAdapter extends BaseAdapter {


    private ImageLoader loader;

    private List<Opi> mOpiList = new ArrayList<Opi>();

    private int mMovieLength;

    private static final String DATE_FORMAT_STR = "HH:mm";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STR);


    public OpiListAdapter(ImageLoader loader) {
        this.loader = loader;
    }

    @Override
    public int getCount() {
        return getOpiList() == null ? 0 : getOpiList().size();
    }

    @Override
    public Opi getItem(int position) {
        return getOpiList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<Opi> getOpiList() {
        return mOpiList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogUtil.i(getClass().getSimpleName(), "SpeedLog getView=" + System.currentTimeMillis());
        ViewHolder holder = null;
        Opi item = getOpiList().get(position);
        Context context = parent.getContext();

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.putao_open_play_list_item, null);
            holder.startTime = (TextView) convertView.findViewById(R.id.start_time);
            holder.endTime = (TextView) convertView.findViewById(R.id.end_time);
            holder.gewaPrice = (TextView) convertView.findViewById(R.id.gewa_price);
            holder.comparePrice = (TextView) convertView.findViewById(R.id.putao_opi_compare_price);
            holder.expandImg = convertView.findViewById(R.id.putao_opi_expand_imv);
            holder.cpList = (ViewGroup) convertView.findViewById(R.id.putao_cplist);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.startTime.setText(SIMPLE_DATE_FORMAT.format(item.getPlaytime()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(item.getPlaytime());
        calendar.add(Calendar.MINUTE, mMovieLength);
        String argEnd = SIMPLE_DATE_FORMAT.format(calendar.getTime());
        String endtime = context.getString(R.string.putao_movie_playlist_item_end, argEnd,
                (item.getLanguage() + item.getEdition()), item.getRoomname());
        holder.endTime.setText(endtime);
        int lowpriceInCents = getCheapest(item);
        int cplistSize = item.getCpList().size();
        if (cplistSize > 1) {// 大于1家cp才显示
            holder.comparePrice.setVisibility(View.VISIBLE);
            holder.comparePrice.setText(context.getString(R.string.putao_cinemalist_item_compare_price, cplistSize));
            String lowPriceStr = context.getString(R.string.putao_cinemalist_item_lowprice,
                    CinemaUtils.centsToYuanStr(lowpriceInCents));
            // 设置最后一个"起"字的样式
            int lowPriceLen = lowPriceStr.length();
            SpannableString lowPriceSpan = new SpannableString(lowPriceStr);
            lowPriceSpan.setSpan(new AbsoluteSizeSpan(12, true), lowPriceLen - 1, lowPriceLen,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            lowPriceSpan.setSpan(
                    new ForegroundColorSpan(context.getResources().getColor(R.color.putao_text_color_secondary)),
                    lowPriceLen - 1, lowPriceLen, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.gewaPrice.setText(lowPriceSpan);
        } else {
            holder.comparePrice.setVisibility(View.GONE);
            holder.gewaPrice.setText(context.getString(R.string.putao_payment_money,
                    CinemaUtils.centsToYuanStr(lowpriceInCents)));
        }

        if (item.isExpand()) {
            holder.expandImg.setVisibility(View.VISIBLE);
            holder.cpList.setVisibility(View.VISIBLE);
            holder.cpList.removeAllViews();
            for (int i = 0; i < cplistSize; i++) {
                Cp cp = item.getCpList().get(i);
                ViewGroup layout = (ViewGroup) View.inflate(context, R.layout.putao_open_play_list_item_cpitem, null);
//                ((NetworkImageView) layout.findViewById(R.id.putao_cplogo)).setImageUrl(cp.getCplogo(), loader);
                ((TextView) layout.findViewById(R.id.putao_cpname)).setText(cp.getCpname());

                TextView finalPriceTv = (TextView) layout.findViewById(R.id.putao_final_price);
                TextView coupontipTv = ((TextView) layout.findViewById(R.id.putao_cpcouponinfo));
                TextView cppriceTv = ((TextView) layout.findViewById(R.id.putao_item_cp_price));
                int finalPrice = cp.getPtprice();
                // 隐藏优惠券
                cppriceTv.setVisibility(View.GONE);
                coupontipTv.setVisibility(View.GONE);

                finalPriceTv.setText(context.getString(R.string.putao_payment_money,
                        CinemaUtils.centsToYuanStr(finalPrice)));

                layout.setOnClickListener(cpitemClickListener);
                ParameterObj obj = new ParameterObj(cp.getCpid(), cp.getMpid(), item.getCinemaname(),
                        item.getPlaytime(), cp.getPtprice());
                layout.setTag(obj);

                if (i == cplistSize - 1) {// 如果是最后一个就去掉分割线
                    View dividerView = layout.findViewById(R.id.putao_movie_cpitem_divider);
                    dividerView.setVisibility(View.GONE);
                }
                holder.cpList.addView(layout);
            }
        } else {
            holder.expandImg.setVisibility(View.GONE);
            holder.cpList.setVisibility(View.GONE);
        }

        return convertView;
    }

    /**
     * 获取最便宜的价格
     *
     * @param item
     * @return
     */
    private int getCheapest(Opi item) {
        int lowprice = Integer.MAX_VALUE;
        for (int i = 0, size = item.getCpList().size(); i < size; i++) {
            Cp cp = item.getCpList().get(i);
            if (lowprice > cp.getPtprice()) {
                lowprice = cp.getPtprice();
            }
        }
        return lowprice;
    }

    private OnClickListener cpitemClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            LogUtil.i(getClass().getSimpleName(), "SpeedLog onItemClick=" + System.currentTimeMillis());
            Context context = v.getContext();
            if (context instanceof CinemaDetailActivity) {
                ParameterObj obj = (ParameterObj) v.getTag();
                ((CinemaDetailActivity) context).startSelectSeat(obj.cpid, obj.mpid, obj.cinemaname, obj.playtime, obj.unitPrice);
            }

        }
    };

    final class ParameterObj {
        public long cpid;

        public long mpid;

        public String cinemaname;

        public long playtime;

        public int unitPrice;

        public ParameterObj(long cpid, long mpid, String cinemaname, long playtime, int unitPrice) {
            super();
            this.cpid = cpid;
            this.mpid = mpid;
            this.cinemaname = cinemaname;
            this.playtime = playtime;
            this.unitPrice = unitPrice;
        }

    }

    public void setMovieLength(int movieLength) {
        this.mMovieLength = movieLength;
    }


    private final class ViewHolder {

        TextView startTime;

        TextView endTime;

        TextView gewaPrice;

        TextView comparePrice;

        View expandImg;

        ViewGroup cpList;
    }

}
