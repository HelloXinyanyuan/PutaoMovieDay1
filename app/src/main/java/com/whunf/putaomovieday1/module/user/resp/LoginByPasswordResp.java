package com.whunf.putaomovieday1.module.user.resp;

import com.whunf.putaomovieday1.module.movie.resp.BaseResp;

/**
 * Created by xiaohui on 16/7/10.
 */
public class LoginByPasswordResp extends BaseResp {

//    {
//        "ret_code": "0000",//代表登录成功
//            "error_remark": "",
//            "next_code": [],
//        "active_m_s": 1800000,
//            "current_action_code": null,
//            "enforce": null,
//            "user_id": null,
//            "registerStatus": 0,
//            "login_code": "0000",
//            "code_mean": "login_success",
//            "pt_uid": "pt1440129543880-13318439",
//            "pt_token": "23bc5ad532694652b504eee425c4904f",
//            "open_token": "9bc4640e18ff41e7bda0a6e0980cdd42",
//            "relateUsers": [{
//        "accName": "13116558641",
//                "accSource": 1,
//                "accType": 1,
//                "accMsg": ""
//    }]

    private String pt_token;
    private String accName;

    public String getPt_token() {
        return pt_token;
    }

    public void setPt_token(String pt_token) {
        this.pt_token = pt_token;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }



}
