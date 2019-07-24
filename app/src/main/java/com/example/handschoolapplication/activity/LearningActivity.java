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
import com.example.handschoolapplication.adapter.LearningAdapter;
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

public class LearningActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.lv_learning_course)
    ListView lvLearningCourse;
    private LearningAdapter mAdapter;
    private List<LearningCourseBean> mList = new ArrayList<>();
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = (String) SPUtils.get(this, "userId", "");
        initView();//实例化视图
        initData();//获取数据

    }

    private void initView() {
        tvTitle.setText("学习中");
        mAdapter = new LearningAdapter(mList, this);
        lvLearningCourse.setAdapter(mAdapter);

        mAdapter.setOnClickCourseSignListener(new AllCourseAdapter.OnClickLearnCodeListener() {
            @Override
            public void setLearnCode(int position) {
                startActivity(new Intent(LearningActivity.this, QRCodeActivity.class)
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

    private void initData() {
        mList.clear();
        OkHttpUtils.post()
                .url(Internet.ALLORDER)
                .addParams("user_id", userId)
                .addParams("type", "course")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(LearningActivity.java:61)" + e.getMessage());
                        Toast.makeText(LearningActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(LearningActivity.java:68)" + response);
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                ArrayList<LearningCourseBean> learningCourseBeen = new ArrayList<>();
                                learningCourseBeen.addAll((Collection<? extends LearningCourseBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<LearningCourseBean>>() {
                                }.getType()));
                                for (int i = 0; i < learningCourseBeen.size(); i++) {
                                    String course_hour = "0";
                                    if (null != learningCourseBeen.get(i).getClass_money()) {
                                        String string = learningCourseBeen.get(i).getClass_money();
                                        course_hour = string.split("/")[1].split("节")[0];
                                    }

                                    int allTime = Integer.parseInt(course_hour);//总学时

                                    String order_state = learningCourseBeen.get(i).getOrder_state();//订单状态
                                    String course_state = learningCourseBeen.get(i).getCourseInfo().getCourse_state();//课程状态
                                    int study_class = 0;
                                    if (learningCourseBeen.get(i).getClassSign() != null) {
                                        study_class = learningCourseBeen.get(i).getClassSign().getStudy_class();
                                    }
                                    boolean isFinish = false;
                                    if (study_class >= allTime) {
                                        isFinish = true;
                                    }

                                    if ("1".equals(order_state) || "2".equals(order_state) || "3".equals(order_state)) {
                                        if ("0".equals(course_state) || order_state.equals("1")) {
                                        } else if (isFinish || course_state.equals("2")) {
                                        } else {
                                            mList.add(learningCourseBeen.get(i));
                                        }
                                    }
                                }
                                Log.e("aaa",
                                        "(LearningActivity.java:141)<--正在学习的课程数量-->" + mList.size());
                                mAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });


    }

    private void showCashDialog(final String courseId, final String time, final String order_id, final boolean canFinish) {
        final SelfDialog selfDialog = new SelfDialog(this);

        selfDialog.setMessage("您确认签到学习?");
        selfDialog.setYesOnclickListener("是", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                signInOrder(courseId, userId, time, order_id,canFinish);
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
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AllCourseActivity.java:97)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int result = jsonObject.getInt("result");
                            if (result == 0) {
                                Toast.makeText(LearningActivity.this, "签到成功", Toast.LENGTH_SHORT).show();
                                if (canFinish) {
                                    finish();
                                } else
                                    initData();
                            } else {
                                Toast.makeText(LearningActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
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

    @Override
    public int getContentViewId() {
        return R.layout.activity_learning;
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
