package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.PJDAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.PJDetailBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.view.MyListView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class PJDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.pjd_mlv)
    MyListView pjdMlv;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_dengji)
    ImageView ivDengji;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_pjcontent)
    TextView tvPjcontent;
    @BindView(R.id.pjd_mlvhuifu)
    MyListView pjdMlvhuifu;
    @BindView(R.id.iv_classhead)
    ImageView ivClasshead;
    @BindView(R.id.iv_classname)
    TextView ivClassname;
    @BindView(R.id.iv_classmoney)
    TextView ivClassmoney;
    @BindView(R.id.tv_pjnum)
    TextView tvPjnum;
    @BindView(R.id.et_replycontent)
    EditText etReplycontent;
    @BindView(R.id.tv_reply)
    TextView tvReply;
    private String interact_id;
    private PJDetailBean.DataBean dataBean;
    ArrayList<PJDetailBean.DataBean.ReplyInfoBean> replyInfoBeens = new ArrayList<PJDetailBean.DataBean.ReplyInfoBean>();
    ArrayList<PJDetailBean.DataBean.ReplyInfoBean> replyInfoBeens2 = new ArrayList<PJDetailBean.DataBean.ReplyInfoBean>();
    private PJDAdapter pjdAdapter;
    private PJDAdapter pjdAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        interact_id = getIntent().getStringExtra("interact_id");
        tvTitle.setText("评价详情");
        pjdAdapter = new PJDAdapter(this, replyInfoBeens);
        pjdAdapter2 = new PJDAdapter(this, replyInfoBeens2);
        pjdMlv.setAdapter(pjdAdapter2);
        pjdMlvhuifu.setAdapter(pjdAdapter);
        initView();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_pjdetail;
    }

    //初始化
    private void initView() {
        OkHttpUtils.post()
                .url(Internet.COMMENTDETAIL)
                .addParams("interact_id", interact_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(PJDetailActivity.java:58)" + response);
                        Gson gson = new Gson();
                        try {
                        } catch (Exception e) {
                            dataBean = gson.fromJson(response, PJDetailBean.class).getData();
                        }
                        Glide.with(PJDetailActivity.this)
                                .load(Internet.BASE_URL + dataBean.getCourse_photo())
                                .centerCrop()
                                .error(R.drawable.kecheng)
                                .into(ivHead);
                        tvName.setText(dataBean.getCourse_name());
                        tvTime.setText(dataBean.getInteract_time());
                        tvPjcontent.setText(dataBean.getContents());
                        replyInfoBeens.clear();
                        replyInfoBeens2.clear();
                        for (int i = 0; i < dataBean.getReplyInfo().size(); i++) {
                            if (!"2".equals(dataBean.getReplyInfo().get(i).getReply_type())) {
                                replyInfoBeens.add(dataBean.getReplyInfo().get(i));
                            } else {
                                replyInfoBeens2.add(dataBean.getReplyInfo().get(i));
                            }
                        }
                        pjdAdapter.notifyDataSetChanged();
                        pjdAdapter2.notifyDataSetChanged();

                    }
                });
    }

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        finish();
    }
}
