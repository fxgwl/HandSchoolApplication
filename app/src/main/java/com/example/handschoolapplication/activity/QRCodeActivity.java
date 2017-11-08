package com.example.handschoolapplication.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import butterknife.BindView;
import butterknife.OnClick;


public class QRCodeActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("学堂二维码");
//        generate();
        ;

        ivQrcode.setImageBitmap(encodeAsBitmap("shdaksnsakjsdasd"));
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

    private Bitmap encodeAsBitmap(String str) {
        Bitmap bitmap = null;
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 400, 400);
            // 使用 ZXing Android Embedded 要写的代码
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(result);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException iae) { // ?
            return null;
        }

        return bitmap;
    }

//    private Bitmap generateBitmap(String content, int width, int height) {
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        Map<EncodeHintType, String> hints = new HashMap<>();
//        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        try {
//            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
//            int[] pixels = new int[width * height];
//            for (int i = 0; i < height; i++) {
//                for (int j = 0; j < width; j++) {
//                    if (encode.get(j, i)) {
//                        pixels[i * width + j] = 0x00000000;
//                    } else {
//                        pixels[i * width + j] = 0xffffffff;
//                    }
//                }
//            }
//            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public void generate() {
//        Bitmap qrBitmap = generateBitmap("http://www.csdn.net",400, 400);
//        ivQrcode.setImageBitmap(qrBitmap);
//    }

//    /**
//     * 添加logo
//     * @param qrBitmap
//     * @param logoBitmap
//     * @return
//     */
//    private Bitmap addLogo(Bitmap qrBitmap, Bitmap logoBitmap) {
//        int qrBitmapWidth = qrBitmap.getWidth();
//        int qrBitmapHeight = qrBitmap.getHeight();
//        int logoBitmapWidth = logoBitmap.getWidth();
//        int logoBitmapHeight = logoBitmap.getHeight();
//        Bitmap blankBitmap = Bitmap.createBitmap(qrBitmapWidth, qrBitmapHeight, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(blankBitmap);
//        canvas.drawBitmap(qrBitmap, 0, 0, null);
//        canvas.save(Canvas.ALL_SAVE_FLAG);
//        float scaleSize = 1.0f;
//        while ((logoBitmapWidth / scaleSize) > (qrBitmapWidth / 5) || (logoBitmapHeight / scaleSize) > (qrBitmapHeight / 5)) {
//            scaleSize *= 2;
//        }
//        float sx = 1.0f / scaleSize;
//        canvas.scale(sx, sx, qrBitmapWidth / 2, qrBitmapHeight / 2);
//        canvas.drawBitmap(logoBitmap, (qrBitmapWidth - logoBitmapWidth) / 2, (qrBitmapHeight - logoBitmapHeight) / 2, null);
//        canvas.restore();
//        return blankBitmap;
//    }
//
//    public void generateWithLoge() {
//        Bitmap qrBitmap = generateBitmap("http://www.csdn.net",400, 400);
//        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//        Bitmap bitmap = addLogo(qrBitmap, logoBitmap);
//        ivQrcode.setImageBitmap(bitmap);
//    }
}
