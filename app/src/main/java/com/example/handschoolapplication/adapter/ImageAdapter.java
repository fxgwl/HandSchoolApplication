package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/31.
 */

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<String> mList;
    private int size = 0;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<String> mList) {
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
            view = View.inflate(context, R.layout.item_class_condition, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Glide.with(context).load(mList.get(position)).centerCrop().into(holder.ivCondition);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_condition)
        ImageView ivCondition;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
