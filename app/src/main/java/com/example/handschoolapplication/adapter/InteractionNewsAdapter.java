package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.InteractionNewsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/17.
 */

public class InteractionNewsAdapter extends BaseAdapter {

    private Context context;
    private List<InteractionNewsBean> mList;
    private int size = 0;


    public InteractionNewsAdapter(Context context, List<InteractionNewsBean> mList) {
        this.context = context;
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
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder = null;

        if (view == null) {
            view = View.inflate(context, R.layout.item_in_lv, null);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_class)
        ImageView ivClass;
        @BindView(R.id.tv_classname)
        TextView tvClassname;
        @BindView(R.id.tv_class_price)
        TextView tvClassPrice;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_detail)
        TextView tvDetail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
