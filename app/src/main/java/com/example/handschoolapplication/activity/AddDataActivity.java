package com.example.handschoolapplication.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.AddDataInfo;
import com.example.handschoolapplication.bean.MenuBean;
import com.example.handschoolapplication.utils.IDCard;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.smarttop.library.bean.City;
import com.smarttop.library.bean.County;
import com.smarttop.library.bean.Province;
import com.smarttop.library.bean.Street;
import com.smarttop.library.widget.AddressSelector;
import com.smarttop.library.widget.BottomDialog;
import com.smarttop.library.widget.OnAddressSelectedListener;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import id.zelory.compressor.Compressor;
import okhttp3.Call;

import static com.bumptech.glide.Glide.with;

public class AddDataActivity extends BaseActivity implements OnAddressSelectedListener, AddressSelector.OnDialogCloseListener {

    /**
     * /**
     * TagAliasCallback类是JPush开发包jar中的类，用于
     * 设置别名和标签的回调接口，成功与否都会回调该方法
     * 同时给定回调的代码。如果code=0,说明别名设置成功。
     * /**
     * 6001   无效的设置，tag/alias 不应参数都为 null
     * 6002   设置超时    建议重试
     * 6003   alias 字符串不合法    有效的别名、标签组成：字母（区分大小写）、数字、下划线、汉字。
     * 6004   alias超长。最多 40个字节    中文 UTF-8 是 3 个字节
     * 6005   某一个 tag 字符串不合法  有效的别名、标签组成：字母（区分大小写）、数字、下划线、汉字。
     * 6006   某一个 tag 超长。一个 tag 最多 40个字节  中文 UTF-8 是 3 个字节
     * 6007   tags 数量超出限制。最多 100个 这是一台设备的限制。一个应用全局的标签数量无限制。
     * 6008   tag/alias 超出总长度限制。总长度最多 1K 字节
     * 6011   10s内设置tag或alias大于3次 短时间内操作过于频繁
     **/
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    //这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    //UserUtils.saveTagAlias(getHoldingActivity(), true);
                    logs = "Set tag and alias success极光推送别名设置成功";
                    Log.e("TAG", logs);
                    break;
                case 6002:
                    //极低的可能设置失败 我设置过几百回 出现3次失败 不放心的话可以失败后继续调用上面那个方面 重连3次即可 记得return 不要进入死循环了...
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试";
                    Log.e("TAG", logs);
                    break;
                default:
                    logs = "极光推送设置失败，Failed with errorCode = " + code;
                    Log.e("TAG", logs);
                    break;
            }
        }
    };
    //    @BindView(R.id.recycler_zizhi)
//    MultiPickResultView recyclerZizhi;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_time)
    EditText etTime;
    @BindView(R.id.et_person_id)
    EditText etPersonId;
    @BindView(R.id.iv_add_zizhi)
    ImageView ivAddZizhi;
    //    @BindView(R.id.recycler_shenfenzheng)
    @BindView(R.id.iv_add_idnum_front)
    ImageView ivAddIdF;
    //    @BindView(R.id.recycler_shenfenzheng)
    @BindView(R.id.iv_add_idnum_back)
    ImageView ivAddIdB;
    @BindView(R.id.iv_menu)
    RelativeLayout rlMenu;
    //    @BindView(R.id.recycler_shenfenzheng)
//    MultiPickResultView recyclerShenfenzheng;
    private BottomDialog dialog;
    private TextView tvCity, tvAddress, tvType;
    private String provinceCode;
    private String cityCode;
    private String countyCode;
    private String streetCode;
    private List<Bitmap> photoStr = new ArrayList<>();
    //    private List<String> idencardStr = new ArrayList<>();
    private Bitmap[] idencardStr = new Bitmap[2];
    private String userId;
    private String address;
    private String street;
    private GeoCoder geoCoder;
    private String latitude;
    private String longitude;
    private String choosecity;
    private String chooseStreets;
    private int REQUEST_CALL_PHONE;
    private int REQUEST_LIST_CODE1 = 0;
    private int REQUEST_LIST_CODE2 = 1;
    private int REQUEST_LIST_CODE3 = 2;
    private double REQUEST_CAMERA_CODE;
    private Intent mIntent;
    private String phone, password, code, type;
    private LoadingDailog dialog1;
    private String user_state;
    private String user_id;
    private String user_id1;//临时保存
    private String dataStr;//临时保存
    private String pName;
    private String cName;
    private String coName;
    private String idPhotoF = "";
    private String idPhotoB = "";
    private String QuPhoto = "";
    private String mechanism_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermission();
        tvTitle.setText("资料填写");
        tvCity = (TextView) findViewById(R.id.tv_city);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvType = (TextView) findViewById(R.id.tv_type);
        rlMenu.setVisibility(View.GONE);

        user_state = (String) SPUtils.get(this, "change_state", "");

        if ("2".equals(user_state)) {
            user_id = (String) SPUtils.get(this, "userId", "");
            Log.e("aaa",
                    "(AddDataActivity.java:133)<---->" + user_id);
            getEditInfo();
        } else {
            mIntent = getIntent();
            phone = mIntent.getStringExtra("user_phone");
            password = mIntent.getStringExtra("user_password");
            code = mIntent.getStringExtra("user_code");
            type = mIntent.getStringExtra("user_type");
        }

        dialog = new BottomDialog(this);
        dialog.setOnAddressSelectedListener(this);
        dialog.setDialogDismisListener(this);
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                with(context).load(path).into(imageView);
            }
        });

        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog1 = loadBuilder.create();
    }

    public int getContentViewId() {
        return R.layout.activity_add_data;
    }

    private void requestPermission() {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CALL_PHONE);
                return;
            } else {
                //已有权限
            }
        } else {
            //API 版本在23以下
        }
    }

    private void getEditInfo() {

        OkHttpUtils.post()
                .url(Internet.EDIT_AND_ADD)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(AddDataActivity.java:171)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AddDataActivity.java:177)<---->" + response);
                        if (TextUtils.isEmpty(response) || response.contains("没有信息")) {
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int result = jsonObject.getInt("result");
                                if (result == 0) {
                                    AddDataInfo addDataInfo = new Gson().fromJson(response, AddDataInfo.class);
                                    /*20200115 fxg 改 start  0:下架，1是上架*/
                                    if(addDataInfo.getData().getChange_state().equals("0")){
                                        SPUtils.clear(getApplicationContext());
                                        EventBus.getDefault().post(new MenuBean(8));
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        return;
                                    } /*20200115 fxg 改 end*/
                                    String mechanism_name = addDataInfo.getData().getMechanism_name();//机构名称
                                    String mechanism_city = addDataInfo.getData().getMechanism_city();//所在省市
                                    String mechanism_ctime = addDataInfo.getData().getMechanism_ctime();//成立时间
                                    String id_number = addDataInfo.getData().getId_number();//身份证号
                                    String mechanism_type = addDataInfo.getData().getMechanism_type();//机构类别
                                    mechanism_address = addDataInfo.getData().getMechanism_address();


                                    String mid_photo = addDataInfo.getData().getMid_photo();//身份证照片图
                                    String mid_photos = addDataInfo.getData().getMid_photos();//身份证照片图
                                    String qualification_prove = addDataInfo.getData().getQualification_prove();
                                    idPhotoF = mid_photo;
                                    idPhotoB = mid_photos;
                                    QuPhoto = qualification_prove;
                                    if (!TextUtils.isEmpty(idPhotoF))
                                        with(AddDataActivity.this).load(Internet.BASE_URL + idPhotoF).centerCrop().into(ivAddIdF);
                                    if (!TextUtils.isEmpty(idPhotoB))
                                        with(AddDataActivity.this).load(Internet.BASE_URL + idPhotoB).centerCrop().into(ivAddIdB);
                                    if (!TextUtils.isEmpty(QuPhoto))
                                        with(AddDataActivity.this).load(Internet.BASE_URL + QuPhoto).centerCrop().into(ivAddZizhi);
                                    String sd_content = "";

                                    if (addDataInfo.getData().getSchoolAddresses().size() > 0 && addDataInfo.getData().getSchoolAddresses().get(0) != null) {
                                        address = addDataInfo.getData().getSchoolAddresses().get(0).getSd_city();
                                        street = addDataInfo.getData().getSchoolAddresses().get(0).getSd_content();
                                        latitude = addDataInfo.getData().getSchoolAddresses().get(0).getSchool_wei();
                                        longitude = addDataInfo.getData().getSchoolAddresses().get(0).getSchool_jing();
                                        sd_content = addDataInfo.getData().getSchoolAddresses().get(0).getSd_content();//机构地址
                                    }
//
//                                    params.put("sd_city", address);//资质证明
//                                    params.put("sd_content", street);//资质证明
//                                    params.put("jing", longitude+"");//资质证明
//                                    params.put("wei", latitude+"");//资质证明
//                                    params.put("school_jing", longitude+"");//资质证明
//                                    params.put("school_wei", latitude+"");//资质证明

                                    etName.setText(mechanism_name);
                                    tvCity.setText(mechanism_city);
                                    etTime.setText(mechanism_ctime);
                                    etPersonId.setText(id_number);
                                    tvType.setText(mechanism_type);
                                    tvAddress.setText(sd_content);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //-----------------------------------start------------------------------------
        if (requestCode == REQUEST_LIST_CODE1 && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");

            // 测试Fresco
            // draweeView.setImageURI(Uri.parse("file://"+pathList.get(0)));
            for (String path : pathList) {
                Log.e("aaa",
                        "(AddDataActivity.java:183)" + path + "\n");
                with(this).load(path).centerCrop().into(ivAddZizhi);
                Bitmap usericon = Compressor.getDefault(AddDataActivity.this).compressToBitmap(new File(path));
                photoStr.add(usericon);
            }


        } else if (requestCode == REQUEST_LIST_CODE2 && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");

            for (String path : pathList) {

                with(this).load(path).centerCrop().into(ivAddIdF);
                Bitmap usericon = Compressor.getDefault(AddDataActivity.this).compressToBitmap(new File(path));
                idencardStr[0] = usericon;
            }

        } else if (requestCode == REQUEST_LIST_CODE3 && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            for (String path : pathList) {
                with(this).load(path).centerCrop().into(ivAddIdB);
                Bitmap usericon = Compressor.getDefault(AddDataActivity.this).compressToBitmap(new File(path));
                idencardStr[1] = usericon;
            }

        } else if (requestCode == REQUEST_CAMERA_CODE && resultCode == RESULT_OK && data != null) {
            String path = data.getStringExtra("result");
            Log.e("aaa",
                    "(AddDataActivity.java:189)" + path);
        }
        //-----------------------------------end-------------------------------------


//        if (requestCode == 2 && resultCode == 22) {
//            tvType.setText(data.getStringExtra("type"));
//        }

        if (requestCode == 1 && resultCode == 11) {
            address = data.getStringExtra("address");
            street = data.getStringExtra("street");
            latitude = data.getStringExtra("latitude");
            longitude = data.getStringExtra("longitude");
            tvAddress.setText(street);
        }
        if (requestCode == 2 && resultCode == 22) {
            String type = data.getStringExtra("type");
            tvType.setText(type);
        }

    }

    @OnClick({R.id.rl_back, R.id.iv_address, R.id.iv_category, R.id.iv_right, R.id.btn_commit, R.id.ll_address,
            R.id.ll_class_address, R.id.ll_category, R.id.iv_add_zizhi, R.id.iv_add_idnum_front, R.id.iv_add_idnum_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_address:
            case R.id.ll_address:
                dialog.show();
                break;
            case R.id.iv_category:
            case R.id.ll_category:
                Intent intent = new Intent(this, ClassTypeFirstActivity.class);
                String trim = tvType.getText().toString().trim();
                if (!TextUtils.isEmpty(trim))
                    intent.putExtra("type", trim);
                startActivityForResult(intent, 2);
                break;
            case R.id.iv_right:
            case R.id.ll_class_address:
                Intent intent1 = new Intent(this, AddAddressActivity.class);
                String trim1 = tvAddress.getText().toString().trim();
                if (!TextUtils.isEmpty(trim1)) {
                    intent1.putExtra("detail", trim1);
                    intent1.putExtra("address", address);
                }
                startActivityForResult(intent1, 1);

                break;
            case R.id.btn_commit:
                String city = tvCity.getText().toString().trim();
                if ("".equals(city)) {
                } else {

                    if (user_state.equals("2")) {
                        Log.e("aaa",
                                "(AddDataActivity.java:344)<--审核失败后填写资料-->");
                        addData(user_id, "1");
                    } else {
                        registerCom();
                        Log.e("aaa",
                                "(AddDataActivity.java:349)<--正常注册是先注册手机号-->");
                    }
                }
                break;
            case R.id.iv_add_zizhi:
                single(REQUEST_LIST_CODE1);
                break;
            case R.id.iv_add_idnum_front:
                single(REQUEST_LIST_CODE2);
                break;
            case R.id.iv_add_idnum_back:
                single(REQUEST_LIST_CODE3);
                break;
        }
    }

    private void addData(String userId, final String flag) {
        String name = etName.getText().toString().trim();//机构名称
        String city = tvCity.getText().toString().trim();//机构所在城市
        String personId = etPersonId.getText().toString().trim();//机构身份证号
        String type = tvType.getText().toString().trim();//机构类型
//        String address = tvAddress.getText().toString().trim();//机构所在地址
        String time = etTime.getText().toString().trim();//机构所在地址


        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(city)
                || TextUtils.isEmpty(type) || TextUtils.isEmpty(address)
                || TextUtils.isEmpty(street) || TextUtils.isEmpty(time)) {
            Toast.makeText(this, "输入信息不完整", Toast.LENGTH_SHORT).show();
            return;
        }

//        if (photoStr.size() < 1) {
//            if (QuPhoto.isEmpty()) {
//                Toast.makeText(this, "请上传资质证明", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }
//        if (idencardStr[0] == null) {
//            if (idPhotoF.isEmpty()) {
//                Toast.makeText(this, "请上传正面身份证图片", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }
//        if (idencardStr[1] == null) {
//            if (idPhotoB.isEmpty()) {
//                Toast.makeText(this, "请上传反面身份证图片", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }
//        user_id
//                mechanism_name
//        mechanism_city
//                mechanism_ctime
//        id_number
//                mechanism_type
//
//        qualification_prove
//                mid_photo
//        sd_city
//                sd_content
        final HashMap<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(personId)) {
            params.put("id_number", personId);
        }
        params.put("user_id", userId);//用户Id
        params.put("mechanism_name", name);//机构名称
        params.put("mechanism_city", city);//机构所在城市
        params.put("mechanism_ctime", time);//成立时间
//        params.put("id_number", personId);//身份证号
        params.put("mechanism_type", type);//机构类型
        final JSONObject jsonObject = new JSONObject();
        JSONObject json2 = new JSONObject();
        JSONObject json3 = new JSONObject();
        try {
            if (photoStr.size() > 0) {
                for (int i = 0; i < photoStr.size(); i++) {
                    try {
                        Bitmap usericon = photoStr.get(i);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        usericon.compress(Bitmap.CompressFormat.JPEG, 40, baos);
                        byte[] b = baos.toByteArray();
                        String string = Base64.encodeToString(b, Base64.DEFAULT);
                        jsonObject.put("image" + i, string);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                params.put("qualification_prove", jsonObject.toString());//资质证明
            }else{
                params.put("qualification_prove", "");//资质证明
            }

            if (idencardStr[0] != null && idencardStr[1] != null) {
                Bitmap usericon1 = idencardStr[0];
                ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                usericon1.compress(Bitmap.CompressFormat.JPEG, 40, baos1);
                byte[] b1 = baos1.toByteArray();
                String string1 = Base64.encodeToString(b1, Base64.DEFAULT);
                json2.put("photo0", string1);

                Bitmap usericon2 = idencardStr[1];
                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                usericon2.compress(Bitmap.CompressFormat.JPEG, 40, baos2);
                byte[] b2 = baos2.toByteArray();
                String string2 = Base64.encodeToString(b2, Base64.DEFAULT);
                json3.put("photo1", string2);

                params.put("mid_photo", json2.toString());//身份证照片
                params.put("mid_photos", json3.toString());//身份证照片

            } else if (idencardStr[0] == null && idencardStr[1] != null) {
                Bitmap usericon2 = idencardStr[1];
                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                usericon2.compress(Bitmap.CompressFormat.JPEG, 40, baos2);
                byte[] b2 = baos2.toByteArray();
                String string2 = Base64.encodeToString(b2, Base64.DEFAULT);
                json3.put("photo1", string2);
                params.put("mid_photos", json3.toString());
            } else if (idencardStr[0] != null && idencardStr[1] == null) {
                Bitmap usericon1 = idencardStr[0];
                ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                usericon1.compress(Bitmap.CompressFormat.JPEG, 40, baos1);
                byte[] b1 = baos1.toByteArray();
                String string1 = Base64.encodeToString(b1, Base64.DEFAULT);
                json2.put("photo0", string1);
                params.put("mid_photo", json2.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        params.put("sd_city", address);//资质证明
        params.put("sd_content", street);//资质证明
        params.put("jing", longitude + "");//资质证明
        params.put("wei", latitude + "");//资质证明
        params.put("school_jing", longitude + "");//资质证明
        params.put("school_wei", latitude + "");//资质证明

        if (TextUtils.isEmpty(longitude) || "null".equals(longitude)) {
            Toast.makeText(this, "地址信息不完整，请重新填写", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "";
        if (flag.equals("0")) {
            url = Internet.COMMITINFO;
        } else {
            url = Internet.COMMITINFOS;
            params.put("mechanism_address", mechanism_address);//资质证明
        }
        Log.e("aaa",
                "(AddDataActivity.java:204)params ==== ==== ==" + params);

        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(AddDataActivity.java:211)" + e.getMessage());
                        dialog1.dismiss();
                        Toast.makeText(AddDataActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("aaa",
                                "(AddDataActivity.java:218)" + response);
                        dialog1.dismiss();

                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
//                            String msg = jsonObject1.getString("msg");
                            Toast.makeText(AddDataActivity.this, "欢迎您来到掌上私塾，我们将在72小时内完成审核，敬请关注！", Toast.LENGTH_LONG).show();
                            if (jsonObject1.getInt("result") == 0) {
//                                MyUtiles.saveBeanByFastJson(AddDataActivity.this, "userId", user_id1);
//                                UserBean userBean = new Gson().fromJson(dataStr.toString(), UserBean.class);
//                                SPUtils.put(AddDataActivity.this, "userId", userBean.getUser_id());
//                                SPUtils.put(AddDataActivity.this, "user_type", userBean.getUser_type());
//                                SPUtils.put(AddDataActivity.this, "user_phone", phone);
//                                SPUtils.put(AddDataActivity.this, "isLogin", false);
//                                SPUtils.put(AddDataActivity.this, "flag", "1");
                                if (flag.equals("1")) {
                                    finish();
                                } else {

                                    startActivity(new Intent(AddDataActivity.this, RegisterAdActivity.class)
                                            .putExtra("from", "register")
                                            .putExtra("phone", phone)
                                            .putExtra("password", password)
                                    );
                                }

                            } else {
                                String msg = jsonObject1.getString("msg");
                                Toast.makeText(AddDataActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AddDataActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

    }

    private void registerCom() {

        String name = etName.getText().toString().trim();//机构名称
        String city = tvCity.getText().toString().trim();//机构所在城市
        String personId = etPersonId.getText().toString().trim();//机构身份证号
        String mechianType = tvType.getText().toString().trim();//机构类型
//        String address = tvAddress.getText().toString().trim();//机构所在地址
        String time = etTime.getText().toString().trim();//机构所在地址


        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(city)
                || TextUtils.isEmpty(mechianType) || TextUtils.isEmpty(address) || TextUtils.isEmpty(street) || TextUtils.isEmpty(time)) {
            Toast.makeText(this, "输入信息不完整", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(longitude) || "null".equals(longitude)) {
            Toast.makeText(this, "地址信息不完整，请重新填写", Toast.LENGTH_SHORT).show();
            return;
        }

//        if (photoStr.size() < 1) {
//            if (QuPhoto.isEmpty()) {
//                Toast.makeText(this, "请上传资质证明", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }
//        if (idencardStr[0] == null) {
//            if (idPhotoF.isEmpty()) {
//                Toast.makeText(this, "请上传正面身份证图片", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }
//        if (idencardStr[1] == null) {
//            if (idPhotoB.isEmpty()) {
//                Toast.makeText(this, "请上传反面身份证图片", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }
//        String personId = etPersonId.getText().toString().trim();//机构身份证号
//        try {
//            String s = IDCard.IDCardValidate(personId);
//            if (s.equals("")) {
//            } else {
//                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
//                dialog1.dismiss();
//                return;
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        dialog1.show();
        HashMap<String, String> params = new HashMap<>();

        params.put("user_phone", phone);
        params.put("user_password", password);
        params.put("user_code", code);
        params.put("user_type", type);
        Log.e("aaa", "(AddDataActivity.java:620)<---->" + params.toString());
        OkHttpUtils.post()
                .url(Internet.REGISTER)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(AddDataActivity.java:346)<---->" + e.getMessage());
                        dialog1.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AddDataActivity.java:352)<---->" + response);

                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                            Toast.makeText(AddDataActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                            dialog1.dismiss();
                        } else {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                int result = jsonObject.getInt("result");
                                if (result == 0) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    user_id1 = data.getString("user_id");
                                    dataStr = data.toString();
                                    addData(user_id1, "0");
                                } else {
                                    dialog1.dismiss();
                                    String msg = jsonObject.getString("msg");
                                    Toast.makeText(AddDataActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                });
    }

    public void single(int flag) {
        ISListConfig config = new ISListConfig.Builder()
                // 是否多选
                .multiSelect(false)
                .btnText("Confirm")
                // 确定按钮背景色
                //.btnBgColor(Color.parseColor(""))
                // 确定按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#3F51B5"))
                // 返回图标ResId
                .title("Images")
                .titleColor(Color.WHITE)
                .titleBgColor(Color.parseColor("#3F51B5"))
                .allImagesText("All Images")
                .needCrop(false)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(1)
                .build();

        ISNav.getInstance().toListActivity(this, config, flag);
    }

    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {


        choosecity = (province == null ? "" : province.name);
        Log.e("aaa",
                "(AddAddressActivity.java:156)" + choosecity);

        provinceCode = (province == null ? "" : province.code);
        cityCode = (city == null ? "" : city.code);
        countyCode = (county == null ? "" : county.code);
        streetCode = (street == null ? "" : street.code);

        pName = province == null ? "" : province.name;
        cName = city == null ? "" : city.name;
        coName = county == null ? "" : county.name;


        chooseStreets = (city == null ? "" : city.name) + (county == null ? "" : county.name) +
                (street == null ? "" : street.name);
        if ("天津市北京市重庆市上海市".contains(pName)) {
            coName = cName;
            cName = pName;
        }

        String s = pName + cName + coName;
        tvCity.setText(s);
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void dialogclose() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 设置标签与别名
     */
    private void setTagAndAlias(String alias) {
        /**
         *这里设置了别名，在这里获取的用户登录的信息
         *并且此时已经获取了用户的userId,然后就可以用用户的userId来设置别名了
         **/
        //false状态为未设置标签与别名成功
        //if (UserUtils.getTagAlias(getHoldingActivity()) == false) {
        Set<String> tags = new HashSet<String>();
        //这里可以设置你要推送的人，一般是用户uid 不为空在设置进去 可同时添加多个
        if (!TextUtils.isEmpty(alias)) {
            tags.add(alias);//设置tag
        }
        //上下文、别名【Sting行】、标签【Set型】、回调
        JPushInterface.setAliasAndTags(this, alias, tags,
                mAliasCallback);
        // }
    }





}
