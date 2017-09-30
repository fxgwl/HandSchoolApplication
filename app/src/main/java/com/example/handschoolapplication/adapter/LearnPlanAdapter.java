//package com.example.handschoolapplication.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.handschoolapplication.R;
//import com.example.handschoolapplication.bean.CourseListBean;
//import com.example.handschoolapplication.bean.ListItemBean;
//import com.example.handschoolapplication.view.MyListView;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//import static com.baidu.location.d.a.i;
//
///**
// * Created by Administrator on 2017/9/12.
// */
//
//public class LearnPlanAdapter extends BaseAdapter {
//
//    // 填充数据的list
//    private List<CourseListBean> list;
//    // 上下文
//    private Context context;
//    // 用来导入布局
//    private LayoutInflater inflater = null;
//
//    private List<ListItemBean> courseListItemBeanList;
//    private ListItemAdapter listItemAdapter;
//    private HashMap<Integer, Boolean> deleteMap = new HashMap<>();
//    // 用来控制CheckBox的选中状况
//    private static HashMap<Integer, Boolean> isSelected;
//
//    public static final int MODE_NORMAL = 0;
//    public static final int MODE_EDIT = 1;
//    private int currentMode = MODE_NORMAL;
//
//    public static HashMap<Integer, Boolean> getIsSelected() {
//        return isSelected;
//    }
//
//    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
//        LearnPlanAdapter.isSelected = isSelected;
//    }
//
//    // 构造器
//    public LearnPlanAdapter(List<CourseListBean> list, Context context) {
//        this.context = context;
//        this.list = list;
//        inflater = LayoutInflater.from(context);
//        isSelected = new HashMap<Integer, Boolean>();
//        // 初始化数据
//        initDate();
//    }
//
//    // 初始化isSelected的数据
//    private void initDate() {
//        for (int i = 0; i < list.size(); i++) {
//            getIsSelected().put(i, false);
//        }
//    }
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return list.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        ViewHolder holder = null;
//        if (convertView == null) {
//            convertView = View.inflate(context, R.layout.item_plan_list_lv, null);
//            holder = new ViewHolder(convertView);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        // 设置list中TextView的显示
////        holder.tv.setText(list.get(position));
//        // 根据isSelected来设置checkbox的选中状况
//        holder.ivSelectAll.setChecked(getIsSelected().get(position));
//
//
//        courseListItemBeanList = new ArrayList<>();
//        courseListItemBeanList.add(new ListItemBean());
//        courseListItemBeanList.add(new ListItemBean());
//        courseListItemBeanList.add(new ListItemBean());
//        listItemAdapter = new ListItemAdapter(context, courseListItemBeanList);
//        holder.lvListitem.setAdapter(listItemAdapter);
//
//        holder.ivSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                deleteMap.put(i, b);
//            }
//        });
//
//        if (currentMode == MODE_NORMAL) {
//
//        } else {
//
//        }
//
//        return convertView;
//    }
//
//    public void setList(List<CourseListBean> mList) {
//        if (mList != null) {
//            this.list = mList;
//            deleteMap.clear();
//            for (int i = 0; i < mList.size(); i++) {
//                deleteMap.put(i, false);
//            }
//        }
//    }
//
//     public static class ViewHolder {
//        @BindView(R.id.iv_select_all)
//        public CheckBox ivSelectAll;
//        @BindView(R.id.tv_listname)
//        TextView tvListname;
//        @BindView(R.id.iv_to_detail)
//        ImageView ivToDetail;
//        @BindView(R.id.lv_listitem)
//        MyListView lvListitem;
//
//        ViewHolder(View view) {
//            ButterKnife.bind(this, view);
//        }
//    }
//
//    @Override
//    public void notifyDataSetInvalidated() {
//        deleteMap.clear();
//        if (list != null) {
//            for (int i = 0; i < list.size(); i++) {
//                deleteMap.put(i, false);
//            }
//        }
//        super.notifyDataSetChanged();
//    }
//
//    public void switchMode() {
//
//        currentMode = MODE_EDIT;
//
//        notifyDataSetChanged();
//    }
//
//    public void switchNormalMode() {
//
//        currentMode = MODE_NORMAL;
//
//        notifyDataSetChanged();
//    }
//}
