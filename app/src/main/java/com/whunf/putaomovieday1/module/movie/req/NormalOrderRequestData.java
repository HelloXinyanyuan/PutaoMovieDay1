package com.whunf.putaomovieday1.module.movie.req;

import com.whunf.putaomovieday1.common.core.AppConfig;
import com.whunf.putaomovieday1.module.user.util.MD5;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 用于订单业务的通用请求数据封装,只需要传构造函数里所包含的参数就行,new出来直接用
 * 
 * @author zj 2014-12-29 17:28:18
 */
public class NormalOrderRequestData extends BaseRequestData
{

    private String orderNo = "";

    private int priceInCents = 0;

    private int productId;

    private int productType;

    private Map<String, String> subObjMap;

    private Object subObj;

    private Class subObjClass;

    /**
     *
     * @param orderNo 订单号
     * @param priceInCents 订单价格,单位分
     * @param productId 产品Id
     * @param productType 产品类型
     * @param subObj 业务扩展数据bean
     * @param subObjClass 业务扩展数据bean的class
     */
    public NormalOrderRequestData(String orderNo, int priceInCents, int productId, int productType, Object subObj,
                                  Class subObjClass)
    {
        this.orderNo = orderNo;
        this.priceInCents = priceInCents;
        this.productId = productId;
        this.productType = productType;
        this.subObj = subObj;
        this.subObjClass = subObjClass;

        if (subObj != null && subObjClass != null)
        {
            addSubObjToMap(subObj, subObjClass);
        }
    }

    /**
     *
     * @param orderNo 订单号
     * @param priceInCents 订单价格,单位分
     * @param productId 产品Id
     * @param productType 产品类型
     * @param subObjMap 业务扩展数据的map
     */
    public NormalOrderRequestData(String orderNo, int priceInCents, int productId, int productType,
                                  Map<String, String> subObjMap)
    {
        this.orderNo = orderNo;
        this.priceInCents = priceInCents;
        this.productId = productId;
        this.productType = productType;

        if (null != subObjMap)
        {
            addSubObjToMap(subObjMap);
        }
    }

    @Override
    protected void setParams(Map<String, String> params)
    {
        setParam("order_no", orderNo);
        setParam("product_type", String.valueOf(productType));
        setParam("product_id", String.valueOf(productId));
        setParam("pay_price", String.valueOf(priceInCents));

    }

    private void addSubObjToMap(Map<String, String> subObjMap)
    {
        if (subObjMap.isEmpty())
        {
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> entry : subObjMap.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append("sub_obj[\"");
            sb.append(key);
            sb.append("\"]");

            setParam(sb.toString(), value);
        }

    }

    private void addSubObjToMap(Object bean, Class clazz)
    {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        for (Field field : fields)
        {
            try
            {
                field.setAccessible(true);
                if (field.get(bean) != null)
                {
                    sb.delete(0, sb.length());
                    sb.append("sub_obj[\"");
                    sb.append(field.getName());
                    sb.append("\"]");

                    setParam(sb.toString(), field.get(bean).toString());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getLocalSign()
    {
        return MD5.toMD5(getTimestamp() + getDev_no() + String.valueOf(priceInCents) + AppConfig.REQUEST_SECRITY);
    }

}
