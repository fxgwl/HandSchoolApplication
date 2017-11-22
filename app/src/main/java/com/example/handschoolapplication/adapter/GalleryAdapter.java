package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.handschoolapplication.R;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> implements View.OnClickListener {

    private LayoutInflater mInflater;
    private List<String> mDatas;
    private int size = 0;
    private int selectedPosition = 0;// 选中的位置
    //define interface
    private OnItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());

        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }
    public GalleryAdapter(Context context, List<String> datats) {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
    }

    public void setList(List<String> datats){
        this.mDatas = datats;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        TextView mTxt;
    }

    @Override
    public int getItemCount() {
        if (mDatas!=null){
            size = mDatas.size();
        }
        return size;
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.hor_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mTxt = (TextView) view.findViewById(R.id.file);
        view.setOnClickListener(this);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,  int position) {
        viewHolder.mTxt.setText(mDatas.get(position));
        viewHolder.itemView.setTag(position);
        if (selectedPosition==position){
            viewHolder.mTxt.setBackgroundResource(R.drawable.textview_pressed);
            viewHolder.mTxt.setTextColor(Color.parseColor("#ffffff"));
        }else {
            viewHolder.mTxt.setBackgroundResource(R.drawable.textview_selector);
            viewHolder.mTxt.setTextColor(Color.parseColor("#6f6f6f"));
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }



}