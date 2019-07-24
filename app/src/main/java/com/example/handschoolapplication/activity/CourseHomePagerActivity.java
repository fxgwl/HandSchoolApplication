package com.example.handschoolapplication.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.CHPPingJiaAdapter;
import com.example.handschoolapplication.adapter.ClassTimeAdapter;
import com.example.handschoolapplication.adapter.CostAdapter;
import com.example.handschoolapplication.adapter.DiscountPageAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ClassInfoCHP;
import com.example.handschoolapplication.bean.CostBean;
import com.example.handschoolapplication.bean.CourseDertailBean;
import com.example.handschoolapplication.bean.CourseTimeBean;
import com.example.handschoolapplication.bean.DiscountPageBean;
import com.example.handschoolapplication.bean.EvaluateBean;
import com.example.handschoolapplication.bean.TimeBean;
import com.example.handschoolapplication.bean.TimeHourBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.MyGridView;
import com.example.handschoolapplication.view.MyListView;
import com.example.handschoolapplication.view.MyPopupWindow;
import com.example.handschoolapplication.view.SelfDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.bumptech.glide.Glide.with;

public class CourseHomePagerActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.course_name)
    TextView courseName;
    @BindView(R.id.course_share_ll)
    LinearLayout courseShareLl;
    @BindView(R.id.course_money_tv)
    TextView courseMoneyTv;
    @BindView(R.id.course_oldmoney_tv)
    TextView courseOldmoneyTv;
    @BindView(R.id.course_classml)
    TextView courseClassml;
    @BindView(R.id.course_duixiang)
    TextView courseDuixiang;
    @BindView(R.id.course_teacher)
    TextView courseTeacher;
    @BindView(R.id.course_address)
    TextView courseAddress;
    @BindView(R.id.course_classtime)
    TextView courseClasstime;
    @BindView(R.id.course_classdetail)
    TextView courseClassdetail;
    @BindView(R.id.course_pingjia)
    TextView coursePingjia;
    @BindView(R.id.course_mlv_pingjia)
    MyListView courseMlvPingjia;
    @BindView(R.id.course_save)
    TextView courseSave;
    @BindView(R.id.tv_popularity)
    TextView tvPopulatrity;
    @BindView(R.id.ll_bottem)
    LinearLayout llBottem;
    @BindView(R.id.iv_class_photo)
    ImageView ivClassPhoto;
    @BindView(R.id.tv_class_name)
    TextView tvClassName;
    @BindView(R.id.iv_grade_1)
    ImageView ivGradeOne;
    @BindView(R.id.iv_grade_2)
    ImageView ivGradeTwo;
    @BindView(R.id.iv_grade_3)
    ImageView ivGradeThree;
    @BindView(R.id.iv_grade_4)
    ImageView ivGradeFour;
    @BindView(R.id.iv_grade_5)
    ImageView ivGradeFive;
    @BindView(R.id.tv_all_course_num)
    TextView tvAllCourseNum;
    @BindView(R.id.tv_love_num)
    TextView tvLoveNum;
    @BindView(R.id.tv_class_money)
    TextView tvClassMoney;
    @BindView(R.id.tv_is_gold)
    TextView tvIsGold;
    @BindView(R.id.tv_is_discount)
    TextView tvIsDiscount;
    @BindView(R.id.course_learnplan)
    TextView tvCourseLearnPlan;
    @BindView(R.id.course_apply)
    TextView tvCourseApply;
    @BindView(R.id.ll_school_info)
    LinearLayout llSchoolInfo;
    @BindView(R.id.sc_view)
    ScrollView scrollView;

    private ConvenientBanner convenientBanner;
    private List<String> listImg = new ArrayList<>();


    private List<TimeBean> mList;

    private List<TimeHourBean> mFeeList;//课时费用
    private String course_id;
    private String user_id;
    private String school_id;
    private String schooluid;
    private String class_money = "";
    private int class_price_id = 0;

    private String class_time = "";
    private String school_name;
    private String course_name;
    private String course_time;
    private int enrol_num;
    private int course_capacity;
    private String age_range;
    private String course_teacher;
    private String original_price;
    private String preferential_price;
    private boolean isLogin;
    private String user_type;
    private String dengji;
    private String course_info;
    private String head_photo;
    private String school_name1;
    private String collect_num;
    private String class_num;
    private String user_dengji;
    private String is_collect;
    private String is_golds;
    private String is_coup;
    private String school_wei;
    private String school_jing;
    private String course_state;
    private String user_phone;
    private String picture_one = "";
    private CostAdapter costAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        tvTitle.setText("课程主页");
        isLogin = (boolean) SPUtils.get(this, "isLogin", false);
        user_type = (String) SPUtils.get(this, "user_type", "0");
        scrollView.smoothScrollTo(0, 0);
        //if (!isLogin || "1".equals(user_type)) {
        if ("1".equals(user_type)) {
            llBottem.setVisibility(View.GONE);
        } else {
            llBottem.setVisibility(View.VISIBLE);
        }
        course_id = getIntent().getStringExtra("course_id");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("class_money"))) {
            class_money = getIntent().getStringExtra("class_money");
            class_price_id = getIntent().getIntExtra("price_id", 0);
            tvClassMoney.setText(class_money);
        }
        user_id = (String) SPUtils.get(this, "userId", "");

        initData();
        getClassTime();//获取上课时间
//        initConvenientBannerData();
        //获取课程的总评价数
        getEvaTotalNum();
    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_course_homepager;
    }

    private void initData() {

        Log.e("aaa", "(CourseHomePagerActivity.java:213)<--课程详情页面 user_id -->" + user_id);
        Log.e("aaa", "(CourseHomePagerActivity.java:214)<--课程详情页面 course_id -->" + course_id);
        OkHttpUtils.post()
                .url(Internet.COURSEHOMEPAGE)
                .addParams("user_id", user_id)
                .addParams("course_id", course_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(CourseHomePagerActivity.java:116)" + response);
                        if (response.contains("没有信息")) {
                        } else {

                            Gson gson = new Gson();
                            CourseDertailBean.DataBean courseDetail =
                                    gson.fromJson(response, CourseDertailBean.class).getData();
//                        course_name: 课程名称，course_photo: 课程轮播图，original_price: 原价，preferential_price: 优惠价，course_capacity: 班级容量，enrol_num: 报名人数，
//                        popularity_num: 人气，age_range: 年龄范围，course_teacher: 课程教师，course_address: 学堂地址，course_time: 上课时间，course_money: 课时费用，
//                        course_info: 课程详情，course_type: 课程类型，course_state: 课程状态，
//                        school_id: 学堂id，study_num: 学习码，school_jing: 经度，school_wei: 纬度，user_id: 用户id，hot_time: 热门推荐时间，school_name: 学堂名字
                            school_name = courseDetail.getSchool_name();
                            course_name = courseDetail.getCourse_name();
                            course_time = courseDetail.getCourse_time();
                            enrol_num = courseDetail.getEnrol_num();
                            course_capacity = courseDetail.getCourse_capacity();
                            age_range = courseDetail.getAge_range();
                            course_teacher = courseDetail.getCourse_teacher();
                            original_price = courseDetail.getOriginal_price();
                            preferential_price = courseDetail.getPreferential_price();
                            dengji = courseDetail.getDengji();
                            course_info = courseDetail.getCourse_info();//课堂图片
                            school_id = courseDetail.getSchool_id();
                            schooluid = courseDetail.getUser_id();
                            is_collect = courseDetail.getIs_collect();
                            is_golds = courseDetail.getIs_golds();
                            is_coup = courseDetail.getIs_coups();//可用优惠券
                            school_wei = courseDetail.getSchool_wei();
                            school_jing = courseDetail.getSchool_jing();
                            //课程状态
                            course_state = courseDetail.getCourse_state();

                            courseName.setText(courseDetail.getCourse_name());
                            courseMoneyTv.setText("¥" + courseDetail.getPreferential_price());
                            courseOldmoneyTv.setText("¥" + courseDetail.getOriginal_price());
                            courseOldmoneyTv.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG | Paint.STRIKE_THRU_TEXT_FLAG);
                            courseClassml.setText(courseDetail.getCourse_capacity() + "人" + "(已报名" + enrol_num + "人 )");
                            courseDuixiang.setText(courseDetail.getAge_range());
                            courseTeacher.setText(courseDetail.getCourse_teacher());
                            courseAddress.setText(courseDetail.getCourse_address());
                            tvPopulatrity.setText(courseDetail.getPopularity_num());
                            if ("1".equals(is_golds)) {
                                tvIsGold.setVisibility(View.VISIBLE);
                            } else {
                                tvIsGold.setVisibility(View.GONE);
                            }

                            if ("1".equals(is_coup)) {
                                tvIsDiscount.setVisibility(View.VISIBLE);
                            } else {
                                tvIsDiscount.setVisibility(View.GONE);
                            }

                            if ("2".equals(course_state)) {
                                tvCourseLearnPlan.setBackgroundColor(Color.parseColor("#aaaaaa"));
                                tvCourseApply.setBackgroundColor(Color.parseColor("#aaaaaa"));
                                tvCourseLearnPlan.setEnabled(false);
                                tvCourseApply.setEnabled(false);
                            }

                            if (enrol_num >= course_capacity) {
                                tvCourseLearnPlan.setBackgroundColor(Color.parseColor("#aaaaaa"));
                                tvCourseApply.setBackgroundColor(Color.parseColor("#aaaaaa"));
                                tvCourseLearnPlan.setEnabled(false);
                                tvCourseApply.setEnabled(false);
                            }

                            if ("0".equals(is_collect)) {
                                Drawable top = getResources().getDrawable(R.drawable.yishoucang);
                                courseSave.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                                courseSave.setText("已收藏");
                            }

                            getClassInfo();
                            picture_one = courseDetail.getPicture_one();
                            String picture_two = courseDetail.getPicture_two();
                            String picture_three = courseDetail.getPicture_three();
                            String picture_four = courseDetail.getPicture_four();
                            String picture_five = courseDetail.getPicture_five();
                            if (!TextUtils.isEmpty(picture_one)) {
                                listImg.add(Internet.BASE_URL + picture_one);
                            }
                            if (!TextUtils.isEmpty(picture_two)) {
                                listImg.add(Internet.BASE_URL + picture_two);
                            }
                            if (!TextUtils.isEmpty(picture_three)) {
                                listImg.add(Internet.BASE_URL + picture_three);
                            }
                            if (!TextUtils.isEmpty(picture_four)) {
                                listImg.add(Internet.BASE_URL + picture_four);
                            }
                            if (!TextUtils.isEmpty(picture_five)) {
                                listImg.add(Internet.BASE_URL + picture_five);
                            }


                            setConvenientBanner(listImg);
                        }
                    }
                });
    }

    //课程时间的选择
    private void getClassTime() {
        class_time = "";
        OkHttpUtils.post()
                .url(Internet.COURSETIME)
                .addParams("course_id", course_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(CourseHomePagerActivity.java:264)课程时间" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(CourseHomePagerActivity.this, "暂无课程时间", Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new Gson();
                            CourseTimeBean courseTime = gson.fromJson(response, CourseTimeBean.class);
                            mList = new ArrayList<>();
                            for (int i = 0; i < courseTime.getData().size(); i++) {
                                ArrayList<TimeHourBean> hourList = new ArrayList<>();
                                TimeBean timebean = new TimeBean();
                                timebean.setName(courseTime.getData().get(i).getCtime_week());
                                for (int m = 0; m < courseTime.getData().get(i).getCtime_times().split(",").length; m++) {
                                    TimeHourBean thb = new TimeHourBean();
                                    thb.setTime(courseTime.getData().get(i).getCtime_times().split(",")[m]);
                                    thb.setChecked(false);
                                    hourList.add(thb);
                                }
                                timebean.setMlist(hourList);
                                mList.add(timebean);
                            }
                            for (int i = 0; i < courseTime.getData().size(); i++) {
                                if (i == (courseTime.getData().size() - 1)) {
                                    class_time = class_time + courseTime.getData().get(i).getCtime_week() + courseTime.getData().get(i).getCtime_times();
                                } else
                                    class_time = class_time + courseTime.getData().get(i).getCtime_week() + courseTime.getData().get(i).getCtime_times() + "\n";
                            }

                            Log.e("aaa",
                                    "(CourseHomePagerActivity.java:708)===class_time===" + class_time);
                        }
                    }
                });

    }

    private void getEvaTotalNum() {

        OkHttpUtils.post()
                .url(InternetS.ALLEVALUATE)
                .addParams("course_id", course_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(CourseHomePagerActivity.java:275)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(CourseHomePagerActivity.java:281)<---->" + response);

                        if (!TextUtils.isEmpty(response) && !response.contains("没有信息")) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                Gson gson = new Gson();
                                ArrayList<EvaluateBean> evaluateBeanArrayList = gson.fromJson(data.toString(), new TypeToken<ArrayList<EvaluateBean>>() {
                                }.getType());
                                coursePingjia.setText("评价（" + evaluateBeanArrayList.size() + "）");
                                CHPPingJiaAdapter pingjiaAdapter = new CHPPingJiaAdapter(CourseHomePagerActivity.this, evaluateBeanArrayList);
                                courseMlvPingjia.setAdapter(pingjiaAdapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
    }

    private void getClassInfo() {
        OkHttpUtils.post()
                .url(InternetS.CPH_CLASS_INFO)
                .addParams("school_id", school_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(CourseHomePagerActivity.java:170)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(CourseHomePagerActivity.java:176)" + response);
                        try {
                            Gson gson = new Gson();
                            ClassInfoCHP classInfoCHP = gson.fromJson(response, ClassInfoCHP.class);
                            user_dengji = classInfoCHP.getData().getPingjia();
                            head_photo = classInfoCHP.getData().getHead_photo();
                            class_num = classInfoCHP.getData().getSchoolDataMap().get(0).getClass_num();
                            school_name1 = classInfoCHP.getData().getSchoolDataMap().get(0).getSchool_name();
                            collect_num = classInfoCHP.getData().getSchoolDataMap().get(0).getCollect_num();

                            switch (user_dengji) {
                                case "0":
                                    break;
                                case "1":
                                    ivGradeOne.setImageResource(R.drawable.wujiaoxing);
                                    break;
                                case "2":
                                    ivGradeOne.setImageResource(R.drawable.wujiaoxing);
                                    ivGradeTwo.setImageResource(R.drawable.wujiaoxing);
                                    break;
                                case "3":
                                    ivGradeOne.setImageResource(R.drawable.wujiaoxing);
                                    ivGradeTwo.setImageResource(R.drawable.wujiaoxing);
                                    ivGradeThree.setImageResource(R.drawable.wujiaoxing);
                                    break;
                                case "4":
                                    ivGradeOne.setImageResource(R.drawable.wujiaoxing);
                                    ivGradeTwo.setImageResource(R.drawable.wujiaoxing);
                                    ivGradeThree.setImageResource(R.drawable.wujiaoxing);
                                    ivGradeFour.setImageResource(R.drawable.wujiaoxing);
                                    break;
                                case "5":
                                    ivGradeOne.setImageResource(R.drawable.wujiaoxing);
                                    ivGradeTwo.setImageResource(R.drawable.wujiaoxing);
                                    ivGradeThree.setImageResource(R.drawable.wujiaoxing);
                                    ivGradeFour.setImageResource(R.drawable.wujiaoxing);
                                    ivGradeFive.setImageResource(R.drawable.wujiaoxing);
                                    break;
                            }
                            with(CourseHomePagerActivity.this).load(Internet.BASE_URL + CourseHomePagerActivity.this.head_photo).centerCrop().into(ivClassPhoto);
                            tvAllCourseNum.setText(CourseHomePagerActivity.this.class_num);
                            tvClassName.setText(school_name1);
                            tvLoveNum.setText(CourseHomePagerActivity.this.collect_num);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void setConvenientBanner(List<String> bannerList) {


        convenientBanner.setPages(
                new CBViewHolderCreator<NetWorkImageHolderView>() {
                    @Override
                    public NetWorkImageHolderView createHolder() {
                        return new NetWorkImageHolderView();
                    }
                }, bannerList)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.conven_point_grey, R.drawable.conven_point_blue})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .startTurning(2000);
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);  //  集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
        //   convenientBanner.setManualPageable(false);//设置不能手动影响

    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.course_share_ll, R.id.course_classtime, R.id.course_classcost,
            R.id.course_classdetail, R.id.course_allpingjia_btn, R.id.course_kefu, R.id.course_xuetang, R.id.course_save,
            R.id.course_learnplan, R.id.course_apply, R.id.ll_school_address, R.id.tv_discount_coupon, R.id.ll_school_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.course_share_ll:
                showShare(course_name, course_name, Internet.BASE_URL + picture_one + "", Internet.BASE_URL + "head/tel_zsss/kczy.jsp?course_id=" + course_id);
                break;
            case R.id.course_classtime:
                initClassTime();
                break;
            case R.id.course_classcost:
                initClassCost();
                break;
            case R.id.course_classdetail://课程详情
                Intent intent = new Intent(this, CourseDetailActivity.class);
                intent.putExtra("courseId", course_id);
                startActivity(intent);
                break;
            case R.id.tv_discount_coupon:
                user_phone = (String) SPUtils.get(this, "user_phone", "");
                if (TextUtils.isEmpty(user_phone)) {
                    if (isLogin)
                        showNoLoginDialog();
                    else
                        Toast.makeText(this, "请先登录个人账号！", Toast.LENGTH_SHORT).show();
                } else {
                    if (!isLogin || "1".equals(user_type)) {
                        Toast.makeText(this, "请先登录或者切换个人账号！", Toast.LENGTH_SHORT).show();
                    } else if ("0".equals(is_coup)) {
                        Toast.makeText(this, "暂无可用的优惠券", Toast.LENGTH_SHORT).show();
                    } else {
                        initShopDiscount();
                    }
                }

                break;
            case R.id.ll_school_info:
                //跳转到学堂主页
                Intent intent4 = new Intent(this, ClassActivity.class);
                intent4.putExtra("school_id", school_id);
                startActivity(intent4);
                break;
            case R.id.course_allpingjia_btn:
                startActivity(new Intent(CourseHomePagerActivity.this, CourseDetailActivity.class)
                        .putExtra("from", "chp")
                        .putExtra("courseId", course_id));
                break;
            case R.id.course_kefu:
                if(!isLogin){
                    Toast.makeText(getApplication(),"请先登录",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent3 = new Intent(this, HumanServiceActivity.class);
                intent3.putExtra("type", "0");
                intent3.putExtra("course_id", course_id);
                intent3.putExtra("send_id", schooluid);
                intent3.putExtra("name", school_name);
                startActivity(intent3);
                break;
            case R.id.ll_school_address:
                Log.e("aaa",
                        "(CourseHomePagerActivity.java:458)<--school_wei-->" + school_wei + "<--school_jing-->" + school_jing);
                ArrayList<String> strings = new ArrayList<>();
                strings.add(school_wei + "," + school_jing);
                startActivity(new Intent(this, CourseAddressActivity.class)
                        .putStringArrayListExtra("addresses", strings));
                break;
            case R.id.course_xuetang:
                if(!isLogin){
                    Toast.makeText(getApplication(),"请先登录",Toast.LENGTH_SHORT).show();
                    return;
                }
                //跳转到学堂主页
                Intent intent2 = new Intent(this, ClassActivity.class);
                if (!TextUtils.isEmpty(school_id)) {
                    intent2.putExtra("school_id", school_id);
                    startActivity(intent2);
                }
                break;
            case R.id.course_save:
                if(!isLogin){
                    Toast.makeText(getApplication(),"请先登录",Toast.LENGTH_SHORT).show();
                    return;
                }
                //收藏
                OkHttpUtils.post()
                        .url(Internet.SAVECOURSE)
                        .addParams("user_id", user_id)
                        .addParams("course_id", course_id)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("aaa",
                                        "(ClassActivity.java:153)" + response);

                                if (response.contains("取消收藏成功")) {
                                    Toast.makeText(CourseHomePagerActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                                    Drawable top = getResources().getDrawable(R.drawable.shoucang);
                                    courseSave.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                                    courseSave.setText("收藏");
                                } else {
                                    Toast.makeText(CourseHomePagerActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                    Drawable top = getResources().getDrawable(R.drawable.yishoucang);
                                    courseSave.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                                    courseSave.setText("已收藏");
                                }
                            }
                        });
                break;
            case R.id.course_learnplan:
                if(!isLogin){
                    Toast.makeText(getApplication(),"请先登录",Toast.LENGTH_SHORT).show();
                    return;
                }
//                添加到购物车
                if (TextUtils.isEmpty(class_money)) {
                    Toast.makeText(this, "请选择课时费用", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!class_money.contains("元") || !class_money.contains("节") || !class_money.contains("/")) {
                    Toast.makeText(this, "该课程费用数据异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("2".equals(course_state)) {
                    tvCourseLearnPlan.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    tvCourseApply.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    tvCourseLearnPlan.setEnabled(false);
                    tvCourseApply.setEnabled(false);
                } else if ("1".equals(course_state)) {
                    Toast.makeText(CourseHomePagerActivity.this, "该课程已经开课，如有疑问，请及时与机构取得联系", Toast.LENGTH_SHORT).show();
                }

                OkHttpUtils.post()
                        .url(Internet.SINGUP)
                        .addParams("user_id", user_id)
                        .addParams("course_id", course_id)
                        .addParams("course_num", "1")
                        .addParams("order_money", class_money.split("元")[0])
                        .addParams("class_money", class_money)
                        .addParams("order_course_time", class_time)
                        .addParams("price_id", String.valueOf(class_price_id))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("aaa",
                                        "(CourseHomePagerActivity.java:540)<---->" + e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("aaa",
                                        "(CourseHomePagerActivity.java:270)" + response);
                                try {
                                    JSONObject json = new JSONObject(response);
                                    String msg = json.getString("msg");
                                    Toast.makeText(CourseHomePagerActivity.this, msg, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                break;
            case R.id.course_apply:
                if(!isLogin){
                    Toast.makeText(getApplication(),"请先登录",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(class_money)) {
                    Toast.makeText(this, "请选择课时费用", Toast.LENGTH_SHORT).show();
                    return;
                }
                user_phone = (String) SPUtils.get(this, "user_phone", "");
                if (TextUtils.isEmpty(user_phone)) {
                    showNoLoginDialog();
                    return;
                }
                if (!class_money.contains("元") || !class_money.contains("节") || !class_money.contains("/")) {
                    Toast.makeText(this, "该课程费用数据异常", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ("2".equals(course_state)) {
                    tvCourseLearnPlan.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    tvCourseApply.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    tvCourseLearnPlan.setEnabled(false);
                    tvCourseApply.setEnabled(false);
                } else if ("1".equals(course_state)) {
                    Toast.makeText(CourseHomePagerActivity.this, "该课程已经开课，如有疑问，请及时与机构取得联系", Toast.LENGTH_SHORT).show();
                }

                HashMap params = new HashMap<String, String>();
                params.put("user_id", user_id);
                params.put("course_id", course_id);
                params.put("course_num", "1");
                params.put("order_money", class_money.split("元")[0]);
                params.put("class_money", class_money);
                params.put("price_id", String.valueOf(class_price_id));
                params.put("order_course_time", class_time);

                Log.e("aaa",
                        "(CourseHomePagerActivity.java:539)<--接口请求的参数-->" + params.toString());
                OkHttpUtils.post()
                        .url(Internet.SINGUP)
                        .params(params)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("aaa",
                                        "(CourseHomePagerActivity.java:540)<---->" + e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("aaa",
                                        "(CourseHomePagerActivity.java:270)" + response);
                                try {
                                    JSONObject json = new JSONObject(response);
                                    String msg = json.getString("msg");
                                    Toast.makeText(CourseHomePagerActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    if (response.contains("添加成功") || response.contains("已添加")) {
                                        JSONObject data = json.getJSONObject("data");
                                        String order_id = data.getString("order_id");
                                        Intent intent1 = new Intent(CourseHomePagerActivity.this, NowApplyActivity.class);
                                        intent1.putExtra("school_id", school_id);
                                        intent1.putExtra("school_name", school_name);
                                        intent1.putExtra("course_name", course_name);
                                        intent1.putExtra("course_time", class_time);
                                        intent1.putExtra("enrol_num", enrol_num + "");
                                        intent1.putExtra("course_capacity", course_capacity + "");
                                        intent1.putExtra("age_range", age_range);
                                        intent1.putExtra("course_teacher", course_teacher);
                                        intent1.putExtra("original_price", original_price);
                                        intent1.putExtra("preferential_price", preferential_price);
                                        intent1.putExtra("class_money", class_money);
                                        intent1.putExtra("course_id", course_id);
                                        intent1.putExtra("order_id", order_id);
                                        intent1.putExtra("course_num", "1");
                                        intent1.putExtra("is_golds", is_golds);
                                        intent1.putExtra("is_coup", is_coup);

                                        startActivity(intent1);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                break;
        }
    }

    //课程时间的选择
    private void initClassTime() {
        class_time = "";
        OkHttpUtils.post()
                .url(Internet.COURSETIME)
                .addParams("course_id", course_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(CourseHomePagerActivity.java:823)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(CourseHomePagerActivity.java:264)课程时间" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(CourseHomePagerActivity.this, "暂无课程时间", Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new Gson();
                            CourseTimeBean courseTime = gson.fromJson(response, CourseTimeBean.class);
                            mList = new ArrayList<>();
                            for (int i = 0; i < courseTime.getData().size(); i++) {
                                ArrayList<TimeHourBean> hourList = new ArrayList<>();
                                TimeBean timebean = new TimeBean();
                                timebean.setName(courseTime.getData().get(i).getCtime_week());
                                for (int m = 0; m < courseTime.getData().get(i).getCtime_times().split(",").length; m++) {
                                    TimeHourBean thb = new TimeHourBean();
                                    thb.setTime(courseTime.getData().get(i).getCtime_times().split(",")[m]);
                                    thb.setChecked(false);
                                    hourList.add(thb);
                                }
                                timebean.setMlist(hourList);
                                mList.add(timebean);
                            }
                            for (int i = 0; i < courseTime.getData().size(); i++) {
                                if (i == (courseTime.getData().size() - 1)) {
                                    class_time = class_time + courseTime.getData().get(i).getCtime_week() + courseTime.getData().get(i).getCtime_times();
                                } else
                                    class_time = class_time + courseTime.getData().get(i).getCtime_week() + courseTime.getData().get(i).getCtime_times() + "\n";
                            }
                            Log.e("aaa",
                                    "(CourseHomePagerActivity.java:630)===class_time===" + class_time);
                        }
                        View v = View.inflate(CourseHomePagerActivity.this, R.layout.class_time, null);
                        ImageView classtime_back = (ImageView) v.findViewById(R.id.classtime_back);//关闭
                        ListView mylistview = (ListView) v.findViewById(R.id.mylistview);
                        TextView classtime_config = (TextView) v.findViewById(R.id.classtime_config);
                        final MyPopupWindow courTimePoP = new MyPopupWindow(CourseHomePagerActivity.this, v);
                        courTimePoP.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                        ClassTimeAdapter ctAdapter = new ClassTimeAdapter(CourseHomePagerActivity.this, mList);
                        mylistview.setAdapter(ctAdapter);
                        classtime_back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                courTimePoP.dismiss();
                            }
                        });
                        classtime_config.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                courTimePoP.dismiss();
                            }
                        });
                        courTimePoP.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                // 设置背景颜色变暗
                                WindowManager.LayoutParams lp = getWindow().getAttributes();
                                lp.alpha = 1f;
                                getWindow().setAttributes(lp);
                            }
                        });

                    }
                });

    }

    //课堂费用选择
    private void initClassCost() {
        OkHttpUtils.post()
                .url(Internet.COURSEMONEY)
                .addParams("course_id", course_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(CourseHomePagerActivity.java:847)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(CourseHomePagerActivity.java:225)费用" + response);
                        Gson gson = new Gson();
                        final ArrayList<String> costs = new ArrayList<String>();//价格
                        final ArrayList<Integer> costId = new ArrayList<Integer>();//价格Id
                        if (response.contains("没有信息")) {
                        } else {
                            CostBean costBean = gson.fromJson(response, CostBean.class);
                            for (int i = 0; i < costBean.getData().size(); i++) {
                                costs.add(costBean.getData().get(i).getPrice_num());
                                costId.add(costBean.getData().get(i).getPrice_id());
                            }
                        }
                        View v = View.inflate(CourseHomePagerActivity.this, R.layout.class_money, null);
                        ImageView classmoney_back = (ImageView) v.findViewById(R.id.classmoney_back);
                        final MyGridView classmoney_mlv = (MyGridView) v.findViewById(R.id.classmoney_mlv);
                        TextView classmoney_config = (TextView) v.findViewById(R.id.classmoney_config);
                        final MyPopupWindow courTimePoP = new MyPopupWindow(CourseHomePagerActivity.this, v);
                        courTimePoP.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                        costAdapter = new CostAdapter(CourseHomePagerActivity.this, costs,class_money);
                        costAdapter.setCbClick(new CostAdapter.CbClick() {
                            @Override
                            public void onCBClick(int postion) {
                                class_money = costs.get(postion);
                                class_price_id = costId.get(postion);
                            }
                        });
                        classmoney_mlv.setAdapter(costAdapter);
                        classmoney_back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                courTimePoP.dismiss();
                            }
                        });
                        classmoney_config.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tvClassMoney.setText(class_money);
                                courTimePoP.dismiss();
                            }
                        });
                        courTimePoP.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                // 设置背景颜色变暗
                                WindowManager.LayoutParams lp = getWindow().getAttributes();
                                lp.alpha = 1f;
                                getWindow().setAttributes(lp);
                            }
                        });

                    }
                });

    }

    private void showNoLoginDialog() {
        final SelfDialog selfDialog = new SelfDialog(CourseHomePagerActivity.this);

        selfDialog.setMessage("是否绑定手机号?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                startActivity(new Intent(CourseHomePagerActivity.this, RegisterPersonActivity.class)
                        .putExtra("flag", "true")
                        .putExtra("type", "0"));
//                finish();
                selfDialog.dismiss();
            }
        });


        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {

                selfDialog.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
    }

    //领取店铺优惠券
    private void initShopDiscount() {

        Log.e("aaa",
                "(CourseHomePagerActivity.java:663)<--schoolId-->" + school_id);
        Log.e("aaa",
                "(CourseHomePagerActivity.java:663)<--user_id-->" + user_id);
        OkHttpUtils.post()
                .url(Internet.DISCOUNT_PAGE_LIST)
                .addParams("school_id", school_id)
                .addParams("user_id", user_id)
                .addParams("course_id", course_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(CourseHomePagerActivity.java:669)<---->" + e.getMessage());
                        Toast.makeText(CourseHomePagerActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(CourseHomePagerActivity.java:675)<---->" + response);
                        if (TextUtils.isEmpty(response) || response.contains("{}") || response.contains("失败")) {
                            Toast.makeText(CourseHomePagerActivity.this, "无可用的优惠券", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int result = jsonObject.getInt("result");
                                if (result == -1) {
                                    String msg = jsonObject.getString("msg");
                                    Toast.makeText(CourseHomePagerActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            DiscountPageBean discountPageBean = new Gson().fromJson(response, DiscountPageBean.class);
                            View v = View.inflate(CourseHomePagerActivity.this, R.layout.class_discount_pager, null);
                            final MyPopupWindow discountView = new MyPopupWindow(CourseHomePagerActivity.this, v);
                            discountView.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                            ListView lvDiscount = (ListView) v.findViewById(R.id.lv_discount_page);
                            Button btnComplete = (Button) v.findViewById(R.id.btn_complete);
                            final ArrayList<DiscountPageBean.DataBean> discountlist = new ArrayList<>();
                            discountlist.addAll(discountPageBean.getData());
                            DiscountPageAdapter discountPageAdapter = new DiscountPageAdapter(CourseHomePagerActivity.this, discountlist);
                            lvDiscount.setAdapter(discountPageAdapter);

                            discountPageAdapter.setListener(new DiscountPageAdapter.OnAdapterOnClick() {
                                @Override
                                public void onClick(int position) {
                                    discountView.dismiss();
                                }
                            });
                            btnComplete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    discountView.dismiss();
                                }
                            });
                            discountView.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                @Override
                                public void onDismiss() {
                                    // 设置背景颜色变暗
                                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                                    lp.alpha = 1f;
                                    getWindow().setAttributes(lp);
                                }
                            });
                        }
                    }
                });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    class NetWorkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {
//            imageView.setImageResource(data);
            if (data != null) {
                with(CourseHomePagerActivity.this).load(data).into(imageView);
                // ImageLoader.getInstance().displayImage(data, imageView, options);
            } else {
                imageView.setImageResource(R.drawable.meinv);
            }
        }
    }

    /**
     * 添加弹出的dialog关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements Dialog.OnDismissListener {


        @Override
        public void onDismiss(DialogInterface dialog) {
            backgroundAlpha(1f);
        }
    }
}
