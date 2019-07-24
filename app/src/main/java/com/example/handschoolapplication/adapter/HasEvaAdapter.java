package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.HasEvaBean;
import com.example.handschoolapplication.utils.Internet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/5.
 */

public class HasEvaAdapter extends BaseAdapter {

    private Context context;
    private List<HasEvaBean.DataBean> mList;
    private int size = 0;

    public HasEvaAdapter(Context context, List<HasEvaBean.DataBean> mList) {
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
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
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
        HasEvaBean.DataBean dataBean = mList.get(position);
        Glide.with(context)
                .load(Internet.BASE_URL + dataBean.getSend_photo())
                .centerCrop()
                .into(holder.civUsericon);
        holder.tvUsername.setText(dataBean.getSend_name());
        holder.tvTime.setText(dataBean.getInteract_time());
        holder.tvComment.setText(dataBean.getContents());
        holder.tvUsername.setText(dataBean.getSend_name());

        String picture_one = dataBean.getPicture_one();

        Glide.with(context)
                .load(Internet.BASE_URL + picture_one)
                .centerCrop()
                .into(holder.ivCourse);
        holder.tvCourse.setText(dataBean.getCourse_name());
        holder.tvPrice.setText(TextUtils.isEmpty(dataBean.getCourse_money()) ? "¥" + 0.00 : "¥" + dataBean.getCourse_money());
        int size = dataBean.getReplyInfo() != null ? dataBean.getReplyInfo().size() : 0;
        int browse_num = dataBean.getBrowse_num();
        holder.tvCommentNum.setText("  浏览" + browse_num + "次     评价" + size + "次");
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
