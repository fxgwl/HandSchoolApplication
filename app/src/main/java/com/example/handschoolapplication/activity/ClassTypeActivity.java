package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ClassTypeAdapter;
import com.example.handschoolapplication.adapter.ClassTypeAddAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ClassType;
import com.example.handschoolapplication.bean.HomeClassTypeBean;
import com.example.handschoolapplication.bean.SchoolInfoBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.MyListView;
import com.example.handschoolapplication.view.MyPopupWindow;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ClassTypeActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.classtype_lv)
    MyListView classtypeLv;
    //第一个类别的数据源
    private List<String> typeOneList = new ArrayList<>();
    //第二个类别的数据源
    private ArrayList<String> typeTwoList = new ArrayList<>();
    private List<ArrayList<String>> typeTowLists = new ArrayList<>();
    //第三个类别的数据源
    private ArrayList<String> typeThreeList = new ArrayList<>();
    private List<ArrayList<String>> typeThreeLists = new ArrayList<>();
    private ClassTypeAddAdapter addAdapter;
    private String userId;
    private List<ClassType> mList = new ArrayList<>();
    private String schoolType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        userId = (String) SPUtils.get(this, "userId", "");
        if (null != getIntent().getStringExtra("schoolType")){
            schoolType = getIntent().getStringExtra("schoolType");
        }
        if (TextUtils.isEmpty(schoolType)){
            mList.clear();
        }else {
            mList.clear();
            ArrayList<String> type = new ArrayList<>();
            String[] string = schoolType.split(";");
            for (int i = 0; i < string.length; i++) {
                if (TextUtils.isEmpty(string[i])){

                }else {
                    type.add(string[i]);
                }
            }

            for (int i = 0; i < type.size(); i++) {
                String[] split = type.get(i).split("//");
                ClassType classType = new ClassType(split[0], split[1]);
                mList.add(classType);
            }
        }
        initView();
        initClassInfo();
        addClassType();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_class_type;
    }

    private void initView() {
        tvTitle.setText("学堂类别");
        ClassTypeAdapter classTypeAdapter = new ClassTypeAdapter(this,mList);
        classtypeLv.setAdapter(classTypeAdapter);
    }

    @OnClick({R.id.rl_back, R.id.tv_save, R.id.classtype_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                setResult(22,new Intent().putExtra("type","文体艺术/音乐"));
                finish();
                break;
            case R.id.tv_save:
                break;
            case R.id.classtype_add:
                View view1 = View.inflate(this, R.layout.classtype_add, null);
                ListView lv = (ListView) view1.findViewById(R.id.lv_classtype_add);
                MyPopupWindow myPopupWindow = new MyPopupWindow(this, view1);
                myPopupWindow.setHeight(500);
                myPopupWindow.showAtLocation(tvTitle, Gravity.BOTTOM, 0, 0);
                addAdapter = new ClassTypeAddAdapter(this,typeOneList);
                lv.setAdapter(addAdapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(new Intent(ClassTypeActivity.this, ClassTypeAddDetailActivity.class)
                                .putExtra("type",typeOneList.get(position)));
                    }
                });
                myPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                break;
        }
    }

    //初始化数据
    private void initClassInfo() {
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
                        schoolType = schoolInfo.getMechanism_type();

                    }
                });
    }

    private void addClassType() {
        OkHttpUtils.post()
                .url(Internet.CLASSTYPE)
                .params(new HashMap<String, String>())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                            "(ClassTypeActivity.java:177)"+response);

                        if (response.contains("没有信息")){}else {
                            try {
                                typeOneList.clear();
                                Gson gson = new Gson();
                                HomeClassTypeBean homeClassType = gson.fromJson(response, HomeClassTypeBean.class);
                                for (int i = 0; i < homeClassType.getData().size(); i++) {
                                    typeOneList.add(homeClassType.getData().get(i).getType_one_name());
                                }
                                addAdapter.notifyDataSetChanged();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}
