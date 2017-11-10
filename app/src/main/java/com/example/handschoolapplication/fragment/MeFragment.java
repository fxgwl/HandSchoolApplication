package com.example.handschoolapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.example.handschoolapplication.activity.SettingsActivity;
import com.example.handschoolapplication.activity.SignActivity;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.SchoolInfoBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {

    @BindView(R.id.civ_usericon)
    CircleImageView civUsericon;
    @BindView(R.id.tv_percent)
    TextView tvPercent;
    @BindView(R.id.iv_jewel1)
    ImageView ivJewel1;
    @BindView(R.id.iv_jewel2)
    ImageView ivJewel2;
    @BindView(R.id.iv_jewel3)
    ImageView ivJewel3;
    @BindView(R.id.iv_jewel4)
    ImageView ivJewel4;
    @BindView(R.id.iv_jewel5)
    ImageView ivJewel5;
    @BindView(R.id.tv_days)
    TextView tvDays;
    @BindView(R.id.tv_gold_num)
    TextView tvGoldNum;
    @BindView(R.id.ll_dengji)
    LinearLayout llDengji;
    Unbinder unbinder;

    private View view;
    private int REQUEST_CODE;
    private String user_id;
    private SchoolInfoBean.DataBean dataBean;
    private String signed_num;


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
        initView();

        //判断签到  后台需要  前台没用
        isSign();
        return view;
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

    //初始化界面
    private void initView() {
        OkHttpUtils.post()
                .url(Internet.USERINFO)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(MeFragment.java:107)" + response);
                        Gson gson = new Gson();
                        try {
                            dataBean = gson.fromJson(response, SchoolInfoBean.class).getData();
                            Glide.with(getActivity())
                                    .load(Internet.BASE_URL + dataBean.getHead_photo())
                                    .centerCrop()
                                    .error(R.drawable.touxiang)
                                    .into(civUsericon);
                            tvPercent.setText(dataBean.getData_integrity() + "%");
                            tvDays.setText(dataBean.getSigned_num());
                            tvGoldNum.setText(dataBean.getUser_gold());
                            signed_num = dataBean.getSigned_num();
                            switch (dataBean.getUser_dengji()) {
                                case "0":
                                    break;
                                case "1":
                                    ivJewel1.setVisibility(View.VISIBLE);
                                    break;
                                case "2":
                                    ivJewel1.setVisibility(View.VISIBLE);
                                    ivJewel2.setVisibility(View.VISIBLE);
                                    break;
                                case "3":
                                    ivJewel1.setVisibility(View.VISIBLE);
                                    ivJewel2.setVisibility(View.VISIBLE);
                                    ivJewel3.setVisibility(View.VISIBLE);
                                    break;
                                case "4":
                                    ivJewel1.setVisibility(View.VISIBLE);
                                    ivJewel2.setVisibility(View.VISIBLE);
                                    ivJewel3.setVisibility(View.VISIBLE);
                                    ivJewel4.setVisibility(View.VISIBLE);
                                    break;
                                case "5":
                                    ivJewel1.setVisibility(View.VISIBLE);
                                    ivJewel2.setVisibility(View.VISIBLE);
                                    ivJewel3.setVisibility(View.VISIBLE);
                                    ivJewel4.setVisibility(View.VISIBLE);
                                    ivJewel5.setVisibility(View.VISIBLE);
                                    break;
                            }
                        } catch (Exception e) {
                        }
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_me;
    }


    @OnClick({R.id.iv_edit, R.id.ll_course_all, R.id.ll_islearning, R.id.ll_unpay, R.id.ll_isevaluate,
            R.id.tv_more, R.id.iv_more, R.id.ll_scan, R.id.ll_evaluate, R.id.ll_broswer, R.id.ll_love,
            R.id.ll_discountcoupon, R.id.iv_settings, R.id.ll_dengji, R.id.civ_usericon,R.id.rl_sign,R.id.ll_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_edit://编辑
                startActivity(new Intent(getActivity(), MyInformationActivity.class));
                break;
            case R.id.ll_course_all://全部课程
                startActivity(new Intent(getActivity(), AllCourseActivity.class));
                break;
            case R.id.rl_sign:
                startActivity(new Intent(getActivity(), SignActivity.class).putExtra("signed_num",signed_num));
                break;
            case R.id.ll_islearning://学习中
                startActivity(new Intent(getActivity(), LearningActivity.class));
                break;
            case R.id.ll_unpay://未付款
                startActivity(new Intent(getActivity(), MyOrderActivity.class).putExtra("flag", "pay"));
                break;
            case R.id.ll_isevaluate://待评价
                startActivity(new Intent(getActivity(), MyOrderActivity.class).putExtra("flag", "eva"));
                break;
            case R.id.tv_more:
            case R.id.iv_more://我的订单更多
                startActivity(new Intent(getActivity(), MyOrderActivity.class).putExtra("flag", "all"));
                break;
            case R.id.ll_scan://扫一扫
//                Intent intent = new Intent(getActivity(), CaptureActivity.class);
//                startActivityForResult(intent, REQUEST_CODE);
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
                        .putExtra("icon", dataBean.getHead_photo())
                        .putExtra("name", dataBean.getUser_name()));
                break;
            case R.id.ll_dengji:
                startActivity(new Intent(getActivity(), GradeActivity.class)
                        .putExtra("grade",dataBean.getUser_dengji())
                        .putExtra("integral",dataBean.getUser_integral())
                        .putExtra("flag","per")
                );
                break;
            case R.id.civ_usericon:
                startActivity(new Intent(getActivity(), MyInformationActivity.class));
                break;
            case R.id.ll_help:
                startActivity(new Intent(getActivity(), HelpActivity.class));
                break;

        }
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
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
//                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
