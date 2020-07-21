package com.example.handschoolapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class HumanServiceSActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_gzh_code)
    ImageView ivGzhCode;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_service_phone)
    TextView tvServicePhone;
    private int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("客服");
        String text = tvServicePhone.getText().toString();
        BackgroundColorSpan blueSpan = new BackgroundColorSpan(getResources().getColor(R.color.blue));
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        builder.setSpan(blueSpan,4,text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvServicePhone.setText(builder.toString());
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_human_service_s;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu,R.id.tv_service_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.tv_service_phone:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    }else {
                        //拨打电话
                        callClass("0316-2018088");
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                showToast("权限已申请");
                //拨打电话
                callClass("0316-2018088");
            } else {
                boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(HumanServiceSActivity.this, permissions[0]);
                if (showRequestPermission) {
//                        Toast.makeText(this, "权限未申请", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "未获得打电话权限", Toast.LENGTH_SHORT).show();
                    //requestPermission();
                }else{
                    Toast.makeText(this, "手机系统设置->应用和通知->权限管理 进行设置", Toast.LENGTH_SHORT).show();
                    //showUnLoginDialog("点开手机系统设置->应用和通知->权限管理",0);
                }
                finish();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void callClass(String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        startActivity(intent);
    }
}
