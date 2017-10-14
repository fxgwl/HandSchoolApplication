package com.example.handschoolapplication.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.ActivityActivity;
import com.example.handschoolapplication.activity.ArtActivity;
import com.example.handschoolapplication.activity.ChildEduActivity;
import com.example.handschoolapplication.activity.EducationActivity;
import com.example.handschoolapplication.activity.HomeEduActivity;
import com.example.handschoolapplication.activity.LearnHelpActivity;
import com.example.handschoolapplication.activity.SearchActivity;
import com.example.handschoolapplication.activity.TrusteeshipActivity;
import com.example.handschoolapplication.adapter.HPClassAdapter;
import com.example.handschoolapplication.adapter.HPCourseAdapter;
import com.example.handschoolapplication.adapter.HorizontalActivityListViewAdapter;
import com.example.handschoolapplication.adapter.HorizontalLearnListViewAdapter;
import com.example.handschoolapplication.adapter.HorizontalListViewAdapter;
import com.example.handschoolapplication.bean.ClassBean;
import com.example.handschoolapplication.bean.CourseBean;
import com.example.handschoolapplication.bean.HomeAdBean;
import com.example.handschoolapplication.bean.HomeClassTypeBean;
import com.example.handschoolapplication.bean.TeachNewsBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.MyUtiles;
import com.example.handschoolapplication.view.HorizontalActivityListView;
import com.example.handschoolapplication.view.HorizontalLearnListView;
import com.example.handschoolapplication.view.HorizontalListView;
import com.example.handschoolapplication.view.Marquee;
import com.example.handschoolapplication.view.MarqueeView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {


    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.et_search)
    TextView etSearch;
    @BindView(R.id.iv_style_art)
    ImageView ivStyleArt;
    @BindView(R.id.iv_learn_help)
    ImageView ivLearnHelp;
    @BindView(R.id.iv_activity)
    ImageView ivActivity;
    @BindView(R.id.iv_child_edu)
    ImageView ivChildEdu;
    @BindView(R.id.iv_trusteeship)
    ImageView ivTrusteeship;
    @BindView(R.id.iv_family_edu)
    ImageView ivFamilyEdu;
    @BindView(R.id.ll_learn)
    LinearLayout llLearn;
    //    @BindView(R.id.tv_tip)
//    TextView tvTip;
//    @BindView(R.id.tv_text)
//    TextView tvText;
//    @BindView(R.id.tv_tips)
//    TextView tvTips;
//    @BindView(R.id.tv_tetxs)
//    TextView tvTetxs;
    Unbinder unbinder;
    @BindView(R.id.hl_art)
    HorizontalListView hlArt;

    @BindView(R.id.hl_learn)
    HorizontalLearnListView hlLearn;
    @BindView(R.id.lv_learn_name)
    ListView lvLearnName;
    @BindView(R.id.hl_activity)
    HorizontalActivityListView hlActivity;
    @BindView(R.id.lv_activity_name)
    ListView lvActivityName;
    @BindView(R.id.lv_child_name)
    ListView lvChildName;
    @BindView(R.id.lv_trusteeship_name)
    ListView lvTrusteeshipName;
    @BindView(R.id.lv_homelearn_name)
    ListView lvHomelearnName;
    @BindView(R.id.ll_sign_in)
    LinearLayout llSignIn;
    @BindView(R.id.ll_sign_ins)
    LinearLayout llSignIns;
    @BindView(R.id.tv_hometype1)
    TextView tvHometype1;
    @BindView(R.id.tv_hometype2)
    TextView tvHometype2;
    @BindView(R.id.tv_hometype3)
    TextView tvHometype3;
    @BindView(R.id.tv_hometype4)
    TextView tvHometype4;
    @BindView(R.id.tv_hometype5)
    TextView tvHometype5;
    @BindView(R.id.tv_hometype6)
    TextView tvHometype6;
    @BindView(R.id.tv_hometypelist1)
    TextView tvHometypelist1;
    @BindView(R.id.tv_hometypelist2)
    TextView tvHometypelist2;
    @BindView(R.id.tv_hometypelist3)
    TextView tvHometypelist3;
    @BindView(R.id.tv_hometypelist4)
    TextView tvHometypelist4;
    @BindView(R.id.tv_hometypelist5)
    TextView tvHometypelist5;
    @BindView(R.id.tv_hometypelist6)
    TextView tvHometypelist6;
    private View view;
    HorizontalListViewAdapter mAdapter;
    HorizontalLearnListViewAdapter mLearnAdapter;
    HorizontalActivityListViewAdapter mActivityAdapter;
    private ListView LvCourseName;
    private HPCourseAdapter courseAdapter1;
    private HPCourseAdapter courseAdapter2;
    private HPCourseAdapter courseAdapter3;
    private HPClassAdapter classAdapter1;
    private HPClassAdapter classAdapter2;
    private HPClassAdapter classAdapter3;
    private List<CourseBean.DataBean> courseBeanList1 = new ArrayList<>();
    private List<CourseBean.DataBean> courseBeanList2 = new ArrayList<>();
    private List<CourseBean.DataBean> courseBeanList3 = new ArrayList<>();
    private List<ClassBean.DataBean> classBeanList1 = new ArrayList<>();
    private List<ClassBean.DataBean> classBeanList2 = new ArrayList<>();
    private List<ClassBean.DataBean> classBeanList3 = new ArrayList<>();
    private MarqueeView marqueeView;
    List<Marquee> marquees = new ArrayList<>();
    private TextView[] type;
    private TextView[] typelist;
    private ArrayList<ArrayList<String>> typetwolist;

    public HomeFragment() {
        // Required empty public constructor
    }

    private ConvenientBanner convenientBanner;
    private List<String> listImg = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initOrgan("托管", classBeanList2, classAdapter2, lvTrusteeshipName);
                    break;
                case 1:
                    initOrgan("家教", classBeanList3, classAdapter3, lvHomelearnName);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, null);
        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        marqueeView = (MarqueeView) view.findViewById(R.id.marqueeView);
        unbinder = ButterKnife.bind(this, view);
        type = new TextView[]{tvHometype1, tvHometype2, tvHometype3, tvHometype4, tvHometype5, tvHometype6};
        typelist = new TextView[]{tvHometypelist1, tvHometypelist2, tvHometypelist3, tvHometypelist4, tvHometypelist5, tvHometypelist6};
        LvCourseName = (ListView) view.findViewById(R.id.lv_course_name);
        initLvData();
        //教育资讯跑马灯
        initTeachNews();
        //初始化首页广告位
        initHomeAd();
        //初始化课程类型
        initClassType();
        //初始化
        initOrgan("早教", classBeanList1, classAdapter1, lvChildName);
        return view;
    }

    private void initOrgan(final String type, final List<ClassBean.DataBean> classBeanList, final HPClassAdapter classAdapter, final ListView lv) {
        OkHttpUtils.post()
                .url(Internet.ORGANLIST)
                .addParams("mechanism_type", type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:217)" + type + "===" + response);
                        switch (type) {
                            case "早教":
                                mHandler.sendEmptyMessage(0);
                                break;
                            case "托管":
                                mHandler.sendEmptyMessage(1);
                                break;
                        }
                        classBeanList.clear();
                        Gson gson = new Gson();
                        if (TextUtils.isEmpty(response)) {
                            classAdapter.notifyDataSetChanged();
                        } else {
                            classBeanList.addAll(gson.fromJson(response, ClassBean.class).getData());
                            classAdapter.notifyDataSetChanged();
                            MyUtiles.setListViewHeightBasedOnChildren(lv, getActivity());
                        }

                    }
                });
    }

    private void initClassType() {
        OkHttpUtils.post()
                .url(Internet.CLASSTYPE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:180)" + response);
                        Gson gson = new Gson();
                        HomeClassTypeBean homeClassType = gson.fromJson(response, HomeClassTypeBean.class);
                        ArrayList<String> typeones = new ArrayList<String>();
                        typetwolist = new ArrayList<ArrayList<String>>();
                        for (int i = 0; i < homeClassType.getData().size(); i++) {
                            typeones.add(homeClassType.getData().get(i).getType_one_name());
                            ArrayList<String> typetwos = new ArrayList<String>();
                            for (int m = 0; m < homeClassType.getData().get(i).getTypeTwoInfo().size(); m++) {
                                typetwos.add(homeClassType.getData().get(i).getTypeTwoInfo().get(m).getType_two_name());
                            }
                            type[i].setText(typeones.get(i));
                            typelist[i].setText(typeones.get(i));
                            typetwolist.add(typetwos);
                        }
                        //初始化第二个列表
                        initHLArtData(typetwolist.get(0));
                        initHLLearnData(typetwolist.get(1));
                        initHLActivityData(typetwolist.get(2));
                    }
                });
    }

    private void initHomeAd() {
        OkHttpUtils.post()
                .url(Internet.HOMEAD)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:183)" + response);
                        Gson gson = new Gson();
                        ArrayList<HomeAdBean.DataBean> homeAdList =
                                (ArrayList<HomeAdBean.DataBean>) gson.fromJson(response, HomeAdBean.class).getData();
                        for (int i = 0; i < homeAdList.size(); i++) {
                            listImg.add(Internet.BASE_URL + homeAdList.get(i).getAdvertising_photo());
                        }
                        setConvenientBanner(listImg);
                    }
                });
    }

    private void initTeachNews() {
        OkHttpUtils.post()
                .url(Internet.TEACHNEWS)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:185)" + response);
                        Gson gson = new Gson();
                        ArrayList<TeachNewsBean.DataBean> teachNewsList =
                                (ArrayList<TeachNewsBean.DataBean>) gson.fromJson(response, TeachNewsBean.class).getData();
                        for (int i = 0; i < teachNewsList.size(); i++) {
                            if (i % 2 == 0 && i != teachNewsList.size()) {
                                Marquee marquee = new Marquee();
                                marquee.setFirstimgUrl(teachNewsList.get(i).getNews_type());
                                marquee.setFirsttitle(teachNewsList.get(i).getNews_title());
                                marquee.setImgUrl(teachNewsList.get(i + 1).getNews_type());
                                marquee.setTitle(teachNewsList.get(i + 1).getNews_title());
                                marquees.add(marquee);
                            }
                        }
                        marqueeView.startWithList(marquees);
                        marqueeView.setOnClick(new MarqueeView.OnClick() {
                            @Override
                            public void onClick() {
                                startActivity(new Intent(getActivity(), EducationActivity.class));
                            }
                        });
                    }
                });
    }

    private void initLvData() {
        courseAdapter1 = new HPCourseAdapter(courseBeanList1, getActivity());
        courseAdapter2 = new HPCourseAdapter(courseBeanList2, getActivity());
        courseAdapter3 = new HPCourseAdapter(courseBeanList3, getActivity());
        classAdapter1 = new HPClassAdapter(getActivity(), classBeanList1);
        classAdapter2 = new HPClassAdapter(getActivity(), classBeanList2);
        classAdapter3 = new HPClassAdapter(getActivity(), classBeanList3);

        LvCourseName.setAdapter(courseAdapter1);
        MyUtiles.setListViewHeightBasedOnChildren(LvCourseName);

        lvLearnName.setAdapter(courseAdapter2);
        MyUtiles.setListViewHeightBasedOnChildren(lvLearnName, getActivity());

        lvActivityName.setAdapter(courseAdapter3);
        MyUtiles.setListViewHeightBasedOnChildren(lvActivityName, getActivity());

        lvChildName.setAdapter(classAdapter1);
        MyUtiles.setListViewHeightBasedOnChildren(lvChildName, getActivity());

        lvTrusteeshipName.setAdapter(classAdapter2);
        MyUtiles.setListViewHeightBasedOnChildren(lvTrusteeshipName, getActivity());

        lvHomelearnName.setAdapter(classAdapter3);
        MyUtiles.setListViewHeightBasedOnChildren(lvHomelearnName, getActivity());

    }


    private void initHLLearnData(final ArrayList<String> strings) {

        mLearnAdapter = new HorizontalLearnListViewAdapter(getActivity(), strings);
        hlLearn.setAdapter(mLearnAdapter);
        hlLearn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "id=" + strings.get(position), Toast.LENGTH_SHORT).show();
                mLearnAdapter.setSelectedPosition(position);
                mLearnAdapter.notifyDataSetChanged();
                chooseClassTwoType(strings.get(position), courseBeanList2, courseAdapter2, lvLearnName);
            }
        });
    }

    private void initHLActivityData(final ArrayList<String> strings) {
        mActivityAdapter = new HorizontalActivityListViewAdapter(getActivity(), strings);
        hlActivity.setAdapter(mActivityAdapter);
        initCourseData();
        hlActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "id=" + strings.get(position), Toast.LENGTH_SHORT).show();
                mActivityAdapter.setSelectedPosition(position);
                mActivityAdapter.notifyDataSetChanged();
                chooseClassTwoType(strings.get(position), courseBeanList3, courseAdapter3, lvActivityName);
            }
        });
    }

    private void initCourseData() {
        if (typetwolist.get(2) != null) {
            OkHttpUtils.post()
                    .url(Internet.COURSELIST)
                    .addParams("course_type", typetwolist.get(2).get(0))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e("aaa",
                                    "(HomeFragment.java:418)" + e.toString());

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.e("aaa",
                                    "(HomeFragment.java:425)" + response);
                            courseBeanList3.clear();
                            Gson gson = new Gson();
                            if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                                courseAdapter3.notifyDataSetChanged();
                                MyUtiles.setListViewHeightBasedOnChildren(lvActivityName);
                            } else {
                                courseBeanList3.addAll(gson.fromJson(response, CourseBean.class).getData());
                                courseAdapter3.notifyDataSetChanged();
                                MyUtiles.setListViewHeightBasedOnChildren(lvActivityName);
                            }

                            if (typetwolist.get(1) != null) {
                                OkHttpUtils.post()
                                        .url(Internet.COURSELIST)
                                        .addParams("course_type", typetwolist.get(1).get(0))
                                        .build()
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onError(Call call, Exception e, int id) {
                                                Log.e("aaa",
                                                        "(HomeFragment.java:418)" + e.toString());

                                            }

                                            @Override
                                            public void onResponse(String response, int id) {
                                                Log.e("aaa",
                                                        "(HomeFragment.java:425)" + response);

                                                courseBeanList2.clear();
                                                Gson gson = new Gson();
                                                if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                                                    courseAdapter2.notifyDataSetChanged();
                                                    MyUtiles.setListViewHeightBasedOnChildren(lvLearnName);
                                                } else {
                                                    courseBeanList2.addAll(gson.fromJson(response, CourseBean.class).getData());
                                                    courseAdapter2.notifyDataSetChanged();
                                                    MyUtiles.setListViewHeightBasedOnChildren(lvLearnName);
                                                }
                                                if (typetwolist.get(0) != null) {
                                                    OkHttpUtils.post()
                                                            .url(Internet.COURSELIST)
                                                            .addParams("course_type", typetwolist.get(0).get(0))
                                                            .build()
                                                            .execute(new StringCallback() {
                                                                @Override
                                                                public void onError(Call call, Exception e, int id) {
                                                                    Log.e("aaa",
                                                                            "(HomeFragment.java:418)" + e.toString());

                                                                }

                                                                @Override
                                                                public void onResponse(String response, int id) {
                                                                    Log.e("aaa",
                                                                            "(HomeFragment.java:425)" + response);
                                                                    courseBeanList1.clear();
                                                                    Gson gson = new Gson();
                                                                    if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                                                                        courseAdapter1.notifyDataSetChanged();
                                                                        MyUtiles.setListViewHeightBasedOnChildren(LvCourseName);
                                                                    } else {
                                                                        courseBeanList1.addAll(gson.fromJson(response, CourseBean.class).getData());
                                                                        courseAdapter1.notifyDataSetChanged();
                                                                        MyUtiles.setListViewHeightBasedOnChildren(LvCourseName);
                                                                    }
                                                                }
                                                            });
                                                }
                                            }
                                        });
                            }
                        }
                    });

        }
    }

    //初始化二小类
    private void initHLArtData(final ArrayList<String> strings) {
        mAdapter = new HorizontalListViewAdapter(getActivity(), strings);
        hlArt.setAdapter(mAdapter);
        hlArt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "id=" + strings.get(position), Toast.LENGTH_SHORT).show();
                mAdapter.setSelectedPosition(position);
                mAdapter.notifyDataSetChanged();
                chooseClassTwoType(strings.get(position), courseBeanList1, courseAdapter1, LvCourseName);
            }
        });

    }

    //选择而小类之后列表
    private void chooseClassTwoType(String s, final List<CourseBean.DataBean> list, final HPCourseAdapter sAdapter, final ListView lv) {
        OkHttpUtils.post()
                .url(Internet.COURSELIST)
                .addParams("course_type", s)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:418)" + e.toString());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:425)" + response);
                        list.clear();
                        Gson gson = new Gson();
                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                            sAdapter.notifyDataSetChanged();
                            MyUtiles.setListViewHeightBasedOnChildren(lv);
                        } else {
                            list.addAll(gson.fromJson(response, CourseBean.class).getData());
                            sAdapter.notifyDataSetChanged();
                            MyUtiles.setListViewHeightBasedOnChildren(lv);
                        }
                    }
                });

//
//        FutureTarget<File> future = Glide.with(getActivity())
//                .load("")
//                .downloadOnly(500, 500);
//
//        try {
//            File file = future.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        Glide.with(getActivity()).load("").diskCacheStrategy(DiskCacheStrategy.ALL).into(new ImageView(getActivity()));
    }

    private void setConvenientBanner(List<String> bannerList) {
        convenientBanner.setPages(
                new CBViewHolderCreator<NetWorkImageHolderView>() {
                    @Override
                    public NetWorkImageHolderView createHolder() {
                        return new NetWorkImageHolderView();
                    }
                }, bannerList)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.conven_point_grey, R.drawable.conven_point_blue})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .startTurning(2000);
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);  //  集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
        //   convenientBanner.setManualPageable(false);//设置不能手动影响
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_search, R.id.ll_sign_in, R.id.rl_style_art, R.id.rl_learn_help, R.id.rl_activity,
            R.id.rl_child_edu, R.id.rl_trusteeship, R.id.rl_family_edu,
            R.id.tv_more_art, R.id.tv_more_learn, R.id.tv_more_activity,
            R.id.tv_more_child, R.id.tv_more_trusteeship, R.id.tv_more_home
            , R.id.et_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_search://搜索
                break;
            case R.id.ll_sign_in://签到
                llSignIn.setVisibility(View.GONE);
                llSignIns.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_style_art://文艺
                startActivity(new Intent(getActivity(), ArtActivity.class));
                break;
            case R.id.rl_learn_help://学习辅导
                startActivity(new Intent(getActivity(), LearnHelpActivity.class));
                break;
            case R.id.rl_activity://活动
                startActivity(new Intent(getActivity(), ActivityActivity.class));
                break;
            case R.id.rl_child_edu://早教
                startActivity(new Intent(getActivity(), ChildEduActivity.class));
                break;
            case R.id.rl_trusteeship://托管
                startActivity(new Intent(getActivity(), TrusteeshipActivity.class));
                break;
            case R.id.rl_family_edu://家教
                startActivity(new Intent(getActivity(), HomeEduActivity.class));
                break;
            case R.id.tv_more_art:
                startActivity(new Intent(getActivity(), ArtActivity.class));
                break;
            case R.id.tv_more_learn:
                startActivity(new Intent(getActivity(), LearnHelpActivity.class));
                break;
            case R.id.tv_more_activity:
                startActivity(new Intent(getActivity(), ActivityActivity.class));
                break;
            case R.id.tv_more_child:
                startActivity(new Intent(getActivity(), ChildEduActivity.class));
                break;
            case R.id.tv_more_trusteeship:
                startActivity(new Intent(getActivity(), TrusteeshipActivity.class));
                break;
            case R.id.tv_more_home:
                startActivity(new Intent(getActivity(), HomeEduActivity.class));
                break;
            case R.id.et_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(getActivity(), "id=" + mData.get(position), Toast.LENGTH_SHORT).show();
        mAdapter.setSelectedPosition(position);
        mAdapter.notifyDataSetChanged();

        mActivityAdapter.setSelectedPosition(position);
    }


    class NetWorkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {
//            imageView.setImageResource(data);
            if (data != null) {
                Glide.with(getActivity()).load(data).into(imageView);
                // ImageLoader.getInstance().displayImage(data, imageView, options);
            } else {
                imageView.setImageResource(R.drawable.meinv);
            }
        }

    }

}
