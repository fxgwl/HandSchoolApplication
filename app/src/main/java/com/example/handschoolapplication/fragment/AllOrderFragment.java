package com.example.handschoolapplication.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.NowApplyActivity;
import com.example.handschoolapplication.activity.PublishEvaluateActivity;
import com.example.handschoolapplication.activity.QRCodeActivity;
import com.example.handschoolapplication.activity.ReturnMoneyActivity;
import com.example.handschoolapplication.adapter.OrderAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.OrderBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.SelfDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllOrderFragment extends BaseFragment {

    @BindView(R.id.lv_all_order)
    ListView lvAllOrder;
    Unbinder unbinder;
    private View view;
    private List<OrderBean.DataBean> mOrderList = new ArrayList<>();
    private String user_id;
    private OrderAdapter orderAdapter;


    public AllOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        user_id = (String) SPUtils.get(getActivity(), "userId", "");
        orderAdapter = new OrderAdapter(getActivity(), mOrderList);
        lvAllOrder.setAdapter(orderAdapter);
        initDataView();

        orderAdapter.setOnMakeOrderListener(new OrderAdapter.OnMakeOrderListener() {
            @Override
            public void setOnCancelOrder(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
                showCashDialog(dataBean, position);
            }

            @Override
            public void setOnPayOrder(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
                String school_name = dataBean.getSchool_name();
                String school_id = dataBean.getSchool_id();
                String course_name = dataBean.getClass_name();
                String course_time = dataBean.getCourseInfo().getCourse_time();
                String course_state = dataBean.getCourseInfo().getCourse_state();
                int enrol_num = dataBean.getCourseInfo().getEnrol_num();
                int course_capacity = dataBean.getCourseInfo().getCourse_capacity();
                String age_range = dataBean.getCourseInfo().getAge_range();
                String course_teacher = dataBean.getCourseInfo().getCourse_teacher();
                String original_price = dataBean.getCourseInfo().getOriginal_price();
                String preferential_price = dataBean.getCourseInfo().getPreferential_price();
                String class_money = dataBean.getClass_money();
                String course_id = dataBean.getCourse_id();
                String order_id = dataBean.getOrder_id();
                String course_num = dataBean.getCourse_num();
                String is_golds = dataBean.getCourseInfo().getIs_golds();
                String student_name = dataBean.getStudent_name();
                String student_sex = dataBean.getStudent_sex();

                if ("2".equals(course_state) || enrol_num >= course_capacity) {
                    Toast.makeText(activity, course_name + "课程已停止报名", Toast.LENGTH_SHORT).show();
                } else {
                    startActivityForResult(new Intent(getActivity(), NowApplyActivity.class)
                                    .putExtra("school_name", school_name)
                                    .putExtra("school_id", school_id)
                                    .putExtra("course_name", course_name)
                                    .putExtra("course_time", course_time)
                                    .putExtra("enrol_num", enrol_num + "")
                                    .putExtra("course_capacity", course_capacity + "")
                                    .putExtra("age_range", age_range)
                                    .putExtra("course_teacher", course_teacher)
                                    .putExtra("original_price", original_price)
                                    .putExtra("preferential_price", preferential_price)
                                    .putExtra("class_money", class_money)
                                    .putExtra("order_id", order_id)
                                    .putExtra("course_id", course_id)
                                    .putExtra("course_num", course_num)
                                    .putExtra("is_golds", is_golds)
                                    .putExtra("student_name", student_name)
                                    .putExtra("student_sex", student_sex)
                            , 0);
                }
            }

            @Override
            public void setOnRefund(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
                Intent intent = new Intent(getActivity(), ReturnMoneyActivity.class);
                intent.putExtra("ordernum", dataBean.getOrder_id());
                intent.putExtra("courseid", dataBean.getCourse_id());
                intent.putExtra("ivcourse", dataBean.getClass_photo());
                intent.putExtra("coursename", dataBean.getClass_name());
                intent.putExtra("money", dataBean.getOrder_money());
                intent.putExtra("coursenum", dataBean.getCourse_num());
                intent.putExtra("tuimoney", dataBean.getOrder_money());
                intent.putExtra("course_id", dataBean.getCourse_id());
                intent.putExtra("schooluid", dataBean.getUser_id());
                startActivityForResult(intent, 0);
            }

            @Override
            public void setOnVertify(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
                String class_teacher = dataBean.getClass_teacher();//学习码
                String order_id = dataBean.getOrder_id();//订单Id
                String school_id = dataBean.getSchool_id();
                String order_state = dataBean.getOrder_state();
                showDialog(class_teacher, order_id,school_id,order_state);
            }

            @Override
            public void setEvaluate(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
                Intent intent = new Intent(getActivity(), PublishEvaluateActivity.class)
                        .putExtra("order_id", dataBean.getOrder_id())
                        .putExtra("school_name", dataBean.getSchool_name())
                        .putExtra("class_photo", dataBean.getClass_photo()
                        );
                startActivityForResult(intent, 0);
            }
        });
        return view;
    }

    private void initDataView() {

        Log.e("aaa",
                "(AllOrderFragment.java:149)<--user_id-->" + user_id);
        OkHttpUtils.post()
                .url(Internet.ALLORDER)
                .addParams("user_id", user_id)
                .addParams("type", "order")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(AllOrderFragment.java:168)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AllOrderFragment.java:174)" + response);
                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                            mOrderList.clear();
                            orderAdapter.notifyDataSetChanged();
                        } else {
                            Gson gson = new Gson();
                            mOrderList.clear();
                            try {
                                mOrderList.addAll(gson.fromJson(response, OrderBean.class).getData());
                                orderAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    }
                });
    }

    private void cancelOrder(OrderBean.DataBean dataBean, final int position) {
        OkHttpUtils.post()
                .url(Internet.CLOSEORDER)
                .addParams("order_id", dataBean.getOrder_id())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(OrderAdapter.java:164)" + response);
                        if (response.contains("成功")) {
                            mOrderList.remove(position);
                            orderAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_all_order;
    }

    @Override
    public void onResume() {
        super.onResume();
//        initDataView();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        initDataView();
    }

    private void sign(String learnCode, String orderId,String school_id) {
        OkHttpUtils.post()
                .url(InternetS.SCAN_ORDER)
                .addParams("study_num", (learnCode + "," + orderId))
                .addParams("school_id", school_id)
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
                                Toast.makeText(getActivity(), "确认成功", Toast.LENGTH_SHORT).show();
                                initDataView();
                            } else {
                                Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void showCashDialog(final OrderBean.DataBean dataBean, final int position) {
        final SelfDialog selfDialog = new SelfDialog(getActivity());

        selfDialog.setMessage("是否取消订单?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                cancelOrder(dataBean, position);
                selfDialog.dismiss();
            }
        });


        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                selfDialog.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
    }

    private void showDialog(final String learnCode, final String orderId, final String school_id, final String order_state) {
        final SelfDialog selfDialog = new SelfDialog(getActivity());

        selfDialog.setMessage("是否需要到学习机构进行确认?");
        selfDialog.setYesOnclickListener("是", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
////                cancelOrder(dataBean, position);
//                startActivity(new Intent(getActivity(), QRCodeActivity.class)
//                        .putExtra("learnCode", "gr,"+learnCode + "," + orderId)
//                        .putExtra("flag", "LC"));
                selfDialog.dismiss();
                showDialog2(learnCode, orderId,school_id,order_state);

            }
        });


        selfDialog.setNoOnclickListener("否", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
//                sign(learnCode,orderId);
                selfDialog.dismiss();
                showDialog3(learnCode, orderId,school_id,order_state);

            }
        });
        backgroundAlpha(0.6f);
        selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
    }

    private void showDialog2(final String learnCode, final String orderId,final String school_id,final String order_state) {
        final SelfDialog selfDialog2 = new SelfDialog(getActivity());

        selfDialog2.setMessage("学费将支付到机构账户，如发生退款请直接与机构联系");
        selfDialog2.setYesOnclickListener("是", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
//                cancelOrder(dataBean, position);
                startActivityForResult(new Intent(getActivity(), QRCodeActivity.class)
                        .putExtra("learnCode", "gr," + learnCode + "," + orderId+","+school_id)
                        .putExtra("flag", "LC")
                        .putExtra("order_state", order_state), 0);
                selfDialog2.dismiss();
            }
        });


        selfDialog2.setNoOnclickListener("否", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {

                selfDialog2.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        selfDialog2.setOnDismissListener(new poponDismissListener());
        selfDialog2.show();
    }


    private void showDialog3(final String learnCode, final String orderId,final String school_id,final String order_state) {
        final SelfDialog selfDialog3 = new SelfDialog(getActivity());

        selfDialog3.setMessage("学费将支付到机构账户，如发生退款请直接与机构联系");
        selfDialog3.setYesOnclickListener("是", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
//                cancelOrder(dataBean, position);
                selfDialog3.dismiss();
                sign(learnCode, orderId,school_id);

            }
        });


        selfDialog3.setNoOnclickListener("否", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                initDataView();
                selfDialog3.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        selfDialog3.setOnDismissListener(new poponDismissListener());
        selfDialog3.show();
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initDataView();
    }
}
