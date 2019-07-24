package com.example.handschoolapplication.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.ClassActivity;
import com.example.handschoolapplication.activity.MainActivity;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.DiscountBean;
import com.example.handschoolapplication.bean.MenuBean;
import com.example.handschoolapplication.bean.UnusedDisBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.SelfDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnusedDisFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.lv_discountcoupon)
    ListView lvDiscountcoupon;
    private View view;
    private List<UnusedDisBean> mList;
    private String userId;
    ArrayList<DiscountBean.DataBean> mlist = new ArrayList<>();

    public UnusedDisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        userId = (String) SPUtils.get(getActivity(), "userId", "");
        initDataView();


        lvDiscountcoupon.setOnItemClickListener(this);
        return view;
    }

    private void initDataView() {
        mList = new ArrayList<>();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("coupons_state", "");

        Log.e("aaa",
                "(UnusedDisFragment.java:68)" + "userID = = === " + userId);
        OkHttpUtils.post()
                .params(params)
                .url(Internet.DISCOUNTLIST)
                .addParams("user_id", userId)
                .addParams("coupons_state", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(UnusedDisFragment.java:66)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(UnusedDisFragment.java:73)" + response);
                        Gson gson = new Gson();
                        mlist.clear();
                        try {
                            mlist.addAll(gson.fromJson(response, DiscountBean.class).getData());
                        } catch (Exception e) {

                        }
                        MyAdapter madapter = new MyAdapter();
                        lvDiscountcoupon.setAdapter(madapter);

                    }
                });

    }

    private void showUseDisDialog(final String coupons_type, final String school_id) {
        final SelfDialog selfDialog = new SelfDialog(getActivity());

        selfDialog.setMessage("前去使用?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                if (coupons_type.equals("1")){
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    EventBus.getDefault().post(new MenuBean(1));
                }else {
                    startActivity(new Intent(getActivity(), ClassActivity.class).putExtra("school_id",school_id));
                }
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

    @Override
    public int getContentViewId() {
        return R.layout.fragment_unused_dis;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DiscountBean.DataBean dataBean = mlist.get(position);
        String coupons_type = dataBean.getCoupons_type();
        String school_id = dataBean.getSchool_id();

        showUseDisDialog(coupons_type,school_id);


    }

        /**
         * 添加弹出的dialog关闭的事件，主要是为了将背景透明度改回来
         *
         * @author cg
         */
        class poponDismissListener implements Dialog.OnDismissListener {


            @Override
            public void onDismiss(DialogInterface dialog) {
                //Log.v("List_noteTypeActivity:", "我是关闭事件");
                backgroundAlpha(1f);
            }
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


    class MyAdapter extends BaseAdapter {

        private int size = 0;

        @Override
        public int getCount() {

            if (mlist != null) {
                size = mlist.size();
            }
            return size;
        }

        @Override
        public Object getItem(int position) {
            return mlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            ViewHolder holder = null;

            if (view == null) {
                view = View.inflate(getActivity(), R.layout.item_unused_dis_lv, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.tvClassname.setText(mlist.get(position).getCoupons_name());
            holder.tvCondition.setText("满" + mlist.get(position).getMax_money() + "元使用");
            holder.tvMoney.setText(mlist.get(position).getDiscount_amount() + "元");
            holder.tvTime.setText(mlist.get(position).getStart_time() + "-" + mlist.get(position).getEnd_time());

            if (mlist.get(position).getCoupons_type().equals("1")){
                holder.ivYhqBj.setImageResource(R.drawable.yhqhuangsebj);
            }else {
                holder.ivYhqBj.setImageResource(R.drawable.youhuiquan);
            }
            return view;
        }

        class ViewHolder {
            @BindView(R.id.tv_classname)
            TextView tvClassname;
            @BindView(R.id.tv_condition)
            TextView tvCondition;
            @BindView(R.id.tv_money)
            TextView tvMoney;
            @BindView(R.id.tv_time)
            TextView tvTime;
            @BindView(R.id.iv_yhq_bj)
            ImageView ivYhqBj;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
