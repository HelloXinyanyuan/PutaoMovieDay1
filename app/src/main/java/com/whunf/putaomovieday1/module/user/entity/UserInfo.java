package com.whunf.putaomovieday1.module.user.entity;

/**
 * Created by Administrator on 2016/7/11.
 */
public class UserInfo {
    private String pt_token;
    private String accName;
    public UserInfo() {
    }
    public UserInfo(String pt_token, String accName) {
        this.pt_token = pt_token;
        this.accName = accName;
    }

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

    @Override
    public String toString() {
        return "UserInfo{" +
                "pt_token='" + pt_token + '\'' +
                ", accName='" + accName + '\'' +
                '}';
    }
}
