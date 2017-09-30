package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.HasEvaBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/5.
 */

public class HasEvaAdapter extends BaseAdapter {

    private Context context;
    private List<HasEvaBean> mList;
    private int size = 0;

    public HasEvaAdapter(Context context, List<HasEvaBean> mList) {
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
            view = View.inflate(context, R.layout.item_haseva_lv, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
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
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.iv_course)
        ImageView ivCourse;
        @BindView(R.id.tv_course)
        TextView tvCourse;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_comment_num)
        TextView tvCommentNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
