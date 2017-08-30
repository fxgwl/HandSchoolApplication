package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.widget.MultiPickResultView;

public class AddDataActivity extends BaseActivity {

    @BindView(R.id.recycler_zizhi)
    MultiPickResultView recyclerZizhi;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_time)
    EditText etTime;
    @BindView(R.id.et_person_id)
    EditText etPersonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerZizhi.init(this, MultiPickResultView.ACTION_SELECT, null);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_data;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recyclerZizhi.onActivityResult(requestCode, resultCode, data);
        ArrayList<String> photos = recyclerZizhi.getPhotos();

        // switchPicture(mMultiPickResultView);
        Log.d("aaa", "--photos--->" + photos);
        PhotoPickUtils.onActivityResult(requestCode, resultCode, data, new PhotoPickUtils.PickHandler() {
            @Override
            public void onPickSuccess(ArrayList<String> photos) {//已经预先做了null或size为0的判断
                Log.d("aaa", "--photos-ssss-->" + photos);

            }

            @Override
            public void onPreviewBack(ArrayList<String> photos) {

            }

            @Override
            public void onPickFail(String error) {
                Toast.makeText(AddDataActivity.this, error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPickCancle() {
                Toast.makeText(AddDataActivity.this, "取消选择", Toast.LENGTH_LONG).show();
            }


        });

    }

    @OnClick({R.id.rl_back, R.id.iv_address, R.id.iv_category, R.id.iv_right, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_address:
                break;
            case R.id.iv_category:
                break;
            case R.id.iv_right:
                break;
            case R.id.btn_commit:
                startActivity(new Intent(AddDataActivity.this,LoginActivity.class));
                finish();
                break;
        }
    }
}
