package com.example.handschoolapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ClassTimeAdapter;
import com.example.handschoolapplication.adapter.CostAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.CostBean;
import com.example.handschoolapplication.bean.CourseDertailBean;
import com.example.handschoolapplication.bean.CourseTimeBean;
import com.example.handschoolapplication.bean.TimeBean;
import com.example.handschoolapplication.bean.TimeHourBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.MyGridView;
import com.example.handschoolapplication.view.MyListView;
import com.example.handschoolapplication.view.MyPopupWindow;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class CourseHomePagerActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
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
    @BindView(R.id.course_classcost)
    TextView courseClasscost;
    @BindView(R.id.course_classdetail)
    TextView courseClassdetail;
    @BindView(R.id.course_pingjia)
    TextView coursePingjia;
    @BindView(R.id.course_mlv_pingjia)
    MyListView courseMlvPingjia;
    @BindView(R.id.course_save)
    TextView courseSave;
    private ConvenientBanner convenientBanner;
    private List<String> listImg = new ArrayList<>();


    private List<TimeBean> mList;

    private List<TimeHourBean> mFeeList;//课时费用
    private String course_id;
    private String user_id;
    private String school_id;
    private String schooluid;
    private String class_money = "";
    private String school_name;
    private String course_name;
    private String course_time;
    private String enrol_num;
    private String course_capacity;
    private String age_range;
    private String course_teacher;
    private String original_price;
    private String preferential_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        tvTitle.setText("课程主页");
        course_id = getIntent().getStringExtra("course_id");
        school_id = getIntent().getStringExtra("school_id");
        schooluid = getIntent().getStringExtra("schooluid");
        user_id = (String) SPUtils.get(this, "userId", "");
        initData();
//        initConvenientBannerData();
        //获取课程的总评价数
        getEvaTotalNum();
    }

    private void getEvaTotalNum() {

        OkHttpUtils.post()
                .addParams("course_id", course_id)
                .url(InternetS.TOTALEVANUM)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(CourseHomePagerActivity.java:123)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(CourseHomePagerActivity.java:129)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int data = jsonObject.getInt("data");
                            coursePingjia.setText("评价（" + data + "）");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initData() {
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

                        courseName.setText(courseDetail.getCourse_name());
                        courseMoneyTv.setText("¥" + courseDetail.getPreferential_price());
                        courseOldmoneyTv.setText("¥" + courseDetail.getOriginal_price());
                        courseClassml.setText(courseDetail.getCourse_capacity());
                        courseDuixiang.setText(courseDetail.getAge_range());
                        courseTeacher.setText(courseDetail.getCourse_teacher());
                        courseAddress.setText(courseDetail.getCourse_address());
                        String photos = courseDetail.getCourse_photo();
                        if (!TextUtils.isEmpty(photos)) {
                            if (photos.contains(",")) {
                                for (String photo :
                                        photos.split(",")) {
                                    listImg.add(Internet.BASE_URL + photo);
                                }
                            } else {
                                listImg.add(Internet.BASE_URL + photos);
                            }
                        }
                        setConvenientBanner(listImg);
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_course_homepager;
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

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.course_share_ll, R.id.course_classtime, R.id.course_classcost, R.id.course_classdetail, R.id.course_allpingjia_btn, R.id.course_kefu, R.id.course_xuetang, R.id.course_save, R.id.course_learnplan, R.id.course_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.course_share_ll:

                break;
            case R.id.course_classtime:
                initClassTime();
                break;
            case R.id.course_classcost:
                initClassCost();
                break;
            case R.id.course_classdetail://课程详情
                Intent intent = new Intent(this, CourseDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.course_allpingjia_btn:
                startActivity(new Intent(CourseHomePagerActivity.this, CourseDetailActivity.class)
                        .putExtra("from", "chp")
                        .putExtra("courseId", course_id));
                break;
            case R.id.course_kefu:
                Intent intent3 = new Intent(this, HumanServiceActivity.class);
                intent3.putExtra("type", "0");
                intent3.putExtra("course_id", course_id);
                intent3.putExtra("schooluid", schooluid);
                startActivity(intent3);
                break;
            case R.id.course_xuetang:
                //跳转到学堂主页
                Intent intent2 = new Intent(this, ClassActivity.class);
                intent2.putExtra("school_id", school_id);
                startActivity(intent2);
                break;
            case R.id.course_save:
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
                                    Drawable top = getResources().getDrawable(R.drawable.wujiaoxinghuise);
                                    courseSave.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                                } else {
                                    Toast.makeText(CourseHomePagerActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                    Drawable top = getResources().getDrawable(R.drawable.wujiaoxing);
                                    courseSave.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                                }
                            }
                        });
                break;
            case R.id.course_learnplan:
                //添加到购物车
//                if (TextUtils.isEmpty(class_money)) {
//                    Toast.makeText(this, "请选择课时费用", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                OkHttpUtils.post()
                        .url(Internet.SINGUP)
                        .addParams("user_id", user_id)
                        .addParams("course_id", course_id)
                        .addParams("course_num", "1")
                        .addParams("order_money", courseOldmoneyTv.getText().toString().split("¥")[1])
                        .addParams("class_money", class_money)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

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
//                if (TextUtils.isEmpty(class_money)) {
//                    Toast.makeText(this, "请选择课时费用", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                Intent intent1 = new Intent(this, NowApplyActivity.class);
                intent1.putExtra("school_id", school_id);
                intent1.putExtra("school_name", school_name);
                intent1.putExtra("course_name", course_name);
                intent1.putExtra("course_time", course_time);
                intent1.putExtra("enrol_num", enrol_num);
                intent1.putExtra("course_capacity", course_capacity);
                intent1.putExtra("age_range", age_range);
                intent1.putExtra("course_teacher", course_teacher);
                intent1.putExtra("original_price", original_price);
                intent1.putExtra("preferential_price", preferential_price);
                intent1.putExtra("class_money", class_money);
                intent1.putExtra("course_id", course_id);
                startActivity(intent1);
                break;
        }
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

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(CourseHomePagerActivity.java:225)费用" + response);
                        Gson gson = new Gson();
                        final ArrayList<String> costs = new ArrayList<String>();
                        if (response.contains("没有信息")) {
                        } else {
                            CostBean costBean = gson.fromJson(response, CostBean.class);
                            for (int i = 0; i < costBean.getData().size(); i++) {
                                costs.add(costBean.getData().get(i).getPrice_num());
                            }
                        }
                        View v = View.inflate(CourseHomePagerActivity.this, R.layout.class_money, null);
                        ImageView classmoney_back = (ImageView) v.findViewById(R.id.classmoney_back);
                        final MyGridView classmoney_mlv = (MyGridView) v.findViewById(R.id.classmoney_mlv);
                        TextView classmoney_config = (TextView) v.findViewById(R.id.classmoney_config);
                        final MyPopupWindow courTimePoP = new MyPopupWindow(CourseHomePagerActivity.this, v);
                        courTimePoP.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                        CostAdapter costAdapter = new CostAdapter(CourseHomePagerActivity.this, costs);
                        costAdapter.setCbClick(new CostAdapter.CbClick() {
                            @Override
                            public void onCBClick(int postion) {
                                class_money = costs.get(postion);
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

    //课程时间的选择
    private void initClassTime() {
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
                        Log.e("aaa",
                                "(CourseHomePagerActivity.java:283)" + mList.toString());
                        View v = View.inflate(CourseHomePagerActivity.this, R.layout.class_time, null);
                        ImageView classtime_back = (ImageView) v.findViewById(R.id.classtime_back);
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
                Glide.with(CourseHomePagerActivity.this).load(data).into(imageView);
                // ImageLoader.getInstance().displayImage(data, imageView, options);
            } else {
                imageView.setImageResource(R.drawable.meinv);
            }
        }
    }
}
