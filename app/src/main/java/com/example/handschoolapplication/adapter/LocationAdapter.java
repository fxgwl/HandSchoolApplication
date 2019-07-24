package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.handschoolapplication.MyApplication;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.AddressSearchTextEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/22.
 */

public class LocationAdapter extends BaseAdapter {

    private Context context;
    private List<AddressSearchTextEntity> mList;


    public LocationAdapter(Context context, List<AddressSearchTextEntity> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_location, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvLocation.setText(mList.get(position).getTitle());
        holder.tvLocationt.setText(mList.get(position).getSnippet());
       if (MyApplication.selectPosition==position){
           holder.cbCheck.setVisibility(View.VISIBLE);
       }else{
           holder.cbCheck.setVisibility(View.GONE);
       }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_location)
        TextView tvLocation;
        @BindView(R.id.tv_locationt)
        TextView tvLocationt;
        @BindView(R.id.cb_check)
        ImageView cbCheck;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
