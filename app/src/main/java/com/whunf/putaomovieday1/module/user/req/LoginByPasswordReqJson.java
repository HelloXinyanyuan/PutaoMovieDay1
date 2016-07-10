package com.whunf.putaomovieday1.module.user.req;

/**
 * 密码登录
 * 
 * @author zj
 * @since 2015-5-14
 */
public class LoginByPasswordReqJson extends BaseReqJson
{
    public String mobile;

    public String password;

    /**
     * [帐户来源] - 0 表使用设备创建, 1 表使用手机创建, 2 表使用酷派创建
     */
    public int accSource;

    /**
     * [帐户类型] - 0 表临时用户, 1 表普通绑定用户, 2 表平台绑定用户
     */
    public int accType;

    public String accName;

//    public String black_box;

    public LoginByPasswordReqJson(String mobile, String password)
    {
        super("200003");
        this.mobile = mobile;
        this.password = password;
        this.accName = mobile;
        this.accSource = 1;
        this.accType = 1;

//        black_box = FMAgent.onEvent();// 同盾风险决策获取客户端的指纹信息
    }

}
