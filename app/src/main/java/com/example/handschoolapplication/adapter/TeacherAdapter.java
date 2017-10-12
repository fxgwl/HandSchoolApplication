package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.Teacher;
import com.example.handschoolapplication.utils.Internet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/31.
 */

public class TeacherAdapter extends BaseAdapter {

    private Context context;
    private List<Teacher.DataBean> mList;

    public TeacherAdapter(Context context, List<Teacher.DataBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
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
        if (view == null) {
            view = View.inflate(context, R.layout.item_teacher_lv, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Teacher.DataBean teachBean = mList.get(position);
        Glide.with(context)
                .load(Internet.BASE_URL + teachBean.getTeacher_photo())
                .centerCrop()
                .error(R.drawable.meinv)
                .into(holder.ivTeacher);
        holder.tvName.setText(teachBean.getTeacher_name());
        holder.tvEduBg.setText(teachBean.getTeacher_education());
        holder.tvProfession.setText(teachBean.getTeacher_longevity());
        holder.tvExperience.setText(teachBean.getTeacher_through());
        holder.tvSay.setText(teachBean.getTeacher_motto());
        return view;

    }

    static class ViewHolder {
        @BindView(R.id.iv_teacher)
        ImageView ivTeacher;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_edu_bg)
        TextView tvEduBg;
        @BindView(R.id.tv_profession)
        TextView tvProfession;
        @BindView(R.id.tv_experience)
        TextView tvExperience;
        @BindView(R.id.tv_say)
        TextView tvSay;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
