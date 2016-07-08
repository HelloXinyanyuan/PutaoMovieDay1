package com.whunf.putaomovieday1.common.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.storage.db.CityTableHand;
import com.whunf.putaomovieday1.common.storage.db.entity.CityBean;
import com.whunf.putaomovieday1.common.util.CityMgr;
import com.whunf.putaomovieday1.common.util.GraphicUtil;
import com.whunf.putaomovieday1.common.util.LogUtil;
import com.whunf.putaomovieday1.common.widget.IndexBar;
import com.whunf.putaomovieday1.common.widget.pinnedheaderlistview.PinnedHeaderListView;
import com.whunf.putaomovieday1.common.widget.pinnedheaderlistview.SectionedBaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 城市选择界面
 */
public class CitySelectActivity extends BaseActivity {

    private static final String TAG = "CitySelectActivity";
    private EditText mEtSearchCity;
    private PinnedHeaderListView mPhlvCityList;
    private CityTableHand cityTableHand;//操作城市数据库的把手

    private Map<String, List<CityBean>> mAllCitysByGroup = new TreeMap<>();//treemap可以对key排序并且保证不重复

    private Map<String, Integer> mGroupOfMap = new HashMap<String, Integer>();

    private List<String> mGroup = new ArrayList<>();

    private CityListAdapter mCityListAdapter;
    private List<CityBean> mAllCitys;

    //字母索引bar
    private IndexBar mIbAbcde;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);
        mEtSearchCity = (EditText) findViewById(R.id.et_search_city);
        mPhlvCityList = (PinnedHeaderListView) findViewById(R.id.phlv_citylist);
        mPhlvCityList.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {
                String key = mGroup.get(section);
                CityBean cb = mAllCitysByGroup.get(key).get(position);
                //存储城市
                CityMgr.getInstance().saveCity(cb.getCityName());
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

            }
        });
        mCityListAdapter = new CityListAdapter();
        mPhlvCityList.setAdapter(mCityListAdapter);
        mIbAbcde = (IndexBar) findViewById(R.id.ib_abcde);

        //添加EditText内容变化的监听器
        mEtSearchCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {//变化后的
                //过滤后的城市列表
                List<CityBean> fitlterCitys = new ArrayList<CityBean>();
                for (CityBean city : mAllCitys) {
                    if (city.getCityPy().contains(s) || city.getCitySpy().contains(s) || city.getCityName().contains(s)) {
                        fitlterCitys.add(city);
                    }
                }
                //将数据分组并且显示
                groupDataShow(fitlterCitys);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //创建一个城市数据库表的操作类
        cityTableHand = new CityTableHand(this);
        mAllCitys = cityTableHand.getYijiCitys();
        groupDataShow(mAllCitys);
    }


    /**
     * 将数据分组并且显示在listview上
     *
     * @param cityList
     */
    private void groupDataShow(List<CityBean> cityList) {
        //清空上一次的
        mAllCitysByGroup.clear();
        mGroup.clear();

        for (CityBean city : cityList) {//装入map
            String key = city.getCitySpy().charAt(0) + "";
            if (mAllCitysByGroup.containsKey(key)) {
                List<CityBean> citys = mAllCitysByGroup.get(key);
                citys.add(city);
            } else {
                ArrayList<CityBean> citys = new ArrayList<CityBean>();
                citys.add(city);
                mAllCitysByGroup.put(key, citys);
            }
        }


        int itemSize = 0;
        for (Map.Entry<String, List<CityBean>> entry :
                mAllCitysByGroup.entrySet()) {
            mGroup.add(entry.getKey());

            mGroupOfMap.put(entry.getKey(), itemSize + mGroup.size() - 1);
            itemSize += entry.getValue().size();
        }

//        for (String key : mAllCitysByGroup.keySet()) {//装入list集合用于组检索
//            mGroup.put(key,);
//        }

        //刷新适配器，间接的更新了PinnedHeaderlistview
        mCityListAdapter.notifyDataSetChanged();

        //将字母传入控件
        mIbAbcde.setIndexs(mGroup);
        mIbAbcde.setIndexBarChangeListener(new IndexBar.IndexBarChangeListener() {
            @Override
            public void onIndexChange(String indexStr, int pos) {//获得字母变化的回调
                LogUtil.d(TAG, "onIndexChange pos:" + pos);
                int selection = mGroupOfMap.get(indexStr);
                mPhlvCityList.setSelection(selection);
            }
        });
    }

    class CityListAdapter extends SectionedBaseAdapter {

        @Override
        public Object getItem(int section, int position) {
            return null;
        }

        @Override
        public long getItemId(int section, int position) {
            return 0;
        }

        @Override
        public int getSectionCount() {
            return mGroup.size();
        }

        @Override
        public int getCountForSection(int section) {
            String key = mGroup.get(section);
            List<CityBean> citys = mAllCitysByGroup.get(key);
            return citys.size();
        }

        @Override
        public View getItemView(int section, int position, View convertView, ViewGroup parent) {
            Context context = parent.getContext();
            TextView textView = (TextView) View.inflate(context, android.R.layout.simple_list_item_1, null);
            String key = mGroup.get(section);
            List<CityBean> citys = mAllCitysByGroup.get(key);
            textView.setText(citys.get(position).getCityName());
            return textView;
        }

        @Override
        public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
            Context context = parent.getContext();
            TextView text = new TextView(context);
            String key = mGroup.get(section);
            int paddinghorizontal = context.getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin);
            int paddingVertical = GraphicUtil.dip2px(context, 8);
            text.setPadding(paddinghorizontal, paddingVertical, paddinghorizontal, paddingVertical);
            text.setBackgroundColor(Color.WHITE);
            text.setText(key);
            return text;
        }
    }
}
