package com.example.handschoolapplication.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.RecommendAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.RecommendBean;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.ListDataSave;
import com.example.handschoolapplication.view.MyListView;
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
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search_title)
    TextView tvSearchTitle;
    @BindView(R.id.lv_history)
    MyListView lvHistory;
    @BindView(R.id.tv_clear_history)
    TextView tvClear;
    @BindView(R.id.lv_recommand)
    MyListView lvRecommand;


    private List<String> mlist;
    private MySearchAdapter mySearchAdapter;

    private List<RecommendBean> recommendBeenList = new ArrayList<>();
    private RecommendAdapter recommendAdapter;
    private List<String> history;
    private ListDataSave listDataSave;
    private double[] locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locations = getIntent().getDoubleArrayExtra("location");
        recommendAdapter = new RecommendAdapter(this, recommendBeenList);
        initViewData();
        lvRecommand.setAdapter(recommendAdapter);
        getRecommand();

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(etSearch.getText().toString().trim())) {
                        Toast.makeText(SearchActivity.this, "搜索不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        String search = etSearch.getText().toString().trim();
                        history.add(search);
                        listDataSave.setDataList("history", history);
                        startActivity(new Intent(SearchActivity.this, SearchResultActivity.class)
                                .putExtra("location", locations)
                                .putExtra("search", search));
                    }
                }
                return false;
            }
        });
    }

    private void getRecommand() {
        recommendBeenList.clear();
        OkHttpUtils.post()
                .url(InternetS.HOT_RECOMMEND)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(SearchActivity.java:77)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SearchActivity.java:84)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            recommendBeenList.addAll((Collection<? extends RecommendBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<RecommendBean>>() {
                            }.getType()));
                            recommendAdapter.setLocation(locations);
                            recommendAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initViewData() {
        listDataSave = new ListDataSave(this, "search");
        history = listDataSave.getDataList("history");
        mlist = new ArrayList<>();
        if (history != null) {
            mlist.addAll(history);
        }
        mySearchAdapter = new MySearchAdapter();
        lvHistory.setAdapter(mySearchAdapter);

        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = mlist.get(position);
                startActivity(new Intent(SearchActivity.this, SearchResultActivity.class)
                        .putExtra("location", locations)
                        .putExtra("search", s));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViewData();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_search;
    }

    @OnClick({R.id.iv_back, R.id.tv_back, R.id.iv_search, R.id.tv_clear_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_search:
//                Internet.COURSESEARCH
                //搜索成功调用的方法
                if (TextUtils.isEmpty(etSearch.getText().toString().trim())) {
                    Toast.makeText(this, "搜索不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String search = etSearch.getText().toString().trim();
                    history.add(search);
                    listDataSave.setDataList("history", history);
                    startActivity(new Intent(this, SearchResultActivity.class)
                            .putExtra("location", locations)
                            .putExtra("search", search));
                }
                break;
            case R.id.tv_clear_history:

                showDialog();
                break;
        }
    }


    class MySearchAdapter extends BaseAdapter {
        int size = 0;

        @Override
        public int getCount() {
            if (mlist != null) {
                size = mlist.size();
            }
            return size;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                view = View.inflate(SearchActivity.this, R.layout.item_history_lv, null);
                holder.textView = (TextView) view.findViewById(R.id.tv_text);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.textView.setText(mlist.get(position));
            return view;
        }

        class ViewHolder {
            TextView textView;
        }
    }

    private void showDialog() {
        final SelfDialog selfDialog = new SelfDialog(SearchActivity.this);

        selfDialog.setMessage("是否清空?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                history.clear();
                listDataSave.clearDataList("history");
                mlist.clear();
                mySearchAdapter.notifyDataSetChanged();
                selfDialog.dismiss();

            }
        });


        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
//                sign(learnCode,orderId);
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
