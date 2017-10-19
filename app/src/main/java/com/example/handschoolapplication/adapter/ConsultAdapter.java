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
import com.example.handschoolapplication.view.CircleImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/10/11.
 */

public class ConsultAdapter extends BaseAdapter {
    private ArrayList<ConsultBean.DataBean> mList;
    private Context context;
    private int size = 0;


    public ConsultAdapter(ArrayList<ConsultBean.DataBean> mList, Context context) {
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
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder = null;
        ConsultBean.DataBean contact = mList.get(position);
        if (view == null) {
            if ("1".equals(contact.getConsult_type())) {
                view = View.inflate(context, R.layout.item_contactservice, null);
            } else {
                view = View.inflate(context, R.layout.item_contactservice2, null);
            }
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        switch (contact.getConsult_type()) {
            case "0":
                Glide.with(context)
                        .load(Internet.BASE_URL + contact.getSchool_photo())
                        .centerCrop()
                        .error(R.drawable.touxiang)
                        .into(holder.civContactHead);
                break;
            case "1":
                Glide.with(context)
                        .load(Internet.BASE_URL + contact.getConsult_photo())
                        .centerCrop()
                        .error(R.drawable.touxiang)
                        .into(holder.civContactHead);
                break;
        }
        holder.tvContactContent.setText(contact.getConsult_content());
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.civ_contact_head)
        CircleImageView civContactHead;
        @BindView(R.id.tv_contact_content)
        TextView tvContactContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
