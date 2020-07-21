package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.SchoolInfoBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.PhotoPickerActivity;
import okhttp3.Call;

import static com.bumptech.glide.Glide.with;

public class QualificationActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_preview)
    ImageView preView1;
    @BindView(R.id.tv_shenhe)
    TextView tvShenhe;



    private List<String> photo = new ArrayList<>();
    private List<Bitmap> photos = new ArrayList<>();
    private Bitmap bitmap;
    private String userId;
    private String[] split3;
    private int num = 0;//点击位置
    private int length = 0;//图片数量
    private boolean isFirst = false;
    private String qualification_prove = "";
    private String state="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        userId = (String) SPUtils.get(this, "userId", "");
        initView();
        initData();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_qualification;
    }

    private void initView() {
        tvTitle.setText("资质认证");
    }

    private void initData() {

        OkHttpUtils
                .post()
                .url(Internet.USERINFO)
                .addParams("user_id", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(QualificationActivity.java:352)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",getClass().getName()+":96===>" + response);
                        Gson gson = new Gson();
                        SchoolInfoBean.DataBean schoolInfo = gson.fromJson(response, SchoolInfoBean.class).getData();
                        /*qualification_prove = schoolInfo.getQualification_prove();
                        initPhoto(qualification_prove);*/
                        state=schoolInfo.getQualiprove_state();
                        if(state.equals("0")){
                            qualification_prove = schoolInfo.getQualification_proves();
                            initPhoto(qualification_prove);
                        }else{
                            qualification_prove = schoolInfo.getQualification_prove();
                            initPhoto(qualification_prove);
                        }
                        switch (schoolInfo.getQualiprove_state()){
                            case "0":
                                tvShenhe.setText("（审核中）");
                                break;
                            case "2":
                                tvShenhe.setText("（已驳回）");
                                break;
                        }
                    }
                });
    }

    private void initPhoto(final String id_photo) {
        photos.clear();
        if (!TextUtils.isEmpty(id_photo))
            with(this).load(Internet.BASE_URL + id_photo).centerCrop().into(preView1);
        length = 1;
        num = 1;
    }

    @OnClick({R.id.rl_back, R.id.identity_tv, R.id.iv_preview,R.id.iv_menu})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, PhotoPickerActivity.class);
        switch (view.getId()) {
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.identity_tv:
                if(state.equals("0")){
                    Toast.makeText(this, "审核中，请勿重复提交", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                changeQualification();
                break;
            case R.id.iv_preview:
                startActivityForResult(intent, 0);
                break;
        }
    }

    private void changeQualification() {

        final JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < photos.size(); i++) {
            try {
                Log.e("aaa",
                        "(SellFragment.java:177)" + photos.get(i));
                Bitmap usericon = photos.get(i);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                usericon.compress(Bitmap.CompressFormat.JPEG, 40, baos);
                byte[] b = baos.toByteArray();
                String string = Base64.encodeToString(b, Base64.DEFAULT);
                jsonObject.put("image" + i, string);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if (photos.size() < 1 && TextUtils.isEmpty(qualification_prove)) {
            Toast.makeText(this, "请先选择资质认证图片", Toast.LENGTH_SHORT).show();
            return;
        }
        /*if (photos.size() < 1) {
            finish();
            return;
        }*/


        OkHttpUtils
                .post()
                .url(Internet.CHANGE_QUALIFICATION)
                .addParams("qualification_proves", jsonObject.toString())
                .addParams("user_id", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(QualificationActivity.java:147)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(QualificationActivity.java:153)" + response);
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            int result = jsonObject1.getInt("result");
                            String msg = jsonObject1.getString("msg");

                            if (result == 0) {
                                Toast.makeText(QualificationActivity.this, "我们已经收到您的资质信息更改，我们会在72小时内进行审核，请关注我们应用通知。", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(QualificationActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // switchPicture(mMultiPickResultView);
        PhotoPickUtils.onActivityResult(requestCode, resultCode, data, new PhotoPickUtils.PickHandler() {
            @Override
            public void onPickSuccess(ArrayList<String> photoss) {//已经预先做了null或size为0的判断
                Log.e("aaa", "--photos-ssss-->" + photos);
                if (requestCode == 0) {
                    Log.e("aaa",
                            "(IdentityCardActivity.java:161)" + "sadsadsa");
                    with(QualificationActivity.this).load(photoss.get(0)).centerCrop().into(preView1);
                    Bitmap usericon = Compressor.getDefault(QualificationActivity.this).compressToBitmap(new File(photoss.get(0)));
                    photos.add(usericon);
                }
            }

            @Override
            public void onPreviewBack(ArrayList<String> photos) {

            }

            @Override
            public void onPickFail(String error) {
                Toast.makeText(QualificationActivity.this, error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPickCancle() {
                Toast.makeText(QualificationActivity.this, "取消选择", Toast.LENGTH_LONG).show();
            }


        });

    }
}
