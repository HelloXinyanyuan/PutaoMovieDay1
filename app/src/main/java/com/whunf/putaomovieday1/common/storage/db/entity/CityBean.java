package com.whunf.putaomovieday1.common.storage.db.entity;

import java.io.Serializable;

/**
 * 外界访问的实体类
 */
public class CityBean implements Serializable {

    private String cityName;

    private String cityPy;//全拼

    private String citySpy;//简拼

    private int selfId;

    private int parentId;

    private int cityType;

    private String districtCode;

    private int cityHot = 0;


    public CityBean(String cityName, String cityPy, String citySpy, int selfId, int parentId, int cityType, String districtCode, int cityHot) {
        this.cityName = cityName;
        this.cityPy = cityPy;
        this.citySpy = citySpy;
        this.selfId = selfId;
        this.parentId = parentId;
        this.cityType = cityType;
        this.districtCode = districtCode;
        this.cityHot = cityHot;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityPy() {
        return cityPy;
    }

    public void setCityPy(String cityPy) {
        this.cityPy = cityPy;
    }

    public String getCitySpy() {
        return citySpy;
    }

    public void setCitySpy(String citySpy) {
        this.citySpy = citySpy;
    }

    public int getSelfId() {
        return selfId;
    }

    public void setSelfId(int selfId) {
        this.selfId = selfId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getCityType() {
        return cityType;
    }

    public void setCityType(int cityType) {
        this.cityType = cityType;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public int getCityHot() {
        return cityHot;
    }

    public void setCityHot(int cityHot) {
        this.cityHot = cityHot;
    }
}