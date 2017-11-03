package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.handschoolapplication.bean.HomeClassTypeBean;
import com.example.handschoolapplication.utils.Internet;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        addClassType();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_class_type;
    }

    private void initView() {
        tvTitle.setText("学堂类别");
        ClassTypeAdapter classTypeAdapter = new ClassTypeAdapter(this, new ArrayList<String>());
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
                                .putExtra("typeTwo", typeTowLists.get(position))
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

                        if (response.contains("没有信息")){}else {
                            try {
                                typeOneList.clear();
                                typeTwoList.clear();
                                typeThreeList.clear();
                                typeTowLists.clear();
                                typeThreeLists.clear();
                                Gson gson = new Gson();
                                HomeClassTypeBean homeClassType = gson.fromJson(response, HomeClassTypeBean.class);
                                for (int i = 0; i < homeClassType.getData().size(); i++) {
                                    typeOneList.add(homeClassType.getData().get(i).getType_one_name());
                                    if (null != homeClassType.getData().get(i).getTypeTwoInfo()){
                                        for (int j = 0; j < homeClassType.getData().get(i).getTypeTwoInfo().size(); j++) {
                                            typeTwoList.add(homeClassType.getData().get(i).getTypeTwoInfo().get(j).getType_two_name());
                                            if (null!=homeClassType.getData().get(i).getTypeTwoInfo().get(j).getTypeThreeInfo()){
                                                for (int k = 0; k < homeClassType.getData().get(i).getTypeTwoInfo().get(j).getTypeThreeInfo().size(); k++) {
                                                    typeThreeList.add(homeClassType.getData().get(i).getTypeTwoInfo().get(j).getTypeThreeInfo().get(k).getType_three_name());
                                                }
                                                typeThreeLists.add(typeThreeList);
                                            }
                                        }
                                        typeTowLists.add(typeTwoList);
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
}
