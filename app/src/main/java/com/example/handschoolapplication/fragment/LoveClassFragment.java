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
import com.example.handschoolapplication.bean.SchoolSaveBean;
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
public class LoveClassFragment extends BaseFragment {

    //收藏的学堂
    @BindView(R.id.lv_love_course)
    ListView lvLoveClass;
    private View view;
    private MyLoveClassAdapter myLoveClassAdapter;
    private String user_id;
    ArrayList<SchoolSaveBean.DataBean> mlist = new ArrayList<>();

    public LoveClassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        Log.e("aaa",
                "(LoveClassFragment.java:41)" + "进入我收藏的学堂fragment");
        user_id = (String) SPUtils.get(getActivity(), "userId", "");
        myLoveClassAdapter = new MyLoveClassAdapter();
        lvLoveClass.setAdapter(myLoveClassAdapter);
        initView();
        return view;
    }

    private void initView() {
        OkHttpUtils.post()
                .url(Internet.SCHOOLCOLLECT)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(LoveClassFragment.java:72)" + response);

                        Gson gson = new Gson();
                        mlist.clear();
                        if (response.contains("没有信息")) {

                        } else {
                            mlist.addAll(gson.fromJson(response, SchoolSaveBean.class).getData());
                        }
                        myLoveClassAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_love_class;
    }

    class MyLoveClassAdapter extends BaseAdapter {

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
                convertView = View.inflate(getActivity(), R.layout.item_love_class_lv, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Glide.with(getActivity())
                    .load(Internet.BASE_URL + Internet.SCHOOLCOLLECT)
                    .centerCrop()
                    .error(R.drawable.kecheng)
                    .into(holder.ivClass);
            holder.tvClass.setText(mlist.get(position).getSchool_name());
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
                            .url(Internet.DELETESCHOOLCOLLECT)
                            .addParams("collect_sid", mlist.get(position).getCollect_sid() + "")
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.e("aaa",
                                            "(LoveClassFragment.java:72)" + response);
                                    if (response.contains("成功")) {
                                        mlist.remove(position);
                                        myLoveClassAdapter.notifyDataSetChanged();
                                        finalHolder.tvDelet.setVisibility(View.GONE);
                                        Toast.makeText(activity, "删除成功", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            });
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.iv_class)
            ImageView ivClass;
            @BindView(R.id.tv_class)
            TextView tvClass;
            @BindView(R.id.iv_xing1)
            ImageView ivXing1;
            @BindView(R.id.iv_xing2)
            ImageView ivXing2;
            @BindView(R.id.iv_xing3)
            ImageView ivXing3;
            @BindView(R.id.iv_xing4)
            ImageView ivXing4;
            @BindView(R.id.iv_xing5)
            ImageView ivXing5;
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
