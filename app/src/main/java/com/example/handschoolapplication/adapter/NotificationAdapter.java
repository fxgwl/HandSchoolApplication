package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.NotificationBean;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;


/**
 * Created by Administrator on 2017/9/2.
 */

public class NotificationAdapter extends BaseAdapter {

    public GetDiscountPagerListener listener;
    private Context context;
    private List<NotificationBean.DataBean> mList;
    private int size = 0;
    private String userId, userPhone;


    public NotificationAdapter(Context context, List<NotificationBean.DataBean> mList) {
        this.context = context;
        this.mList = mList;
        userId = (String) SPUtils.get(context, "userId", "");
        userPhone = (String) SPUtils.get(context, "user_phone", "");
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_notification_lv, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final NotificationBean.DataBean notificationBean = mList.get(position);

        if (notificationBean.getCoupons_type() != null && notificationBean.getCoupons_type().equals("1")) {
            holder.rlLan.setBackgroundResource(R.drawable.yhqhuangsebj);
        } else {
            holder.rlLan.setBackgroundResource(R.drawable.youhuiquan);
        }

        if (notificationBean.getInform_type().equals("1")) {
            holder.rlDisImg.setVisibility(View.VISIBLE);
            String inform_type = notificationBean.getInform_state();
            holder.tvContent.setClickable(true);
            if ("0".equals(inform_type)) {
//            notificationBean.setGet(true);
                holder.tvContent.setTextColor(Color.parseColor("#da2525"));
                holder.tvContent.setEnabled(true);
                holder.tvReceive.setText("立即领取");
            } else {
                holder.tvContent.setTextColor(Color.parseColor("#e6e6e6"));
                holder.tvContent.setEnabled(false);
                holder.tvReceive.setText("立即使用");
            }

        } else {
            holder.rlDisImg.setVisibility(View.GONE);
            holder.tvContent.setTextColor(Color.parseColor("#323232"));
            holder.tvContent.setEnabled(false);
        }
        holder.tvTime.setText(notificationBean.getMessage_time());//通知时间
        holder.tvTitle.setText(notificationBean.getInform_name());//通知标题
        holder.tvContent.setText(notificationBean.getInform_content());//通知内容
        if (notificationBean.getInform_content().contains("金币")) {
            holder.ivSignGold.setVisibility(View.VISIBLE);
        } else {
            holder.ivSignGold.setVisibility(View.GONE);
        }
        if (notificationBean.getCoupons_name() != null || !TextUtils.isEmpty(notificationBean.getCoupons_name())) {
            holder.tvDiscountName.setText(notificationBean.getCoupons_name() + "");
        }
        if (notificationBean.getMax_money() != null || !TextUtils.isEmpty(notificationBean.getMax_money())) {
            holder.tvRequirement.setText("满" + notificationBean.getMax_money() + "元可使用");
        }

        if (notificationBean.getDiscount_amount() != null || !TextUtils.isEmpty(notificationBean.getDiscount_amount())) {
            holder.tvMoney.setText(notificationBean.getDiscount_amount());
        }
        if (notificationBean.getEnd_time() != null || !TextUtils.isEmpty(notificationBean.getEnd_time())) {
            holder.tvPeriod.setText("有效期：" + notificationBean.getEnd_time());
        }


        final ViewHolder finalHolder = holder;
        holder.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listener.getDispager(position);
                int coupons_id = mList.get(position).getCoupons_id();
                HashMap<String, String> params = new HashMap<>();
                params.put("coupons_id", coupons_id + "");
                params.put("user_id", userId);
                params.put("user_phone", userPhone);
                OkHttpUtils.post()
                        .url(InternetS.GET_DISCOUNT_PAPER)
                        .params(params)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("aaa",
                                        "(NotificationNewsActivity.java:82)" + e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("aaa",
                                        "(NotificationNewsActivity.java:88)" + response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String msg = jsonObject.getString("msg");
                                    int result = jsonObject.getInt("result");
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                    if (result == 0) {
                                        finalHolder.tvContent.setTextColor(Color.parseColor("#e6e6e6"));
                                        finalHolder.tvContent.setEnabled(false);
                                        finalHolder.tvReceive.setText("立即使用");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
            }
        });

        holder.rlDisImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (notificationBean.getInform_state().equals("1")) {
                    listener.useDisPager(position);
                } else {
                    int coupons_id = mList.get(position).getCoupons_id();
                    HashMap<String, String> params = new HashMap<>();
                    params.put("coupons_id", coupons_id + "");
                    params.put("user_id", userId);
                    params.put("user_phone", userPhone);
                    OkHttpUtils.post()
                            .url(InternetS.GET_DISCOUNT_PAPER)
                            .params(params)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Log.e("aaa",
                                            "(NotificationNewsActivity.java:82)" + e.getMessage());
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.e("aaa",
                                            "(NotificationNewsActivity.java:88)" + response);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String msg = jsonObject.getString("msg");
                                        int result = jsonObject.getInt("result");
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                        if (result == 0) {
                                            finalHolder.tvContent.setTextColor(Color.parseColor("#e6e6e6"));
                                            finalHolder.tvContent.setEnabled(false);
                                            finalHolder.tvReceive.setText("立即使用");
                                            mList.get(position).setInform_state("1");
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                }

            }
        });

        if(mList.get(position).getCoupons_state().equals("1")){
            holder.tvContent.setTextColor(Color.parseColor("#e6e6e6"));
            holder.tvContent.setEnabled(false);
            holder.tvContent.setText("已过期");
            holder.tvReceive.setText("已过期");
            holder.rlDisImg.setEnabled(false);
        }else if (mList.get(position).getCoupons_state().equals("2")){
            holder.tvContent.setTextColor(Color.parseColor("#e6e6e6"));
            holder.tvContent.setEnabled(false);
            holder.tvContent.setText("已使用");
            holder.tvReceive.setText("已使用");
            holder.rlDisImg.setEnabled(false);
        }
        return convertView;
    }

    public void setOnDiscountpagerListener(GetDiscountPagerListener listener) {
        this.listener = listener;
    }


    public interface GetDiscountPagerListener {
        void getDispager(int position);

        void useDisPager(int position);
    }

    static class ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_discount_name)
        TextView tvDiscountName;
        @BindView(R.id.tv_requirement)
        TextView tvRequirement;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_receive)
        TextView tvReceive;
        @BindView(R.id.tv_period)
        TextView tvPeriod;
        @BindView(R.id.rl_discount_page)
        RelativeLayout rlDisImg;
        @BindView(R.id.rl_lan)
        RelativeLayout rlLan;
        @BindView(R.id.iv_isgold)
        ImageView ivSignGold;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
