package com.whunf.putaomovieday1.common.core;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2016/6/21.
 */
public abstract class BaseFragment extends Fragment {

    /**
     * 让其子类标准化，都具备此方法
     */
   public abstract void initData();


    public BaseActivity getBaseActivity(){

        return  (BaseActivity)getActivity();
    }

}
