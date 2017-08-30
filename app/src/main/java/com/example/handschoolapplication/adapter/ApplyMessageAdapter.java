package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.ApplyMessage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/18.
 */

public class ApplyMessageAdapter extends BaseAdapter {

    private Context context;
    private List<ApplyMessage> mList;
    private int size = 0;
    private int state=1;

    private CancelListener cancelListener;
    private StartLisener startLisener;
    private EndListener endListener;
    private HaveEndListener haveEndListener;

    public ApplyMessageAdapter(Context context, List<ApplyMessage> mList) {
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

        ViewHolder holder=null;

        if (view == null) {
            view = View.inflate(context, R.layout.item_applymsg_lv, null);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }



        holder.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelListener.onCancel(position);
            }
        });
        holder.tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLisener.onStart(position);
            }
        });
        holder.tvEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endListener.onEnd(position);
            }
        });
        holder.tvHadEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                haveEndListener.hadEnd(position);
            }
        });


        switch (state){
            case 1:
                int num = position % 3;
                switch (num){
                    case 0:
                        holder.tvCancel.setVisibility(View.VISIBLE);
                        holder.tvStart.setVisibility(View.VISIBLE);
                        holder.tvHadEnd.setVisibility(View.GONE);
                        holder.tvEnd.setVisibility(View.GONE);
                        break;
                    case 1:
                        holder.tvStart.setVisibility(View.GONE);
                        holder.tvCancel.setVisibility(View.GONE);
                        holder.tvHadEnd.setVisibility(View.GONE);
                        holder.tvEnd.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        holder.tvStart.setVisibility(View.GONE);
                        holder.tvCancel.setVisibility(View.GONE);
                        holder.tvEnd.setVisibility(View.GONE);
                        holder.tvHadEnd.setVisibility(View.VISIBLE);
                        break;
                }
                break;
            case 2:
                holder.tvCancel.setVisibility(View.VISIBLE);
                holder.tvStart.setVisibility(View.VISIBLE);
                holder.tvHadEnd.setVisibility(View.GONE);
                holder.tvEnd.setVisibility(View.GONE);
                break;
            case 3:
                holder.tvStart.setVisibility(View.GONE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvHadEnd.setVisibility(View.GONE);
                holder.tvEnd.setVisibility(View.VISIBLE);
                break;
            case 4:
                holder.tvStart.setVisibility(View.GONE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvEnd.setVisibility(View.GONE);
                holder.tvHadEnd.setVisibility(View.VISIBLE);
                break;
        }

        return view;
    }

    public void setState(int state){
        this.state=state;
    }

    static class ViewHolder {
        @BindView(R.id.iv_course)
        ImageView ivCourse;
        @BindView(R.id.tv_course_name)
        TextView tvCourseName;
        @BindView(R.id.tv_num1)
        TextView tvNum1;
        @BindView(R.id.tv_num2)
        TextView tvNum2;
        @BindView(R.id.tv_tearcher)
        TextView tvTearcher;
        @BindView(R.id.tv_cancel)
        TextView tvCancel;
        @BindView(R.id.tv_start)
        TextView tvStart;
        @BindView(R.id.tv_end)
        TextView tvEnd;
        @BindView(R.id.tv_had_end)
        TextView tvHadEnd;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private interface CancelListener{
        void onCancel(int position);
    }
    private interface StartLisener{
        void onStart(int position);
    }
    private interface EndListener{
        void onEnd(int position);
    }
    private interface HaveEndListener{
        void hadEnd(int position);
    }

    //取消按键
    private void setOnCancelListener(CancelListener listener){
        this.cancelListener=listener;
    }

    //开课按键
    private void setOnStartListener(StartLisener listener){
        this.startLisener=listener;
    }
    //结束按键
    private void setOnEndListener(EndListener listener){
        this.endListener=listener;
    }
    //已结束按键
    private void setOnHadEndListener(HaveEndListener listener){
        this.haveEndListener=listener;
    }
}
