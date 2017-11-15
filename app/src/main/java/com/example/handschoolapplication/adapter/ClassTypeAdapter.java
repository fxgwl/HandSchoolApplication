package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.ClassType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/24.
 */

public class ClassTypeAdapter extends BaseAdapter {
    private Context context;
    private List<ClassType> mlist;
    private int size = 0;

    public ClassTypeAdapter(Context context, List<ClassType> mlist) {
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
            view = View.inflate(context, R.layout.classtype_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
//        ClassTypelittleAdapter classTypelittleAdapter = new ClassTypelittleAdapter(context, new ArrayList<String>());
//        holder.typeLv.setAdapter(classTypelittleAdapter);
        ClassType classType = mlist.get(position);
        holder.tvTypeOne.setText(classType.getTypeOne());
        holder.tvTypeTwo.setText(classType.getTypetwo());
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_type_one)
        TextView tvTypeOne;
        @BindView(R.id.tv_type_two)
        TextView tvTypeTwo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
