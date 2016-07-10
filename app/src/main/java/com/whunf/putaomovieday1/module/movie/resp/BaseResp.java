package com.whunf.putaomovieday1.module.movie.resp;

/**
 * Created by Administrator on 2016/6/22.
 */
public class BaseResp {

    private String ret_code;
    private String msg;

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseResp{" +
                "ret_code='" + ret_code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
