package com.example.handschoolapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.PJDetailActivity;
import com.example.handschoolapplication.adapter.PingJiaAdapter;
import com.example.handschoolapplication.bean.EvaluateBean;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.view.MyListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class CdPingJiaFragment extends Fragment {


    @BindView(R.id.mlv_cdpingjia)
    MyListView mlvCdpingjia;
    Unbinder unbinder;
    @BindView(R.id.tv_eva_num)
    TextView tvEvaNum;
    private String courseId;
    private PingJiaAdapter pingjiaAdapter;
    private ArrayList<EvaluateBean> evaluateBeanArrayList = new ArrayList<>();

    public CdPingJiaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            Log.e("aaa", "(CdPingJiaFragment.java:111)<---->" + "显示");
            initView();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            initView();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cd_ping_jia, container, false);
        unbinder = ButterKnife.bind(this, view);
        courseId = getArguments().getString("courseId");
        Log.e("aaa",
                "(CdPingJiaFragment.java:50)" + courseId);
//        Toast.makeText(getActivity(), courseId, Toast.LENGTH_SHORT).show();

        pingjiaAdapter = new PingJiaAdapter(getActivity(), evaluateBeanArrayList);
        mlvCdpingjia.setAdapter(pingjiaAdapter);
        initView();

        pingjiaAdapter.setListener(new PingJiaAdapter.OnItemGoDetail() {
            @Override
            public void setGoToDetail(int position) {
                Intent intent = new Intent(getActivity(), PJDetailActivity.class);
                intent.putExtra("interact_id", evaluateBeanArrayList.get(position).getInteract_id());
                startActivityForResult(intent, 1);
            }
        });
        return view;
    }

    private void initView() {
        evaluateBeanArrayList.clear();
        OkHttpUtils.post()
                .url(InternetS.ALLEVALUATE)
                .addParams("course_id", courseId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(CdPingJiaFragment.java:61)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(CdPingJiaFragment.java:68)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            Gson gson = new Gson();
                            evaluateBeanArrayList.addAll((ArrayList<EvaluateBean>) gson.fromJson(data.toString(), new TypeToken<ArrayList<EvaluateBean>>() {
                            }.getType()));
                            pingjiaAdapter.notifyDataSetChanged();
                            tvEvaNum.setText("评价（" + evaluateBeanArrayList.size() + "）");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
