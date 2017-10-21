package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.MyUtiles;
import com.smarttop.library.bean.City;
import com.smarttop.library.bean.County;
import com.smarttop.library.bean.Province;
import com.smarttop.library.bean.Street;
import com.smarttop.library.widget.AddressSelector;
import com.smarttop.library.widget.BottomDialog;
import com.smarttop.library.widget.OnAddressSelectedListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.widget.MultiPickResultView;
import okhttp3.Call;

public class AddDataActivity extends BaseActivity implements OnAddressSelectedListener, AddressSelector.OnDialogCloseListener {

    @BindView(R.id.recycler_zizhi)
    MultiPickResultView recyclerZizhi;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_time)
    EditText etTime;
    @BindView(R.id.et_person_id)
    EditText etPersonId;
    @BindView(R.id.recycler_shenfenzheng)
    MultiPickResultView recyclerShenfenzheng;
    private String type;
    private BottomDialog dialog;
    private TextView tvCity, tvAddress, tvType;
    private String provinceCode;
    private String cityCode;
    private String countyCode;
    private String streetCode;

    private List<String> photoStr = new ArrayList<>();
    private List<String> idencardStr = new ArrayList<>();
    private String userId;
    private String address;
    private String street;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerZizhi.init(this, MultiPickResultView.ACTION_SELECT, null, 0);
        recyclerShenfenzheng.init(this, MultiPickResultView.ACTION_SELECT, null, 1);
        type = getIntent().getStringExtra("type");
        tvTitle.setText("资料填写");
        tvCity = (TextView) findViewById(R.id.tv_city);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvType = (TextView) findViewById(R.id.tv_type);

        dialog = new BottomDialog(this);
        dialog.setOnAddressSelectedListener(this);
        dialog.setDialogDismisListener(this);
        userId = MyUtiles.getBeanByFastJson(this, "userId", String.class);
        Log.e("aaa",
                "(AddDataActivity.java:72)" + "本地保存的Id为" + userId);
    }

    public int getContentViewId() {
        return R.layout.activity_add_data;
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            recyclerZizhi.onActivityResult(requestCode, resultCode, data);
        } else {
            recyclerShenfenzheng.onActivityResult(requestCode, resultCode, data);
        }
        PhotoPickUtils.onActivityResult(requestCode, resultCode, data, new PhotoPickUtils.PickHandler() {
            @Override
            public void onPickSuccess(ArrayList<String> photos) {//已经预先做了null或size为0的判断
                Log.d("aaa", "--photos-ssss-->" + photos);
                if (requestCode == 0) {
                    Log.e("aaa",
                            "(AddDataActivity.java:103)" + "===111====第一个选择" + photos.toString());
                    for (int i = 0; i < photos.size(); i++) {
                        String s = MyUtiles.imageToBase64(photos.get(i));
                        photoStr.add(s);
                    }
                } else {
                    Log.e("aaa",
                            "(AddDataActivity.java:103)" + "===222====第二个选择" + photos.toString());
                    for (int i = 0; i < photos.size(); i++) {
                        String s = MyUtiles.imageToBase64(photos.get(i));
                        idencardStr.add(s);
                    }
                }
            }

            @Override
            public void onPreviewBack(ArrayList<String> photos) {

            }

            @Override
            public void onPickFail(String error) {
                Toast.makeText(AddDataActivity.this, error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPickCancle() {
                Toast.makeText(AddDataActivity.this, "取消选择", Toast.LENGTH_LONG).show();
            }


        });

        if (requestCode == 1 && resultCode == 11) {
            address = data.getStringExtra("address");
            street = data.getStringExtra("street");
            tvAddress.setText(street);
        }
        if (requestCode == 2 && resultCode == 22) {
            String type = data.getStringExtra("type");
            tvType.setText(type);
        }

    }

    @OnClick({R.id.rl_back, R.id.iv_address, R.id.iv_category, R.id.iv_right, R.id.btn_commit, R.id.ll_address, R.id.ll_class_address, R.id.ll_category})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_address:
            case R.id.ll_address:
                dialog.show();
                break;
            case R.id.iv_category:
            case R.id.ll_category:
                startActivityForResult(new Intent(this, ClassTypeActivity.class), 2);
                break;
            case R.id.iv_right:
            case R.id.ll_class_address:
                startActivityForResult(new Intent(this, AddAddressActivity.class), 1);
                break;
            case R.id.btn_commit:
                addData();
//                startActivity(new Intent(AddDataActivity.this, LoginActivity.class).putExtra("type", type));
//                finish();
                break;
        }
    }

    private void addData() {
        String name = etName.getText().toString().trim();//机构名称
        String city = tvCity.getText().toString().trim();//机构所在城市
        String personId = etPersonId.getText().toString().trim();//机构身份证号
        String type = tvType.getText().toString().trim();//机构类型
//        String address = tvAddress.getText().toString().trim();//机构所在地址
        String time = etTime.getText().toString().trim();//机构所在地址

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(city) || TextUtils.isEmpty(personId)
                || TextUtils.isEmpty(type) || TextUtils.isEmpty(address) || TextUtils.isEmpty(street) || TextUtils.isEmpty(time)) {
            Toast.makeText(this, "输入信息不完整", Toast.LENGTH_SHORT).show();
            return;
        }

        if (photoStr.size() < 1) {
            Toast.makeText(this, "请上传身份证图片及资质证明", Toast.LENGTH_SHORT).show();
            return;
        }
//        user_id
//                mechanism_name
//        mechanism_city
//                mechanism_ctime
//        id_number
//                mechanism_type
//
//        qualification_prove
//                mid_photo
//        sd_city
//                sd_content
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);//用户Id
        params.put("mechanism_name", name);//机构名称
        params.put("mechanism_city", city);//机构所在城市
        params.put("mechanism_ctime", time);//成立时间
        params.put("id_number", personId);//身份证号
        params.put("mechanism_type", type);//机构类型
        final JSONObject jsonObject = new JSONObject();
        JSONObject json2 = new JSONObject();
        try {
            for (int i = 0; i < photoStr.size(); i++) {
                jsonObject.put("image" + i, photoStr.get(i));
            }

            for (int i = 0; i < photoStr.size(); i++) {
                json2.put("photo" + i, idencardStr.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        params.put("qualification_prove", jsonObject.toString());//资质证明
        params.put("mid_photo", json2.toString());//身份证照片
        params.put("sd_city", address);//资质证明
        params.put("sd_content", street);//资质证明

        Log.e("aaa",
                "(AddDataActivity.java:204)params ==== ==== ==" + params);

        OkHttpUtils.post()
                .url(Internet.COMMITINFO)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(AddDataActivity.java:211)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("aaa",
                                "(AddDataActivity.java:218)" + response);

                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            String msg = jsonObject1.getString("msg");
                            Toast.makeText(AddDataActivity.this, msg, Toast.LENGTH_SHORT).show();
                            if (jsonObject1.getInt("result") == 0) {
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });

    }


    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {
        provinceCode = (province == null ? "" : province.code);
        cityCode = (city == null ? "" : city.code);
        countyCode = (county == null ? "" : county.code);
        streetCode = (street == null ? "" : street.code);
        String s = (province == null ? "" : province.name) + (city == null ? "" : city.name) + (county == null ? "" : county.name) +
                (street == null ? "" : street.name);
        tvCity.setText(s);
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void dialogclose() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}
