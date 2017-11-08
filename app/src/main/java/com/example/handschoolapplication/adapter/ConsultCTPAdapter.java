package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.ContactServiceBean;
import com.example.handschoolapplication.utils.Internet;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Administrator on 2017/10/11.
 */

public class ConsultCTPAdapter extends BaseAdapter {
    private ArrayList<ContactServiceBean.DataBean> mList;
    private Context context;
    private int size = 0;


    public ConsultCTPAdapter(ArrayList<ContactServiceBean.DataBean> mList, Context context) {
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
        return Integer.parseInt(mList.get(position).getConsult_type());
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder = null;
        ViewHolder2 holder2 = null;
        ContactServiceBean.DataBean dataBean = mList.get(position);
        if (view == null) {
            if ("0".equals(dataBean.getConsult_type())) {
                view = View.inflate(context, R.layout.item_contactservice, null);
                holder2 = new ViewHolder2(view);
                view.setTag(holder2);
            } else {

                view = View.inflate(context, R.layout.item_contactservice2, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }

        } else {
            if ("0".equals(dataBean.getConsult_type())) {
                holder2 = (ViewHolder2) view.getTag();
            } else {
                holder = (ViewHolder) view.getTag();
            }

        }
        switch (dataBean.getConsult_type()) {
            case "1":
                Glide.with(context)
                        .load(Internet.BASE_URL + dataBean.getSchool_photo())
                        .centerCrop()
                        .error(R.drawable.touxiang)
                        .into(holder.civContactHead);
                holder.tvContactContent.setText(dataBean.getConsult_content());
                holder.tvChatTime.setText(dataBean.getConsult_time());
                break;
            case "0":
                Glide.with(context)
                        .load(Internet.BASE_URL + dataBean.getConsult_photo())
                        .centerCrop()
                        .error(R.drawable.touxiang)
                        .into(holder2.civContactHead);
                holder2.tvContactContent.setText(dataBean.getConsult_content());
                holder2.tvChatTime.setText(dataBean.getConsult_time());
                break;
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
