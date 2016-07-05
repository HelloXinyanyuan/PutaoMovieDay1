package com.whunf.putaomovieday1.common.parser;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.whunf.putaomovieday1.common.core.PMApplication;

import java.util.Map;

/**
 * Created by Administrator on 2016/7/5.
 * <p/>
 * 通用的异步任务请求类
 */
public class CommParserTask<T> implements Response.ErrorListener {

    /**
     * 请求方式
     */
    public enum RequstMethod {
        //        DEPRECATED_GET_OR_POST,
        GET,
        POST,
//        PUT,
//        DELETE,
//        HEAD,
//        OPTIONS,
//        TRACE,
//        PATCH
    }

    //请求路径
    private String url;
    //请求的实体
    private Class<T> entity;
    //请求参数
    private Map<String, String> requestParams;
    //请求方法方式
    public RequstMethod method;
    //请求状态监听
    private TaskStatusListener mTaskListener;
    //Volley请求类
    private Request<T> mRequset;


    /**
     * 部分参数构造方法
     *
     * @param url    请求地址
     * @param entity 请求的返回的实体字节码对象
     */
    public CommParserTask(String url, Class<T> entity) {
        this(url, entity, null, RequstMethod.GET);
    }

    /**
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
        int mothod = 0;
        switch (method) {
            case GET:
                mothod = Request.Method.GET;
            case POST:
                mothod = Request.Method.POST;
        }
        return mothod;
    }

    /**
     * 开启异步任务
     */
    public void startAsyncTask() {
        if (mTaskListener != null) {
            mTaskListener.onTaskStart();
        }

        int volleyMethod = getVolleyMethod();
        //创建一个定制的Request对象
        mRequset = new Request<T>(volleyMethod, url, this) {
            @Override
            protected Response<T> parseNetworkResponse(NetworkResponse response) {//子线程中
                String dataOfStr = new String(response.data);//HTTP服务器 string类型的返回数据
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
}
