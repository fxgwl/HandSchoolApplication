package com.example.handschoolapplication.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.AddressBeanS;
import com.example.handschoolapplication.bean.DataAbc;
import com.example.handschoolapplication.bean.HotCity;
import com.example.handschoolapplication.utils.ChineseToEnglish;
import com.example.handschoolapplication.utils.CompareSort;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.view.MyGridView;
import com.example.handschoolapplication.view.MyListView;
import com.example.handschoolapplication.view.SideBarView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;




public class CurrentCityActivity extends BaseActivity implements SideBarView.LetterSelectListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.gv_hot_city)
    MyGridView gvHotCity;
    @BindView(R.id.lv_address)
    MyListView lvAddress;
    @BindView(R.id.sidebarview)
    SideBarView sidebarview;
    @BindView(R.id.tv_current_city)
    TextView tvCurrentCity;
    @BindView(R.id.listview)
    MyListView listView;
    @BindView(R.id.tip)
    TextView mTip;

    private List<HotCity> mList;
    private String city;
    private UserAdapter mAdapter;
    private MyHotCityAdapter myHotCityAdapter;
    private ArrayList<AddressBeanS> addressBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        myHotCityAdapter = new MyHotCityAdapter();
        gvHotCity.setAdapter(myHotCityAdapter);
        city = getIntent().getStringExtra("city");
        tvTitle.setText("当前城市" + "（" + city + "）");
        tvCurrentCity.setText(city);
        initData();
//        initView();
        init();

    }


    private void init() {
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
        Gson gson = new Gson();
        addressBean = gson.fromJson(JsonData, new TypeToken<ArrayList<AddressBeanS>>() {
        }.getType());

        Log.e("aaa",
                "(CurrentCityActivity.java:95)size ===== " + addressBean.size());
        ArrayList<DataAbc> users = new ArrayList<>();
        for (int i = 0; i < addressBean.size(); i++) {
            DataAbc dataAbc = new DataAbc();
            dataAbc.setName(addressBean.get(i).getName());
            String firstSpell = ChineseToEnglish.getFirstSpell(addressBean.get(i).getName());
            String substring = firstSpell.substring(0, 1).toUpperCase();
            if (substring.matches("[A-Z]")) {
                dataAbc.setLetter(substring);
            } else {
                dataAbc.setLetter("#");
            }
            users.add(dataAbc);
        }

        //排序
        Collections.sort(users, new CompareSort());

        //设置数据
        mAdapter = new UserAdapter(this);
        mAdapter.setData(users);
        listView.setAdapter(mAdapter);

        //设置回调
        sidebarview.setOnLetterSelectListen(this);

    }

    private void initData() {

        OkHttpUtils.post()
                .url(InternetS.HOT_ADDRESS_INFOR)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(CurrentCityActivity.java:52)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(CurrentCityActivity.java:59)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            mList = new ArrayList<HotCity>();
                            mList.addAll((Collection<? extends HotCity>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<HotCity>>() {
                            }.getType()));
                            myHotCityAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_current_city;
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

    @Override
    public void onLetterSelected(String letter) {
        setListviewPosition(letter);
        mTip.setText(letter);
        mTip.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLetterChanged(String letter) {
        setListviewPosition(letter);
        mTip.setText(letter);
    }

    @Override
    public void onLetterReleased(String letter) {
        mTip.setVisibility(View.GONE);
    }

    private void setListviewPosition(String letter) {
        final int firstLetterPosition = mAdapter.getFirstLetterPosition(letter);
        if (firstLetterPosition != -1) {
            listView.post(new Runnable() {
                @Override
                public void run() {
                    listView.requestFocusFromTouch();//获取焦点
                    listView.setSelection(firstLetterPosition);
                }
            });

        }
    }

    public class GetJsonDataUtil {


        public String getJson(Context context, String fileName) {

            StringBuilder stringBuilder = new StringBuilder();
            try {
                AssetManager assetManager = context.getAssets();
                BufferedReader bf = new BufferedReader(new InputStreamReader(
                        assetManager.open(fileName)));
                String line;
                while ((line = bf.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

    }

    /**
     * Created by Administrator on 2016/1/8.
     */
    public class UserAdapter extends BaseAdapter {
        private Context mContext;
        private ArrayList<DataAbc> users;

        public UserAdapter(Context context) {
            this.mContext = context;
            users = new ArrayList<>();
        }

        public void setData(List<DataAbc> data) {
            this.users.clear();
            this.users.addAll(data);
        }


        @Override
        public int getCount() {
            return users.size();
        }

        @Override
        public Object getItem(int position) {
            return users.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item, null);
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.title);
                viewHolder.tvName = (TextView) convertView.findViewById(R.id.name);
                viewHolder.tvItem = (LinearLayout) convertView.findViewById(R.id.item);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tvName.setText(users.get(position).getName());
            viewHolder.tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, users.get(position).getName(), Toast.LENGTH_SHORT).show();
                }
            });
            //当前的item的title与上一个item的title不同的时候回显示title(A,B,C......)
            if (position == getFirstLetterPosition(position) && !users.get(position).getLetter().equals("@")) {
                viewHolder.tvTitle.setVisibility(View.VISIBLE);
                viewHolder.tvTitle.setText(users.get(position).getLetter().toUpperCase());
            } else {
                viewHolder.tvTitle.setVisibility(View.GONE);
            }


            return convertView;
        }

        /**
         * 顺序遍历所有元素．找到position对应的title是什么（A,B,C?）然后找这个title下的第一个item对应的position
         *
         * @param position
         * @return
         */
        private int getFirstLetterPosition(int position) {

            String letter = users.get(position).getLetter();
            int cnAscii = ChineseToEnglish.getCnAscii(letter.toUpperCase().charAt(0));
            int size = users.size();
            for (int i = 0; i < size; i++) {
                if (cnAscii == users.get(i).getLetter().charAt(0)) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * 顺序遍历所有元素．找到letter下的第一个item对应的position
         *
         * @param letter
         * @return
         */
        public int getFirstLetterPosition(String letter) {
            int size = users.size();
            for (int i = 0; i < size; i++) {
                if (letter.charAt(0) == users.get(i).getLetter().charAt(0)) {
                    return i;
                }
            }
            return -1;
        }

        class ViewHolder {
            TextView tvName;
            TextView tvTitle;
            LinearLayout tvItem;
        }

    }

    class MyHotCityAdapter extends BaseAdapter {
        int size = 0;

        @Override
        public int getCount() {
            if (mList != null) {
                if (mList.size() > 9) {
                    size = 9;
                } else {
                    size = mList.size();
                }
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(CurrentCityActivity.this, R.layout.item_hot_city_gv, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            HotCity hotCity = mList.get(position);
            String address_name = hotCity.getAddress_name();
            holder.tvCity.setText(address_name);
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.tv_city)
            TextView tvCity;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
