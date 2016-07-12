/* Copyright (c) 2009-2011 Matthias Kaeppler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.whunf.putaomovieday1.module.user.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.whunf.putaomovieday1.common.util.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * 
 ************************************************ <br>
 * 文件名称: SystemUtil.java <br>
 * 版权声明: <b>深圳市葡萄信息技术有限公司</b> 版权所有 <br>
 * 创建人员: putao_ffh <br>
 * 文件描述: 系统参数获取工具类<br>
 * 修改时间: 2015-12-31 下午5:10:31 <br>
 * 修改历史: 2015-12-31 1.00 初始版本 <br>
 *************************************************
 */
public class SystemUtil
{

    private static final String TAG = "SystemUtil";

    private static final String PT_DEVICE_ID_FILENAME = "pt_sys2.txt";
    
    private static final String DEFAULT_CHANNEL = "wandoujia";

    private static String sPtDeviceId;


    /**
     * 系统版本
     * @author putao_ffh
     * @since 2015-12-31
     * @return
     */
    public static String getOS()
    {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 手机型号
     * @author putao_ffh
     * @since 2015-12-31
     * @return
     */
    public static String getMachine()
    {
        return android.os.Build.MODEL;
    }

    /**
     * 从META_DATA中获取versionName
     * @author putao_ffh
     * @since 2015-12-31
     * @param context
     * @return
     */
    public static String getAppVersion(Context context)
    {
        return "2.6.0";
    }

    /**
     * 从META_DATA中获取app_id
     * @author putao_ffh
     * @since 2015-12-31
     * @param context
     * @return
     */
    public static String getAppid(Context context)
    {
//        try
//        {
//            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
//                    PackageManager.GET_META_DATA);
//            return String.valueOf(ai.metaData.get("PUTAO_APPID"));
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return "0";
        return "10";//代表独立版本
    }

    /**
     * 从META_DATA中获取versionCode
     * @author putao_ffh
     * @since 2015-12-31
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context)
    {
        /**
         * 在合一版本上读取不到我们自己的versionName和versionCode,把版本写在META_DATA里 modified by cj
         * 2014/11/05 start
         */
        try
        {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            return Integer.parseInt(String.valueOf(ai.metaData.get("versionCode")));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取设备id
     * 
     * @param context
     * @return
     */
    public static String getDeviceId(Context context)
    {
        /**
         * 由于山寨机的存在，imei号存在被盗用的可能，imei不能保整设备的唯一性，所以不再使用imei作为设备号 old
         * code：getImei TelephonyManager tm = (TelephonyManager)
         * context.getSystemService(Context.TELEPHONY_SERVICE); String devid =
         * tm.getDeviceId(); if(null == devid) { // add by cj 2015/04/07 devid =
         * ""; } return devid;
         */
        return getPutaoDeviceId(context);
    }

    /**
     * 
     * 获取imei号
     * 
     * @param context
     * @return String imei号
     */
    public static String getImei(Context context)
    {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String devid = tm.getDeviceId();
        if (null == devid)
        {
            devid = "";
        }
        return devid;
    }
    
    /**
     * 获取META_DATA
     * 
     * @author putao_ffh
     * @since 2015-12-31
     * @param context
     * @return
     */
    public static String getMetaData(Context context, String name)
    {
        String metaData = "";
        try
        {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            return ai.metaData.get(name).toString();
        }
        catch (Exception e)
        {
            LogUtil.w(TAG, "catch Exception throw by getChannelNo.", e);
        }
        return metaData;
    }

    /**
     * 获取渠道号
     * 
     * @author putao_ffh
     * @since 2015-12-31
     * @param context
     * @return
     */
    public static String getChannelNo(Context context)
    {
        return DEFAULT_CHANNEL;
    }


    /**
     * 获取ptDeviceId作为用户唯一标识 （MD5(imei+mac+androidId)）
     * @author hyl
     * @since 2015-6-12
     */
    public static String getPutaoDeviceId(Context context)
    {
        if (!TextUtils.isEmpty(sPtDeviceId))
        {
            return sPtDeviceId;
        }
        // 从SD卡中获取ptcid
        String ptDeviceId = getPtDeviceIdBySDCrad();
        sPtDeviceId = ptDeviceId;
        if (TextUtils.isEmpty(ptDeviceId))
        {
            /*
             * 
             * 多个线程访问时出现数据不同步的问题 add by lxh 2015-7-2 start old code: ptDeviceId
             * = MD5.toMD5(imeiMacAndroidId);
             * savePtDeviceIdToSDCard(ptDeviceId);
             */
            String androidId = android.provider.Settings.Secure.getString(context.getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);
            // imei＋mac+androidId
            String imeiMacAndroidId = getImei(context) + getLocalMacAddress(context) + androidId;
            synchronized (SystemUtil.class)
            {
                ptDeviceId = getPtDeviceIdBySDCrad();
                if (TextUtils.isEmpty(ptDeviceId))
                {
                    ptDeviceId = MD5.toMD5(imeiMacAndroidId);
                    sPtDeviceId = ptDeviceId;
                    savePtDeviceIdToSDCard(ptDeviceId);
                }
            }
            // end 2015-7-2 by lxh
        }
        return ptDeviceId;
    }

    /**
     * 获取mac地址
     * @author hyl
     * @since 2015-6-12
     */
    public static String getLocalMacAddress(Context context)
    {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String mac = info.getMacAddress();
        if (null == mac)
        { // add by cj 2015/04/07
            mac = "";
        }
        return mac;
    }

    /**
     * 保存ptDeviceId到SDCard
     * @author hyl
     * @since 2015-6-12
     * @param ptDeviceId
     */
    private static void savePtDeviceIdToSDCard(String ptDeviceId)
    {
        File fileDir = new File(Environment.getExternalStorageDirectory() + "/pt_system");
        if (!fileDir.exists())
            fileDir.mkdirs();
        File cidFile = new File(fileDir, PT_DEVICE_ID_FILENAME);
        if (!cidFile.exists())
        {
            try
            {
                cidFile.createNewFile();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        FileOutputStream foos = null;
        try
        {
            foos = new FileOutputStream(cidFile, false);
            foos.write(ptDeviceId.toString().getBytes());
            foos.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (foos != null)
            {
                try
                {
                    foos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                foos = null;
            }
        }
    }

    /**
     * 从SD卡中读取ptDeviceId
     * @author hyl
     * @since 2015-6-12
     * @return
     */
    private static String getPtDeviceIdBySDCrad()
    {
        File fileDir = null;
        try
        {
            fileDir = new File(Environment.getExternalStorageDirectory(), "/pt_system");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        File cidFile = new File(fileDir, PT_DEVICE_ID_FILENAME);
        InputStreamReader inputReader = null;
        BufferedReader bufferReader = null;
        StringBuffer strBuffer = new StringBuffer();
        try
        {
            InputStream inputStream = new FileInputStream(cidFile);
            inputReader = new InputStreamReader(inputStream);
            bufferReader = new BufferedReader(inputReader);
            String line = null;
            while ((line = bufferReader.readLine()) != null)
            {
                strBuffer.append(line);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (inputReader != null)
                {
                    inputReader.close();
                }
                if (bufferReader != null)
                {
                    bufferReader.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            inputReader = null;
            bufferReader = null;
        }
        return strBuffer.toString();
    }
}