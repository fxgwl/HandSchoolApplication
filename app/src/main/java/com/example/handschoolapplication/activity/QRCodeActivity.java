package com.example.handschoolapplication.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.SelfDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import okhttp3.Call;


public class QRCodeActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;
    @BindView(R.id.tv_code_text)
    TextView tvCodeText;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    String text = "";
    private String school_id;
    private String flag;
    private String orderId;
    private String order_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        school_id = (String) SPUtils.get(this, "school_id", "");
        flag = getIntent().getStringExtra("flag");
        order_state = getIntent().getStringExtra("order_state");

//
//        createChineseQRCode(text);
        if (flag.equals("CC")) {
            tvTitle.setText("机构二维码");
            tvCodeText.setText("我的机构二维码");
            btnCommit.setVisibility(View.GONE);
            createChineseQRCodeWithLogo("xt," + school_id);
        } else {
            tvTitle.setText("学习码");
            tvCodeText.setText("我的学习码");
            String learnCode = getIntent().getStringExtra("learnCode");
            btnCommit.setVisibility(View.VISIBLE);
            if (!order_state.equals("1")) {
                btnCommit.setVisibility(View.GONE);
            }
            Log.e("aaa",
                    "(QRCodeActivity.java:57)<---->" + learnCode);
            if (learnCode.contains(",")) {
                orderId = learnCode.split(",")[2];
            }
            createChineseQRCodeWithLogo(learnCode);
        }

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_qrcode;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.btn_commit:
                affirm();
                break;
        }
    }

    /**
     * 机构扫码确认按钮
     */
    private void affirm() {

        Log.e("aaa",
                "(QRCodeActivity.java:93)<---->" + orderId);

        OkHttpUtils.post()
                .url(Internet.AFFIRM_ORDER)
                .addParams("order_id", orderId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(QRCodeActivity.java:93)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(QRCodeActivity.java:100)<---->" + response);

                        if (!TextUtils.isEmpty(response)) {
                            if (response.contains("确认前")) {
                                showDialog();
                            } else if (response.contains("确认后")) {
                                finish();
                            } else {
                                Toast.makeText(QRCodeActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(QRCodeActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void createChineseQRCode(final String text) {
        /*
        这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
        请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
         */
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                return QRCodeEncoder.syncEncodeQRCode(text, BGAQRCodeUtil.dp2px(QRCodeActivity.this, 200));
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    ivQrcode.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(QRCodeActivity.this, "生成中文二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void createChineseQRCodeWithLogo(final String text) {
        /*
        这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
        请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
         */
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap logoBitmap = BitmapFactory.decodeResource(QRCodeActivity.this.getResources(), R.mipmap.logo);
                return QRCodeEncoder.syncEncodeQRCode(text, BGAQRCodeUtil.dp2px(QRCodeActivity.this, 200), Color.parseColor("#000000"), logoBitmap);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    ivQrcode.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(QRCodeActivity.this, "生成带logo的中文二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void showDialog() {
        final SelfDialog selfDialog = new SelfDialog(QRCodeActivity.this);

        selfDialog.setMessage("是否等待学习机构进行确认?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                selfDialog.dismiss();
            }
        });


        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                finish();
                selfDialog.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
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
