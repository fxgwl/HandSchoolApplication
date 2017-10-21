package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.RefundDetailActivity;
import com.example.handschoolapplication.bean.ClassDealManagerBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by axehome on 2017/8/24.
 */

public class RefundManagerLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<ClassDealManagerBean> mList;
    private int size = 0;

    public RefundManagerLvAdapter(Context mContext, List<ClassDealManagerBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        if (mList != null)
            size = mList.size();
        return size;
    }

    public void setList(List<ClassDealManagerBean> list){
        this.mList = list;
        notifyDataSetChanged();
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_refundmanager, null);
            holder = new ViewHolder();
            holder.mTvOrderId = ((TextView) convertView.findViewById(R.id.tv_itemrefundmanager_orderid));
            holder.mTvNickName = ((TextView) convertView.findViewById(R.id.tv_itemrefundmanager_nickname));
            holder.mTvClassName = ((TextView) convertView.findViewById(R.id.tv_itemrefundmanager_classname));
            holder.mTvClassprice = ((TextView) convertView.findViewById(R.id.tv_itemrefundmanager_classprice));
            holder.mTvNum = ((TextView) convertView.findViewById(R.id.tv_itemrefundmanager_num));
            holder.mTvContemt = ((TextView) convertView.findViewById(R.id.tv_itemrefundmanager_contemt));
            holder.civHeadImage = ((CircleImageView) convertView.findViewById(R.id.civ_itemrefundmanager_usericon));
            holder.ivClasslogo = ((ImageView) convertView.findViewById(R.id.iv_itemrefundmanager_classlogo));
            holder.btnDetail = ((Button) convertView.findViewById(R.id.btn_itemrefundmanager_detail));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //查看详情
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, RefundDetailActivity.class));
            }
        });
        return convertView;
    }


    class ViewHolder {
        TextView mTvOrderId, mTvNickName, mTvClassName, mTvClassprice, mTvNum, mTvContemt;
        ImageView ivClasslogo;
        CircleImageView civHeadImage;
        Button btnDetail;

    }
}
