package com.example.handschoolapplication.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;


public class QRCodeActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;

    String text="";
    private String school_id;
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        school_id = (String) SPUtils.get(this, "school_id", "");
        flag = getIntent().getStringExtra("flag");

//        createChineseQRCode(text);
        if (flag.equals("CC")){
            tvTitle.setText("学堂二维码");
            createChineseQRCodeWithLogo("xt,"+ school_id);
        }
        else {
            tvTitle.setText("学习码");
            String learnCode = getIntent().getStringExtra("learnCode");
            createChineseQRCode(learnCode);
        }

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_qrcode;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
        }
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

}
