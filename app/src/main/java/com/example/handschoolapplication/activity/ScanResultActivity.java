package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.OrderDetailCourseTimeAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.OrderDetailsBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.view.MyListView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class ScanResultActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_learn_state)
    TextView tvLearnState;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_class_name)
    TextView tvClassName;
    @BindView(R.id.tv_course_name)
    TextView tvCourseName;
    @BindView(R.id.tv_learn_time)
    MyListView tvLearnTime;
    @BindView(R.id.tv_student_info)
    TextView tvStudentInfo;
    @BindView(R.id.tv_teacher)
    TextView tvTeacher;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_class_hour)
    TextView tvClassHour;
    @BindView(R.id.tv_learn_code)
    TextView tvLearnCode;

    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != getIntent().getStringExtra("orderId")) {
            orderId = getIntent().getStringExtra("orderId");
        }

        initView();

        initData();
    }

    private void initView() {

        tvTitle.setText("扫描结果");
    }

    private void initData() {

        OkHttpUtils.post()
                .url(Internet.SCAN_RESULT)
                .addParams("order_id", orderId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ScanResultActivity.java:60)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ScanResultActivity.java:67)<---->" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int result = jsonObject.getInt("result");
                            if (result == 0) {

                                OrderDetailsBean orderDetailsBean = new Gson().fromJson(response, OrderDetailsBean.class);
                                OrderDetailsBean.DataBean data = orderDetailsBean.getData();
//                                JSONObject data = jsonObject.getJSONObject("data");
                                String school_name = data.getSchool_name();//所在课堂
                                String ordre_time = data.getOrdre_time();//订单时间
                                String class_name = data.getClass_name();//所选课程
//                                String order_course_time = data.getString("order_course_time");//上课时间
//                                JSONObject courseInfo = data.getJSONObject("courseInfo");
                                String course_teacher = data.getCourseInfo().getCourse_teacher();//专家讲师
                                String course_address = data.getCourseInfo().getCourse_address();//学堂地址
                                int study_class = data.getCourseInfo().getStudy_class()==0?0:data.getCourseInfo().getStudy_class();//已学课时
                                String course_money = data.getCourseInfo().getCourse_money();//总课时
                                String course_hour = course_money.split("/")[1].split("节")[0];
                                 String class_teacher = data.getClass_teacher();//学习码
                                String student_sex = data.getStudent_sex()==null?"":data.getStudent_sex();//学生性别
                                String student_name = data.getStudent_name()==null?"":data.getStudent_name();//学生姓名
//
                                int studyAllTime = Integer.parseInt(course_hour);
                                boolean isFinish = false;
                                if (study_class >= studyAllTime) {
                                    isFinish = true;
                                }
                                String order_state = data.getOrder_state();//订单状态
                                String course_state = data.getCourseInfo().getCourse_state();//课程状态

                                if (order_state.equals("1")) {//即将学习
                                    tvLearnState.setText("即将学习");
                                } else if (order_state.equals("2")){
                                    if (course_state.equals("2") || isFinish) {//已完结
                                        tvLearnState.setText("学习已完成");
                                    } else {//学习中
                                        tvLearnState.setText("学习中");
                                    }
                                }

                                List<OrderDetailsBean.DataBean.CourseInfoBean.CourseTimeInfosBean> courseTimeInfos = data.getCourseInfo().getCourseTimeInfos();
                                if (courseTimeInfos!=null){
                                    OrderDetailCourseTimeAdapter myCourseTimeAdapter = new OrderDetailCourseTimeAdapter(courseTimeInfos, ScanResultActivity.this);
                                    tvLearnTime.setAdapter(myCourseTimeAdapter);
                                }
                                tvTime.setText(ordre_time);
                                tvClassName.setText(school_name);
                                tvCourseName.setText(class_name);
//                                tvLearnTime.setText(order_course_time);
                                tvTeacher.setText(course_teacher);
                                tvAddress.setText(course_address);
                                tvClassHour.setText("已学习" + study_class + "节课" + "  共" + course_hour + "节课");
                                tvLearnCode.setText(class_teacher);
                                tvStudentInfo.setText(student_name+"("+student_sex+")");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_scan_result;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
        }
    }
}
