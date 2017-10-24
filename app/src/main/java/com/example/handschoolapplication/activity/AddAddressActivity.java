package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
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

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class AddAddressActivity extends BaseActivity implements OnAddressSelectedListener, AddressSelector.OnDialogCloseListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_address_detail)
    EditText etAddressDetail;
    private TextView tvCity;

    private BottomDialog dialog;
    private String provinceCode;
    private String cityCode;
    private String countyCode;
    private String streetCode;
    private String citys;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("学堂地址");
        user_id = (String) SPUtils.get(this, "userId", "");
        tvCity = (TextView) findViewById(R.id.tv_city);
        dialog = new BottomDialog(this);
        dialog.setOnAddressSelectedListener(this);
        dialog.setDialogDismisListener(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_address;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                String city = tvCity.getText().toString().trim();
                String addressDetail = etAddressDetail.getText().toString().trim();
//                user_id
//                        sd_city
//                sd_content
//                        school_jing
//                school_wei
                if ("".equals(city) || "".equals(addressDetail)) {

                } else {
                    setResult(11, new Intent()
                            .putExtra("address", city)
                            .putExtra("street", addressDetail));
                }
                OkHttpUtils.post()
                        .url(Internet.ADDADDRESS)
                        .addParams("user_id", user_id)
                        .addParams("sd_city", citys)
                        .addParams("sd_content", addressDetail)
                        .addParams("school_jing", "0")
                        .addParams("school_wei", "0")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("aaa",
                                        "(AddAddressActivity.java:94)" + response);

                            }
                        });

                finish();
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
}
