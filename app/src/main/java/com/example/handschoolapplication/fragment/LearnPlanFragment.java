package com.example.handschoolapplication.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.ApplyDetailPayActivity;
import com.example.handschoolapplication.activity.NowApplyActivity;
import com.example.handschoolapplication.activity.RegisterPersonActivity;
import com.example.handschoolapplication.adapter.LearnPlansAdapter;
import com.example.handschoolapplication.adapter.ListItemAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.CarListBean;
import com.example.handschoolapplication.bean.GroupInfo;
import com.example.handschoolapplication.bean.ListItemBean;
import com.example.handschoolapplication.bean.ProductInfo;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.SelfDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;


/**
 * A simple {@link Fragment} subclass.
 */
public class LearnPlanFragment extends BaseFragment implements LearnPlansAdapter.CheckInterface, LearnPlansAdapter.ModifyCountInterface {

    @BindView(R.id.lv_list)
    ExpandableListView lvList;
    @BindView(R.id.tv_complete)
    TextView tvComplete;
    Unbinder unbinder;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.tv_delect)
    TextView tvDelect;
    @BindView(R.id.ll_delete)
    LinearLayout llDelete;
    @BindView(R.id.tv_heji)
    TextView tvHeji;
    @BindView(R.id.tv_jiesuan)
    TextView tvJiesuan;
    @BindView(R.id.ll_jiesuan)
    LinearLayout llJiesuan;
    ArrayList<CarListBean.DataBean> carBeans = new ArrayList<>();
    private View view;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    private List<GroupInfo> groups = new ArrayList<GroupInfo>();// 组元素数据列表
    private Map<String, List<CarListBean.DataBean>> children = new HashMap<String, List<CarListBean.DataBean>>();// 子元素数据列表
    private String flag = "1";//此时处于完成状态  显示编辑
    private List<ProductInfo> courseListBeanList;
    private LearnPlansAdapter mAdapter;
    private ListItemAdapter listItemAdapter;
    private List<ListItemBean> courseListItemBeanList;
    private String user_id;
    private List<String> orderIdList;
    private ArrayList<CarListBean.DataBean> orderBean;
    private String course_name;
    private String user_phone;

    public LearnPlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        user_id = (String) SPUtils.get(getActivity(), "userId", "");
        mAdapter = new LearnPlansAdapter(groups, children, getActivity());
        lvList.setAdapter(mAdapter);
        return view;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_plan;
    }

    @Override
    public void onResume() {
        super.onResume();
        initViewData();
        cbAll.setChecked(false);
        tvHeji.setText("合计:  ¥" + 0.00);
    }

    @OnClick(R.id.tv_complete)
    public void onViewClicked() {
        //完成、编辑点击的事件
        if (flag.equals("1")) {
            tvComplete.setText("完成");
//            llJiesuan.setVisibility(View.GONE);
            llDelete.setVisibility(View.VISIBLE);
//            mAdapter.switchMode();
            flag = "2";
        } else {
            tvComplete.setText("编辑");
//            llJiesuan.setVisibility(View.VISIBLE);
            llDelete.setVisibility(View.GONE);
//            mAdapter.switchNormalMode();
            flag = "1";
        }
    }

    @OnClick({R.id.cb_all, R.id.tv_delect, R.id.tv_jiesuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_all:
                doCheckAll();
                break;
            case R.id.tv_delect:
                doDelete();
                break;
            case R.id.tv_jiesuan:
                doPay();
                break;
        }
    }

    /**
     * 全选与反选
     */
    private void doCheckAll() {
        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).setChoosed(cbAll.isChecked());
            GroupInfo group = groups.get(i);
            List<CarListBean.DataBean> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                childs.get(j).setChoosed(cbAll.isChecked());
            }
        }
        mAdapter.notifyDataSetChanged();
        calculate();
    }

    /**
     * 删除操作<br>
     * 1.不要边遍历边删除，容易出现数组越界的情况<br>
     * 2.现将要删除的对象放进相应的列表容器中，待遍历完后，以removeAll的方式进行删除
     */
    protected void doDelete() {
        orderIdList = new ArrayList<>();
        List<GroupInfo> toBeDeleteGroups = new ArrayList<GroupInfo>();// 待删除的组元素列表
        for (int i = 0; i < groups.size(); i++) {
            GroupInfo group = groups.get(i);
            if (group.isChoosed()) {
                toBeDeleteGroups.add(group);
            }
            List<CarListBean.DataBean> toBeDeleteProducts = new ArrayList<CarListBean.DataBean>();// 待删除的子元素列表
            List<CarListBean.DataBean> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                if (childs.get(j).isChoosed()) {
                    toBeDeleteProducts.add(childs.get(j));
                    String order_id = childs.get(j).getOrder_id();
                    orderIdList.add(order_id);
                }
            }
            childs.removeAll(toBeDeleteProducts);

        }
        groups.removeAll(toBeDeleteGroups);

        String orderIdS = "";
        for (int i = 0; i < orderIdList.size(); i++) {
            if (i == 0) {
                orderIdS = orderIdList.get(i);
            } else {
                orderIdS = orderIdS + "," + orderIdList.get(i);
            }
        }
        OkHttpUtils
                .post()
                .url(Internet.DELECT_DELECT_PLAN)
                .addParams("order_id", orderIdS)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(LearnPlanFragment.java:255)" + e.getMessage());
                        Toast.makeText(getActivity(), "获取数据失败,请检查网络", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(LearnPlanFragment.java:261)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        mAdapter.notifyDataSetChanged();
        calculate();
    }

    private void doPay() {
        boolean canPay = true;
        orderBean = new ArrayList<>();
        for (int i = 0; i < groups.size(); i++) {
            GroupInfo group = groups.get(i);
            List<CarListBean.DataBean> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                if (childs.get(j).isChoosed()) {
                    //判断选中的课程是否可以报名
                    boolean stopPay = false;//是否可以报名
                    String course_state = childs.get(j).getCourseInfo().getCourse_state();//课程状态
                    //课程名称
                    course_name = childs.get(j).getCourseInfo().getCourse_name();
                    int enrol_num = childs.get(j).getCourseInfo().getEnrol_num();//已报名人数
                    int course_capacity = childs.get(j).getCourseInfo().getCourse_capacity();//课程总人数
                    if (enrol_num >= course_capacity) {
                        stopPay = true;
                    }
                    if (course_state.equals("2") || stopPay) {
                        canPay = false;
                    } else {
                        orderBean.add(childs.get(j));
                    }

                }
            }
        }

        boolean canBuy = orderBean.size() > 0;
        if (!canBuy) {
            Toast.makeText(activity, "您还没有选择任何课程", Toast.LENGTH_SHORT).show();
            return;
        }

        user_phone = (String) SPUtils.get(getActivity(), "user_phone", "");
        if (TextUtils.isEmpty(user_phone)) {
            showUnLoginDialog();
            return;
        }

        if (canPay) {
            startActivity(new Intent(getActivity(), ApplyDetailPayActivity.class).putExtra("orderBean", orderBean));
        } else {
            Toast.makeText(activity, course_name + "已停止报名", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private void calculate() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < groups.size(); i++) {
            GroupInfo group = groups.get(i);
            List<CarListBean.DataBean> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                CarListBean.DataBean product = childs.get(j);
                if (product.isChoosed()) {
                    totalCount++;
                    String class_money = product.getClass_money();
                    String money = class_money.split("元")[0];
                    totalPrice += (Double.parseDouble(money) * Double.parseDouble(product.getCourse_num()));
                }
            }
        }
        tvHeji.setText("合计:  ¥" + totalPrice);
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        GroupInfo group = groups.get(groupPosition);
        List<CarListBean.DataBean> childs = children.get(group.getId());
        for (int i = 0; i < childs.size(); i++) {
            childs.get(i).setChoosed(isChecked);
        }
        if (isAllCheck())
            cbAll.setChecked(true);
        else
            cbAll.setChecked(false);
        mAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {

        boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
        GroupInfo group = groups.get(groupPosition);
        List<CarListBean.DataBean> childs = children.get(group.getId());
        for (int i = 0; i < childs.size(); i++) {
            if (childs.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        if (allChildSameState) {
            group.setChoosed(isChecked);// 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
        } else {
            group.setChoosed(false);// 否则，组元素一律设置为未选中状态
        }

        if (isAllCheck())
            cbAll.setChecked(true);
        else
            cbAll.setChecked(false);
        mAdapter.notifyDataSetChanged();
        calculate();
    }

    private boolean isAllCheck() {

        for (GroupInfo group : groups) {
            if (!group.isChoosed())
                return false;

        }

        return true;
    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        CarListBean.DataBean product = (CarListBean.DataBean) mAdapter.getChild(groupPosition, childPosition);
        int currentCount = Integer.parseInt(product.getCourse_num());
        currentCount++;
        product.setCourse_num(currentCount + "");
        changeCount(currentCount + "", product);
        ((TextView) showCountView).setText(currentCount + "");
        mAdapter.notifyDataSetChanged();
        calculate();
    }

    private void changeCount(String num, CarListBean.DataBean product) {
        OkHttpUtils.post()
                .url(Internet.CHANGENUM)
                .addParams("order_id", product.getOrder_id())
                .addParams("course_num", num)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(LearnPlanFragment.java:395)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(LearnPlanFragment.java:312)" + response);

                    }
                });

    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        CarListBean.DataBean product = (CarListBean.DataBean) mAdapter.getChild(groupPosition, childPosition);
        int currentCount = Integer.parseInt(product.getCourse_num());
        if (currentCount == 1)
            return;
        currentCount--;
        product.setCourse_num(currentCount + "");
        changeCount(currentCount + "", product);
        ((TextView) showCountView).setText(currentCount + "");
        mAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        initViewData();
        cbAll.setChecked(false);
        tvHeji.setText("合计:  ¥" + 0.00);
    }

    private void initViewData() {
        Log.e("aaa",
                "(LearnPlanFragment.java:111)<--用户Id-->" + user_id);
        Log.e("aaa",
                "(LearnPlanFragment.java:113)<--购物车的url-->" + Internet.SHOPCAR);
        OkHttpUtils.post()
                .url(Internet.SHOPCAR)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(LearnPlanFragment.java:122)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(LearnPlanFragment.java:101)" + response);
                        Gson gson = new Gson();
                        carBeans.clear();
                        groups.clear();
                        children.clear();

                        if (TextUtils.isEmpty(response) || response.contains("没有信息")) {
//                            Toast.makeText(activity, "暂无学习计划", Toast.LENGTH_SHORT).show();
                            mAdapter.notifyDataSetChanged();
                            initEvents();
                        } else {
                            try {
                                carBeans.addAll(gson.fromJson(response, CarListBean.class).getData());
                            } catch (Exception e) {
                            }
                            HashMap<String, ArrayList<CarListBean.DataBean>> map = new HashMap<String, ArrayList<CarListBean.DataBean>>();
                            for (int i = 0; i < carBeans.size(); i++) {
                                if (map.containsKey(carBeans.get(i).getSchool_name())) {
                                    map.get(carBeans.get(i).getSchool_name()).add(carBeans.get(i));
                                } else {
                                    ArrayList<CarListBean.DataBean> dataBean = new ArrayList<CarListBean.DataBean>();
                                    dataBean.add(carBeans.get(i));
                                    map.put(carBeans.get(i).getSchool_name(), dataBean);
                                }
                            }
                            Log.e("aaa",
                                    "(LearnPlanFragment.java:120)" + map.toString());
                            Set set = map.keySet();
                            Iterator iterator = set.iterator();
                            for (int i = 0; i < map.size(); i++) {
                                iterator.hasNext();
                                String m = iterator.next().toString();
                                String school_id = map.get(m).get(0).getSchool_id();
                                groups.add(new GroupInfo(school_id, m));
                                List<CarListBean.DataBean> products = new ArrayList<CarListBean.DataBean>();
                                products = map.get(m);
                                children.put(groups.get(i).getId(), products);// 将组元素的一个唯一值，这里取Id，作为子元素List的Key
                            }
                            mAdapter.notifyDataSetChanged();
                            initEvents();
                        }

                    }
                });
    }

    private void initEvents() {
        mAdapter.setCheckInterface(this);// 关键步骤1,设置复选框接口
        mAdapter.setModifyCountInterface(this);// 关键步骤2,设置数量增减接口

        for (int i = 0; i < mAdapter.getGroupCount(); i++) {
            lvList.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
        }

    }

    private void showUnLoginDialog() {
        final SelfDialog selfDialog = new SelfDialog(getActivity());

        selfDialog.setMessage("是否绑定手机号?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                startActivity(new Intent(getActivity(), RegisterPersonActivity.class)
                        .putExtra("flag", "true")
                        .putExtra("type", "0"));
                selfDialog.dismiss();
            }
        });


        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                selfDialog.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    /**
     * 添加弹出的dialog关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements Dialog.OnDismissListener {


        @Override
        public void onDismiss(DialogInterface dialog) {
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
