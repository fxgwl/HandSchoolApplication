/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.example.handschoolapplication.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.handschoolapplication.MyApplication;
import com.example.handschoolapplication.activity.MainActivity;
import com.example.handschoolapplication.bean.WeChatConfig;
import com.example.handschoolapplication.utils.NetUtils;
import com.example.handschoolapplication.bean.WeixinBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import cn.sharesdk.wechat.utils.WXAppExtendObject;
import cn.sharesdk.wechat.utils.WXMediaMessage;
import cn.sharesdk.wechat.utils.WechatHandlerActivity;
import okhttp3.Call;
import okhttp3.Response;



/** 微信客户端回调activity示例 */
public class WXEntryActivity extends WechatHandlerActivity implements IWXAPIEventHandler {

	// 获取第一步的code后，请求以下链接获取access_token
	private String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	// 获取用户个人信息
	private String GetUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

	private String mycode="";

	/**
	 * 处理微信发出的向第三方应用请求app message
	 * <p>
	 * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
	 * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
	 * 做点其他的事情，包括根本不打开任何页面
	 */
	public void onGetMessageFromWXReq(WXMediaMessage msg) {
		if (msg != null) {
			Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
			startActivity(iLaunchMyself);
		}
	}

	/**
	 * 处理微信向第三方应用发起的消息
	 * <p>
	 * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
	 * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
	 * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
	 * 回调。
	 * <p>
	 * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
	 */
	public void onShowMessageFromWXReq(WXMediaMessage msg) {
		if (msg != null && msg.mediaObject != null
				&& (msg.mediaObject instanceof WXAppExtendObject)) {
			WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
			Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
		}
	}

	private IWXAPI api;
	private static final String APP_ID = "wx433e119bb99f2075";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		api = WXAPIFactory.createWXAPI(this, APP_ID, false);
		api.handleIntent(getIntent(), this);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onReq(BaseReq baseReq) {

	}

	@Override
	public void onResp(BaseResp resp) {
		switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				Log.i("WXTest","onResp OK");
					String code = ((SendAuth.Resp) resp).code;
					Log.i("WXTest>>发送成功","onResp code = "+code);
				/*
				 * 将你前面得到的AppID、AppSecret、code，拼接成URL 获取access_token等等的信息(微信)
				 */
				if(MyApplication.loginkg==0){
					return;
				}
				String get_access_token = getCodeRequest(code);
				Map<String, String> reqBody = new ConcurrentSkipListMap<>();
				NetUtils netUtils = NetUtils.getInstance();
				netUtils.postDataAsynToNet(get_access_token, reqBody,
						new NetUtils.MyNetCall() {
							@Override
							public void success(Call call, Response response)
									throws IOException {

								String responseData = response.body().string();
								parseJSONWithGSON(responseData);

							}

							@Override
							public void failed(Call call, IOException e) {

							}
						});
				finish();
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				Log.d("WXTest","onResp ERR_USER_CANCEL ");
				//发送取消
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				Log.i("WXTest","onResp ERR_AUTH_DENIED");
				//发送被拒绝
				break;
			default:
				Log.i("WXTest","onResp default errCode " + resp.errCode);
				//发送返回
				break;
		}
		finish();
	}

	/**
	 * 通过拼接的用户信息url获取用户信息
	 *
	 * @param user_info_url
	 */
	private void getUserInfo(String user_info_url) {
		Map<String, String> reqBody = new ConcurrentSkipListMap<>();
		NetUtils netUtils = NetUtils.getInstance();
		netUtils.postDataAsynToNet(user_info_url, reqBody,
				new NetUtils.MyNetCall() {
					@Override
					public void success(Call call, Response response) throws IOException{
						String str = response.body().string();
						//parseJSONUser(str);

					}

					@Override
					public void failed(Call call, IOException e) {

					}

				});

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
		finish();
	}

	/**
	 * 获取access_token的URL（微信）
	 *
	 * @param code
	 *            授权时，微信回调给的
	 * @return URL
	 */
	private String getCodeRequest(String code) {
		/*if(mycode.isEmpty()){
			mycode=code;
		}*/
		String result = null;
		GetCodeRequest = GetCodeRequest.replace("APPID",
				urlEnodeUTF8(WeChatConfig.APP_ID));
		GetCodeRequest = GetCodeRequest.replace("SECRET",
				urlEnodeUTF8(WeChatConfig.APP_SECRET));
		GetCodeRequest = GetCodeRequest.replace("CODE", urlEnodeUTF8(code));
		result = GetCodeRequest;
		return result;
	}

	/**
	 * 获取用户个人信息的URL（微信）
	 *
	 * @param access_token
	 *            获取access_token时给的
	 * @param openid
	 *            获取access_token时给的
	 * @return URL
	 */
	private String getUserInfo(String access_token, String openid) {
		String result = null;
		GetUserInfo = GetUserInfo.replace("ACCESS_TOKEN",
				urlEnodeUTF8(access_token));
		GetUserInfo = GetUserInfo.replace("OPENID", urlEnodeUTF8(openid));
		result = GetUserInfo;
		return result;
	}

	private String urlEnodeUTF8(String str) {
		String result = str;
		try {
			result = URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 解析返回的数据
	private void parseJSONWithGSON(String jsonData) {
		// 使用轻量级的Gson解析得到的json
		Gson gson = new Gson();
		WeixinBean appList = gson.fromJson(jsonData,
				new TypeToken<WeixinBean>() {
				}.getType());
		// 控制台输出结果，便于查看
		String str = getUserInfo(appList.getAccess_token(), appList.getOpenid());
		MyApplication.open_id=appList.getOpenid();
//		获取用户信息
		getUserInfo(str);
	}

	// 解析用户信息
	private void parseJSONUser(String jsonData) {
		// 使用轻量级的Gson解析得到的json
		Gson gson = new Gson();
		WeixinBean appList = gson.fromJson(jsonData,
				new TypeToken<WeixinBean>() {
				}.getType());
		// 控制台输出结果，便于查看
		Intent intent=new Intent(WXEntryActivity.this,MainActivity.class);
		String str=appList.getSex()==1?"   性别:男" : "   性别:女";

		intent.putExtra("username", "微信昵称:"+appList.getNickname());
		intent.putExtra("sex", str);
		startActivity(intent);
}
}
