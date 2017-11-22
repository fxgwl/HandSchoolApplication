package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.RefundManagerLvAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ClassDealManagerBean;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class RefundManagerActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    RelativeLayout ivMenu;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tv_refundmanager_all)
    TextView tvRefundmanagerAll;
    @BindView(R.id.tv_refundmanager_allline)
    TextView tvRefundmanagerAllline;
    @BindView(R.id.tv_refundmanager_tkz)
    TextView tvRefundmanagerTkz;
    @BindView(R.id.tv_refundmanager_tkzline)
    TextView tvRefundmanagerTkzline;
    @BindView(R.id.tv_refundmanager_complete)
    TextView tvRefundmanagerComplete;
    @BindView(R.id.tv_refundmanager_completeline)
    TextView tvRefundmanagerCompleteline;
    @BindView(R.id.lv_refundmanager)
    ListView lvRefundmanager;
    @BindView(R.id.ll_refundmanager_all)
    LinearLayout llRefundmanagerAll;
    @BindView(R.id.ll_refundmanager_tkz)
    LinearLayout llRefundmanagerTkz;
    @BindView(R.id.ll_refundmanager_complete)
    LinearLayout llRefundmanagerComplete;
    private RefundManagerLvAdapter mAdapter;
    private List<ClassDealManagerBean> mList;
    private String school_id;

    private List<ClassDealManagerBean> allRefunList;
    private List<ClassDealManagerBean> refuningList;
    private List<ClassDealManagerBean> hadRefunList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ButterKnife.bind(this);
        school_id = (String) SPUtils.get(this, "school_id", "");
        tvTitle.setText("退款管理");
        mList = new ArrayList<>();
        allRefunList = new ArrayList<>();
        refuningList = new ArrayList<>();
        hadRefunList = new ArrayList<>();
        mAdapter = new RefundManagerLvAdapter(RefundManagerActivity.this, refuningList);
        lvRefundmanager.setAdapter(mAdapter);

        initData();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_refund_manager;
    }

    private void initData() {
        mList.clear();
        HashMap<String, String> params = new HashMap<>();
        params.put("school_id", school_id);
        OkHttpUtils.post()
                .url(InternetS.CLASS_ORDER_INFOR)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(RefundManagerActivity.java:90)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(RefundManagerActivity.java:97)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            mList.addAll((Collection<? extends ClassDealManagerBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassDealManagerBean>>() {
                            }.getType()));
                            for (int i = 0; i < mList.size(); i++) {
                                switch (mList.get(i).getOrder_state()){
                                    case "4":
                                        allRefunList.add(mList.get(i));
                                        refuningList.add(mList.get(i));
                                        break;
                                    case "6":
                                        allRefunList.add(mList.get(i));
                                        hadRefunList.add(mList.get(i));
                                        break;
                                }

                            }
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_refundmanager_all, R.id.ll_refundmanager_tkz, R.id.ll_refundmanager_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:                 show(view);
                break;
            case R.id.ll_refundmanager_all:
                tvRefundmanagerAll.setTextColor(getResources().getColor(R.color.blue));
                tvRefundmanagerAllline.setBackgroundColor(getResources().getColor(R.color.blue));
                tvRefundmanagerTkz.setTextColor(getResources().getColor(R.color.t666666));
                tvRefundmanagerTkzline.setBackgroundColor(getResources().getColor(R.color.white));
                tvRefundmanagerComplete.setTextColor(getResources().getColor(R.color.t666666));
                tvRefundmanagerCompleteline.setBackgroundColor(getResources().getColor(R.color.white));
                mAdapter.setList(allRefunList);
                break;

            case R.id.ll_refundmanager_tkz:

                tvRefundmanagerAll.setTextColor(getResources().getColor(R.color.t666666));
                tvRefundmanagerAllline.setBackgroundColor(getResources().getColor(R.color.white));
                tvRefundmanagerTkz.setTextColor(getResources().getColor(R.color.blue));
                tvRefundmanagerTkzline.setBackgroundColor(getResources().getColor(R.color.blue));
                tvRefundmanagerComplete.setTextColor(getResources().getColor(R.color.t666666));
                tvRefundmanagerCompleteline.setBackgroundColor(getResources().getColor(R.color.white));
                mAdapter.setList(refuningList);
                break;

            case R.id.ll_refundmanager_complete:

                tvRefundmanagerAll.setTextColor(getResources().getColor(R.color.t666666));
                tvRefundmanagerAllline.setBackgroundColor(getResources().getColor(R.color.white));
                tvRefundmanagerTkz.setTextColor(getResources().getColor(R.color.t666666));
                tvRefundmanagerTkzline.setBackgroundColor(getResources().getColor(R.color.white));
                tvRefundmanagerComplete.setTextColor(getResources().getColor(R.color.blue));
                tvRefundmanagerCompleteline.setBackgroundColor(getResources().getColor(R.color.blue));
                mAdapter.setList(hadRefunList);
                break;


        }
    }


}
