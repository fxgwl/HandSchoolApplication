package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.handschoolapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IdentityCardActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.identity_et)
    EditText identityEt;
    @BindView(R.id.identity_iv1)
    ImageView identityIv1;
    @BindView(R.id.identity_iv2)
    ImageView identityIv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_card);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("");
    }

    @OnClick({R.id.rl_back, R.id.identity_iv1, R.id.identity_iv2, R.id.identity_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.identity_iv1:
                break;
            case R.id.identity_iv2:
                break;
            case R.id.identity_tv:
                break;
        }
    }
}
