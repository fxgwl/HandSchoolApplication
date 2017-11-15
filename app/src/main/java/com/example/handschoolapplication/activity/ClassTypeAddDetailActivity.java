package com.example.handschoolapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.HorizontalListViewAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.TimeHourBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.view.HorizontalListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ClassTypeAddDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ctad_hl_art)
    HorizontalListView ctadHlArt;
    @BindView(R.id.gv_art_detail)
    GridView gvArtDetail;
    @BindView(R.id.tv_type_one)
    TextView tvTypeOne;
    private List<String> typeTwo = new ArrayList<>();
    private List<TimeHourBean> typeThree = new ArrayList<>();
    private String typeOne;
    private String typeTwoName;
    private HorizontalListViewAdapter hlvAdapter;
    private MyThirdAdapter myThirdAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        typeOne = getIntent().getStringExtra("type");
        initView();
        getSecondList(typeOne);
        getThirdList(typeTwoName);
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
                                "(ArtActivity.java:203)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:209)" + response);
                        try {
                            if (response.contains("没有信息")){}else {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject jsonObject1 = data.getJSONObject(i);
                                    String type_two_name = jsonObject1.getString("type_two_name");
                                    if (i==0)typeTwoName = type_two_name;
                                    typeTwo.add(type_two_name);
                                }
                                hlvAdapter.setList(typeTwo);
                                hlvAdapter.notifyDataSetChanged();
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
                                "(ArtActivity.java:140)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:146)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(ClassTypeAddDetailActivity.this, "没有信息", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject jsonObject1 = data.getJSONObject(i);
                                    String type_three_name = jsonObject1.getString("type_three_name");
                                    TimeHourBean timeHourBean = new TimeHourBean(false, type_three_name);
                                    typeThree.add(timeHourBean);
                                }
                                myThirdAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
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
        hlvAdapter = new HorizontalListViewAdapter(this, typeTwo);
        myThirdAdapter = new MyThirdAdapter(this,typeThree);
        ctadHlArt.setAdapter(hlvAdapter);
        ctadHlArt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hlvAdapter.setSelectedPosition(position);
                hlvAdapter.notifyDataSetChanged();
            }
        });
        gvArtDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("aaa",
                    "(ClassTypeAddDetailActivity.java:69)"+position);
//                hlvAdapter2.setSelectedPosition(position);
//                hlvAdapter2.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.rl_back, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }


    class MyThirdAdapter extends BaseAdapter {

        private List<TimeHourBean> mCourseList;
        private int size = 0;
        private ArtActivity.ChooseItem chooseItem;

        public MyThirdAdapter(Context context, List<TimeHourBean> mCourseList) {
            this.mCourseList = mCourseList;
        }

        public void setChooseItem(ArtActivity.ChooseItem chooseItem) {
            this.chooseItem = chooseItem;
        }

        @Override
        public int getCount() {

            if (mCourseList != null) {
                size = mCourseList.size();
            }
            return mCourseList.size();
        }

        @Override
        public Object getItem(int position) {
            return mCourseList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, final ViewGroup parent) {

            ViewHolder holder = null;

            if (view == null) {
                view = View.inflate(ClassTypeAddDetailActivity.this, R.layout.sort_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.tvTime.setChecked(mCourseList.get(position).isChecked());
            holder.tvTime.setText(mCourseList.get(position).getTime());
            Log.e("aaa",
                    "(TimeAdapter.java:71)" + mCourseList.toString());
            holder.tvTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub
                    if (isChecked) {
                        Log.e("aaa",
                                "(TimeAdapter.java:79)" + parent.getTag());
                        chooseItem.cbCheck(position, Integer.parseInt(parent.getTag() + ""), true);
                        mCourseList.get(position).setChecked(true);
                        notifyDataSetChanged();
                    } else {
                        chooseItem.cbCheck(position, Integer.parseInt(parent.getTag() + ""), false);
                        mCourseList.get(position).setChecked(false);
                        notifyDataSetChanged();
                    }
                }

            });
            return view;
        }

        class ViewHolder {
            @BindView(R.id.cb_time)
            CheckBox tvTime;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

    }
}
