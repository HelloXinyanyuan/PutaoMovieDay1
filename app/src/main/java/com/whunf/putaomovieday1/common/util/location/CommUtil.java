package com.whunf.putaomovieday1.common.util.location;

import com.whunf.putaomovieday1.common.util.UserInfoUtil;
import com.whunf.putaomovieday1.module.user.entity.UserInfo;

/**
 * Created by Administrator on 2016/7/12.
 */
public class CommUtil {
    public  static String getCookie(){
        UserInfo userInfo = UserInfoUtil.getInstance().loadUserInfo();
        if (userInfo == null) {
            return null;
        }
        //获得token用于下单
        String pttoken = userInfo.getPt_token();
        String cookie = "app_id=10;channel=wandoujia;version=2.6.0;dev_no=20f10f4175287e8c5b80f6a150a64e70;band=Google Galaxy Nexus - 4.1.1 - API 16 - 720x1280;city=%E6%B7%B1%E5%9C%B3;loc=30.2784662,120.1194347;pt_token=" + pttoken;
        return cookie;
    }
}
