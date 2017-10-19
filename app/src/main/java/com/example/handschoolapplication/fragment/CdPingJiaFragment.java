package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.handschoolapplication.R;
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

    public CdPingJiaFragment() {
        // Required empty public constructor
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
        initView();
        return view;
    }

    private void initView() {
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
                            ArrayList<EvaluateBean> evaluateBeanArrayList = gson.fromJson(data.toString(), new TypeToken<ArrayList<EvaluateBean>>() {
                            }.getType());
                            tvEvaNum.setText("评价（" + evaluateBeanArrayList.size() + "）");
                            pingjiaAdapter = new PingJiaAdapter(getActivity(), evaluateBeanArrayList);
                            mlvCdpingjia.setAdapter(pingjiaAdapter);
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
