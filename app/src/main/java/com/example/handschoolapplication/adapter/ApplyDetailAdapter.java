package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.ApplyDetail;
import com.example.handschoolapplication.utils.Internet;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/2.
 */

public class ApplyDetailAdapter extends BaseAdapter {

    public SendMessageListener listener;
    private Context context;
    private List<ApplyDetail.DataBean> mList;
    private int size = 0;
    private HashMap<Integer, Boolean> selectMap = new HashMap<>();


    public ApplyDetailAdapter(Context context, List<ApplyDetail.DataBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {

        if (mList != null) {
            size = mList.size();
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
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_apply_detail_lv, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        ApplyDetail.DataBean applydetail = mList.get(position);

        String head_photo = applydetail.getUserInfos().get(0).getHead_photo();
        Glide.with(context)
                .load(Internet.BASE_URL + head_photo)
                .centerCrop()
                .into(holder.civUsericon);
        holder.tvUsername.setText(applydetail.getUserInfos().get(0).getUser_name());
        holder.tvTime.setText(applydetail.getCreated());
        holder.tvCoursenum.setText(applydetail.getStudy_class() + "节课 【共" + applydetail.getAll_class() + "节课】");
        holder.num.setText((position + 1) + "");
        holder.tvStudentName.setText(applydetail.getOrderInfo().getStudent_name());

        holder.llSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.sendMessage(position);
            }
        });

        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mList.get(position).setCheck(true);
                } else {
                    mList.get(position).setCheck(false);
                }
                selectMap.put(position, isChecked);
            }
        });

        holder.cbSelect.setChecked(mList.get(position).isCheck());
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        selectMap.clear();
        if (mList != null) {
            for (int i = 0; i < mList.size(); i++) {
                selectMap.put(i, false);
            }
        }
        super.notifyDataSetChanged();
    }

    public void setList(List<ApplyDetail.DataBean> mList) {
        if (mList != null) {
            this.mList = mList;
            selectMap.clear();
            for (int i = 0; i < mList.size(); i++) {
                selectMap.put(i, false);
            }
        }
    }

    public HashMap<Integer, Boolean> getSeleteMap() {
        return selectMap;
    }

    public void setOnSendMessageListener(SendMessageListener listener) {
        this.listener = listener;
    }

    public interface SendMessageListener {
        void sendMessage(int position);
    }

    static class ViewHolder {
        @BindView(R.id.civ_usericon)
        CircleImageView civUsericon;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_coursenum)
        TextView tvCoursenum;
        @BindView(R.id.ll_send_message)
        LinearLayout llSendMessage;
        @BindView(R.id.num)
        TextView num;
        @BindView(R.id.iv_select)
        CheckBox cbSelect;
        @BindView(R.id.tv_student_name)
        TextView tvStudentName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
