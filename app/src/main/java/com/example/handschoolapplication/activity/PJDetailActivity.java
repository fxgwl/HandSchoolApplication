package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.PJDAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.PJDetailBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
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
    private String user_id;
    private String user_type;
    private String reply_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        user_id = (String) SPUtils.get(this, "userId", "");
        user_type = (String) SPUtils.get(this, "user_type", "");
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

                        Log.e("aaa",
                                "(PJDetailActivity.java:109)" + interact_id);
                        Gson gson = new Gson();
                        try {
                            dataBean = gson.fromJson(response, PJDetailBean.class).getData();
                        } catch (Exception e) {

                        }
                        Glide.with(PJDetailActivity.this)
                                .load(Internet.BASE_URL + dataBean.getCourse_photo())
                                .centerCrop()
                                .error(R.drawable.kecheng)
                                .into(ivClasshead);
                        Glide.with(PJDetailActivity.this)
                                .load(Internet.BASE_URL + dataBean.getSend_photo())
                                .centerCrop()
                                .into(ivHead);
                        tvName.setText(dataBean.getSend_name());
                        tvTime.setText(dataBean.getInteract_time());
                        tvPjcontent.setText(dataBean.getContents());
                        ivClassname.setText(dataBean.getCourse_name());
                        ivClassmoney.setText("价格：¥"+dataBean.getCourse_money());
                        replyInfoBeens.clear();
                        replyInfoBeens2.clear();
                        if (null != dataBean.getReplyInfo()) {
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
                    }
                });
    }

    @OnClick({R.id.rl_back, R.id.tv_reply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_reply:
                //评价回复
                String reply = etReplycontent.getText().toString();
                if (TextUtils.isEmpty(reply)) {
                    Toast.makeText(this, "评论不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
//                send_uid
//                        from_uid
//                order_id
//                        reply_type
//                interact_id
//                        reply_content
                String uid = dataBean.getUser_id();
                String senduid = dataBean.getSend_uid();
                if (uid.equals(user_id)) {
                    reply_type = "1";
                } else if (senduid.equals(user_id)) {
                    reply_type = "0";
                } else {
                    reply_type = "2";
                }
                OkHttpUtils.post()
                        .url(Internet.REPLAYCOMMENT)
                        .addParams("send_uid", dataBean.getUser_id())
                        .addParams("from_uid", user_id)
                        .addParams("order_id", dataBean.getOrder_id())
                        .addParams("reply_type", reply_type)
                        .addParams("interact_id", interact_id)
                        .addParams("reply_content", reply)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("aaa",
                                        "(PJDetailActivity.java:183)" + response);
                                if (response.contains("成功")) {
                                    Toast.makeText(PJDetailActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                                    etReplycontent.setText("");
                                }
                            }
                        });


                break;
        }
    }
}
