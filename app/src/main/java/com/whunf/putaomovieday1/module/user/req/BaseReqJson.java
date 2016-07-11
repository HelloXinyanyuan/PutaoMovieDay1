package com.whunf.putaomovieday1.module.user.req;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.whunf.putaomovieday1.common.core.AppConfig;
import com.whunf.putaomovieday1.common.core.PMApplication;
import com.whunf.putaomovieday1.module.user.util.MD5;
import com.whunf.putaomovieday1.module.user.util.SystemUtil;

public abstract class BaseReqJson
{

    public String pt_token;// add by hyl 2014-9-20

    public String action_code;// [string][not null][指令码]

    public String token;// [string][null able][用户令牌,云端生成]

    public UaInfo ua;// [UaInfo][null able][Ua系统]

    public long timestamp;// [long][not null][时间戳：毫秒数]

    public VersionInfo version;// [VersionInfo][not null][版本结构]

    public String secret_key;// [string][not null][交互密钥]

    public int active_status;// [int][not null][0:后台运行,1:主程序]

    public String channel_no = "";// :[String][not null][渠道号,无渠道号为PUTAO]

    public String app_id = "10"; // 标识appId[葡萄生活 10, 测试103]

    public String device_code; // [String][not null][手机设备唯一标识]

    public int local_dual_version;// [int][not null][本地保存双卡信息版本，默认为0]

    public BaseReqJson(String actionCode)
    {
        Context context = PMApplication.getInstance();
        pt_token = AppConfig.TOKEN;//登录后的token
        action_code = actionCode;
        ua = new UaInfo();
        timestamp = System.currentTimeMillis();
        version = new VersionInfo(SystemUtil.getAppVersion(context), "", "");
        secret_key = MD5.toMD5(actionCode + SystemUtil.getAppVersion(context) + timestamp + PT_KEY);
        active_status = 1; // 当前程序状态: 1:前台 0:后台
        channel_no = SystemUtil.getChannelNo(context);

//        device_code = SystemUtil.getPutaoDeviceId(context);// 采用getPutaoDeviceId
                                                           // 替换 getDeviceId方法

        app_id = SystemUtil.getAppid(context);
    }


    public static final String PT_KEY ="233&*Adc^%$$per" ;


    @JSONField(serialize = false)
    public String getJsonString(){//fastjson死循环问题
        String jsonStr= JSONObject.toJSONString(this);
        return jsonStr;
    }


    public String convertToJsonString(){
        String jsonStr= JSONObject.toJSONString(this);
        return jsonStr;
    }


    //getter and setter 方法
    public String getPt_token() {
        return pt_token;
    }

    public void setPt_token(String pt_token) {
        this.pt_token = pt_token;
    }

    public String getAction_code() {
        return action_code;
    }

    public void setAction_code(String action_code) {
        this.action_code = action_code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UaInfo getUa() {
        return ua;
    }

    public void setUa(UaInfo ua) {
        this.ua = ua;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public VersionInfo getVersion() {
        return version;
    }

    public void setVersion(VersionInfo version) {
        this.version = version;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public int getActive_status() {
        return active_status;
    }

    public void setActive_status(int active_status) {
        this.active_status = active_status;
    }

    public String getChannel_no() {
        return channel_no;
    }

    public void setChannel_no(String channel_no) {
        this.channel_no = channel_no;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public int getLocal_dual_version() {
        return local_dual_version;
    }

    public void setLocal_dual_version(int local_dual_version) {
        this.local_dual_version = local_dual_version;
    }
}
