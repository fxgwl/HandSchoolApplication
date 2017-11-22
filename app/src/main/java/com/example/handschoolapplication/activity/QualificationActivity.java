package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.PhotoPickerActivity;
import okhttp3.Call;

public class QualificationActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_preview)
    ImageView preView1;
    @BindView(R.id.iv_preview2)
    ImageView preView2;
    @BindView(R.id.iv_preview3)
    ImageView preView3;
    @BindView(R.id.iv_preview4)
    ImageView preView4;
    @BindView(R.id.iv_preview5)
    ImageView preView5;
    @BindView(R.id.iv_preview6)
    ImageView preView6;


    private List<String> photo = new ArrayList<>();
    private List<Bitmap> photos = new ArrayList<>();
    private Bitmap bitmap;
    private String userId;
    private String[] split3;
    private int num = 0;//点击位置
    private int length = 0;//图片数量
    private boolean isFirst = false;

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

    @OnClick({R.id.rl_back, R.id.identity_tv, R.id.iv_preview, R.id.iv_preview2, R.id.iv_preview3, R.id.iv_preview4,
            R.id.iv_preview5, R.id.iv_preview6})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, PhotoPickerActivity.class);
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.identity_tv:
                changeQualification();
                break;
            case R.id.iv_preview:
                startActivityForResult(intent, 0);
                break;
            case R.id.iv_preview2:
                startActivityForResult(intent, 1);
                break;
            case R.id.iv_preview3:
                startActivityForResult(intent, 2);
                break;
            case R.id.iv_preview4:
                startActivityForResult(intent, 3);
                break;
            case R.id.iv_preview5:
                startActivityForResult(intent, 4);
                break;
            case R.id.iv_preview6:
                startActivityForResult(intent, 5);
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
                            Toast.makeText(QualificationActivity.this, msg, Toast.LENGTH_SHORT).show();
                            if (result==0){
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initPhoto(String id_photo) {

        photos.clear();

        split3 = id_photo.split("\\,");

        switch (split3.length) {
            case 1:
                Glide.with(this).load(Internet.BASE_URL + split3[0]).centerCrop().into(preView1);
                preView2.setVisibility(View.VISIBLE);
                preView3.setVisibility(View.GONE);
                preView4.setVisibility(View.GONE);
                preView5.setVisibility(View.GONE);
                preView6.setVisibility(View.GONE);
                length = 1;
                num = 1;
                break;
            case 2:
                Glide.with(this).load(Internet.BASE_URL + split3[0]).centerCrop().into(preView1);
                Glide.with(this).load(Internet.BASE_URL + split3[1]).centerCrop().into(preView2);
                preView3.setVisibility(View.VISIBLE);
                preView4.setVisibility(View.GONE);
                preView5.setVisibility(View.GONE);
                preView6.setVisibility(View.GONE);
                length = 2;
                num = 2;
                break;
            case 3:
                Glide.with(this).load(Internet.BASE_URL + split3[0]).centerCrop().into(preView1);
                Glide.with(this).load(Internet.BASE_URL + split3[1]).centerCrop().into(preView2);
                Glide.with(this).load(Internet.BASE_URL + split3[2]).centerCrop().into(preView3);
                preView4.setVisibility(View.VISIBLE);
                preView5.setVisibility(View.GONE);
                preView6.setVisibility(View.GONE);
                length = 3;
                num = 3;
                break;
            case 4:
                Glide.with(this).load(Internet.BASE_URL + split3[0]).centerCrop().into(preView1);
                Glide.with(this).load(Internet.BASE_URL + split3[1]).centerCrop().into(preView2);
                Glide.with(this).load(Internet.BASE_URL + split3[2]).centerCrop().into(preView3);
                Glide.with(this).load(Internet.BASE_URL + split3[3]).centerCrop().into(preView4);
                preView5.setVisibility(View.VISIBLE);
                preView6.setVisibility(View.GONE);
                length = 4;
                num = 4;
                break;
            case 5:
                Glide.with(this).load(Internet.BASE_URL + split3[0]).centerCrop().into(preView1);
                Glide.with(this).load(Internet.BASE_URL + split3[1]).centerCrop().into(preView2);
                Glide.with(this).load(Internet.BASE_URL + split3[2]).centerCrop().into(preView3);
                Glide.with(this).load(Internet.BASE_URL + split3[3]).centerCrop().into(preView4);
                Glide.with(this).load(Internet.BASE_URL + split3[4]).centerCrop().into(preView5);
                preView6.setVisibility(View.VISIBLE);
                length = 5;
                num = 5;
                break;
            case 6:
                Glide.with(this).load(Internet.BASE_URL + split3[0]).centerCrop().into(preView1);
                Glide.with(this).load(Internet.BASE_URL + split3[1]).centerCrop().into(preView2);
                Glide.with(this).load(Internet.BASE_URL + split3[2]).centerCrop().into(preView3);
                Glide.with(this).load(Internet.BASE_URL + split3[3]).centerCrop().into(preView4);
                Glide.with(this).load(Internet.BASE_URL + split3[4]).centerCrop().into(preView5);
                Glide.with(this).load(Internet.BASE_URL + split3[5]).centerCrop().into(preView6);
                length = 6;
                num = 6;
                break;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < split3.length; i++) {
                    final int finalI = i;
                    try {
                        bitmap = Glide.with(QualificationActivity.this)
                                .load(Internet.BASE_URL + split3[finalI])
                                .asBitmap().centerCrop()
                                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                .get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    photos.add(bitmap);
                }
            }
        }).start();
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // switchPicture(mMultiPickResultView);
        PhotoPickUtils.onActivityResult(requestCode, resultCode, data, new PhotoPickUtils.PickHandler() {
            @Override
            public void onPickSuccess(ArrayList<String> photoss) {//已经预先做了null或size为0的判断
                Log.e("aaa", "--photos-ssss-->" + photos);
//                for (int i = 0; i < photos.size(); i++) {
//                    String s = MyUtiles.imageToBase64(photos.get(i));
//                    photo.add(s);
//                }
                if (requestCode == 0) {
                    Log.e("aaa",
                            "(IdentityCardActivity.java:161)" + "sadsadsa");
                    Glide.with(QualificationActivity.this).load(photoss.get(0)).centerCrop().into(preView1);
                    Bitmap usericon = Compressor.getDefault(QualificationActivity.this).compressToBitmap(new File(photoss.get(0)));
                    photos.add(0, usericon);
                } else if (requestCode == 1) {
                    Glide.with(QualificationActivity.this).load(photoss.get(0)).centerCrop().into(preView2);
                    Bitmap usericon = Compressor.getDefault(QualificationActivity.this).compressToBitmap(new File(photoss.get(0)));
                    photos.add(1, usericon);
                    if (requestCode == num) {
                        preView3.setVisibility(View.VISIBLE);
                        num += 1;
                    }
                } else if (requestCode == 2) {
                    Glide.with(QualificationActivity.this).load(photoss.get(0)).centerCrop().into(preView3);
                    Bitmap usericon = Compressor.getDefault(QualificationActivity.this).compressToBitmap(new File(photoss.get(0)));
                    photos.add(2, usericon);
                    if (requestCode == num) {
                        preView4.setVisibility(View.VISIBLE);
                        num += 1;
                    }
                } else if (requestCode == 3) {
                    Glide.with(QualificationActivity.this).load(photoss.get(0)).centerCrop().into(preView4);
                    Bitmap usericon = Compressor.getDefault(QualificationActivity.this).compressToBitmap(new File(photoss.get(0)));
                    photos.add(3, usericon);
                    if (requestCode == num) {
                        preView5.setVisibility(View.VISIBLE);
                        num += 1;
                    }
                } else if (requestCode == 4) {
                    Glide.with(QualificationActivity.this).load(photoss.get(0)).centerCrop().into(preView5);
                    Bitmap usericon = Compressor.getDefault(QualificationActivity.this).compressToBitmap(new File(photoss.get(0)));
                    photos.add(4, usericon);
                    if (requestCode == num) {
                        preView5.setVisibility(View.VISIBLE);
                        num += 1;
                    }
                } else if (requestCode == 5) {
                    Glide.with(QualificationActivity.this).load(photoss.get(0)).centerCrop().into(preView6);
                    Bitmap usericon = Compressor.getDefault(QualificationActivity.this).compressToBitmap(new File(photoss.get(0)));
                    photos.add(5, usericon);
                    if (requestCode == num) {
                        preView6.setVisibility(View.VISIBLE);
                        num += 1;
                    }
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

    private void initData() {

        OkHttpUtils
                .post()
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
                                "(ClassTypeActivity.java:126)" + response);
                        Gson gson = new Gson();
                        SchoolInfoBean.DataBean schoolInfo = gson.fromJson(response, SchoolInfoBean.class).getData();
                        String qualification_prove = schoolInfo.getQualification_prove();
                        initPhoto(qualification_prove);
                    }
                });
    }
}
