package com.example.handschoolapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import okhttp3.Call;


public class ScanQRCodeActivity extends BaseActivity implements QRCodeView.Delegate { // 实现相关接口
    private static final String TAG = ScanQRCodeActivity.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private QRCodeView mQRCodeView;
    //区分扫描二维码是干什么的
    private String flag;//0 去学堂
    private String user_type;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        tvTitle.setText("二维码扫描");
        user_type = (String) SPUtils.get(this, "user_type", "");
        flag = getIntent().getStringExtra("flag");

        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        mQRCodeView.startSpot();
        mQRCodeView.changeToScanQRCodeStyle();
        mQRCodeView.setDelegate(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_custom_scan;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);

        mQRCodeView.showScanRect();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    //震动
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "result:" + result);
        vibrate();
        if ("0".equals(user_type)) {
            String[] split = result.split(",");
            if ("xt".equals(split[0])) {
                startActivity(new Intent(this, ClassActivity.class).putExtra("school_id", split[1]));
                ScanQRCodeActivity.this.finish();
            } else {
                Toast.makeText(this, "扫描结果：" + result, Toast.LENGTH_SHORT).show();
                ScanQRCodeActivity.this.finish();
            }
        } else {
            String[] split = result.split(",");
            if ("gr".equals(split[0])) {
                sign(split[1], split[2]);
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                ScanQRCodeActivity.this.finish();
            }
        }
        mQRCodeView.stopSpot();
        ScanQRCodeActivity.this.finish();
    }

    private void sign(String learnCode, String orderId) {
        OkHttpUtils.post()
                .url(InternetS.SCAN_ORDER)
                .addParams("study_num", (learnCode + "," + orderId))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ScanQRCodeActivity.java:118)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ScanQRCodeActivity.java:124)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int result = jsonObject.getInt("result");
                            if (result == 0) {
                                ScanQRCodeActivity.this.finish();
                                Toast.makeText(ScanQRCodeActivity.this, "确认成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ScanQRCodeActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.start_spot:
//                mQRCodeView.startSpot();
//                break;
//            case R.id.stop_spot:
//                mQRCodeView.stopSpot();
//                break;
//            case R.id.start_spot_showrect:
//                mQRCodeView.startSpotAndShowRect();
//                break;
//            case R.id.stop_spot_hiddenrect:
//                mQRCodeView.stopSpotAndHiddenRect();
//                break;
//            case R.id.show_rect:
//                mQRCodeView.showScanRect();
//                break;
//            case R.id.hidden_rect:
//                mQRCodeView.hiddenScanRect();
//                break;
//            case R.id.start_preview:
//                mQRCodeView.startCamera();
//                break;
//            case R.id.stop_preview:
//                mQRCodeView.stopCamera();
//                break;
//            case R.id.open_flashlight:
//                mQRCodeView.openFlashlight();
//                break;
//            case R.id.close_flashlight:
//                mQRCodeView.closeFlashlight();
//                break;
//            case R.id.scan_barcode:
//                mQRCodeView.changeToScanBarcodeStyle();
//                break;
//            case R.id.scan_qrcode:
//                mQRCodeView.changeToScanQRCodeStyle();
//                break;
//            case R.id.choose_qrcde_from_gallery:
//                /*
//                从相册选取二维码图片，这里为了方便演示，使用的是
//                https://github.com/bingoogolapple/BGAPhotoPicker-Android
//                这个库来从图库中选择二维码图片，这个库不是必须的，你也可以通过自己的方式从图库中选择图片
//                 */
//                startActivityForResult(BGAPhotoPickerActivity.newIntent(this, null, 1, null, false), REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
//                break;
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mQRCodeView.showScanRect();

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
            final String picturePath = BGAPhotoPickerActivity.getSelectedImages(data).get(0);

            /*
            这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
            请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
             */
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    return QRCodeDecoder.syncDecodeQRCode(picturePath);
                }

                @Override
                protected void onPostExecute(String result) {
                    if (TextUtils.isEmpty(result)) {
                        Toast.makeText(ScanQRCodeActivity.this, "未发现二维码", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ScanQRCodeActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        }
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
}
