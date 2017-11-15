package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.LearningCourseBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/16.
 */

public class AllCourseAdapter extends BaseAdapter {

    private List<LearningCourseBean> mList;
    private Context context;
    private int size = 0;

    private OnClickLearnCodeListener listener;


    public AllCourseAdapter(List<LearningCourseBean> mList, Context context) {
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
            view = View.inflate(context, R.layout.item_learn_state_lv, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        LearningCourseBean learningCourseBean = mList.get(position);
        if ("2".equals(learningCourseBean.getOrder_state())) {
            holder.llLearn.setVisibility(View.GONE);
            holder.tvLearnState.setText("学习中");//学习状态
            holder.llSign.setVisibility(View.VISIBLE);

        } else if ("1".equals(learningCourseBean.getOrder_state())) {
            holder.llLearn.setVisibility(View.VISIBLE);
            holder.tvLearnState.setText("即将学习");
            holder.tvTolearn.setText("即将学习");
            holder.llSign.setVisibility(View.GONE);
        }
        holder.tvTime.setText(learningCourseBean.getOrdre_time());//时间
        holder.tvClassName.setText(learningCourseBean.getSchool_name());//学堂名称
        holder.tvCourseName.setText(learningCourseBean.getClass_name());//课程名称
        if (null != learningCourseBean.getClass_money()) {
            String string = learningCourseBean.getClass_money();
            final String course_hour = string.split("/")[1].split("节")[0];
            holder.tvClassHour.setText("共" + course_hour + "学时");//学时
            Log.e("aaa",
                    "(AllCourseAdapter.java:88)" + learningCourseBean.getClass_teacher());
            holder.tvLearnCode.setText(learningCourseBean.getClass_teacher());//学习码
            holder.llSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.setCourseSign(position, course_hour);
                }
            });
        }
        holder.tvLearnTime.setText(learningCourseBean.getOrder_course_time());
        if (null != learningCourseBean.getCourseInfo()) {
//        holder.tvLearnTime.setText(learningCourseBean.get);
            holder.tvTeacher.setText(learningCourseBean.getCourseInfo().getCourse_teacher());//讲师
            holder.tvAddress.setText(learningCourseBean.getCourseInfo().getCourse_address());//地址
        }


        holder.llLearnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "dbsadbsahjdbjksad", Toast.LENGTH_SHORT).show();
                listener.setLearnCode(position);
            }
        });
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_learn_state)
        TextView tvLearnState;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_class_name)
        TextView tvClassName;
        @BindView(R.id.tv_course_name)
        TextView tvCourseName;
        @BindView(R.id.tv_learn_time)
        TextView tvLearnTime;
        @BindView(R.id.tv_tolearn)
        TextView tvTolearn;
        @BindView(R.id.ll_learn)
        LinearLayout llLearn;
        @BindView(R.id.tv_teacher)
        TextView tvTeacher;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_class_hour)
        TextView tvClassHour;
        @BindView(R.id.tv_learn_code)
        TextView tvLearnCode;
        @BindView(R.id.ll_learn_code)
        LinearLayout llLearnCode;
        @BindView(R.id.ll_sign)
        LinearLayout llSign;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnClickLearnCodeListener {
        void setLearnCode(int position);

        void setCourseSign(int position, String classTime);
    }

    public void setOnClickLearnCodeListener(OnClickLearnCodeListener listener) {
        this.listener = listener;
    }

    public void setOnClickCourseSignListener(OnClickLearnCodeListener listener) {
        this.listener = listener;
    }
}
