package com.example.handschoolapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.handschoolapplication.activity.HumanServiceActivity;
import com.example.handschoolapplication.activity.InteractionNewsActivity;
import com.example.handschoolapplication.activity.LearnNewsActivity;
import com.example.handschoolapplication.activity.NotificationNewsActivity;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.NewsBean;
import com.example.handschoolapplication.bean.NewsListBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment {

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
    @BindView(R.id.lv_news)
    ListView lvNews;
    Unbinder unbinder;

    private View view;

    private List<NewsBean.DataBean> newsBeanList = new ArrayList<>();//个人版消息列表

    private List<NewsListBean> newsList;//上面三个消息
    private MyAdapter myAdapter;
    private String userId;


    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        userId = (String) SPUtils.get(getActivity(), "userId", "");

        return view;
    }

    private void getNews() {
        newsList = new ArrayList<>();
        OkHttpUtils.post()
                .url(InternetS.NEWSLIST)
                .addParams("user_id", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(NewsFragment.java:90)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(NewsFragment.java:97)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("data");
                            Gson gson = new Gson();
                            newsList.addAll((Collection<? extends NewsListBean>) gson.fromJson(data.toString(), new TypeToken<ArrayList<NewsListBean>>() {
                            }.getType()));

                            for (int i = 0; i < newsList.size(); i++) {
                                String message_type = newsList.get(i).getMessage_type();
                                if (message_type.equals("0")) {//学习消息
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void initViewData() {
        myAdapter = new MyAdapter();
        lvNews.setAdapter(myAdapter);
        OkHttpUtils.post()
                .url(Internet.PERSONDIALOG)
                .addParams("user_id", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(NewsFragment.java:151)" + response);
                        newsBeanList.clear();
                        Gson gson = new Gson();
                        try {
                            newsBeanList.addAll(gson.fromJson(response, NewsBean.class).getData());
                            myAdapter.notifyDataSetChanged();
                            lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent3 = new Intent(getActivity(), HumanServiceActivity.class);
                                    intent3.putExtra("type", "0");
                                    intent3.putExtra("course_id", newsBeanList.get(position).getCourse_id());
                                    intent3.putExtra("schooluid", newsBeanList.get(position).getSend_id());
                                    startActivity(intent3);
                                }
                            });
                        } catch (Exception e) {

                        }
                    }
                });


    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_news;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_learn_news, R.id.ll_notification_news, R.id.ll_interaction_news})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_learn_news://学习消息
                startActivity(new Intent(getActivity(), LearnNewsActivity.class));
                break;
            case R.id.ll_notification_news://通知消息
                startActivity(new Intent(getActivity(), NotificationNewsActivity.class));
                break;
            case R.id.ll_interaction_news://互动消息
                startActivity(new Intent(getActivity(), InteractionNewsActivity.class));
                break;
        }
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newsBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return newsBeanList.get(position);
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
            Glide.with(getActivity())
                    .load(Internet.BASE_URL + newsBeanList.get(position).getSchool_photo())
                    .centerCrop()
                    .into(holder.profileImage);
            holder.tvUsername.setText(newsBeanList.get(position).getSchool_name());
            holder.tvTime.setText(newsBeanList.get(position).getConsult_time());
            holder.tvNews.setText(newsBeanList.get(position).getConsult_content());
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

    @Override
    public void onResume() {
        super.onResume();
        getNews();
        initViewData();
    }
}
