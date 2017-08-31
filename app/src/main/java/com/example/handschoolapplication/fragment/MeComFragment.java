package com.example.handschoolapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.ApplyActivity;
import com.example.handschoolapplication.activity.ClassActivity;
import com.example.handschoolapplication.activity.CommentManagerActivity;
import com.example.handschoolapplication.activity.DealManagerActivity;
import com.example.handschoolapplication.activity.MyAccountActivity;
import com.example.handschoolapplication.activity.SchoolInformationActivity;
import com.example.handschoolapplication.activity.SettingsActivity;
import com.example.handschoolapplication.base.BaseFragment;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeComFragment extends BaseFragment {

    @BindView(R.id.civ_usericon)
    CircleImageView civUsericon;
    @BindView(R.id.tv_percent)
    TextView tvPercent;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.iv_grade)
    ImageView ivGrade;
    @BindView(R.id.iv_img1)
    ImageView ivImg1;
    @BindView(R.id.iv_img2)
    ImageView ivImg2;
    @BindView(R.id.iv_img3)
    ImageView ivImg3;
    @BindView(R.id.iv_img4)
    ImageView ivImg4;
    @BindView(R.id.iv_img5)
    ImageView ivImg5;

    Unbinder unbinder;
    @BindView(R.id.ll_evaluate_grade)
    LinearLayout llEvaluateGrade;
    @BindView(R.id.ll_scan)
    LinearLayout llScan;
    @BindView(R.id.ll_my_class)
    LinearLayout llMyClass;
    @BindView(R.id.ll_my_account)
    LinearLayout llMyAccount;
    @BindView(R.id.ll_apply)
    LinearLayout llApply;
    @BindView(R.id.ll_deal_manager)
    LinearLayout llDealManager;
    @BindView(R.id.ll_evaluate_manager)
    LinearLayout llEvaluateManager;
    @BindView(R.id.ll_code)
    LinearLayout llCode;
    private View view;
    private int REQUEST_CODE;


    public MeComFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_me_com;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.civ_usericon,R.id.iv_settings, R.id.iv_edit, R.id.ll_scan, R.id.ll_my_class, R.id.ll_my_account, R.id.ll_apply, R.id.ll_deal_manager, R.id.ll_evaluate_manager, R.id.ll_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_settings://设置
                startActivity(new Intent(getActivity(), SettingsActivity.class).putExtra("type","com"));
                break;
            case R.id.iv_edit://编辑
                break;
            case R.id.ll_scan://扫一扫
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.ll_my_class://我的学堂
                startActivity(new Intent(getActivity(), ClassActivity.class));
                break;
            case R.id.ll_my_account://我的账户
                startActivity(new Intent(getActivity(),MyAccountActivity.class));
                break;
            case R.id.ll_apply://报名信息
                startActivity(new Intent(getActivity(),ApplyActivity.class));
                break;
            case R.id.ll_deal_manager:
                startActivity(new Intent(getActivity(), DealManagerActivity.class));
                break;
            case R.id.ll_evaluate_manager:
                startActivity(new Intent(getActivity(), CommentManagerActivity.class));
                break;
            case R.id.ll_code:
                break;
            case R.id.civ_usericon:
                startActivity(new Intent(getActivity(), SchoolInformationActivity.class));
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
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
