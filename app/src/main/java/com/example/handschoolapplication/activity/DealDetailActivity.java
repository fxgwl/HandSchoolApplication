package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.MyAccountBean;
import com.example.handschoolapplication.utils.Internet;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class DealDetailActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_itemdaifukuang_orderid)
    TextView tvItemdaifukuangOrderid;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.civ_itendaifukuang_usericon)
    CircleImageView civItendaifukuangUsericon;
    @BindView(R.id.tv_daifukuang_nickname)
    TextView tvDaifukuangNickname;
    @BindView(R.id.iv_itemdaifukuang_classlogo)
    ImageView ivItemdaifukuangClasslogo;
    @BindView(R.id.tv_itemdaifukuang_classname)
    TextView tvItemdaifukuangClassname;
    @BindView(R.id.tv_itemdaifukuang_classprice)
    TextView tvItemdaifukuangClassprice;
    @BindView(R.id.tv_itemdaifukuang_totalprice)
    TextView tvItemdaifukuangTotalprice;
    @BindView(R.id.tv_itemdaifukuang_num)
    TextView tvItemdaifukuangNum;
    private MyAccountBean.DataBean myAccountBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("交易详情");
        myAccountBean = (MyAccountBean.DataBean) getIntent().getSerializableExtra("myAccountBean");

        initData();
    }

    private void initData() {
        tvItemdaifukuangOrderid.setText(myAccountBean.getOrderInfo().getOrder_id());
        tvTime.setText(myAccountBean.getRecord_date() + " " + myAccountBean.getRecord_time());
        Glide.with(this).load(Internet.BASE_URL + myAccountBean.getUserInfo().getHead_photo()).centerCrop().into(civItendaifukuangUsericon);
        tvDaifukuangNickname.setText(myAccountBean.getUserInfo().getUser_name());
        Glide.with(this).load(Internet.BASE_URL + myAccountBean.getCourseInfo().getPicture_one()).centerCrop().into(ivItemdaifukuangClasslogo);
        tvItemdaifukuangClassname.setText(myAccountBean.getCourse_name());
        tvItemdaifukuangClassprice.setText("价格：" + myAccountBean.getOrderInfo().getClass_money().split("/")[0]);
        tvItemdaifukuangTotalprice.setText("总价：" + myAccountBean.getOrderInfo().getOrder_money());
        tvItemdaifukuangNum.setText("数量：x" + myAccountBean.getOrderInfo().getCourse_num());

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_deal_detail;
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
}
