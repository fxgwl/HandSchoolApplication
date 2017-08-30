package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ListItemAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.CourseListBean;
import com.example.handschoolapplication.bean.ListItemBean;
import com.example.handschoolapplication.utils.MyUtiles;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class LearnPlanFragment extends BaseFragment {

    @BindView(R.id.lv_list)
    ListView lvList;
    private View view;

    private List<CourseListBean> courseListBeanList;
    private ListAdapter listAdapter;

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
        initViewData();
        return view;
    }

    private void initViewData() {
        courseListBeanList = new ArrayList<>();
        courseListBeanList.add(new CourseListBean());
        courseListBeanList.add(new CourseListBean());
        courseListBeanList.add(new CourseListBean());
        listAdapter = new ListAdapter();

        lvList.setAdapter(listAdapter);
        MyUtiles.setListViewHeightBasedOnChildren(lvList,getActivity());
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_plan;
    }


    @OnClick(R.id.tv_complete)
    public void onViewClicked() {
        // TODO: 2017/7/25 完成点击的事件
    }

    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return courseListBeanList.size();
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
        public View getView(int position, View view, ViewGroup parent) {

            ViewHolder holder = null;

            if (view == null) {
                view = View.inflate(getActivity(), R.layout.item_plan_list_lv, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            courseListItemBeanList = new ArrayList<>();
            courseListItemBeanList.add(new ListItemBean());
            courseListItemBeanList.add(new ListItemBean());
            courseListItemBeanList.add(new ListItemBean());
            listItemAdapter=new ListItemAdapter(getActivity(),courseListItemBeanList);
            holder.lvListitem.setAdapter(listItemAdapter);
//            MyUtiles.setListViewHeightBasedOnChildren(holder.lvListitem);

            return view;
        }

        class ViewHolder {
            @BindView(R.id.iv_select_all)
            ImageView ivSelectAll;
            @BindView(R.id.tv_listname)
            TextView tvListname;
            @BindView(R.id.iv_to_detail)
            ImageView ivToDetail;
            @BindView(R.id.lv_listitem)
            ListView lvListitem;
            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }

        }
    }
}
