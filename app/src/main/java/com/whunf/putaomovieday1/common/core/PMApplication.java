package com.whunf.putaomovieday1.common.core;

import android.app.Application;
import android.content.Intent;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.SDKInitializer;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/6/21.
 */
public class PMApplication extends Application {
    /**
     * VolleyHttp请求队列
     */
    private RequestQueue requestQueue;
    private static PMApplication sIntance;

    /**
     * 获得当前运行时PMApplication实例对象
     * @return
     */
    public static PMApplication getInstance() {
        return sIntance;
    }

    MyCrashHandler crashHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        sIntance = this;
        requestQueue = Volley.newRequestQueue(this);
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        startService(new Intent(this,AppInitIntentService.class));
        //初始化自定义crash handler
        crashHandler= new MyCrashHandler();
        //初始化极光sdk
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }

    /**
     * 获得Volley的Http请求队列
     * @return
     */
    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
