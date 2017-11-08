package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.HorizontalListViewAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.view.HorizontalListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassTypeAddDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ctad_hl_art)
    HorizontalListView ctadHlArt;
    @BindView(R.id.gv_art_detail)
    GridView gvArtDetail;
    @BindView(R.id.tv_type_one)
    TextView tvTypeOne;
    private List<String> typeTwo;
    private String typeOne;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        if (null!=getIntent().getSerializableExtra("typeTwo"))
        typeTwo = (List<String>) getIntent().getSerializableExtra("typeTwo");
        typeOne = getIntent().getStringExtra("type");
        initView();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_class_type_add_detail;
    }

    private void initView() {
        tvTitle.setText("添加学堂类别");
        tvTypeOne.setText(typeOne);
        final HorizontalListViewAdapter hlvAdapter = new HorizontalListViewAdapter(this, typeTwo);
        final HorizontalListViewAdapter hlvAdapter2 = new HorizontalListViewAdapter(this, typeTwo);
        ctadHlArt.setAdapter(hlvAdapter);
        gvArtDetail.setAdapter(hlvAdapter2);
        ctadHlArt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hlvAdapter.setSelectedPosition(position);
                hlvAdapter.notifyDataSetChanged();
            }
        });
        gvArtDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("aaa",
                    "(ClassTypeAddDetailActivity.java:69)"+position);
                hlvAdapter2.setSelectedPosition(position);
                hlvAdapter2.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.rl_back, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_save:
                finish();
                break;
        }
    }
}
