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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ClassTypeAddAdapter;
import com.example.handschoolapplication.adapter.ClassTypeFirstAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ClassType;
import com.example.handschoolapplication.bean.HomeClassTypeBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.view.MyListView;
import com.example.handschoolapplication.view.MyPopupWindow;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ClassTypeFirstActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.classtype_lv)
    MyListView classtypeLv;
    @BindView(R.id.iv_menu)
    RelativeLayout rlMenu;
    //第一个类别的数据源
    private List<String> typeOneList = new ArrayList<>();
    private ClassTypeAddAdapter addAdapter;
    private List<ClassType> mList = new ArrayList<>();
    private String schoolType;
    private ClassTypeFirstAdapter classTypeAdapter;
    private String flag = "0";//判断是否为第一次添加学堂类型
    private ArrayList<String> type = new ArrayList<>();
    private ArrayList<String> tempList;

    private String oldType = "";//原来的类别


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        rlMenu.setVisibility(View.GONE);
        initView();
//        initClassInfo();
        addClassType();
        //暂时注释
        oldType = getIntent().getStringExtra("type");
//        String type = "";
        if (oldType != null && !TextUtils.isEmpty(oldType)) {
            initClassInfo(oldType);
        }

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_class_type_first;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        initClassInfo();
    }

    private void initView() {
        tvTitle.setText("机构类别");
        classTypeAdapter = new ClassTypeFirstAdapter(this, mList);
        classtypeLv.setAdapter(classTypeAdapter);


        classTypeAdapter.setOnDelectType(new ClassTypeFirstAdapter.OnDelectTypeListener() {
            @Override
            public void setDelect(int position) {
                mList.remove(position);
                schoolType = "";
                classTypeAdapter.notifyDataSetChanged();

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
                Intent intent = new Intent(ClassTypeFirstActivity.this, ClassTypeAddActivity.class);
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
                        Log.e("aaa", "(ClassTypeFirstActivity.java:128)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ClassTypeActivity.java:177)" + response);

                        if (response.contains("没有信息")) {
                        } else {
                            try {
                                typeOneList.clear();
                                JSONObject jsonObject = new JSONObject(response);
                                int result = jsonObject.getInt("result");
                                if (result == 0) {
                                    Gson gson = new Gson();
                                    HomeClassTypeBean homeClassType = gson.fromJson(response, HomeClassTypeBean.class);
                                    for (int i = 0; i < homeClassType.getData().size(); i++) {
                                        typeOneList.add(homeClassType.getData().get(i).getType_one_name());
                                    }
                                }
                                addAdapter.notifyDataSetChanged();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    //初始化数据
    private void initClassInfo(String response) {
        type.clear();
        mList.clear();
        if (TextUtils.isEmpty(response)) {
        } else {
            schoolType = response;
            if (TextUtils.isEmpty(schoolType)) {
                mList.clear();
                flag = "0";
            } else {
//                    mList.clear();//这里不能清除集合数据
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
//                                                   mList.add(classType);
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

    @OnClick({R.id.rl_back, R.id.classtype_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:

//                String string = "";
//                if (mList != null) {

//                    for (int i = 0; i < mList.size(); i++) {
//                        String tempStr = mList.get(i).getTypeOne();
//                        Log.e("aaa", "(ClassTypeFirstActivity.java:127)<--tempStr-->" + tempStr);
//                        if (mList.get(i).getTypetwo() != null && mList.get(i).getTypetwo().size() > 0) {
//                            Log.e("aaa", "(ClassTypeFirstActivity.java:243)<---->" + mList.get(i).getTypetwo().get(0));
//                            tempStr = tempStr + "//";
//                            List<String> typetwo = mList.get(i).getTypetwo();
//                            for (int j = 0; j < typetwo.size(); j++) {
//                                if (j == 0) {
//                                    tempStr = tempStr + typetwo.get(j);
//                                } else {
//                                    tempStr = tempStr + "," + typetwo.get(j);
//                                }
//                            }
//                        }
//
//                        if (i == 0) {
//                            string = tempStr;
//                        } else {
//                            string = string + ";" + tempStr;
//                        }
//
//                    }

//                    Log.e("aaa",
//                            "(ClassTypeFirstActivity.java:145)<--string-->" + string);

                setResult(22, new Intent().putExtra("type", oldType));
//                }
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
                                oldType = typeOneList.get(position);
                                initClassInfo(oldType);
                            } else {
                                if (schoolType.contains(typeOneList.get(position))) {
                                    Toast.makeText(ClassTypeFirstActivity.this, "已添加此类别，无须重复添加！", Toast.LENGTH_SHORT).show();
                                } else
                                    oldType = oldType + ";" + typeOneList.get(position);
                                initClassInfo(oldType);
                            }
                        } else {
                            if (flag.equals("0")) {
                                startActivityForResult(new Intent(ClassTypeFirstActivity.this, ClassTypeFirstAddActivity.class)
                                        .putExtra("type", typeOneList.get(position))
                                        .putExtra("flag", flag), 11
                                );
                            } else {
                                if (schoolType.contains(typeOneList.get(position))) {
//                                    Toast.makeText(ClassTypeFirstActivity.this, "已添加此类别，无须重复添加！", Toast.LENGTH_SHORT).show();
                                    ClassType classType = null;

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
                                    Intent intent = new Intent(ClassTypeFirstActivity.this, ClassTypeFirstAddActivity.class);
                                    intent.putExtra("flag", "2");
                                    intent.putExtra("type", typeOne);
                                    intent.putExtra("typetwo", oldType);
                                    Log.e("aaa",
                                            "(ClassTypeActivity.java:111)<--oldType-->" + oldType);
                                    startActivityForResult(intent, 11);
                                } else {
                                    startActivityForResult(new Intent(ClassTypeFirstActivity.this, ClassTypeFirstAddActivity.class)
                                            .putExtra("type", typeOneList.get(position))
                                            .putExtra("flag", flag), 11);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 22) {
            String type = data.getStringExtra("type");
            String olderType2 = data.getStringExtra("olderType");
            Log.e("aaa",
                    "(ClassTypeFirstActivity.java:216)<--type-->" + type);
            Log.e("aaa", "(ClassTypeFirstActivity.java:360)<--原来的-->" + oldType);
            Log.e("aaa", "(ClassTypeFirstActivity.java:358)<--需要替换得 olderType2-->" + olderType2);
            if (!TextUtils.isEmpty(oldType)) {
                if (null == olderType2 || TextUtils.isEmpty(olderType2) || !oldType.contains(olderType2))
                    oldType = oldType + type;
                else oldType = oldType.replace(olderType2, type);
            } else oldType = type;
            Log.e("aaa", "(ClassTypeFirstActivity.java:360)<--替换后的-->" + oldType);

            initClassInfo(oldType);
        }
    }

}
