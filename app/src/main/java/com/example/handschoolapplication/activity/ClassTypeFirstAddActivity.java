package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.TypeBean;
import com.example.handschoolapplication.utils.Internet;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.zhy.http.okhttp.OkHttpUtils.post;


/**
 * Created by Axehome_Mr.z on 2019/3/13
 * <p>
 * 注册用类别填写（包括修改类别 ） 这个界面不用接口请求
 */
public class ClassTypeFirstAddActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_type_one)
    ListView lvTypeOne;
    @BindView(R.id.gv_choose_type)
    GridView gvChooseType;
    @BindView(R.id.tf_type_two)
    TagFlowLayout tfTypeTwo;
    @BindView(R.id.iv_menu)
    RelativeLayout rlMenu;
    private String typeOne;
    private String typTwo;
    private String tyThree;
    private String flag = "";  //0 --->第一次添加   1--->修改
    private String userId;

    private List<String> typeTwo = new ArrayList<>();
    private ArrayList<String> typeThree = new ArrayList<>();//展示三级类型展示
    private ArrayList<TypeBean> typeThreeList = new ArrayList<>();//展示三级类型展示
    private ArrayList<TypeBean> chooseTypes = new ArrayList<>();
    private TwoTypeAdapter twoTypeAdapter;
    private TagAdapter<String> tagAdapter;
    private TypeChooseAdapter chooseAdapter;
    private int selectPosition = 0;

    private List<String> temp = new ArrayList<>();
    private String typeTwoOld;
    private String oldType = "";

//    private List<>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rlMenu.setVisibility(View.GONE);
        typeOne = getIntent().getStringExtra("type");
        flag = getIntent().getStringExtra("flag");
        oldType = getIntent().getStringExtra("oldType");//原来的类别
        Log.e("aaa", "(ClassTypeFirstAddActivity.java:83)<-- 原来的类别oldType-->" + oldType);
        Log.e("aaa", "(ClassTypeFirstAddActivity.java:91)<--修改的flag标记 -->" + flag);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("typetwo"))) {
            typeTwoOld = getIntent().getStringExtra("typetwo");
            Log.e("aaa",
                    "(ClassTypeAddActivity.java:80)<--上个页面传过来的typeTwo-->" + typeTwoOld);

            if (typeTwoOld.contains(",")) {
                String[] split = typeTwoOld.split(",");
                for (int i = 0; i < split.length; i++) {
                    String[] split1 = split[i].split(":");
                    typTwo = split1[0];

                    if (split1[1].contains("/")) {
                        String[] split2 = split1[1].split("/");
                        for (int j = 0; j < split2.length; j++) {
                            chooseTypes.add(new TypeBean(typeOne, typTwo, split2[j]));
                        }
                    } else {
                        chooseTypes.add(new TypeBean(typeOne, typTwo, split1[1]));
                    }
                }
            } else {
                String[] split1 = typeTwoOld.split(":");
                typTwo = split1[0];

                if (split1[1].contains("/")) {
                    String[] split2 = split1[1].split("/");
                    for (int i = 0; i < split2.length; i++) {
                        chooseTypes.add(new TypeBean(typeOne, typTwo, split2[i]));
                    }
                } else {
                    chooseTypes.add(new TypeBean(typeOne, typTwo, split1[1]));
                }
            }

        }
//        userId = (String) SPUtils.get(this, "userId", "");

        initView();
        getSecondList(typeOne);
    }

    private void initView() {
        tvTitle.setText(typeOne);

        twoTypeAdapter = new TwoTypeAdapter();
        lvTypeOne.setAdapter(twoTypeAdapter);
        chooseAdapter = new TypeChooseAdapter();
        gvChooseType.setAdapter(chooseAdapter);

        lvTypeOne.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = typeTwo.get(position);
                selectPosition = position;
                twoTypeAdapter.notifyDataSetChanged();
                getThirdList(s);
            }
        });

        tagAdapter = new TagAdapter<String>((ArrayList<String>) typeThree) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView textView = (TextView) View.inflate(ClassTypeFirstAddActivity.this, R.layout.type_textview, null);
                textView.setText(s);
                return textView;
            }
        };
        tfTypeTwo.setAdapter(tagAdapter);

//        chooseAdapter = new TagAdapter<String>(chooseTypes) {
//            @Override
//            public View getView(FlowLayout parent, int position, String s) {
//                TextView textView = (TextView) View.inflate(ClassTypeAddActivity.this, R.layout.textview, null);
//                textView.setText(s);
//                return textView;
//            }
//        };
//
//        tfTypeTwo.setAdapter(chooseAdapter);


        tfTypeTwo.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                String threeType = typeThree.get(position);
                boolean isHad = false;
                if (chooseTypes.size() > 0) {
                    for (int i = 0; i < chooseTypes.size(); i++) {
                        String typeThree = chooseTypes.get(i).getTypeThree();
                        if (threeType.equals(typeThree)) {
                            isHad = true;
                            break;
                        }
                    }
                    if (!isHad) {
                        chooseTypes.add(typeThreeList.get(position));
                    }else {
                        Toast.makeText(ClassTypeFirstAddActivity.this, "已包含该类别", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    chooseTypes.add(typeThreeList.get(position));
                }
                chooseAdapter.notifyDataSetChanged();
                return true;
            }
        });


    }

    private void getSecondList(final String typeOne) {
        typeTwo.clear();
        post()
                .url(Internet.GET_SECOND)
                .addParams("type_one_name", typeOne)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ClassTypeAddDetailActivity.java:75)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ClassTypeAddDetailActivity.java:81)" + response);
                        try {
                            if (response.contains("没有信息")) {
                            } else {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject jsonObject1 = data.getJSONObject(i);
                                    String type_two_name = jsonObject1.getString("type_two_name");
                                    typeTwo.add(type_two_name);
                                }

                                if (typeTwo.size() > 0) {
                                    getThirdList(typeTwo.get(0));
                                }
                                twoTypeAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getThirdList(String flag) {
        typeThree.clear();
        typeThreeList.clear();
        post()
                .url(Internet.GET_THIRD)
                .addParams("two_name", flag)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ClassTypeAddDetailActivity.java:111)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ClassTypeAddDetailActivity.java:117)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(ClassTypeFirstAddActivity.this, "没有信息", Toast.LENGTH_SHORT).show();
                            tagAdapter.notifyDataChanged();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject jsonObject1 = data.getJSONObject(i);
                                    String type_three_name = jsonObject1.getString("type_three_name");
                                    String two_name = jsonObject1.getString("two_name");//二级类型
                                    String one_name = jsonObject1.getString("one_name");//一级类型

                                    TypeBean typeBean = new TypeBean(one_name, two_name, type_three_name);
                                    typeThree.add(type_three_name);
                                    typeThreeList.add(typeBean);
                                }
                                tagAdapter.notifyDataChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_class_type_add;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.tv_comment:
                setData();
                break;
        }
    }

    private void setData() {

        Log.e("aaa",
                "(ClassTypeAddActivity.java:187)<--选择的集合-->" + chooseTypes.toString());
        if (chooseTypes.size() < 1) {
            Toast.makeText(this, "请选择类别", Toast.LENGTH_SHORT).show();
            return;
        }

        //定义一个二级分类的中间变量
        String tempTypeTwo;
        ArrayList<String> typetwos = new ArrayList<>();

        String lastStr = typeOne + "//";

        for (int i = 0; i < chooseTypes.size(); i++) {
            String typeTwo = chooseTypes.get(i).getTypeTwo();

            if (!typetwos.contains(typeTwo)) {
                typetwos.add(typeTwo);
            }

        }

        Log.e("aaa",
                "(ClassTypeAddActivity.java:205)<--去重后的集合-->" + typetwos.toString());

        String s = "";
        boolean isFirstAdd = true;

        for (int i = 0; i < typetwos.size(); i++) {
            s = typetwos.get(i);
            for (int j = 0; j < chooseTypes.size(); j++) {
                if (s.equals(chooseTypes.get(j).getTypeTwo())) {
                    if (isFirstAdd) {
                        lastStr = lastStr + s + ":" + chooseTypes.get(j).getTypeThree();
                        isFirstAdd = false;
                    } else {
                        lastStr = lastStr + "/" + chooseTypes.get(j).getTypeThree();
                    }
                }
            }
            if (i == typetwos.size() - 1) {

            } else {
                lastStr = lastStr + ",";
            }

            isFirstAdd = true;
        }

        Log.e("aaa",
                "(ClassTypeAddActivity.java:222)<---->" + lastStr);

        if ("0".equals(flag)) {
            addType(lastStr);
        } else if ("1".equals(flag)) {
            addType(";" + lastStr);
        } else {
//            changData(lastStr);
            String oldType2 = typeOne + "//" + typeTwoOld;
            Log.e("aaa", "(ClassTypeFirstAddActivity.java:366)<--olderType2-->" + oldType2);
            setResult(22, new Intent()
                    .putExtra("type", lastStr)
                    .putExtra("olderType", oldType2)
            );
            finish();
        }

    }

    private void addType(String type) {
        Log.e("aaa",
                "(ClassTypeFirstAddActivity.java:386)<--type-->" + type);

        setResult(22, new Intent().putExtra("type", type));
        finish();
//        OkHttpUtils
//                .post()
//                .url(Internet.ADD_CLASS_TYPE)
//                .addParams("user_id",userId)
//                .addParams("newType",type)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Log.e("aaa",
//                                "(ClassTypeAddDetailActivity.java:211)"+e.getMessage());
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        Log.e("aaa",
//                                "(ClassTypeAddDetailActivity.java:218)"+response);
//                        if (response.contains("修改成功")){
//                            finish();
//                        }
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String msg = jsonObject.getString("msg");
//                            Toast.makeText(ClassTypeFirstAddActivity.this, msg, Toast.LENGTH_SHORT).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
    }

    private void changData(String lastStr) {
        OkHttpUtils.post()
                .url(Internet.CHANGE_CLASS_TYPE)
                .addParams("user_id", userId)
                .addParams("newType", lastStr)
                .addParams("oldType", typeOne + "//" + typeTwoOld)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ClassTypeAddActivity.java:249)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ClassTypeAddActivity.java:255)<---->" + response);
                        if (response.contains("成功")) {
                            finish();
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(ClassTypeFirstAddActivity.this, msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    class TwoTypeAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return typeTwo.size();
        }

        class ViewHolder {
            @BindView(R.id.tv_type_two)
            TextView tvTypeTwo;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(ClassTypeFirstAddActivity.this, R.layout.item_two_type_lv, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvTypeTwo.setText(typeTwo.get(position));

            if (position == selectPosition) {
                holder.tvTypeTwo.setTextColor(Color.parseColor("#27acf6"));
                holder.tvTypeTwo.setBackgroundResource(R.drawable.type_one_tx_bg_select);
            } else {
                holder.tvTypeTwo.setTextColor(Color.parseColor("#333333"));
                holder.tvTypeTwo.setBackgroundResource(R.drawable.type_one_tx_bg_normal);
            }
            return convertView;
        }


    }


    class TypeChooseAdapter extends BaseAdapter {

        class ViewHolder {
            @BindView(R.id.ll_delete)
            LinearLayout llDelete;
            @BindView(R.id.tv_type_name)
            TextView tvTypeName;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        @Override
        public int getCount() {
            return chooseTypes.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(ClassTypeFirstAddActivity.this, R.layout.item_three_type_gv, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvTypeName.setText(chooseTypes.get(position).getTypeThree());
            holder.llDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseTypes.remove(position);
                    chooseAdapter.notifyDataSetChanged();
                }
            });

            return convertView;
        }


    }
}
