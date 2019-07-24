package com.example.handschoolapplication.activity;

import android.os.Bundle;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.Internet;

import java.util.ArrayList;
import java.util.List;

public class ImageLookActivity extends BaseActivity {

    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String images = getIntent().getStringExtra("images");
        if (images.contains(",")){
            String[] split = images.split(",");
            for (int i = 0; i < split.length; i++) {
                mList.add(Internet.BASE_URL+split[i]);
            }
        }else {
            mList.add(images);
        }

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_image_look;
    }
}
