package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.SchoolAddressAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.AddressBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class SchoolAddressActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    RelativeLayout ivMenu;
    @BindView(R.id.lv_schooladdress)
    ListView lvSchooladdress;
    @BindView(R.id.tv_schooladdress_add)
    TextView tvSchooladdressAdd;
    ArrayList<AddressBean.DataBean> addressList = new ArrayList<>();
    private String user_id;
    private SchoolAddressAdapter schoolAddressAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("机构地址");
        user_id = (String) SPUtils.get(this, "userId", "");

        schoolAddressAdapter = new SchoolAddressAdapter(this, addressList, new SchoolAddressAdapter.DeleteClick() {
            @Override
            public void delet(final int position) {
                Log.e("aaa",
                        "(SchoolAddressActivity.java:54)" + addressList.toString());
                OkHttpUtils.post()
                        .url(Internet.DELETEADDRESS)
                        .addParams("sd_id", addressList.get(position).getSd_id() + "")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("aaa",
                                        "(SchoolAddressActivity.java:66)" + response);
                                addressList.remove(position);
                                schoolAddressAdapter.notifyDataSetChanged();
                            }
                        });
            }

            @Override
            public void edit(int position) {
                int sd_id = addressList.get(position).getSd_id();
                String sd_city = addressList.get(position).getSd_city();
                String sd_content = addressList.get(position).getSd_content();
                String mechanism_address = addressList.get(position).getMechanism_address();
                String school_wei = addressList.get(position).getSchool_wei();
                String school_jing = addressList.get(position).getSchool_jing();

                Intent intent = new Intent(SchoolAddressActivity.this, AddAddressActivity.class);
                intent.putExtra("sd_id", sd_id + "");
                intent.putExtra("sd_city", sd_city);
                intent.putExtra("sd_content", sd_content);
                intent.putExtra("mechanism_address", mechanism_address);
                intent.putExtra("school_wei", school_wei);
                intent.putExtra("school_jing", school_jing);
                intent.putExtra("user_id", user_id);

                Log.e("aaa",
                        "(SchoolAddressActivity.java:93)<--sd_id-->" + sd_city);
                startActivity(intent);
            }
        });
        lvSchooladdress.setAdapter(schoolAddressAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_school_address;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initAddressList();
    }

    //初始化地址列表
    private void initAddressList() {
        OkHttpUtils.post()
                .url(Internet.ADDRESSLIST)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SchoolAddressActivity.java:67)" + response);
                        addressList.clear();
                        if (TextUtils.isEmpty(response)) {
                            tvSchooladdressAdd.setVisibility(View.VISIBLE);
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int result = jsonObject.getInt("result");
                                if (result==0){
                                    tvSchooladdressAdd.setVisibility(View.GONE);
                                    Gson gson = new Gson();
//                        addressList.clear();
                                    addressList.addAll((ArrayList<AddressBean.DataBean>) gson.fromJson(response, AddressBean.class).getData());
                                }else {
                                    tvSchooladdressAdd.setVisibility(View.VISIBLE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        schoolAddressAdapter.notifyDataSetChanged();
                    }
                });
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_schooladdress_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.tv_schooladdress_add:
                startActivity(new Intent(this, AddAddressActivity.class)
                        .putExtra("user_id", user_id)
                );
                break;
        }
    }


}
