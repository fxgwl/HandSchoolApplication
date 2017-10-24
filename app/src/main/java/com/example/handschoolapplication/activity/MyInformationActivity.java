package com.example.handschoolapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.UserInfoBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.MyUtiles;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.utils.Utils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class MyInformationActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.iv_usericon)
    ImageView ivUsericon;
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

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private ImageView iv_personal_icon;
    private String image64;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = (String) SPUtils.get(MyInformationActivity.this, "userId", "");
        tvTitle.setText("个人资料");
        ivMenu.setVisibility(View.VISIBLE);
        initView();
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
                        Gson gson = new Gson();
                        try {
                            UserInfoBean.DataBean user = gson.fromJson(response, UserInfoBean.class).getData();
                            Glide.with(MyInformationActivity.this)
                                    .load(Internet.BASE_URL + user.getHead_photo())
                                    .error(R.drawable.touxiang)
                                    .centerCrop()
                                    .into(ivUsericon);
                            tvUsername.setText(user.getMember_name());
                            tvName.setText(user.getUser_name());
                            tvSex.setText(user.getUser_sex());
                            tvIdcode.setText(user.getId_number());
                            tvMyaddress.setText(user.getUser_area());
                        } catch (Exception e) {
                        }
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_information;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_icon, R.id.ll_username, R.id.ll_name, R.id.ll_sex, R.id.ll_idcode, R.id.ll_myaddress, R.id.btn_save})
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
                showChoosePicDialog();
                break;
            case R.id.ll_username:
                if ("会员名称".equals(tvUsername.getText().toString())) {
                    startActivityForResult(new Intent(this, SetUserNameActivity.class), 1);
                } else {
                    Toast.makeText(this, "会员名称只能修改一次", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_name:
                startActivityForResult(new Intent(this, SetNameActivity.class), 1);
                break;
            case R.id.ll_sex:
                startActivityForResult(new Intent(this, SelectSexActivity.class), 1);
                break;
            case R.id.ll_idcode:
                if (TextUtils.isEmpty(tvIdcode.getText().toString())) {
                    startActivityForResult(new Intent(this, SetIdCodeActivity.class), 1);
                } else {
                    Toast.makeText(this, "身份证号只能修改一次", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.ll_myaddress:
                startActivityForResult(new Intent(this, CurrentCityActivity.class), 1);
                break;
            case R.id.btn_save:
                //保存资料
                saveInfo();
                break;
        }
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

        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    Uri newUri = Uri.parse("file:///" + Utils.getPath(this, data.getData()));
                    startPhotoZoom(newUri); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
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

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     * @param
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            Log.e("aaa",
                    "(MyInformationActivity.java:206)" + photo);
            ivUsericon.setImageBitmap(photo);
            uploadPic(photo);
        }
    }

    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了
//        String imagePath = Utils.savePhoto(bitmap, Environment
//                .getExternalStorageDirectory().getAbsolutePath(), String
//                .valueOf(System.currentTimeMillis()));
//        Log.e("imagePath", imagePath + "");
//        if (imagePath != null) {
//            // 拿着imagePath上传了
//        }

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

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(MyInformationActivity.java:260保存头像返回)" + response);
                    }
                });
    }
}
