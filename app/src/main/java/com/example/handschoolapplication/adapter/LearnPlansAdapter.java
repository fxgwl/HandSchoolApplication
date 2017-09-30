package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.GroupInfo;
import com.example.handschoolapplication.bean.ProductInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/13.
 */

public class LearnPlansAdapter extends BaseExpandableListAdapter {


    private List<GroupInfo> mList;
    private Map<String, List<ProductInfo>> childrens;
    private Context context;

    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;

    private static final int MODE_NORMAL = 0;
    private static final int MODE_EDIT = 1;
    private int currentMode = MODE_NORMAL;

    public LearnPlansAdapter(List<GroupInfo> mList, Map<String, List<ProductInfo>> childrens, Context context) {
        this.mList = mList;
        this.childrens = childrens;
        this.context = context;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String groupId = mList.get(groupPosition).getId();
        return childrens.get(groupId).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<ProductInfo> childs = childrens.get(mList.get(groupPosition).getId());

        return childs.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        GroupHolder gholder = null;
        if (convertView == null) {
            gholder = new GroupHolder();
            convertView = View.inflate(context, R.layout.item_plan_list_lv, null);
            gholder.cb_check = (CheckBox) convertView.findViewById(R.id.cb_select_all);
            gholder.tv_group_name = (TextView) convertView.findViewById(R.id.tv_listname);
            convertView.setTag(gholder);
        } else {
            gholder = (GroupHolder) convertView.getTag();
        }

        final GroupInfo group = (GroupInfo) getGroup(groupPosition);
        if (group != null) {
            gholder.tv_group_name.setText(group.getName());
            gholder.cb_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)

                {
                    group.setChoosed(((CheckBox) v).isChecked());
                    checkInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());// 暴露组选接口
                }
            });
            gholder.cb_check.setChecked(group.isChoosed());
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final ChildHolder cholder;
        if (convertView == null) {
            cholder = new ChildHolder();
            convertView = View.inflate(context, R.layout.item_listitem_lv, null);
            cholder.cb_check = (CheckBox) convertView.findViewById(R.id.iv_select);

            cholder.tv_product_desc = (TextView) convertView.findViewById(R.id.tv_name);//课程名称
            cholder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);//课程单价
            cholder.iv_increase = (TextView) convertView.findViewById(R.id.tv_add);//课程数量增加
            cholder.iv_decrease = (TextView) convertView.findViewById(R.id.tv_sub);//课程数量减少
            cholder.tv_count = (TextView) convertView.findViewById(R.id.tv_num);//课程数量
            cholder.ll_num= (LinearLayout) convertView.findViewById(R.id.ll_number);
            cholder.ll_edit_num= (LinearLayout) convertView.findViewById(R.id.ll_edit_num);

            // childrenMap.put(groupPosition, convertView);
            convertView.setTag(cholder);
        } else {
            // convertView = childrenMap.get(groupPosition);
            cholder = (ChildHolder) convertView.getTag();
        }
        final ProductInfo product = (ProductInfo) getChild(groupPosition, childPosition);

        if (product != null) {

            if (currentMode==MODE_EDIT){
                cholder.ll_edit_num.setVisibility(View.VISIBLE);
                cholder.ll_num.setVisibility(View.GONE);
            }
            if (currentMode==MODE_NORMAL){
                cholder.ll_num.setVisibility(View.GONE);
                cholder.ll_edit_num.setVisibility(View.VISIBLE);
            }

            cholder.tv_product_desc.setText(product.getDesc());
            cholder.tv_price.setText("￥" + product.getPrice() + "");
            cholder.tv_count.setText(product.getCount() + "");
            cholder.cb_check.setChecked(product.isChoosed());
            cholder.cb_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    product.setChoosed(((CheckBox) v).isChecked());
                    cholder.cb_check.setChecked(((CheckBox) v).isChecked());
                    checkInterface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());// 暴露子选接口
                }
            });
            cholder.iv_increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyCountInterface.doIncrease(groupPosition, childPosition, cholder.tv_count, cholder.cb_check.isChecked());// 暴露增加接口
                }
            });
            cholder.iv_decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyCountInterface.doDecrease(groupPosition, childPosition, cholder.tv_count, cholder.cb_check.isChecked());// 暴露删减接口
                }
            });
        }
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void switchMode() {

        currentMode = MODE_EDIT;

        notifyDataSetChanged();
    }

    public void switchNormalMode() {

        currentMode = MODE_NORMAL;

        notifyDataSetChanged();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    /**
     * 组元素绑定器
     */
    private class GroupHolder {
        CheckBox cb_check;
        TextView tv_group_name;
    }

    /**
     * 子元素绑定器
     */
    private class ChildHolder {
        CheckBox cb_check;

        TextView tv_product_name;
        TextView tv_product_desc;
        TextView tv_price;
        TextView iv_increase;
        TextView tv_count;
        TextView iv_decrease;
        LinearLayout ll_num;
        LinearLayout ll_edit_num;
    }

    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition 组元素位置
         * @param isChecked     组元素选中与否
         */
        public void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变时触发的事件
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param isChecked     子元素选中与否
         */
        public void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);
    }
}
