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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class VertifyOrderFragment extends BaseFragment {

    @BindView(R.id.lv_vertify_order)
    ListView lvVertifyOrder;
    private View view;
    private List<OrderBean.DataBean> mOrderList = new ArrayList<>();
    private String user_id;
    private OrderAdapter orderAdapter;


    public VertifyOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        user_id = (String) SPUtils.get(getActivity(), "userId", "");
        orderAdapter = new OrderAdapter(getActivity(), mOrderList);
        lvVertifyOrder.setAdapter(orderAdapter);
        EventBus.getDefault().register(this);
//        initDataView();

        orderAdapter.setOnMakeOrderListener(new OrderAdapter.OnMakeOrderListener() {
            @Override
            public void setOnCancelOrder(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
            }

            @Override
            public void setOnPayOrder(int position) {
                Toast.makeText(getActivity(), "去支付", Toast.LENGTH_SHORT).show();
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
                intent.putExtra("course_id",dataBean.getCourse_id());
                intent.putExtra("schooluid",dataBean.getUser_id());
                startActivity(intent);
            }

            @Override
            public void setOnVertify(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
                String course_id = dataBean.getOrder_id();//课程id
                String class_teacher = dataBean.getClass_teacher();//学习码
                String school_id = dataBean.getSchool_id();
                String order_state = dataBean.getOrder_state();
                showDialog(class_teacher,course_id,school_id,order_state);
            }

            @Override
            public void setEvaluate(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
                Intent intent = new Intent(getActivity(), PublishEvaluateActivity.class)
                        .putExtra("order_id", dataBean.getCourse_id())
                        .putExtra("school_name", dataBean.getSchool_name())
                        .putExtra("class_photo", dataBean.getSchool_logo()
                        );
                startActivity(intent);
            }
        });
        return view;
    }

    private void showDialog(final String learnCode, final String orderId, final String school_id, final String order_state) {
        final SelfDialog selfDialog = new SelfDialog(getActivity());

        selfDialog.setMessage("是否需要学堂确认订单?");
        selfDialog.setYesOnclickListener("是", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
////                cancelOrder(dataBean, position);
//                startActivity(new Intent(getActivity(), QRCodeActivity.class)
//                        .putExtra("learnCode", "gr,"+learnCode + "," + orderId)
//                        .putExtra("flag", "LC"));
                selfDialog.dismiss();
                showDialog2(learnCode,orderId,school_id,order_state);

            }
        });
        selfDialog.setNoOnclickListener("否", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
//                sign(learnCode,orderId);
                selfDialog.dismiss();
                showDialog3(learnCode,orderId,school_id);

            }
        });
        backgroundAlpha(0.6f);
//        selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
    }
    private void showDialog2(final String learnCode, final String orderId, final String school_id, final String order_state) {
        final SelfDialog selfDialog = new SelfDialog(getActivity());

        selfDialog.setMessage("学费将支付到机构账户，如发生退款请直接与机构联系");
        selfDialog.setYesOnclickListener("是", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
//                cancelOrder(dataBean, position);
                startActivity(new Intent(getActivity(), QRCodeActivity.class)
                        .putExtra("learnCode", "gr,"+learnCode + "," + orderId+","+school_id)
                        .putExtra("flag", "LC")
                        .putExtra("order_state", order_state));
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
    private void showDialog3(final String learnCode, final String orderId, final String school_id) {
        final SelfDialog selfDialog = new SelfDialog(getActivity());

        selfDialog.setMessage("学费将支付到机构账户，如发生退款请直接与机构联系");
        selfDialog.setYesOnclickListener("是", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
//                cancelOrder(dataBean, position);

                selfDialog.dismiss();
                sign(learnCode,orderId,school_id);
            }
        });
        selfDialog.setNoOnclickListener("否", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
//                initDataView();
                selfDialog.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
    }
    private void sign(String learnCode, String orderId, String school_id) {
        OkHttpUtils.post()
                .url(InternetS.SCAN_ORDER)
                .addParams("study_num", (learnCode + "," + orderId))
                .addParams("school_id", (school_id))
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

    private void initDataView() {
        OkHttpUtils.post()
                .url(Internet.ORDERSTATE)
                .addParams("user_id", user_id)
                .addParams("order_state", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AllOrderFragment.java:7373待确认)" + response);
                        if (response.contains("没有信息")|| TextUtils.isEmpty(response)){
                            mOrderList.clear();
                            orderAdapter.notifyDataSetChanged();
                        }else {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void hello(String event) {
        Log.e("aaa",
                "(VertifyOrderFragment.java:140)" + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        if (event.equals("refund")){
            initDataView();
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_vertify_order;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initDataView();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        initDataView();
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
}
