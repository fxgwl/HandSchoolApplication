package com.example.handschoolapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.ClassActivity;
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

import static com.bumptech.glide.Glide.with;

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

            final String school_logo = mlist.get(position).getSchool_logo();
            with(getActivity())
                    .load(Internet.BASE_URL + school_logo)
                    .centerCrop()
                    .error(R.drawable.kecheng)
                    .into(holder.ivClass);
            holder.tvClass.setText(mlist.get(position).getSchool_name());
            String school_jifen = mlist.get(position).getSchool_jifen();
            switch (school_jifen){
                case "0":
                    break;
                case "1":
                    holder.ivXing1.setImageResource(R.drawable.wujiaoxing);
                    break;
                case "2":
                    holder.ivXing1.setImageResource(R.drawable.wujiaoxing);
                    holder.ivXing2.setImageResource(R.drawable.wujiaoxing);

                    break;
                case "3":
                    holder.ivXing1.setImageResource(R.drawable.wujiaoxing);
                    holder.ivXing2.setImageResource(R.drawable.wujiaoxing);
                    holder.ivXing3.setImageResource(R.drawable.wujiaoxing);
                    break;
                case "4":
                    holder.ivXing1.setImageResource(R.drawable.wujiaoxing);
                    holder.ivXing2.setImageResource(R.drawable.wujiaoxing);
                    holder.ivXing3.setImageResource(R.drawable.wujiaoxing);
                    holder.ivXing4.setImageResource(R.drawable.wujiaoxing);
                    break;
                case "5":
                    holder.ivXing1.setImageResource(R.drawable.wujiaoxing);
                    holder.ivXing2.setImageResource(R.drawable.wujiaoxing);
                    holder.ivXing3.setImageResource(R.drawable.wujiaoxing);
                    holder.ivXing4.setImageResource(R.drawable.wujiaoxing);
                    holder.ivXing5.setImageResource(R.drawable.wujiaoxing);
                    break;
            }
            final ViewHolder finalHolder = holder;
            holder.rl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    finalHolder.tvDelet.setVisibility(View.VISIBLE);
                    return false;
                }
            });
            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String school_id = mlist.get(position).getSchool_id();
                    startActivity(new Intent(getActivity(), ClassActivity.class).putExtra("school_id",school_id));
                }
            });

            finalHolder.llShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String school_id = mlist.get(position).getSchool_id();
                    String school_name = mlist.get(position).getSchool_name();
                    showShare(school_name,school_name,Internet.BASE_URL+school_logo,"http://120.92.44.55/PrivateSchool/head/tel_zsss/xtzy_sz.jsp?schoolID="+school_id);
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
            @BindView(R.id.ll_share)
            LinearLayout llShare;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
