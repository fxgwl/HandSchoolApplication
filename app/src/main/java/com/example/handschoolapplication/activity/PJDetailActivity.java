package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.PJDAdapter;
import com.example.handschoolapplication.adapter.PJDThreeAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.PJDetailBean;
import com.example.handschoolapplication.bean.ReplyInfoThree;
import com.example.handschoolapplication.utils.FilterEmojiTextWatcher;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.KeybordS;
import com.example.handschoolapplication.utils.MyUtiles;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.utils.SystemUtil;
import com.example.handschoolapplication.view.KeyMapDialog;
import com.example.handschoolapplication.view.MyListView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.bumptech.glide.Glide.with;

public class PJDetailActivity extends BaseActivity implements KeyMapDialog.SendBackListener {

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
    @BindView(R.id.tv_additional_pj)
    TextView tvAdditional_pj;
    @BindView(R.id.ll_send_message)
    LinearLayout llSendMessage;
    @BindView(R.id.rl_parent)
    RelativeLayout rlParent;
    @BindView(R.id.view_20dp)
    View view20Dp;
    @BindView(R.id.ll_course)
    LinearLayout llCourse;
    ArrayList<PJDetailBean.DataBean.ReplyInfoBean> replyInfoBeens = new ArrayList<PJDetailBean.DataBean.ReplyInfoBean>();
    //    ArrayList<PJDetailBean.DataBean.ReplyInfoBean> replyInfoBeens2 = new ArrayList<PJDetailBean.DataBean.ReplyInfoBean>();
    ArrayList<ReplyInfoThree.DataBean> replyInfoBeens2 = new ArrayList<ReplyInfoThree.DataBean>();
    private String interact_id;
    private PJDetailBean.DataBean dataBean;
    private PJDAdapter pjdAdapter;
    //    private PJDAdapter pjdAdapter2;
    private PJDThreeAdapter pjdAdapter2;
    private String user_id;
    private String user_type;
    private String reply_type;
    private KeyMapDialog keyMapDialog;//声明一个回复Dialog

    /**
     * 声明几个变量  用来记录点击列表的回复数据
     */
    private String beReplyId = "";//被回复人的Id
    private String beReplyOrderId = "";//被回复的订单Id
    private String beReplyInteractId = "";//被回复的订单Id
    private int browse_num=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        View decorView = getWindow().getDecorView();
        View contentView = findViewById(Window.ID_ANDROID_CONTENT);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        user_id = (String) SPUtils.get(this, "userId", "");
        user_type = (String) SPUtils.get(this, "user_type", "");

        Log.e("aaa", "(PJDetailActivity.java:111)<--user_id-->" + "user_id == " + user_id);
        interact_id = getIntent().getStringExtra("interact_id");
        tvTitle.setText("评价详情");
        pjdAdapter = new PJDAdapter(this, replyInfoBeens);
//        pjdAdapter2 = new PJDAdapter(this, replyInfoBeens2);
        pjdAdapter2 = new PJDThreeAdapter(this, replyInfoBeens2);

        pjdMlv.setAdapter(pjdAdapter2);
        pjdMlvhuifu.setAdapter(pjdAdapter);
        pjdMlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (TextUtils.isEmpty(user_id)) {
                    Toast toa = Toast.makeText(PJDetailActivity.this, "请先登录", Toast.LENGTH_SHORT);
                    toa.setGravity(Gravity.CENTER, 0, 0);
                    toa.show();
//                    startActivity(new Intent(PJDetailActivity.this,LoginActivity.class));
                } else {
                    if(user_type.equals("1")){
                        return;
                    }
                    beReplyId = replyInfoBeens2.get(position).getSender_id();
                    beReplyOrderId = replyInfoBeens2.get(position).getOrder_id();
                    beReplyInteractId = replyInfoBeens2.get(position).getInteract_id();
                    String send_name = replyInfoBeens2.get(position).getUser_name();
                    //String send_name = replyInfoBeens2.get(position).getUserInfo().getMember_name();
                    keyMapDialog = new KeyMapDialog("回复：" + send_name, PJDetailActivity.this, "1");
                    keyMapDialog.show(getSupportFragmentManager(), "view");
                }

            }
        });

        if (user_type.equals("0")) {
            llSendMessage.setVisibility(View.VISIBLE);
            tvAdditional_pj.setText("追评");
        } else {
            llSendMessage.setVisibility(View.GONE);
            tvAdditional_pj.setText("回复");
        }

        String systemModel = SystemUtil.getSystemModel();
        if (systemModel.contains("AL")) {
            view20Dp.setVisibility(View.VISIBLE);
        } else {
            view20Dp.setVisibility(View.GONE);
        }

        initView();

        etReplycontent.addTextChangedListener(new FilterEmojiTextWatcher(this));
    }

    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);

                int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
                int diff = height - r.bottom;

                if (diff != 0) {
                    if (contentView.getPaddingBottom() != diff) {
                        contentView.setPadding(0, 0, 0, diff);
                    }
                } else {
                    if (contentView.getPaddingBottom() != 0) {
                        contentView.setPadding(0, 0, 0, 0);
                    }
                }
            }
        };
    }

    //初始化
    private void initView() {
        replyInfoBeens.clear();
        replyInfoBeens2.clear();
        KeybordS.setupUI(rlParent, this);
        OkHttpUtils.post()
                .url(Internet.COMMENTDETAIL)
                .addParams("interact_id", interact_id)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(PJDetailActivity.java:179)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(PJDetailActivity.java:185)" + response);

                        Log.e("aaa",
                                "(PJDetailActivity.java:109)" + interact_id);
                        Gson gson = new Gson();
                        try {
                            dataBean = gson.fromJson(response, PJDetailBean.class).getData();
                            String userId = user_type.equals("0") ? dataBean.getSend_uid() : dataBean.getUser_id();
                            if (userId.equals(user_id)) {
                                tvAdditional_pj.setVisibility(View.VISIBLE);
                            } else {
                                tvAdditional_pj.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {

                        }
                        String picture_one = dataBean.getPicture_one();
                        with(PJDetailActivity.this)
                                .load(Internet.BASE_URL + picture_one)
                                .centerCrop()
                                .error(R.drawable.kecheng)
                                .into(ivClasshead);
                        with(PJDetailActivity.this)
                                .load(Internet.BASE_URL + dataBean.getSend_photo())
                                .centerCrop()
                                .into(ivHead);
                        tvName.setText(dataBean.getSend_name());
                        tvTime.setText(dataBean.getInteract_time());
                        tvPjcontent.setText(dataBean.getContents());
                        ivClassname.setText(dataBean.getCourse_name());
                        ivClassmoney.setText("价格：¥" + dataBean.getCourse_money());

                        if (dataBean.getReplyInfo() != null) {
                            int size = 0;
                            if (null != replyInfoBeens2)
                                size = replyInfoBeens2.size();
                            browse_num = dataBean.getBrowse_num();
                            //tvPjnum.setText(size + "条评论    " + browse_num + "条浏览");
                            for (int i = 0; i < dataBean.getReplyInfo().size(); i++) {
                                if (!"2".equals(dataBean.getReplyInfo().get(i).getReply_type())) {
                                    replyInfoBeens.add(dataBean.getReplyInfo().get(i));
                                } else {
//                                    replyInfoBeens2.add(dataBean.getReplyInfo().get(i));
                                }
                            }
                            pjdAdapter.notifyDataSetChanged();
//                            pjdAdapter2.notifyDataSetChanged();
                        } else {
                            browse_num = dataBean.getBrowse_num();
                            //tvPjnum.setText("0条评论    " + browse_num + "条浏览");

                        }
                        GetReply();
                    }
                });

    }

    public void GetReply(){
        HashMap<String, String> params = new HashMap<>();
        params.put("interact_id", interact_id);
        params.put("page", "1");
        params.put("limit", "10000");

        Log.e("aaa",
                "(PJDetailActivity.java:245)<---->" + params);

        OkHttpUtils.post()
                .url(Internet.COMMENT_NEW_LIST)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(PJDetailActivity.java:253)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(PJDetailActivity.java:261)<---->" + response);
                        ReplyInfoThree replyInfoThree = new Gson().fromJson(response, ReplyInfoThree.class);
                        List<ReplyInfoThree.DataBean> data = replyInfoThree.getData();
                        if (data != null) {
                            replyInfoBeens2.addAll(data);
                        }
                        tvPjnum.setText(replyInfoThree.getCount() + "条评论    " + browse_num + "条浏览");
                        Log.e("aaa",
                                "(PJDetailActivity.java:268)<--replyInfoBeens2.size()-->" + replyInfoBeens2.size());
                        pjdAdapter2.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_pjdetail;
    }

    @OnClick({R.id.rl_back, R.id.tv_reply, R.id.tv_additional_pj,R.id.ll_course})
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
                HashMap<String, String> params = new HashMap<>();
                params.put("sender_id", user_id);
                params.put("replier_id", dataBean.getSend_uid());
                params.put("records", reply);
                params.put("interact_id", interact_id);
                params.put("critic_type", "0");
                params.put("order_id", dataBean.getOrder_id());

                Log.e("aaa",
                        "(PJDetailActivity.java:335)<--评论的地址 url-->" + Internet.COMMENT_NEW);
                Log.e("aaa",
                        "(PJDetailActivity.java:337)<--评论的参数 params-->" + params.toString());
                OkHttpUtils.post()
                        .url(Internet.COMMENT_NEW)
                        .params(params)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("aaa",
                                        "(PJDetailActivity.java:346)<---->" + e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("aaa",
                                        "(PJDetailActivity.java:352)<---->" + response);
                                etReplycontent.setText("");
                                initView();//刷新
                            }
                        });
//                send_uid
//                        from_uid
//                order_id
//                        reply_type
//                interact_id
//                        reply_content
//                String uid = dataBean.getUser_id();
//                String senduid = dataBean.getSend_uid();
//                if (uid.equals(user_id)) {
//                    reply_type = "1";
//                } else if (senduid.equals(user_id)) {
//                    reply_type = "0";
//                } else {
//                    reply_type = "2";
//                }

//                HashMap<String, String> params = new HashMap<>();
//                params.put("send_uid", dataBean.getUser_id());
//                params.put("from_uid", user_id);
//                params.put("order_id", dataBean.getOrder_id());
//                params.put("reply_type", reply_type);
//                params.put("interact_id", interact_id);
//                params.put("reply_content", reply);
//
//                Log.e("aaa",
//                        "(PJDetailActivity.java:228)<--评论回复的参数-->" + params.toString());
//
//                OkHttpUtils.post()
//                        .url(Internet.REPLAYCOMMENT)
//                        .params(params)
//                        .build()
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onError(Call call, Exception e, int id) {
//                                Log.e("aaa",
//                                        "(PJDetailActivity.java:230)<---->" + e.getMessage());
//                            }
//
//                            @Override
//                            public void onResponse(String response, int id) {
//                                Log.e("aaa",
//                                        "(PJDetailActivity.java:183)" + response);
//                                if (response.contains("成功")) {
//                                    Toast.makeText(PJDetailActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
//                                    etReplycontent.setText("");
//                                    initView();//刷新
//                                }
//                            }
//                        });
                break;
            case R.id.tv_additional_pj://追加评论和回复的按钮
                Log.e("aaa",
                        "(PJDetailActivity.java:259)<---->" + "aaaa");

                keyMapDialog = new KeyMapDialog("回复：", this, "0");
                keyMapDialog.show(getSupportFragmentManager(), "view");
                break;
            case R.id.ll_course:
                Intent intent = new Intent(this, CourseHomePagerActivity.class);
                intent.putExtra("course_id", dataBean.getCourse_id());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void sendMessage(String comment) {

        Log.e("aaa", "(PJDetailActivity.java:386)<---->" + "aaaaaaaaaaaaaaaaaaaaaa");

        String uid = dataBean.getUser_id();
        String senduid = dataBean.getSend_uid();
        if (uid.equals(user_id)) {
            reply_type = "1";
        } else if (senduid.equals(user_id)) {
            reply_type = "0";
        } else {
            reply_type = "2";
        }


        HashMap<String, String> params = new HashMap<>();
        if (user_type.equals("0")) {
            params.put("from_uid", dataBean.getUser_id());
        } else {
            params.put("from_uid", dataBean.getSend_uid());
        }
        params.put("send_uid", user_id);
//        params.put("from_uid", user_id);
        params.put("order_id", dataBean.getOrder_id());
        params.put("reply_type", reply_type);
        params.put("interact_id", interact_id);
        params.put("reply_content", comment);

        Log.e("aaa",
                "(PJDetailActivity.java:228)<--评论回复的参数-->" + params.toString());

        OkHttpUtils.post()
                .url(Internet.REPLAYCOMMENT)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(PJDetailActivity.java:230)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(PJDetailActivity.java:183)" + response);
                        if (response.contains("成功")) {
                            Toast.makeText(PJDetailActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                            etReplycontent.setText("");
                            keyMapDialog.hideProgressdialog();
                            keyMapDialog.dismiss();
                            initView();//刷新
                        }
                    }
                });
        // TODO: 2018/8/21 发送事件

//        HashMap<String, String> params = new HashMap<>();
//        params.put("sender_id", user_id);
//        params.put("replier_id", dataBean.getUser_id());
//        params.put("records", comment);
//        params.put("interact_id", dataBean.getInteract_id());
//        params.put("order_id", dataBean.getOrder_id());
//
//        Log.e("aaa",
//                "(PJDetailActivity.java:299)<--评论的地址 url-->"+Internet.COMMENT_NEW);
//        Log.e("aaa",
//                "(PJDetailActivity.java:301)<--评论的参数 params-->"+params.toString());
//        OkHttpUtils.post()
//                .url(Internet.COMMENT_NEW)
//                .params(params)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Log.e("aaa",
//                                "(PJDetailActivity.java:306)<---->" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        Log.e("aaa",
//                                "(PJDetailActivity.java:312)<---->" + response);
//                        keyMapDialog.hideProgressdialog();
//                        keyMapDialog.dismiss();
//                    }
//                });


    }

    @Override
    public void sendMessages(String message) {
        Log.e("aaa", "(PJDetailActivity.java:478)<---->" + "bbbbbbbbbbbbbbbb");
        HashMap<String, String> params = new HashMap<>();
        params.put("sender_id", user_id);
        params.put("replier_id", beReplyId);
        params.put("records", message);
        params.put("interact_id", beReplyInteractId);
        params.put("order_id", beReplyOrderId);
        params.put("critic_type", "1");

        Log.e("aaa",
                "(PJDetailActivity.java:335)<--评论的地址 url-->" + Internet.COMMENT_NEW);
        Log.e("aaa",
                "(PJDetailActivity.java:337)<--评论的参数 params-->" + params.toString());
        OkHttpUtils.post()
                .url(Internet.COMMENT_NEW)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(PJDetailActivity.java:346)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(PJDetailActivity.java:352)<---->" + response);
                        keyMapDialog.hideProgressdialog();
                        keyMapDialog.dismiss();
                        initView();//刷新
                    }
                });
    }
}
