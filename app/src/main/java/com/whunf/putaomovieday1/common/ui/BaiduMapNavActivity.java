package com.whunf.putaomovieday1.common.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.util.UserInfoUtil;
import com.whunf.putaomovieday1.common.util.location.LocationPostion;
import com.whunf.putaomovieday1.module.movie.util.CinemaConstants;

/**
 * 使用百度地图内嵌导航功能
 */
public class BaiduMapNavActivity extends AppCompatActivity implements OnGetRoutePlanResultListener {
    //纬度
    private double mCinemaLat;
    //经度
    private double mCinemaLng;
    private MapView mNavMap;
    private BaiduMap mBaiduMap;

    // 搜索相关
    private RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使用


    private boolean useDefaultIcon = true;//使用默认icon

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_map);
        Intent intent = getIntent();
        mCinemaLat = intent.getDoubleExtra(CinemaConstants.EXTRA_CINEMA_LAT, 0.0);
        mCinemaLng = intent.getDoubleExtra(CinemaConstants.EXTRA_CINEMA_LNG, 0.0);

        LocationPostion lp = UserInfoUtil.getInstance().getLastPos();
        LatLng userPos = new LatLng(lp.getLatitude(), lp.getLongitude());
        LatLng cinemaPos = new LatLng(mCinemaLat, mCinemaLng);
        // 地图初始化
        mNavMap = (MapView) findViewById(R.id.baidumap_nav);
        mBaiduMap = mNavMap.getMap();

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //设置地图中心点位置
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(100)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(userPos.latitude)
                .longitude(userPos.longitude).build();
        mBaiduMap.setMyLocationData(locData);

        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(userPos).zoom(16.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));


        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);


        // 设置起终点信息，对于tranist search 来说，城市名无意义
//        PlanNode stNode = PlanNode.withCityNameAndPlaceName("北京", editSt.getText().toString());
//        PlanNode enNode = PlanNode.withCityNameAndPlaceName("北京", editEn.getText().toString());

        PlanNode stNode = PlanNode.withLocation(userPos);
        PlanNode enNode = PlanNode.withLocation(cinemaPos);
        //步行路线搜索
        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(stNode).to(enNode));
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {

        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(BaiduMapNavActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
//            nodeIndex = -1;
//            route = result.getRouteLines().get(0);
            WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
//            routeOverlay = overlay;
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }


    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }
    }


}
