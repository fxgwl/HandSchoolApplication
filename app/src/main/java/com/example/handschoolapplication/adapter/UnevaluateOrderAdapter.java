package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.OrderBean;
import com.example.handschoolapplication.bean.UnevaluateOrderBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/16.
 */

public class UnevaluateOrderAdapter extends BaseAdapter {

    private Context context;
    private List<UnevaluateOrderBean> mList;
    private List<OrderBean> mOrderList;
    private int size = 0;

    public UnevaluateOrderAdapter(Context context, List<UnevaluateOrderBean> mList, List<OrderBean> mOrderList) {
        this.context = context;
        this.mList = mList;
        this.mOrderList = mOrderList;
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

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_uneva_lv, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        OrderAdapter orderAdapter = new OrderAdapter(context, mOrderList);
//        holder.lvOrder.setAdapter(orderAdapter);
//        MyUtiles.setListViewHeightBasedOnChildren(holder.lvOrder);
//        holder.tvMake.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(new Intent(context, PublishEvaluateActivity.class));
//            }
//        });
        return convertView;
    }



    static class ViewHolder {
        @BindView(R.id.tv_organization)
        TextView tvOrganization;
        @BindView(R.id.lv_order)
        ListView lvOrder;
        @BindView(R.id.tv_make)
        TextView tvMake;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }



}
