package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.NotificationBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/9/2.
 */

public class NotificationAdapter extends BaseAdapter {

    private Context context;
    private List<NotificationBean> mList;
    private int size = 0;

    public GetDiscountPagerListener listener;


    public NotificationAdapter(Context context, List<NotificationBean> mList) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_notification_lv, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final NotificationBean notificationBean = mList.get(position);
        holder.tvTime.setText(notificationBean.getMessage_time());//通知时间
        holder.tvTitle.setText(notificationBean.getInform_name());//通知标题
        holder.tvContent.setText(notificationBean.getInform_content());//通知内容
        String inform_type = notificationBean.getInform_type();
        if ("1".equals(inform_type)){
//            notificationBean.setGet(true);
                holder.tvContent.setTextColor(Color.parseColor("#da2525"));
                holder.tvContent.setEnabled(true);
        }else if ("0".equals(inform_type)){
            holder.tvContent.setTextColor(Color.parseColor("#e6e6e6"));
            holder.tvContent.setEnabled(false);
        }

        final ViewHolder finalHolder = holder;
        holder.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.getDispager(position);
                finalHolder.tvContent.setEnabled(false);
                finalHolder.tvContent.setTextColor(Color.parseColor("#e6e6e6"));
                notificationBean.setInform_type("0");
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface GetDiscountPagerListener {
        void getDispager(int position);
    }

    public void setOnDiscountpagerListener(GetDiscountPagerListener listener) {
        this.listener = listener;
    }
}
