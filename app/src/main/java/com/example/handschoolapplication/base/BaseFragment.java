package com.example.handschoolapplication.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.handschoolapplication.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.sharesdk.alipay.friends.Alipay;
import cn.sharesdk.alipay.moments.AlipayMoments;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.renren.Renren;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.favorite.WechatFavorite;

/**
 * Created by Administrator on 2017/7/22.
 */

public abstract class BaseFragment extends Fragment {

    protected FragmentActivity activity;
    protected View mRootView;
    private Unbinder bind;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getContentViewId() != 0) {
            mRootView = inflater.inflate(getContentViewId(), null);
        } else {
            mRootView = super.onCreateView(inflater, container, savedInstanceState);
        }
        bind = ButterKnife.bind(this, mRootView);
        Log.e(this.getClass().getName(), "--->onCreateView");
        initData(getArguments());
        return mRootView;
    }

    public abstract int getContentViewId();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    /**
     * 初始化数据
     *
     * @param arguments 接收到的从其他地方传递过来的参数
     */
    public void initData(Bundle arguments){};

    @Override
    public void onResume() {
        super.onResume();
        Log.e(this.getClass().getName(),"--->onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(this.getClass().getName(),"--->onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
        Log.e(this.getClass().getName(),"--->onDestroy");
    }

    public void showShare(String title, String text, String imageUrl, String url) {
        OnekeyShare oks = new OnekeyShare();
        oks.addHiddenPlatform(WechatFavorite.NAME);
        oks.addHiddenPlatform(TencentWeibo.NAME);
        oks.addHiddenPlatform(QZone.NAME);
        oks.addHiddenPlatform(Renren.NAME);
        oks.addHiddenPlatform(Alipay.NAME);
        oks.addHiddenPlatform(AlipayMoments.NAME);
        oks.addHiddenPlatform(ShortMessage.NAME);
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(text);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(getActivity());


    }
}
