package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.MyUtiles;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.widget.MultiPickResultView;

public class IdentityCardActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.identity_et)
    EditText identityEt;
    @BindView(R.id.recycler_shenfenzheng)
    MultiPickResultView multiPickResultView;

    private List<String> photo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_identity_card;
    }

    private void initView() {
        multiPickResultView.init(this, MultiPickResultView.ACTION_SELECT, null,0);
        tvTitle.setText("身份认证");
    }

    @OnClick({R.id.rl_back, R.id.identity_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.identity_tv:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        multiPickResultView.onActivityResult(requestCode, resultCode, data);
        ArrayList<String> photos = multiPickResultView.getPhotos();
        // switchPicture(mMultiPickResultView);
        Log.d("aaa", "--photos--->" + photos);
        PhotoPickUtils.onActivityResult(requestCode, resultCode, data, new PhotoPickUtils.PickHandler() {
            @Override
            public void onPickSuccess(ArrayList<String> photos) {//已经预先做了null或size为0的判断
                Log.e("aaa", "--photos-ssss-->" + photos);
                photo.clear();
                for (int i = 0; i < photos.size(); i++) {
                    String s = MyUtiles.imageToBase64(photos.get(i));
                    photo.add(s);
                }
            }

            @Override
            public void onPreviewBack(ArrayList<String> photos) {

            }

            @Override
            public void onPickFail(String error) {
                Toast.makeText(IdentityCardActivity.this, error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPickCancle() {
                Toast.makeText(IdentityCardActivity.this, "取消选择", Toast.LENGTH_LONG).show();
            }


        });

    }
}
