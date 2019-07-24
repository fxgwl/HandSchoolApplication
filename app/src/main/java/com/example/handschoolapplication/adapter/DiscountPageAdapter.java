package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.DiscountPageBean;
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
 * Created by zhw on 2018/6/12.
 */

public class DiscountPageAdapter extends BaseAdapter {

    private Context context;
    private List<DiscountPageBean.DataBean> mList;
    private int size = 0;
    private String userPhone,userId;
    private OnAdapterOnClick listener;

    public void setListener(OnAdapterOnClick listener) {
        this.listener = listener;
    }

    public DiscountPageAdapter(Context context, List<DiscountPageBean.DataBean> mList) {
        this.context = context;
        this.mList = mList;
        userPhone = (String) SPUtils.get(context, "user_phone", "");
        userId = (String) SPUtils.get(context, "userId", "");
    }

    @Override
    public int getCount() {
        if (mList != null) return mList.size();
        return 0;
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

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_discount_page_lv, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvMoney.setText(mList.get(position).getDiscount_amount());
        viewHolder.tvPeriod.setText("有效期："+mList.get(position).getEnd_time());
        viewHolder.tvRequirement.setText("满"+mList.get(position).getMax_money()+"可使用");
        String coupons_state = mList.get(position).getCoupon_state();
        Log.e("aaa",
                "(DiscountPageAdapter.java:80)<--优惠券的状态-->"+coupons_state);
        if ("1".equals(coupons_state)){
            viewHolder.tvReceiver.setText("立即使用");
            viewHolder.tvReceiver.setEnabled(true);
        }else {
            viewHolder.tvReceiver.setText("立即领取");
            viewHolder.tvReceiver.setEnabled(true);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.tvReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> params = new HashMap<>();
                int coupons_id = mList.get(position).getCoupons_id();
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
                                    if (result==0){
                                        finalViewHolder.tvReceiver.setText("已领取");
                                        finalViewHolder.tvReceiver.setEnabled(false);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                listener.onClick(position);
            }
        });
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_text)
        TextView tvText;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_requirement)
        TextView tvRequirement;
        @BindView(R.id.tv_period)
        TextView tvPeriod;
        @BindView(R.id.tv_receiver)
        TextView tvReceiver;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnAdapterOnClick{
        void onClick(int position);
    }
}
