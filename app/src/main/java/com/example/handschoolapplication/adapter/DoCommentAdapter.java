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

public class DoCommentAdapter extends BaseAdapter {
    private Context context;
    private List<String> mlist;

    public DoCommentAdapter(Context context, List<String> mlist) {
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
            view = View.inflate(context, R.layout.docomment_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ClassAdapter classAdapter = new ClassAdapter(context, new ArrayList<String>());
        holder.docomment_item_mlv.setAdapter(classAdapter);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.docomment_item_mlv)
        MyListView docomment_item_mlv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
