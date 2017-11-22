package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.SchoolIntroBean;
import com.example.handschoolapplication.fragment.ClassConditionFragment;
import com.example.handschoolapplication.fragment.ClassCourseFragment;
import com.example.handschoolapplication.fragment.ClassInfoFragment;
import com.example.handschoolapplication.fragment.ClassTeacherFragment;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.MenuPopupWindow;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


public class ClassActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.iv_grade)
    ImageView ivGrade;
    @BindView(R.id.tv_bg_info_line)
    TextView tvBgInfoLine;
    @BindView(R.id.bg_condition_line)
    TextView bgConditionLine;
    @BindView(R.id.bg_tearcher_line)
    TextView bgTearcherLine;
    @BindView(R.id.bg_course_line)
    TextView bgCourseLine;
    @BindView(R.id.tv_schoolname)
    TextView tvSchoolname;
    @BindView(R.id.ll_course)
    LinearLayout llCourse;
    @BindView(R.id.iv_love)
    ImageView ivLove;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.iv4)
    ImageView iv4;
    @BindView(R.id.iv5)
    ImageView iv5;
    @BindView(R.id.tv_pingfen)
    TextView tvPingfen;

    private int mIndex = 0;

    private Fragment[] fragments;
    private ClassInfoFragment classInfoFragment;
    private ClassConditionFragment classConditionFragment;
    private ClassTeacherFragment classTeacherFragment;
    private ClassCourseFragment classCourseFragment;
    private String school_id;
    private String user_id;
    private SchoolIntroBean school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        school_id = getIntent().getStringExtra("school_id");
        user_id = (String) SPUtils.get(this, "userId", "");
        initView();
        tvTitle.setText("学堂主页");
    }

    //初始化学堂简介
    private void initView() {
        OkHttpUtils.post()
                .url(Internet.SCHOOLINTO)
                .addParams("school_id", school_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ClassActivity.java:79)" + response);
                        Gson gson = new Gson();
                        school = gson.fromJson(response, SchoolIntroBean.class);
                        Glide.with(ClassActivity.this)
                                .load(Internet.BASE_URL + school.getData().getHead_photo())
                                .centerCrop()
                                .error(R.drawable.zhangshangsishu)
                                .into(ivIcon);
                        tvSchoolname.setText(school.getData().getSchoolData().getSchool_name());
                        switch (school.getData().getPingjia()) {
                            case "0":
                                tvPingfen.setText(0 + "分");
                                break;
                            case "1":
                                iv1.setImageResource(R.drawable.wujiaoxing);
                                tvPingfen.setText(1 + "分");
                                break;
                            case "2":
                                iv1.setImageResource(R.drawable.wujiaoxing);
                                iv2.setImageResource(R.drawable.wujiaoxing);
                                tvPingfen.setText(2 + "分");
                                break;
                            case "3":
                                iv1.setImageResource(R.drawable.wujiaoxing);
                                iv2.setImageResource(R.drawable.wujiaoxing);
                                iv3.setImageResource(R.drawable.wujiaoxing);
                                tvPingfen.setText(3 + "分");
                                break;
                            case "4":
                                tvPingfen.setText(4 + "分");
                                iv1.setImageResource(R.drawable.wujiaoxing);
                                iv2.setImageResource(R.drawable.wujiaoxing);
                                iv3.setImageResource(R.drawable.wujiaoxing);
                                iv4.setImageResource(R.drawable.wujiaoxing);
                                break;
                            case "5":
                                tvPingfen.setText(5 + "分");
                                iv1.setImageResource(R.drawable.wujiaoxing);
                                iv2.setImageResource(R.drawable.wujiaoxing);
                                iv3.setImageResource(R.drawable.wujiaoxing);
                                iv4.setImageResource(R.drawable.wujiaoxing);
                                iv5.setImageResource(R.drawable.wujiaoxing);
                                break;
                        }
                        switch (school.getData().getUser_dengji()){
                            case "0":
                            case "1":
                                ivGrade.setImageResource(R.drawable.xuetangdengji_tong);
                                break;
                            case "2":
                                ivGrade.setImageResource(R.drawable.xuetangdengji_yin);
                                break;
                            case "3":
                                ivGrade.setImageResource(R.drawable.xuetangdengji_gold);
                                break;
                        }

                        initFragments();
                    }
                });
    }

    private void initFragments() {
        Bundle bundle = new Bundle();
        bundle.putString("school_id", school_id);
        bundle.putDouble("longitude", Double.parseDouble(school.getData().getSchoolData().getSchool_jing()));
        bundle.putDouble("latitude", Double.parseDouble(school.getData().getSchoolData().getSchool_wei()));
        classInfoFragment = new ClassInfoFragment();
        classConditionFragment = new ClassConditionFragment();
        classTeacherFragment = new ClassTeacherFragment();
        classCourseFragment = new ClassCourseFragment();

        classInfoFragment.setArguments(bundle);
        classConditionFragment.setArguments(bundle);
        classTeacherFragment.setArguments(bundle);
        classCourseFragment.setArguments(bundle);
        fragments = new Fragment[]{
                classInfoFragment, classConditionFragment, classTeacherFragment, classCourseFragment
        };

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fl_fragment, fragments[0]).commit();
        setIndexSelected(0);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_class;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_love, R.id.ll_share, R.id.ll_info, R.id.ll_condition, R.id.ll_tearcher, R.id.ll_course})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.ll_love:
                //收藏
                OkHttpUtils.post()
                        .url(Internet.SAVECLASS)
                        .addParams("user_id", user_id)
                        .addParams("school_id", school_id)
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
                                    Toast.makeText(ClassActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                                    ivLove.setImageResource(R.drawable.shoucang);
                                } else {
                                    Toast.makeText(ClassActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                    ivLove.setImageResource(R.drawable.yishoucang);
                                }
                            }
                        });
                break;
            case R.id.ll_share:
                showShare("我是标题", "我的分享文本", "", "");
                break;
            case R.id.ll_info:
                tvBgInfoLine.setBackgroundResource(R.color.blue);
                bgConditionLine.setBackgroundResource(R.color.white);
                bgTearcherLine.setBackgroundResource(R.color.white);
                bgCourseLine.setBackgroundResource(R.color.white);
                setIndexSelected(0);
                break;
            case R.id.ll_condition:
                bgConditionLine.setBackgroundResource(R.color.blue);
                tvBgInfoLine.setBackgroundResource(R.color.white);
                bgTearcherLine.setBackgroundResource(R.color.white);
                bgCourseLine.setBackgroundResource(R.color.white);
                setIndexSelected(1);
                break;
            case R.id.ll_tearcher:
                bgTearcherLine.setBackgroundResource(R.color.blue);
                tvBgInfoLine.setBackgroundResource(R.color.white);
                bgConditionLine.setBackgroundResource(R.color.white);
                bgCourseLine.setBackgroundResource(R.color.white);
                setIndexSelected(2);
                break;
            case R.id.ll_course:
                View view1 = View.inflate(this, R.layout.course_type, null);
                final MenuPopupWindow myPopupWindow = new MenuPopupWindow(this, view1);
                TextView tv_course_finish = (TextView) view1.findViewById(R.id.tv_course_finish);
                TextView tv_course_baoming = (TextView) view1.findViewById(R.id.tv_course_baoming);
                int intw = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                int inth = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                view1.measure(intw, inth);
                int hight = view1.getMeasuredHeight();
                int width = view1.getMeasuredWidth();
                myPopupWindow.setHeight(hight);
                myPopupWindow.setWidth(width);
                myPopupWindow.showAsDropDown(llCourse);
                tv_course_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myPopupWindow.dismiss();
                        EventBus.getDefault().post("1");
                    }
                });
                tv_course_baoming.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myPopupWindow.dismiss();
                        EventBus.getDefault().post("0");
                    }
                });
                myPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                bgCourseLine.setBackgroundResource(R.color.blue);
                tvBgInfoLine.setBackgroundResource(R.color.white);
                bgConditionLine.setBackgroundResource(R.color.white);
                bgTearcherLine.setBackgroundResource(R.color.white);
                setIndexSelected(3);
                break;
        }
    }

    private void setIndexSelected(int index) {

        if (mIndex == index) {
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(fragments[mIndex]);
        if (!fragments[index].isAdded()) {
            ft.add(R.id.fl_fragment, fragments[index]);
        } else {
            ft.show(fragments[index]);
        }
        ft.commit();
        mIndex = index;
    }
}
