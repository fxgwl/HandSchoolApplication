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

import java.text.DecimalFormat;
import java.util.List;

import static com.example.handschoolapplication.R.id.iv_itemrefunddetail_classlogo;

/**
 * Created by axehome on 2017/8/24.
 */

public class RefundDetailLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<OrderInfoBean> mList;
    private int size = 0;

    public RefundDetailLvAdapter(Context mContext, List<OrderInfoBean> mList) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_refunddetail, null);
            holder = new ViewHolder();
            holder.mTvClassId = ((TextView) convertView.findViewById(R.id.tv_itemrefunddetail_classid));
            holder.mTvClassName = ((TextView) convertView.findViewById(R.id.tv_itemrefunddetail_classname));
            holder.mTvClassprice = ((TextView) convertView.findViewById(R.id.tv_itemrefunddetail_classprice));
            holder.mTvNum = ((TextView) convertView.findViewById(R.id.tv_itemrefunddetail_num));
            holder.mTvRefundPrice = ((TextView) convertView.findViewById(R.id.tv_itemrefunddetail_refundprice));
            holder.ivClasslogo = ((ImageView) convertView.findViewById(iv_itemrefunddetail_classlogo));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderInfoBean orderInfoBean = mList.get(position);
        String picture_one = orderInfoBean.getData().getCourseInfo().getPicture_one();
        Glide.with(mContext).load(Internet.BASE_URL+picture_one).centerCrop().into(holder.ivClasslogo);
        double money = Double.parseDouble(orderInfoBean.getData().getOrder_money());
        double num = Double.parseDouble(orderInfoBean.getData().getCourse_num());
        double totalMon = money * num;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String format = decimalFormat.format(totalMon);
        holder.mTvClassId.setText(orderInfoBean.getData().getCourse_id());
        holder.mTvClassName.setText(orderInfoBean.getData().getClass_name());
        holder.mTvClassprice.setText("价格：¥" + orderInfoBean.getData().getOrder_money());
        holder.mTvNum.setText("数量：x" + orderInfoBean.getData().getCourse_num());
        holder.mTvRefundPrice.setText(format + "元");
        return convertView;
    }

    class ViewHolder {
        TextView mTvClassId, mTvClassName, mTvClassprice, mTvNum, mTvRefundPrice;
        ImageView ivClasslogo;


    }
}
