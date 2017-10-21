package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.EvaluateManagerBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/24.
 */

public class CmApplyAdapter extends BaseAdapter {
    private Context context;
    private List<EvaluateManagerBean.ReplyInfoBean> mlist;
    private int size = 0;

    public CmApplyAdapter(Context context, List<EvaluateManagerBean.ReplyInfoBean> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {
        if (mlist != null) {
            size = mlist.size();
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
            view = View.inflate(context, R.layout.cmapply_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Log.e("aaa",
                "(CmApplyAdapter.java:63)" + size);
        EvaluateManagerBean.ReplyInfoBean replyInfoBean = mlist.get(position);
        String reply_type = replyInfoBean.getReply_type();
        if ("0".equals(reply_type)) {
            holder.tvReplyType.setText("追加评价：");
        } else if ("1".equals(reply_type)) {
            holder.tvReplyType.setText("回复：");
        }
        holder.tvContent.setText(replyInfoBean.getReply_content());
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_reply_type)
        TextView tvReplyType;
        @BindView(R.id.tv_content)
        TextView tvContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
