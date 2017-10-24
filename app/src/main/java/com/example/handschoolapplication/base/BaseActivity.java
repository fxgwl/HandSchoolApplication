package com.example.handschoolapplication.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.HelpActivity;
import com.example.handschoolapplication.activity.MainActivity;
import com.example.handschoolapplication.activity.SettingsActivity;
import com.example.handschoolapplication.bean.MenuBean;
import com.example.handschoolapplication.view.CommonPopupWindow;
import com.example.handschoolapplication.view.MyPopupWindow;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2017/7/20.
 */

public abstract class BaseActivity extends AppCompatActivity {


    private Unbinder bind;
    private static CommonPopupWindow menuWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(getContentViewId());
        bind = ButterKnife.bind(this);
        Log.e(getClass().getSimpleName(), "--->onCreate: ");

    }

    public abstract int getContentViewId();

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(getClass().getSimpleName(), "--->onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(getClass().getSimpleName(), "--->onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(getClass().getSimpleName(), "--->onRestart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(getClass().getSimpleName(), "--->onpause: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(getClass().getSimpleName(), "--->onResume: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        Log.e(getClass().getSimpleName(), "--->onDestroy: ");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public static void setMenu(Activity context) {
        View view = View.inflate(context, R.layout.menu_style, null);
        MyPopupWindow myPopupWindow = new MyPopupWindow(context, view);
        myPopupWindow.setHeight(500);
        myPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    //向下弹出
    public void showMenuPop(final Context context, View view) {
        View view2 = View.inflate(context, R.layout.menu, null);
        RelativeLayout news = (RelativeLayout) view2.findViewById(R.id.ll_news);
        RelativeLayout home = (RelativeLayout) view2.findViewById(R.id.ll_home);
        RelativeLayout help = (RelativeLayout) view2.findViewById(R.id.ll_help);
        RelativeLayout wode = (RelativeLayout) view2.findViewById(R.id.ll_wode);
        final MyPopupWindow mppWinow = new MyPopupWindow(this, view2);
        mppWinow.setWidth(300);
        mppWinow.showAsDropDown(view, 0, 0);
        mppWinow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mppWinow.dismiss();
                startActivity(new Intent(BaseActivity.this, MainActivity.class));
                EventBus.getDefault().post(new MenuBean(2));
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mppWinow.dismiss();
                startActivity(new Intent(BaseActivity.this, MainActivity.class));
                EventBus.getDefault().post(new MenuBean(1));
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mppWinow.dismiss();
                startActivity(new Intent(BaseActivity.this, HelpActivity.class));
            }
        });
        wode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mppWinow.dismiss();
                startActivity(new Intent(BaseActivity.this, MainActivity.class));
                EventBus.getDefault().post(new MenuBean(3));
            }
        });
    }

    public void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("我是标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(this);
    }

    public void show(View view) {
        showMenuPop(this, view);
    }
}
