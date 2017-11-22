package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.GalleryAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
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
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ClassTypeAddDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ctad_hl_art)
    RecyclerView ctadHlArt;
    @BindView(R.id.gv_art_detail)
    TagFlowLayout gvArtDetail;
    @BindView(R.id.tv_type_one)
    TextView tvTypeOne;
    @BindView(R.id.actad_config)
    TextView tvOk;
    private List<String> typeTwo = new ArrayList<>();
    private ArrayList<String> typeThree = new ArrayList<>();
    private String typeOne;
    private String typeTwoName;
    private GalleryAdapter galleryAdapter;
    private TagAdapter<String> tagAdapter;

    private String threeType = "";
    private String twoType;
    private String userId;
    private Integer[] position;
    private String flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        typeOne = getIntent().getStringExtra("type");
        flag = getIntent().getStringExtra("flag");
        userId = (String) SPUtils.get(this, "userId", "");
        initView();
        getSecondList(typeOne);
//        getThirdList(typeTwoName);
    }

    private void getSecondList(final String typeOne) {
        typeTwo.clear();
        OkHttpUtils.post()
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
                            if (response.contains("没有信息")){}else {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject jsonObject1 = data.getJSONObject(i);
                                    String type_two_name = jsonObject1.getString("type_two_name");
                                    typeTwo.add(type_two_name);
                                }
                                galleryAdapter.setList(typeTwo);
                                galleryAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    private void getThirdList(String flag) {
        typeThree.clear();
        OkHttpUtils.post()
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
                            Toast.makeText(ClassTypeAddDetailActivity.this, "没有信息", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject jsonObject1 = data.getJSONObject(i);
                                    String type_three_name = jsonObject1.getString("type_three_name");
                                    typeThree.add(type_three_name);
                                }
//                                tagAdapter.notifyDataChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        tagAdapter.notifyDataChanged();
                    }
                });

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_class_type_add_detail;
    }

    private void initView() {
        tvTitle.setText("添加学堂类别");
        tvTypeOne.setText(typeOne);
        galleryAdapter = new GalleryAdapter(this, typeTwo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ctadHlArt.setLayoutManager(linearLayoutManager);
        ctadHlArt.setAdapter(galleryAdapter);
        galleryAdapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                twoType = typeTwo.get(position)+":";
                Log.e("aaa",
                    "(ClassTypeAddDetailActivity.java:168)"+twoType);
                galleryAdapter.setSelectedPosition(position);
                galleryAdapter.notifyDataSetChanged();
                getThirdList(typeTwo.get(position));
            }
        });

        tagAdapter = new TagAdapter<String>((ArrayList<String>) typeThree) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView textView = (TextView) View.inflate(ClassTypeAddDetailActivity.this, R.layout.textview, null);
                textView.setText(s);
                return textView;
            }
        };
        gvArtDetail.setAdapter(tagAdapter);

        gvArtDetail.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                threeType = threeType+"/"+typeThree.get(position);//得到三级标签
                return true;
            }
        });

        gvArtDetail.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                int size = selectPosSet.size();
                position = selectPosSet.toArray(new Integer[size]);
            }
        });

    }

    @OnClick({R.id.rl_back,R.id.actad_config})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.actad_config:
                threeType = "";
                for (int i = 0; i < position.length; i++) {
                    threeType = threeType+"/"+typeThree.get(position[i]);
                }
                String substring = threeType.substring(1, threeType.length());
                String string = twoType+substring;
//                Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
//                addType(string);
                if ("0".equals(flag)){
                    addType(typeOne+"//"+string);
                }else {
                    addType(";"+typeOne+"//"+string);
                }
                break;
        }
    }

    private void addType(String type) {

        OkHttpUtils
                .post()
                .url(Internet.ADD_CLASS_TYPE)
                .addParams("user_id",userId)
                .addParams("newType",type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                            "(ClassTypeAddDetailActivity.java:211)"+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                            "(ClassTypeAddDetailActivity.java:218)"+response);
                        if (response.contains("修改成功")){
                            finish();
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(ClassTypeAddDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
