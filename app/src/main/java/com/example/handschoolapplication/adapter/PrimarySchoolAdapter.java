package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.PrimaryBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhw on 2018/7/10.
 */

public class PrimarySchoolAdapter extends BaseAdapter {

    private List<PrimaryBean.DataBean> mList;
    private Context context;
    public OnTVClickListener listener;

    public PrimarySchoolAdapter(List<PrimaryBean.DataBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (mList != null) return mList.size();
        return 0;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_class_schools_lv, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvSchoolName.setText(mList.get(position).getBind_name());
        holder.tvDelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnClickListener(position);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_school_name)
        TextView tvSchoolName;
        @BindView(R.id.tv_delect)
        ImageView tvDelect;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    public void setListener(OnTVClickListener listener) {
        this.listener = listener;
    }

    public interface OnTVClickListener{
        void setOnClickListener(int position);
    }
}
