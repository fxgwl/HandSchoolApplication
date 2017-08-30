package com.example.handschoolapplication.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/20.
 */

public abstract class BaseActivity extends AppCompatActivity{


    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(getContentViewId());
        bind = ButterKnife.bind(this);
        Log.e(getClass().getSimpleName(), "--->onCreate: " );
    }
    public abstract int getContentViewId();

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(getClass().getSimpleName(), "--->onStart: " );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(getClass().getSimpleName(), "--->onStop: " );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(getClass().getSimpleName(), "--->onRestart: " );
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.e(getClass().getSimpleName(), "--->onpause: " );
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e(getClass().getSimpleName(), "--->onResume: " );
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        Log.e(getClass().getSimpleName(), "--->onDestroy: " );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
