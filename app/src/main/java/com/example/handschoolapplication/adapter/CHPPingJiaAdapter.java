package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.EvaluateBean;
import com.example.handschoolapplication.utils.Internet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/24.
 */

public class CHPPingJiaAdapter extends BaseAdapter {
    private Context context;
    private List<EvaluateBean> mlist;
    private int size;

    public CHPPingJiaAdapter(Context context, List<EvaluateBean> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {

        if (mlist != null&&mlist.size()>0) {
            size = 1;
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
            view = View.inflate(context, R.layout.item_chp_pingjia, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final EvaluateBean evaluateBean = mlist.get(position);

        Glide.with(context).load(Internet.BASE_URL + evaluateBean.getSend_photo()).centerCrop().into(holder.ivUsericon);
        holder.tvUsername.setText(evaluateBean.getSend_name());
        holder.tvTime.setText(evaluateBean.getInteract_time());
        holder.tvContent.setText(evaluateBean.getContents());

//        holder.tvClassReply.setText("学堂回复："+evaluateBean.getReplyInfo().get(0).getReply_content());

        return view;
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
                holder.textView.setVisibility(View.VISIBLE);
                holder.tvContentAdd.setVisibility(View.VISIBLE);
                holder.tvClassReply.setVisibility(View.GONE);
                holder.tvContentAdd.setText(replyList.get(position).getReply_content());
            } else if ("1".equals(replyList.get(position).getReply_type())) {
                holder.tvClassReply.setVisibility(View.VISIBLE);
                holder.textView.setVisibility(View.GONE);
                holder.tvContentAdd.setVisibility(View.GONE);
                holder.tvClassReply.setText("机构回复：" + replyList.get(position).getReply_content());
            }
            return view;
        }

        class ViewHolder {
            @BindView(R.id.tv_class_reply)
            TextView tvClassReply;
            @BindView(R.id.tv_content_add)
            TextView tvContentAdd;
            @BindView(R.id.tv_text)
            TextView textView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
