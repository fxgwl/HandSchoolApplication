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

public class LearningAdapter extends BaseAdapter {

    private List<LearningCourseBean> mList;
    private int size = 0;
    private Context context;
    private AllCourseAdapter.OnClickLearnCodeListener listener;
    private boolean canFinish = false;

    public LearningAdapter(List<LearningCourseBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_learning_lv, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final LearningCourseBean learningCourseBean = mList.get(position);
        holder.tvTime.setText(learningCourseBean.getOrdre_time());//时间
        holder.tvClassName.setText(learningCourseBean.getSchool_name());//学堂名称
        holder.tvCourseName.setText(learningCourseBean.getCourseInfo().getCourse_name());//课程名称
        holder.tvTeacher.setText(learningCourseBean.getCourseInfo().getCourse_teacher());//讲师
        holder.tvAddress.setText(learningCourseBean.getCourseInfo().getCourse_address());//地址
        boolean isFinish = false;
        if (null != learningCourseBean.getClass_money()) {
            String string = learningCourseBean.getClass_money();
            String keshi = string.split("/")[1];
            if (keshi.equals("节")) {
                if (learningCourseBean.getClassSign() != null) {
                    int study_class = learningCourseBean.getClassSign().getStudy_class();
                    holder.tvClassHour.setText("已学习" + study_class + "节课  " + "共" + "1" + "学时");
                    if (study_class == 1) {
                        isFinish = true;
                    } else isFinish = false;
//                    holder.tvClassHour.setText();//学时
                } else {
                    holder.tvClassHour.setText("已学习" + "0" + "节课  " + "共" + "1" + "学时");
                }
                holder.tvToLearn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        learningCourseBean.setLastSign(true);
                        listener.setCourseSign(position, "1");
                    }
                });
            } else {
                final String course_hour = keshi.split("节")[0];
                if (learningCourseBean.getClassSign() != null) {
                    int study_class = learningCourseBean.getClassSign().getStudy_class();
                    holder.tvClassHour.setText("已学习" + study_class + "节课  " + "共" + course_hour + "学时");

                    if (study_class >= Integer.parseInt(course_hour)) {
                        isFinish = true;
                    } else isFinish = false;

                    if (study_class + 1 >= Integer.parseInt(course_hour)) {
                        learningCourseBean.setLastSign(true);
                    } else {
                        learningCourseBean.setLastSign(false);
                    }

//                    holder.tvClassHour.setText();//学时
                } else {
                    holder.tvClassHour.setText("已学习" + "0" + "节课  " + "共" + course_hour + "学时");
                    if (course_hour.equals("1")) {
                        learningCourseBean.setLastSign(true);
                    } else {
                        learningCourseBean.setLastSign(false);
                    }
                }
//                holder.tvClassHour.setText();//学时
                holder.tvToLearn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.setCourseSign(position, course_hour);
                    }
                });
            }

        }

        String course_state = learningCourseBean.getCourseInfo().getCourse_state();

        holder.tvLearnState.setText("学习中");//学习状态
        holder.tvTolearn.setText("学习中");
        holder.llSign.setVisibility(View.VISIBLE);
//        }
        List<LearningCourseBean.CourseTimeInfoBean> courseTimeInfo = learningCourseBean.getCourseTimeInfo();
        if (courseTimeInfo != null) {
            MyCourseTimeAdapter myCourseTimeAdapter = new MyCourseTimeAdapter(courseTimeInfo, context);
            holder.tvLearnTime.setAdapter(myCourseTimeAdapter);
        }
//        holder.tvLearnTime.setText(learningCourseBean.getOrder_course_time());
        holder.tvLearnCode.setText(learningCourseBean.getCourseInfo().getStudy_code());
        holder.llLearn.setVisibility(View.VISIBLE);
        holder.tvStudentInfo.setText(learningCourseBean.getStudent_name() + "(" + learningCourseBean.getStudent_sex() + ")");
        holder.llSchoolAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String school_wei = learningCourseBean.getCourseInfo().getSchool_wei();
                String school_jing = learningCourseBean.getCourseInfo().getSchool_jing();
                Log.e("aaa",
                        "(AllCourseAdapter.java:179)<--学习中课程的经纬度-->" + "school_jing---" + school_jing + "---school_wei---" + school_wei);
                ArrayList<String> strings = new ArrayList<>();
                strings.add(school_wei + "," + school_jing);
                context.startActivity(new Intent(context, CourseAddressActivity.class)
                        .putStringArrayListExtra("addresses", strings));
            }
        });
        return convertView;
    }

    public void setOnClickCourseSignListener(AllCourseAdapter.OnClickLearnCodeListener listener) {
        this.listener = listener;
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
        @BindView(R.id.tv_class_hour)
        TextView tvClassHour;
        @BindView(R.id.tv_learn_code)
        TextView tvLearnCode;
        @BindView(R.id.tv_student_info)
        TextView tvStudentInfo;
        @BindView(R.id.ll_learn_code)
        LinearLayout llLearnCode;
        @BindView(R.id.tv_tosign)
        TextView tvToLearn;
        @BindView(R.id.ll_sign)
        LinearLayout llSign;
        @BindView(R.id.ll_school_address)
        LinearLayout llSchoolAddress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
