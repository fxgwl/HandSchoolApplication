package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.PublishEvaluateActivity;
import com.example.handschoolapplication.activity.ReturnMoneyActivity;
import com.example.handschoolapplication.bean.OrderBean;
import com.example.handschoolapplication.utils.Internet;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/16.
 */

public class OrderAdapter extends BaseAdapter {

    private Context context;
    private List<OrderBean.DataBean> mlist;
    private int size = 0;

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
        Glide.with(context)
                .load(Internet.BASE_URL + dataBean.getClass_photo())
                .centerCrop()
                .error(R.drawable.kecheng)
                .into(holder.ivCourse);
        holder.tvCourse.setText(dataBean.getClass_name());
        holder.tvPrice.setText("价格：¥" + dataBean.getOrder_money());
        holder.tvRealmoney.setText("¥" + dataBean.getOrder_money());
        holder.tvNum.setText("x" + dataBean.getCourse_num());
//        0待付款 1待确认 2待评价 3评价后 4退款 5取消订单 6已退款
        switch (dataBean.getOrder_state()) {
            case "0":
                holder.llWaitpay.setVisibility(View.VISIBLE);
                holder.llPingjia.setVisibility(View.GONE);
                holder.llTuikuan.setVisibility(View.GONE);
                holder.llWaitconfig.setVisibility(View.GONE);
                holder.llCancle.setVisibility(View.GONE);
                holder.llYipingjia.setVisibility(View.GONE);
                holder.llYituikuan.setVisibility(View.GONE);

                break;
            case "1":
                holder.llWaitpay.setVisibility(View.GONE);
                holder.llPingjia.setVisibility(View.GONE);
                holder.llTuikuan.setVisibility(View.GONE);
                holder.llWaitconfig.setVisibility(View.VISIBLE);
                holder.llCancle.setVisibility(View.GONE);
                holder.llYipingjia.setVisibility(View.GONE);
                holder.llYituikuan.setVisibility(View.GONE);
                break;
            case "2":
                holder.llWaitpay.setVisibility(View.GONE);
                holder.llPingjia.setVisibility(View.VISIBLE);
                holder.llTuikuan.setVisibility(View.GONE);
                holder.llWaitconfig.setVisibility(View.GONE);
                holder.llCancle.setVisibility(View.GONE);
                holder.llYipingjia.setVisibility(View.GONE);
                holder.llYituikuan.setVisibility(View.GONE);
                break;
            case "3":
                holder.llWaitpay.setVisibility(View.GONE);
                holder.llPingjia.setVisibility(View.GONE);
                holder.llTuikuan.setVisibility(View.GONE);
                holder.llWaitconfig.setVisibility(View.GONE);
                holder.llCancle.setVisibility(View.GONE);
                holder.llYipingjia.setVisibility(View.VISIBLE);
                holder.llYituikuan.setVisibility(View.GONE);
                break;
            case "4":
                holder.llWaitpay.setVisibility(View.GONE);
                holder.llPingjia.setVisibility(View.GONE);
                holder.llTuikuan.setVisibility(View.VISIBLE);
                holder.llWaitconfig.setVisibility(View.GONE);
                holder.llCancle.setVisibility(View.GONE);
                holder.llYipingjia.setVisibility(View.GONE);
                holder.llYituikuan.setVisibility(View.GONE);
                break;
            case "5":
                holder.llWaitpay.setVisibility(View.GONE);
                holder.llPingjia.setVisibility(View.GONE);
                holder.llTuikuan.setVisibility(View.GONE);
                holder.llWaitconfig.setVisibility(View.GONE);
                holder.llCancle.setVisibility(View.VISIBLE);
                holder.llYipingjia.setVisibility(View.GONE);
                holder.llYituikuan.setVisibility(View.GONE);
                break;
            case "6":
                holder.llWaitpay.setVisibility(View.GONE);
                holder.llPingjia.setVisibility(View.GONE);
                holder.llTuikuan.setVisibility(View.GONE);
                holder.llWaitconfig.setVisibility(View.GONE);
                holder.llCancle.setVisibility(View.GONE);
                holder.llYipingjia.setVisibility(View.GONE);
                holder.llYituikuan.setVisibility(View.VISIBLE);
                break;
        }
        holder.tvMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PublishEvaluateActivity.class);
                intent.putExtra("order_id", dataBean.getOrder_id());
                context.startActivity(intent);
            }
        });
        holder.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils.post()
                        .url(Internet.CLOSEORDER)
                        .addParams("order_id", dataBean.getOrder_id())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("aaa",
                                        "(OrderAdapter.java:164)" + response);
                                if (response.contains("成功")) {
                                    mlist.remove(position);
                                    notifyDataSetChanged();
                                }
                            }
                        });

            }
        });
        holder.tvRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReturnMoneyActivity.class);
                intent.putExtra("ordernum", dataBean.getOrder_id());
                intent.putExtra("courseid", dataBean.getCourse_id());
                intent.putExtra("ivcourse", dataBean.getClass_photo());
                intent.putExtra("coursename", dataBean.getClass_name());
                intent.putExtra("money", dataBean.getOrder_money());
                intent.putExtra("coursenum", dataBean.getCourse_num());
                intent.putExtra("tuimoney", dataBean.getOrder_money());
                context.startActivity(intent);
            }
        });
        return view;
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
        @BindView(R.id.ll_tuikuan)
        LinearLayout llTuikuan;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
