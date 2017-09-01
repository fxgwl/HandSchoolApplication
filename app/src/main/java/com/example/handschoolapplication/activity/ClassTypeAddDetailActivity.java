package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.HorizontalListViewAdapter;
import com.example.handschoolapplication.view.HorizontalListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassTypeAddDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ctad_hl_art)
    HorizontalListView ctadHlArt;
    @BindView(R.id.gv_art_detail)
    GridView gvArtDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_type_add_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("添加学堂类别");
        ArrayList<String> list = new ArrayList<>();
        list.add("书画");
        list.add("音乐");
        list.add("舞蹈");
        list.add("球类");
        list.add("棋类");
        list.add("爱好");
        list.add("棋类");
        list.add("爱好");
        list.add("棋类");
        list.add("爱好");
        list.add("棋类");
        list.add("爱好");
        list.add("棋类");
        list.add("爱好");
        final HorizontalListViewAdapter hlvAdapter = new HorizontalListViewAdapter(this, list);
        final HorizontalListViewAdapter hlvAdapter2 = new HorizontalListViewAdapter(this, list);
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
