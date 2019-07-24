package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.EvaluateManagerBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/24.
 */

public class CommentManagerAdapter extends BaseAdapter {
    private Context context;
    private List<EvaluateManagerBean> mlist;
    private int size = 0;
    private ReplyEvaluateListener listener;

    public CommentManagerAdapter(Context context, List<EvaluateManagerBean> mlist) {
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
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder = null;

        if (view == null) {
            view = View.inflate(context, R.layout.commentmanager_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        EvaluateManagerBean evaluateManagerBean = mlist.get(position);
        holder.tvCourseName.setText(evaluateManagerBean.getCourse_name());
        String picture_one = evaluateManagerBean.getPicture_one();

//        if (evaluateManagerBean.getCourse_photo().contains(",")){
//            Glide.with(context).load(Internet.BASE_URL+evaluateManagerBean.getCourse_photo().split(",")[0]).centerCrop().into(holder.ivCourse);
//        }else
        Glide.with(context).load(Internet.BASE_URL + picture_one).centerCrop().into(holder.ivCourse);
        holder.tvEavaName.setText("评价人：" + evaluateManagerBean.getSend_name());
        holder.tvContent.setText(evaluateManagerBean.getContents());
        holder.tvReplyEva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.toReplyEvaluate(position);
            }
        });
        List<EvaluateManagerBean.ReplyInfoBean> replyInfo = evaluateManagerBean.getReplyInfo();
        ArrayList<EvaluateManagerBean.ReplyInfoBean> replyInfoBeen = new ArrayList<>();
        if (replyInfo != null) {
            for (int i = 0; i < replyInfo.size(); i++) {
                String reply_type = replyInfo.get(i).getReply_type();
                if ("2".equals(reply_type)) {
                    replyInfo.remove(i);
                }
            }
            replyInfoBeen.addAll(replyInfo);
        }
        CmApplyAdapter cmApplyAdapter = new CmApplyAdapter(context, replyInfo);
        holder.commentmanagerLv.setAdapter(cmApplyAdapter);
        holder.tvTime.setText(evaluateManagerBean.getInteract_time() + "");
        return view;
    }

    public void setOnReplyEvaluateListener(ReplyEvaluateListener listener) {
        this.listener = listener;
    }

    public interface ReplyEvaluateListener {
        void toReplyEvaluate(int position);
    }

    static class ViewHolder {
        @BindView(R.id.iv_course)
        ImageView ivCourse;
        @BindView(R.id.tv_course_name)
        TextView tvCourseName;
        @BindView(R.id.tv_eava_name)
        TextView tvEavaName;
        @BindView(R.id.tv_delete_eva)
        TextView tvDeleteEva;
        @BindView(R.id.tv_reply_eva)
        TextView tvReplyEva;
        @BindView(R.id.commentmanager_lv)
        MyListView commentmanagerLv;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
