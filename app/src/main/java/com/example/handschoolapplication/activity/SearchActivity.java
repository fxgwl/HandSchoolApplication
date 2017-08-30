package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.HistorySearch;
import com.example.handschoolapplication.bean.SearchSuccess;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search_title)
    TextView tvSearchTitle;
    @BindView(R.id.lv_history)
    ListView lvHistory;

    private List<HistorySearch> mlist;
    private MySearchAdapter mySearchAdapter;

    private List<SearchSuccess> mSlist;
    private MySearchSuccessAdapter mySearchSuccessAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewData();
    }

    private void initViewData() {

        mlist = new ArrayList<>();
        mlist.add(new HistorySearch());
        mlist.add(new HistorySearch());
        mlist.add(new HistorySearch());
        mlist.add(new HistorySearch());
        mlist.add(new HistorySearch());
        mlist.add(new HistorySearch());
        mySearchAdapter = new MySearchAdapter();
        lvHistory.setAdapter(mySearchAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_search;
    }

    @OnClick({R.id.iv_back, R.id.tv_back, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_search:

                //搜索成功调用的方法
                setViewData();

                break;
        }
    }

    private void setViewData() {
        tvSearchTitle.setText("搜索结果");

        mSlist = new ArrayList<>();
        mSlist.add(new SearchSuccess());
        mSlist.add(new SearchSuccess());
        mSlist.add(new SearchSuccess());
        mSlist.add(new SearchSuccess());
        mSlist.add(new SearchSuccess());
        mySearchSuccessAdapter = new MySearchSuccessAdapter();
        lvHistory.setAdapter(mySearchSuccessAdapter);
    }

    class MySearchAdapter extends BaseAdapter {

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
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                view = View.inflate(SearchActivity.this, R.layout.item_history_lv, null);
                holder.textView = (TextView) view.findViewById(R.id.tv_text);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            return view;
        }

        class ViewHolder {
            TextView textView;
        }
    }

    class MySearchSuccessAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mSlist.size();
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
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder = null;
            if (view == null) {
                view = View.inflate(SearchActivity.this, R.layout.item_hf_course_lv, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            return view;
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
