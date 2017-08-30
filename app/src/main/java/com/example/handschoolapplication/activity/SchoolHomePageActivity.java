package com.example.handschoolapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;

public class SchoolHomePageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_school_home_page;
    }
}
