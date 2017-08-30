package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.DaifukuangLvAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DaifukuangActivity extends AppCompatActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.lv_daifukuang)
    ListView lvDaifukuang;
    private DaifukuangLvAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_daifukuang);
        ButterKnife.bind(this);
        String type=getIntent().getStringExtra("type");
        switch (type){
            case "1":
                tvTitle.setText("等待买家付款");
                break;
            case "2":
                tvTitle.setText("等待学习确认");
                break;
            case "3":
                tvTitle.setText("等待买家评价");
                break;
            case "4":
                tvTitle.setText("等待买家评价");
                break;
            case "5":
                tvTitle.setText("等待买家评价");
                break;
            case "6":
                tvTitle.setText("等待买家评价");
                break;
        }

        lvDaifukuang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(DaifukuangActivity.this,OrderDetailActivity.class));
            }
        });
        mAdapter=new DaifukuangLvAdapter(DaifukuangActivity.this);
        lvDaifukuang.setAdapter(mAdapter);

    }

    @OnClick({R.id.rl_back, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
