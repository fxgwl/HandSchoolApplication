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
import com.example.handschoolapplication.bean.MyBroswerBean;
import com.example.handschoolapplication.utils.Internet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/29.
 */

public class BroswerAdapter extends BaseAdapter {

    private Context context;
    private List<MyBroswerBean.DataBean> mList;
    private LayoutInflater inflater;

    private ShareListener listener;

    public BroswerAdapter(Context context, List<MyBroswerBean.DataBean> mList) {
        this.context = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_bro_lv, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MyBroswerBean.DataBean dataBean = mList.get(position);
        holder.tvTime.setText(dataBean.getFootprint_time());
        Glide.with(context)
                .load(Internet.BASE_URL + dataBean.getFootprint_name())
                .centerCrop()
                .error(R.drawable.kecheng)
                .into(holder.ivCourse);
        holder.tvCourse.setText(dataBean.getCourse_name());
        holder.tvPrice.setText("价格： ¥" + dataBean.getCourse_money());

        holder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnShare(position);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_course)
        ImageView ivCourse;
        @BindView(R.id.tv_course)
        TextView tvCourse;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.iv_share)
        ImageView ivShare;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface ShareListener {
        void setOnShare(int postion);
    }

    public void setOnShareListener(ShareListener listener) {
        this.listener = listener;
    }
}
