package com.example.handschoolapplication.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.NotificationAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.MenuBean;
import com.example.handschoolapplication.bean.NotificationBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.SelfDialog;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class NotificationNewsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_notification_news)
    PullToRefreshListView lvNotificationNews;

    private List<NotificationBean.DataBean> mList;
    private NotificationAdapter mAdapter;
    private String userId, userPhone;

    private int page = 0;
    private String limit = "10";
    FinishRefresh onRefreshComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("通知消息");
        userId = (String) SPUtils.get(this, "userId", "");
        mList = new ArrayList<>();
        mAdapter = new NotificationAdapter(NotificationNewsActivity.this, mList);
        lvNotificationNews.setAdapter(mAdapter);

        initView();

        initData();

        onSetListener();

    }

    private void initView() {

        // 1.设置刷新模式
        lvNotificationNews.setMode(PullToRefreshBase.Mode.BOTH);
        // 上拉加载更多，分页加载
        lvNotificationNews.getLoadingLayoutProxy(false, true).setPullLabel("加载更多");
        lvNotificationNews.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
        lvNotificationNews.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");
        // 下拉刷新
        lvNotificationNews.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        lvNotificationNews.getLoadingLayoutProxy(true, false).setRefreshingLabel("更新中...");
        lvNotificationNews.getLoadingLayoutProxy(true, false).setReleaseLabel("松开更新");

        setRefresh();
    }


    /**
     * 判断是否为三方登录
     */
    private void isHasPhone() {

        String user_phone = (String) SPUtils.get(this, "user_phone", "");
        if (TextUtils.isEmpty(user_phone)){
            showNoLoginDialog();
        }else {
            startActivity(new Intent(this, ModifyPwdActivity.class));
        }

    }

    private void showNoLoginDialog() {
        final SelfDialog selfDialog = new SelfDialog(this);

        selfDialog.setMessage("是否绑定手机号以便获取优惠券?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                startActivity(new Intent(NotificationNewsActivity.this, RegisterPersonActivity.class)
                        .putExtra("flag","true")
                        .putExtra("type","0"));
//                finish();
                selfDialog.dismiss();
            }
        });


        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                selfDialog.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
    }

    private void setRefresh() {
        lvNotificationNews.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(
                        getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                page = 0;
                mList.clear();
                initData();
//                isMore = true;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                // 自定义上拉header内容

                lvNotificationNews.getLoadingLayoutProxy(false, true)
                        .setPullLabel("上拉加载...");
                lvNotificationNews.getLoadingLayoutProxy(false, true)
                        .setRefreshingLabel("正在为你加载更多内容...");
                lvNotificationNews.getLoadingLayoutProxy(false, true)
                        .setReleaseLabel("松开自动加载...");
                page++;
                initData();


            }
        });
    }

    private class FinishRefresh extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
//          adapter.notifyDataSetChanged();
            if (lvNotificationNews!=null)
            lvNotificationNews.onRefreshComplete();
        }
    }

    private void sendMessage() {

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id",userId);
        params.put("inform_type","0");
        params.put("inform_state","1");
        OkHttpUtils.post()
                .url(Internet.UPDATE_NOTIFICATION)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(NotificationNewsActivity.java:78)<---->"+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(NotificationNewsActivity.java:84)<---->"+response);
                    }
                });
    }

    private void onSetListener() {
        mAdapter.setOnDiscountpagerListener(new NotificationAdapter.GetDiscountPagerListener() {
            @Override
            public void getDispager(int position) {
                int coupons_id = mList.get(position-1).getCoupons_id();
                userPhone = (String) SPUtils.get(NotificationNewsActivity.this, "user_phone", "");
                if (TextUtils.isEmpty(userPhone)){
                    isHasPhone();
                }else {
                    getDiscountPager(coupons_id);
                }

            }

            @Override
            public void useDisPager(int position){
                String inform_type = mList.get(position).getInform_type();
                if (inform_type.equals("1")){
                    showUseDisDialog("1","");
                }else {
//                        String school_id = mList.get(position).getSchool_id();
//                        startActivity(new Intent(getActivity(), ClassActivity.class).putExtra("school_id",school_id));
                }

            }
        });
    }

    private void showUseDisDialog(final String coupons_type, final String school_id) {
        final SelfDialog selfDialog = new SelfDialog(this);

        selfDialog.setMessage("前去使用?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                if (coupons_type.equals("1")){
                    startActivity(new Intent(NotificationNewsActivity.this, MainActivity.class));
                    EventBus.getDefault().post(new MenuBean(1));
                }else {
                    startActivity(new Intent(NotificationNewsActivity.this, ClassActivity.class).putExtra("school_id",school_id));
                }
                selfDialog.dismiss();
            }
        });


        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {

                selfDialog.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
    }

    private void getDiscountPager(int coupons_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("coupons_id", coupons_id + "");
        params.put("user_id", userId);
        params.put("user_phone", userPhone);
        OkHttpUtils.post()
                .url(InternetS.GET_DISCOUNT_PAPER)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(NotificationNewsActivity.java:82)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(NotificationNewsActivity.java:88)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            int result = jsonObject.getInt("result");
                            Toast.makeText(NotificationNewsActivity.this, msg, Toast.LENGTH_SHORT).show();

                            if (result == 0){
                                page =0;
                                mList.clear();
                                initData();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    private void initData() {

        Log.e("aaa",
                "(NotificationNewsActivity.java:211)<---->"+page);
        OkHttpUtils.post()
                .url(InternetS.NOTIFATION_NEWS_INFORMINAGER)
                .addParams("user_id", userId)
                .addParams("page", page+"")
                .addParams("limit", "10")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(NotificationNewsActivity.java:57)" + e.getMessage());
                        Toast.makeText(NotificationNewsActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(NotificationNewsActivity.java:63)" + response);

                        //通知后台的借口
                        sendMessage();
                        if (response.contains("没有信息")|| TextUtils.isEmpty(response)){
                            Toast.makeText(NotificationNewsActivity.this, "没有信息", Toast.LENGTH_SHORT).show();
                            mAdapter.notifyDataSetChanged();
                            onRefreshComplete = new FinishRefresh();
                            onRefreshComplete.execute();
                        }else {

                            NotificationBean notificationBean = new Gson().fromJson(response, NotificationBean.class);
                            mList.addAll(notificationBean.getData());
                            mAdapter.notifyDataSetChanged();
                            onRefreshComplete = new FinishRefresh();
                            onRefreshComplete.execute();

                        }
                    }
                });


    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_notification_news;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
        }
    }

    /**
     * 添加弹出的dialog关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements Dialog.OnDismissListener {

        @Override
        public void onDismiss(DialogInterface dialog) {
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
}
