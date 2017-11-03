package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.CourseBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrusteeshipActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    RelativeLayout ivMenu;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_sort)
    TextView tvSort;

    private ListView listView;
    private List<CourseBean> mlist;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = (ListView) findViewById(R.id.lv_course);
        ivMenu.setVisibility(View.VISIBLE);
        tvTitle.setText("托管");
        initData();


    }

    private void initData() {
        mlist = new ArrayList<>();
        mlist.add(new CourseBean());
        mlist.add(new CourseBean());
        mlist.add(new CourseBean());
        mlist.add(new CourseBean());

        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_art;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.et_search, R.id.iv_search, R.id.tv_sort, R.id.iv_sort, R.id.tv_defaultrank, R.id.tv_allrank, R.id.iv_allrank})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:                 show(view);
                break;
            case R.id.et_search:
            case R.id.iv_search://搜索
                startActivity(new Intent(TrusteeshipActivity.this, SearchActivity.class));
                break;
            case R.id.tv_sort:
            case R.id.iv_sort://类别
                break;
            case R.id.tv_defaultrank://默认排序
                break;
            case R.id.tv_allrank://综合排序
            case R.id.iv_allrank:
                break;
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(TrusteeshipActivity.this, R.layout.item_find_course_lv, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            return convertView;


        }

        class ViewHolder {
            @BindView(R.id.iv_course)
            ImageView ivCourse;
            @BindView(R.id.tv_course)
            TextView tvCourse;
            @BindView(R.id.tv_distance)
            TextView tvDistance;
            @BindView(R.id.tv_price)
            TextView tvPrice;
            @BindView(R.id.popularity)
            TextView popularity;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
