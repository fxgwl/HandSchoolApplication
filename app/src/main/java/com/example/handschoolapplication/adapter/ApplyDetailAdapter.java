package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.ApplyDetail;
import com.example.handschoolapplication.utils.Internet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/2.
 */

public class ApplyDetailAdapter extends BaseAdapter {

    private Context context;
    private List<ApplyDetail.DataBean> mList;
    private int size = 0;


    public ApplyDetailAdapter(Context context, List<ApplyDetail.DataBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {

        if (mList != null) {
            size = mList.size();
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
            view = View.inflate(context, R.layout.item_apply_detail_lv, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ApplyDetail.DataBean applydetail = mList.get(position);
        Glide.with(context)
                .load(Internet.BASE_URL + applydetail.getUser_head())
                .centerCrop()
                .into(holder.civUsericon);
        holder.tvUsername.setText(applydetail.getUser_name());
        holder.tvTime.setText(applydetail.getCreated());
        holder.tvCoursenum.setText(applydetail.getStudy_class() + "节课 【共" + applydetail.getAll_class() + "节课】");
        holder.num.setText((position + 1) + "");
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.civ_usericon)
        CircleImageView civUsericon;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_coursenum)
        TextView tvCoursenum;
        @BindView(R.id.ll_send_message)
        LinearLayout llSendMessage;
        @BindView(R.id.num)
        TextView num;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
