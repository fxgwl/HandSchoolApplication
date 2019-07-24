package com.example.handschoolapplication.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.RefreshData;
import com.example.handschoolapplication.bean.UserInfoBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.MyUtiles;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.utils.Utils;
import com.example.handschoolapplication.view.SelfDialog;
import com.google.gson.Gson;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.Call;

import static com.bumptech.glide.Glide.with;

public class MyInformationActivity extends BaseActivity {

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_usericon)
    CircleImageView ivUsericon;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_idcode)
    TextView tvIdcode;
    @BindView(R.id.tv_myaddress)
    TextView tvMyaddress;
    @BindView(R.id.tv_logo_add)
    TextView tvLogoAdd;
    @BindView(R.id.tv_sex_add)
    TextView tvSexAdd;
    @BindView(R.id.tv_id_add)
    TextView tvIdAdd;
    @BindView(R.id.tv_address_add)
    TextView tvAddressAdd;
    // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
    List<String> mPermissionList = new ArrayList<>();
    String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE

    };
    private ImageView iv_personal_icon;
    private String image64;
    private String userId;
    private UserInfoBean.DataBean user;
    private int REQUEST_LIST_CODE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = (String) SPUtils.get(MyInformationActivity.this, "userId", "");
        tvTitle.setText("个人资料");
        initView();
        requestPermission();

        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                with(context).load(path).into(imageView);
            }
        });
    }

    private void initView() {
        OkHttpUtils.post()
                .url(Internet.USERINFO)
                .addParams("user_id", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(MyInformationActivity.java:87)" + response);
                        if (TextUtils.isEmpty(response) || response.contains("没有信息")) {
                            Toast.makeText(MyInformationActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new Gson();
                            try {
                                user = gson.fromJson(response, UserInfoBean.class).getData();
                                with(MyInformationActivity.this)
                                        .load(Internet.BASE_URL + user.getHead_photo())
                                        .error(R.drawable.morentouxiang)
                                        .centerCrop()
                                        .into(ivUsericon);
                                tvUsername.setText(user.getMember_name());
                                tvName.setText(user.getUser_name());
                                tvSex.setText(user.getUser_sex());
                                tvIdcode.setText(user.getId_number());
                                tvMyaddress.setText(user.getUser_area());
                                if (TextUtils.isEmpty(user.getHead_photo())) {
                                    tvLogoAdd.setVisibility(View.VISIBLE);
                                } else {
                                    tvLogoAdd.setVisibility(View.GONE);
                                }

                                if (TextUtils.isEmpty(user.getUser_sex())) {
                                    tvSexAdd.setVisibility(View.VISIBLE);
                                } else {
                                    tvSexAdd.setVisibility(View.GONE);
                                }

                                if (TextUtils.isEmpty(user.getId_number())) {
                                    tvIdAdd.setVisibility(View.VISIBLE);
                                } else {
                                    tvIdAdd.setVisibility(View.GONE);
                                }
                                if (TextUtils.isEmpty(user.getUser_area())) {
                                    tvAddressAdd.setVisibility(View.VISIBLE);
                                } else {
                                    tvAddressAdd.setVisibility(View.GONE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void requestPermission() {
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
//            Toast.makeText(this,"已经授权",Toast.LENGTH_LONG).show();
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_information;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_icon, R.id.ll_username, R.id.ll_name, R.id.ll_sex, R.id.ll_idcode,
            R.id.ll_myaddress, R.id.btn_save, R.id.ll_change_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                //弹出对话框
                show(view);
                break;
            case R.id.ll_icon:
//                showChoosePicDialog();
                single(REQUEST_LIST_CODE);
                break;
            //修改手机号
            case R.id.ll_change_phone:
                String user_phone = (String) SPUtils.get(this, "user_phone", "");
                if (TextUtils.isEmpty(user_phone)) {
                    showUnLoginDialog();
                } else {
                    startActivity(new Intent(MyInformationActivity.this, ChangePhoneActivity.class));
                }

                break;
            case R.id.ll_username:

                String userName = tvUsername.getText().toString().trim();
                if (user != null) {
                    startActivityForResult(new Intent(this, SetUserNameActivity.class)
                            .putExtra("userName", userName), 1);
                } else {
                    Toast.makeText(this, "信息有误，请重新登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_name:
                String name = tvName.getText().toString().trim();
                if (user != null) {
                    startActivityForResult(new Intent(this, SetNameActivity.class)
                            .putExtra("name", name), 1);
                } else {
                    Toast.makeText(this, "信息有误，请重新登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_sex:
                String sex = tvSex.getText().toString().trim();
                if (user != null) {
                    startActivityForResult(new Intent(this, SelectSexActivity.class)
                            .putExtra("sex", sex), 1);
                } else {
                    Toast.makeText(this, "信息有误，请重新登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_idcode:
                if (TextUtils.isEmpty(tvIdcode.getText().toString()))
                    startActivityForResult(new Intent(this, SetIdCodeActivity.class), 1);
                else
                    Toast.makeText(this, "身份证号码只能修改一次", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_myaddress:
                startActivityForResult(new Intent(this, CurrentCitysActivity.class), 2);
                break;
            case R.id.btn_save:
                //保存资料
                saveInfo();
                break;
        }
    }

    public void single(int flag) {
        ISListConfig config = new ISListConfig.Builder()
                // 是否多选
                .multiSelect(false)
                .btnText("Confirm")
                // 确定按钮背景色
                //.btnBgColor(Color.parseColor(""))
                // 确定按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#3F51B5"))
                // 返回图标ResId
                .title("Images")
                .titleColor(Color.WHITE)
                .titleBgColor(Color.parseColor("#3F51B5"))
                .allImagesText("All Images")
                .needCrop(false)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(1)
                .build();

        ISNav.getInstance().toListActivity(this, config, flag);
    }

    private void showUnLoginDialog() {
        final SelfDialog selfDialog = new SelfDialog(MyInformationActivity.this);

        selfDialog.setMessage("是否绑定手机号?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                startActivity(new Intent(MyInformationActivity.this, RegisterPersonActivity.class)
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

    private void saveInfo() {
        String user_name = tvName.getText().toString();
        String user_sex = tvSex.getText().toString();
        String user_area = tvMyaddress.getText().toString();
        Log.e("aaa",
                "(MyInformationActivity.java:115)" + userId);
        OkHttpUtils.post()
                .url(Internet.CHANGEHEAD)
                .addParams("url", "")
                .addParams("user_id", userId)
                .addParams("user_name", user_name)
                .addParams("user_sex", user_sex)
                .addParams("user_area", user_area)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(MyInformationActivity.java:128保存资料返回)" + response);
                        try {
                            JSONObject json = new JSONObject(response);
                            if (response.contains("成功")) {
                                EventBus.getDefault().post("save");
                                EventBus.getDefault().post(new RefreshData(888));
                                finish();
                            }
                            Toast.makeText(MyInformationActivity.this, json.getString("msg"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 11) {
            String sex = data.getStringExtra("sex");
            tvSex.setText(sex);
        }
        if (requestCode == 1 && resultCode == 22) {
            String username = data.getStringExtra("username");
            tvUsername.setText(username);
        }
        if (requestCode == 1 && resultCode == 33) {
            String idcode = data.getStringExtra("idcode");
            tvIdcode.setText(idcode);
        }
        if (requestCode == 1 && resultCode == 44) {
            String name = data.getStringExtra("name");
            tvName.setText(name);
        }
        if (requestCode == 2 && resultCode == 11) {
            tvMyaddress.setText(data.getStringExtra("cityName"));
        }

        if (resultCode == RESULT_OK && requestCode == REQUEST_LIST_CODE) {

            List<String> pathList = data.getStringArrayListExtra("result");
            for (String path : pathList) {
                Log.e("aaa",
                        "(AddDataActivity.java:183)" + path + "\n");
                with(this).load(path).centerCrop().into(ivUsericon);
                Bitmap usericon = Compressor.getDefault(MyInformationActivity.this).compressToBitmap(new File(path));
                Bitmap usericon1 = Utils.toRoundBitmap(usericon, tempUri); // 这个时候的图片已经被处理成圆形的了
                Log.e("aaa",
                        "(MyInformationActivity.java:206)" + usericon);
                ivUsericon.setImageBitmap(usericon1);
                uploadPic(usericon);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
                    if (showRequestPermission) {
//                        showToast("权限未申请");
                        Toast.makeText(this, "权限未获得不能上传照片！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        image64 = MyUtiles.bitmapToBase64(bitmap);
        Log.e("aaa",
                "(MyInformationActivity.java:230)" + image64);
        JSONObject json = new JSONObject();
        try {
            json.put("photo", image64);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils.post()
                .url(Internet.CHANGEHEAD)
                .addParams("user_id", userId)
                .addParams("url", json.toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(MyInformationActivity.java:421)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(MyInformationActivity.java:260保存头像返回)" + response);
                    }
                });
    }

    /**
     * 添加弹出的dialog关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements Dialog.OnDismissListener {


        @Override
        public void onDismiss(DialogInterface dialog) {
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }
    }
}
