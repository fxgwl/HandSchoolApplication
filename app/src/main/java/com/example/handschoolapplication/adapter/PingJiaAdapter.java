package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.PJDetailActivity;
import com.example.handschoolapplication.bean.EvaluateBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/24.
 */

public class PingJiaAdapter extends BaseAdapter {
    private Context context;
    private List<EvaluateBean> mlist;
    private int size;
    private OnItemGoDetail listener;

    public PingJiaAdapter(Context context, List<EvaluateBean> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    public void setListener(OnItemGoDetail listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {

        if (mlist != null) {
            size = mlist.size();
        }
        return size;
    }

    public interface OnItemGoDetail {
        void setGoToDetail(int position);
    }

    static class ViewHolder {
        @BindView(R.id.iv_usericon)
        ImageView ivUsericon;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.iv_grade)
        ImageView ivGrade;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.lv_reply)
        MyListView listView;
        @BindView(R.id.tv_look)
        TextView tvLook;
        @BindView(R.id.tv_pingjia)
        TextView tvPingjia;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class MyAdapter extends BaseAdapter {

        private ArrayList<EvaluateBean.ReplyInfoBean> replyList;
        private int size = 0;

        public MyAdapter(ArrayList<EvaluateBean.ReplyInfoBean> replyList) {
            this.replyList = replyList;
        }

        class ViewHolder {
            @BindView(R.id.ll_class_reply)
            LinearLayout tvClassReply;
            @BindView(R.id.tv_content_add)
            TextView tvContentAdd;
            @BindView(R.id.ll_zhuiping)
            LinearLayout llZhuiping;
            @BindView(R.id.tv_zhuiping)
            TextView tvZhuiping;
            @BindView(R.id.tv_class_reply_time)
            TextView tvClassReplyTime;
            @BindView(R.id.tv_class_reply_content)
            TextView tvClassReplyContent;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        @Override
        public int getCount() {
            if (replyList != null) {
                size = replyList.size();
            }
            return size;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder = null;
            if (view == null) {
                view = View.inflate(context, R.layout.item_reply_lv, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            if ("0".equals(replyList.get(position).getReply_type())) {
                holder.llZhuiping.setVisibility(View.VISIBLE);
                holder.tvContentAdd.setVisibility(View.VISIBLE);
                holder.tvClassReply.setVisibility(View.GONE);
                holder.tvContentAdd.setText(replyList.get(position).getReply_content());
                holder.tvZhuiping.setText(replyList.get(position).getReply_time());
            } else if ("1".equals(replyList.get(position).getReply_type())) {
                holder.tvClassReply.setVisibility(View.VISIBLE);
                holder.llZhuiping.setVisibility(View.GONE);
                holder.tvContentAdd.setVisibility(View.GONE);
                holder.tvClassReplyContent.setText("" + replyList.get(position).getReply_content());
                holder.tvClassReplyTime.setText("" + replyList.get(position).getReply_time());
            }
            return view;
        }


    }    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder = null;

        if (view == null) {
            view = View.inflate(context, R.layout.pingjia_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final EvaluateBean evaluateBean = mlist.get(position);
        holder.tvPingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setGoToDetail(position);
            }
        });

        Glide.with(context).load(Internet.BASE_URL + evaluateBean.getSend_photo()).centerCrop().into(holder.ivUsericon);
        holder.tvUsername.setText(evaluateBean.getSend_name());
        holder.tvTime.setText(evaluateBean.getInteract_time());
        holder.tvContent.setText(evaluateBean.getContents());
        holder.tvPingjia.setText("评论("+evaluateBean.getReplyInfoCount()+")条");
        List<EvaluateBean.ReplyInfoBean> replyInfo = evaluateBean.getReplyInfo();

        if (null != evaluateBean.getReplyInfo()) {
            for (int i = 0; i < replyInfo.size(); i++) {
                String reply_type = replyInfo.get(i).getReply_type();
                if ("2".equals(reply_type)) {
                    replyInfo.remove(i);
                }
            }
            MyAdapter adapter = new MyAdapter((ArrayList<EvaluateBean.ReplyInfoBean>) replyInfo);
            holder.listView.setAdapter(adapter);
        }
//        holder.tvClassReply.setText("学堂回复："+evaluateBean.getReplyInfo().get(0).getReply_content());

        return view;
    }




}
