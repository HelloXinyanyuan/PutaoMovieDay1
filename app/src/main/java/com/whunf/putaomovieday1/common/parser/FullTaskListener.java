package com.whunf.putaomovieday1.common.parser;

import android.app.Activity;

import com.whunf.putaomovieday1.common.core.BaseActivity;

/**
 * Created by Administrator on 2016/7/5.
 * 全任务状态监听实现类
 */
public class FullTaskListener<T> implements TaskStatusListener<T> {
   private Activity mActivity;

    public FullTaskListener(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onTaskStart() {
        if (mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).showLoadingDialog();
        }
    }

    @Override
    public void onTaskFinish() {
        if (mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).dismissLoadingDialog();
        }
    }

    @Override
    public void onTaskSuccess(T response) {

    }

    @Override
    public void onTaskFailure(Exception e) {

    }
}
