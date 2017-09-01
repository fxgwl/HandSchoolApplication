package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/24.
 */

public class ClassTypeAdapter extends BaseAdapter {
    private Context context;
    private List<String> mlist;

    public ClassTypeAdapter(Context context, List<String> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {

        return 3;
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
            view = View.inflate(context, R.layout.classtype_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ClassTypelittleAdapter classTypelittleAdapter = new ClassTypelittleAdapter(context, new ArrayList<String>());
        holder.typeLv.setAdapter(classTypelittleAdapter);
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.type_lv)
        MyListView typeLv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
