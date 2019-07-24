package com.example.handschoolapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.handschoolapplication.activity.MainActivity;
import com.example.handschoolapplication.bean.MenuBean;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/4/17.
 */

public class MyJPushReceiver extends BroadcastReceiver {

    private static final String TAG = "JpushRecviver";


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            /**
             * 用户注册SDK的intent
             */
            Log.e(TAG, "onReceive1: ");
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            /**
             * 用户接受SDK消息的intent
             */
            Log.e(TAG, "收到了自定义消息消息是2");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            /**
             * 用户接收SDK通知栏信息的intent
             */
            Log.e(TAG, "收到了自定义消息消息是3");
            //保存服务器推送下来的消息的标题。
            String extra1 = bundle.getString(JPushInterface.EXTRA_TITLE);
            //消息内容
            String extra2 = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            //附加字段。这是个 JSON 字符串。
            String extra3 = bundle.getString(JPushInterface.EXTRA_EXTRA);
            //唯一标识消息的 ID, 可用于上报统计等。
            String extra4 = bundle.getString(JPushInterface.EXTRA_MSG_ID);
            //通知的标题
            String extra5 = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            //通知内容
            String extra6 = bundle.getString(JPushInterface.EXTRA_ALERT);
            //通知栏的Notification ID，可以用于清除Notification
            //如果服务端内容（alert）字段为空，则notification id 为0
            String extra7 = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_ID);
            //富媒体通知推送下载的HTML的文件路径,用于展现WebView
            String extra8 = bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_PATH);
            //富媒体通知推送下载的图片资源的文件名,多个文件名用 “，” 分开。
            // 与 “JPushInterface.EXTRA_RICHPUSH_HTML_PATH” 位于同一个路径。
            String extra9 = bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_RES);
            //JSON
            String extra10 = bundle.getString(JPushInterface.EXTRA_EXTRA);

            //这里做自己的操作，关于EventBus后续会讲的
            //匹配对应的内容发送通知
            if (extra6.contains("Hello World")) {
                Log.e(TAG, "onReceive包含: ");
//                EventBus.getDefault().post(new HomeRecviverEvent());
            }

            Log.e(TAG, "收到了自定义消息消息内容是2:" + extra1);
            Log.e(TAG, "收到了自定义消息消息extra是2:" + extra2);
            Log.e(TAG, "收到了自定义消息消息extra是2:" + extra3);
            Log.e(TAG, "收到了自定义消息消息extra是2:" + extra4);
            Log.e(TAG, "收到了自定义消息消息extra是2:" + extra5);
            Log.e(TAG, "收到了自定义消息消息extra是2:" + extra6);
            Log.e(TAG, "收到了自定义消息消息extra是2:" + extra7);
            Log.e(TAG, "收到了自定义消息消息extra是2:" + extra8);
            Log.e(TAG, "收到了自定义消息消息extra是2:" + extra9);
            Log.e(TAG, "收到了自定义消息消息extra是2:" + extra10);

            EventBus.getDefault().post(new MenuBean(4));
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            /**
             * 用户打开自定义通知栏的intent
             */
            String extra6 = bundle.getString(JPushInterface.EXTRA_ALERT);
            //如果包含 跳转至对应的界面
            if (extra6.contains("Hello World")) {
                Log.e(TAG, "onReceive包含: ");
                Log.e(TAG, "收到了自定义消息消息extra是4:");
//                Intent in = new Intent(context, NewFriendActivity.class);
//                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(in);

                //打开自定义的Activity
                Intent i = new Intent(context, MainActivity.class);
                EventBus.getDefault().post(new MenuBean(2));
                i.putExtras(bundle);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                context.startActivity(i);
            } else {
                Log.e(TAG, "onReceive不包含: ");
                //打开自定义的Activity
                Intent i = new Intent(context, MainActivity.class);
                EventBus.getDefault().post(new MenuBean(2));
                i.putExtras(bundle);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                context.startActivity(i);
            }
        } else {
            Log.e(TAG, "Unhandled intent - " + intent.getAction());
            Log.e(TAG, "收到了自定义消息消息extra是5:");
        }
    }
}
