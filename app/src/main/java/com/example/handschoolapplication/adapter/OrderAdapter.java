package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.ClassActivity;
import com.example.handschoolapplication.activity.OrderDetailActivity;
import com.example.handschoolapplication.activity.RefundDetailActivity;
import com.example.handschoolapplication.bean.OrderBean;
import com.example.handschoolapplication.utils.Internet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/16.
 */

public class OrderAdapter extends BaseAdapter {

    private Context context;
    private List<OrderBean.DataBean> mlist;
    private int size = 0;
    private OnMakeOrderListener listener;

    public OrderAdapter(Context context, List<OrderBean.DataBean> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {

        if (mlist != null) {
            size = mlist.size();
        }
        return size;
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder = null;

        if (view == null) {
            view = View.inflate(context, R.layout.item_orderbean_lv, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final OrderBean.DataBean dataBean = mlist.get(position);
        holder.tvOrganization.setText(dataBean.getSchool_name());
        String class_photo = dataBean.getCourseInfo().getPicture_one();
        Glide.with(context)
                .load(Internet.BASE_URL + class_photo)
                .centerCrop()
                .error(R.drawable.kecheng)
                .into(holder.ivCourse);
        holder.tvCourse.setText(dataBean.getClass_name());
        holder.tvPrice.setText("价格：¥" + dataBean.getOrder_money());
        holder.tvRealmoney.setText("¥" + dataBean.getOrder_money());
        holder.tvNum.setText("x" + dataBean.getCourse_num());
        holder.tvStudentName.setText(dataBean.getStudent_name());
        holder.tvStudentSex.setText(dataBean.getStudent_sex());
        String pay_type = dataBean.getPay_type();

//        0待付款 1待确认 2待评价 3评价后 4退款中 5取消订单 6已退款
        switch (dataBean.getOrder_state()) {
            case "0":
                //待付款
                holder.llWaitpay.setVisibility(View.VISIBLE);
                holder.llPingjia.setVisibility(View.GONE);
                holder.llTuikuan.setVisibility(View.GONE);
                holder.llWaitconfig.setVisibility(View.GONE);
                holder.llCancle.setVisibility(View.GONE);
                holder.llYipingjia.setVisibility(View.GONE);
                holder.llYituikuan.setVisibility(View.GONE);
                break;
            case "1":
                //待确认
                holder.llWaitpay.setVisibility(View.GONE);
                holder.llPingjia.setVisibility(View.GONE);
                holder.llTuikuan.setVisibility(View.GONE);
                holder.llWaitconfig.setVisibility(View.VISIBLE);
                holder.llCancle.setVisibility(View.GONE);
                holder.llYipingjia.setVisibility(View.GONE);
                holder.llYituikuan.setVisibility(View.GONE);
                break;
            case "2":
                //去评价
                holder.llWaitpay.setVisibility(View.GONE);
                holder.llPingjia.setVisibility(View.VISIBLE);
                holder.llTuikuan.setVisibility(View.GONE);
                holder.llWaitconfig.setVisibility(View.GONE);
                holder.llCancle.setVisibility(View.GONE);
                holder.llYipingjia.setVisibility(View.GONE);
                holder.llYituikuan.setVisibility(View.GONE);
                break;
            case "3":
                //已评价
                holder.llWaitpay.setVisibility(View.GONE);
                holder.llPingjia.setVisibility(View.GONE);
                holder.llTuikuan.setVisibility(View.GONE);
                holder.llWaitconfig.setVisibility(View.GONE);
                holder.llCancle.setVisibility(View.GONE);
                holder.llYipingjia.setVisibility(View.VISIBLE);
                holder.llYituikuan.setVisibility(View.GONE);
                break;
            case "4":
                //退款中
                holder.llWaitpay.setVisibility(View.GONE);
                holder.llPingjia.setVisibility(View.GONE);
                holder.llTuikuan.setVisibility(View.VISIBLE);
                holder.llWaitconfig.setVisibility(View.GONE);
                holder.llCancle.setVisibility(View.GONE);
                holder.llYipingjia.setVisibility(View.GONE);
                holder.llYituikuan.setVisibility(View.GONE);
                break;
            case "5":
                //已取消
                holder.llWaitpay.setVisibility(View.GONE);
                holder.llPingjia.setVisibility(View.GONE);
                holder.llTuikuan.setVisibility(View.GONE);
                holder.llWaitconfig.setVisibility(View.GONE);
                holder.llCancle.setVisibility(View.VISIBLE);
                holder.llYipingjia.setVisibility(View.GONE);
                holder.llYituikuan.setVisibility(View.GONE);
                break;
            case "6":
                //已退款
                holder.llWaitpay.setVisibility(View.GONE);
                holder.llPingjia.setVisibility(View.GONE);
                holder.llTuikuan.setVisibility(View.GONE);
                holder.llWaitconfig.setVisibility(View.GONE);
                holder.llCancle.setVisibility(View.GONE);
                holder.llYipingjia.setVisibility(View.GONE);
                holder.llYituikuan.setVisibility(View.VISIBLE);
                break;
        }

        if (!TextUtils.isEmpty(pay_type) && pay_type.equals("2")) {
            holder.tvRefund.setVisibility(View.INVISIBLE);
        }
        holder.tvMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//去评价
                listener.setEvaluate(position);
            }
        });
        holder.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//取消订单
                listener.setOnCancelOrder(position);
            }
        });
        holder.tvRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//退款
                listener.setOnRefund(position);
            }
        });
        holder.llClassInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到学堂主页
                Intent intent2 = new Intent(context, ClassActivity.class);
                intent2.putExtra("school_id", dataBean.getSchool_id());
                context.startActivity(intent2);
            }
        });


        holder.rlCourseInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataBean.getOrder_state().equals("4")) {
                    Intent intent = new Intent(context, RefundDetailActivity.class);
                    intent.putExtra("order_id", dataBean.getOrder_id());
                    intent.putExtra("courseid", dataBean.getCourse_id());
                    intent.putExtra("ivcourse", dataBean.getClass_photo());
                    intent.putExtra("coursename", dataBean.getClass_name());
                    intent.putExtra("money", dataBean.getOrder_money());
                    intent.putExtra("coursenum", dataBean.getCourse_num());
                    intent.putExtra("tuimoney", dataBean.getOrder_money());
                    intent.putExtra("course_id", dataBean.getCourse_id());
                    intent.putExtra("schooluid", dataBean.getUser_id());
                    context.startActivity(intent);
                } else {
                    Log.e("aaa",
                            "(OrderAdapter.java:211)orderId === " + dataBean.getOrder_id());
                    context.startActivity(new Intent(context, OrderDetailActivity.class).putExtra("order_id", dataBean.getOrder_id()));
                }
            }
        });
        holder.tvVerify.setOnClickListener(new View.OnClickListener() {//学习确认
            @Override
            public void onClick(View v) {
                listener.setOnVertify(position);
            }
        });
        holder.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnPayOrder(position);
            }
        });

        return view;
    }

    public void setOnMakeOrderListener(OnMakeOrderListener listener) {
        this.listener = listener;
    }

    public interface OnMakeOrderListener {
        //取消订单的方法
        void setOnCancelOrder(int position);

        //确认支付的方法
        void setOnPayOrder(int position);

        //退款的方法
        void setOnRefund(int position);

        //学习确认的方法
        void setOnVertify(int position);

        //评价的方法
        void setEvaluate(int position);
    }

    static class ViewHolder {
        @BindView(R.id.ll_cancle)
        LinearLayout llCancle;
        @BindView(R.id.ll_yituikuan)
        LinearLayout llYituikuan;
        @BindView(R.id.ll_yipingjia)
        LinearLayout llYipingjia;
        @BindView(R.id.tv_organization)
        TextView tvOrganization;
        @BindView(R.id.iv_course)
        ImageView ivCourse;
        @BindView(R.id.tv_course)
        TextView tvCourse;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.ll_course)
        LinearLayout llCourse;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_youhui)
        TextView tvYouhui;
        @BindView(R.id.tv_realmoney)
        TextView tvRealmoney;
        @BindView(R.id.tv_make)
        TextView tvMake;
        @BindView(R.id.ll_pingjia)
        LinearLayout llPingjia;
        @BindView(R.id.tv_cancel)
        TextView tvCancel;
        @BindView(R.id.tv_pay)
        TextView tvPay;
        @BindView(R.id.ll_waitpay)
        LinearLayout llWaitpay;
        @BindView(R.id.tv_refund)
        TextView tvRefund;
        @BindView(R.id.tv_verify)
        TextView tvVerify;
        @BindView(R.id.ll_waitconfig)
        LinearLayout llWaitconfig;
        @BindView(R.id.tv_refund2)
        TextView tvRefund2;
        @BindView(R.id.tv_student_name)
        TextView tvStudentName;
        @BindView(R.id.ll_tuikuan)
        LinearLayout llTuikuan;
        @BindView(R.id.ll_class_info)
        LinearLayout llClassInfo;
        @BindView(R.id.rl_course_info)
        RelativeLayout rlCourseInfo;
        @BindView(R.id.tv_student_sex)
        TextView tvStudentSex;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
