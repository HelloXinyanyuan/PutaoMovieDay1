package com.whunf.putaomovieday1.common.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaohui on 16/7/7.
 * 请求的准备工作
 */
public class ReqPrepare<T> {
    //转字节码类
    public Class<T> clazz;
    //请求路径
    public String reqPath;
    //请求参数
    public Map<String, String> reqParams = new HashMap<>();
    //请求头
    public Map<String, String> reqHeaders = new HashMap<>();
    //请求方式（默认get方式）
    public CommParserTask.RequstMethod requstMethod = CommParserTask.RequstMethod.GET;
    //内容格式(默认是Form表单方式提交参数)
    public CommParserTask.ContentType contentType = CommParserTask.ContentType.FORM;
    //内容
    public String content;
    //任务状态的监听
    public TaskStatusListener<T> listener;

    public ReqPrepare(Class<T> clazz, String reqPath) {
        this.clazz = clazz;
        this.reqPath = reqPath;
    }


}
