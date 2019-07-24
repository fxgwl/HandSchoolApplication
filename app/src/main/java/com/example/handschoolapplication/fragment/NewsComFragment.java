package com.example.handschoolapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.ConsultNewsActivity;
import com.example.handschoolapplication.activity.InteractionNewsActivity;
import com.example.handschoolapplication.activity.LoginActivity;
import com.example.handschoolapplication.activity.NotificationNewsActivity;
import com.example.handschoolapplication.activity.SchoolNewsActivity;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.MenuBean;
import com.example.handschoolapplication.bean.NewsComBean;
import com.example.handschoolapplication.bean.NewsListBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

import static com.bumptech.glide.Glide.with;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsComFragment extends BaseFragment {

    @BindView(R.id.tv_learn_time)
    TextView tvLearnTime;
    @BindView(R.id.tv_learn)
    TextView tvLearn;
    @BindView(R.id.tv_notification_time)
    TextView tvNotificationTime;
    @BindView(R.id.tv_notification)
    TextView tvNotification;
    @BindView(R.id.tv_interact_time)
    TextView tvInteractTime;
    @BindView(R.id.tv_interact)
    TextView tvInteract;
    @BindView(R.id.tv_consult_num)
    TextView tvConsultNum;
    @BindView(R.id.tv_notification_num)
    TextView tvNotificationNum;
    @BindView(R.id.tv_interaction_num)
    TextView tvInteractionNum;
    @BindView(R.id.lv_news_com)
    ListView lvNews;
    Unbinder unbinder;
    private List<NewsListBean> newsList;//上面三个消息
    private View view;
    private String userId;
    private List<NewsComBean.DataBean> mList;
    private MyAdapter myAdapter;

//    private List<NewsBean> newsBeanList;
//    private MyAdapter myAdapter;


    public NewsComFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        userId = (String) SPUtils.get(getActivity(), "userId", "");

//        initViewData();
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);

        initViewData();

        initNewsData();

        initListener();

        return view;
    }

    private void initViewData() {
        newsList = new ArrayList<>();

        if (TextUtils.isEmpty(userId) || userId == null) {
            return;
        }
        OkHttpUtils.post()
                .url(InternetS.NEWSLIST)
                .addParams("user_id", userId)
                .addParams("user_type", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(NewsComFragment.java:90)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(NewsComFragment.java:97) == " + response);

                        getNewsNum();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("data");
                            Gson gson = new Gson();
                            if (response.contains("没有信息")) {

                            } else if (response.contains("interactmsg")) {

                            } else {
                                newsList.addAll((Collection<? extends NewsListBean>) gson.fromJson(data.toString(), new TypeToken<ArrayList<NewsListBean>>() {
                                }.getType()));

                                for (int i = 0; i < newsList.size(); i++) {
                                    String message_type = newsList.get(i).getMessage_type();
                                    if (message_type.equals("3")) {//学习消息
                                        tvLearn.setText(newsList.get(i).getMessage_content());
                                        tvLearnTime.setText(newsList.get(i).getMessage_date());
                                    } else if (message_type.equals("1")) {//通知消息
                                        tvNotification.setText(newsList.get(i).getMessage_content());
                                        tvNotificationTime.setText(newsList.get(i).getMessage_date());
                                    } else if (message_type.equals("2")) {//互动消息
                                        tvInteract.setText(newsList.get(i).getMessage_content());
                                        tvInteractTime.setText(newsList.get(i).getMessage_date());
                                    }
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    private void initNewsData() {

        mList = new ArrayList<>();
        myAdapter = new MyAdapter();
        lvNews.setAdapter(myAdapter);

        String school_id = (String) SPUtils.get(getActivity(), "school_id", "");
        HashMap<String, String> params = new HashMap<>();
        params.put("school_id", school_id);
        OkHttpUtils.post()
                .url(Internet.NEWS_COM_COURSE)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(NewsComFragment.java:125)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", "(NewsComFragment.java:130)<---->" + response);
                        if (TextUtils.isEmpty(response)) {

                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int result = jsonObject.getInt("result");
                                if (result == 0) {
                                    NewsComBean newsComBean = new Gson().fromJson(response, NewsComBean.class);
                                    mList.clear();
                                    mList.addAll(newsComBean.getData());
                                    myAdapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void initListener() {

        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), SchoolNewsActivity.class).putExtra("course_id", mList.get(i).getCourse_id()));
            }
        });
    }

    private void getNewsNum() {

        try {
            String userId = (String) SPUtils.get(getActivity(), "userId", "");
            final String user_type = (String) SPUtils.get(getActivity(), "user_type", "");
            OkHttpUtils.post()
                    .url(Internet.UNREAD_NEWS_NUM)
                    .addParams("user_id", userId)
                    .addParams("user_type", "1")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e("aaa",
                                    "(NewsComFragment.java:293)" + e.getMessage());
                        }

                        @Override
                        public void onResponse(String response, int id) {

                            Log.e("aaa",
                                    "(NewsComFragment.java:210)<--学堂版消息接口-->" + response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
//                            String studyNum = jsonObject.getJSONObject("data").getString("studymsg");
                                String cousultNum = jsonObject.getJSONObject("data").getString("consult");
                                String informNum = jsonObject.getJSONObject("data").getString("inform");
                                String interactNum = jsonObject.getJSONObject("data").getString("interact");
                                String zong = jsonObject.getJSONObject("data").getString("count");

                                Log.e("aaa",
                                        "(NewsComFragment.java:205)---user_type----" + user_type);
                                if (user_type.equals("1")) {
                                    if (!cousultNum.equals("0")) {
                                        tvConsultNum.setVisibility(View.VISIBLE);
                                        tvConsultNum.setText(cousultNum);
                                    } else {
                                        tvConsultNum.setVisibility(View.INVISIBLE);
                                    }
                                    if (!informNum.equals("0")) {
                                        tvNotificationNum.setVisibility(View.VISIBLE);
                                        tvNotificationNum.setText(informNum);
                                    } else {
                                        tvNotificationNum.setVisibility(View.INVISIBLE);
                                    }
                                    if (!interactNum.equals("0")) {
                                        tvInteractionNum.setVisibility(View.VISIBLE);
                                        tvInteractionNum.setText(interactNum);
                                    } else {
                                        tvInteractionNum.setVisibility(View.INVISIBLE);
                                    }
                                    EventBus.getDefault().post(zong);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {

        }

    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_com_news;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void hello(MenuBean event) {
        /* Do something */
//        mhandler.sendEmptyMessageDelayed(event.getNum(), 50);
        Log.e("aaa",
                "(MainActivity.java:395)<------- NewsFragment EventBus的传递事件------->");
        initViewData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        initViewData();
        initNewsData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        getNewsNum();
        initViewData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.ll_learn_news, R.id.ll_notification_news, R.id.ll_interaction_news})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_learn_news:
                startActivityForResult(new Intent(getActivity(), ConsultNewsActivity.class), 0);
                break;
            case R.id.ll_notification_news:
                startActivityForResult(new Intent(getActivity(), NotificationNewsActivity.class), 0);
                break;
            case R.id.ll_interaction_news:
                startActivityForResult(new Intent(getActivity(), InteractionNewsActivity.class), 0);
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
                view = View.inflate(getActivity(), R.layout.item_news_lv, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            with(getActivity()).load(Internet.BASE_URL + mList.get(position).getPicture_one()).centerCrop().into(holder.profileImage);
            holder.tvTime.setText(mList.get(position).getMessage_time());
            holder.tvUsername.setText(mList.get(position).getCourse_name());
            holder.tvNews.setText("ID：" + mList.get(position).getCourse_id());

            return view;
        }

        class ViewHolder {
            @BindView(R.id.profile_image)
            CircleImageView profileImage;
            @BindView(R.id.tv_username)
            TextView tvUsername;
            @BindView(R.id.tv_time)
            TextView tvTime;
            @BindView(R.id.tv_news)
            TextView tvNews;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
