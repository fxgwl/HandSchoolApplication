package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.search.geocode.GeoCoder;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.Internet;
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

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


public class AddAddressActivity extends BaseActivity implements OnAddressSelectedListener, AddressSelector.OnDialogCloseListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_address_detail)
    TextView etAddressDetail;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.iv_menu)
    RelativeLayout rlMenu;
    private TextView tvCity;

    private BottomDialog dialog;
    private String provinceCode;
    private String cityCode;
    private String countyCode;
    private String streetCode;
    private String citys = "";
    private String user_id;
    private GeoCoder geoCoder;
    private String latitude;
    private String longitude;
    private String choosecity;
    private String chooseStreets;
    private String city;
    private String addressDetail;
    private String flag;
    private String sd_id;
    private String sd_city;
    private String mechanism_address;
    private String sd_content;
    private String school_wei;
    private String school_jing;
    private String pName;
    private String cName;
    private String coName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("添加机构地址");

        tvCity = (TextView) findViewById(R.id.tv_city);

        if (null != getIntent().getStringExtra("sd_id")) {
            //编辑状态
            flag = "0";
            initView();
        } else {
            rlMenu.setVisibility(View.GONE);
            String address = getIntent().getStringExtra("address");
            String detail = getIntent().getStringExtra("detail");
            tvCity.setText(address);
            etAddressDetail.setText(detail);

        }

        if (null != getIntent().getStringExtra("user_id")) {
            user_id = getIntent().getStringExtra("user_id");
        }

        dialog = new BottomDialog(this);
        dialog.setOnAddressSelectedListener(this);
        dialog.setDialogDismisListener(this);
    }

    private void initView() {

        sd_id = getIntent().getStringExtra("sd_id");
        sd_city = getIntent().getStringExtra("sd_city");
        mechanism_address = getIntent().getStringExtra("mechanism_address");
        sd_content = getIntent().getStringExtra("sd_content");
        school_wei = getIntent().getStringExtra("school_wei");
        school_jing = getIntent().getStringExtra("school_jing");
        tvCity.setText(sd_city);
        etAddressDetail.setText(sd_content);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        geoCoder.destroy();
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_area, R.id.tv_commit, R.id.ll_address_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_commit:
                if ("0".equals(flag)) {//编辑过来的
                    if (TextUtils.isEmpty(citys)) {//没编辑省市区
                        editAddress();
                    } else {
                        editAddressT();
                    }

                } else {
                    city = tvCity.getText().toString().trim();
                    addressDetail = etAddressDetail.getText().toString().trim();
                    if (TextUtils.isEmpty(city) || TextUtils.isEmpty(addressDetail)) {
                        Toast.makeText(this, "请填写完整的地址", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("aaa",
                                "(AddAddressActivity.java:135)<--user_id-->" + user_id);
                        if (null == user_id || "".equals(user_id) || "null".equals(user_id)) {
                            setResult(11, new Intent()
                                    .putExtra("street", addressDetail)
                                    .putExtra("address", city)
                                    .putExtra("latitude", latitude)
                                    .putExtra("longitude", longitude));
                            finish();
                        } else {
                            toadd();
                        }
                    }
                }

                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.ll_area:
//                startActivityForResult(new Intent(AddAddressActivity.this,LocationActivity.class),11);
                dialog.show();
                break;
            case R.id.ll_address_detail:
//                if (TextUtils.isEmpty(cName)){
//                    Toast.makeText(this, "请先选择所在", Toast.LENGTH_SHORT).show();
//                }
                startActivityForResult(new Intent(AddAddressActivity.this, GaodeLocationActivity.class)
                        .putExtra("city", cName), 11);
                break;
        }
    }

    private void editAddress() {

        HashMap<String, String> params = new HashMap<>();

        String trim = etAddressDetail.getText().toString().trim();
        params.put("sd_id", sd_id);
        params.put("sd_content", trim);
        params.put("school_jing", school_jing);
        params.put("school_wei", school_wei);
        OkHttpUtils.post()
                .url(Internet.CHANGE_SCHOOL_ADDRESS)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(AddAddressActivity.java:169)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AddAddressActivity.java:177)<---->" + response);
                        if (!TextUtils.isEmpty(response) && response.contains("修改成功")) {
                            Toast.makeText(AddAddressActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }
                });

    }

    private void editAddressT() {

        Log.e("aaa",
                "(AddAddressActivity.java:289)<--上传的经纬度-->" + latitude + " 经度===" + longitude);
        HashMap<String, String> params = new HashMap<>();

        String trim = etAddressDetail.getText().toString().trim();
        params.put("sd_id", sd_id);
        params.put("sd_content", trim);
        params.put("sd_city", citys);
        params.put("school_jing", school_jing + "");
        params.put("school_wei", school_wei + "");
        params.put("mechanism_address", mechanism_address);
        OkHttpUtils.post()
                .url(Internet.CHANGE_SCHOOL_ADDRESS)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(AddAddressActivity.java:169)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AddAddressActivity.java:177)<---->" + response);
                        if (!TextUtils.isEmpty(response)) {
                            finish();
                        }

                    }
                });
    }

    private void toadd() {

        Log.e("aaa",
                "(AddAddressActivity.java:325)<--上传的经纬度-->" + latitude + "   经度===" + longitude);
        Log.e("aaa",
                "(AddAddressActivity.java:335)<--sd_city-->" + citys + "------sd_content------" + addressDetail);

        Log.e("aaa",
                "(AddAddressActivity.java:260)<--user_id-->" + user_id);
        Log.e("aaa",
                "(AddAddressActivity.java:260)<--citys-->" + citys);
        Log.e("aaa",
                "(AddAddressActivity.java:260)<--addressDetail-->" + addressDetail);

        if (TextUtils.isEmpty(citys)) {
            Toast.makeText(this, "参数为空", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpUtils.post()
                .url(Internet.ADDADDRESS)
                .addParams("user_id", user_id)
                .addParams("sd_city", citys)
                .addParams("sd_content", addressDetail)
                .addParams("school_jing", "" + longitude)
                .addParams("school_wei", latitude + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(AddAddressActivity.java:120)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AddAddressActivity.java:94)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            if (response.contains("添加成功")) {
                                setResult(11, new Intent()
                                        .putExtra("street", addressDetail)
                                        .putExtra("address", citys)
                                        .putExtra("latitude", school_wei)
                                        .putExtra("longitude", school_jing));
                                finish();
                            }
                            Toast.makeText(AddAddressActivity.this, msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 11 && resultCode == 111) {
            etAddressDetail.setText(data.getStringExtra("Address"));
//            citys = data.getStringExtra("city");
            latitude = data.getStringExtra("Latitude");
            longitude = data.getStringExtra("Longitude");
            sd_content = data.getStringExtra("Address");

            school_wei = latitude;
            school_jing = longitude;

            Log.e("aaa",
                    "(AddAddressActivity.java:194)<--citys-->" + citys);
        }
    }

    //三级联动的回调
    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {

        provinceCode = (province == null ? "" : province.code);
        cityCode = (city == null ? "" : city.code);
        countyCode = (county == null ? "" : county.code);
        streetCode = (street == null ? "" : street.code);

        pName = province == null ? "" : province.name;
        cName = city == null ? "" : city.name;
        coName = county == null ? "" : county.name;

        if ("天津市北京市重庆市上海市".contains(pName)) {
            coName = cName;
            cName = pName;
        }

        String s = pName + "" + cName + "" + coName;

        choosecity = (province == null ? "" : province.name);
        Log.e("aaa",
                "(AddAddressActivity.java:156)" + choosecity);
        chooseStreets = (city == null ? "" : city.name) + (county == null ? "" : county.name) +
                (street == null ? "" : street.name);

        citys = s;
        tvCity.setText(s);
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    //三级联动的关闭回调
    @Override
    public void dialogclose() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
