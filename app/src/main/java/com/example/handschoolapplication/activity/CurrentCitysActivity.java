package com.example.handschoolapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.AddressBeanS;
import com.example.handschoolapplication.bean.HotCity;
import com.example.handschoolapplication.others.CommonAdapter;
import com.example.handschoolapplication.others.DividerItemDecoration;
import com.example.handschoolapplication.others.HeaderRecyclerAndFooterWrapperAdapter;
import com.example.handschoolapplication.others.MeiTuanBean;
import com.example.handschoolapplication.others.MeituanAdapter;
import com.example.handschoolapplication.others.MeituanHeaderBean;
import com.example.handschoolapplication.others.MeituanTopHeaderBean;
import com.example.handschoolapplication.others.OnItemClickListener;
import com.example.handschoolapplication.others.ViewHolder;
import com.example.handschoolapplication.utils.InternetS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


/**
 * 介绍： 高仿美团选择城市页面
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/7.
 */
public class CurrentCitysActivity extends BaseActivity {
    private static final String TAG = "zxt";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Context mContext;
    private RecyclerView mRv;
    private MeituanAdapter mAdapter;
    private HeaderRecyclerAndFooterWrapperAdapter mHeaderAdapter;
    private LinearLayoutManager mManager;

    //设置给InexBar、ItemDecoration的完整数据集
    private List<BaseIndexPinyinBean> mSourceDatas;
    //头部数据源
    private List<MeituanHeaderBean> mHeaderDatas;
    //主体部分数据源（城市数据）
    private List<MeiTuanBean> mBodyDatas;

    private SuspensionDecoration mDecoration;

    /**
     * 右侧边栏导航区域
     */
    private IndexBar mIndexBar;

    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;
    private String city;
    private ArrayList<HotCity> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        city = getIntent().getStringExtra("city");
        tvTitle.setText("当前城市" + "（" + city + "）");
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));

        mSourceDatas = new ArrayList<>();
        mHeaderDatas = new ArrayList<>();
        List<String> locationCity = new ArrayList<>();
        locationCity.add("定位中");
        mHeaderDatas.add(new MeituanHeaderBean(locationCity, "定位城市", " "));
        List<String> recentCitys = new ArrayList<>();
        mHeaderDatas.add(new MeituanHeaderBean(recentCitys, "常驻城市", " "));
        List<String> hotCitys = new ArrayList<>();
        mHeaderDatas.add(new MeituanHeaderBean(hotCitys, "热门城市", " "));
        mSourceDatas.addAll(mHeaderDatas);

        mAdapter = new MeituanAdapter(this, R.layout.meituan_item_select_city, mBodyDatas);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                String city = mBodyDatas.get(position).getCity();
//                Toast.makeText(mContext, city, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("cityName", city);
                setResult(11, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
        mHeaderAdapter = new HeaderRecyclerAndFooterWrapperAdapter(mAdapter) {
            @Override
            protected void onBindHeaderHolder(ViewHolder holder, int headerPos, int layoutId, Object o) {
                switch (layoutId) {
                    case R.layout.meituan_item_header:
                        final MeituanHeaderBean meituanHeaderBean = (MeituanHeaderBean) o;
                        //网格
                        RecyclerView recyclerView = holder.getView(R.id.rvCity);
                        recyclerView.setAdapter(
                                new CommonAdapter<String>(mContext, R.layout.meituan_item_header_item, meituanHeaderBean.getCityList()) {
                                    @Override
                                    public void convert(ViewHolder holder, final String cityName) {
                                        holder.setText(R.id.tvName, cityName);
                                        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
//                                                Toast.makeText(mContext, "cityName:" + cityName, Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent();
                                                intent.putExtra("cityName", cityName);
                                                setResult(11, intent);
                                                finish();
                                            }
                                        });
                                    }
                                });
                        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                        break;
                    case R.layout.meituan_item_header_top:
                        MeituanTopHeaderBean meituanTopHeaderBean = (MeituanTopHeaderBean) o;
                        holder.setText(R.id.tvCurrent, meituanTopHeaderBean.getTxt());
                        break;
                    default:
                        break;
                }
            }
        };
        mHeaderAdapter.setHeaderView(0, R.layout.meituan_item_header, mHeaderDatas.get(0));
        mHeaderAdapter.setHeaderView(1, R.layout.meituan_item_header, mHeaderDatas.get(1));
        mHeaderAdapter.setHeaderView(2, R.layout.meituan_item_header, mHeaderDatas.get(2));


        mRv.setAdapter(mHeaderAdapter);
        mRv.addItemDecoration(mDecoration = new SuspensionDecoration(this, mSourceDatas)
                .setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics()))
                .setColorTitleBg(0xffffffff)
                .setTitleFontSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()))
                .setColorTitleFont(mContext.getResources().getColor(R.color.t666666))
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount() - mHeaderDatas.size()));
        mRv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));

        //使用indexBar
        mTvSideBarHint = (TextView) findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = (IndexBar) findViewById(R.id.indexBar);//IndexBar

        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount() - mHeaderDatas.size());

        initStringData();
//        initDatas(getResources().getStringArray(R.array.provinces));//数据源
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_meituan;
    }

    private void initStringData() {
        List<String> cities = new ArrayList<>();
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
        Gson gson = new Gson();
        List<AddressBeanS> addressBean = gson.fromJson(JsonData, new TypeToken<ArrayList<AddressBeanS>>() {
        }.getType());
        for (int i = 0; i < addressBean.size(); i++) {
            List<AddressBeanS.CityBean> city = addressBean.get(i).getCity();
            for (int j = 0; j < city.size(); j++) {
                String name = city.get(j).getName();
                cities.add(name);
            }
        }
        String[] citys = (String[]) (cities.toArray(new String[cities.size()]));
        initDatas(citys);
    }

    @OnClick({R.id.rl_back, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
        }
    }

    public class GetJsonDataUtil {


        public String getJson(Context context, String fileName) {

            StringBuilder stringBuilder = new StringBuilder();
            try {
                AssetManager assetManager = context.getAssets();
                BufferedReader bf = new BufferedReader(new InputStreamReader(
                        assetManager.open(fileName)));
                String line;
                while ((line = bf.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

    }

    /**
     * 组织数据源
     *
     * @param data
     * @return
     */
    private void initDatas(final String[] data) {
        //延迟两秒 模拟加载数据中....
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBodyDatas = new ArrayList<>();
                for (int i = 0; i < data.length; i++) {
                    MeiTuanBean cityBean = new MeiTuanBean();
                    cityBean.setCity(data[i]);//设置城市名称
                    mBodyDatas.add(cityBean);
                }
                //先排序
                mIndexBar.getDataHelper().sortSourceDatas(mBodyDatas);
                mAdapter.setDatas(mBodyDatas);
                mHeaderAdapter.notifyDataSetChanged();
                mSourceDatas.addAll(mBodyDatas);

                mIndexBar.setmSourceDatas(mSourceDatas)//设置数据
                        .invalidate();
                mDecoration.setmDatas(mSourceDatas);
            }
        }, 1000);

        OkHttpUtils.post()
                .url(InternetS.HOT_ADDRESS_INFOR)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(CurrentCityActivity.java:52)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(CurrentCityActivity.java:59)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");

                            MeituanHeaderBean header1 = mHeaderDatas.get(0);
                            header1.getCityList().clear();
                            header1.getCityList().add(city);

                            MeituanHeaderBean header2 = mHeaderDatas.get(1);
                            List<String> recentCitys = new ArrayList<>();
                            recentCitys.add("天津");
                            header2.setCityList(recentCitys);


                            mList = new ArrayList<HotCity>();
                            mList.addAll((Collection<? extends HotCity>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<HotCity>>() {
                            }.getType()));
                            MeituanHeaderBean header3 = mHeaderDatas.get(2);
                            List<String> hotCitys = new ArrayList<>();
                            for (int i = 0; i < mList.size(); i++) {
                                String address_name = mList.get(i).getAddress_name();
                                hotCitys.add(address_name);
                            }
                            header3.setCityList(hotCitys);
                            mHeaderAdapter.notifyItemRangeChanged(0, 2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 更新数据源
     *
     * @param view
     */
    public void updateDatas(View view) {
        for (int i = 0; i < 5; i++) {
            mBodyDatas.add(new MeiTuanBean("东京"));
            mBodyDatas.add(new MeiTuanBean("大阪"));
        }
        //先排序
        mIndexBar.getDataHelper().sortSourceDatas(mBodyDatas);
        mSourceDatas.clear();
        mSourceDatas.addAll(mHeaderDatas);
        mSourceDatas.addAll(mBodyDatas);

        mHeaderAdapter.notifyDataSetChanged();
        mIndexBar.invalidate();
    }
}
