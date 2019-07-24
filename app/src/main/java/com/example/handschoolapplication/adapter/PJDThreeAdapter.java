package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.ReplyInfoThree;
import com.example.handschoolapplication.utils.Internet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/8/24.
 */

public class PJDThreeAdapter extends BaseAdapter {

    private Context context;
    private List<ReplyInfoThree.DataBean> mlist;

    public PJDThreeAdapter(Context context, List<ReplyInfoThree.DataBean> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {
        if (mlist != null) return mlist.size();
        return 0;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.pjd_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context)
                .load(Internet.BASE_URL + mlist.get(position).getHead_photo())
                .centerCrop()
                .into(holder.ivHfhead);
        if (null!=mlist.get(position).getCritic_type()&&mlist.get(position).getCritic_type().equals("1")){
            holder.tvBeReply.setVisibility(View.VISIBLE);
            holder.tvHuifu.setVisibility(View.VISIBLE);
            holder.tvBeReply.setText(mlist.get(position).getUserInfo().getUser_name());
            holder.tvReplycontent.setText(mlist.get(position).getRecords());

        }else {
            holder.tvBeReply.setVisibility(View.GONE);
            holder.tvHuifu.setVisibility(View.GONE);
            holder.tvReplycontent.setText(mlist.get(position).getRecords());
        }
        holder.tvHfname.setText(mlist.get(position).getUser_name());
        holder.tvTime.setText(mlist.get(position).getCreate_time());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_hfhead)
        CircleImageView ivHfhead;
        @BindView(R.id.tv_hfname)
        TextView tvHfname;
        @BindView(R.id.iv_dj)
        ImageView ivDj;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_be_reply)
        TextView tvBeReply;
        @BindView(R.id.tv_huifu)
        TextView tvHuifu;
        @BindView(R.id.tv_replycontent)
        TextView tvReplycontent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
