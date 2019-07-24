package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.HasEvaBean;
import com.example.handschoolapplication.utils.Internet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/8/17.
 */

public class InteractionNewsAdapter extends BaseAdapter {

    private Context context;
    private List<HasEvaBean.DataBean> mList;
    private int size = 0;
    private OnDetailClickListener listener;


    public InteractionNewsAdapter(Context context, List<HasEvaBean.DataBean> mList, OnDetailClickListener listener) {
        this.context = context;
        this.mList = mList;
        this.listener = listener;
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
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder = null;

        if (view == null) {
            view = View.inflate(context, R.layout.item_in_lv, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        HasEvaBean.DataBean dataBean = mList.get(position);
        Glide.with(context)
                .load(Internet.BASE_URL + dataBean.getSend_photo())
                .centerCrop()
                .into(holder.civHead);
        holder.tvName.setText(dataBean.getSend_name());
//        String photo = "";
//        if (dataBean.getCourse_photo().contains(",")){
//            photo = dataBean.getCourse_photo().split(",")[0];
//        }else {
//            photo = dataBean.getCourse_photo();
//        }
        String picture_one = dataBean.getPicture_one();
        Glide.with(context)
                .load(Internet.BASE_URL + picture_one)
                .centerCrop()
                .into(holder.ivClass);
        holder.tvClassname.setText(dataBean.getCourse_name());
        holder.tvClassPrice.setText("价格：\t¥" + dataBean.getCourse_money());
        holder.tvNum.setText("数量：x" + dataBean.getCourse_num());

        if (dataBean.getUnread() > 0) {
            holder.tvUnRead.setVisibility(View.VISIBLE);
            holder.tvUnRead.setText(dataBean.getUnread() + "");
        } else {
            holder.tvUnRead.setVisibility(View.GONE);
        }

        holder.tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDetailClick(position);
            }
        });
        return view;
    }


    public interface OnDetailClickListener {
        void onDetailClick(int position);
    }

    static class ViewHolder {
        @BindView(R.id.civ_head)
        CircleImageView civHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_class)
        ImageView ivClass;
        @BindView(R.id.tv_classname)
        TextView tvClassname;
        @BindView(R.id.tv_class_price)
        TextView tvClassPrice;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_detail)
        TextView tvDetail;
        @BindView(R.id.tv_un_read)
        TextView tvUnRead;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
