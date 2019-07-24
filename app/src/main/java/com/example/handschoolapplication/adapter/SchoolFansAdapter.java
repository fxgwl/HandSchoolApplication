package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.SchoolFans;
import com.example.handschoolapplication.utils.Internet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/16.
 */

public class SchoolFansAdapter extends BaseAdapter {

    private Context context;
    private List<SchoolFans.DataBean> mList;
    private int size = 0;


    public SchoolFansAdapter(Context context, List<SchoolFans.DataBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {

        if (mList != null && mList.size() > 0) {
            size = mList.size();
        }
        return size;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_fans_school_lv, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        SchoolFans.DataBean.UserInfoBean userInfo = mList.get(position).getUserInfo();
        SchoolFans.DataBean dataBean = mList.get(position);
        Glide.with(context).load(Internet.BASE_URL+userInfo.getHead_photo()).centerCrop().into(holder.ivUsericon);
        holder.leveUser.setText("LV."+userInfo.getUser_dengji());
        /*switch (userInfo.getUser_dengji()){
            case "0":
                holder.ivJewel1.setVisibility(View.GONE);
                holder.ivJewel2.setVisibility(View.GONE);
                holder.ivJewel3.setVisibility(View.GONE);
                holder.ivJewel4.setVisibility(View.GONE);
                holder.ivJewel5.setVisibility(View.GONE);
                break;
            case "1":
                holder.ivJewel1.setVisibility(View.VISIBLE);
                holder.ivJewel2.setVisibility(View.GONE);
                holder.ivJewel3.setVisibility(View.GONE);
                holder.ivJewel4.setVisibility(View.GONE);
                holder.ivJewel5.setVisibility(View.GONE);
                break;
            case "2":
                holder.ivJewel1.setVisibility(View.VISIBLE);
                holder.ivJewel2.setVisibility(View.VISIBLE);
                holder.ivJewel3.setVisibility(View.GONE);
                holder.ivJewel4.setVisibility(View.GONE);
                holder.ivJewel5.setVisibility(View.GONE);
                break;
            case "3":
                holder.ivJewel1.setVisibility(View.VISIBLE);
                holder.ivJewel2.setVisibility(View.VISIBLE);
                holder.ivJewel3.setVisibility(View.VISIBLE);
                holder.ivJewel4.setVisibility(View.GONE);
                holder.ivJewel5.setVisibility(View.GONE);
                break;
            case "4":
                holder.ivJewel1.setVisibility(View.VISIBLE);
                holder.ivJewel2.setVisibility(View.VISIBLE);
                holder.ivJewel3.setVisibility(View.VISIBLE);
                holder.ivJewel4.setVisibility(View.VISIBLE);
                holder.ivJewel5.setVisibility(View.GONE);
                break;
            case "5":
                holder.ivJewel1.setVisibility(View.VISIBLE);
                holder.ivJewel2.setVisibility(View.VISIBLE);
                holder.ivJewel3.setVisibility(View.VISIBLE);
                holder.ivJewel4.setVisibility(View.VISIBLE);
                holder.ivJewel5.setVisibility(View.VISIBLE);
                break;

        }*/

        switch (userInfo.getUser_type()){
            case "0":
                holder.tvUsername.setText(userInfo.getUser_name());
                break;
            case "1":
                holder.tvUsername.setText(dataBean.getSchool_name());
                break;
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_usericon)
        ImageView ivUsericon;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.iv_jewel1)
        ImageView ivJewel1;
        @BindView(R.id.iv_jewel2)
        ImageView ivJewel2;
        @BindView(R.id.iv_jewel3)
        ImageView ivJewel3;
        @BindView(R.id.iv_jewel4)
        ImageView ivJewel4;
        @BindView(R.id.iv_jewel5)
        ImageView ivJewel5;
        @BindView(R.id.leve_user)
                TextView leveUser;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
