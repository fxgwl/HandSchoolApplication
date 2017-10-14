package com.example.handschoolapplication.activity;

import android.content.Context;
import android.content.Intent;
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

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ClassTimeAdapter;
import com.example.handschoolapplication.adapter.TimeAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.CourseDertailBean;
import com.example.handschoolapplication.bean.TimeBean;
import com.example.handschoolapplication.bean.TimeHourBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.MyGridView;
import com.example.handschoolapplication.view.MyListView;
import com.example.handschoolapplication.view.MyPopupWindow;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

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
    private ConvenientBanner convenientBanner;
    private List<String> listImg = new ArrayList<>();

    private List<TimeHourBean> mHourList;//每周的具体小时上课时间
    private List<TimeBean> mList;

    private List<TimeHourBean> mFeeList;//课时费用
    private String course_id;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        tvTitle.setText("课程主页");
        course_id = getIntent().getStringExtra("course_id");
        user_id = (String) SPUtils.get(this, "userId", "");
        initData();
//        initConvenientBannerData();
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
                break;
            case R.id.course_kefu:
                startActivity(new Intent(this, HumanServiceActivity.class));
                break;
            case R.id.course_xuetang:
                startActivity(new Intent(this, ClassActivity.class));
                break;
            case R.id.course_save:
                break;
            case R.id.course_learnplan:
                break;
            case R.id.course_apply:
                Intent intent1 = new Intent(this, NowApplyActivity.class);
                startActivity(intent1);
                break;
        }
    }

    //课堂费用选择
    private void initClassCost() {
        View v = View.inflate(CourseHomePagerActivity.this, R.layout.class_money, null);
        ImageView classmoney_back = (ImageView) v.findViewById(R.id.classmoney_back);
        MyGridView classmoney_mlv = (MyGridView) v.findViewById(R.id.classmoney_mlv);
        TextView classmoney_config = (TextView) v.findViewById(R.id.classmoney_config);
        final MyPopupWindow courTimePoP = new MyPopupWindow(this, v);
        courTimePoP.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        mHourList = new ArrayList<>();
        mHourList.add(new TimeHourBean());
        mHourList.add(new TimeHourBean());
        mHourList.add(new TimeHourBean());
        mHourList.add(new TimeHourBean());
        TimeAdapter timeAdapter = new TimeAdapter(this, mHourList);
        classmoney_mlv.setAdapter(timeAdapter);

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
                    }
                });
        View v = View.inflate(CourseHomePagerActivity.this, R.layout.class_time, null);
        ImageView classtime_back = (ImageView) v.findViewById(R.id.classtime_back);
        ListView mylistview = (ListView) v.findViewById(R.id.mylistview);
        TextView classtime_config = (TextView) v.findViewById(R.id.classtime_config);
        final MyPopupWindow courTimePoP = new MyPopupWindow(this, v);
        courTimePoP.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        mList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ArrayList<TimeHourBean> hourList = new ArrayList<>();
            TimeBean timebean = new TimeBean();
            for (int m = 0; m < 5; m++) {
                TimeHourBean thb = new TimeHourBean();
                thb.setChecked(false);
                hourList.add(thb);
            }
            timebean.setMlist(hourList);
            mList.add(timebean);
        }
        ClassTimeAdapter ctAdapter = new ClassTimeAdapter(this, mList);
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
