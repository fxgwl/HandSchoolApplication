package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.HorizontalListViewAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.CourseSortBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.RankListUtils;
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

public class ArtActivity extends BaseActivity implements CommonPopupWindow.ViewInterface, View.OnClickListener {

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

    private CommonPopupWindow sortPopupwindow, synthesisRankPopupwindow;

    private ListView listView;
    private List<CourseSortBean> mlist;
    private MyAdapter myAdapter;
    private ArrayList types;//第一级下拉菜单的数据源
//    public LocationClient mLocationClient = null;
//    public MyLocationListener myListener = new MyLocationListener();
    private String city;
    private double[] locations;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    tvLocation.setText(city);
                    break;
                case 1:
                    locations = (double[]) msg.obj;
                    myAdapter.notifyDataSetChanged();
                    break;
            }

        }
    };
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = (ListView) findViewById(R.id.lv_course);
        mlist = new ArrayList<>();
        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        ivMenu.setVisibility(View.VISIBLE);

        types = (ArrayList) getIntent().getSerializableExtra("types");
        flag =  getIntent().getStringExtra("flag");
        city =  getIntent().getStringExtra("city");
        double latitude =  getIntent().getDoubleExtra("latitude",0);
        double longitude =  getIntent().getDoubleExtra("longitude",0);
        locations = new double[]{latitude,longitude};
        switch (flag){
            case "art":
                tvTitle.setText("文体艺术");
                break;
            case "help":
                tvTitle.setText("学习辅导");
                break;
            case "activity":
                tvTitle.setText("活动拓展");
                break;
        }
//        startLocate();
        //获取文体艺术的小类
        initData(flag);


    }

    /**
     * 定位
     */
//    private void startLocate() {
//        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
//        mLocationClient.registerLocationListener(myListener);    //注册监听函数
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving
//        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
////        int span = 1000;
//        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
//        option.setOpenGps(true);//可选，默认false,设置是否使用gps
//        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
//        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
//        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
//        mLocationClient.setLocOption(option);
//        //开启定位
//        mLocationClient.start();
//    }

    private void initData(String sort) {

        mlist.clear();
        OkHttpUtils.post()
                .url(Internet.COURSELIST)
                .addParams("course_type", sort)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:78)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:84)" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            mlist.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                            }.getType()));
                            if (locations != null)
                                RankListUtils.rankList(mlist, new LatLng(locations[0], locations[1]));
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
                show(view);
                break;
            case R.id.et_search:
            case R.id.iv_search://搜索
                startActivity(new Intent(ArtActivity.this, SearchActivity.class));
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
                showSynthesisRankPopupwindow(view);
                iv_bg.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popupwindow_sort:
                HorizontalListView hlvSort = (HorizontalListView) view.findViewById(R.id.hlv_sort);
                final HorizontalListViewAdapter horizontalListViewAdapter = new HorizontalListViewAdapter(this, types);
                hlvSort.setAdapter(horizontalListViewAdapter);
                hlvSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(ArtActivity.this, types.get(position).toString(), Toast.LENGTH_SHORT).show();
                        horizontalListViewAdapter.setSelectedPosition(position);
                        horizontalListViewAdapter.notifyDataSetChanged();
                        sortPopupwindow.dismiss();
                        iv_bg.setVisibility(View.GONE);
                        initData(types.get(position).toString());
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
                            mlist.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                            }.getType()));
//                            myAdapter.setLocations(locations);
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
                                "(ArtActivity.java:233)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:239)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            mlist.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                            }.getType()));
//                            myAdapter.setLocations(locations);
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
                            mlist.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                            }.getType()));
//                            myAdapter.setLocations(locations);
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
                            mlist.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                            }.getType()));
//                            myAdapter.setLocations(locations);
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
                            mlist.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                            }.getType()));

//                            myAdapter.setLocations(locations);
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
                            ArrayList<CourseSortBean> list = new ArrayList<CourseSortBean>();
                            list.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                            }.getType()));
                            if (list.size() > 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    mlist.add(list.get((list.size() - 1) - i));
                                }
                            }
//                            myAdapter.setLocations(locations);
                            myAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getDistenceDesc() {//由远到近
        List<CourseSortBean> courseSortBeen = RankListUtils.rankList(mlist, new LatLng(locations[0], locations[1]));
        for (int i = 0; i < courseSortBeen.size(); i++) {
            double distance = courseSortBeen.get(i).getDistance();
        }
        mlist.clear();
        mlist.addAll(courseSortBeen);
        myAdapter.notifyDataSetChanged();
    }

    private void getDistenceAsc() {//由近到远
        List<CourseSortBean> courseSortBeen = RankListUtils.rankListsss(mlist, new LatLng(locations[0], locations[1]));
        mlist.clear();
        mlist.addAll(courseSortBeen);
        myAdapter.notifyDataSetChanged();
    }


    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.e("描述：", sb.toString());

            city = location.getCity();
            Message message = new Message();
            message.obj = city;
            message.what = 0;
            mHandler.sendEmptyMessage(0);
            double[] locations = new double[2];
            locations[0] = location.getLatitude();//纬度
            locations[1] = location.getLongitude();//经度
            Log.e("aaa",
                    "(MyLocationListener.java:524)" + "获取到的纬度是====" + location.getLatitude() + "     获取到的纬度是====" + location.getLongitude());
            Message message1 = new Message();
            message1.obj = locations;
            message1.what = 1;
            mHandler.sendMessage(message1);

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

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
                getDistenceDesc();
                break;
            case R.id.tv_far_rank:
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                getDistenceAsc();
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
            if (mlist != null) {
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
                convertView = View.inflate(ArtActivity.this, R.layout.item_find_course_lv, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            CourseSortBean courseSortBean = mlist.get(position);
            Glide.with(ArtActivity.this).load(Internet.BASE_URL + courseSortBean.getCourse_photo()).centerCrop().into(holder.ivCourse);
            holder.tvCourse.setText(courseSortBean.getCourse_name());
            holder.tvPrice.setText("¥" + courseSortBean.getPreferential_price());//价格是放的优惠价
            holder.popularity.setText("（" + courseSortBean.getPopularity_num() + "人已报名）");

            double school_wei = Double.parseDouble(courseSortBean.getSchool_wei());
            double school_jing = Double.parseDouble(courseSortBean.getSchool_jing());

            if (locations != null) {
                double distance = DistanceUtil.getDistance(new LatLng(locations[0], locations[1]), new LatLng(school_wei, school_jing));
                Log.e("aaa",
                        "(ArtActivity.java:635)" + "纬度是=====" + locations[0] + "     经度为======" + locations[1]);
                Log.e("aaa",
                        "(ArtActivity.java:637)" + "学堂返回的纬度是=====" + school_wei + "     学堂返回的经度为======" + school_jing);
                holder.tvDistance.setText((int) distance + "m");
            } else {
                holder.tvDistance.setText("定位失败");
            }
            return convertView;
        }


        class ViewHolder {
            @BindView(R.id.iv_course)
            ImageView ivCourse;
            @BindView(R.id.tv_course)
            TextView tvCourse;
            @BindView(R.id.tv_distance)
            TextView tvDistance;
            @BindView(R.id.tv_price)
            TextView tvPrice;
            @BindView(R.id.popularity)
            TextView popularity;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
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
