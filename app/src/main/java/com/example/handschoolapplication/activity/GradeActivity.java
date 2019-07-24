package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.IntegralBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class GradeActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_grade)
    TextView tvGrade;
    @BindView(R.id.tv_integral_num)
    TextView tvIntegralNum;
    @BindView(R.id.iv_class_grade)
    ImageView ivClassGrade;
    @BindView(R.id.lv_integral)
    ListView lvIntegral;

    private List<IntegralBean.DataBean> mList = new ArrayList<>();
    private MyAdapter myAdapter;
    private String user_id;
    private String flag;
    private String grade;
    private String integral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("等级详情");
        user_id = (String) SPUtils.get(this, "userId", "");
        flag = getIntent().getStringExtra("flag");
        grade = getIntent().getStringExtra("grade");
        integral = getIntent().getStringExtra("integral");
        if (flag.equals("com")) {
            ivClassGrade.setVisibility(View.VISIBLE);
            switch (grade) {
                case "":
                case "0":
                    ivClassGrade.setImageResource(R.drawable.xuetangdengji_wu_zheng);
                case "1":
                    ivClassGrade.setImageResource(R.drawable.xuetangdengji_tong_zheng);
                    break;
                case "2":
                    ivClassGrade.setImageResource(R.drawable.xuetangdengji_yin_zheng);
                    break;
                case "3":
                    ivClassGrade.setImageResource(R.drawable.xuetangdengji_jin_zheng);
                    break;
            }
        } else {
            ivClassGrade.setVisibility(View.GONE);
            tvGrade.setText("Lv." + grade);
        }
        int v = (int) Double.parseDouble(integral);
        tvIntegralNum.setText(v + "分");

        initData();
    }

    private void initData() {
        myAdapter = new MyAdapter();
        lvIntegral.setAdapter(myAdapter);
        OkHttpUtils.post()
                .url(Internet.INTEGRALRECORD)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(GradeActivity.java:73)" + response);
                        Gson gson = new Gson();
                        mList.clear();
                        if (response.contains("没有信息")) {

                        } else {
                            mList.addAll(gson.fromJson(response, IntegralBean.class).getData());
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_grade;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_integral_more,R.id.ll_grade_rule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.ll_integral_more:
                break;
            case R.id.ll_grade_rule:
                startActivity(new Intent(this, WebGradeActivity.class)
                        .putExtra("url", "personal_rating1.html")
                        .putExtra("title", "积分等级规则"));
                break;
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder = null;
            if (view == null) {
                view = View.inflate(GradeActivity.this, R.layout.item_integral_lv, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            IntegralBean.DataBean interalBean = mList.get(position);
            holder.tvContent.setText(interalBean.getIntegral_cause().equals("0") ? "购买加分" : "评论加分");
            int interal = (int) Double.parseDouble(interalBean.getIntegral_num());
            holder.tvNum.setText("+" + interal);
            holder.tvDay.setText(interalBean.getIntegral_time().split(" ")[0]);
            holder.tvHour.setText(interalBean.getIntegral_time().split(" ")[1].split(":")[0] +
                    ":" + interalBean.getIntegral_time().split(" ")[1].split(":")[1]);
            return view;
        }

        class ViewHolder {
            @BindView(R.id.tv_hour)
            TextView tvHour;
            @BindView(R.id.tv_day)
            TextView tvDay;
            @BindView(R.id.tv_num)
            TextView tvNum;
            @BindView(R.id.tv_content)
            TextView tvContent;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
