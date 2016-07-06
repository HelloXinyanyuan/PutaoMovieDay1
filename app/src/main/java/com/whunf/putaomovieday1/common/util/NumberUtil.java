package com.whunf.putaomovieday1.common.util;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * 数字工具类
 * 
 * @author lxh
 * @since 2015-5-15
 */
public class NumberUtil
{

    /**
     * 将String转double，不往外报异常，转换错了值为0.0d
     * 
     * @author lxh
     * @since 2015-5-15
     * @param doubleOfStr
     * @return
     */
    public static double parseDoubleSafe(String doubleOfStr)
    {
        double safeDouble = 0.0d;
        if (TextUtils.isEmpty(doubleOfStr))
        {
            return safeDouble;
        }
        try
        {
            safeDouble = Double.parseDouble(doubleOfStr);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return safeDouble;
    }

    /**
     * 将String转float，不往外报异常，转换错了值为0.0f
     * 
     * @author lxh
     * @since 2015-5-15
     * @param floatOfStr
     * @return
     */
    public static float parseFloatSafe(String floatOfStr)
    {
        float safeFloat = 0.0f;
        if (TextUtils.isEmpty(floatOfStr))
        {
            return safeFloat;
        }
        try
        {
            safeFloat = Float.parseFloat(floatOfStr);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return safeFloat;
    }

    /**
     * 将String转long，不往外报异常，转换错了值为0
     * 
     * @author lxh
     * @since 2015-5-15
     * @param longOfStr
     * @return
     */
    public static long parseLongSafe(String longOfStr)
    {
        long safeLong = 0;
        if (TextUtils.isEmpty(longOfStr))
        {
            return safeLong;
        }
        try
        {
            safeLong = Long.parseLong(longOfStr);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return safeLong;
    }

    /**
     * 将String转int，不往外报异常，转换错了值为0
     * 
     * @author lxh
     * @since 2015-5-15
     * @param intOfStr
     * @return
     */
    public static int parseIntSafe(String intOfStr)
    {
        /*
         * modify by putao_lhq at 2015年5月16日 start 添加校验逻辑,空或者非数字的返回为0.
         */
        if (TextUtils.isEmpty(intOfStr) || !TextUtils.isDigitsOnly(intOfStr))
        {
            return 0;
        }
        // end by putao_lhq 2015年5月16日
        int safeInt = 0;
        try
        {
            safeInt = Integer.parseInt(intOfStr);
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
        return safeInt;
    }

    /**
     * 计算使用了优惠券以后的最终金额,单位为分。可用于“至少支付一分钱”的逻辑控制
     * 
     * @author lxh
     * @since 2015-5-15
     * @param originMoneyInCents 原价金额
     * @param voucherInCents 优惠券金额
     * @return 返回差值之后的值。单位为分
     */
    public static int calcMoneyRemain(int originMoneyInCents, int voucherInCents)
    {
        int payInt = originMoneyInCents - voucherInCents;
        return payInt <= 0 ? 1 : payInt;
    }

    /**
     * 四舍五入格式化double类型数 如：3.05显示为3.1
     * 
     * @author lxh
     * @since 2015-6-12
     * @param num 待处理的数
     * @param retainCount 需要保留的位数
     * @return
     */
    public static String formatWithRound(String numOfStr, int retainCount)
    {
        BigDecimal dData = new BigDecimal(numOfStr);
        double data = dData.setScale(retainCount, BigDecimal.ROUND_HALF_UP).doubleValue();
        return "" + data;
    }

    /**
     * 高精度计算两个数的差值
     * 
     * @author lxh
     * @since 2015-9-10
     * @param numA
     * @param numB
     * @return
     */
    public static BigDecimal subtractWithBig(String numA, String numB)
    {
        BigDecimal bigDecimalA = new BigDecimal(numA);
        BigDecimal bigDecimalB = new BigDecimal(numB);
        return bigDecimalA.subtract(bigDecimalB);
    }

    /**
     * 高精度计算两个数的乘积
     * 
     * @author lxh
     * @since 2015-9-10
     * @param numA
     * @param numB
     * @return
     */
    public static BigDecimal multiplyWithBig(String numA, String numB)
    {
        BigDecimal bigDecimalA = new BigDecimal(numA);
        BigDecimal bigDecimalB = new BigDecimal(numB);
        return bigDecimalA.multiply(bigDecimalB);
    }

    /**
     * 高精度计算两个数的和
     *
     * @author lxh
     * @since 2015-9-10
     * @param numA
     * @param numB
     * @return
     */
    public static BigDecimal plusWithBig(String numA, String numB)
    {
        BigDecimal bigDecimalA = new BigDecimal(numA);
        BigDecimal bigDecimalB = new BigDecimal(numB);
        return bigDecimalA.add(bigDecimalB);
    }

}
