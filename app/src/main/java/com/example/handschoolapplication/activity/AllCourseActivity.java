package com.example.handschoolapplication.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.AllCourseAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.LearningCourseBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.SelfDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class AllCourseActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_all_course)
    ListView lvAllCourse;

    private List<LearningCourseBean> mList = new ArrayList<>();
    private AllCourseAdapter mAdapter;
    private String userId;

    private boolean isLastSign = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = (String) SPUtils.get(this, "userId", "");

        initView();
        initData();
    }

    private void initView() {
        tvTitle.setText("全部课程");
        mAdapter = new AllCourseAdapter(mList, this);
        lvAllCourse.setAdapter(mAdapter);

        mAdapter.setOnClickLearnCodeListener(new AllCourseAdapter.OnClickLearnCodeListener() {
            @Override
            public void setLearnCode(int position) {
//                Log.e("aaa",
//                    "(AllCourseActivity.java:62)"+mList.get(position).getCourseInfo().getStudy_num());
                startActivity(new Intent(AllCourseActivity.this, QRCodeActivity.class)
                        .putExtra("learnCode", "gr," + (mList.get(position).getClass_teacher() + "," + mList.get(position).getOrder_id() + "," + mList.get(position).getSchool_id()))
                        .putExtra("flag", "LC")
                        .putExtra("order_state", mList.get(position).getOrder_state()));
            }

            @Override
            public void setCourseSign(int position, String classTime) {
                String course_id = mList.get(position).getCourse_id();
                String order_id = mList.get(position).getOrder_id();
                boolean lastSign = mList.get(position).isLastSign();

                showCashDialog(course_id, classTime, order_id,lastSign);
            }
        });
    }

    private void signInOrder(String course_id, String userId, String classTime, String order_id, final boolean canFinish) {
        HashMap<String, String> params = new HashMap<>();
        params.put("course_id", course_id);
        params.put("user_id", userId);
        params.put("all_class", classTime);
        params.put("order_id", order_id);
        OkHttpUtils.post()
                .url(InternetS.SIGN_LIST_ORDER)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(AllCourseActivity.java:91)" + e.getMessage());
                        Toast.makeText(AllCourseActivity.this, "网络不给力！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AllCourseActivity.java:97)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int result = jsonObject.getInt("result");
                            if (result == 0) {
                                Toast.makeText(AllCourseActivity.this, "签到成功", Toast.LENGTH_SHORT).show();
                                if (canFinish){
                                    finish();
                                }else {
                                    initData();
                                }
                            } else {
                                Toast.makeText(AllCourseActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initData() {
        mList.clear();
        Log.e("aaa",
                "(AllCourseActivity.java:125)<--userId-->" + userId);
        OkHttpUtils.post()
                .url(Internet.ALLORDER)
                .addParams("user_id", userId)
                .addParams("type", "course")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(AllCourseActivity.java:136)" + e.getMessage());
                        Toast.makeText(AllCourseActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
                    }


                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AllCourseActivity.java:86)" + response);
                        try {
                            if (!TextUtils.isEmpty(response)){
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                ArrayList<LearningCourseBean> learningCourseBeen = new ArrayList<>();
                                learningCourseBeen.addAll((Collection<? extends LearningCourseBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<LearningCourseBean>>() {
                                }.getType()));
                                for (int i = 0; i < learningCourseBeen.size(); i++) {
                                    if (!"0".equals(learningCourseBeen.get(i).getOrder_state()) && !"4".equals(learningCourseBeen.get(i).getOrder_state())
                                            && !"5".equals(learningCourseBeen.get(i).getOrder_state()) && !"6".equals(learningCourseBeen.get(i).getOrder_state())) {
                                        mList.add(learningCourseBeen.get(i));
                                    }
                                }
                                mAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_all_course;
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

    private void showCashDialog(final String courseId, final String time, final String order_id,final boolean canfinish) {
        final SelfDialog selfDialog = new SelfDialog(this);

        selfDialog.setMessage("您是否签到学习?");
        selfDialog.setYesOnclickListener("是", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                signInOrder(courseId, userId, time, order_id,canfinish);
                selfDialog.dismiss();
            }
        });

        selfDialog.setNoOnclickListener("否", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                selfDialog.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
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

    /**
     * 添加弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements Dialog.OnDismissListener {

        @Override
        public void onDismiss(DialogInterface dialogInterface) {
            backgroundAlpha(1f);
        }
    }

}
