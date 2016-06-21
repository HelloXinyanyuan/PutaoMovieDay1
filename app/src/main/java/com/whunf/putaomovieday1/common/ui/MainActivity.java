package com.whunf.putaomovieday1.common.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;
import com.whunf.putaomovieday1.common.core.PMApplication;
import com.whunf.putaomovieday1.common.util.T;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView helloVolley= (TextView) findViewById(R.id.helloVolley);

        String url="http://api.putao.so/sbiz/movie/cinema/list?citycode=%E4%B8%8A%E6%B5%B7";

        StringRequest stringRequest=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                helloVolley.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                T.showShort(MainActivity.this,"加载出错了！");
            }
        });
        //将这个请求放到请求队列中
        PMApplication.getInstance().getRequestQueue().add(stringRequest);

    }
}
