package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.RefundDetailActivity;
import com.example.handschoolapplication.bean.ClassDealManagerBean;
import com.example.handschoolapplication.utils.Internet;

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
            holder.mTvOrderId = ((TextView) convertView.findViewById(R.id.tv_itemrefundmanager_orderid));//订单编号
            holder.mTvNickName = ((TextView) convertView.findViewById(R.id.tv_itemrefundmanager_nickname));//用户昵称
            holder.mTvClassName = ((TextView) convertView.findViewById(R.id.tv_itemrefundmanager_classname));//课程名称
            holder.mTvClassprice = ((TextView) convertView.findViewById(R.id.tv_itemrefundmanager_classprice));//课程价格
            holder.mTvNum = ((TextView) convertView.findViewById(R.id.tv_itemrefundmanager_num));//课程数量
            holder.mTvContemt = ((TextView) convertView.findViewById(R.id.tv_itemrefundmanager_contemt));//退款状态
            holder.civHeadImage = ((CircleImageView) convertView.findViewById(R.id.civ_itemrefundmanager_usericon));//用户头像
            holder.ivClasslogo = ((ImageView) convertView.findViewById(R.id.iv_itemrefundmanager_classlogo));//课程图片
            holder.btnDetail = ((Button) convertView.findViewById(R.id.btn_itemrefundmanager_detail));
            holder.llUser = (LinearLayout) convertView.findViewById(R.id.ll_user);
            holder.llCourse = (LinearLayout) convertView.findViewById(R.id.ll_course);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ClassDealManagerBean classDealManagerBean = mList.get(position);
        final String order_id = classDealManagerBean.getOrder_id();
        holder.mTvOrderId.setText(order_id);
        holder.mTvNickName.setText(classDealManagerBean.getUserInfo().getUser_name());
        holder.mTvClassName.setText(classDealManagerBean.getClass_name());
        holder.mTvClassprice.setText("价格：¥"+classDealManagerBean.getOrder_money());
        holder.mTvNum.setText("数量：x"+classDealManagerBean.getCourse_num());
        if ("4".equals(classDealManagerBean.getOrder_type())){
            holder.mTvContemt.setText("退款中");
        }else {
            holder.mTvContemt.setText("已退款");
        }
        Glide.with(mContext).load(Internet.BASE_URL+classDealManagerBean.getUserInfo().getHead_photo()).centerCrop().into(holder.civHeadImage);
        Glide.with(mContext).load(Internet.BASE_URL+classDealManagerBean.getClass_photo()).centerCrop().into(holder.ivClasslogo);
        //查看详情
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, RefundDetailActivity.class).putExtra("order_id",order_id));
            }
        });

        //
        holder.llCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.llUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }


    class ViewHolder {
        TextView mTvOrderId, mTvNickName, mTvClassName, mTvClassprice, mTvNum, mTvContemt;
        ImageView ivClasslogo;
        CircleImageView civHeadImage;
        LinearLayout llUser,llCourse;
        Button btnDetail;

    }
}
