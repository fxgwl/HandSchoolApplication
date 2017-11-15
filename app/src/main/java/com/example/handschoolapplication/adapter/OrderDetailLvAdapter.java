package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.OrderInfoBean;
import com.example.handschoolapplication.utils.Internet;

import java.util.List;

/**
 * Created by axehome on 2017/8/24.
 */

public class OrderDetailLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<OrderInfoBean> mList;
    private int size = 0;

    public OrderDetailLvAdapter(Context mContext, List<OrderInfoBean> mList) {
        this.mContext = mContext;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_orderdetail, null);
            holder = new ViewHolder();

            holder.mTvClassName = ((TextView) convertView.findViewById(R.id.tv_itemorderdetail_classname));
            holder.mTvClassprice = ((TextView) convertView.findViewById(R.id.tv_itemorderdetail_classprice));
            holder.mTvNum = ((TextView) convertView.findViewById(R.id.tv_itemorderdetail_num));
            holder.ivClasslogo = ((ImageView) convertView.findViewById(R.id.iv_itemorderdetail_classlogo));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderInfoBean dataBean = mList.get(position);
        String class_photo = dataBean.getClass_photo();
        String photo = "";

        if (class_photo.contains(",")){
            String[] split = class_photo.split(",");
            photo = split[0];
        }else {
            photo = class_photo;
        }
        Glide.with(mContext)
                .load(Internet.BASE_URL + photo)
                .centerCrop()
                .into(holder.ivClasslogo);
        holder.mTvClassName.setText(dataBean.getClass_name());
        holder.mTvClassprice.setText(dataBean.getOrder_money());
        holder.mTvNum.setText("数量：" + dataBean.getCourse_num());

        return convertView;
    }


    class ViewHolder {
        TextView mTvClassName, mTvClassprice, mTvNum;
        ImageView ivClasslogo;


    }
}
