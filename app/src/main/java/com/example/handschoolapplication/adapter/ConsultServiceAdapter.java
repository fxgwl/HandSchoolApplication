package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.ConsultBean;
import com.example.handschoolapplication.utils.Internet;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Administrator on 2017/10/11.
 */

public class ConsultServiceAdapter extends BaseAdapter {
    private ArrayList<ConsultBean.DataBean> mList;
    private Context context;
    private int size = 0;
    private String type;


    public ConsultServiceAdapter(ArrayList<ConsultBean.DataBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {

        if (mList.size() <= 0 || mList == null) {
            size = 0;
        } else {
            size = mList.size();
        }
        return size;
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if ("用户".equals(mList.get(position).getMessage_type())){
            type = "0";
        }else {
            type = "1";
        }
        return Integer.parseInt(type);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder = null;
        ViewHolder2 holder2 = null;
        ConsultBean.DataBean dataBean = mList.get(position);
        if (view == null) {
            if ("用户".equals(dataBean.getMessage_type())) {
                view = View.inflate(context, R.layout.item_contactservice2, null);
                holder2 = new ViewHolder2(view);
                view.setTag(holder2);
            } else {
                view = View.inflate(context, R.layout.item_contactservice, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }

        } else {
            if ("用户".equals(dataBean.getMessage_type())) {
                holder2 = (ViewHolder2) view.getTag();
            } else {
                holder = (ViewHolder) view.getTag();
            }

        }
        if ("用户".equals(dataBean.getMessage_type())) {
            Glide.with(context)
                    .load(Internet.BASE_URL + dataBean.getMessage_photo())
                    .centerCrop()
                    .error(R.drawable.touxiang)
                    .into(holder2.civContactHead);
            holder2.tvContactContent.setText(dataBean.getMessage_content());
            holder2.tvChatTime.setText(dataBean.getMessage_time());
        }else {

            Glide.with(context)
                    .load(Internet.BASE_URL + dataBean.getMessage_photo())
                    .centerCrop()
                    .error(R.drawable.touxiang)
                    .into(holder.civContactHead);
            holder.tvContactContent.setText(dataBean.getMessage_content());
            holder.tvChatTime.setText(dataBean.getMessage_time());
        }

        return view;
    }

    class ViewHolder2 {
        @BindView(R.id.civ_contact_head)
        CircleImageView civContactHead;
        @BindView(R.id.tv_contact_content)
        TextView tvContactContent;
        @BindView(R.id.tv_chat_time)
        TextView tvChatTime;

        ViewHolder2(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolder {
        @BindView(R.id.civ_contact_head)
        CircleImageView civContactHead;
        @BindView(R.id.tv_contact_content)
        TextView tvContactContent;
        @BindView(R.id.tv_chat_time)
        TextView tvChatTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
