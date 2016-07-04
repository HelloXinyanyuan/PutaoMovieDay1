package com.whunf.putaomovieday1.module.movie.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.BaseActivity;

/**
 * 剧照大图显示
 */
public class ShowStillActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_still);
        String[] stills = getIntent().getStringArrayExtra("stills");
        int currentIndex = getIntent().getIntExtra("currentIndex", 0);
        ((TextView) findViewById(R.id.tv_still)).setText(stills[currentIndex]);

    }

}
