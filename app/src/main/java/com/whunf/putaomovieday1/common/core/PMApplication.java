package com.whunf.putaomovieday1.common.core;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

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


    @Override
    public void onCreate() {
        super.onCreate();
        sIntance = this;
        requestQueue = Volley.newRequestQueue(this);
    }

    /**
     * 获得Volley的Http请求队列
     * @return
     */
    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
