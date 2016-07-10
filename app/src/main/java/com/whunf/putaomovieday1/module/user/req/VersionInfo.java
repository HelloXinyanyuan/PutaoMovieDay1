package com.whunf.putaomovieday1.module.user.req;

public class VersionInfo
{
    public String app_version;// [string][not null][应用程序版本]

    public String mobile_no_version;// [string][not null][号段版本]

    public String public_sns_version;// [String][not
                                     // null][公用账号版本,若客户端当前没有存储公用账号，则提交”null”]

    public VersionInfo(String app_version, String mobile_no_version, String public_sns_version)
    {
        this.app_version = app_version;
        this.mobile_no_version = mobile_no_version;
        this.public_sns_version = public_sns_version;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getMobile_no_version() {
        return mobile_no_version;
    }

    public void setMobile_no_version(String mobile_no_version) {
        this.mobile_no_version = mobile_no_version;
    }

    public String getPublic_sns_version() {
        return public_sns_version;
    }

    public void setPublic_sns_version(String public_sns_version) {
        this.public_sns_version = public_sns_version;
    }
}
