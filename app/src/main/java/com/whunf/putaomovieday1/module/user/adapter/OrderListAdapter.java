package com.whunf.putaomovieday1.module.user.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.storage.db.entity.PTOrderBean;
import com.whunf.putaomovieday1.common.util.LogUtil;

import java.util.ArrayList;


public class OrderListAdapter extends BaseListViewAdapter
{

    private ArrayList<PTOrderBean> orders;

    private DataLoader mDataLoader;

    private BaseActivity mContext;

    public OrderListAdapter(BaseActivity context, ArrayList<PTOrderBean> newestOrders)
    {
        this.orders = newestOrders;
        this.mContext = context;
        mDataLoader = new ImageLoaderFactory(mContext).getOrderListLoader();
    }

    @Override
    public int getCount()
    {
        return orders.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LogUtil.i(getClass().getSimpleName(), "SpeedLog getView=" + System.currentTimeMillis());
        PTOrderBean bean = orders.get(position);
        AbstractOrderBussiness bussiness = PTOrderCenter.getInstance().getService(bean);
        if (bussiness != null)
        {
            bussiness.setDataLoader(mDataLoader);
            View view = bussiness.getOrderView(mContext, orders.get(position), convertView);
            // if (position == 0)
            // {
            // view.findViewById(R.id.divider).setVisibility(View.VISIBLE);
            // }
            return view;
        }
        return new View(ContactsApp.getInstance());
    }

    @Override
    public DataLoader getmImageLoader()
    {
        return mDataLoader;
    }

    public void clearCache()
    {
        mDataLoader.clearCache();
    }
}
