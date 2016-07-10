package com.whunf.putaomovieday1.module.user.req;


import com.whunf.putaomovieday1.module.user.util.SystemUtil;

public class UaInfo
{
    public String system_name;// [not null][系统名字android/ios]

    public String system_version;// [not null][当前系统版本]

    public String band;// [not null][手机型号]

    public UaInfo()
    {
        system_name = "android";
        system_version = SystemUtil.getOS();
        band = SystemUtil.getMachine();
    }

    public String getSystem_name() {
        return system_name;
    }

    public void setSystem_name(String system_name) {
        this.system_name = system_name;
    }

    public String getSystem_version() {
        return system_version;
    }

    public void setSystem_version(String system_version) {
        this.system_version = system_version;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }
}
