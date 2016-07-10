package com.whunf.putaomovieday1.module.user.req;

/**
 * Created by xiaohui on 16/7/10.
 * 请求服务器发送验证码到指定手机request
 * 200001登录 200004设置密码
 */
public class GetSmscodeReqJson extends BaseReqJson
{
    public String mobile;

    public GetSmscodeReqJson(String mobile)
    {
        super("200001");
        this.mobile = mobile;
    }
}