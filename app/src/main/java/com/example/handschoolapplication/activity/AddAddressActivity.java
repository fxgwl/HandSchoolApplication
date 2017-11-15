package com.example.handschoolapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.AddressBeanS;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.smarttop.library.bean.City;
import com.smarttop.library.bean.County;
import com.smarttop.library.bean.Province;
import com.smarttop.library.bean.Street;
import com.smarttop.library.widget.AddressSelector;
import com.smarttop.library.widget.BottomDialog;
import com.smarttop.library.widget.OnAddressSelectedListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class AddAddressActivity extends BaseActivity implements OnAddressSelectedListener, AddressSelector.OnDialogCloseListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_address_detail)
    EditText etAddressDetail;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    private TextView tvCity;

    private BottomDialog dialog;
    private String provinceCode;
    private String cityCode;
    private String countyCode;
    private String streetCode;
    private String citys;
    private String user_id;
    private AddressBeanS addressBean;
    private GeoCoder geoCoder;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("学堂地址");
        user_id = (String) SPUtils.get(this, "userId", "");
        tvCity = (TextView) findViewById(R.id.tv_city);
        dialog = new BottomDialog(this);
        dialog.setOnAddressSelectedListener(this);
        dialog.setDialogDismisListener(this);
        geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(listener);



    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_address;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_area,R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_commit:
                final String city = tvCity.getText().toString().trim();
                final String addressDetail = etAddressDetail.getText().toString().trim();
//                user_id
//                        sd_city
//                sd_content
//                        school_jing
//                school_wei
                if ("".equals(city) || "".equals(addressDetail)) {

                } else {
                    geoCoder.geocode(new GeoCodeOption()
                            .city("北京")
                            .address("海淀区上地十街10号"));
                    OkHttpUtils.post()
                            .url(Internet.ADDADDRESS)
                            .addParams("user_id", user_id)
                            .addParams("sd_city", citys)
                            .addParams("sd_content", addressDetail)
                            .addParams("school_jing", ""+longitude)
                            .addParams("school_wei", latitude+"")
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Log.e("aaa",
                                        "(AddAddressActivity.java:120)"+e.getMessage());
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.e("aaa",
                                            "(AddAddressActivity.java:94)" + response);
                                    setResult(11, new Intent()
                                            .putExtra("address", city)
                                            .putExtra("street", addressDetail));
                                }
                            });
                }

                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.ll_area:
                dialog.show();
                break;
        }
    }



    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {
        citys = (province == null ? "" : province.name) + (city == null ? "" : city.name);
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

    public class GetJsonDataUtil {


        public String getJson(Context context, String fileName) {

            StringBuilder stringBuilder = new StringBuilder();
            try {
                AssetManager assetManager = context.getAssets();
                BufferedReader bf = new BufferedReader(new InputStreamReader(
                        assetManager.open(fileName)));
                String line;
                while ((line = bf.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

    }


    OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
        public void onGetGeoCodeResult(GeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有检索到结果
                Toast.makeText(AddAddressActivity.this, "抱歉，未能找到结果", Toast.LENGTH_SHORT).show();
            }
            //获取地理编码结果
            latitude = result.getLocation().latitude;
            longitude = result.getLocation().longitude;
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有找到检索结果
            }
            //获取反向地理编码结果
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        geoCoder.destroy();
    }
}
