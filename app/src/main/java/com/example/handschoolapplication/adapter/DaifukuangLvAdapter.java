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
import com.example.handschoolapplication.bean.ClassDealManagerBean;
import com.example.handschoolapplication.utils.Internet;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by axehome on 2017/8/24.
 */

public class DaifukuangLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<ClassDealManagerBean> mList;
    private int size = 0;
    private String type;

    public DaifukuangLvAdapter(Context mContext, List<ClassDealManagerBean> mList, String type) {
        this.mContext = mContext;
        this.mList = mList;
        this.type = type;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_daifukuang, null);
            holder = new ViewHolder();
            holder.mTvOrderId = ((TextView) convertView.findViewById(R.id.tv_itemdaifukuang_orderid));
            holder.tvTime = ((TextView) convertView.findViewById(R.id.tv_time));
            holder.mTvNickName = ((TextView) convertView.findViewById(R.id.tv_daifukuang_nickname));
            holder.mTvClassName = ((TextView) convertView.findViewById(R.id.tv_itemdaifukuang_classname));
            holder.mTvClassprice = ((TextView) convertView.findViewById(R.id.tv_itemdaifukuang_classprice));
            holder.mTvTotalprice = ((TextView) convertView.findViewById(R.id.tv_itemdaifukuang_totalprice));
            holder.mTvNum = ((TextView) convertView.findViewById(R.id.tv_itemdaifukuang_num));
            holder.mTvContemt = ((TextView) convertView.findViewById(R.id.tv_itemdaifukuang_contemt));
            holder.civHeadImage = ((CircleImageView) convertView.findViewById(R.id.civ_itendaifukuang_usericon));
            holder.ivClasslogo = ((ImageView) convertView.findViewById(R.id.iv_itemdaifukuang_classlogo));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ClassDealManagerBean classDealManagerBean = mList.get(position);
        if (null != classDealManagerBean.getUserInfo()) {
            Glide.with(mContext).load(Internet.BASE_URL + classDealManagerBean.getUserInfo().getHead_photo()).centerCrop().into(holder.civHeadImage);
            holder.mTvNickName.setText(classDealManagerBean.getUserInfo().getUser_name());
        }
        holder.mTvOrderId.setText(classDealManagerBean.getOrder_id());//订单编号

//        String class_photo = classDealManagerBean.getClass_photo();
//        String classPhoto = class_photo.contains(",") ? class_photo.split(",")[0] : "";
        String picture_one = classDealManagerBean.getCourseInfo().getPicture_one();
        Glide.with(mContext).load(Internet.BASE_URL + picture_one).centerCrop().into(holder.ivClasslogo);
        holder.mTvClassName.setText(classDealManagerBean.getClass_name());
        holder.mTvClassprice.setText("价格： ¥" + classDealManagerBean.getOrder_money());
        holder.tvTime.setText(classDealManagerBean.getOrdre_time());
        double unitPrice = Double.parseDouble(classDealManagerBean.getOrder_money());
        holder.mTvNum.setText("数量：x" + classDealManagerBean.getCourse_num());
        double num = Double.parseDouble(classDealManagerBean.getCourse_num());
        double totalPrice = unitPrice * num;
        holder.mTvTotalprice.setText("总价：" + new DecimalFormat("0.00").format(totalPrice) + "元");
        switch (classDealManagerBean.getOrder_state()) {
            case "0"://0待付款
                holder.mTvContemt.setText("等待买家付款");
                break;
            case "1"://1待确认
                holder.mTvContemt.setText("等待学习确认");
                break;
            case "2"://2待评价
                holder.mTvContemt.setText("等待买家评价");
                break;
            case "3"://3评价后
                holder.mTvContemt.setText("成功的订单");
                break;
            case "5":// 5取消订单
                holder.mTvContemt.setText("取消的订单");
                break;
        }
        return convertView;
    }


    class ViewHolder {
        TextView mTvOrderId, mTvNickName, mTvClassName, mTvClassprice, mTvTotalprice,
                mTvNum, mTvContemt,tvTime;
        ImageView ivClasslogo;
        CircleImageView civHeadImage;

    }
}
