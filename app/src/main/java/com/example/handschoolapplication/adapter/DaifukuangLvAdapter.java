package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by axehome on 2017/8/24.
 */

public class DaifukuangLvAdapter extends BaseAdapter {
    private Context mContext;

    public DaifukuangLvAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 2;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_daifukuang, null);
            holder=new ViewHolder();
            holder.mTvOrderId=((TextView) convertView.findViewById(R.id.tv_itemdaifukuang_orderid));
            holder.mTvNickName=((TextView) convertView.findViewById(R.id.tv_itemdaifukuang_orderid));
            holder.mTvClassName=((TextView) convertView.findViewById(R.id.tv_itemdaifukuang_orderid));
            holder.mTvClassprice=((TextView) convertView.findViewById(R.id.tv_itemdaifukuang_orderid));
            holder.mTvTotalprice=((TextView) convertView.findViewById(R.id.tv_itemdaifukuang_orderid));
            holder.mTvNum=((TextView) convertView.findViewById(R.id.tv_itemdaifukuang_orderid));
            holder.mTvContemt=((TextView) convertView.findViewById(R.id.tv_itemdaifukuang_orderid));
            holder.civHeadImage=((CircleImageView) convertView.findViewById(R.id.civ_itendaifukuang_usericon));
            holder.ivClasslogo=((ImageView) convertView.findViewById(R.id.iv_itemdaifukuang_classlogo));
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }



    class ViewHolder {
        TextView mTvOrderId,mTvNickName,mTvClassName,mTvClassprice,mTvTotalprice,mTvNum,mTvContemt;
       ImageView ivClasslogo;
        CircleImageView civHeadImage;

    }
}
