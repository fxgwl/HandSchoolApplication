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
import android.widget.Toast;

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

import org.json.JSONException;
import org.json.JSONObject;

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
    private ClassTypeAddAdapter addAdapter;
    private String userId;
    private List<ClassType> mList = new ArrayList<>();
    private String schoolType;
    private ClassTypeAdapter classTypeAdapter;
    private String flag = "0";//判断是否为第一次添加学堂类型
    private ArrayList<String> type;
    private ArrayList<String> tempList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        userId = (String) SPUtils.get(this, "userId", "");
        initView();
//        initClassInfo();
        addClassType();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_class_type;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initClassInfo();
    }

    private void initView() {
        tvTitle.setText("机构类别");
        classTypeAdapter = new ClassTypeAdapter(this, mList);
        classtypeLv.setAdapter(classTypeAdapter);

        classTypeAdapter.setOnDelectType(new ClassTypeAdapter.OnDelectTypeListener() {
            @Override
            public void setDelect(int position) {
                ClassType classType = mList.get(position);
                if (type.size() == 1) {
                    delectType(type.get(0));
                } else {
                    if (position == 0) {
//                    String string = ";" + classType.getTypeOne()+"//"+classType.getTypetwo();
                        String string = type.get(position);
                        string = string + ";";
                        delectType(string);
                    } else {
//                    String string = classType.getTypeOne()+"//"+classType.getTypetwo()+";";
                        String string = type.get(position);
                        string = ";" + string;
                        delectType(string);
                    }
                }
            }

            @Override
            public void setEdit(int position) {

                ClassType classType = mList.get(position);
                String typeOne = classType.getTypeOne();
                ArrayList<String> typetwo = (ArrayList<String>) classType.getTypetwo();
                String oldType = "";
                for (int i = 0; i < typetwo.size(); i++) {
                    if (i == typetwo.size() - 1) {
                        oldType = oldType + typetwo.get(i);
                    } else
                        oldType = oldType + typetwo.get(i) + ",";
                }
                Intent intent = new Intent(ClassTypeActivity.this, ClassTypeAddActivity.class);
                intent.putExtra("flag", "2");
                intent.putExtra("type", typeOne);
                intent.putExtra("typetwo", oldType);
                Log.e("aaa",
                        "(ClassTypeActivity.java:111)<--oldType-->" + oldType);
                startActivity(intent);
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
                                "(ClassTypeActivity.java:177)" + response);

                        if (response.contains("没有信息")) {
                        } else {
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

    private void delectType(final String delectType) {
        OkHttpUtils.post()
                .url(Internet.DELECT_CLASS_TYPE)
                .addParams("user_id", userId)
                .addParams("oldType", delectType)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ClassTypeActivity.java:131)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ClassTypeActivity.java:137)" + response);

                        Log.e("aaa",
                                "(ClassTypeActivity.java:121)-----delectType-----" + delectType);
                        if (response.contains("修改成功")) {
                            initClassInfo();
                        }
                    }
                });
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
                        if (TextUtils.isEmpty(response)) {
                        } else {
                            if (response.contains("没有信息")) {
                            } else {
                                Gson gson = new Gson();
                                SchoolInfoBean.DataBean schoolInfo = gson.fromJson(response, SchoolInfoBean.class).getData();
                                schoolType = schoolInfo.getMechanism_type();
                                if (TextUtils.isEmpty(schoolType)) {
                                    mList.clear();
                                    flag = "0";
                                } else {
                                    mList.clear();
                                    type = new ArrayList<>();
                                    String[] string = schoolType.split(";");
                                    for (int i = 0; i < string.length; i++) {
                                        if (TextUtils.isEmpty(string[i])) {
                                        } else {
                                            type.add(string[i]);//拆分第一类的字符串  代表的是整个list的大小
                                        }
                                    }

                                    //声明一个集合  添加去重过的数据
                                    tempList = new ArrayList<>();

                                    for (int i = 0; i < type.size(); i++) {
                                        if (type.get(i).contains("//")) {
                                            String[] split = type.get(i).split("//");
                                            if (!tempList.contains(split[0])) {
                                                tempList.add(split[0]);
                                            }
                                        } else {
                                            if (!tempList.contains(type.get(i))) {
                                                tempList.add(type.get(i));
                                            }
                                        }
                                    }

                                    for (int i = 0; i < type.size(); i++) {

                                        if (type.get(i).contains("//")) {
                                            String[] split = type.get(i).split("//");

                                            if (split[1].contains(",")) {
                                                String[] split1 = split[1].split(",");
                                                ArrayList<String> twoTypes = new ArrayList<>();
                                                for (int j = 0; j < split1.length; j++) {
                                                    twoTypes.add(split1[j]);
//                                                    ClassType classType = new ClassType(split[0], split1[j]);
//                                                    mList.add(classType);
                                                }
                                                ClassType classType = new ClassType(split[0], twoTypes);
                                                mList.add(classType);
                                            } else {
                                                ArrayList<String> twoTypes = new ArrayList<>();
                                                twoTypes.add(split[1]);
                                                ClassType classType = new ClassType(split[0], twoTypes);
                                                mList.add(classType);
                                            }

                                        } else {
                                            ClassType classType = new ClassType(type.get(i), new ArrayList<String>());
                                            mList.add(classType);
                                        }
                                    }
                                    flag = "1";
                                }
                            }

                            classTypeAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }

    @OnClick({R.id.rl_back, R.id.classtype_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                if (type != null && type.size() > 0) {
                    setResult(22, new Intent().putExtra("type", type.get(0)));
                }
                finish();
                break;
            case R.id.classtype_add:
                View view1 = View.inflate(this, R.layout.classtype_add, null);
                ListView lv = (ListView) view1.findViewById(R.id.lv_classtype_add);
                final MyPopupWindow myPopupWindow = new MyPopupWindow(this, view1);
                myPopupWindow.setHeight(700);
                myPopupWindow.showAtLocation(tvTitle, Gravity.BOTTOM, 0, 0);
                addAdapter = new ClassTypeAddAdapter(this, typeOneList);
                lv.setAdapter(addAdapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("aaa",
                                "(ClassTypeActivity.java:125)typeOneList.get(position) ===" + typeOneList.get(position));
                        if (position > 2) {
                            if (flag.equals("0")) {
                                addType(typeOneList.get(position));
                            } else {
                                if (schoolType.contains(typeOneList.get(position))) {
//                                    Toast.makeText(ClassTypeActivity.this, "已添加此类别，无须重复添加！", Toast.LENGTH_SHORT).show();

                                } else
                                    addType(";" + typeOneList.get(position));
                            }
                        } else {
                            if (flag.equals("0")) {
                                startActivity(new Intent(ClassTypeActivity.this, ClassTypeAddActivity.class)
                                        .putExtra("type", typeOneList.get(position))
                                        .putExtra("flag", flag)
                                );
                            } else {
                                if (schoolType.contains(typeOneList.get(position))) {
//                                    Toast.makeText(ClassTypeActivity.this, "已添加此类别，无须重复添加！", Toast.LENGTH_SHORT).show();
                                    ClassType classType = new ClassType();
                                    for (int i = 0; i < mList.size(); i++) {
                                        if (typeOneList.get(position).equals(mList.get(i).getTypeOne())) {
                                            classType = mList.get(i);
                                        }
                                    }
                                    String typeOne = classType.getTypeOne();
                                    ArrayList<String> typetwo = (ArrayList<String>) classType.getTypetwo();
                                    String oldType = "";
                                    for (int i = 0; i < typetwo.size(); i++) {
                                        if (i == typetwo.size() - 1) {
                                            oldType = oldType + typetwo.get(i);
                                        } else
                                            oldType = oldType + typetwo.get(i) + ",";
                                    }
                                    Intent intent = new Intent(ClassTypeActivity.this, ClassTypeAddActivity.class);
                                    intent.putExtra("flag", "2");
                                    intent.putExtra("type", typeOne);
                                    intent.putExtra("typetwo", oldType);
                                    Log.e("aaa",
                                            "(ClassTypeActivity.java:111)<--oldType-->" + oldType);
                                    startActivity(intent);
                                } else {
                                    startActivity(new Intent(ClassTypeActivity.this, ClassTypeAddActivity.class)
                                            .putExtra("type", typeOneList.get(position))
                                            .putExtra("flag", flag)
                                    );
                                }
                            }
                        }
                        myPopupWindow.dismiss();
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

    private void addType(String type) {

        OkHttpUtils
                .post()
                .url(Internet.ADD_CLASS_TYPE)
                .addParams("user_id", userId)
                .addParams("newType", type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ClassTypeAddDetailActivity.java:211)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ClassTypeAddDetailActivity.java:218)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(ClassTypeActivity.this, msg, Toast.LENGTH_SHORT).show();
                            initClassInfo();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
