package com.whunf.putaomovieday1.module.movie.util;

import android.view.View;
import android.view.ViewGroup;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.PMApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * 
 ************************************************ <br>
 * 文件名称: CinemaUtils.java <br>
 * 版权声明: <b>深圳市葡萄信息技术有限公司</b> 版权所有 <br>
 * 创建人员: lxh <br>
 * 文件描述: 电影业务相关的工具类<br>
 * 修改时间: 2015-6-25 下午3:46:25 <br>
 * 修改历史: 2015-6-25 1.00 初始版本 <br>
 ************************************************* 
 */
public class CinemaUtils
{

    private static final String TAG = "CinemaUtils";

    public static String formatOrderItemSeat(String waitFormatSeat)
    {
        String[] seats = new String[]
        {};
        seats = waitFormatSeat.split(",");
        for (int i = 0; i < seats.length; i++)
        {
            int index = seats[i].indexOf('座');
            seats[i] = seats[i].substring(0, index + 1);
            if (i != seats.length - 1)
                seats[i] += ",";
        }
        StringBuilder sb = new StringBuilder();
        for (String seat : seats)
        {
            sb.append(seat);
        }
        return sb.toString();
    }

    public static String hidePhone(String waitHidePhone)
    {
        char[] phoneNo = waitHidePhone.toCharArray();

        for (int i = 3; i < 7; i++)
        {
            phoneNo[i] = '*';
        }
        return String.valueOf(phoneNo);
    }


    /**
     * 分转元字串，支持角、分显示。float形式显示去掉尾数为“.00”或者“.0”
     * 
     * @param num
     * @return
     */
    public static String centsToYuanStr(int num)
    {
        return num % 100 == 0 ? num / 100 + "" : (float) num / 100 + "";
    }


    /**
     * 格式化今天的时间，即，将今天的时间设置为yyyy-MM-dd 00:00:00:000格式的
     * 
     * @author lxh
     * @since 2015-6-25
     * @return
     */
    public static Calendar getFormatedToday()
    {
        SimpleDateFormat mDateFormat = new SimpleDateFormat(CinemaConstants.DATE_PATTERN_YDM,Locale.CHINA);
        final Calendar today = Calendar.getInstance();
        // 将今天的时间设置为yyyy-MM-dd 00:00:00:000格式的
        try
        {
            today.setTime(mDateFormat.parse(mDateFormat.format(today.getTime())));
        }
        catch (ParseException e1)
        {
            e1.printStackTrace();
        }
        return today;
    }

    /**
     * 处理日期，显示为“今天 12-30” “周日01-3 ”等
     * 
     * @param today
     * @param date
     * @return
     */
    public static String getPlaydateStr(Calendar today, Date date)
    {
        
        SimpleDateFormat dateFormat2 = new SimpleDateFormat(PMApplication.getInstance().getString(
                R.string.putao_movie_playlist_cinema_format_pattern2), Locale.CHINA);
        SimpleDateFormat dateFormat3 = new SimpleDateFormat(PMApplication.getInstance().getString(
                R.string.putao_movie_playlist_cinema_format_pattern3), Locale.CHINA);
        String dealedStr = null;
        try
        {
            int dayDiff = daysBetween(today.getTime(), date);//计算相隔天数
            if (dayDiff == 0)
            {
                dealedStr = PMApplication.getInstance().getString(R.string.putao_movie_playlist_pd_today,
                        dateFormat2.format(date));
            }
            else if (dayDiff == 1)
            {
                dealedStr = PMApplication.getInstance().getString(R.string.putao_movie_playlist_pd_tmr,
                        dateFormat2.format(date));
            }
            else if (dayDiff == 2)
            {
                dealedStr = PMApplication.getInstance().getString(R.string.putao_movie_playlist_pd_atmr,
                        dateFormat2.format(date));
            }
            else if (dayDiff > 2 && dayDiff < 6)
            {
                dealedStr = dateFormat3.format(date);
            }
            else
            {
                dealedStr = dateFormat2.format(date);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return dealedStr;
    }

    
    /**
     * 处理view选中
     * 
     * @param v
     */
    public static void doViewSelected(View v)
    {
        if (v == null)
        {
            return;
        }
        ViewGroup parent = (ViewGroup) v.getParent();
        for (int i = 0, len = parent.getChildCount(); i < len; i++)
        {
            View child = parent.getChildAt(i);
            if (child == v)
            {
                child.setSelected(true);
            }
            else
            {
                child.setSelected(false);
            }
        }
    }


    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }
}
