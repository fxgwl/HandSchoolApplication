package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.base.SaveCourseBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoveCourseFragment extends BaseFragment {

    //收藏的课程
    private View view;
    private ListView lvCourseLove;
    private MyLoveCourseAdapter myLoveCourseAdapter;
    private String user_id;
    ArrayList<SaveCourseBean.DataBean> mlist = new ArrayList<>();


    public LoveCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        lvCourseLove = (ListView) view.findViewById(R.id.lv_love_course);
        user_id = (String) SPUtils.get(getActivity(), "userId", "");
        myLoveCourseAdapter = new MyLoveCourseAdapter();
        lvCourseLove.setAdapter(myLoveCourseAdapter);
        initView();
        return view;
    }

    private void initView() {
        OkHttpUtils.post()
                .url(Internet.SAVECOURSELIST)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(LoveCourseFragment.java:66)" + response);
                        Gson gson = new Gson();
                        mlist.clear();
                        if (response.contains("没有信息")) {
                            myLoveCourseAdapter.notifyDataSetChanged();
                        } else {
                            mlist.addAll(gson.fromJson(response, SaveCourseBean.class).getData());
                            myLoveCourseAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_love_class;
    }


    class MyLoveCourseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int position) {
            return mlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.item_love_course_lv, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Glide.with(getActivity())
                    .load(Internet.BASE_URL +mlist.get(position).getCourse_photo())
                    .centerCrop()
                    .error(R.drawable.kecheng)
                    .into(holder.ivCourse);
            holder.tvCourse.setText(mlist.get(position).getCourse_name());
            holder.tvPrice.setText("价格： ¥" + mlist.get(position).getCourse_money());
            final ViewHolder finalHolder = holder;
            holder.rl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    finalHolder.tvDelet.setVisibility(View.VISIBLE);
                    return false;
                }
            });
            finalHolder.tvDelet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OkHttpUtils.post()
                            .url(Internet.DELETECOLLECT)
                            .addParams("collect_id", mlist.get(position).getCollect_id() + "")
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.e("aaa",
                                            "(MyLoveCourseAdapter.java:150)" + response);
                                    if (response.contains("成功")) {
                                        mlist.remove(position);
                                        myLoveCourseAdapter.notifyDataSetChanged();
                                        finalHolder.tvDelet.setVisibility(View.GONE);
                                        Toast.makeText(activity, "删除成功", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            });
            holder.ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2017/8/15 fenxiang
                }
            });
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.iv_course)
            ImageView ivCourse;
            @BindView(R.id.tv_course)
            TextView tvCourse;
            @BindView(R.id.tv_price)
            TextView tvPrice;
            @BindView(R.id.iv_share)
            ImageView ivShare;
            @BindView(R.id.rl)
            RelativeLayout rl;
            @BindView(R.id.tvdelet)
            TextView tvDelet;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
