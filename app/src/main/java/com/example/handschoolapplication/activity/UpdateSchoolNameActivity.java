package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.PhotoPickerActivity;
import okhttp3.Call;

import static com.bumptech.glide.Glide.with;

/**
 * Created by Axehome_Mr.Z on ${date}
 */
public class UpdateSchoolNameActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_school_name)
    TextView etSchoolName;
    @BindView(R.id.et_school_detile)
    TextView etSchoolDetile;
    @BindView(R.id.iv_preview)
    ImageView preView1;


    private List<String> photo = new ArrayList<>();
    private List<Bitmap> photos = new ArrayList<>();
    private Bitmap bitmap;
    private String userId;
    private String[] split3;
    private int num = 0;//点击位置
    private int length = 0;//图片数量
    private boolean isFirst = false;
    private String qualification_prove = "";
    private boolean push=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_school_name);
        ButterKnife.bind(this);
        userId = (String) SPUtils.get(this, "userId", "");
        initView();
        initData();
    }
    @Override
    public int getContentViewId() {
        return R.layout.activity_update_school_name;
    }

    private void initView() {
        tvTitle.setText("机构名称");
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
                        Log.e("aaa", getClass().getName()+":89" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",getClass().getName()+":96===>" + response);
                        Gson gson = new Gson();
                        SchoolInfoBean.DataBean schoolInfo = gson.fromJson(response, SchoolInfoBean.class).getData();
                        if(schoolInfo.getMechanism_name_state().equals("1")){
                            etSchoolName.setText(schoolInfo.getMechanism_name());
                            etSchoolDetile.setText("");
                            qualification_prove = "";
                        }else{
                            if(schoolInfo.getMechanism_name_state().equals("0")||schoolInfo.getMechanism_name_state().equals("3")){
                                push=false;
                                etSchoolName.setText(schoolInfo.getMechanism_name_new());
                                etSchoolDetile.setText(schoolInfo.getMechanism_name_des());
                                qualification_prove = schoolInfo.getMechanism_name_img();
                            }else{
                                etSchoolName.setText(schoolInfo.getMechanism_name());
                                etSchoolDetile.setText("");
                                qualification_prove = "";
                            }
                            initPhoto(qualification_prove);
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
                if(push){
                    changeQualification();
                }else{
                    Toast.makeText(getApplication(),"审核中，请勿重复提交！",Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                break;
            case R.id.iv_preview:
                startActivityForResult(intent, 0);
                break;
        }
    }

    private void changeQualification() {
        String string="";
        final JSONObject jsonObject = new JSONObject();
        Map<String,String> map= new HashMap<String,String>();
        for (int i = 0; i < photos.size(); i++) {
            try {
                Log.e("aaa",getClass().getName()+"===>:135" + photos.get(i));
                Bitmap usericon = photos.get(i);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                usericon.compress(Bitmap.CompressFormat.JPEG, 40, baos);
                byte[] b = baos.toByteArray();
                string = Base64.encodeToString(b, Base64.DEFAULT);
                jsonObject.put("image" + i, string);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if (photos.size() < 1 && TextUtils.isEmpty(qualification_prove)) {
            Toast.makeText(this, "请先选择资质认证图片", Toast.LENGTH_SHORT).show();
            return;
        }
        if(etSchoolName.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "请输入机构名称", Toast.LENGTH_SHORT).show();
            return;
        }
        if(etSchoolDetile.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "请输入申请说明", Toast.LENGTH_SHORT).show();
            return;
        }
        if (photos.size() < 1) {

        }else{
            map.put("mechanism_name_img",jsonObject.toString());
        }
        map.put("mechanism_name",etSchoolName.getText().toString().trim());
        map.put("user_id",userId);
        map.put("mechanism_name_des",etSchoolDetile.getText().toString().trim());

        OkHttpUtils
                .post()
                .url(Internet.modifyCompanyName)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",getClass().getName()+":166" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",getClass().getName()+":172"+ response.toString());
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            int result = jsonObject1.getInt("result");
                            String msg = jsonObject1.getString("msg");
                            if (result == 0) {
                                Toast.makeText(getApplication(), "提交成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
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
                Log.e("aaa", getClass().getName()+":194--photos-ssss-->" + photos);
                if (requestCode == 0) {
                    photos.clear();
                    Log.e("aaa",getClass().getName()+":198" + "sadsadsa");
                    with(getApplication()).load(photoss.get(0)).centerCrop().into(preView1);
                    Bitmap usericon = Compressor.getDefault(getApplication()).compressToBitmap(new File(photoss.get(0)));
                    photos.add(usericon);
                }
            }

            @Override
            public void onPreviewBack(ArrayList<String> photos) {

            }

            @Override
            public void onPickFail(String error) {
                Toast.makeText(getApplication(), error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPickCancle() {
                Toast.makeText(getApplication(), "取消选择", Toast.LENGTH_LONG).show();
            }


        });

    }
}
