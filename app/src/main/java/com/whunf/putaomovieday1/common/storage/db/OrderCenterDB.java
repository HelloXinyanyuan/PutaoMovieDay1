package com.whunf.putaomovieday1.common.storage.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.whunf.putaomovieday1.common.storage.db.entity.PTOrderBean;
import com.whunf.putaomovieday1.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 消息中心数据库, 统一数据库，各业务不对该数据库进行操作处理
 * 
 * @author putao_lhq
 */
public class OrderCenterDB
{

    private static final String TAG = "OrderCenterDB";

    private PutaoDBProxy db;

    public OrderCenterDB(PutaoDBProxy helper)
    {
        db = helper;
    }

    /**
     * 订单数据表
     * 
     * @author putao_lhq
     */
    public static class OrderTable implements BaseColumns
    {
        public static String TABLE_NAME = "pt_order";

        /**
         * 订单号
         */
        public static String ORDER_NO = "order_no";

        /**
         * 订单主题
         */
        public static String ORDER_TITLE = "order_title";

        /**
         * 订单状态
         */
        public static String ORDER_STATUS = "order_status";

        /**
         * 订单状态码
         */
        public static String ORDER_STATUS_CODE = "order_status_code";

        /**
         * 订单金额单位分
         */
        public static String ORDER_PRICE = "order_price";

        /**
         * 支付方式
         */
        public static String ORDER_PAYMENT_TYPE = "order_payment_type";

        /**
         * 订单数据最后变化的时间
         */
        public static String ORDER_M_TIEM = "order_m_tiem";

        /**
         * 业务类型
         */
        public static String ORDER_PRODUCT_TYPE = "order_product_type";

        /**
         * 业务产品id
         */
        public static String ORDER_PRODUCT_ID = "order_product_id";

        /**
         * 查看状态
         */
        public static String ORDER_VIEW_STATUS = "order_view_status";

        /**
         * 订单业务详情扩展字段
         */
        public static String ORDER_EXPAND = "order_expand";

        /**
         * 优惠券列表
         */
        public static String ORDER_COUPON_IDS = "order_coupon_ids";

        /**
         * 优惠券价格 add by zjh 2015-04-15
         */
        public static String ORDER_COUPON_PRICE = "order_coupon_price";

        /**
         * 订单创建时间
         */
        public static String ORDER_C_TIME = "order_c_time";

        public static String ORDER_CP_ID = "order_cp_id"; // add by lxh
                                                          // 2015-3-30

        public static String ORDER_CP_NAME = "order_cp_name"; // add by hyl
                                                              // 2015-3-27

        public static String ORDER_CP_NUMBER = "order_cp_number";// add by hyl
                                                                 // 2015-3-27

        public static String ORDER_CP_NOTE = "order_cp_note";// add by hyl
                                                             // 2015-3-27
    }

    public static String getCreateOrderTableSQL()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ").append(OrderTable.TABLE_NAME).append(" (");
        sb.append(OrderTable._ID).append(" INTEGER  PRIMARY KEY autoincrement,");

        // add by xcx 2015-03-27 start 新增cp信息相关字段
        sb.append(OrderTable.ORDER_CP_ID).append(" LONG,");
        sb.append(OrderTable.ORDER_CP_NAME).append(" TEXT,");
        sb.append(OrderTable.ORDER_CP_NUMBER).append(" TEXT,");
        sb.append(OrderTable.ORDER_CP_NOTE).append(" TEXT,");
        // add by xcx 2015-03-27 end 新增cp信息相关字段

        // add by xcx 2015-01-12 start 新增优惠券列表字段
        sb.append(OrderTable.ORDER_COUPON_IDS).append(" TEXT,");
        // add by xcx 2015-01-12 end 新增优惠券列表字段

        // add by zjh 2015-04-15 start 新增优惠券列表字段
        sb.append(OrderTable.ORDER_COUPON_PRICE).append(" LONG,");
        // add by zjh 2015-04-15 end 新增优惠券列表字段

        sb.append(OrderTable.ORDER_EXPAND).append(" TEXT,");
        sb.append(OrderTable.ORDER_M_TIEM).append(" LONG,");
        sb.append(OrderTable.ORDER_NO).append(" TEXT,");
        sb.append(OrderTable.ORDER_PRICE).append(" INTEGER,");
        sb.append(OrderTable.ORDER_PRODUCT_ID).append(" INTEGER,");
        sb.append(OrderTable.ORDER_PRODUCT_TYPE).append(" INTEGER,");
        sb.append(OrderTable.ORDER_PAYMENT_TYPE).append(" INTEGER,");
        sb.append(OrderTable.ORDER_VIEW_STATUS).append(" INTEGER,");
        sb.append(OrderTable.ORDER_STATUS).append(" TEXT,");
        sb.append(OrderTable.ORDER_STATUS_CODE).append(" INTEGER,");
        sb.append(OrderTable.ORDER_TITLE).append(" TEXT,");
        sb.append(OrderTable.ORDER_C_TIME).append(" LONG");
        sb.append(");");
        return sb.toString();
    }

    /**
     * 清楚表tableName数据
     * 
     * @param tableName
     */
    public void clearTable(String tableName)
    {
        if (TextUtils.isEmpty(tableName))
        {
            return;
        }
        db.delete(tableName, null, null);
    }

    /**
     * 插入订单数据,不更新订单的ORDER_VIEW_STATUS
     * 
     * @param msg_id
     * @param order_id
     * @param order_content
     */
    public synchronized void insertOrder(PTOrderBean order)
    {
        if (order == null)
        {
            return;
        }

        LogUtil.d(TAG, "insertOrder: " + order.toString());
        if (TextUtils.isEmpty(order.getOrder_no()))
        {
            return;
        }

        PTOrderBean bean = queryOrderById(order.getOrder_no());
        ContentValues values = new ContentValues();
        values.put(OrderTable.ORDER_EXPAND, order.getExpand());
        values.put(OrderTable.ORDER_M_TIEM, order.getM_time());
        values.put(OrderTable.ORDER_NO, order.getOrder_no());
        values.put(OrderTable.ORDER_PRICE, order.getPrice());
        values.put(OrderTable.ORDER_PRODUCT_ID, order.getProduct_id());
        values.put(OrderTable.ORDER_PRODUCT_TYPE, order.getProduct_type());
        values.put(OrderTable.ORDER_PAYMENT_TYPE, order.getPayment_type());
        // values.put(OrderTable.ORDER_VIEW_STATUS, order.getView_status());
        values.put(OrderTable.ORDER_STATUS, order.getStatus());
        values.put(OrderTable.ORDER_STATUS_CODE, order.getStatus_code());
        values.put(OrderTable.ORDER_TITLE, order.getTitle());
        values.put(OrderTable.ORDER_COUPON_IDS, order.getCoupon_ids());

        values.put(OrderTable.ORDER_COUPON_PRICE, order.getFavo_price());

        values.put(OrderTable.ORDER_C_TIME, order.getC_time());// add by jsy
                                                               // 2015-11-24
                                                               // 按照创建时间排序
        // add by hyl 2015-3-27 start 添加cp信息
        values.put(OrderTable.ORDER_CP_ID, order.getCp_id());
        values.put(OrderTable.ORDER_CP_NAME, order.getCp_name());
        values.put(OrderTable.ORDER_CP_NUMBER, order.getCp_number());
        values.put(OrderTable.ORDER_CP_NOTE, order.getCp_note());
        // add by hyl 2015-3-27 end

        try
        {
            if (bean == null)
            {
                db.insert(OrderTable.TABLE_NAME, null, values);
            }
            else
            {
                db.update(OrderTable.TABLE_NAME, values, OrderTable.ORDER_NO + "=?", new String[]
                { order.getOrder_no() });
            }

        }
        catch (Exception e)
        {
            LogUtil.i(TAG, "insertOrder fail", e);
        }
    }

    public void updateOrderData(PTOrderBean order)
    {
        if (order == null)
        {
            return;
        }

        LogUtil.d(TAG, "updateOrderData: " + order.toString());
        if (TextUtils.isEmpty(order.getOrder_no()))
        {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(OrderTable.ORDER_EXPAND, order.getExpand());
        values.put(OrderTable.ORDER_M_TIEM, order.getM_time());
        values.put(OrderTable.ORDER_NO, order.getOrder_no());
        values.put(OrderTable.ORDER_PRICE, order.getPrice());
        values.put(OrderTable.ORDER_PRODUCT_ID, order.getProduct_id());
        values.put(OrderTable.ORDER_PRODUCT_TYPE, order.getProduct_type());
        values.put(OrderTable.ORDER_PAYMENT_TYPE, order.getPayment_type());
        values.put(OrderTable.ORDER_VIEW_STATUS, order.getView_status());
        values.put(OrderTable.ORDER_STATUS, order.getStatus());
        values.put(OrderTable.ORDER_STATUS_CODE, order.getStatus_code());
        values.put(OrderTable.ORDER_TITLE, order.getTitle());
        values.put(OrderTable.ORDER_C_TIME, order.getC_time());// add by jsy
                                                               // 2015-11-24
                                                               // 按照创建时间排序
        try
        {
            db.update(OrderTable.TABLE_NAME, values, OrderTable.ORDER_NO + "=?", new String[]
            { order.getOrder_no() });
        }
        catch (Exception e)
        {
            LogUtil.i(TAG, "updateOrderData fail", e);
        }
    }

    public void setOrderDeleted(String orderNo)
    {
        if (TextUtils.isEmpty(orderNo))
        {
            return;
        }

        LogUtil.d(TAG, "setOrderDeleted: " + orderNo);

        ContentValues values = new ContentValues();
        values.put(OrderTable.ORDER_VIEW_STATUS, 2);
        
        try
        {
            db.update(OrderTable.TABLE_NAME, values, OrderTable.ORDER_NO + "=?", new String[]
            { orderNo });
        }
        catch (Exception e)
        {
            LogUtil.i(TAG, "setOrderDeleted fail", e);
        }
    }

    /**
     * 根据订单号来查询消息
     * 
     * @param id
     * @return
     */
    public Cursor queryOrderByOrderId(String id)
    {
        return db.query(OrderTable.TABLE_NAME, null, OrderTable.ORDER_NO + "=" + id, null, null, null, null);
    }

    public PTOrderBean queryOrderById(String order_id)
    {
        if (TextUtils.isEmpty(order_id))
        {
            return null;
        }
        Cursor cursor = null;
        PTOrderBean bean = null;
        try
        {
            cursor = db.query(OrderTable.TABLE_NAME, null, OrderTable.ORDER_NO + "=?", new String[]
            { order_id }, null, null, null);
            if (cursor != null && cursor.moveToFirst())
            {
                bean = covertOrder(cursor);
            }
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
        return bean;
    }

    public ArrayList<PTOrderBean> queryOrderByProductType(int productType)
    {
        ArrayList<PTOrderBean> list = null;
        Cursor cursor = null;
        try
        {
            cursor = db.query(OrderTable.TABLE_NAME, null, OrderTable.ORDER_PRODUCT_TYPE + "=?", new String[]
            { productType + "" }, null, null, null);
            if (cursor != null)
            {
                list = new ArrayList<PTOrderBean>();
                while (cursor.moveToNext())
                {
                    PTOrderBean bean = covertOrder(cursor);
                    if (bean != null)
                    {
                        list.add(bean);
                    }
                }
            }
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
        return list;
    }

    /**
     * 根据productType和statusCode查询
     * 
     * @author lxh
     * @since 2015-11-5
     * @param productType
     * @param statusCode
     * @return
     */
    private ArrayList<PTOrderBean> queryOrderBy(int productType, int statusCode, String orderBy)
    {
        StringBuffer whereClauseBuf = new StringBuffer();
        if (productType != 0)
        {
            whereClauseBuf.append(OrderTable.ORDER_PRODUCT_TYPE + "=" + productType);
        }
        if (statusCode != 0)
        {
            // 前一个有条件
            if (whereClauseBuf.length() > 0)
            {
                whereClauseBuf.append(" and ");
            }
            whereClauseBuf.append(OrderTable.ORDER_STATUS_CODE + "=" + statusCode);
        }
        String selection = whereClauseBuf.toString();
        ArrayList<PTOrderBean> list = null;
        Cursor cursor = null;
        try
        {
            cursor = db.query(OrderTable.TABLE_NAME, null, selection, null, null, null, orderBy);
            if (cursor != null)
            {
                list = new ArrayList<PTOrderBean>();
                while (cursor.moveToNext())
                {
                    PTOrderBean bean = covertOrder(cursor);
                    if (bean != null)
                    {
                        list.add(bean);
                    }
                }
            }
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
        return list;
    }

    /**
     * 按时间倒序排序获得交易成功的订单
     * 
     * @author lxh
     * @since 2015-11-5
     * @param productType
     * @return
     */
    public ArrayList<PTOrderBean> querySuccessOrderByProductType(int productType)
    {
        return queryOrderBy(productType, PTOrderStatus.TRADE_SUCCESS.getStatusInt(), OrderTable.ORDER_M_TIEM + " DESC ");
    }

    /**
     * 分页查询数据库订单数据,按时间倒序,包括无订单业务的数据 add by zj 2014-12-27 11:37:42
     * 
     * @param startIndex 分页起始坐标
     * @param sum 这一页数据的数量
     * @return
     */
    public List<PTOrderBean> queryOrders(int startIndex, int sum)
    {
        List<PTOrderBean> orders = new ArrayList<PTOrderBean>();
        Cursor cursor = null;
        try
        {
            cursor = db.query(OrderTable.TABLE_NAME, null, null, null, null, null, OrderTable.ORDER_M_TIEM + " DESC ",
                    startIndex + "," + sum);
            if (cursor != null)
            {
                while (cursor.moveToNext())
                {
                    PTOrderBean bean = covertOrder(cursor);
                    if (bean != null)
                    {
                        orders.add(bean);
                    }
                }
            }
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
        return orders;
    }

    /**
     * add by zj 2015-01-14 16:36:15 获取所有订单总数,只包括订单,不包括非订单
     * 
     * @return
     */
    public int getOrderSumExceptNoOrder()
    {
        Cursor cursor = null;
        int sum = 0;
        try
        {
            cursor = db.query(OrderTable.TABLE_NAME, new String[]
            { "COUNT(1)" }, OrderTable.ORDER_STATUS + " IS NOT NULL ", null, null, null, null);

            if (cursor != null && cursor.moveToFirst())
            {
                sum = cursor.getInt(0);
            }
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }

        return sum;
    }

    /**
     * 分页查询数据库订单数据,按时间倒序,不包括无订单业务的数据
     * 
     * @param startIndex 分页起始坐标
     * @param sum 这一页数据的数量
     * @return
     */
    public List<PTOrderBean> queryOrdersExceptNoOrder(int startIndex, int sum)
    {
        List<PTOrderBean> orders = new ArrayList<PTOrderBean>();
        Cursor cursor = null;
        try
        {
            cursor = db.query(OrderTable.TABLE_NAME, null, OrderTable.ORDER_STATUS + " IS NOT NULL ", null, null, null,
                    OrderTable.ORDER_M_TIEM + " DESC ", startIndex + "," + sum);
            if (cursor != null)
            {
                while (cursor.moveToNext())
                {
                    PTOrderBean bean = covertOrder(cursor);
                    if (bean != null)
                    {
                        orders.add(bean);
                    }
                }
            }
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
        return orders;
    }

    /**
     * 
     * 方法表述 时间顺序加载
     * 
     * @param startIndex：起始位置
     * @param sum：加载总数
     * @return List<PTOrderBean>
     */
    public List<PTOrderBean> queryOrdersTimeACS(int startIndex, int sum)
    {
        return queryOrders(startIndex, sum, OrderTable.ORDER_M_TIEM);
    }

    /**
     * 分页查询数据库订单数据,按修改时间倒序,不包括无订单业务的数据
     * 
     * @param startIndex 分页起始坐标
     * @param sum 这一页数据的数量
     * @return
     */
    public List<PTOrderBean> queryOrdersMTimeDESC(int startIndex, int sum)
    {
        return queryOrders(startIndex, sum, OrderTable.ORDER_M_TIEM + " DESC ");
    }

    public List<PTOrderBean> queryOrders(int startIndex, int sum, String sort)
    {

        List<PTOrderBean> orders = new ArrayList<PTOrderBean>();
        Cursor cursor = null;
        try
        {
            cursor = db.query(OrderTable.TABLE_NAME, null, OrderTable.ORDER_STATUS + " IS NOT NULL ", null, null, null,
                    sort, startIndex + "," + sum);
            if (cursor != null)
            {
                while (cursor.moveToNext())
                {
                    PTOrderBean bean = covertOrder(cursor);
                    if (bean != null)
                    {
                        orders.add(bean);
                    }
                }
            }
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
        return orders;

    }

    /**
     * 分页查询数据库订单数据,按时间倒序,只包括无订单业务的数据
     * 
     * @param startIndex 分页起始坐标
     * @param sum 这一页数据的数量
     * @return
     */
    public List<PTOrderBean> queryNotOrdersData(int startIndex, int sum)
    {
        List<PTOrderBean> orders = new ArrayList<PTOrderBean>();
        Cursor cursor=null;
        try
        {
            cursor = db.query(OrderTable.TABLE_NAME, null, OrderTable.ORDER_STATUS + " IS NULL ", null, null, null,
                    OrderTable.ORDER_M_TIEM + " DESC ", startIndex + "," + sum);
            if (cursor != null)
            {
                while (cursor.moveToNext())
                {
                    PTOrderBean bean = covertOrder(cursor);
                    if (bean != null)
                    {
                        orders.add(bean);
                    }
                }
            }
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }

        return orders;
    }

    private PTOrderBean covertOrder(Cursor orderCursor)
    {
        PTOrderBean bean = null;
        if (orderCursor != null && !orderCursor.isAfterLast())
        {
            bean = new PTOrderBean();

            // 取订单的cp信息 add by hyl 2015-3-28 start
            bean.setCp_id(orderCursor.getLong(orderCursor.getColumnIndex(OrderTable.ORDER_CP_ID)));
            bean.setCp_name(orderCursor.getString(orderCursor.getColumnIndex(OrderTable.ORDER_CP_NAME)));
            bean.setCp_number(orderCursor.getString(orderCursor.getColumnIndex(OrderTable.ORDER_CP_NUMBER)));
            bean.setCp_note(orderCursor.getString(orderCursor.getColumnIndex(OrderTable.ORDER_CP_NOTE)));
            // add by hyl 2015-3-28 end

            bean.setExpand(orderCursor.getString(orderCursor.getColumnIndex(OrderTable.ORDER_EXPAND)));
            bean.setM_time(orderCursor.getLong(orderCursor.getColumnIndex(OrderTable.ORDER_M_TIEM)));
            // modifid by jsy 2015-12-09 目前不按创建时间排序后台没有返回这条数据,先注释掉创建时间
            // bean.setC_time(orderCursor.getLong(orderCursor.getColumnIndex(OrderTable.ORDER_C_TIME)));
            bean.setOrder_no(orderCursor.getString(orderCursor.getColumnIndex(OrderTable.ORDER_NO)));
            bean.setPrice(orderCursor.getInt(orderCursor.getColumnIndex(OrderTable.ORDER_PRICE)));
            bean.setProduct_id(orderCursor.getInt(orderCursor.getColumnIndex(OrderTable.ORDER_PRODUCT_ID)));
            bean.setProduct_type(orderCursor.getInt(orderCursor.getColumnIndex(OrderTable.ORDER_PRODUCT_TYPE)));
            bean.setPayment_type(orderCursor.getInt(orderCursor.getColumnIndex(OrderTable.ORDER_PAYMENT_TYPE)));
            bean.setView_status(orderCursor.getInt(orderCursor.getColumnIndex(OrderTable.ORDER_VIEW_STATUS)));
            bean.setStatus(orderCursor.getString(orderCursor.getColumnIndex(OrderTable.ORDER_STATUS)));
            bean.setStatus_code(orderCursor.getInt(orderCursor.getColumnIndex(OrderTable.ORDER_STATUS_CODE)));
            bean.setTitle(orderCursor.getString(orderCursor.getColumnIndex(OrderTable.ORDER_TITLE)));
            bean.setCoupon_ids(orderCursor.getString(orderCursor.getColumnIndex(OrderTable.ORDER_COUPON_IDS)));
            bean.setFavo_price(orderCursor.getLong(orderCursor.getColumnIndex(OrderTable.ORDER_COUPON_PRICE)));
        }
        return bean;
    }

}
