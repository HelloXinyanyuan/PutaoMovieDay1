package com.whunf.putaomovieday1.common.util;

import android.util.Log;

/**
 * 日志工具类
 */
public class LogUtil
{

    public static final String TAG_PREFIX = "PutaoMovie";

    /**
     * here, the TAG is a module TAG. please don't confuse with in each File
     * TAG. We can open the debug log by the TAG: log.tag.<TAG> = D
     */
    private static final String TAG = TAG_PREFIX;

    private static final String TAG_INIT = TAG_PREFIX + "_INIT";

    private static final String TAG_FLOW = TAG_PREFIX + "_FLOW";

    private static boolean mHwLog_e = true;//是否开启error级别的日志打印

    private static boolean mHwLog_v = true;//

    /* It control all development stage log */
    private static boolean mHwDbg=true;//

    /* It controll all the import flow log */
    // public static boolean HWFLOW =
    // SystemProperties.getBoolean("ro.debuggable", false);
    private static boolean mHwFlow = true;//

//    static
//    {
//        try
//        {
//            // Class<?> log = Class.forName("android.util.Log");
//            // boolean HWLog = log.getField("HWLog").getBoolean(null);
//            // boolean HWModuleLog =
//            // log.getField("HWModuleLog").getBoolean(null);
//            // HWDBG = HWLog || (HWModuleLog && Log.isLoggable(TAG, Log.DEBUG));
//            //
//            // boolean HWINFO = log.getField("HWINFO").getBoolean(null);
//            // HWFLOW = HWINFO || (HWModuleLog && Log.isLoggable(TAG,
//            // Log.INFO));
//
//            mHwDbg = Log.isLoggable(TAG, Log.DEBUG);
//            mHwFlow = Log.isLoggable(TAG, Log.INFO);
//
//            Log.i(TAG, "HWDBG:" + mHwDbg + " HWFLOW:" + mHwFlow);
//        }
//        catch (Throwable t)
//        {
//            t.printStackTrace();
//            Log.i(TAG, "init ptlog fail." + "HWDBG:" + mHwDbg + " HWFLOW:" + mHwFlow);
//        }
//    };

    public static void i(String tag, String msg)
    {
        if (mHwFlow)
        {
            if (tag != null && msg != null)
            {
                Log.i(TAG, "[" + tag + "]" + msg);
            }
        }
    }

    public static void i(String tag, String msg, Throwable throwable)
    {
        if (mHwFlow)
        {
            if (tag != null && msg != null)
            {
                Log.i(TAG, "[" + tag + "]" + msg, throwable);
            }
        }
    }

    public static void d(String tag, String msg)
    {
        if (mHwDbg)
        {
            if (tag != null && msg != null)
            {
                Log.d(TAG, "[" + tag + "]" + msg);
            }
        }
    }

    public static void d(String tag, String msg, Throwable throwable)
    {
        if (mHwDbg)
        {
            if (tag != null && msg != null)
            {
                Log.d(TAG, "[" + tag + "]" + msg, throwable);
            }
        }
    }

    public static void w(String tag, String msg)
    {
        if (mHwLog_e)
        {
            if (tag != null && msg != null)
            {
                // Log.w(tag, msg);
                Log.w(TAG, "[" + tag + "]" + msg);
            }
        }
    }

    public static void w(String tag, String msg, Throwable throwable)
    {
        if (mHwLog_e)
        {
            if (tag != null && msg != null)
            {
                Log.w(TAG, "[" + tag + "]" + msg, throwable);
            }
        }
    }

    public static void e(String tag, String msg)
    {
        if (mHwLog_e)
        {
            if (tag != null && msg != null)
            {
                Log.e(TAG, "[" + tag + "]" + msg);
            }
        }
    }

    public static void e(String tag, String msg, Throwable throwable)
    {
        if (mHwLog_e)
        {
            if (tag != null && msg != null)
            {
                Log.e(TAG, "[" + tag + "]" + msg, throwable);
            }
        }
    }

    public static void v(String tag, String msg)
    {
        if (mHwLog_v)
        {
            if (tag != null && msg != null)
            {
                Log.v(TAG, "[" + tag + "]" + msg);
            }
        }
    }

    public static void v(String tag, String msg, Throwable throwable)
    {
        if (mHwLog_v)
        {
            if (tag != null && msg != null)
            {
                Log.v(TAG, "[" + tag + "]" + msg, throwable);
            }
        }
    }

}
