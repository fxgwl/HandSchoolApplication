package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.MyUtiles;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.widget.MultiPickResultView;
import okhttp3.Call;

public class IdentityCardActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.identity_et)
    EditText identityEt;
    @BindView(R.id.iv_preview)
    ImageView ivPreview;
    @BindView(R.id.iv_preview2)
    ImageView ivPreview2;
    @BindView(R.id.recycler_shenfenzheng)
    MultiPickResultView multiPickResultView;

    private List<String> photo = new ArrayList<>();
    private String userId;
    private Bitmap bitmap;
    private List<Bitmap> photos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        userId = (String) SPUtils.get(this, "userId", "");
        initView();
        initData();

    }

    private void initPhoto(String id_photo) {
        photos.clear();

        final String[] split3 = id_photo.split("\\,");

        switch (split3.length) {
            case 1:
                Glide.with(this).load(Internet.BASE_URL + split3[0]).centerCrop().into(ivPreview);
                break;
            case 2:
                Glide.with(this).load(Internet.BASE_URL + split3[0]).centerCrop().into(ivPreview);
                Glide.with(this).load(Internet.BASE_URL + split3[1]).centerCrop().into(ivPreview2);
                break;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < split3.length; i++) {
                    final int finalI = i;
                    try {
                        bitmap = Glide.with(IdentityCardActivity.this)
                                .load(Internet.BASE_URL + split3[finalI])
                                .asBitmap().centerCrop()
                                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                .get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }
                photos.add(bitmap);
            }
        }).start();
    }

    private void initData() {

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
                                "(ClassTypeActivity.java:126)" + response);
                        Gson gson = new Gson();
                        SchoolInfoBean.DataBean schoolInfo = gson.fromJson(response, SchoolInfoBean.class).getData();
                        String mid_photo = schoolInfo.getMid_photo();
                        String id_number = schoolInfo.getId_number();
                        initPhoto(mid_photo);
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_identity_card;
    }

    private void initView() {
        multiPickResultView.init(this, MultiPickResultView.ACTION_SELECT, null,0);
        tvTitle.setText("身份认证");
    }

    @OnClick({R.id.rl_back, R.id.identity_tv,R.id.iv_preview,R.id.iv_preview2})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, PhotoPickerActivity.class);
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.identity_tv:
                break;
            case R.id.iv_preview:
                startActivityForResult(intent, 1);
                break;
            case R.id.iv_preview2:
                startActivityForResult(intent, 2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        multiPickResultView.onActivityResult(requestCode, resultCode, data);
        ArrayList<String> photos = multiPickResultView.getPhotos();
        // switchPicture(mMultiPickResultView);
        Log.d("aaa", "--photos--->" + photos);
        PhotoPickUtils.onActivityResult(requestCode, resultCode, data, new PhotoPickUtils.PickHandler() {
            @Override
            public void onPickSuccess(ArrayList<String> photos) {//已经预先做了null或size为0的判断
                Log.e("aaa", "--photos-ssss-->" + photos);
                photo.clear();
                for (int i = 0; i < photos.size(); i++) {
                    String s = MyUtiles.imageToBase64(photos.get(i));
                    photo.add(s);
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
