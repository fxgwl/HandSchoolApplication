package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.example.handschoolapplication.R;

import java.util.List;

public class ChoutiRecyclerViewAdapter extends BaseAdapter {

    private List<PoiItem> mList;
    private Context context;
    private boolean isClick;


    public ChoutiRecyclerViewAdapter(List<PoiItem> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    @Override
    public int getCount() {

        if (mList != null) return mList.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_poi_search_lv, null);
            holder = new ViewHolder();
            holder.tvWords = (TextView) view.findViewById(R.id.tv_words);
            holder.tvAddress = (TextView) view.findViewById(R.id.tv_poi_address);
            holder.ivSelect = (ImageView) view.findViewById(R.id.iv_selected);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
//        if (isClick){
        if (i == 0) {
            holder.ivSelect.setVisibility(View.VISIBLE);
            holder.tvWords.setTextColor(context.getResources().getColor(R.color.blue));
            holder.tvAddress.setTextColor(context.getResources().getColor(R.color.blue));
        } else {
            holder.ivSelect.setVisibility(View.GONE);
            holder.tvWords.setTextColor(context.getResources().getColor(R.color.t333333));
            holder.tvAddress.setTextColor(context.getResources().getColor(R.color.t333333));
        }
//        }else {
//            holder.ivSelect.setVisibility(View.GONE);
//            holder.tvWords.setTextColor(context.getResources().getColor(R.color.t333333));
//            holder.tvAddress.setTextColor(context.getResources().getColor(R.color.t333333));
//        }
        holder.tvWords.setText(mList.get(i).getTitle());
        holder.tvAddress.setText(mList.get(i).getSnippet());
        return view;
    }

    class ViewHolder {
        TextView tvWords;
        TextView tvAddress;
        ImageView ivSelect;
    }
}
