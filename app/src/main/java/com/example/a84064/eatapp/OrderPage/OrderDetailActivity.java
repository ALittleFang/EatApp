package com.example.a84064.eatapp.OrderPage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a84064.eatapp.Adapter.OrderMenuAdapter;
import com.example.a84064.eatapp.MenuPage.ComplainActivity;
import com.example.a84064.eatapp.MenuPage.MainShopActivity;
import com.example.a84064.eatapp.Model.orderList;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.WebServices.ShopWebService;
import com.google.gson.Gson;

/**
 * Created by 84064 on 2016/4/11.
 */
public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton imgOrderDetailBack;
    private ImageButton imgOrderDetailPhone;
    private ListView lvOrderMenu;
    private TextView tvOrderDetailShopName;
    private TextView tvOrderDetailDistriPrice;
    private RelativeLayout orderDetailLayoutDiscount;
    private TextView tvOrderDetailDiacount;
    private RelativeLayout orderDetailLayoutLuvkyBug;
    private TextView tvOrderDetailLuckyBug;
    private TextView tvOrderDetailPrice;
    private TextView tvOrderDetailTotalPrice;
    private TextView tvOrderName;
    private TextView tvOrderTel;
    private TextView tvOrderAddress;
    private TextView tvOrderPayMode;
    private TextView tvOrderOrderTime;
    private TextView tvOrderHopeTime;
    private TextView tvOrderTip;
    private ImageButton ordersImgBtnBack;
    private RelativeLayout orderdetailLayoutTop;
    private Button btnOrderDetailOperate;
    private RelativeLayout layoutOrderDetailSend;

    public static String ORDER_DETAIL_ORDER="order_detail_order";
    private orderList order;
    private boolean shop=true;
    private static final String ACTIVITY_TAG="LogDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        init();
    }

    public void init(){
        imgOrderDetailBack = (ImageButton) findViewById(R.id.img_orderDetail_back);
        imgOrderDetailPhone = (ImageButton) findViewById(R.id.img_orderDetail_phone);
        tvOrderDetailShopName = (TextView) findViewById(R.id.tv_orderDetail_shopName);
        lvOrderMenu = (ListView) findViewById(R.id.lv_order_menu);
        tvOrderDetailDistriPrice = (TextView) findViewById(R.id.tv_orderDetail_distriPrice);
        orderDetailLayoutDiscount = (RelativeLayout) findViewById(R.id.orderDetail_layout_discount);
        tvOrderDetailDiacount = (TextView) findViewById(R.id.tv_orderDetail_diacount);
        orderDetailLayoutLuvkyBug = (RelativeLayout) findViewById(R.id.orderDetail_layout_luvkyBug);
        tvOrderDetailLuckyBug = (TextView) findViewById(R.id.tv_orderDetail_luckyBug);
        tvOrderDetailPrice = (TextView) findViewById(R.id.tv_orderDetail_price);
        tvOrderDetailTotalPrice = (TextView) findViewById(R.id.tv_orderDetail_totalPrice);
        tvOrderName = (TextView) findViewById(R.id.tv_order_name);
        tvOrderTel = (TextView) findViewById(R.id.tv_order_tel);
        tvOrderAddress = (TextView) findViewById(R.id.tv_order_address);
        tvOrderPayMode = (TextView) findViewById(R.id.tv_order_payMode);
        tvOrderOrderTime = (TextView) findViewById(R.id.tv_order_orderTime);
        tvOrderHopeTime = (TextView) findViewById(R.id.tv_order_hopeTime);
        tvOrderTip = (TextView) findViewById(R.id.tv_order_tip);
        orderdetailLayoutTop = (RelativeLayout) findViewById(R.id.orderdetail_layout_top);
        btnOrderDetailOperate = (Button) findViewById(R.id.btn_orderDetail_operate);
        layoutOrderDetailSend = (RelativeLayout) findViewById(R.id.layout_orderDetail_send);

        imgOrderDetailBack.setOnClickListener(this);
        orderdetailLayoutTop.setOnClickListener(this);
        imgOrderDetailPhone.setOnClickListener(this);
        btnOrderDetailOperate.setOnClickListener(this);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            String json = bundle.getString(ORDER_DETAIL_ORDER);
            Gson g=new Gson();
            order=g.fromJson(json,orderList.class);
            handlerInfo.sendEmptyMessage(0);
        }
    }
    Handler handlerInfo = new Handler() {
        public void handleMessage(Message msg) {
            OrderMenuAdapter oa = new OrderMenuAdapter(OrderDetailActivity.this, order.getMenuList());
            lvOrderMenu.setAdapter(oa);
            tvOrderAddress.setText("收货地址："+order.getAccountAddress());
            tvOrderDetailShopName.setText(order.getShopName());
            tvOrderDetailTotalPrice.setText("总计"+String.valueOf(order.getTotalPrice())+"元");
            tvOrderHopeTime.setText("送达时间："+order.getHopeTime());
            tvOrderName.setText("联系人："+order.getAccountName());
            tvOrderOrderTime.setText("下单时间："+order.getOrderTime());
            tvOrderPayMode.setText("支付方式："+order.getOrderPayMode());
            tvOrderTel.setText("联系电话："+order.getAccountTel());
            tvOrderTip.setText("备注："+order.getAccountTip());
            if(!TextUtils.isEmpty(order.getDiscount())){
                orderDetailLayoutDiscount.setVisibility(View.VISIBLE);
                tvOrderDetailDiacount.setText(order.getDiscount().split(";")[0]);
            }
            else
                orderDetailLayoutDiscount.setVisibility(View.GONE);
            if(!TextUtils.isEmpty(String.valueOf(order.getLuckyBug()))){
                orderDetailLayoutLuvkyBug.setVisibility(View.VISIBLE);
                tvOrderDetailLuckyBug.setText(order.getLuckyBug());
            }
            else
                orderDetailLayoutLuvkyBug.setVisibility(View.GONE);
            if(order.getDistrui_price()==0)
                layoutOrderDetailSend.setVisibility(View.GONE);
            else {
                layoutOrderDetailSend.setVisibility(View.VISIBLE);
                tvOrderDetailDistriPrice.setText(String.valueOf(order.getDistrui_price()));
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_orderDetail_back:
                this.finish();
                break;
            case R.id.orderdetail_layout_top:
                new Thread(new Runnable() {
                    public void run() {
                        checkShop();
                    }
                }).start();
                break;
            case R.id.img_orderDetail_phone:
                break;
        }
    }
    public void checkShop(){
        ShopWebService swc=new ShopWebService();
        try
        {
            shop=swc.checkShop(order.getShopId());
            if(shop){
                Intent i=new Intent(OrderDetailActivity.this,MainShopActivity.class);
                i.putExtra(MainShopActivity.SHOP_RECEIVE,order.getShopId());
                startActivity(i);
            }
            else
                showDialog();
        }catch(Exception ex)
        {
            Log.e(ACTIVITY_TAG,ex.toString());
        }
    }
     public void showDialog(){
         new AlertDialog.Builder(this).setTitle(
                 "商家停业中，已停止接单").setPositiveButton("确定",
                 new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int whichButton) {
                     }
                 }).show();
     }
}
