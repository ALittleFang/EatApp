package com.example.a84064.eatapp.MenuPage;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.example.a84064.eatapp.Adapter.AddressAdapter;
import com.example.a84064.eatapp.Adapter.PoiSearchAdapter;
import com.example.a84064.eatapp.FragmentMainActivity;
import com.example.a84064.eatapp.Model.Address;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.WebServices.AddressWebServiceCall;
import com.example.a84064.eatapp.util.SharedPreferenceUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 84064 on 2016/4/16.
 */
public class ChangePointActivity extends AppCompatActivity implements BDLocationListener,View.OnClickListener,OnGetGeoCoderResultListener, TextWatcher {
    private ImageButton collectBack;
    private TextView tvLayoutTitle;
    private EditText etChangeLocalSearch;
    private LinearLayout layoutLocalAgain;
    private TextView tvChangeLocalLocalAddress;
    private ListView lvAddress;
    private ListView mainSearchPois;
    private LinearLayout layoutChangeLocalAddress;
    private Button btnChangePointExit;
    private TextView tvAddressNull;

    public static final String POINT_NAME="com.example.a84064.eatapp.ChangePoint.point_name";
    public static final String POINT_LOCAL="com.example.a84064.eatapp.ChangePoint.point_local";
    private static final String ACTIVITY_TAG="LogDemo";
    private Button bt;
    private int account_id;
    private String result;
    private List<Address> list=new ArrayList<>();
    private AddressAdapter my;
    private LocationClient mLocClient;
    private LatLng locationLatLng;      //定位坐标
    private GeoCoder geoCoder;      //反地理编码
    private String city;
    private String address;
    private String address_point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_changelocation);

        init();
    }
    public void init(){
        collectBack = (ImageButton) findViewById(R.id.collect_back);
        tvLayoutTitle = (TextView) findViewById(R.id.tv_layout_title);
        etChangeLocalSearch = (EditText) findViewById(R.id.et_changeLocal_search);
        layoutLocalAgain = (LinearLayout) findViewById(R.id.layout_localAgain);
        tvChangeLocalLocalAddress = (TextView) findViewById(R.id.tv_changeLocal_localAddress);
        lvAddress = (ListView) findViewById(R.id.lv_address);
        mainSearchPois = (ListView) findViewById(R.id.main_search_pois);
        layoutChangeLocalAddress = (LinearLayout) findViewById(R.id.layout_changeLocal_address);
        btnChangePointExit = (Button) findViewById(R.id.btn_changePoint_exit);
        tvAddressNull = (TextView) findViewById(R.id.tv_address_null);

        collectBack.setOnClickListener(this);
        layoutLocalAgain.setOnClickListener(this);
        btnChangePointExit.setOnClickListener(this);

        //初始化定位
        mLocClient = new LocationClient(ChangePointActivity.this);
        //注册定位监听
        mLocClient.registerLocationListener(this);
        LocationClientOption locOption = new LocationClientOption();
        locOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        locOption.setCoorType("bd09ll");// 设置定位结果类型
        locOption.setScanSpan(1000);// 设置发起定位请求的间隔时间,ms
        locOption.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        locOption.setNeedDeviceDirect(true);// 设置返回结果包含手机的方向

        mLocClient.setLocOption(locOption);
        mLocClient.start();

        account_id= SharedPreferenceUtil.getInt("id", 0);
        new Thread(new Runnable() {
            public void run() {
                getAddress();
            }
        }).start();
    }
    public void getAddress(){
        if(account_id==0)
            result="null";
        else {
            AddressWebServiceCall esc = new AddressWebServiceCall();
            try {
                list=esc.getAddressList(account_id);
                result = "ok";
                if(list.size()==0)
                    result="null";
            } catch (Exception ex) {
                Log.e(this.ACTIVITY_TAG, ex.toString());
            }
        }
        handlerShow.sendEmptyMessage(0);
    }
    Handler handlerShow = new Handler() {
        public void handleMessage(Message msg) {
            tvLayoutTitle.setText("选择收货地址");
            switch(result){
                case "null":
                    tvAddressNull.setVisibility(View.VISIBLE);
                    lvAddress.setVisibility(View.GONE);
                    break;
                case "ok":
                    tvAddressNull.setVisibility(View.GONE);
                    lvAddress.setVisibility(View.VISIBLE);
                    final AddressAdapter addressAdapter = new AddressAdapter(ChangePointActivity.this, list);
                    lvAddress.setAdapter(addressAdapter);
                    lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                            address =list.get(arg2).getStreet();
                            address_point = list.get(arg2).getAddress_point();
                            setPointResult();
                        }
                    });
                    break;
            }
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.collect_back:
                this.finish();
                break;
            case R.id.layout_localAgain:
                Intent i=new Intent(ChangePointActivity.this, FragmentMainActivity.class);
                startActivity(i);
                break;
            case R.id.btn_changePoint_exit:
                if (geoCoder != null) {
                    geoCoder.destroy();
                }
                etChangeLocalSearch.setText("");
                etChangeLocalSearch.clearFocus();
                layoutChangeLocalAddress.setVisibility(View.VISIBLE);
                mainSearchPois.setVisibility(View.GONE);
                btnChangePointExit.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            // 没有检测到结果
            tvChangeLocalLocalAddress.setText("定位失败");
        }
        tvChangeLocalLocalAddress.setText(reverseGeoCodeResult.getAddress());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        btnChangePointExit.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0 || "".equals(s.toString())) {
            mainSearchPois.setVisibility(View.GONE);
        } else {
            //创建PoiSearch实例
            PoiSearch poiSearch = PoiSearch.newInstance();
            //城市内检索
            PoiCitySearchOption poiCitySearchOption = new PoiCitySearchOption();
            //关键字
            poiCitySearchOption.keyword(s.toString());
            //城市
            poiCitySearchOption.city(city);
            //设置每页容量，默认为每页10条
            poiCitySearchOption.pageCapacity(10);
            //分页编号
            poiCitySearchOption.pageNum(1);
            poiSearch.searchInCity(poiCitySearchOption);
            //设置poi检索监听者
            poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
                //poi 查询结果回调
                @Override
                public void onGetPoiResult(PoiResult poiResult) {
                    List<PoiInfo> poiInfos = poiResult.getAllPoi();
                    if(poiInfos!=null) {
                        final PoiSearchAdapter poiSearchAdapter = new PoiSearchAdapter(ChangePointActivity.this, poiInfos, locationLatLng);
                        mainSearchPois.setVisibility(View.VISIBLE);
                        layoutChangeLocalAddress.setVisibility(View.GONE);
                        mainSearchPois.setAdapter(poiSearchAdapter);
                        mainSearchPois.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                address = poiSearchAdapter.getStreet(arg2);
                                address_point = poiSearchAdapter.getPoint(arg2);
                                setPointResult();
                            }
                        });
                    }
                }
                //poi 详情查询结果回调
                @Override
                public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                }
            });
        }
    }

    private void setPointResult(){
        Intent data=new Intent();
        data.putExtra(POINT_NAME,address);
        data.putExtra(POINT_LOCAL,address_point);
        setResult(RESULT_OK,data);
        ChangePointActivity.this.finish();
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation == null) {
            return;
        }
        locationLatLng=new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        city = bdLocation.getCity();
        //文本输入框改变监听，必须在定位完成之后
        etChangeLocalSearch.addTextChangedListener(this);
        geoCoder = GeoCoder.newInstance();
        //发起反地理编码请求(经纬度->地址信息)
        ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption();
        //设置反地理编码位置坐标
        reverseGeoCodeOption.location(locationLatLng);
        geoCoder.reverseGeoCode(reverseGeoCodeOption);
        //设置查询结果监听者
        geoCoder.setOnGetGeoCodeResultListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //退出时停止定位
        mLocClient.stop();
        //释放资源
        if (geoCoder != null) {
            geoCoder.destroy();
        }
    }
}
