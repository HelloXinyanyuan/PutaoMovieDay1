package com.whunf.putaomovieday1.common.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2016/6/22.
 */
public class BitmapCache implements ImageLoader.ImageCache {
    LruCache<String, Bitmap> mCache;

    public BitmapCache() {
        mCache = new LruCache<String, Bitmap>(1022 * 1024 * 8) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };

    }


    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }
}
