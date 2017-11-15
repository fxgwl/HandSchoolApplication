package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.PJDetailBean;
import com.example.handschoolapplication.utils.Internet;

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
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {

        return Integer.parseInt(mlist.get(position).getReply_type());
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        viewHolder1 holder1 = null;
        viewHolder2 holder2 = null;
        viewHolder3 holder3 = null;
        String type = mlist.get(position).getReply_type();
        if (view == null) {

            switch (type) {
                case "1":
                    view = View.inflate(context, R.layout.pjd_item1, null);
                    holder1 = new viewHolder1();
                    holder1.tv_schoolreply = (TextView) view.findViewById(R.id.tv_schoolreply);
                    view.setTag(holder1);
                    break;
                case "0":
                    view = View.inflate(context, R.layout.pjd_item2, null);
                    holder2 = new viewHolder2();
                    holder2.tv_yhzp = (TextView) view.findViewById(R.id.tv_yhzp);
                    view.setTag(holder2);
                    break;
                case "2":
                    view = View.inflate(context, R.layout.pjd_item, null);
                    holder3 = new viewHolder3();
                    holder3.iv_hfhead = (ImageView) view.findViewById(R.id.iv_hfhead);
                    holder3.tv_hfname = (TextView) view.findViewById(R.id.tv_hfname);
                    holder3.iv_dj = (ImageView) view.findViewById(R.id.iv_dj);
                    holder3.tv_replycontent = (TextView) view.findViewById(R.id.tv_replycontent);
                    view.setTag(holder3);
                    break;
            }

        } else {
            switch (type) {
                case "1":
                    holder1 = (viewHolder1) view.getTag();
                    break;
                case "0":
                    holder2 = (viewHolder2) view.getTag();
                    break;
                case "2":
                    holder3 = (viewHolder3) view.getTag();
                    break;
            }

        }
        switch (type) {
            case "1":
                holder1.tv_schoolreply.setText("学堂回复:" + mlist.get(position).getReply_content());
                break;
            case "0":
                holder2.tv_yhzp.setText(mlist.get(position).getReply_content());
                break;
            case "2":
                Glide.with(context)
                        .load(Internet.BASE_URL + mlist.get(position).getSend_photo())
                        .centerCrop()
                        .into(holder3.iv_hfhead);
                holder3.tv_hfname.setText(mlist.get(position).getFrom_name());
                holder3.tv_replycontent.setText(mlist.get(position).getReply_content());
                break;
        }

        return view;
    }

    //各个布局的控件资源
    class viewHolder1 {
        TextView tv_schoolreply;
    }

    class viewHolder2 {
        TextView tv_yhzp;
    }

    class viewHolder3 {
        ImageView iv_hfhead;
        TextView tv_hfname;
        ImageView iv_dj;
        TextView tv_time;
        TextView tv_replycontent;
    }
}
