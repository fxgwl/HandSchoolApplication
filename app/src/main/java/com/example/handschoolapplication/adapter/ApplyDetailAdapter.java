package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.ApplyDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/2.
 */

public class ApplyDetailAdapter extends BaseAdapter {

    private Context context;
    private List<ApplyDetail> mList;
    private int size = 0;


    public ApplyDetailAdapter(Context context, List<ApplyDetail> mList) {
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

        ViewHolder holder=null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_apply_detail_lv, null);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.civ_usericon)
        CircleImageView civUsericon;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_coursenum)
        TextView tvCoursenum;
        @BindView(R.id.ll_send_message)
        LinearLayout llSendMessage;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
