package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.SchoolBean;
import com.example.handschoolapplication.bean.UserBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.ListDataSave;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.CommonPopupWindow;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;

public class LoginActivity extends BaseActivity implements PlatformActionListener, CommonPopupWindow.ViewInterface, AdapterView.OnItemClickListener {

    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_password)
    EditText etPassword;
    List<String> mlist = new ArrayList<>();
    private CommonPopupWindow historyUserPopupwindow;
    private ListDataSave dataSave;
    OnekeyShare oks = new OnekeyShare();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String user_id = (String) SPUtils.get(this, "userId", "");
        String user_type = (String) SPUtils.get(this, "user_type", "");
        dataSave = new ListDataSave(this, "login");
//        //登录历史
//        if (!TextUtils.isEmpty(user_id)) {
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.putExtra("flag", user_type);
//            startActivity(intent);
//            finish();
//        }
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

    @OnClick({R.id.rl_back, R.id.btn_login, R.id.iv_wechat_login, R.id.iv_weibo_login, R.id.iv_qq_login,
            R.id.tv_forget_pwd, R.id.tv_register, R.id.iv_history_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                break;
            case R.id.iv_history_user:

                showHistoryUser(view);
                break;
            case R.id.btn_login:
                String phone = etPhoneNum.getText().toString().trim();
                String pwd = etPassword.getText().toString().trim();
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
        }
    }


    /**
     * 三方登录
     */
    private void wechatLogin() {
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.SSOSetting(false);  //设置false表示使用SSO授权方式
        if (!wechat.isClientValid()) {
            Toast.makeText(LoginActivity.this, "微信未安装,请先安装微信", Toast.LENGTH_LONG).show();
        }
        wechat.setPlatformActionListener(this); // 设置分享事件回调
//        wechat.showUser(null);//授权并获取用户信息
        wechat.authorize();
    }

    private void qqLogin() {
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.SSOSetting(false);  //设置false表示使用SSO授权方式
        if (!qq.isClientValid()) {
            Toast.makeText(LoginActivity.this, "QQ未安装,请先安装微信", Toast.LENGTH_LONG).show();
        }
        qq.setPlatformActionListener(this); // 设置分享事件回调
//        wechat.showUser(null);//授权并获取用户信息
        qq.authorize();
    }

    private void sinaLogin() {
        Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
        sina.SSOSetting(false);  //设置false表示使用SSO授权方式
        if (!sina.isClientValid()) {
            Toast.makeText(LoginActivity.this, "QQ未安装,请先安装微信", Toast.LENGTH_LONG).show();
        }
        sina.setPlatformActionListener(this); // 设置分享事件回调
//        wechat.showUser(null);//授权并获取用户信息
        sina.authorize();
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
        params.put("user_phone", phone);
        params.put("user_pwd", pwd);

        OkHttpUtils.post()
                .url(Internet.LOGIN)
                .params(params)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(LoginActivity.java:94)" + e.getMessage());
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
                            JSONObject data = jsonObject.getJSONObject("data");
                            if (data.getString("user_type").equals("0")) {
                                UserBean userBean = new Gson().fromJson(data.toString(), UserBean.class);
                                SPUtils.put(LoginActivity.this, "userId", userBean.getUser_id());
                                SPUtils.put(LoginActivity.this, "user_type", userBean.getUser_type());
                                SPUtils.put(LoginActivity.this, "user_phone", userBean.getUser_phone());
                                SPUtils.put(LoginActivity.this, "isLogin", true);
                                SPUtils.put(LoginActivity.this, "flag", "0");
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                            if (data.getString("user_type").equals("1")) {
                                SchoolBean schoolBean = new Gson().fromJson(data.toString(), SchoolBean.class);
                                SPUtils.put(LoginActivity.this, "userId", schoolBean.getUser_id());
                                SPUtils.put(LoginActivity.this, "school_id", schoolBean.getSchool_id());
                                SPUtils.put(LoginActivity.this, "user_type", schoolBean.getUser_type());
                                SPUtils.put(LoginActivity.this, "user_phone", schoolBean.getUser_phone());
                                SPUtils.put(LoginActivity.this, "isLogin", true);
                                SPUtils.put(LoginActivity.this, "flag", "1");
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    //向下弹出
    public void showHistoryUser(View view) {
        if (historyUserPopupwindow != null && historyUserPopupwindow.isShowing()) return;
        historyUserPopupwindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.login_history)
                .setWidthAndHeight(500, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimDown)
                .setViewOnclickListener(this)
                .create();
        historyUserPopupwindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        historyUserPopupwindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        historyUserPopupwindow.showAsDropDown(view);
    }


    //微信登录的回调
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        String name = platform.getName();
        String userIcon = platform.getDb().getUserIcon();
        String userName = platform.getDb().getUserName();
        String userId = platform.getDb().getUserId();

        toLogin(name, userIcon, userName, userId);

    }

    private void toLogin(String name, String userIcon, String userName, String userId) {

        String url = "";
        String user_id = "";
        switch (name) {
            case "Wechat":
                url = Internet.WECHAT_LOGIN;
                user_id = "weixin_id";
                break;
            case "QQ":
                url = Internet.QQ_LOGIN;
                user_id = "qq_id";
                break;
            case "":
                url = Internet.SINA_WEIBO_LOGIN;
                user_id = "weibo_id";
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
