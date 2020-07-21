package com.example.handschoolapplication.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.MyApplication;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.MenuBean;
import com.example.handschoolapplication.bean.SchoolBean;
import com.example.handschoolapplication.bean.ThreeUserBean;
import com.example.handschoolapplication.bean.UserBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.ListDataSave;
import com.example.handschoolapplication.utils.MyUtiles;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.CommonPopupWindow;
import com.example.handschoolapplication.view.SelfDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;


public class LoginActivity extends BaseActivity implements PlatformActionListener, CommonPopupWindow.ViewInterface, AdapterView.OnItemClickListener {

    /**
     * /**
     * TagAliasCallback类是JPush开发包jar中的类，用于
     * 设置别名和标签的回调接口，成功与否都会回调该方法
     * 同时给定回调的代码。如果code=0,说明别名设置成功。
     * /**
     * 6001   无效的设置，tag/alias 不应参数都为 null
     * 6002   设置超时    建议重试
     * 6003   alias 字符串不合法    有效的别名、标签组成：字母（区分大小写）、数字、下划线、汉字。
     * 6004   alias超长。最多 40个字节    中文 UTF-8 是 3 个字节
     * 6005   某一个 tag 字符串不合法  有效的别名、标签组成：字母（区分大小写）、数字、下划线、汉字。
     * 6006   某一个 tag 超长。一个 tag 最多 40个字节  中文 UTF-8 是 3 个字节
     * 6007   tags 数量超出限制。最多 100个 这是一台设备的限制。一个应用全局的标签数量无限制。
     * 6008   tag/alias 超出总长度限制。总长度最多 1K 字节
     * 6011   10s内设置tag或alias大于3次 短时间内操作过于频繁
     **/
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    //这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    //UserUtils.saveTagAlias(getHoldingActivity(), true);
                    logs = "Set tag and alias success极光推送别名设置成功";
                    Log.e("TAG", logs);
                    break;
                case 6002:
                    //极低的可能设置失败 我设置过几百回 出现3次失败 不放心的话可以失败后继续调用上面那个方面 重连3次即可 记得return 不要进入死循环了...
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试";
                    Log.e("TAG", logs);
                    break;
                default:
                    logs = "极光推送设置失败，Failed with errorCode = " + code;
                    Log.e("TAG", logs);
                    break;
            }
        }
    };
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.iv_is_see)
    ImageView ivIsSee;
    List<String> mlist = new ArrayList<>();
    OnekeyShare oks = new OnekeyShare();
    private CommonPopupWindow historyUserPopupwindow;
    private ListDataSave dataSave;
    private InputMethodManager inputMethodManager;
    private boolean pwdVisible = false;//密码可见状态  默认不可见
    private double exitTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        dataSave = new ListDataSave(this, "login");
        etPhoneNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dataSave.getDataList("history");
                }
            }
        });

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (null != intent.getStringExtra("from")) {
            Log.e("aaa",
                    "(LoginActivity.java:93)<---->" + intent.getStringExtra("from"));
        } else {
            Log.e("aaa",
                    "(LoginActivity.java:93)<--空空空空-->");
        }

        if (null != intent.getStringExtra("from") && !TextUtils.isEmpty(intent.getStringExtra("from"))) {
            if ("register".equals(intent.getStringExtra("from"))) {
                String phone = intent.getStringExtra("phone");
                String password = intent.getStringExtra("password");
                login(phone, password);
            }
        }
    }
    private void login(final String phone, String pwd) {

//        if (!MyUtiles.isPhone(phone)) {
//            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
//           return;
//        }
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "输入有误！", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("user", phone);
        params.put("user_password", pwd);

        OkHttpUtils.post()
                .url(Internet.LOGIN)
                .params(params)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(LoginActivity.java:94)" + e.getMessage());
                        Toast.makeText(LoginActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(LoginActivity.java:100)" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                            List<String> history = dataSave.getDataList("history");
                            /*20200115 fxg 改 start*/
                            if(jsonObject.getInt("result")==0){
                            }else{

                                return;
                            } /*20200115 fxg 改 end*/

                            boolean flag = false;
                            for (int i = 0; i < history.size(); i++) {
                                if (history.get(i).equals(phone)) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (!flag) {
                                history.add(phone);
                                dataSave.setDataList("history", history);
                            }

                            for (Object aaa : (dataSave.getDataList("history"))) {
                                Log.e("aaa", "(LoginActivity.java:215)<--aaaaaaa-->" + (String) aaa);
                            }
                            JSONObject data = jsonObject.getJSONObject("data");
                            if (data.getString("user_type").equals("0")) {
                                UserBean userBean = new Gson().fromJson(data.toString(), UserBean.class);
                                SPUtils.put(LoginActivity.this, "userId", userBean.getUser_id());
                                SPUtils.put(LoginActivity.this, "user_type", userBean.getUser_type());
                                SPUtils.put(LoginActivity.this, "user_phone", userBean.getUser_phone());
                                SPUtils.put(LoginActivity.this, "isLogin", true);
                                SPUtils.put(LoginActivity.this, "flag", "0");
                                setTagAndAlias(userBean.getUser_id());
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                            if (data.getString("user_type").equals("1")) {
                                SchoolBean schoolBean = new Gson().fromJson(data.toString(), SchoolBean.class);
                                Log.e("aaa",
                                        "(LoginActivity.java:242)<---->" + schoolBean.getChange_state());
                                SPUtils.put(LoginActivity.this, "change_state", schoolBean.getChange_state());
                                SPUtils.put(LoginActivity.this, "userId", schoolBean.getUser_id());
                                SPUtils.put(LoginActivity.this, "school_id", schoolBean.getSchool_id());
                                SPUtils.put(LoginActivity.this, "user_type", schoolBean.getUser_type());
                                SPUtils.put(LoginActivity.this, "user_phone", schoolBean.getUser_phone());
                                SPUtils.put(LoginActivity.this, "isLogin", true);
                                SPUtils.put(LoginActivity.this, "flag", "1");
                                setTagAndAlias(schoolBean.getUser_id());
                                //change_state  0待审核  1审核通过 2驳回审核
                                /*if (schoolBean.getChange_state().equals("0") || schoolBean.getChange_state().equals("1")) {
                                    setTagAndAlias(schoolBean.getUser_id());
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else {
                                    startActivity(new Intent(LoginActivity.this, AddDataActivity.class));
                                }*/
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 设置标签与别名
     */
    private void setTagAndAlias(String alias) {
        /**
         *这里设置了别名，在这里获取的用户登录的信息
         *并且此时已经获取了用户的userId,然后就可以用用户的userId来设置别名了
         **/
        //false状态为未设置标签与别名成功
        //if (UserUtils.getTagAlias(getHoldingActivity()) == false) {
        Set<String> tags = new HashSet<String>();
        //这里可以设置你要推送的人，一般是用户uid 不为空在设置进去 可同时添加多个
        if (!TextUtils.isEmpty(alias)) {
            tags.add(alias);//设置tag
        }
        //上下文、别名【Sting行】、标签【Set型】、回调
        JPushInterface.setAliasAndTags(this, alias, tags,
                mAliasCallback);
        // }
    }

    @OnClick({R.id.rl_back, R.id.btn_login, R.id.iv_wechat_login, R.id.iv_weibo_login, R.id.iv_qq_login,
            R.id.tv_forget_pwd, R.id.tv_register, R.id.iv_history_user, R.id.iv_is_see})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.iv_history_user:
                Log.e("aaa", "(LoginActivity.java:290)<---->" + "点击点击点击");
                View view1 = getWindow().peekDecorView();
                if (view1 != null) {
                    InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(view1.getWindowToken(), 0);
                }
                if (true) {
                    Log.e("aaa","(LoginActivity.java:297)<---->" + "不为空不为空");
                    showHistoryUser(view);
                }
                break;
            case R.id.btn_login:
                String phone = etPhoneNum.getText().toString().trim();
                String pwd = etPassword.getText().toString().trim();
                if (!MyUtiles.isPhone(phone)) {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                login(phone, pwd);
                break;
            case R.id.iv_wechat_login:
                wechatLogin();
                break;
            case R.id.iv_weibo_login:
                sinaLogin();
                break;
            case R.id.iv_qq_login:
                qqLogin();
                break;
            case R.id.tv_forget_pwd:
                startActivity(new Intent(LoginActivity.this, ForgetPwdActivity.class));
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterToActivity.class));
                break;
            case R.id.iv_is_see:
                if (pwdVisible) {
                    pwdVisible = false;
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);//设置密码可见，如果只设置TYPE_TEXT_VARIATION_PASSWORD则无效
                    ivIsSee.setImageResource(R.drawable.mimabukejian);
                } else {
                    pwdVisible = true;
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//设置密码不可见
                    ivIsSee.setImageResource(R.drawable.mimakejian);
                }
                break;
        }
    }

    private boolean isSoftShowing() {
        //获取当前屏幕内容的高度
        int screenHeight = getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        Log.e("aaa","(LoginActivity.java:346)<---->" + (screenHeight - rect.bottom != 0));
        return screenHeight - rect.bottom != 0;
    }

    //向下弹出
    @SuppressLint("WrongConstant")
    public void showHistoryUser(View view) {
        if (historyUserPopupwindow != null && historyUserPopupwindow.isShowing()) return;
        historyUserPopupwindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.login_history)
                .setWidthAndHeight(500, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimDown)
                .setViewOnclickListener(this)
                .create();
        historyUserPopupwindow.setSoftInputMode(CommonPopupWindow.INPUT_METHOD_NEEDED);
        historyUserPopupwindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        historyUserPopupwindow.showAsDropDown(view, 0, 0);
    }

    /**
     * 三方登录
     */
    private void wechatLogin() {
        MyApplication.loginkg=0;
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.SSOSetting(false);  //设置false表示使用SSO授权方式
        if (!wechat.isClientValid()) {
            Toast.makeText(LoginActivity.this, "微信未安装,请先安装微信", Toast.LENGTH_LONG).show();
        }
        wechat.setPlatformActionListener(this); // 设置分享事件回调
//        wechat.showUser(null);//授权并获取用户信息
        wechat.authorize();
    }

    private void sinaLogin() {
        Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
        sina.SSOSetting(false);  //设置false表示使用SSO授权方式
        if (!sina.isClientValid()) {
            Toast.makeText(LoginActivity.this, "微博未安装,请先安装微博", Toast.LENGTH_LONG).show();
        }
        sina.setPlatformActionListener(this); // 设置分享事件回调
//        wechat.showUser(null);//授权并获取用户信息
        sina.authorize();
    }

    private void qqLogin() {
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.SSOSetting(false);  //设置false表示使用SSO授权方式
        if (!qq.isClientValid()) {
            Toast.makeText(LoginActivity.this, "QQ未安装,请先安装QQ", Toast.LENGTH_LONG).show();
        }
        qq.setPlatformActionListener(this); // 设置分享事件回调
//        wechat.showUser(null);//授权并获取用户信息
        qq.authorize();
    }

    //微信登录的回调
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        String name = platform.getName();
        String userIcon = platform.getDb().getUserIcon();
        String userName = platform.getDb().getUserName();
//        String userName = platform.getDb().getUserIcon();
        String userId = platform.getDb().getUserId();


        String s = MyUtiles.filterCharToNormal(userName);
        Log.e("aaa",
                "(LoginActivity.java:356)<--过滤后的字符串-->" + s);

        Log.e("aaa",
                "(LoginActivity.java:262)  name=== " + name + "   userIcon====" + userIcon + "   userName====" + userName + "   userId===" + userId);
        toLogin(name, userIcon, s, userId);

    }

    private void toLogin(String name, String userIcon, String userName, String userId) {

        String url = "";
        String user_id = "";
        switch (name) {
            case "Wechat":
                url = Internet.WECHAT_LOGIN;
                user_id = "weixin_id";
                userName = "wechat_" + userName;
                break;
            case "QQ":
                url = Internet.QQ_LOGIN;
                user_id = "qq_id";
                userName = "qq_" + userName;
                break;
            case "SinaWeibo":
                url = Internet.SINA_WEIBO_LOGIN;
                user_id = "weibo_id";
                userName = "weibo_" + userName;
                break;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put(user_id, userId);
        params.put("user_name", userName);
        params.put("urlStr", userIcon);

        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(LoginActivity.java:295)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(LoginActivity.java:301)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int result = jsonObject.getInt("result");
                            ThreeUserBean userBean = new Gson().fromJson(response, ThreeUserBean.class);
                            String user_id = userBean.getData().getUser_id();
                            if (result == 1) {
                                setTagAndAlias(user_id);
                                startActivity(new Intent(LoginActivity.this, RegisterPersonActivity.class)
                                        .putExtra("flag", "three")
                                        .putExtra("user_id", user_id)
                                        .putExtra("type", "0"));

                            } else {
                                Log.e("aaa",
                                        "(LoginActivity.java:417)<--三方登陆-->" + user_id);
                                SPUtils.put(LoginActivity.this, "userId", user_id);
                                SPUtils.put(LoginActivity.this, "user_type", userBean.getData().getUser_type());
                                SPUtils.put(LoginActivity.this, "isLogin", true);
                                SPUtils.put(LoginActivity.this, "flag", "0");
                                SPUtils.put(LoginActivity.this, "user_phone", userBean.getData().getUser_phone());
                                setTagAndAlias(user_id);
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        } catch (JSONException e) {


                        }
                    }
                });

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }

    //下拉监听
    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.login_history:
                mlist.clear();
                List<String> history = dataSave.getDataList("history");
                mlist.addAll(history);
                ListView listView = (ListView) view.findViewById(R.id.lv_history);
                listView.setAdapter(new MyAdapter());
                listView.setOnItemClickListener(this);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        etPhoneNum.setText(mlist.get(position));
        historyUserPopupwindow.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class MyAdapter extends BaseAdapter {
        int size = 0;

        @Override
        public int getCount() {
            if (mlist != null) {
                size = mlist.size();
            }
            return size;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(LoginActivity.this, R.layout.item_history_user_lv, null);
                holder.tvUserPhone = (TextView) convertView.findViewById(R.id.tv_user_phone);
                holder.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvUserPhone.setText(mlist.get(position));
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mlist.remove(position);
                    dataSave.setDataList("history", mlist);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tvUserPhone;
            ImageView ivDelete;
        }

    }

}
