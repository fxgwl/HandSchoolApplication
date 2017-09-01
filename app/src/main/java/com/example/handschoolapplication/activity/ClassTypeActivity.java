package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ClassTypeAdapter;
import com.example.handschoolapplication.adapter.ClassTypeAddAdapter;
import com.example.handschoolapplication.view.MyListView;
import com.example.handschoolapplication.view.MyPopupWindow;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassTypeActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.classtype_lv)
    MyListView classtypeLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_type);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("学堂类别");
        ClassTypeAdapter classTypeAdapter = new ClassTypeAdapter(this, new ArrayList<String>());
        classtypeLv.setAdapter(classTypeAdapter);
    }

    @OnClick({R.id.rl_back, R.id.tv_save, R.id.classtype_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_save:
                break;
            case R.id.classtype_add:
                View view1 = View.inflate(this, R.layout.classtype_add, null);
                ListView lv = (ListView) view1.findViewById(R.id.lv_classtype_add);
                MyPopupWindow myPopupWindow = new MyPopupWindow(this, view1);
                myPopupWindow.setHeight(500);
                myPopupWindow.showAtLocation(tvTitle, Gravity.BOTTOM, 0, 0);
                ClassTypeAddAdapter addAdapter = new ClassTypeAddAdapter(this, new ArrayList<String>());
                lv.setAdapter(addAdapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(new Intent(ClassTypeActivity.this, ClassTypeAddDetailActivity.class));
                    }
                });
                myPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                break;
        }
    }
}
