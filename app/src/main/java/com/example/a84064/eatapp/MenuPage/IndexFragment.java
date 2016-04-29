package com.example.a84064.eatapp.MenuPage;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.a84064.eatapp.Adapter.ShopAdapter;
import com.example.a84064.eatapp.Model.Shop_Type;
import com.example.a84064.eatapp.Model.UrlText;
import com.example.a84064.eatapp.Model.simple_shop;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.View.DoubleListFilterView;
import com.example.a84064.eatapp.View.ExpandMenuView;
import com.example.a84064.eatapp.View.SingleListFilterView;
import com.example.a84064.eatapp.WebServices.ShopWebService;
import com.example.a84064.eatapp.util.SharedPreferenceUtil;
import com.example.a84064.eatapp.util.SortAlgorithm;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by 84064 on 2016/3/30.
 */
public class IndexFragment extends Fragment implements BDLocationListener,OnGetGeoCoderResultListener,View.OnClickListener,View.OnFocusChangeListener {
    private View view;
    private TextView tvMainPoint;
    private PtrFrameLayout rotateHeaderListViewFrame;
    private EditText etIndexSearch;
    private RecyclerView rvList;
    private TextView tvIndexNull;
    private Button btnIndexSearch;

    private LocationClient mLocClient;
    private LatLng locationLatLng;      //定位坐标
    private GeoCoder geoCoder;      //反地理编码
    private String address;
    private String result;
    UrlText u=new UrlText();

    //List数据
    private List<simple_shop> shopes;
    //RecyclerView自定义Adapter
    private ShopAdapter mAdapter;
    /**
     * 可扩展的条件筛选菜单组合控件
     */
    private ExpandMenuView expandTabView;
    /**
     * 筛选条件视图集合
     */
    private ArrayList<View> mViewArray;
    private BaseAdapter adapter;
     //城市筛选条件数据
    private List<Shop_Type> allTypes;
     //筛选条件数据
    private List<Shop_Type.type_value> selectList=new ArrayList<>();
     //排序条件数据
    private List<Shop_Type.type_value> sortList=new ArrayList<>();

    /**
     * 筛选后的数据
     */
    private List<simple_shop> items=new ArrayList<>();
    private ArrayList<Shop_Type.type_value> superItemDatas;

    // 筛选条件
    private String typeName = null;
    private String selectName = null;
    private String sortName = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_index, container, false);
        SharedPreferenceUtil.initPreference(getActivity().getApplicationContext());
        SDKInitializer.initialize(getActivity().getApplicationContext());
        init();
        return view;
        //SharedPreferenceUtil.putString("position","30.30672532445859, 120.38514628910656");

    }

    public void init(){
        btnIndexSearch = (Button) view.findViewById(R.id.btn_index_search);
        btnIndexSearch.setOnClickListener(this);
        expandTabView = (ExpandMenuView) view.findViewById(R.id.expandTabView);
        tvIndexNull = (TextView) view.findViewById(R.id.tv_index_null);
        tvMainPoint = (TextView) view.findViewById(R.id.tv_main_point);
        rvList = (RecyclerView) view.findViewById(R.id.rv_list);
        rotateHeaderListViewFrame = (PtrFrameLayout) view.findViewById(R.id.rotate_header_list_view_frame);
        etIndexSearch = (EditText) view.findViewById(R.id.et_index_search);
        etIndexSearch.setOnFocusChangeListener(this);
        tvMainPoint.setOnClickListener(this);
        //初始化定位
        mLocClient = new LocationClient(getActivity());
        //注册定位监听
        mLocClient.registerLocationListener(this);
        LocationClientOption locOption = new LocationClientOption();
        locOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        locOption.setCoorType("bd09ll");// 设置定位结果类型
        locOption.setScanSpan(0);// 设置发起定位请求的间隔时间,ms
        locOption.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        locOption.setNeedDeviceDirect(true);// 设置返回结果包含手机的方向


        mLocClient.setLocOption(locOption);
        mLocClient.start();

        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));

        //设置下拉刷新样式
        StoreHouseHeader header = new StoreHouseHeader(getActivity());
        header.setBackgroundColor(Color.BLACK);
        header.setPadding(0, LocalDisplay.dp2px(20), 0, LocalDisplay.dp2px(20));
        header.initWithString("Panda Coming");
        rotateHeaderListViewFrame.setDurationToCloseHeader(1500);
        rotateHeaderListViewFrame.setHeaderView(header);
        rotateHeaderListViewFrame.addPtrUIHandler(header);
        rotateHeaderListViewFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, rvList, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                rotateHeaderListViewFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rotateHeaderListViewFrame.refreshComplete();
                    }
                }, 1500);
            }
        });
        rotateHeaderListViewFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                rotateHeaderListViewFrame.autoRefresh();
            }
        }, 100);

    }

    //获取店铺列表
    public void getShop(){
        ShopWebService esc=new ShopWebService();
        try
        {
            shopes=esc.getIndexList(locationLatLng,"-1");
            if(shopes!=null) {
                result = null;
            }
            else
                result="附近没有外卖商家";
        }catch(Exception ex)
        {
            result=ex.toString();
        }
        handlerShow.sendEmptyMessage(0);
    }
    Handler handlerShow = new Handler() {
        public void handleMessage(Message msg) {
            if(!TextUtils.isEmpty(result)) {
                tvIndexNull.setVisibility(View.VISIBLE);
                rvList.setVisibility(View.GONE);
            }
            else{
                tvIndexNull.setVisibility(View.GONE);
                rvList.setVisibility(View.VISIBLE);
                mAdapter=new ShopAdapter(getActivity(),shopes,0);
                rvList.setAdapter(mAdapter);
                initData();
            }
        }
    };
    /**
     * 定位监听
     *
     * @param bdLocation
     */
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation == null) {
            return;
        }
        locationLatLng=new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        new Thread(new Runnable() {
            public void run() {
                getShop();
            }
        }).start();
        geoCoder = GeoCoder.newInstance();
        //发起反地理编码请求(经纬度->地址信息)
        ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption();
        //设置反地理编码位置坐标
        reverseGeoCodeOption.location(locationLatLng);
        geoCoder.reverseGeoCode(reverseGeoCodeOption);

        //设置查询结果监听者
        geoCoder.setOnGetGeoCodeResultListener(this);

    }

    //地理编码查询结果回调函数
    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
    }

    //反地理编码查询结果回调函数
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            // 没有检测到结果
            tvMainPoint.setText("定位失败");
        }
        address=reverseGeoCodeResult.getAddress();
        tvMainPoint.setText(address);
        SharedPreferenceUtil.putString("point", u.LatlngToString(locationLatLng));
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_main_point:
                Intent inten=new Intent(getActivity(),ChangePointActivity.class);
                startActivityForResult(inten,0);
                break;
            case R.id.btn_index_search:{
                if(!TextUtils.isEmpty(etIndexSearch.getText())) {
                    btnIndexSearch.setVisibility(View.GONE);
                    etIndexSearch.clearFocus();
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    String lating=u.LatlngToString(locationLatLng);
                    intent.putExtra(SearchActivity.SEARCH_TEXT, etIndexSearch.getText().toString());
                    intent.putExtra(SearchActivity.SEARCH_LOCATION,lating);
                    etIndexSearch.setText("");
                    startActivity(intent);
                }
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(data==null){
            return;
        }
        address=data.getStringExtra(ChangePointActivity.POINT_NAME);
        String[] point=data.getStringExtra(ChangePointActivity.POINT_LOCAL).split(",");
        locationLatLng=new LatLng(Double.valueOf(point[0]),Double.valueOf(point[1]));
        new Thread(new Runnable() {
            public void run() {
                getShop();
            }
        }).start();
    }
    @Override
    public void onResume() {
        super.onResume();
        if(locationLatLng!=null) {
            SharedPreferenceUtil.putString("point", u.LatlngToString(locationLatLng));
        }
        handlerShowStreet.sendEmptyMessage(0);
    }

    Handler handlerShowStreet = new Handler() {
        public void handleMessage(Message msg) {
            tvMainPoint.setText(address);
        }
    };
    private void initData() {
        allTypes = new ArrayList<Shop_Type>();

        for(int i=0;i<shopes.size();i++){
            if(allTypes.size()==0){
                allTypes.add(new Shop_Type("全部商家",shopes.size()));
            }
            if(allTypes.size()==1){
                List<simple_shop.TypeBean> type=shopes.get(i).getType();
                for(int j=0;j<type.size();j++){
                    if(j==0)
                        allTypes.add(new Shop_Type(type.get(j).getType_name(), "全部"+type.get(j).getType_name()));
                    else
                        allTypes.get(1).addValue(type.get(j).getType_value());
                }
                continue;
            }
            else{
                List<simple_shop.TypeBean> type=shopes.get(i).getType();
                for(int j=0;j<type.size();j++){
                    boolean r=true;
                    for(int m=0;m<allTypes.size();m++) {
                        if (allTypes.get(m).getAlltype().getName().equals(type.get(j).getType_name())) {
                            allTypes.get(m).addType();
                            allTypes.get(m).getTypies().get(0).add();
                            for(int n=0;n<type.get(j).getType_value().size();n++) {
                                allTypes.get(m).checkAndAdd(type.get(j).getType_value().get(n));
                            }
                            r=true;
                            break;
                        }
                        else {
                            r=false;
                        }
                    }
                    if(!r){
                        allTypes.add(new Shop_Type(type.get(j).getType_name(), "全部"+type.get(j).getType_name()));
                        allTypes.get(allTypes.size()-1).addValue(type.get(j).getType_value());
                    }
                }
            }
        }
        superItemDatas = new ArrayList<Shop_Type.type_value>();
        SparseArray<List<Shop_Type.type_value>> children = new SparseArray<List<Shop_Type.type_value>>();

        // 提取并设置数据
        for (int i = 0; i < allTypes.size(); i++) {
            superItemDatas.add(allTypes.get(i).getAlltype());
            List<Shop_Type.type_value> values = allTypes.get(i).getTypies();
            List<Shop_Type.type_value> items = new ArrayList<Shop_Type.type_value>();
            if (values == null || values.size() == 0) {
                children.put(i, null);
                continue;
            }
            for (int j = 0; j < values.size(); j++) {
                items.add(values.get(j));
            }
            children.put(i, items);
        }

        final DoubleListFilterView cityFilterView = new DoubleListFilterView(getActivity(), "分类", superItemDatas, children, 0, 0);
        cityFilterView.setOnSelectListener(new DoubleListFilterView.OnSelectListener() {

            @Override
            public void getValue(String showText, int superPosition, int position) {
                refreshScreen(cityFilterView, showText, superPosition, position);
            }
        });

        // 排序
        selectList.clear();
        selectList.add(new Shop_Type.type_value("智能排序",0));
        selectList.add(new Shop_Type.type_value("销量最高",0));
        selectList.add(new Shop_Type.type_value("距离最近",0));
        selectList.add(new Shop_Type.type_value("评分最高",0));
        selectList.add(new Shop_Type.type_value("配送速度最快",0));

        final SingleListFilterView gradFilterView = new SingleListFilterView(getActivity(), selectList, "排序");
        gradFilterView.setOnSelectListener(new SingleListFilterView.OnSelectListener() {
            @Override
            public void getValue(String showText, int position) {
                refreshScreen(gradFilterView, showText, -1, position);
            }
        });

        // 筛选
        sortList.clear();
        sortList.add(new Shop_Type.type_value("满减优惠",0));
        sortList.add(new Shop_Type.type_value("免配送费",0));
        sortList.add(new Shop_Type.type_value("新用户立减",0));
        final SingleListFilterView sortFilterView = new SingleListFilterView(getActivity(),sortList , "筛选");
        sortFilterView.setOnSelectListener(new SingleListFilterView.OnSelectListener() {

            @Override
            public void getValue(String showText, int position) {
                refreshScreen(sortFilterView, showText, -1, position);
            }
        });

        //添加条件筛选控件到数据集合中
        mViewArray = new ArrayList<View>();
        mViewArray.add(cityFilterView);
        mViewArray.add(gradFilterView);
        mViewArray.add(sortFilterView);

        ArrayList<String> mTextArray = new ArrayList<String>();
        mTextArray.add("分类");
        mTextArray.add("排序");
        mTextArray.add("筛选");

        //给组合控件设置数据
        expandTabView.setValue(mTextArray, mViewArray);

        //处理组合控件按钮点击事件
        expandTabView.setOnButtonClickListener(new ExpandMenuView.OnButtonClickListener() {

            @Override
            public void onClick(int selectPosition, boolean isChecked) {
                // TODO Auto-generated method stub
            }
        });
    }
    /**
     * 更新筛选条件
     * @param view
     * @param showText
     * @param superPosition
     * @param pos 选中的位置
     */
    private void refreshScreen(View view, String showText, int superPosition, int pos){
        expandTabView.closeView();
        int position = getPositon(view);
        if (position >= 0)
            expandTabView.setTitle(showText, position);

        items.clear();
        switch (position) {
            case 0:// 分类
                if(superPosition == 0){
                    typeName = null;
                    break;
                }
                Shop_Type type = allTypes.get(superPosition);
                List<Shop_Type.type_value> values = type.getTypies();
                if(values == null || values.size() == 0 || pos == 0){
                    typeName = type.getAlltype().getName();
                    break;
                }
                typeName = values.get(pos).getName();
                break;
            case 1:// 排序
                if(pos == 0){
                    selectName = null;
                    break;
                }
                selectName=selectList.get(pos).getName();
                break;
            case 2:// 筛选
                sortName = sortList.get(pos).getName();
                break;
        }
        for (int i = 0; i < shopes.size(); i++) {
            boolean isChoose = false;
            boolean isSelect = false;
            simple_shop item = shopes.get(i);
            // 筛选城市
            if(typeName != null){
                for(int m=0;m<item.getType().size();m++) {
                    boolean r=false;
                    String t_name = item.getType().get(m).getType_name();
                    List<String> t_value = item.getType().get(m).getType_value();
                    if (typeName.equals(t_name)) {
                        isChoose = true;
                        break;
                    }
                    for (int j = 0; j < t_value.size(); j++) {
                        if (typeName.equals(t_value.get(j))) {
                            isChoose = true;
                            r=true;
                            break;
                        }
                    }
                    if(r)
                        break;
                }
            }else {
                isChoose = true;
            }
            // 筛选等级
            if(selectName != null){
                List<simple_shop.DiscountBean> discount = item.getDiscount();
                for(int j=0;j<discount.size();j++){
                    if(discount.get(j).getDiacount_name().equals(selectName)) {
                        isSelect = true;
                        break;
                    }
                }
            }else{
                isSelect = true;
            }
            if(isChoose && isSelect){
                items.add(item);
            }
        }
        // 排序
        if(sortName != null){
            double[] condition = new double[items.size()];
            String t="asc";
            for (int i = 0; i < items.size(); i++) {
                double number=0;
                switch (sortName){
                    case "智能排序":
                        number=items.get(i).getShopId();
                        break;
                    case "销量最高":
                        number=items.get(i).getSoldNum();
                        t="desc";
                        break;
                    case "距离最近":
                        number=items.get(i).getDistint();
                        break;
                    case "评分最高":
                        number=items.get(i).getScore();
                        t="desc";
                        break;
                    case "配送速度最快":
                        number=items.get(i).getShopId();
                        break;
                }
                condition[i] = number;
            }
            SortAlgorithm.selectSort(items, condition, t);
        }
        mAdapter=new ShopAdapter(getActivity(),items,0);
        rvList.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged();
    }

    /**
     * 获取当前点击的位置
     *
     * @param tView
     * @return
     */
    private int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView)
                return i;
        }
        return -1;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(v.getId()==R.id.et_index_search){
            if(hasFocus)
                btnIndexSearch.setVisibility(View.VISIBLE);
            else
                btnIndexSearch.setVisibility(View.GONE);
        }
    }
}
