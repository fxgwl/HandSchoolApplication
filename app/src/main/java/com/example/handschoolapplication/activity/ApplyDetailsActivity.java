package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.CarListBean;

import butterknife.BindView;
import butterknife.OnClick;

public class ApplyDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    RelativeLayout ivMenu;
    @BindView(R.id.tv_schoolname)
    TextView tvSchoolname;
    @BindView(R.id.tv_coursename)
    TextView tvCoursename;
    @BindView(R.id.tv_coursetime)
    TextView tvCoursetime;
    @BindView(R.id.tv_personnum)
    TextView tvPersonnum;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_teacher)
    TextView tvTeacher;
    @BindView(R.id.tv_costtime)
    TextView tvCosttime;
    @BindView(R.id.tv_coursenum)
    TextView tvCoursenum;
    @BindView(R.id.tv_coursemoney)
    TextView tvCoursemoney;
    @BindView(R.id.tv_user_discount)
    TextView tvUserDiscount;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.tv_student_name)
    TextView tvStudentName;
    private CarListBean.DataBean order;
    private String school_id;
    private int aDouble;
    private double discount;
    private int coupons_id;
    private double hasDiscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        order = (CarListBean.DataBean) getIntent().getSerializableExtra("order");
        school_id = order.getSchool_id();
        Log.e("aaa",
            "(ApplyDetailsActivity.java:61)school_id===="+school_id);
        initView();
    }

    private void initView() {
        tvTitle.setText("报名详情");
        ivMenu.setVisibility(View.INVISIBLE);
        tvSchoolname.setText(order.getSchool_name());
        tvCoursename.setText(order.getClass_name());
        tvCoursetime.setText(order.getOrder_course_time());
        tvPersonnum.setText(order.getCourseInfo().getEnrol_num() + "【已报名】/" + order.getCourseInfo().getCourse_capacity() + "人【总人数】");
        tvAge.setText(order.getCourseInfo().getAge_range());
        tvTeacher.setText(order.getCourseInfo().getCourse_teacher());
        tvCosttime.setText(order.getClass_money());

        String class_money = order.getClass_money();
        String yuan = class_money.split("元")[0];
        String jie = class_money.split("/")[1].split("节")[0];
        String course_num = order.getCourse_num();
        int uCourseNum = Integer.parseInt(jie);
        int CourseNum = Integer.parseInt(course_num);
        double uCoursePrice = Double.parseDouble(yuan);

        tvCoursenum.setText((uCourseNum*CourseNum)+"节");
        aDouble = (int) (uCoursePrice * CourseNum);
        tvCoursemoney.setText("共"+(uCourseNum*CourseNum)+"节课时\t\t\t"+"小计：￥"+ aDouble +"元");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_apply_details;
    }

    @OnClick({R.id.rl_back, R.id.ll_student_name, R.id.tv_nowapply_config,R.id.ll_discountcoupon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.ll_student_name:
                startActivityForResult(new Intent(ApplyDetailsActivity.this,SetStudentNameActivity.class),2);
                break;
            case R.id.tv_nowapply_config:
                String studentName = tvStudentName.getText().toString().trim();
                if (TextUtils.isEmpty(studentName)){
                    Toast.makeText(this, "请设置上课学生姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                setResult(22,new Intent()
                        .putExtra("student",studentName)
                        .putExtra("coupons_id",coupons_id)
                        .putExtra("hasDiscount",hasDiscount)
                        .putExtra("discount",discount));
                finish();
                break;
            case R.id.ll_discountcoupon:
                Intent intent1 = new Intent(this, MyDiscountActivity.class);
                intent1.putExtra("school_id", school_id);
                intent1.putExtra("money", aDouble+"");
                startActivityForResult(intent1, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==11){
            discount = data.getDoubleExtra("discount",0);
            coupons_id = data.getIntExtra("coupons_id",0);
            hasDiscount = aDouble - discount;
            tvDiscount.setText("-￥"+ discount);
        }else if (requestCode==2&&resultCode==22){
            tvStudentName.setText(data.getStringExtra("student"));
        }
    }
}
