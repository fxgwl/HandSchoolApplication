package com.example.handschoolapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.AllCourseActivity;
import com.example.handschoolapplication.activity.GradeActivity;
import com.example.handschoolapplication.activity.HelpActivity;
import com.example.handschoolapplication.activity.LearningActivity;
import com.example.handschoolapplication.activity.MyBroswerActivity;
import com.example.handschoolapplication.activity.MyDiscountcouponActivity;
import com.example.handschoolapplication.activity.MyEvaluateActivity;
import com.example.handschoolapplication.activity.MyInformationActivity;
import com.example.handschoolapplication.activity.MyLoveActivity;
import com.example.handschoolapplication.activity.MyOrderActivity;
import com.example.handschoolapplication.activity.ScanQRCodeActivity;
import com.example.handschoolapplication.activity.SettingsActivity;
import com.example.handschoolapplication.activity.SignActivity;
import com.example.handschoolapplication.activity.WebGradeActivity;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.CourseNumBean;
import com.example.handschoolapplication.bean.LearningCourseBean;
import com.example.handschoolapplication.bean.RefreshData;
import com.example.handschoolapplication.bean.SchoolInfoBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

import static com.bumptech.glide.Glide.with;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {

    @BindView(R.id.civ_usericon)
    CircleImageView civUsericon;
    @BindView(R.id.tv_percent)
    TextView tvPercent;
    @BindView(R.id.tv_days)
    TextView tvDays;
    @BindView(R.id.tv_gold_num)
    TextView tvGoldNum;
    @BindView(R.id.ll_dengji)
    LinearLayout llDengji;
    @BindView(R.id.tv_all_num)
    TextView tvAllNum;
    @BindView(R.id.tv_learning_num)
    TextView tv_learning_num;
    @BindView(R.id.tv_pay_num)
    TextView tv_pay_num;
    @BindView(R.id.tv_evlua_num)
    TextView tv_evlua_num;
    @BindView(R.id.tv_grade)
    TextView tvGrade;
    Unbinder unbinder;

    private View view;
    private int REQUEST_CODE;
    private String user_id;
    private SchoolInfoBean.DataBean dataBean;
    private String signed_num;
    private String totle_sign;
    private List<LearningCourseBean> mList = new ArrayList<>();
    private List<LearningCourseBean> mList2 = new ArrayList<>();//学习中
    private List<LearningCourseBean> mList3 = new ArrayList<>();//代付款
    private List<LearningCourseBean> mList4 = new ArrayList<>();//待评价
    private List<LearningCourseBean> mList5 = new ArrayList<>();//学习已完成
    private int allnum;
    private int learningnum;
    private int paynum;
    private int evluaingnum;
    private String head_photo;
    private String user_integral;
    private String user_dengji;
    private String user_name;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        user_id = (String) SPUtils.get(getActivity(), "userId", "");
        EventBus.getDefault().register(this);
//        initView();

        initView();
        initData();

        //判断签到  后台需要  前台没用
        isSign();
        return view;
    }

    /**
     * 获取这个页面的基本参数（个人信息）
     */
    private void initView() {
        OkHttpUtils.post()
                .url(Internet.USERINFO)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(MeFragment.java:160)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(MeFragment.java:107)" + response);

                        if (TextUtils.isEmpty(response) || response.contains("没有信息")) {
                            return;
                        }
                        if (!TextUtils.isEmpty(response)) {
                            Gson gson = new Gson();
                            try {
                                dataBean = gson.fromJson(response, SchoolInfoBean.class).getData();
                                with(getActivity())
                                        .load(Internet.BASE_URL + dataBean.getHead_photo())
                                        .centerCrop()
                                        .error(R.drawable.morentouxiang)
                                        .into(civUsericon);
                                tvPercent.setText(dataBean.getData_integrity() + "%");
                                tvDays.setText(dataBean.getSigned_num()+"天");
                                tvGoldNum.setText(dataBean.getUser_gold()+"金币");
                                signed_num = dataBean.getSigned_num();
                                totle_sign = dataBean.getTotle_sign();
                                head_photo = dataBean.getHead_photo();
                                user_integral = dataBean.getUser_integral();
                                user_dengji = dataBean.getUser_dengji();
                                user_name = dataBean.getUser_name();
                                tvGrade.setText("Lv." + user_dengji);

                            } catch (Exception e) {

                            }
                        }

                    }
                });
    }

    private void initData() {
        allnum = 0;
        learningnum = 0;
        paynum = 0;
        evluaingnum = 0;
        mList.clear();
        mList2.clear();
        mList3.clear();
        mList4.clear();
        mList5.clear();
        OkHttpUtils.post()
                .url(Internet.ALLORDER)
                .addParams("user_id", user_id)
                .addParams("type", "course")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(LearningActivity.java:61)" + e.getMessage());
//                        Toast.makeText(getActivity(), "获取消息数量失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AllCourseActivity.java:86)" + response);
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                ArrayList<LearningCourseBean> learningCourseBeen = new ArrayList<>();
                                learningCourseBeen.addAll((Collection<? extends LearningCourseBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<LearningCourseBean>>() {
                                }.getType()));
                                for (int i = 0; i < learningCourseBeen.size(); i++) {
                                    if ("2".equals(learningCourseBeen.get(i).getOrder_state())) {
                                        mList.add(learningCourseBeen.get(i));
                                        mList4.add(learningCourseBeen.get(i));
                                    } else if ("1".equals(learningCourseBeen.get(i).getOrder_state()) || "3".equals(learningCourseBeen.get(i).getOrder_state())) {
                                        mList.add(learningCourseBeen.get(i));
                                    } else if ("0".equals(learningCourseBeen.get(i).getOrder_state())) {
                                        mList3.add(learningCourseBeen.get(i));
                                    }

                                    String course_hour = "0";
                                    if (null != learningCourseBeen.get(i).getClass_money()) {
                                        String string = learningCourseBeen.get(i).getClass_money();
                                        course_hour = string.split("/")[1].split("节")[0];
                                    }

                                    int allTime = Integer.parseInt(course_hour);//总学时

                                    String order_state = learningCourseBeen.get(i).getOrder_state();//订单状态
                                    String course_state = "0";
                                    try {
                                        course_state = learningCourseBeen.get(i).getCourseInfo().getCourse_state();//课程状态
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    int study_class = 0;
                                    if (learningCourseBeen.get(i).getClassSign() != null) {
                                        study_class = learningCourseBeen.get(i).getClassSign().getStudy_class();
                                    }
                                    boolean isFinish = false;
                                    if (study_class >= allTime) {
                                        isFinish = true;
                                    }

                                    if ("1".equals(order_state) || "2".equals(order_state) || "3".equals(order_state)) {
                                        if ("0".equals(course_state) || order_state.equals("1")) {

                                        } else if (isFinish || course_state.equals("2")) {
                                            mList5.add(learningCourseBeen.get(i));
                                        } else {
                                            mList2.add(learningCourseBeen.get(i));
                                        }
                                    }
                                }

                                allnum = mList.size();
                                learningnum = mList2.size();
                                paynum = mList3.size();
                                evluaingnum = mList4.size();
                                int finishLearn = mList5.size();

                                allnum -= finishLearn;

                                Log.e("aaa",
                                        "(MeFragment.java:380)<--全部课程的数量-->" + allnum);
                                Log.e("aaa",
                                        "(MeFragment.java:382)<--正在学习的数量-->" + learningnum);
                                Log.e("aaa",
                                        "(MeFragment.java:384)<--代付款的数量-->" + paynum);
                                Log.e("aaa",
                                        "(MeFragment.java:386)<--待评价的数量-->" + evluaingnum);


                                if (allnum > 0) {
                                    tvAllNum.setText(allnum + "");
                                    tvAllNum.setVisibility(View.VISIBLE);
                                } else {
                                    tvAllNum.setVisibility(View.GONE);
                                }
                                if (learningnum > 0) {
                                    tv_learning_num.setText(learningnum + "");
                                    tv_learning_num.setVisibility(View.VISIBLE);
                                } else {
                                    tv_learning_num.setVisibility(View.GONE);
                                }
                                if (paynum > 0) {
                                    tv_pay_num.setText(paynum + "");
                                    tv_pay_num.setVisibility(View.VISIBLE);
                                } else {
                                    tv_pay_num.setVisibility(View.GONE);
                                }
                                if (evluaingnum > 0) {
                                    tv_evlua_num.setText(evluaingnum + "");
                                    tv_evlua_num.setVisibility(View.VISIBLE);
                                } else {
                                    tv_evlua_num.setVisibility(View.GONE);
                                }

                                Log.e("aaa", "(MeFragment.java:373)<---->"
                                        + "全部提示数字为：" + (allnum + learningnum + paynum + evluaingnum));
                                EventBus.getDefault().post(new CourseNumBean((allnum + learningnum + paynum + evluaingnum)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void isSign() {
        OkHttpUtils.post()
                .url(InternetS.IS_SIGN)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:231)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:237)" + response);
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick({R.id.iv_edit, R.id.ll_course_all, R.id.ll_islearning, R.id.ll_unpay, R.id.ll_isevaluate,
            R.id.tv_more, R.id.iv_more, R.id.ll_scan, R.id.ll_evaluate, R.id.ll_broswer, R.id.ll_love,
            R.id.ll_discountcoupon, R.id.iv_settings, R.id.ll_dengji, R.id.civ_usericon, R.id.rl_sign, R.id.ll_help,
            R.id.rl_gold_cup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_edit://编辑
                startActivityForResult(new Intent(getActivity(), MyInformationActivity.class),11);
                break;
            case R.id.ll_course_all://全部课程
                startActivityForResult(new Intent(getActivity(), AllCourseActivity.class),11);
                break;
            case R.id.rl_sign:
                startActivity(new Intent(getActivity(), SignActivity.class)
                        .putExtra("total_sign", totle_sign)
                        .putExtra("signed_num", signed_num));
                break;
            case R.id.ll_islearning://学习中
                startActivityForResult(new Intent(getActivity(), LearningActivity.class),11);
                break;
            case R.id.ll_unpay://未付款
                startActivityForResult(new Intent(getActivity(), MyOrderActivity.class).putExtra("flag", "pay"),11);
                break;
            case R.id.ll_isevaluate://待评价
                startActivityForResult(new Intent(getActivity(), MyOrderActivity.class).putExtra("flag", "eva"),11);
                break;
            case R.id.tv_more:
            case R.id.iv_more://我的订单更多
                startActivity(new Intent(getActivity(), MyOrderActivity.class).putExtra("flag", "all"));
                break;
            case R.id.ll_scan://扫一扫
                startActivity(new Intent(getActivity(), ScanQRCodeActivity.class));
                break;
            case R.id.ll_evaluate://评价
                startActivity(new Intent(getActivity(), MyEvaluateActivity.class));
                break;
            case R.id.ll_broswer://足迹
                startActivity(new Intent(getActivity(), MyBroswerActivity.class));
                break;
            case R.id.ll_love://收藏
                startActivity(new Intent(getActivity(), MyLoveActivity.class));
                break;
            case R.id.ll_discountcoupon://优惠券
                startActivity(new Intent(getActivity(), MyDiscountcouponActivity.class));
                break;
            case R.id.iv_settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class).putExtra("type", "per")
                        .putExtra("icon", head_photo)
                        .putExtra("name", user_name));
                break;
            case R.id.ll_dengji:
                startActivity(new Intent(getActivity(), GradeActivity.class)
                        .putExtra("grade", user_dengji)
                        .putExtra("integral", user_integral)
                        .putExtra("flag", "per")
                );
                break;
            case R.id.civ_usericon:
                startActivityForResult(new Intent(getActivity(), MyInformationActivity.class),11);
                break;
            case R.id.ll_help:
                startActivity(new Intent(getActivity(), HelpActivity.class));
                break;
            case R.id.rl_gold_cup:
                startActivity(new Intent(getActivity(), WebGradeActivity.class)
                        .putExtra("url", "gold_regulation1.html")
                        .putExtra("title", "金币使用规则"));
                break;

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        initView();
        initData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
            }
        }else if (requestCode==11){
            initView();
            initData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(RefreshData data){
        initView();
    }

}
