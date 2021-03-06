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
import com.example.handschoolapplication.bean.LearnNewsBean;
import com.example.handschoolapplication.utils.Internet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/8/17.
 */

public class LearnNewsAdapter extends BaseAdapter {

    private Context context;
    private List<LearnNewsBean> mList;
    private int size = 0;

    public LearnNewsAdapter(Context context, List<LearnNewsBean> mList) {
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
        LearnNewsBean learnNewsBean = mList.get(position);
        Glide.with(context).load(Internet.BASE_URL+learnNewsBean.getMessage_type()).centerCrop().into(holder.civUsericon);//学堂头像
        holder.tvClassName.setText(learnNewsBean.getSchool_name());//学堂昵称
        holder.tvTime.setText(learnNewsBean.getMessage_time());//通知时间
        holder.tvNewsContent.setText(learnNewsBean.getMessage_content());//通知内容

        String message_state = learnNewsBean.getMessage_state();
        if ("0".equals(message_state)){
            holder.ivRedDot.setVisibility(View.VISIBLE);
        }else {
            holder.ivRedDot.setVisibility(View.GONE);
        }

        holder.civUsericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setIconClick(position);
            }
        });

        holder.rlNewsContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setLearnClick(position);
            }
        });
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
