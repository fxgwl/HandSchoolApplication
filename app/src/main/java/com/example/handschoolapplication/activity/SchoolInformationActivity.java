package com.example.handschoolapplication.activity;

import android.Manifest;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.SchoolInfoBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.MyUtiles;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.utils.Utils;
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

public class SchoolInformationActivity extends BaseActivity {


    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    //    @BindView(R.id.btn_schoolinfo_save)
//    Button btnSchoolinfoSave;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0;
    protected static Uri tempUri;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    RelativeLayout ivMenu;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.iv_schoolinfo_usericon)
    CircleImageView ivUsericon;
    @BindView(R.id.ll_schoolinfo_logo)
    LinearLayout llSchoolinfoLogo;
    @BindView(R.id.tv_schoolinfo_schoolname)
    TextView tvSchoolinfoSchoolname;
    @BindView(R.id.ll_schoolinfo_schoolname)
    LinearLayout llSchoolinfoSchoolname;
    @BindView(R.id.tv_schoolinfo_schoolclass)
    TextView tvSchoolinfoSchoolclass;
    @BindView(R.id.ll_schoolinfo_schoolclass)
    LinearLayout llSchoolinfoSchoolclass;
    @BindView(R.id.tv_schoolinfo_shenfenrenzheng)
    TextView tvSchoolinfoShenfenrenzheng;
    @BindView(R.id.ll_schoolinfo_shenfenrenzheng)
    LinearLayout llSchoolinfoShenfenrenzheng;
    @BindView(R.id.tv_schoolinfo_qualification)
    TextView tvSchoolinfoQualification;
    @BindView(R.id.tv_is_add)
    TextView tvIsAdd;
    @BindView(R.id.tv_logo_add)
    TextView tvLogoAdd;
    @BindView(R.id.tv_qua_add)
    TextView tvQuaAdd;
    @BindView(R.id.ll_schoolinfo_qualification)
    LinearLayout llSchoolinfoQualification;
    @BindView(R.id.tv_schoolinfo_schooladdress)
    TextView tvSchoolinfoSchooladdress;
    @BindView(R.id.ll_schoolinfo_schooladdress)
    LinearLayout llSchoolinfoSchooladdress;
    // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
    List<String> mPermissionList = new ArrayList<>();
    String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE

    };
    private ImageView iv_personal_icon;
    private String change_state, midphoto_state, qualiprove_state;
    private String user_id;
    private SchoolInfoBean.DataBean schoolInfo;
    private String mechanism_type = "";
    private int REQUEST_LIST_CODE = 1;
    private String schoolNameState="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user_id = (String) SPUtils.get(this, "userId", "");
        change_state = (String) SPUtils.get(this, "change_state", "");
        midphoto_state = (String) SPUtils.get(this, "midphoto_state", "");
        qualiprove_state = (String) SPUtils.get(this, "qualiprove_state", "");
        tvTitle.setText("机构资料");
        ivMenu.setVisibility(View.VISIBLE);
        requestPermission();

        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                with(context).load(path).into(imageView);
            }
        });

        initView();

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

    //初始化数据
    private void initView() {
        OkHttpUtils.post()
                .url(Internet.USERINFO)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(SchoolInformationActivity.java:185)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SchoolInformationActivity.java:102)" + response);
                        if (TextUtils.isEmpty(response)) {
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int result = jsonObject.getInt("code");
                                if (result == 0) {
                                    Gson gson = new Gson();
                                    schoolInfo = gson.fromJson(response, SchoolInfoBean.class).getData();
                                    with(SchoolInformationActivity.this)
                                            .load(Internet.BASE_URL + schoolInfo.getHead_photo())
                                            .centerCrop()
                                            .into(ivUsericon);
                                    tvSchoolinfoSchoolname.setText(schoolInfo.getMechanism_name());
//                        tvSchoolinfoSchoolclass.setText(schoolInfo.getMechanism_type());
                                    tvSchoolinfoShenfenrenzheng.setText(schoolInfo.getId_number());
//                        tvSchoolinfoQualification.setText(schoolInfo.getQualification_prove());
                                    mechanism_type = schoolInfo.getMechanism_type();
                                    if (TextUtils.isEmpty(schoolInfo.getId_number()) || TextUtils.isEmpty(schoolInfo.getMidphoto_card())
                                            || TextUtils.isEmpty(schoolInfo.getMidphoto_cards())) {
                                        tvIsAdd.setVisibility(View.VISIBLE);
                                    } else {
                                        tvIsAdd.setVisibility(View.GONE);
                                    }
                                    if (TextUtils.isEmpty(schoolInfo.getQualification_prove())) {
                                        tvQuaAdd.setVisibility(View.VISIBLE);
                                    } else {
                                        tvQuaAdd.setVisibility(View.GONE);
                                    }
                                    if (TextUtils.isEmpty(schoolInfo.getHead_photo())) {
                                        tvLogoAdd.setVisibility(View.VISIBLE);
                                    } else {
                                        tvLogoAdd.setVisibility(View.GONE);
                                    }

                                    qualiprove_state = schoolInfo.getQualiprove_state();
                                    midphoto_state = schoolInfo.getMidphoto_state();
                                    schoolNameState=schoolInfo.getMechanism_name_state();

                                    switch (schoolNameState){
                                        case "0":
                                            tvSchoolinfoSchoolname.setText("审核中");
                                            break;
                                        case "1":
                                            tvSchoolinfoSchoolname.setText(schoolInfo.getMechanism_name());
                                            break;
                                        case "2":
                                            tvSchoolinfoSchoolname.setText("初审驳回");
                                            break;
                                        case "3":
                                            tvSchoolinfoSchoolname.setText("审核中");
                                            break;
                                        case "4":
                                            tvSchoolinfoSchoolname.setText("复审驳回");
                                            break;
                                    }

                                    if ("0".equals(qualiprove_state)) {
                                        tvSchoolinfoQualification.setText("审核中");
                                    }else if("2".equals(qualiprove_state)){
                                        tvSchoolinfoQualification.setText("已驳回");
                                    }
                                    if ("0".equals(SchoolInformationActivity.this.midphoto_state)) {
                                        tvSchoolinfoShenfenrenzheng.setText("审核中");
                                    }else if("2".equals(SchoolInformationActivity.this.midphoto_state)){
                                        tvSchoolinfoShenfenrenzheng.setText("已驳回");
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_school_information;
    }

    /**
     * 显示修改头像的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "image.jpg"));
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_LIST_CODE) {
            List<String> pathList = data.getStringArrayListExtra("result");
            for (String path : pathList) {
                with(this).load(path).centerCrop().into(ivUsericon);
                Bitmap usericon = Compressor.getDefault(SchoolInformationActivity.this).compressToBitmap(new File(path));
                Bitmap usericon1 = Utils.toRoundBitmap(usericon, tempUri); // 这个时候的图片已经被处理成圆形的了
                ivUsericon.setImageBitmap(usericon1);
                uploadPic(usericon);
            }
        } else if (requestCode == 1) {
            Log.e("aaa", "(SchoolInformationActivity.java:279)<---->" + "45454545");
            initView();
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
                    }else{
                        Toast.makeText(this, "手机系统设置->应用和通知->权限管理 进行设置", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了
        String icon64 = MyUtiles.bitmapToBase64(bitmap);
        JSONObject json = new JSONObject();
        try {
            json.put("icon", icon64);
            OkHttpUtils.post()
                    .url(Internet.CHANGEHEAD)
                    .addParams("user_id", user_id)
                    .addParams("url", json.toString())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e("aaa",
                                    "(SchoolInformationActivity.java:301)<---->" + e.getMessage());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.e("aaa",
                                    "(SchoolInformationActivity.java:307)" + response);
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }


    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_schoolinfo_logo, R.id.ll_schoolinfo_schoolclass,
            R.id.ll_schoolinfo_shenfenrenzheng, R.id.ll_schoolinfo_qualification,R.id.ll_schoolinfo_schoolname,
            R.id.ll_schoolinfo_schooladdress, R.id.btn_schoolinfo_save, R.id.ll_change_phone, R.id.ll_near_school})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            //学堂logo
            case R.id.ll_schoolinfo_logo:
//                showChoosePicDialog();
                single(REQUEST_LIST_CODE);
                break;
            //学堂类别
            case R.id.ll_schoolinfo_schoolclass:
                startActivityForResult(new Intent(this, ClassTypeActivity.class),1);
                break;
            //身份认证
            case R.id.ll_schoolinfo_shenfenrenzheng:
                startActivityForResult(new Intent(this, IdentityCardActivity.class), 1);
                break;
            //资质认证
            case R.id.ll_schoolinfo_qualification:
                startActivityForResult(new Intent(this, QualificationActivity.class), 1);
                break;
            //学堂地址
            case R.id.ll_schoolinfo_schooladdress:
                startActivity(new Intent(SchoolInformationActivity.this, SchoolAddressActivity.class));
                break;
            //保存
            case R.id.btn_schoolinfo_save:
                //假的保存
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post("save");
                finish();
                break;
            case R.id.ll_near_school:
                //附近小学
                // TODO: 2018/7/12 需先判断是否包含托教类别
                if (mechanism_type.contains("托教")) {
                    startActivity(new Intent(this, NearLittleSchoolActivity.class));
                } else {
                    Toast.makeText(this, "请先在机构类别内添加托教类别", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.ll_change_phone:
                startActivity(new Intent(SchoolInformationActivity.this, ChangePhoneActivity.class));
                break;
            case R.id.ll_schoolinfo_schoolname:
                startActivityForResult(new Intent(this, UpdateSchoolNameActivity.class), 1);
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
}
