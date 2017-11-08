package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.util.Log;
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

public class ContactServiceAdapter extends BaseAdapter {
    private ArrayList<ContactServiceBean.DataBean> mList;
    private Context context;
    private int size = 0;


    public ContactServiceAdapter(ArrayList<ContactServiceBean.DataBean> mList, Context context) {
        this.mList = mList;
        this.context = context;

        Log.e("aaa",
            "(ContactServiceAdapter.java:37)集合的长度"+mList.size());
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
        ContactServiceBean.DataBean contact = mList.get(position);
        if (view == null) {
            String type = contact.getConsult_type();
            Log.e("aaa",
                "(ContactServiceAdapter.java:65)--------->"+type+"position----->"+position);
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
        Glide.with(context)
                .load(Internet.BASE_URL + contact.getConsult_photo())
                .centerCrop()
                .error(R.drawable.touxiang)
                .into(holder.civContactHead);
        holder.tvContactContent.setText(contact.getConsult_content());
        holder.tvChatTime.setText(contact.getConsult_time());
        return view;
    }


    static class ViewHolder {
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
