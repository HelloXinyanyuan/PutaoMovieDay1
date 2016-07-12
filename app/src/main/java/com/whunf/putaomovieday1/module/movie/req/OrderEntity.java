package com.whunf.putaomovieday1.module.movie.req;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.whunf.putaomovieday1.common.core.AppConfig;
import com.whunf.putaomovieday1.common.core.PMApplication;
import com.whunf.putaomovieday1.module.user.util.MD5;
import com.whunf.putaomovieday1.module.user.util.SystemUtil;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


/**
 * 通用创建Order用参数
 * 
 * @author zj
 */
public class OrderEntity implements Parcelable
{

    private String devNo = "";

    private String timestamp = "";

    private String localSign = "";

    private String orderNo = "";

    private String channelNo = SystemUtil.getChannelNo(null);

    private List<Long> couponIds;

    /**
     * 支付价格, 单位为分
     */
    private int priceInCents = 0;

    private int productId;

    private int productType;

    private Map<String, String> subObjMap;

    private Map<String, String> uiMap = new HashMap<String, String>();

    @SuppressWarnings("unchecked")
    private OrderEntity(Parcel parcel)
    {
        devNo = parcel.readString();
        timestamp = parcel.readString();
        localSign = parcel.readString();
        orderNo = parcel.readString();
        channelNo = parcel.readString();
        couponIds = parcel.readArrayList(getClass().getClassLoader());
        productId = parcel.readInt();
        productType = parcel.readInt();
        subObjMap = parcel.readHashMap(getClass().getClassLoader());
        uiMap = parcel.readHashMap(getClass().getClassLoader());
    }

    @SuppressLint("UseSparseArrays")
    public OrderEntity()
    {
        couponIds = new ArrayList<Long>(1);
        subObjMap = new HashMap<String, String>();
    }

    public String getDevNo()
    {
        if ("".equals(devNo))
        {
            devNo = SystemUtil.getDeviceId(PMApplication.getInstance());
        }
        return devNo;
    }

    public int getPriceInCents()
    {
        return priceInCents;
    }

    public OrderEntity setPriceInCents(int priceInCents)
    {
        this.priceInCents = priceInCents;
        return this;
    }

    public OrderEntity setDevNo(String devNo)
    {
        this.devNo = devNo;
        return this;
    }

    public void setSubObjMap(Map<String, String> map)
    {
        subObjMap = map;
    }

    public String getTimestamp()
    {
        if ("".equals(timestamp))
        {
            timestamp = String.valueOf(System.currentTimeMillis());
        }
        return timestamp;
    }

    public OrderEntity setTimestamp(String stamp)
    {
        timestamp = stamp;
        return this;
    }

    public String getLocalSign()
    {
        if ("".equals(localSign))
        {
            localSign = generateLocalSign();
        }
        return localSign;
    }

    public OrderEntity setLocalSign(String localSign)
    {
        this.localSign = localSign;
        return this;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public OrderEntity setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
        return this;
    }

    public String getChannelNo()
    {
        return channelNo;
    }

    public int getProductType()
    {
        return productType;
    }

    public OrderEntity setProductType(int productTye)
    {
        this.productType = productTye;
        return this;
    }

    public OrderEntity addCounponId(long id)
    {
        couponIds.add(Long.valueOf(id));
        return this;
    }

    public OrderEntity putSubObj(String key, String value)
    {
        subObjMap.put(key, value);
        return this;
    }

    public int getProductId()
    {
        return productId;
    }

    public OrderEntity setProductId(int productId)
    {
        this.productId = productId;
        return this;
    }

    public String getSubObj(String key)
    {
        return subObjMap.get(key);
    }

    protected String generateLocalSign()
    {
        return MD5.toMD5(getTimestamp() + getDevNo() + String.valueOf(priceInCents) + AppConfig.REQUEST_SECRITY);
    }

    public HashMap<String, String> toResultUI()
    {
        HashMap<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put("dev_no", devNo);
        jsonMap.put("timestamp", timestamp);
        jsonMap.put("local_sign", localSign);
        jsonMap.put("order_no", orderNo);
        jsonMap.put("channel_no", channelNo);
        jsonMap.put("product_type", String.valueOf(productType));
        jsonMap.put("product_id", String.valueOf(productId));
        jsonMap.put("coupon_ids", concactCouponId());
        jsonMap.putAll(subObjMap);
        jsonMap.putAll(uiMap);
        return jsonMap;
    }

    public String toQueryString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("dev_no=");
        sb.append(getDevNo());
        sb.append("&timestamp=");
        sb.append(getTimestamp());
        sb.append("&local_sign=");
        sb.append(getLocalSign());
        sb.append("&order_no=");
        sb.append(orderNo);
        sb.append("&channel_no=");
        sb.append(getChannelNo());
        String couponStr = concactCouponId();
        if (null != couponStr)
        {
            sb.append("&coupon_ids=");
            sb.append(couponStr);
        }
        sb.append("&product_type=");
        sb.append(productType);
        sb.append("&product_id=");
        sb.append(productId);
        sb.append("&pay_price=");
        sb.append(String.valueOf(priceInCents));

        String subObStr = concactSubObjMap();
        if (null != subObStr)
        {
            sb.append(subObStr);
        }
        return sb.toString();
    }

    private String concactCouponId()
    {
        if (couponIds.size() == 0)
        {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0, max = couponIds.size(); i < max; i++)
        {
            sb.append(couponIds.get(i));
            if (i < (max - 1))
            {
                sb.append(',');
            }
        }

        return sb.toString();
    }

    public OrderEntity putUIPair(String key, String value)
    {
        uiMap.put(key, value);
        return this;
    }

    public Map<String, String> getUIMap()
    {
        return uiMap;
    }

    private String concactSubObjMap()
    {
        if (subObjMap.isEmpty())
        {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append('&');
        int counter = 0, max = subObjMap.size();
        for (Entry<String, String> entry : subObjMap.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();
            try
            {
                value=URLEncoder.encode(value, "UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
            }

            sb.append("sub_obj[\"");
            sb.append(key);
            sb.append("\"]=");
            sb.append(value);
            if (counter++ < (max - 1))
            {
                sb.append('&');
            }
        }

        return sb.toString();
    }

    public Map<String, String> getEncodedUIMap()
    {
        HashMap<String, String> result = new HashMap<String, String>(uiMap.size());
        for (Entry<String, String> entry : uiMap.entrySet())
        {
            try
            {
                result.put(entry.getKey(), URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            catch (UnsupportedEncodingException e)
            {
            }
        }
        return result;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(devNo);
        dest.writeString(timestamp);
        dest.writeString(localSign);
        dest.writeString(orderNo);
        dest.writeString(channelNo);
        dest.writeList(couponIds);
        dest.writeInt(productId);
        dest.writeInt(productType);
        dest.writeMap(subObjMap);
        dest.writeMap(uiMap);
    }

    public static final Creator<OrderEntity> CREATOR = new Creator<OrderEntity>()
    {
        @Override
        public OrderEntity[] newArray(int size)
        {
            return new OrderEntity[size];
        }

        @Override
        public OrderEntity createFromParcel(Parcel source)
        {
            return new OrderEntity(source);
        }
    };

    public static Map<String, String> convertToMap(Object bean, Class clazz)
    {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, String> map = new TreeMap<String, String>();
        for (Field field : fields)
        {
            try
            {
                field.setAccessible(true);
                if (field.get(bean) != null)
                {
                    map.put(field.getName(), field.get(bean).toString());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return map;
    }

}
