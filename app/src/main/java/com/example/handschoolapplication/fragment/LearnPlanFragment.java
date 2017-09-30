package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.LearnPlansAdapter;
import com.example.handschoolapplication.adapter.ListItemAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.GroupInfo;
import com.example.handschoolapplication.bean.ListItemBean;
import com.example.handschoolapplication.bean.ProductInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


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
    private View view;

    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量

    private List<GroupInfo> groups = new ArrayList<GroupInfo>();// 组元素数据列表
    private Map<String, List<ProductInfo>> children = new HashMap<String, List<ProductInfo>>();// 子元素数据列表

    private String flag = "1";//此时处于完成状态  显示编辑

    private List<ProductInfo> courseListBeanList;
    private LearnPlansAdapter mAdapter;

    private ListItemAdapter listItemAdapter;
    private List<ListItemBean> courseListItemBeanList;

    public LearnPlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initViewData();
        initEvents();

        return view;
    }


    private void initViewData() {
//        courseListBeanList = new ArrayList<>();
//        courseListBeanList.add(new CourseListBean());
//        courseListBeanList.add(new CourseListBean());
//        courseListBeanList.add(new CourseListBean());

        for (int i = 0; i < 6; i++) {

            groups.add(new GroupInfo(i + "", (i + 1) + "号店"));

            List<ProductInfo> products = new ArrayList<ProductInfo>();
            for (int j = 0; j <= i; j++) {

                products.add(new ProductInfo(j + "", "商品", "", groups.get(i).getName() + "的第" + (j + 1) + "个商品", 120.00 + i * j, 1));
            }
            children.put(groups.get(i).getId(), products);// 将组元素的一个唯一值，这里取Id，作为子元素List的Key
        }

        mAdapter=new LearnPlansAdapter(groups,children,getActivity());
        lvList.setAdapter(mAdapter);
    }

    private void initEvents() {
        mAdapter.setCheckInterface(this);// 关键步骤1,设置复选框接口
        mAdapter.setModifyCountInterface(this);// 关键步骤2,设置数量增减接口

        for (int i = 0; i < mAdapter.getGroupCount(); i++) {
            lvList.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
        }

    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_plan;
    }


    @OnClick(R.id.tv_complete)
    public void onViewClicked() {
        //完成、编辑点击的事件
        if (flag.equals("1")) {
            tvComplete.setText("完成");
            mAdapter.switchMode();
            flag = "2";
        } else {
            tvComplete.setText("编辑");
            mAdapter.switchNormalMode();
            flag = "1";
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.cb_all, R.id.tv_delect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_all:
                doCheckAll();
                break;
            case R.id.tv_delect:
                break;
        }
    }

    /**
     * 删除操作<br>
     * 1.不要边遍历边删除，容易出现数组越界的情况<br>
     * 2.现将要删除的对象放进相应的列表容器中，待遍历完后，以removeAll的方式进行删除
     */
    protected void doDelete() {
        List<GroupInfo> toBeDeleteGroups = new ArrayList<GroupInfo>();// 待删除的组元素列表
        for (int i = 0; i < groups.size(); i++) {
            GroupInfo group = groups.get(i);
            if (group.isChoosed()) {

                toBeDeleteGroups.add(group);
            }
            List<ProductInfo> toBeDeleteProducts = new ArrayList<ProductInfo>();// 待删除的子元素列表
            List<ProductInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                if (childs.get(j).isChoosed()) {
                    toBeDeleteProducts.add(childs.get(j));
                }
            }
            childs.removeAll(toBeDeleteProducts);

        }

        groups.removeAll(toBeDeleteGroups);

        mAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        GroupInfo group = groups.get(groupPosition);
        List<ProductInfo> childs = children.get(group.getId());
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
        List<ProductInfo> childs = children.get(group.getId());
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

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        ProductInfo product = (ProductInfo) mAdapter.getChild(groupPosition, childPosition);
        int currentCount = product.getCount();
        currentCount++;
        product.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");

        mAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        ProductInfo product = (ProductInfo) mAdapter.getChild(groupPosition, childPosition);
        int currentCount = product.getCount();
        if (currentCount == 1)
            return;
        currentCount--;

        product.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");

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


    /**
     * 全选与反选
     */
    private void doCheckAll() {
        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).setChoosed(cbAll.isChecked());
            GroupInfo group = groups.get(i);
            List<ProductInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                childs.get(j).setChoosed(cbAll.isChecked());
            }
        }
        mAdapter.notifyDataSetChanged();
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
            List<ProductInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                ProductInfo product = childs.get(j);
                if (product.isChoosed()) {
                    totalCount++;
                    totalPrice += product.getPrice() * product.getCount();
                }
            }
        }
//        tv_total_price.setText("￥" + totalPrice);
//        tv_go_to_pay.setText("去支付(" + totalCount + ")");
    }
}
