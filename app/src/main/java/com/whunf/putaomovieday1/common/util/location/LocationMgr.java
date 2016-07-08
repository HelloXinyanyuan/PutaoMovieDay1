package com.whunf.putaomovieday1.common.util.location;

import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.whunf.putaomovieday1.common.core.PMApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/30.
 * <p/>
 * 管理定位相关操作
 */
public class LocationMgr {
    private static final String TAG = "LocationMgr";
    private static LocationMgr ourInstance = new LocationMgr();
    List<MovieLocationListener> mListeners = new ArrayList<>();
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();


    GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.d(TAG, "onReceiveLocation() called with: " + "location = [" + location + "]");
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }

            if (TextUtils.isEmpty(location.getCity())) {
                LatLng ptCenter = new LatLng(location.getLatitude(), location.getLongitude());
                // 反Geo搜索
                mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(ptCenter));
            } else {
                LocationPostion lp = new LocationPostion();
                lp.setAddress(location.getAddrStr());
                lp.setCity(location.getCity());
                lp.setLatitude(location.getLatitude());
                lp.setLongitude(location.getLongitude());
                dispatchLocationInfo(lp);
            }

            destroy();//定位完后销毁

        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }


    private void dispatchLocationInfo(LocationPostion lp) {
        //去掉“市”“县”
        String city = lp.getCity();
        if (!TextUtils.isEmpty(city)) {
            if (city.endsWith("市") || city.endsWith("县")) {
                lp.setCity(city.substring(0, city.length() - 1));
            }
        }

        //分发给每一个注册者
        for (MovieLocationListener listener : mListeners) {
            listener.onLocationSuccess(lp);
        }
    }

    /**
     * 这个是GEO的Listener，拿到GEO和反GEO的结果
     */
    private OnGetGeoCoderResultListener mOnGetGeoCoderResultListener = new OnGetGeoCoderResultListener() {
        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
            //分发给每一个注册者
            for (MovieLocationListener listener : mListeners) {
                LocationPostion lp = new LocationPostion();
                lp.setAddress(reverseGeoCodeResult.getAddress());
                lp.setCity(reverseGeoCodeResult.getAddressDetail().city);
                lp.setLatitude(reverseGeoCodeResult.getLocation().latitude);
                lp.setLongitude(reverseGeoCodeResult.getLocation().longitude);
                dispatchLocationInfo(lp);
            }

        }
    };

    public static LocationMgr getInstance() {
        return ourInstance;
    }

    private LocationMgr() {
    }

    /**
     * 执行定位
     */
    public void startLocation() {
        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(mOnGetGeoCoderResultListener);

        Log.d(TAG, "startLocation() called with: " + "");
        // 定位初始化
        mLocClient = new LocationClient(PMApplication.getInstance());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedLocationDescribe(true);//需要详细描述
        option.setIsNeedAddress(true);//需要地址
        mLocClient.setLocOption(option);
        mLocClient.start();//发起百度定位
    }

    public void destroy() {
        // 退出时销毁定位
        mLocClient.stop();
    }

    public void addListener(MovieLocationListener listener) {
        mListeners.add(listener);
    }

    public void removeListener(MovieLocationListener listener) {
        mListeners.remove(listener);
    }

    /**
     * 定位结果的监听接口
     */
    public interface MovieLocationListener {
        void onLocationSuccess(LocationPostion locationPostion);

        void onLocationFailed();

    }
}
