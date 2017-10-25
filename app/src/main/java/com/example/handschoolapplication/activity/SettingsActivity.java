package com.example.handschoolapplication.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.MenuBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.ShenjiDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.ll_shenji)
    LinearLayout llShenji;
    @BindView(R.id.ll_help)
    LinearLayout llHelp;

    private TextView tvType;
    private String type;
    private ShenjiDialog selfDialog;
    private String icon;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvType = (TextView) findViewById(R.id.tv_type);
        tvTitle.setText("设置");
        ivMenu.setVisibility(View.VISIBLE);
        type = getIntent().getStringExtra("type");
        icon = getIntent().getStringExtra("icon");
        name = getIntent().getStringExtra("name");
        Glide.with(this)
                .load(Internet.BASE_URL + icon)
                .centerCrop()
                .into(ivIcon);
        tvUsername.setText(name);
        switch (type) {
            case "com":
                tvType.setText("学堂资料");
                break;
            case "per":
                tvType.setText("个人资料");
                break;
        }


    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_settings;
    }

    @OnClick({R.id.ll_shenji, R.id.ll_help, R.id.rl_back, R.id.iv_menu, R.id.ll_change_info, R.id.ll_change_phone, R.id.ll_changepwd, R.id.ll_about, R.id.btn_unlogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                show(view);
                break;
            case R.id.ll_change_info:
                if (type.equals("per"))
                    startActivity(new Intent(this, MyInformationActivity.class));
                else startActivity(new Intent(this, SchoolInformationActivity.class));
                break;
            //修改手机号
            case R.id.ll_change_phone:
                startActivity(new Intent(SettingsActivity.this, ChangePhoneActivity.class));
                break;
            //修改密码
            case R.id.ll_changepwd:
                startActivity(new Intent(SettingsActivity.this, ModifyPwdActivity.class));
                break;
            //关于我们
            case R.id.ll_about:
                startActivity(new Intent(SettingsActivity.this, AboutUsActivity.class));
                break;
            case R.id.btn_unlogin:
                SPUtils.clear(this);
                EventBus.getDefault().post(new MenuBean(8));
                startActivity(new Intent(this, LoginActivity.class));
                break;
            //版本升级
            case R.id.ll_shenji:
                selfDialog = new ShenjiDialog(SettingsActivity.this);

                selfDialog.setYesOnclickListener("立即更新", new ShenjiDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {


                        selfDialog.dismiss();
                    }
                });
                selfDialog.setNoOnclickListener("稍后再说", new ShenjiDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {

                        selfDialog.dismiss();
                    }
                });
                backgroundAlpha(0.4f);
                selfDialog.setOnDismissListener(new poponDismissListener());
                selfDialog.show();
                break;
            //帮助
            case R.id.ll_help:
                startActivity(new Intent(SettingsActivity.this, HelpActivity.class));
                break;
        }
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

}
