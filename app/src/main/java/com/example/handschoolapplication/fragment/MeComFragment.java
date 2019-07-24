package com.example.handschoolapplication.fragment;


import android.Manifest;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.AddDataActivity;
import com.example.handschoolapplication.activity.ApplyActivity;
import com.example.handschoolapplication.activity.AttentionMeActivity;
import com.example.handschoolapplication.activity.ClassActivity;
import com.example.handschoolapplication.activity.CommentManagerActivity;
import com.example.handschoolapplication.activity.DealManagerActivity;
import com.example.handschoolapplication.activity.GradeComActivity;
import com.example.handschoolapplication.activity.HelpActivity;
import com.example.handschoolapplication.activity.MyAccountActivity;
import com.example.handschoolapplication.activity.QRCodeActivity;
import com.example.handschoolapplication.activity.ScanQRCodeActivity;
import com.example.handschoolapplication.activity.SchoolInformationActivity;
import com.example.handschoolapplication.activity.SettingsActivity;
import com.example.handschoolapplication.activity.SignActivity;
import com.example.handschoolapplication.activity.WebGradeActivity;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.SchoolInfoBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.bumptech.glide.Glide.with;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeComFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {

    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
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
    @BindView(R.id.tv_days)
    TextView tvDays;
    private View view;
    private int REQUEST_CODE;
    private String school_id;
    private String user_id;
    private SchoolInfoBean.DataBean dataBean;
    private String signed_num;
    private String totle_sign;
    private String change_state;//学堂审核状态

    public MeComFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
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
    public void onResume() {
        super.onResume();
//        initView();
    }

    //初始化界面
    private void initView() {
        Log.e("aaa","(MeComFragment.java:136)<--user_id-->" + user_id);
        OkHttpUtils.post()
                .url(Internet.USERINFO)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(MeComFragment.java:144)<---->" + e.getMessage());
                        Toast.makeText(getActivity(), "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(MeComFragment.java:150)" + response);

                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
//                            Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Gson gson = new Gson();
                            try {
                                dataBean = gson.fromJson(response, SchoolInfoBean.class).getData();
                                with(getActivity())
                                        .load(Internet.BASE_URL + dataBean.getHead_photo())
                                        .centerCrop()
                                        .error(R.drawable.morentouxiang)
                                        .into(civUsericon);
                                tvPercent.setText(dataBean.getData_integrity() + "%");
                                tvIntegral.setText(dataBean.getUser_integral());
                                signed_num = dataBean.getSigned_num();
                                totle_sign = dataBean.getTotle_sign();
                                change_state = dataBean.getChange_state();
                                String qualiprove_state = dataBean.getQualiprove_state() == null ? "0" : dataBean.getQualiprove_state();
                                String midphoto_state = dataBean.getMidphoto_state() == null ? "0" : dataBean.getMidphoto_state();
                                SPUtils.put(getActivity(), "qualiprove_state", qualiprove_state);
                                SPUtils.put(getActivity(), "midphoto_state", midphoto_state);
                                Log.e("aaa",
                                        "(MeComFragment.java:172)<--change_state-->" + change_state);

                                SPUtils.put(getActivity(), "change_state", change_state);//保存学堂审核状态

                                switch (dataBean.getPingjia()) {
                                    case "0":
                                        break;
                                    case "1":
                                        ivImg1.setImageResource(R.drawable.wujiaoxing);
                                        ivImg2.setVisibility(View.INVISIBLE);
                                        ivImg3.setVisibility(View.INVISIBLE);
                                        ivImg4.setVisibility(View.INVISIBLE);
                                        ivImg5.setVisibility(View.INVISIBLE);
                                        break;
                                    case "2":
                                        ivImg1.setImageResource(R.drawable.wujiaoxing);
                                        ivImg2.setImageResource(R.drawable.wujiaoxing);
                                        ivImg3.setVisibility(View.INVISIBLE);
                                        ivImg4.setVisibility(View.INVISIBLE);
                                        ivImg5.setVisibility(View.INVISIBLE);

                                        break;
                                    case "3":
                                        ivImg1.setImageResource(R.drawable.wujiaoxing);
                                        ivImg2.setImageResource(R.drawable.wujiaoxing);
                                        ivImg3.setImageResource(R.drawable.wujiaoxing);
                                        ivImg4.setVisibility(View.INVISIBLE);
                                        ivImg5.setVisibility(View.INVISIBLE);
                                        break;
                                    case "4":
                                        ivImg1.setImageResource(R.drawable.wujiaoxing);
                                        ivImg2.setImageResource(R.drawable.wujiaoxing);
                                        ivImg3.setImageResource(R.drawable.wujiaoxing);
                                        ivImg4.setImageResource(R.drawable.wujiaoxing);
                                        ivImg5.setVisibility(View.INVISIBLE);
                                        break;
                                    case "5":
                                        ivImg1.setImageResource(R.drawable.wujiaoxing);
                                        ivImg2.setImageResource(R.drawable.wujiaoxing);
                                        ivImg3.setImageResource(R.drawable.wujiaoxing);
                                        ivImg4.setImageResource(R.drawable.wujiaoxing);
                                        ivImg5.setImageResource(R.drawable.wujiaoxing);
                                        break;
                                }

                                Log.e("aaa",
                                        "(MeComFragment.java:223)<--等级-->" + dataBean.getUser_dengji());

                                switch (dataBean.getUser_dengji()) {
                                    case "0":
                                        ivGrade.setImageResource(R.drawable.xuetangdengji_wu_zheng);
                                        break;
                                    case "1":
                                        ivGrade.setImageResource(R.drawable.xuetangdengji_tong_zheng);
                                        break;
                                    case "2":
                                        ivGrade.setImageResource(R.drawable.xuetangdengji_yin_zheng);
                                        break;
                                    case "3":
                                        ivGrade.setImageResource(R.drawable.xuetangdengji_jin_zheng);
                                        break;
                                }
                                tvDays.setText(signed_num);
                            } catch (Exception e) {
                            }
                        }

                    }
                });
    }

    @OnClick({R.id.civ_usericon, R.id.iv_settings, R.id.iv_edit, R.id.ll_scan, R.id.ll_my_class, R.id.ll_my_account, R.id.ll_apply,
            R.id.ll_deal_manager, R.id.ll_evaluate_manager, R.id.ll_code, R.id.rl_class_grade, R.id.rl_sign, R.id.ll_attention, R.id.ll_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_settings://设置
                if (dataBean==null){
                    Toast.makeText(getActivity(), "数据错误，暂时不能打开设置界面", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dataBean.getHead_photo() == null && TextUtils.isEmpty(dataBean.getHead_photo())) {
                    startActivityForResult(new Intent(getActivity(), SettingsActivity.class).putExtra("type", "com")
                            .putExtra("icon", "null")
                            .putExtra("name", dataBean.getMechanism_name()), 0);
                } else {
                    startActivityForResult(new Intent(getActivity(), SettingsActivity.class).putExtra("type", "com")
                            .putExtra("icon", dataBean.getHead_photo())
                            .putExtra("name", dataBean.getMechanism_name()), 0);
                }

                break;
            case R.id.iv_edit://编辑
                if (dataBean==null){
                    Toast.makeText(getActivity(), "数据错误，暂时不能编辑", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (change_state.equals("2")) {
                    Toast.makeText(getActivity(), "审核未通过，请重新提交资料", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(getActivity(), AddDataActivity.class), 0);
                } else if (change_state.equals("0")) {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    Toast.makeText(getActivity(), "审核通过后才能编辑资料", Toast.LENGTH_SHORT).show();
                } else
                    startActivityForResult(new Intent(getActivity(), SchoolInformationActivity.class), 0);
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
            case R.id.ll_deal_manager://交易管理
                startActivity(new Intent(getActivity(), DealManagerActivity.class));
                break;
            case R.id.ll_evaluate_manager://评价管理
                startActivity(new Intent(getActivity(), CommentManagerActivity.class));
                break;
            case R.id.ll_code://学堂二维码
                startActivity(new Intent(getActivity(), QRCodeActivity.class).putExtra("flag", "CC"));
                break;
            case R.id.civ_usericon://学堂信息
                if (dataBean==null){
                    Toast.makeText(getActivity(), "数据错误，暂时不能编辑资料", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (change_state.equals("2")) {
                    startActivity(new Intent(getActivity(), AddDataActivity.class));
                } else if (change_state.equals("0")) {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    Toast.makeText(getActivity(), "审核过后才能编辑资料", Toast.LENGTH_SHORT).show();
                } else startActivityForResult(new Intent(getActivity(), SchoolInformationActivity.class),0);
                break;
            case R.id.rl_class_grade://学堂等级
                if (dataBean==null){
                    Toast.makeText(getActivity(), "数据错误，暂时不能查看等级", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(getActivity(), WebGradeActivity.class)
                        .putExtra("url", "merchant_level1.html")
                        .putExtra("title", "等级规则的介绍"));
                break;
            case R.id.rl_sign://签到
                if (dataBean==null){
                    Toast.makeText(getActivity(), "数据错误，暂时不能查看签到", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(getActivity(), SignActivity.class)
                        .putExtra("signed_num", signed_num)
                        .putExtra("total_sign", totle_sign));
                break;
            case R.id.ll_attention://学堂粉丝
                startActivity(new Intent(getActivity(), AttentionMeActivity.class));
                break;
            case R.id.ll_help:
                startActivity(new Intent(getActivity(), HelpActivity.class));
                break;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        initView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        requestCodeQRCodePermissions();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(getActivity(), perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }
}
