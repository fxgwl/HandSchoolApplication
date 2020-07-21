package com.example.handschoolapplication.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.MenuBean;
import com.example.handschoolapplication.bean.SchoolInfoBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.MyUtiles;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.SelfDialog;
import com.example.handschoolapplication.view.ShenjiDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.bumptech.glide.Glide.with;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.ll_shenji)
    LinearLayout llShenji;
    @BindView(R.id.ll_help)
    LinearLayout llHelp;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    private TextView tvType;
    private String type;
    private ShenjiDialog selfDialog;
    private String icon;
    private String name;
    private String user_id;
    private SchoolInfoBean.DataBean dataBean;
    private String versionName;
    private String apk_url;
    private String change_state;
    private String version_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvType = (TextView) findViewById(R.id.tv_type);
        tvTitle.setText("设置");
        user_id = (String) SPUtils.get(this, "userId", "");
        type = getIntent().getStringExtra("type");
        icon = getIntent().getStringExtra("icon");
        name = getIntent().getStringExtra("name");
        with(this)
                .load(Internet.BASE_URL + icon)
                .centerCrop()
                .into(ivIcon);
        tvUsername.setText(name);
        switch (type) {
            case "com":
                tvType.setText("机构资料");
                change_state = (String) SPUtils.get(this, "change_state", "");
                break;
            case "per":
                tvType.setText("个人资料");
                break;
        }
        versionName = MyUtiles.getVersionName(this);
        tvVersion.setText("当前版本：" + versionName);
        getUserInfo();
        getApkVersion();
    }

    private void getUserInfo() {

        OkHttpUtils.post()
                .url(Internet.USERINFO)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(SettingsActivity.java:147)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SettingsActivity.java:152)<---->" + response);

                        try {
                            dataBean = new Gson().fromJson(response, SchoolInfoBean.class).getData();
                            with(SettingsActivity.this)
                                    .load(Internet.BASE_URL + dataBean.getHead_photo())
                                    .centerCrop()
                                    .error(R.drawable.morentouxiang)
                                    .into(ivIcon);
                            if (type.equals("com")) {
                                tvUsername.setText(dataBean.getMechanism_name());
                            } else {
                                tvUsername.setText(dataBean.getUser_name());
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getApkVersion() {

        OkHttpUtils
                .post()
                .url(InternetS.APK)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(SettingsActivity.java:93)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SettingsActivity.java:100)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("data");
                            version_num = data.getString("version_num");
                            apk_url = data.getString("apk_url");
                            if (!version_num.equals(versionName)) {
                                tvVersion.setText("有新版本更新");
                                tvVersion.setTextColor(getResources().getColor(R.color.red));
                            } else {
                                tvVersion.setText("当前版本：" + versionName);
                                Toast.makeText(SettingsActivity.this, "暂无版本更新", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    @OnClick({R.id.ll_shenji, R.id.ll_help, R.id.rl_back, R.id.iv_menu, R.id.ll_change_info,
            R.id.ll_change_phone, R.id.ll_changepwd, R.id.ll_about, R.id.btn_unlogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.ll_change_info:
                if (type.equals("per"))
                    startActivity(new Intent(this, MyInformationActivity.class));
                else {
                    /*if (change_state.equals("2")) {
                        startActivity(new Intent(this, AddDataActivity.class));
                    } else if (change_state.equals("0")) {
                        Toast.makeText(this, "审核过后才能编辑资料", Toast.LENGTH_SHORT).show();
                    } else */
                    startActivity(new Intent(this, SchoolInformationActivity.class));
                }
                break;
            //修改手机号
            case R.id.ll_change_phone:
                startActivity(new Intent(SettingsActivity.this, ChangePhoneActivity.class));
                break;
            //修改密码
            case R.id.ll_changepwd:
                String user_phone = (String) SPUtils.get(this, "user_phone", "");
                if (TextUtils.isEmpty(user_phone)) {
                    showNoLoginDialog();
                } else {
                    startActivity(new Intent(SettingsActivity.this, ModifyPwdActivity.class));
                }

                break;
            //关于我们
            case R.id.ll_about:
                startActivity(new Intent(SettingsActivity.this, AboutUsActivity.class).putExtra("type", type));
                break;
            case R.id.btn_unlogin:
                showUnLoginDialog();
                break;
            //版本升级
            case R.id.ll_shenji:
                if (!version_num.equals(versionName)) {
                    showUpdateApk();
                } else {
                    Toast.makeText(SettingsActivity.this, "暂无版本更新", Toast.LENGTH_SHORT).show();
                }
                break;
            //帮助
            case R.id.ll_help:
                startActivity(new Intent(SettingsActivity.this, HelpActivity.class));
                break;
        }
    }

    private void showNoLoginDialog() {
        final SelfDialog selfDialog = new SelfDialog(SettingsActivity.this);

        selfDialog.setMessage("是否绑定手机号?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                startActivity(new Intent(SettingsActivity.this, RegisterPersonActivity.class)
                        .putExtra("flag", "true")
                        .putExtra("type", "0"));
//                finish();
                selfDialog.dismiss();
            }
        });


        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {

                selfDialog.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
    }

    private void showUnLoginDialog() {
        final SelfDialog selfDialog = new SelfDialog(SettingsActivity.this);

        selfDialog.setMessage("是否退出登录?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                SPUtils.clear(SettingsActivity.this);
                EventBus.getDefault().post(new MenuBean(8));
                startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                finish();
                selfDialog.dismiss();
            }
        });


        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {

                selfDialog.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
    }

    private void showUpdateApk() {
        final SelfDialog selfDialog = new SelfDialog(SettingsActivity.this);

        selfDialog.setMessage("是否进行版本更新?");
        selfDialog.setYesOnclickListener("立即更新", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri uri = Uri.parse(Internet.BASE_URL + apk_url);
                intent.setData(uri);
                startActivity(intent);
                selfDialog.dismiss();
            }
        });


        selfDialog.setNoOnclickListener("稍后再说", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {

                selfDialog.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    /**
     * 添加弹出的dialog关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements Dialog.OnDismissListener {


        @Override
        public void onDismiss(DialogInterface dialog) {
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }
    }
}
