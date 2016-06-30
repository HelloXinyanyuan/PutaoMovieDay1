package com.whunf.putaomovieday1.module.movie.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseFragment;

/**
 * 发现页
 */
public class DiscoverFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void initData() {

    }
}
