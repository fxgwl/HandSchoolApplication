package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SchoolAddressActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.lv_schooladdress)
    ListView lvSchooladdress;
    @BindView(R.id.tv_schooladdress_add)
    TextView tvSchooladdressAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_school_address;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_save, R.id.tv_schooladdress_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                break;
            case R.id.iv_menu:
                break;
            case R.id.tv_save:
                break;
            case R.id.tv_schooladdress_add:
                break;
        }
    }
}
