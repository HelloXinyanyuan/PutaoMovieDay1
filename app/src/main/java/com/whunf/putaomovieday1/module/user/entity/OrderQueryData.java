package com.whunf.putaomovieday1.module.user.entity;

import com.whunf.putaomovieday1.common.storage.db.entity.PTOrderBean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public class OrderQueryData {

   private List<PTOrderBean> result;

    public List<PTOrderBean> getResult() {
        return result;
    }

    public void setResult(List<PTOrderBean> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "OrderQueryData{" +
                "result=" + result +
                '}';
    }
}
