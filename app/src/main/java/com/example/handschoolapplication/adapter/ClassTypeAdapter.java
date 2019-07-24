package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.ClassType;
import com.example.handschoolapplication.view.MyListView;

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
    private OnDelectTypeListener listener;
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
    public View getView(final int position, View view, ViewGroup parent) {

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
        List<String> typetwo = classType.getTypetwo();
//        holder.tvTypeTwo.setText(classType.getTypetwo());
        TypeTwoAdapter typeTwoAdapter = new TypeTwoAdapter(context, typetwo);
        holder.tvTypeTwo.setAdapter(typeTwoAdapter);
        if (typetwo.size()==0){
            holder.tvEdit.setVisibility(View.GONE);
        }else {
            holder.tvEdit.setVisibility(View.VISIBLE);
        }
        holder.tvDelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setDelect(position);
            }
        });

        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setEdit(position);
            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_type_one)
        TextView tvTypeOne;
        @BindView(R.id.lv_two_type_lsit)
        MyListView tvTypeTwo;
        @BindView(R.id.tv_delect)
        TextView tvDelect;
        @BindView(R.id.tv_edit)
        TextView tvEdit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnDelectTypeListener{
        void setDelect(int position);
        void setEdit(int position);
    }

    public void setOnDelectType(OnDelectTypeListener listener){
        this.listener = listener;
    }
}
