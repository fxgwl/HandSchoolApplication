package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.SchoolInfoBean;
import com.example.handschoolapplication.utils.IDCard;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
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

public class IdentityCardActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.identity_et)
    EditText identityEt;
    @BindView(R.id.iv_preview)
    ImageView ivPreview;
    @BindView(R.id.iv_preview2)
    ImageView ivPreview2;

    private String userId, midphoto_state;
    private Bitmap bitmap, bitmap1;
    private List<Bitmap> photos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        userId = (String) SPUtils.get(this, "userId", "");
        midphoto_state = (String) SPUtils.get(this, "midphoto_state", "");
        initView();
        initData();

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_identity_card;
    }

    private void initView() {
        tvTitle.setText("身份认证");
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
                        if (TextUtils.isEmpty(response)) {

                        } else {
                            Gson gson = new Gson();
                            SchoolInfoBean.DataBean schoolInfo = gson.fromJson(response, SchoolInfoBean.class).getData();
                            String mid_photo = schoolInfo.getMid_photo();
                            String mid_photos = schoolInfo.getMid_photos();
                            String id_number = schoolInfo.getId_number();
                            initPhoto(mid_photo, mid_photos, id_number);
                        }

                    }
                });
    }

    private void initPhoto(final String id_photo, final String mid_photos, String id_number) {

        identityEt.setText(id_number);
        photos.clear();

        if (!TextUtils.isEmpty(id_photo)) {
            Log.e("aaa", "(IdentityCardActivity.java:114)<--id_photo-->" + id_photo);
            with(this).load(Internet.BASE_URL + id_photo).centerCrop().into(ivPreview);
        }


        if (!TextUtils.isEmpty(mid_photos)) {
            Log.e("aaa", "(IdentityCardActivity.java:120)<--mid_photos-->" + mid_photos);
            with(this).load(Internet.BASE_URL + mid_photos).centerCrop().into(ivPreview2);
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bitmap = with(IdentityCardActivity.this)
                            .load(Internet.BASE_URL + id_photo)
                            .asBitmap().centerCrop()
                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                    bitmap1 = with(IdentityCardActivity.this)
                            .load(Internet.BASE_URL + mid_photos)
                            .asBitmap().centerCrop()
                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if (!TextUtils.isEmpty(id_photo))
                    photos.add(bitmap);
                if (!TextUtils.isEmpty(mid_photos))
                    photos.add(bitmap1);

            }
        }).start();


    }

    @OnClick({R.id.rl_back, R.id.identity_tv, R.id.iv_preview, R.id.iv_preview2})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, PhotoPickerActivity.class);
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.identity_tv:
                changIdentity();
                break;
            case R.id.iv_preview:
                startActivityForResult(intent, 1);
                break;
            case R.id.iv_preview2:
                startActivityForResult(intent, 2);
                break;
        }
    }

    private void changIdentity() {

        String idNumber = identityEt.getText().toString().trim();
        if (TextUtils.isEmpty(idNumber)) {
            Toast.makeText(this, "身份证号码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (photos.size() < 2) {
            Toast.makeText(this, "请上传完整的信息", Toast.LENGTH_SHORT).show();
            return;
        }

        //        String personId = etPersonId.getText().toString().trim();//机构身份证号
        try {
            String s = IDCard.IDCardValidate(idNumber);
            if (s.equals("")) {
            } else {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        HashMap<String, String> params = new HashMap<>();
        final JSONObject jsonObject = new JSONObject();
        try {
            Log.e("aaa",
                    "(SellFragment.java:177)" + photos.get(0));
            Bitmap usericon = photos.get(0);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            usericon.compress(Bitmap.CompressFormat.JPEG, 40, baos);
            byte[] b = baos.toByteArray();
            String string = Base64.encodeToString(b, Base64.DEFAULT);
            jsonObject.put("image" + 0, string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final JSONObject jsonObject1 = new JSONObject();
        try {
            Log.e("aaa",
                    "(SellFragment.java:177)" + photos.get(1));
            Bitmap usericon = photos.get(1);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            usericon.compress(Bitmap.CompressFormat.JPEG, 40, baos);
            byte[] b = baos.toByteArray();
            String string = Base64.encodeToString(b, Base64.DEFAULT);
            jsonObject1.put("image" + 1, string);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        params.put("midphoto_card", jsonObject.toString());
        params.put("midphoto_cards", jsonObject1.toString());
        params.put("id_numbers", idNumber);
        params.put("user_id", userId);
        OkHttpUtils
                .post()
                .url(Internet.CHANGE_IDENTITY)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(IdentityCardActivity.java:198)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(IdentityCardActivity.java:204)" + response);
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            int result = jsonObject1.getInt("result");
                            String msg = jsonObject1.getString("msg");
                            Toast.makeText(IdentityCardActivity.this, msg, Toast.LENGTH_SHORT).show();
                            if (result == 0) {
                                finish();
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
        Log.d("aaa", "--photos--->" + photos);
        PhotoPickUtils.onActivityResult(requestCode, resultCode, data, new PhotoPickUtils.PickHandler() {
            @Override
            public void onPickSuccess(ArrayList<String> photoss) {//已经预先做了null或size为0的判断
                Log.e("aaa", "--photos-ssss-->" + photoss);
                if (requestCode == 1) {
                    Log.e("aaa",
                            "(IdentityCardActivity.java:161)" + "sadsadsa");

                    with(IdentityCardActivity.this).load(photoss.get(0)).centerCrop().into(ivPreview);
                    Bitmap usericon = Compressor.getDefault(IdentityCardActivity.this).compressToBitmap(new File(photoss.get(0)));
                    photos.add(0, usericon);
                } else if (requestCode == 2) {
                    Log.e("aaa",
                            "(IdentityCardActivity.java:165)" + "dfgdgfdgfd");
                    Log.e("aaa",
                            "(IdentityCardActivity.java:172)" + photoss.get(0));
                    with(IdentityCardActivity.this).load(photoss.get(0)).centerCrop().into(ivPreview2);
                    Bitmap usericon = Compressor.getDefault(IdentityCardActivity.this).compressToBitmap(new File(photoss.get(0)));
                    photos.add(1, usericon);
                }
            }

            @Override
            public void onPreviewBack(ArrayList<String> photos) {

            }

            @Override
            public void onPickFail(String error) {
                Toast.makeText(IdentityCardActivity.this, error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPickCancle() {
                Toast.makeText(IdentityCardActivity.this, "取消选择", Toast.LENGTH_LONG).show();
            }
        });

    }
}
