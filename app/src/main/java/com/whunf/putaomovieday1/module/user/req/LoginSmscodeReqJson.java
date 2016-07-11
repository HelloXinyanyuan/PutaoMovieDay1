package com.whunf.putaomovieday1.module.user.req;

/**
 * Created by gwq on 2015/4/14.
 * 
 * 检测验证码是否正确request
 */
public class LoginSmscodeReqJson extends BaseReqJson
{
    public String check_code;
    /**
     * [帐户来源] - 0 表使用设备创建, 1 表使用手机创建, 2 表使用酷派创建
     */
    public int accSource;

    /**
     * [帐户类型] - 0 表临时用户, 1 表普通绑定用户, 2 表平台绑定用户
     */
    public int accType;

    public String accName;

    public String accMsg;

//    public String black_box;

    public LoginSmscodeReqJson(String check_code, String accName)
    {
        super("200002");
        this.check_code = check_code;
        this.accSource = 1;
        this.accType = 1;
        this.accName = accName;
        this.accMsg = "";
//        black_box = FMAgent.onEvent();// 同盾风险决策获取客户端的指纹信息
    }
}
