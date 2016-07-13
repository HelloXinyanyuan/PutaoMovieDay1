package com.whunf.putaomovieday1.common.core;

import android.app.IntentService;
import android.content.Intent;

import com.whunf.putaomovieday1.common.storage.db.CityTableHand;

/**
 * 初始化的服务
 */
public class AppInitIntentService extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public AppInitIntentService(String name) {
        super(name);
    }

    public AppInitIntentService() {
        this("AppInitIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //初始化城市数据
        new CityTableHand(this).init();


    }

}
