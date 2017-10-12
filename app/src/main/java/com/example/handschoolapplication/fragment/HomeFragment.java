package com.example.handschoolapplication.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    private View view;
    HorizontalListViewAdapter mAdapter;
    HorizontalLearnListViewAdapter mLearnAdapter;
    HorizontalActivityListViewAdapter mActivityAdapter;
    List<String> mData;//文艺横向的Listview数据源
    List<String> mLearnData;//学辅横向的Listview数据源
    List<String> mActivityData;//活动拓展横向的Listview数据源
    private ListView LvCourseName;
    private HPCourseAdapter courseAdapter;
    private HPClassAdapter classAdapter;
    private List<CourseBean> courseBeanList;
    private List<ClassBean> classBeanList;
    private MarqueeView marqueeView;
    List<Marquee> marquees = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    private ConvenientBanner convenientBanner;
    private String s1 = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1525187520,1111175605&fm=23&gp=0.jpg";
    private String s2 = "http://img0.imgtn.bdimg.com/it/u=1660138183,1040754863&fm=23&gp=0.jpg";
    private String s3 = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1525187520,1111175605&fm=23&gp=0.jpg";
    private String s4 = "http://img0.imgtn.bdimg.com/it/u=1660138183,1040754863&fm=23&gp=0.jpg";
    private List<String> listImg = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, null);
        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        marqueeView = (MarqueeView) view.findViewById(R.id.marqueeView);
        unbinder = ButterKnife.bind(this, view);
        LvCourseName = (ListView) view.findViewById(R.id.lv_course_name);
        initHLArtData();
        initHLLearnData();
        initHLActivityData();
        initLvData();
        //教育资讯跑马灯
        initTeachNews();
        //初始化首页广告位
        initHomeAd();

//        tvText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                startActivity(new Intent(getActivity(), EducationActivity.class));
//            }
//        });
        return view;
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
        courseBeanList = new ArrayList<>();
        classBeanList = new ArrayList<>();
        classBeanList.add(new ClassBean());
        classBeanList.add(new ClassBean());
        classBeanList.add(new ClassBean());

//        courseAdapter = new HPCourseAdapter(courseBeanList, getActivity());
//        LvCourseName.setAdapter(courseAdapter);
//        MyUtiles.setListViewHeightBasedOnChildren(LvCourseName, getActivity());
        courseAdapter = new HPCourseAdapter(courseBeanList, getActivity());
        classAdapter = new HPClassAdapter(getActivity(), classBeanList);

        LvCourseName.setAdapter(courseAdapter);
        MyUtiles.setListViewHeightBasedOnChildren(LvCourseName);

        lvLearnName.setAdapter(courseAdapter);
        MyUtiles.setListViewHeightBasedOnChildren(lvLearnName, getActivity());

        lvActivityName.setAdapter(courseAdapter);
        MyUtiles.setListViewHeightBasedOnChildren(lvActivityName, getActivity());

        lvChildName.setAdapter(classAdapter);
        MyUtiles.setListViewHeightBasedOnChildren(lvChildName, getActivity());

        lvTrusteeshipName.setAdapter(classAdapter);
        MyUtiles.setListViewHeightBasedOnChildren(lvTrusteeshipName, getActivity());

        lvHomelearnName.setAdapter(classAdapter);
        MyUtiles.setListViewHeightBasedOnChildren(lvHomelearnName, getActivity());

    }


    private void initHLLearnData() {
        mLearnData = new ArrayList<>();
        mLearnData.add("中小学教育");
        mLearnData.add("中小学教育");
        mLearnData.add("中小学教育");
        mLearnData.add("中小学教育");
        mLearnData.add("中小学教育");

        mLearnAdapter = new HorizontalLearnListViewAdapter(getActivity(), mLearnData);
        hlLearn.setAdapter(mLearnAdapter);
        hlLearn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "id=" + mLearnData.get(position), Toast.LENGTH_SHORT).show();
                mLearnAdapter.setSelectedPosition(position);
                mLearnAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initHLActivityData() {
        mActivityData = new ArrayList<>();
        mActivityData.add("夏令营");
        mActivityData.add("冬令营");
        mActivityData.add("素质训练");
        mActivityData.add("父母团");
        mActivityData.add("父母团");
        mActivityData.add("父母团");

        mActivityAdapter = new HorizontalActivityListViewAdapter(getActivity(), mActivityData);
        hlActivity.setAdapter(mActivityAdapter);
        hlActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "id=" + mActivityData.get(position), Toast.LENGTH_SHORT).show();
                mActivityAdapter.setSelectedPosition(position);
                mActivityAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initHLArtData() {
        mData = new ArrayList<>();
        mData.add("书画");
        mData.add("音乐");
        mData.add("舞蹈");
        mData.add("球类");
        mData.add("武术");
        mData.add("书法");
        mData.add("围棋");
        mAdapter = new HorizontalListViewAdapter(getActivity(), mData);
        hlArt.setAdapter(mAdapter);

//        courseBeanList = new ArrayList<>();
//        courseAdapter = new HPCourseAdapter(courseBeanList, getActivity());
//        LvCourseName.setAdapter(courseAdapter);
//        MyUtiles.setListViewHeightBasedOnChildren(LvCourseName, getActivity());


        hlArt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "id=" + mData.get(position), Toast.LENGTH_SHORT).show();
                mAdapter.setSelectedPosition(position);
                mAdapter.notifyDataSetChanged();
            }
        });

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
