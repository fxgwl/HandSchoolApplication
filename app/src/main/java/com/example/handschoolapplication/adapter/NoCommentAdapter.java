package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.PublishCommentActivity;
import com.example.handschoolapplication.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/24.
 */

public class NoCommentAdapter extends BaseAdapter {
    private Context context;
    private List<String> mlist;

    public NoCommentAdapter(Context context, List<String> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {

        return 4;
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
            view = View.inflate(context, R.layout.nocomment_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ClassAdapter classAdapter = new ClassAdapter(context, new ArrayList<String>());
        holder.nocom__item_mlv.setAdapter(classAdapter);
        holder.nocomment_pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PublishCommentActivity.class));
            }
        });
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.nocom__item_mlv)
        MyListView nocom__item_mlv;
        @BindView(R.id.nocomment_pingjia)
        TextView nocomment_pingjia;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
