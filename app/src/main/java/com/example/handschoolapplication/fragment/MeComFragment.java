package com.example.handschoolapplication.fragment;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.ApplyActivity;
import com.example.handschoolapplication.activity.ClassActivity;
import com.example.handschoolapplication.activity.CommentManagerActivity;
import com.example.handschoolapplication.activity.DealManagerActivity;
import com.example.handschoolapplication.activity.GradeActivity;
import com.example.handschoolapplication.activity.MyAccountActivity;
import com.example.handschoolapplication.activity.QRCodeActivity;
import com.example.handschoolapplication.activity.ScanQRCodeActivity;
import com.example.handschoolapplication.activity.SchoolInformationActivity;
import com.example.handschoolapplication.activity.SettingsActivity;
import com.example.handschoolapplication.activity.SignActivity;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.SchoolInfoBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeComFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks{

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
    @BindView(R.id.rl_class_grade)
    RelativeLayout rlClassGrade;
    private View view;
    private int REQUEST_CODE;
    private String school_id;
    private String user_id;
    private SchoolInfoBean.DataBean dataBean;
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    private String signed_num;

    public MeComFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        school_id = (String) SPUtils.get(getActivity(), "school_id", "");
        user_id = (String) SPUtils.get(getActivity(), "userId", "");
        initView();
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
                            tvIntegral.setText(dataBean.getUser_integral());
                            signed_num = dataBean.getSigned_num();
                            switch (dataBean.getUser_dengji()) {
                                case "0":
                                    break;
                                case "1":
                                    ivImg1.setVisibility(View.VISIBLE);
                                    break;
                                case "2":
                                    ivImg1.setVisibility(View.VISIBLE);
                                    ivImg2.setVisibility(View.VISIBLE);
                                    break;
                                case "3":
                                    ivImg1.setVisibility(View.VISIBLE);
                                    ivImg2.setVisibility(View.VISIBLE);
                                    ivImg3.setVisibility(View.VISIBLE);
                                    break;
                                case "4":
                                    ivImg1.setVisibility(View.VISIBLE);
                                    ivImg2.setVisibility(View.VISIBLE);
                                    ivImg3.setVisibility(View.VISIBLE);
                                    ivImg4.setVisibility(View.VISIBLE);
                                    break;
                                case "5":
                                    ivImg1.setVisibility(View.VISIBLE);
                                    ivImg2.setVisibility(View.VISIBLE);
                                    ivImg3.setVisibility(View.VISIBLE);
                                    ivImg4.setVisibility(View.VISIBLE);
                                    ivImg5.setVisibility(View.VISIBLE);
                                    break;
                            }
                        } catch (Exception e) {
                        }
                    }
                });
    }


    @OnClick({R.id.civ_usericon, R.id.iv_settings, R.id.iv_edit, R.id.ll_scan, R.id.ll_my_class, R.id.ll_my_account, R.id.ll_apply,
            R.id.ll_deal_manager, R.id.ll_evaluate_manager, R.id.ll_code,R.id.rl_class_grade,R.id.rl_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_settings://设置
                startActivity(new Intent(getActivity(), SettingsActivity.class).putExtra("type", "com")
                        .putExtra("icon", dataBean.getHead_photo())
                        .putExtra("name", dataBean.getMechanism_name()));
                break;
            case R.id.iv_edit://编辑
                break;
            case R.id.ll_scan://扫一扫
                startActivity(new Intent(getActivity(), ScanQRCodeActivity.class));
//                Intent intent = new Intent(getActivity(), CaptureActivity.class);
//                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.ll_my_class://我的学堂
                startActivity(new Intent(getActivity(), ClassActivity.class).putExtra("school_id", school_id));
                break;
            case R.id.ll_my_account://我的账户
                startActivity(new Intent(getActivity(), MyAccountActivity.class));
                break;
            case R.id.ll_apply://报名信息
                startActivity(new Intent(getActivity(), ApplyActivity.class));
                break;
            case R.id.ll_deal_manager:
                startActivity(new Intent(getActivity(), DealManagerActivity.class));
                break;
            case R.id.ll_evaluate_manager:
                startActivity(new Intent(getActivity(), CommentManagerActivity.class));
                break;
            case R.id.ll_code:
                startActivity(new Intent(getActivity(), QRCodeActivity.class).putExtra("flag","CC"));
                break;
            case R.id.civ_usericon:
                startActivity(new Intent(getActivity(), SchoolInformationActivity.class));
                break;
            case R.id.rl_class_grade:
                startActivity(new Intent(getActivity(), GradeActivity.class)
                        .putExtra("grade",dataBean.getUser_dengji())
                        .putExtra("integral",dataBean.getUser_integral())
                        .putExtra("flag","com"));
                break;
            case R.id.rl_sign:
                startActivity(new Intent(getActivity(), SignActivity.class).putExtra("signed_num",signed_num));
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        requestCodeQRCodePermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(getActivity(), perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
//
//        Log.e("aaa",
//            "(MeComFragment.java:236)"+"dasdadasdsadsadsadsad");
//        if(intentResult != null) {
//            if(intentResult.getContents() == null) {
//                Toast.makeText(getActivity(),"内容为空",Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(getActivity(),"扫描成功",Toast.LENGTH_LONG).show();
//                // ScanResult 为 获取到的字符串
//                String ScanResult = intentResult.getContents();
//            }
//        } else {
//            super.onActivityResult(requestCode,resultCode,data);
//        }
    }
}
