package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.ConsultNewsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/8/17.
 */

public class ConsultNewsAdapter extends BaseAdapter {

    private Context context;
    private List<ConsultNewsBean> mList;
    private int size = 0;

    public ConsultNewsAdapter(Context context, List<ConsultNewsBean> mList) {
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
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;

        if (view == null) {
            view = View.inflate(context, R.layout.item_ln_lv, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.civ_usericon)
        CircleImageView civUsericon;
        @BindView(R.id.tv_class_name)
        TextView tvClassName;
        @BindView(R.id.tv_news_content)
        TextView tvNewsContent;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
