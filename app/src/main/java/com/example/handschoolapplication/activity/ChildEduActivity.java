package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.HorizontalListViewAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ClassSortBean;
import com.example.handschoolapplication.bean.CourseSortBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.view.CommonPopupWindow;
import com.example.handschoolapplication.view.HorizontalListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ChildEduActivity extends BaseActivity implements CommonPopupWindow.ViewInterface, View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_sort)
    TextView tvSort;
    @BindView(R.id.iv_img_bg)
    ImageView iv_bg;

    private ListView listView;
    private List<ClassSortBean> mlist;
    private MyAdapter myAdapter;
    private ArrayList<String> citys = new ArrayList<>();
    private CommonPopupWindow sortPopupwindow,synthesisRankPopupwindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = (ListView) findViewById(R.id.lv_course);
        ivMenu.setVisibility(View.VISIBLE);
        mlist = new ArrayList<>();
        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        tvTitle.setText("早教");
        initData("早教");
        citys.add("和平区");
        citys.add("河北区");
        citys.add("河东区");
        citys.add("南开区");
        citys.add("红桥区");
        citys.add("东丽区");
        citys.add("西青区");
        citys.add("津南区");
        citys.add("北辰区");

        //、"河西区"、"河北区"、"河东区"、"南开区"、"红桥区"东丽区、西青区、津南区、北辰区。 武清、宝坻、蓟县、静海、宁河 塘沽、大港、汉沽
    }

    private void initData(String sort) {
        OkHttpUtils.post()
                .url(Internet.ORGANLIST)
                .addParams("mechanism_type", sort)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:72)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:78)" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            mlist.addAll((Collection<? extends ClassSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                            }.getType()));
                            myAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_art;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.et_search, R.id.iv_search, R.id.tv_sort, R.id.iv_sort, R.id.tv_defaultrank, R.id.tv_allrank, R.id.iv_allrank})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                break;
            case R.id.et_search:
            case R.id.iv_search://搜索
                startActivity(new Intent(ChildEduActivity.this, SearchActivity.class));
                break;
            case R.id.tv_sort:
            case R.id.iv_sort://类别
                showCourse(view);
                iv_bg.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_defaultrank://默认排序
                break;
            case R.id.tv_allrank://综合排序
            case R.id.iv_allrank:
                break;
        }
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popupwindow_sort:
                HorizontalListView hlvSort = (HorizontalListView) view.findViewById(R.id.hlv_sort);
                final HorizontalListViewAdapter horizontalListViewAdapter = new HorizontalListViewAdapter(this, citys);
                hlvSort.setAdapter(horizontalListViewAdapter);
                hlvSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(ChildEduActivity.this, citys.get(position).toString(), Toast.LENGTH_SHORT).show();
                        horizontalListViewAdapter.setSelectedPosition(position);
                        horizontalListViewAdapter.notifyDataSetChanged();
                        sortPopupwindow.dismiss();
                        iv_bg.setVisibility(View.GONE);
                        initData(citys.get(position).toString());
                    }
                });
                break;
            case R.layout.popupwindow_synthesis_rank:
                TextView tvGradeRAnk = (TextView) view.findViewById(R.id.tv_grade_rank);//等级排序
                TextView tvStarRAnk = (TextView) view.findViewById(R.id.tv_star_rank);//星级排序
                TextView tvPopularityRAnk = (TextView) view.findViewById(R.id.tv_popularity_rank);//人气排序
                TextView tvUpRank = (TextView) view.findViewById(R.id.tv_up_rank);//价格高到低
                TextView tvDownRank = (TextView) view.findViewById(R.id.tv_down_rank);//价格低到高
                TextView tvNearRank = (TextView) view.findViewById(R.id.tv_near_rank);//距离近到远
                TextView tvFarRank = (TextView) view.findViewById(R.id.tv_far_rank);//距离远到近
                TextView tvCourseRank = (TextView) view.findViewById(R.id.tv_course_rank);//课程排序
                TextView tvOrganizationRank = (TextView) view.findViewById(R.id.tv_organization_rank);//机构排序

                tvGradeRAnk.setOnClickListener(this);
                tvStarRAnk.setOnClickListener(this);
                tvPopularityRAnk.setOnClickListener(this);
                tvUpRank.setOnClickListener(this);
                tvDownRank.setOnClickListener(this);
                tvNearRank.setOnClickListener(this);
                tvFarRank.setOnClickListener(this);
                tvCourseRank.setOnClickListener(this);
                tvOrganizationRank.setOnClickListener(this);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_grade_rank:
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                getStarOrGradeRank();
                break;
            case R.id.tv_star_rank:
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                getStarOrGradeRank();
                break;
            case R.id.tv_popularity_rank:
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                getPopularityRank();
                break;
            case R.id.tv_up_rank:
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                getPriceUpRank();
                break;
            case R.id.tv_down_rank:
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                getPriceDownRank();
                break;
            case R.id.tv_near_rank:
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                break;
            case R.id.tv_far_rank:
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                break;
            case R.id.tv_course_rank:
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                getCourseRank();
                break;
            case R.id.tv_organization_rank:
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                getOrganizationRank();
                break;
        }
    }

    class MyAdapter extends BaseAdapter {

        int size = 0;

        @Override
        public int getCount() {
            if (mlist!=null){
                size = mlist.size();
            }
            return size;
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
                convertView = View.inflate(ChildEduActivity.this, R.layout.item_find_class_lv, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            return convertView;


        }

        class ViewHolder {
            @BindView(R.id.iv_course)
            ImageView ivCourse;
            @BindView(R.id.tv_course)
            TextView tvCourse;
            @BindView(R.id.iv_star_1)
            ImageView ivStar1;
            @BindView(R.id.iv_star_2)
            ImageView ivStar2;
            @BindView(R.id.iv_star_3)
            ImageView ivStar3;
            @BindView(R.id.iv_star_4)
            ImageView ivStar4;
            @BindView(R.id.iv_star_5)
            ImageView ivStar5;
            @BindView(R.id.tv_distance)
            TextView tvDistance;
            @BindView(R.id.ll_1)
            LinearLayout ll1;
            @BindView(R.id.popularity)
            TextView popularity;
            @BindView(R.id.rl_item)
            RelativeLayout rlItem;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    /**
     * 课程排名
     */
    private void getCourseRank() {
        mlist.clear();
        OkHttpUtils.post()
                .url(InternetS.COURSE_RANK)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:141)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:148)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
//                            mlist.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
//                            }.getType()));
                            myAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 机构排序
     */
    private void getOrganizationRank() {
        mlist.clear();
        OkHttpUtils.post()
                .url(InternetS.ORGANIZATION_RANK)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:233)"+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:239)"+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
//                            mlist.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
//                            }.getType()));
                            myAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

    /**
     * 等级推荐  星级排行
     */
    private void getStarOrGradeRank() {
        mlist.clear();
        OkHttpUtils.post()
                .url(InternetS.GRADE_RANK)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:232)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:238)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
//                            mlist.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
//                            }.getType()));
                            myAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 人气排行
     */

    private void getPopularityRank() {
        mlist.clear();
        OkHttpUtils.post()
                .url(InternetS.POPULIRATION_RANK)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:266)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:272)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
//                            mlist.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
//                            }.getType()));
                            myAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 价格排序  由高到低
     */

    private void getPriceUpRank() {
        mlist.clear();
        OkHttpUtils.post()
                .url(InternetS.PRICE_UP_RANK)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:298)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("aaa",
                                "(ArtActivity.java:305)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
//                            mlist.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
//                            }.getType()));
                            myAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    /**
     * 价格排序  由低到高
     */

    private void getPriceDownRank() {
        mlist.clear();
        OkHttpUtils.post()
                .url(InternetS.PRICE_UP_RANK)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:298)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("aaa",
                                "(ArtActivity.java:305)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
//                            ArrayList<CourseSortBean> list = new ArrayList<CourseSortBean>();
//                            list.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
//                            }.getType()));
//                            if (list.size()>0){
//                                for (int i = 0; i <list.size() ; i++) {
//                                    mlist.add(list.get((list.size()-1)-i));
//                                }
//                            }
                            myAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //向下弹出
    public void showCourse(View view) {
        if (sortPopupwindow != null && sortPopupwindow.isShowing()) return;
        sortPopupwindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popupwindow_sort)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimDown)
                .setViewOnclickListener(this)
                .setOutsideTouchable(false)
                .create();
        sortPopupwindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        sortPopupwindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        sortPopupwindow.showAsDropDown(view, 0, 0);
    }

    //向下弹出
    public void showSynthesisRankPopupwindow(View view) {
        if (synthesisRankPopupwindow != null && synthesisRankPopupwindow.isShowing()) return;
        synthesisRankPopupwindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popupwindow_synthesis_rank)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimDown)
                .setViewOnclickListener(this)
                .setOutsideTouchable(false)
                .create();
        synthesisRankPopupwindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        synthesisRankPopupwindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        synthesisRankPopupwindow.showAsDropDown(view, 0, 0);
    }
}
