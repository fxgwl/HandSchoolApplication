package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.CourseNewsBean;
import com.example.handschoolapplication.bean.LearnNewsBean;
import com.example.handschoolapplication.utils.Internet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/8/17.
 * 课程消息列表的Adapter
 */

public class CourseNewsAdapter extends BaseAdapter {

    private Context context;
    private List<CourseNewsBean.DataBean> mList;
    private int size = 0;

    public CourseNewsAdapter(Context context, List<CourseNewsBean.DataBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    public OnItemClickListener listener;

    @Override
    public int getCount() {
        if (mList != null) {
            size = mList.size();
        }
        return size;

    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
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
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder = null;

        if (view == null) {
            view = View.inflate(context, R.layout.item_ln_lv, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Glide.with(context).load(Internet.BASE_URL+mList.get(position).getHead_photo()).centerCrop().into(holder.civUsericon);
        holder.tvClassName.setText(mList.get(position).getUser_name());
        holder.tvNewsContent.setText(mList.get(position).getMessage_content());
        holder.tvTime.setText(mList.get(position).getMessage_time());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.civ_usericon)
        CircleImageView civUsericon;
        @BindView(R.id.tv_class_name)
        TextView tvClassName;
        @BindView(R.id.tv_news_content)
        TextView tvNewsContent;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_red_dot)
        ImageView ivRedDot;
        @BindView(R.id.rl_news_content)
        RelativeLayout rlNewsContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener{
        void setIconClick(int position);
        void setLearnClick(int position);
    }
}
