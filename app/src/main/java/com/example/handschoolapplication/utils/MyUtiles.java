package com.example.handschoolapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    /**
     * 判断电话号码是否符合格式.
     *
     * @param inputText the input text
     * @return true, if is phone
     */
    public static boolean isPhone(String inputText) {
        Pattern p = Pattern.compile("^((14[0-9])|(13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(inputText);
        return m.matches();
    }

    /**
     * @将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     * @author QQ986945193
     * @Date 2015-01-26
     * @param path 图片路径
     * @return
     */
    public static String imageToBase64(String path) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {

            InputStream in = new FileInputStream(path);

            data = new byte[in.available()];

            in.read(data);

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        Base64Encoder encoder = new Base64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data);

    }

    public static void saveBeanByFastJson(Context context, String key,
                                          Object obj) {
        SharedPreferences.Editor editor = context.getSharedPreferences(key,Context.MODE_PRIVATE).edit();
        String objString = JSON.toJSONString(obj);// fastjson的方法，需要导包的
        editor.putString(key, objString).commit();
    }

    /**
     *
     * @param context
     * @param key
     * @param clazz
     *            这里传入一个类就是我们所需要的实体类(obj)
     * @return 返回我们封装好的该实体类(obj)
     */
    public static <T> T getBeanByFastJson(Context context, String key,
                                          Class<T> clazz) {
        String objString = context.getSharedPreferences(key,Context.MODE_PRIVATE).getString(key, "");
        return JSON.parseObject(objString, clazz);
    }
}
