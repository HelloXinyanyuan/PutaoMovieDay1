package com.whunf.putaomovieday1.common.util;

import com.alibaba.fastjson.JSONObject;
import com.whunf.putaomovieday1.common.storage.PreferenceUtil;
import com.whunf.putaomovieday1.common.util.location.LocationPostion;
import com.whunf.putaomovieday1.module.user.entity.UserInfo;

/**
 * Created by Administrator on 2016/7/6.
 */
public class UserInfoUtil {


    private static UserInfoUtil ourInstance = new UserInfoUtil();

    public static UserInfoUtil getInstance() {
        return ourInstance;
    }

    private UserInfoUtil() {
    }


    /**
     * 获得上一次（最近一次）用户定位的位置信息
     *
     * @return
     */
    public LocationPostion getLastPos() {
        LocationPostion locationPostion = null;
        try {
            String jsonStr = PreferenceUtil.loadString(PreferenceUtil.KEY_USER_POS, "");
            locationPostion = JSONObject.parseObject(jsonStr, LocationPostion.class);
        } catch (Exception e) {
        }
        return locationPostion;
    }

    /**
     * 为用户保存位置信息
     *
     * @param locationPostion
     */
    public void savePos(LocationPostion locationPostion) {
        //将用户定位到的位置信息保存起来
        String locationJsonStr = JSONObject.toJSONString(locationPostion);
        PreferenceUtil.save(PreferenceUtil.KEY_USER_POS, locationJsonStr);
    }

    /**
     * 保存用户登录账号信息
     *
     * @param userInfo
     */
    public void saveUserInfo(UserInfo userInfo) {
        //将用户定位到的位置信息保存起来
        String locationJsonStr = JSONObject.toJSONString(userInfo);
        PreferenceUtil.save(PreferenceUtil.KEY_USER_LOGIN_INFO, locationJsonStr);
    }

    /**
     * 获取登录的账号信息
     * @return
     */
    public UserInfo loadUserInfo() {
        UserInfo userInfo = null;
        try {
            String userInfoJson = PreferenceUtil.loadString(PreferenceUtil.KEY_USER_LOGIN_INFO, "");
            userInfo = JSONObject.parseObject(userInfoJson, UserInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }


    /**
     * 清除用户保存的位置信息
     */
    public void clearPos() {
    }

}
