package com.whunf.putaomovieday1.common.parser;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.whunf.putaomovieday1.common.core.PMApplication;
import com.whunf.putaomovieday1.common.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/5.
 * <p/>
 * 通用的异步任务请求类
 */
public class CommParserTask<T> implements Response.ErrorListener {

    private static final String TAG = "CommParserTask";

    private CommParserTask(ReqPrepare<T> reqPrepare) {
        url = reqPrepare.reqPath;
        entity = reqPrepare.clazz;
        method = reqPrepare.requstMethod;
        requestParams = reqPrepare.reqParams;
    }


    //请求路径
    private String url;
    //请求的实体
    private Class<T> entity;
    //请求参数
    private Map<String, String> requestParams;
    //请求方法方式
    private RequstMethod method;
    //请求状态监听
    private TaskStatusListener mTaskListener;
    //Volley请求类
    private Request<T> mRequset;


    /**
     * @deprecated 用Builder方式创建
     * 部分参数构造方法
     *
     * @param url    请求地址
     * @param entity 请求的返回的实体字节码对象
     */
    public CommParserTask(String url, Class<T> entity) {
        this(url, entity, null, RequstMethod.GET);
    }

    /**
     * @deprecated 用Builder方式创建
     * 具体的参数构造方法
     *
     * @param url           请求路径
     * @param entity        请求的实体字节码对象
     * @param requestParams 请求参数
     * @param method        设置请求方式
     */
    public CommParserTask(String url, Class<T> entity, Map<String, String> requestParams, RequstMethod method) {
        this.url = url;
        this.entity = entity;
        this.requestParams = requestParams;
        this.method = method;
        LogUtil.i(TAG,"requestParams:"+requestParams);

    }

    public TaskStatusListener getTaskListener() {
        return mTaskListener;
    }

    /**
     * 对外提供设置请求结果的回调监听
     *
     * @param taskListener
     */
    public void setTaskListener(TaskStatusListener taskListener) {
        this.mTaskListener = taskListener;
    }

    /**
     * 获得Volley定义的请求方式
     *
     * @return
     */
    private int getVolleyMethod() {
        int volleyMethod = 0;
        switch (method) {
            case GET:
                volleyMethod = Request.Method.GET;
            case POST:
                volleyMethod = Request.Method.POST;
        }
        return volleyMethod;
    }

    /**
     * 开启异步JSON解析任务
     */
    public void asyncParse() {
        if (mTaskListener != null) {
            mTaskListener.onTaskStart();
        }

        LogUtil.i(TAG, "url:" + url);
        int volleyMethod = getVolleyMethod();
        //创建一个定制的Request对象
        mRequset = new Request<T>(volleyMethod, url, this) {
            @Override
            protected Response<T> parseNetworkResponse(NetworkResponse response) {//子线程中
                String dataOfStr = new String(response.data);//HTTP服务器 string类型的返回数据
                LogUtil.i(TAG, "http Content:" + dataOfStr);
                T t = null;
                try {
                    t = JSONObject.parseObject(dataOfStr, entity);//fastjson解析对象
                } catch (Exception e) {
                    return Response.error(new VolleyError("JSON解析异常"));
                }
                return Response.success(t, HttpHeaderParser.parseCacheHeaders(response));//返回成功
            }

            @Override
            protected void deliverResponse(T response) {//主线程
                if (mTaskListener != null) {
                    if (response != null) {
                        mTaskListener.onTaskSuccess(response);
                    } else {
                        mTaskListener.onTaskFailure(new Exception("解析失败"));
                    }

                    if (mTaskListener != null) {//任务结束的回调，无关乎成功与否
                        mTaskListener.onTaskFinish();
                    }
                }
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return requestParams;
            }

        };
        //将request对象添加到请求队列中
        PMApplication.getInstance().getRequestQueue().add(mRequset);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (mTaskListener != null) {
            mTaskListener.onTaskFailure(error);
        }

        if (mTaskListener != null) {//任务结束的回调，无关乎成功与否
            mTaskListener.onTaskFinish();
        }
    }

    /**
     * 取消异步任务
     */
    public void cancel() {
        if (mRequset != null) {
            mRequset.cancel();
        }
    }


    /**
     * 任务创建者类
     *
     * @param <T>
     */
    public static class Builder<T> {
        //请求准备对象
        private ReqPrepare<T> reqPrepare;

        /**
         * 构造一个task的builder对象，两个必须填写的参数
         *
         * @param reqPath
         * @param clazz
         */
        public Builder(String reqPath, Class<T> clazz) {
            reqPrepare = new ReqPrepare<T>(clazz, reqPath);
        }


        /**
         * 放置参数
         *
         * @param key
         * @param value String类型
         * @return
         */
        public Builder<T> putParams(String key, String value) {
            if (reqPrepare.reqParams == null) {
                reqPrepare.reqParams = new HashMap<>();
            }
            reqPrepare.reqParams.put(key, value);
            return this;
        }

        /**
         * 放置参数
         *
         * @param key
         * @param value long类型
         * @return
         */
        public Builder<T> putParams(String key, long value) {
            putParams(key, String.valueOf(value));
            return this;
        }

        /**
         * 放置请求头信息
         *
         * @param key
         * @param value
         * @return
         */
        public Builder<T> putHeader(String key, String value) {
            if (reqPrepare.reqHeaders == null) {
                reqPrepare.reqHeaders = new HashMap<>();
            }
            reqPrepare.reqHeaders.put(key, value);
            return this;
        }

        /**
         * 配置请求方式（默认GET请求）
         *
         * @param rm
         * @return
         */
        public Builder<T> setMethod(RequstMethod rm) {
            reqPrepare.requstMethod = rm;
            return this;
        }

        /**
         * 设置请求内容的类型（默认FORM表单）
         *
         * @param ct
         * @return
         */
        public Builder<T> setContentType(ContentType ct) {
            reqPrepare.contentType = ct;
            return this;
        }

        /**
         * 设置请求内容（写在body中的）
         *
         * @param content
         * @return
         */
        public Builder<T> setContent(String content) {
            reqPrepare.content = content;
            return this;
        }

        /**
         * 设置任务监听器
         * @param listener
         * @return
         */
        public Builder<T> setTaskStatusListener(TaskStatusListener<T> listener) {
            reqPrepare.listener = listener;
            return this;
        }

        /**
         * 创建请求任务
         */
        public CommParserTask<T> createTask() {
            return new CommParserTask<T>(reqPrepare);
        }

    }

    /**
     * Created by xiaohui on 16/7/7.
     * 内容的类型
     */
    public enum ContentType {
        FORM, JSON, XML
    }

    /**
     * 请求方式
     */
    public enum RequstMethod {
        GET,

        POST,
    }
}
