package com.whunf.putaomovieday1.module.user.resp;

import com.whunf.putaomovieday1.module.movie.resp.BaseResp;

/**
 * Created by xiaohui on 16/7/10.
 * 获取短信验证码返回
 */
public class GetSmscodeResp extends BaseResp {
//    {
//        "ret_code": "0000",
//            "error_remark": "",
//            "next_code": [],
//        "active_m_s": 1800000,
//            "current_action_code": null,
//            "enforce": null,
//            "user_id": null,
//            "send_num": "106900485185",
//            "timeout": 60000
//    }

    private String send_num;
    private int timeout;

    public String getSend_num() {
        return send_num;
    }

    public void setSend_num(String send_num) {
        this.send_num = send_num;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "GetSmscodeResp{" +
                "send_num='" + send_num + '\'' +
                ", timeout=" + timeout +
                '}';
    }
}