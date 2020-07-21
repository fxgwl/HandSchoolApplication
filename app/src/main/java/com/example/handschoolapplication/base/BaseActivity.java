package com.example.handschoolapplication.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.blankj.utilcode.utils.ScreenUtils;
import com.example.handschoolapplication.MyApplication;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.AddDataActivity;
import com.example.handschoolapplication.activity.HelpActivity;
import com.example.handschoolapplication.activity.LoginActivity;
import com.example.handschoolapplication.activity.MainActivity;
import com.example.handschoolapplication.bean.AddDataInfo;
import com.example.handschoolapplication.bean.MenuBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.CommonPopupWindow;
import com.example.handschoolapplication.view.MenuPopupWindow;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.sharesdk.alipay.friends.Alipay;
import cn.sharesdk.alipay.moments.AlipayMoments;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.renren.Renren;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import okhttp3.Call;

import static com.bumptech.glide.Glide.with;

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
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        ScreenUtils.hideStatusBar(this);
        setContentView(getContentViewId());
        MyApplication.getInstance().addActivity(this);
        bind = ButterKnife.bind(this);
        Log.e(getClass().getSimpleName(), "--->onCreate: ");

        //getInfo();
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
        MenuPopupWindow myPopupWindow = new MenuPopupWindow(context, view);
        myPopupWindow.setHeight(500);
        myPopupWindow.showAtLocation(view, Gravity.BOTTOM, 10, 0);
    }

    //向下弹出
    public void showMenuPop(final Context context, View view) {
        View view2 = View.inflate(context, R.layout.menu, null);
        LinearLayout news = (LinearLayout) view2.findViewById(R.id.ll_news);
        LinearLayout home = (LinearLayout) view2.findViewById(R.id.ll_home);
        LinearLayout help = (LinearLayout) view2.findViewById(R.id.ll_help);
        LinearLayout wode = (LinearLayout) view2.findViewById(R.id.ll_wode);
        final MenuPopupWindow mppWinow = new MenuPopupWindow(this, view2);
        mppWinow.setWidth(400);
        mppWinow.showAsDropDown(view,120,0);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState, outPersistentState);
    }

//    public void showShare(final String title, final String text, final String imageUrl, final String url) {
//        final OnekeyShare oks = new OnekeyShare();
//        oks.addHiddenPlatform(WechatFavorite.NAME);
//        oks.addHiddenPlatform(TencentWeibo.NAME);
//        oks.addHiddenPlatform(QZone.NAME);
//        oks.addHiddenPlatform(Renren.NAME);
//        oks.addHiddenPlatform(Alipay.NAME);
//        oks.addHiddenPlatform(AlipayMoments.NAME);
//        oks.addHiddenPlatform(ShortMessage.NAME);
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//
//        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
//            @Override
//            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
//                if (platform.getName().equals("SinaWeibo")){
//                    // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
//                    //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
//                    // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//                    paramsToShare.setTitle(title);
//                    // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//                    paramsToShare.setTitleUrl(url);
//                    // text是分享文本，所有平台都需要这个字段
//                    paramsToShare.setText(text+"http://m.xczsss.com/PrivateSchool/head/tel_zsss/load_page.html");
//                    // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
////        oks.setImagePath("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");//确保SDcard下面存在此张图片
//                    paramsToShare.setImageUrl(imageUrl);//确保SDcard下面存在此张图片
//                    // url仅在微信（包括好友和朋友圈）中使用
//                    paramsToShare.setUrl(url);
//                    // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//                    paramsToShare.setComment("我是测试评论文本");
//                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
//                    // site是分享此内容的网站名称，仅在QQ空间使用
//                    paramsToShare.setSite(getString(R.string.app_name));
//                    // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//                    paramsToShare.setSiteUrl(url);
//                }else {
//                    // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
//                    //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
//                    // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//                    paramsToShare.setTitle(title);
//                    // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//                    paramsToShare.setTitleUrl(url);
//                    // text是分享文本，所有平台都需要这个字段
//                    paramsToShare.setText(text);
//                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
//                    // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
////        oks.setImagePath("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");//确保SDcard下面存在此张图片
//                    paramsToShare.setImageUrl(imageUrl);//确保SDcard下面存在此张图片
//                    // url仅在微信（包括好友和朋友圈）中使用
//                    paramsToShare.setUrl(url);
//                    // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//                    paramsToShare.setComment("我是测试评论文本");
//                    // site是分享此内容的网站名称，仅在QQ空间使用
//                    paramsToShare.setSite(getString(R.string.app_name));
//                    // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//                    paramsToShare.setSiteUrl(url);
//                }
//            }
//        });
//
//
//        // 启动分享GUI
//        oks.show(BaseActivity.this);
//
//
//
//    }

    public void show(View view) {
        showMenuPop(this, view);
    }

    public void showShare(final String title, final String text, final String imageUrl, final String url) {
        OnekeyShare oks = new OnekeyShare();
        /*oks.addHiddenPlatform(QQ.NAME);
        oks.setImageData();
        oks.setSilent(true);*/
        oks.addHiddenPlatform(WechatFavorite.NAME);
        oks.addHiddenPlatform(TencentWeibo.NAME);
        oks.addHiddenPlatform(QZone.NAME);
        oks.addHiddenPlatform(Renren.NAME);
        oks.addHiddenPlatform(Alipay.NAME);
        oks.addHiddenPlatform(AlipayMoments.NAME);
        oks.addHiddenPlatform(ShortMessage.NAME);
        oks.disableSSOWhenAuthorize();
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, cn.sharesdk.framework.Platform.ShareParams paramsToShare) {
                if ("SinaWeibo".equals(platform.getName())) {
                    paramsToShare.setText(url + text + "http://m.xczsss.com/PrivateSchool/head/tel_zsss/load_page.html");
                    paramsToShare.setImageUrl(imageUrl);
                    /*paramsToShare.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());*/
                   /* paramsToShare.setUrl("http://sharesdk.cn");*/
                }
                if ("Wechat".equals(platform.getName())) {
                    paramsToShare.setTitle(title);
                    paramsToShare.setUrl(url);
                    paramsToShare.setText(text);
                    /*paramsToShare.setWxUserName("");
                    paramsToShare.setW*/
                    /*Bitmap imageData = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                    paramsToShare.setImageData(imageData);*/
                    /*paramsToShare.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png");*/
                    paramsToShare.setImageUrl(imageUrl);
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                    Log.e("ShareSDK", paramsToShare.toMap().toString());
                }
                if ("WechatMoments".equals(platform.getName())) {
                    paramsToShare.setTitle(title);
                    paramsToShare.setUrl(url);
                    paramsToShare.setText(text);
                    paramsToShare.setImageUrl(imageUrl);
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                }
                if ("QQ".equals(platform.getName())) {
                    paramsToShare.setTitle(title);
                    paramsToShare.setTitleUrl(url);
                    paramsToShare.setText(text);
                    paramsToShare.setImageUrl(imageUrl);
                }
                if ("Facebook".equals(platform.getName())) {
                    paramsToShare.setText("我是共用的参数，这几个平台都有text参数要求，提取出来啦");
                    paramsToShare.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png");
                }
                if ("QZone".equals(platform.getName())) {
                    //QQ空间您自己写了
                }
                if ("Alipay".equals(platform.getName())) {
                    paramsToShare.setTitle("标题");
                    paramsToShare.setUrl("http://sharesdk.cn");
                    paramsToShare.setText("我是共用的参数，这几个平台都有text参数要求，提取出来啦");
                    paramsToShare.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png");
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                }
            }
        });

        // 启动分享GUI
        oks.show(BaseActivity.this);

    }


    private void getInfo() {
        String user_id = (String) SPUtils.get(this, "userId", "");
        if(user_id.equals("")){
            return;
        }
        OkHttpUtils.post()
                .url(Internet.EDIT_AND_ADD)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(AddDataActivity.java:171)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AddDataActivity.java:177)<---->" + response);
                        if (TextUtils.isEmpty(response) || response.contains("没有信息")) {
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int result = jsonObject.getInt("result");
                                if (result == 0) {
                                    AddDataInfo addDataInfo = new Gson().fromJson(response, AddDataInfo.class);
                                    /*20200115 fxg 改 start  0:下架，1是上架*/
                                    if(addDataInfo.getData().getChange_state().equals("0")){
                                        SPUtils.clear(getApplicationContext());
                                        EventBus.getDefault().post(new MenuBean(8));
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        return;
                                    } /*20200115 fxg 改 end*/
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}
