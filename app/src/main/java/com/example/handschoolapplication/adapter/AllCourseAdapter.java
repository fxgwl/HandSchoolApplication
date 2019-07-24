package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.CourseAddressActivity;
import com.example.handschoolapplication.bean.LearningCourseBean;
import com.example.handschoolapplication.view.MyListView;

import java.util.ArrayList;
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
    private boolean canFinish = false;


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
        final LearningCourseBean learningCourseBean = mList.get(position);

        holder.tvTime.setText(learningCourseBean.getOrdre_time());//时间
        holder.tvClassName.setText(learningCourseBean.getSchool_name());//学堂名称
        holder.tvCourseName.setText(learningCourseBean.getClass_name());//课程名称
        String course_hour = "1";
        if (null != learningCourseBean.getClass_money()) {
            String string = learningCourseBean.getClass_money();
            course_hour = string.split("/")[1].split("节")[0];
//            holder.tvClassHour.setText("共" + course_hour + "学时");//学时
            Log.e("aaa",
                    "(AllCourseAdapter.java:88)---Study_code---" + learningCourseBean.getCourseInfo().getStudy_code());
            final String finalCourse_hour = course_hour;
//            holder.llSign.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.setCourseSign(position, finalCourse_hour,canFinish);
//                }
//            });
        }
        holder.tvLearnCode.setText(learningCourseBean.getCourseInfo().getStudy_code());//学习码

        int allTime = Integer.parseInt(course_hour);//总学时

        String order_state = learningCourseBean.getOrder_state();//订单状态
        String course_state = learningCourseBean.getCourseInfo().getCourse_state();//课程状态
        int study_class = 0;
        if (learningCourseBean.getClassSign() != null) {
            study_class = learningCourseBean.getClassSign().getStudy_class();
        }
        boolean isFinish = false;
        if (study_class >= allTime) {
            isFinish = true;
        }

        if ("0".equals(course_state) || order_state.equals("1")) {
            holder.llLearn.setVisibility(View.VISIBLE);
            holder.tvLearnState.setText("即将学习");
            holder.tvTolearn.setText("即将学习");
            holder.llSign.setVisibility(View.GONE);
            if (null != learningCourseBean.getClass_money()) {
                String string = learningCourseBean.getClass_money();
                course_hour = string.split("/")[1].split("节")[0];
                holder.tvClassHour.setText("共" + course_hour + "学时");//学时
                Log.e("aaa",
                        "(AllCourseAdapter.java:88)" + learningCourseBean.getClass_teacher());
                holder.tvLearnCode.setText(learningCourseBean.getCourseInfo().getStudy_code());//学习码
            }
        } else if (isFinish || course_state.equals("2")) {
            holder.llLearn.setVisibility(View.VISIBLE);
            holder.tvLearnState.setText("学习已完成");
            holder.tvTolearn.setText("学习已完成");
            holder.llSign.setVisibility(View.GONE);
            if (null != learningCourseBean.getClass_money()) {
                String string = learningCourseBean.getClass_money();
                course_hour = string.split("/")[1].split("节")[0];
                holder.tvClassHour.setText("共" + course_hour + "学时");//学时
                Log.e("aaa",
                        "(AllCourseAdapter.java:88)" + learningCourseBean.getClass_teacher());
                holder.tvLearnCode.setText(learningCourseBean.getCourseInfo().getStudy_code());//学习码
            }
        } else {
            holder.llLearn.setVisibility(View.VISIBLE);
            holder.tvTolearn.setText("学习中");
            holder.tvLearnState.setText("学习中");//学习状态
            holder.llSign.setVisibility(View.VISIBLE);

            if (null != learningCourseBean.getClass_money()) {
                String string = learningCourseBean.getClass_money();
                course_hour = string.split("/")[1].split("节")[0];
                holder.tvClassHour.setText("已学习" + study_class + "课时  " + "共" + course_hour + "学时");//学时
                Log.e("aaa",
                        "(AllCourseAdapter.java:88)" + learningCourseBean.getClass_teacher());
                holder.tvLearnCode.setText(learningCourseBean.getCourseInfo().getStudy_code());//学习码
                final String finalCourse_hour = course_hour;

                canFinish = false;
                try {
                    if (course_hour.isEmpty()) {
                        int allClass = 1;
                        learningCourseBean.setLastSign(true);
                    } else {
                        int i = Integer.parseInt(course_hour);
                        if (study_class + 1 == i) {
                            learningCourseBean.setLastSign(true);
                        } else {
                            learningCourseBean.setLastSign(false);
                        }
                    }
                } catch (Exception e) {

                }

                holder.llSign.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.setCourseSign(position, finalCourse_hour);
                    }
                });
            }
        }

        List<LearningCourseBean.CourseTimeInfoBean> courseTimeInfo = learningCourseBean.getCourseTimeInfo();
        if (courseTimeInfo != null) {
            MyCourseTimeAdapter myCourseTimeAdapter = new MyCourseTimeAdapter(courseTimeInfo, context);
            holder.tvLearnTime.setAdapter(myCourseTimeAdapter);
        }
        if (null != learningCourseBean.getCourseInfo()) {
//        holder.tvLearnTime.setText(learningCourseBean.get);
            holder.tvTeacher.setText(learningCourseBean.getCourseInfo().getCourse_teacher());//讲师
            holder.tvAddress.setText(learningCourseBean.getCourseInfo().getCourse_address());//地址
        }
        holder.tv_student_info.setText(learningCourseBean.getStudent_name() + "(" + learningCourseBean.getStudent_sex() + ")");
        holder.llLearnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "dbsadbsahjdbjksad", Toast.LENGTH_SHORT).show();
                listener.setLearnCode(position);
            }
        });

        holder.llSchoolAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String school_wei = learningCourseBean.getCourseInfo().getSchool_wei();
                String school_jing = learningCourseBean.getCourseInfo().getSchool_jing();
                Log.e("aaa",
                        "(AllCourseAdapter.java:179)<--全部课程的经纬度-->" + "school_jing---" + school_jing + "---school_wei---" + school_wei);
                ArrayList<String> strings = new ArrayList<>();
                strings.add(school_wei + "," + school_jing);
                context.startActivity(new Intent(context, CourseAddressActivity.class)
                        .putStringArrayListExtra("addresses", strings));
            }
        });
        return view;
    }

    public void setOnClickLearnCodeListener(OnClickLearnCodeListener listener) {
        this.listener = listener;
    }

    public void setOnClickCourseSignListener(OnClickLearnCodeListener listener) {
        this.listener = listener;
    }

    public interface OnClickLearnCodeListener {
        void setLearnCode(int position);

        void setCourseSign(int position, String classTime);
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
        @BindView(R.id.lv_learn_time)
        MyListView tvLearnTime;
        @BindView(R.id.tv_tolearn)
        TextView tvTolearn;
        @BindView(R.id.ll_learn)
        LinearLayout llLearn;
        @BindView(R.id.tv_teacher)
        TextView tvTeacher;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_student_name)
        TextView tv_student_info;
        @BindView(R.id.tv_class_hour)
        TextView tvClassHour;
        @BindView(R.id.tv_learn_code)
        TextView tvLearnCode;
        @BindView(R.id.ll_learn_code)
        LinearLayout llLearnCode;
        @BindView(R.id.ll_sign)
        LinearLayout llSign;
        @BindView(R.id.ll_school_address)
        LinearLayout llSchoolAddress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
