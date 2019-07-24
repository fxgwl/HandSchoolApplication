package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ClassSchoolAdapter;
import com.example.handschoolapplication.adapter.ClassSchoolsAdapter;
import com.example.handschoolapplication.adapter.PrimarySchoolAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ClassSchoolBean;
import com.example.handschoolapplication.bean.PrimaryBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.MyListView;
import com.google.gson.Gson;
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
import okhttp3.Call;

public class AddClassSchoolActivity extends BaseActivity implements OnAddressSelectedListener, AddressSelector.OnDialogCloseListener, AdapterView.OnItemClickListener, PrimarySchoolAdapter.OnTVClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    TextView etName;
    @BindView(R.id.lv_choose_school)
    MyListView lvChooseSchool;
    @BindView(R.id.lv_school_list)
    MyListView lvSchoolList;
    private BottomDialog dialog;
    private String school_id;

    private PrimarySchoolAdapter hadChooseAadpter;
    private ClassSchoolsAdapter unHadChooseAadpter;
    private List<PrimaryBean.DataBean> mChoosedList = new ArrayList<>();
    private List<ClassSchoolBean.DataBean> mList = new ArrayList<>();
    private LoadingDailog dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        school_id = (String) SPUtils.get(this, "school_id", "");
        initView();
        getSchoolList();

        lvSchoolList.setOnItemClickListener(this);
        dialog = new BottomDialog(this);
        dialog.setOnAddressSelectedListener(this);
        dialog.setDialogDismisListener(this);

        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(false)
                .setCancelOutside(false);
        dialog1 = loadBuilder.create();
    }

    private void initView() {
        tvTitle.setText("添加学校");
        hadChooseAadpter = new PrimarySchoolAdapter(mChoosedList, this);
        unHadChooseAadpter = new ClassSchoolsAdapter(mList, this);
        lvChooseSchool.setAdapter(hadChooseAadpter);
        lvSchoolList.setAdapter(unHadChooseAadpter);

        hadChooseAadpter.setListener(this);
    }

    private void getSchoolList() {
        mChoosedList.clear();
        OkHttpUtils.post()
                .url(Internet.GET_SCHOOL_BY_CLASS)
                .addParams("school_id", school_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(NearLittleSchoolActivity.java:48)<---->" + e.getMessage());
                        Toast.makeText(AddClassSchoolActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
                        hadChooseAadpter.notifyDataSetChanged();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(NearLittleSchoolActivity.java:55)<---->" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(AddClassSchoolActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
                            hadChooseAadpter.notifyDataSetChanged();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    PrimaryBean classSchoolBean = new Gson().fromJson(response, PrimaryBean.class);
                                    mChoosedList.addAll(classSchoolBean.getData());
                                    hadChooseAadpter.notifyDataSetChanged();
                                } else {
                                    String msg = jsonObject.getString("msg");
                                    Toast.makeText(AddClassSchoolActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    hadChooseAadpter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_class_school;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_choose_address, R.id.tv_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.ll_choose_address:
                dialog.show();
                break;
            case R.id.tv_comment:
                finish();
                Toast.makeText(AddClassSchoolActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {
//        provinceCode = (province == null ? "" : province.code);
//        cityCode = (city == null ? "" : city.code);
//        countyCode = (county == null ? "" : county.code);
//        streetCode = (street == null ? "" : street.code);
//        String s = (province == null ? "" : province.name) + (city == null ? "" : city.name) + (county == null ? "" : county.name);
        String pName = province == null ? "" : province.name;
        String cName = city == null ? "" : city.name;
        String CoName = county == null ? "" : county.name;

        if ("天津,北京,上海,重庆".contains(pName)) {
            CoName = cName;
            cName = pName + "市";
        }

        Log.e("aaa",
                "(AddClassSchoolActivity.java:81)<--province-->" + pName);
        Log.e("aaa",
                "(AddClassSchoolActivity.java:84)<--city-->" + cName);
        Log.e("aaa",
                "(AddClassSchoolActivity.java:86)<--county-->" + CoName);

        String s = pName + " " + cName + " " + CoName;
        getSchoolList(pName, cName, CoName);
        etName.setText(s);
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 根据省市区获取小学
     */
    private void getSchoolList(String pName, String cName, String coName) {
        mList.clear();
        HashMap<String, String> params = new HashMap<>();
        params.put("province", pName);
        params.put("city", cName);
        params.put("district", coName);
        OkHttpUtils.post()
                .url(Internet.GET_SCHOOL_BY_ADDRESS)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(AddClassSchoolActivity.java:118)<---->" + e.getMessage());
                        unHadChooseAadpter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AddClassSchoolActivity.java:124)<---->" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(AddClassSchoolActivity.this, "没有信息", Toast.LENGTH_SHORT).show();
                            unHadChooseAadpter.notifyDataSetChanged();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    ClassSchoolBean classSchoolBean = new Gson().fromJson(response, ClassSchoolBean.class);
                                    mList.addAll(classSchoolBean.getData());
                                } else {
                                    String msg = jsonObject.getString("msg");
                                    Toast.makeText(AddClassSchoolActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                                unHadChooseAadpter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                });
    }

    @Override
    public void dialogclose() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO: 2018/7/10 点击添加
        boolean isAdd = false;
        for (int i = 0; i < mChoosedList.size(); i++) {
            if (mChoosedList.get(i).getGakuen_id().equals(mList.get(position).getGakuen_id())) {
                Toast.makeText(this, "已添加该学校，请勿重复添加", Toast.LENGTH_SHORT).show();
                isAdd = true;
            }
        }
        if (!isAdd) {
            PrimaryBean.DataBean dataBean = new PrimaryBean.DataBean();
            dataBean.setBind_name(mList.get(position).getGakuen_name());
            dataBean.setGakuen_id(mList.get(position).getGakuen_id());
            mChoosedList.add(dataBean);
            commit();
        }
    }

    /**
     * 保存已选小学
     */
    private void commit() {

        dialog1.show();
        String commitStr = "";
        for (int i = 0; i < mChoosedList.size(); i++) {
            if (i == 0) {
                commitStr = mChoosedList.get(i).getGakuen_id() + "";
            } else {
                commitStr = commitStr + "," + mChoosedList.get(i).getGakuen_id();
            }
        }
        Log.e("aaa",
                "(AddClassSchoolActivity.java:139)<--添加的小学id-->" + commitStr);
        HashMap<String, String> params = new HashMap<>();
        params.put("school_id", school_id);
        params.put("gakuen_id", commitStr);
        OkHttpUtils.post()
                .url(Internet.ADD_SCHOOL_NEAR_CLASS)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(AddClassSchoolActivity.java:152)<---->" + e.getMessage());
                        Toast.makeText(AddClassSchoolActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AddClassSchoolActivity.java:158)<---->" + response);
                        dialog1.dismiss();
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(AddClassSchoolActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    hadChooseAadpter.notifyDataSetChanged();
                                }
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(AddClassSchoolActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });

    }

    @Override
    public void setOnClickListener(int position) {
        deleteSchool(position);
    }

    private void deleteSchool(int position) {
        OkHttpUtils.post()
                .url(Internet.DELECT_CLASS_SCHOOL)
                .addParams("gakuen_id", mChoosedList.get(position).getGakuen_id() + "")
                .addParams("school_id", school_id + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(NearLittleSchoolActivity.java:116)<---->" + e.getMessage());
                        Toast.makeText(AddClassSchoolActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(NearLittleSchoolActivity.java:122)<---->" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(AddClassSchoolActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(AddClassSchoolActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                getSchoolList();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
    }
}
