package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.TeachNewsBean;
import com.example.handschoolapplication.utils.Internet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/27.
 */

public class EducationAdapter extends BaseAdapter {

    private List<TeachNewsBean.DataBean> mlist;
    private Context context;
    private LayoutInflater inflater;

    public EducationAdapter(List<TeachNewsBean.DataBean> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_edu_lv, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TeachNewsBean.DataBean teachNews = mlist.get(position);
        holder.tvTitle.setText(teachNews.getNews_title());
        holder.tvType.setText(teachNews.getNews_type());
        holder.tvNum.setText("阅读" + (TextUtils.isEmpty(teachNews.getNews_record()) ? 0 : teachNews.getNews_record()) + "人");
        Glide.with(context)
                .load(Internet.BASE_URL + teachNews.getNews_photo())
                .centerCrop()
                .into(holder.ivImag);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.iv_imag)
        ImageView ivImag;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
