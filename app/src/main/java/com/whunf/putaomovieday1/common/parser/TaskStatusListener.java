package com.whunf.putaomovieday1.common.parser;

/**
 * Created by Administrator on 2016/7/5.
 * 任务监听接口类
 */
public interface TaskStatusListener<T> {

    void onTaskStart();//任务开启时

    void onTaskFinish();//任务结束时

    void onTaskSuccess(T t);//任务成功

    void onTaskFailure(Exception e);//任务失败
}
