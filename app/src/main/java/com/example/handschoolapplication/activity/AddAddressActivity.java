package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.smarttop.library.bean.City;
import com.smarttop.library.bean.County;
import com.smarttop.library.bean.Province;
import com.smarttop.library.bean.Street;
import com.smarttop.library.widget.AddressSelector;
import com.smarttop.library.widget.BottomDialog;
import com.smarttop.library.widget.OnAddressSelectedListener;

import butterknife.BindView;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("学堂地址");

        tvCity= (TextView) findViewById(R.id.tv_city);
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
                if ("".equals(city)||"".equals(addressDetail)){

                }else {
                    setResult(11,new Intent()
                            .putExtra("address",city)
                    .putExtra("street",addressDetail));
                }
                finish();
                break;
            case R.id.iv_menu:
                break;
            case R.id.ll_area:
                dialog.show();
                break;
        }
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
