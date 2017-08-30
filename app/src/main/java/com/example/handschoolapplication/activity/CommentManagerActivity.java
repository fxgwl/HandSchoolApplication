package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.CommentManagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentManagerActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.commentmanager_lv)
    ListView commentmanagerLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_comment_manager);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("评价管理");
        CommentManagerAdapter cmAdapter = new CommentManagerAdapter(this, new ArrayList<String>());
        commentmanagerLv.setAdapter(cmAdapter);

    }

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        finish();
    }
}
