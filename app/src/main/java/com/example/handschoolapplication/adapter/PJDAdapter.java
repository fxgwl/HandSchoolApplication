package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.PJDetailBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */

public class PJDAdapter extends BaseAdapter {
    private Context context;
    private List<PJDetailBean.DataBean.ReplyInfoBean> mlist;

    public PJDAdapter(Context context, List<PJDetailBean.DataBean.ReplyInfoBean> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder = null;

        if (view == null) {
            switch (mlist.get(position).getReply_type()) {
                case "0":
                    view = View.inflate(context, R.layout.pjd_item1, null);
                    holder.tv_schoolreply = (TextView) view.findViewById(R.id.tv_schoolreply);
                    break;
                case "1":
                    view = View.inflate(context, R.layout.pjd_item2, null);
                    holder.tv_yhzp = (TextView) view.findViewById(R.id.tv_yhzp);
                    break;
                case "2":
                    view = View.inflate(context, R.layout.pjd_item, null);
                    holder.iv_hfhead= (ImageView) view.findViewById(R.id.iv_hfhead);
                    holder.tv_hfname= (TextView) view.findViewById(R.id.tv_hfname);
                    holder.iv_dj= (ImageView) view.findViewById(R.id.iv_dj);
                    holder.iv_hfhead= (ImageView) view.findViewById(R.id.iv_hfhead);


                    break;
            }
            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        return view;
    }

    static class ViewHolder {
        TextView tv_schoolreply;
        TextView tv_yhzp;
        ImageView iv_hfhead;
        TextView tv_hfname;
        ImageView iv_dj;
        TextView tv_time;
        TextView tv_replycontent;
    }
}
