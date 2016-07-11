package com.whunf.putaomovieday1.module.user.resp;

import com.whunf.putaomovieday1.module.movie.resp.BaseResp;

/**
 * Created by xiaohui on 16/7/10.
 * 登录返回
 */
public class LoginSmscodeResp extends BaseResp {

//    {
//        "ret_code": "-100010",//返回0000代表登录成功
//            "error_remark": "验证码已经过期，请重新获取验证码",
//            "next_code": [],
//        "active_m_s": 1800000,
//            "current_action_code": null,
//            "enforce": null,
//            "user_id": null,
//            "registerStatus": 0,
//            "login_code": null,
//            "code_mean": null,
//            "pt_uid": null,
//            "pt_token": null,
//            "open_token": null,
//            "relateUsers": null
//    }


//    [
//    {
//        "active_m_s": 1800000,
//            "error_remark": "",
//            "next_code": [],
//        "open_token": "01e7ed14825647afbd1b4816eadc9f59",
//            "pt_token": "b09fdbc357f441ab92b9fb2be6ade58b",
//            "pt_uid": "pt1468205834572-21207225",
//            "registerStatus": 0,
//            "relateUsers": [
//        {
//            "accMsg": "",
//                "accName": "13537720819",
//                "accSource": 1,
//                "accType": 1
//        }
//        ],
//        "ret_code": "0000"
//    }
//    ]


    private String pt_token;

    private RelateUsers[] relateUsers;


    public RelateUsers[] getRelateUsers() {
        return relateUsers;
    }

    public void setRelateUsers(RelateUsers[] relateUsers) {
        this.relateUsers = relateUsers;
    }

    public String getPt_token() {
        return pt_token;
    }

    public void setPt_token(String pt_token) {
        this.pt_token = pt_token;
    }

    public  static class RelateUsers{
        private String accName;

        public String getAccName() {
            return accName;
        }

        public void setAccName(String accName) {
            this.accName = accName;
        }
    }

}
