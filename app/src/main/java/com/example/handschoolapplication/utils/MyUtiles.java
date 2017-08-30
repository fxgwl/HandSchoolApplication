package com.example.handschoolapplication.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/7/24.
 */

public class MyUtiles {

    public static void setHeight(ListView listview){
        int height = 0;
        int count = listview.getAdapter().getCount();
        for(int i=0;i<count;i++){
            View temp = listview.getAdapter().getView(i,null,listview);
            temp.measure(0,0);
            height += temp.getMeasuredHeight();
        }
        ListView.LayoutParams params = (ListView.LayoutParams) listview.getLayoutParams();
        params.width = ListView.LayoutParams.MATCH_PARENT;
        params.height = height;
        listview.setLayoutParams(params);
    }

    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dp * scale + 0.5f);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView,Context context) {

        //获取listview的适配器
        ListAdapter listAdapter = listView.getAdapter(); //item的高度

        if (listAdapter == null) {

            return;
        }
        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            listItem.measure(0, 0); //计算子项View 的宽高 //统计所有子项的总高度
//            totalHeight += Dp2Px(context,listItem.getMeasuredHeight())+listView.getDividerHeight();
            totalHeight+=listItem.getMeasuredHeight()+listView.getDividerHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight; listView.setLayoutParams(params);

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
