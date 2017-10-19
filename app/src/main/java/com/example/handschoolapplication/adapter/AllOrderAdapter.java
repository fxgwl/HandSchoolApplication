package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.AllOrderBean;
import com.example.handschoolapplication.bean.OrderBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/16.
 */

public class AllOrderAdapter extends BaseAdapter {

    private Context context;
    private List<AllOrderBean> mList;
    private List<OrderBean> mOrderList;
    private int size = 0;
    private EvaluateListener listener;

    public AllOrderAdapter(Context context, List<AllOrderBean> mList, List<OrderBean> mOrderList) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_order_lv, null);
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
//                listener.onEvaluate(position);
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

    public interface EvaluateListener {
        void onEvaluate(int position);
    }

    public void setEvaluateListener(EvaluateListener listener) {
        this.listener = listener;
    }


}
