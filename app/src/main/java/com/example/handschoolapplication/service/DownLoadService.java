package com.example.handschoolapplication.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2018/3/22.
 */

public class DownLoadService extends Service {

    private BroadcastReceiver receiver;//广播接收者
    private DownloadManager dlManager;//系统下载管理器
    private long downId;//下载管理器分配的
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
