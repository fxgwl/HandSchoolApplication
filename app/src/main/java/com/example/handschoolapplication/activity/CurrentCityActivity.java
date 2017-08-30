package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;

public class CurrentCityActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_current_city;
    }
}
