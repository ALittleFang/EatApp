package com.example.a84064.eatapp.MenuPage;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a84064.eatapp.Adapter.MenuAdapter;
import com.example.a84064.eatapp.Model.Account_Cart;
import com.example.a84064.eatapp.Model.Menu_Info;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.View.ListPopup;
import com.example.a84064.eatapp.WebServices.OrderWebService;
import com.example.a84064.eatapp.WebServices.ShopWebService;
import com.example.a84064.eatapp.util.CommonPopupWindow;
import com.example.a84064.eatapp.util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 84064 on 2016/4/24.
 */
public class SearchMenuActivity extends AppCompatActivity implements View.OnClickListener{
    public static String SEARCH_SHOP_ID="search_shop_id";
    private ImageButton imgSearchBack;
    private EditText etSearch;
    private Button btnSearch;
    private ListView lvSearchMenu;
    private LinearLayout layoutSearchNull;
    private RelativeLayout layoutBottom;
    private ImageView imgCart;
    private TextView tvCartNum;
    private TextView tvCartTotalPrice;
    private TextView tvCartStartingPrice;
    private Button btnCartBuy;

    private List<Menu_Info> menus=new ArrayList<>();
    private String result;
    private MenuAdapter mAdapter;
    private Account_Cart cart;
    private int shop_id;
    private int user_id;
    private Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchmenu);

        init();
    }

    public void init(){
        imgSearchBack = (ImageButton) findViewById(R.id.img_search_back);
        etSearch = (EditText) findViewById(R.id.et_search);
        btnSearch = (Button) findViewById(R.id.btn_search);
        lvSearchMenu = (ListView) findViewById(R.id.lv_searchMenu);
        layoutSearchNull = (LinearLayout) findViewById(R.id.layout_search_null);
        layoutBottom = (RelativeLayout) findViewById(R.id.layout_bottom);
        imgCart = (ImageView) findViewById(R.id.img_cart);
        tvCartNum = (TextView) findViewById(R.id.tv_cart_num);
        tvCartTotalPrice = (TextView) findViewById(R.id.tv_cart_totalPrice);
        tvCartStartingPrice = (TextView) findViewById(R.id.tv_cart_startingPrice);
        btnCartBuy = (Button) findViewById(R.id.btn_cart_buy);
        btnCartBuy.setOnClickListener(this);
        imgSearchBack.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        mContext = this;
        user_id = SharedPreferenceUtil.getInt("id", 0);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
            shop_id= bundle.getInt(SEARCH_SHOP_ID);
        new Thread(new Runnable() {
            public void run() {
                searchCart();
            }
        }).start();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_search_back:
                this.finish();
                break;
            case R.id.btn_search:
                if(TextUtils.isEmpty(etSearch.getText().toString())){

                }
                else {
                    final String name=etSearch.getText().toString();
                    etSearch.clearFocus();
                    new Thread(new Runnable() {
                        public void run() {
                            searchMenu(name);
                        }
                    }).start();
                }
                break;
            case R.id.layout_bottom:
                if (getPopup() != null) {
                    getPopup().showPopupWindow(layoutBottom);
                }
                break;
        }
    }

    public CommonPopupWindow getPopup() {
        ListPopup popup = new ListPopup(this, cart.getCart_menu());
        //popup.setOnListClickListener(this);
        return popup;
    }


    public void searchCart(){
        OrderWebService osc=new OrderWebService();
        try {
            cart=osc.getAccountCart(shop_id,user_id);
        }catch(Exception ex)
        {
            result=ex.toString();
        }
        handlerShow.sendEmptyMessage(0);
    }
    public void searchMenu(String name){
        ShopWebService esc=new ShopWebService();
        try
        {
            menus=esc.getSearchMenuList(name,shop_id,user_id);
            if(menus!=null) {
                result = null;
            }
            else
                result="null";
        }catch(Exception ex)
        {
            result=ex.toString();
        }
        handlerCart.sendEmptyMessage(0);
    }

    Handler handlerCart = new Handler() {
        public void handleMessage(Message msg) {
            if(cart.getTotal_price()==0){
                imgCart.setImageResource(R.mipmap.cart);
                tvCartTotalPrice.setText("您的购物车已经饿得咕咕叫了");
                tvCartStartingPrice.setVisibility(View.GONE);
                btnCartBuy.setVisibility(View.GONE);
                layoutBottom.setOnClickListener(null);
            }
            else{
                imgCart.setImageResource(R.mipmap.cart_have);
                tvCartNum.setText(String.valueOf(cart.getCart_menu().size()));
                tvCartTotalPrice.setText(Html.fromHtml(textColor("¥ "+String.valueOf(cart.getTotal_price()),"#ff4500")+
                        " | 另需配送费¥"+String.valueOf(cart.getSend_price())));
                if(cart.getTotal_price()<cart.getStart_price()){
                    tvCartStartingPrice.setText("还差"+String.valueOf(cart.getStart_price()-cart.getTotal_price())+"元");
                    tvCartStartingPrice.setVisibility(View.VISIBLE);
                    btnCartBuy.setVisibility(View.GONE);
                }
                else{
                    tvCartStartingPrice.setVisibility(View.GONE);
                    btnCartBuy.setVisibility(View.VISIBLE);
                }
                layoutBottom.setOnClickListener(SearchMenuActivity.this);
            }
        }
    };
    Handler handlerShow = new Handler() {
        public void handleMessage(Message msg) {
            if(!TextUtils.isEmpty(result)) {
                layoutSearchNull.setVisibility(View.VISIBLE);
                lvSearchMenu.setVisibility(View.GONE);
            }
            else{
                layoutSearchNull.setVisibility(View.GONE);
                lvSearchMenu.setVisibility(View.VISIBLE);
                mAdapter=new MenuAdapter(SearchMenuActivity.this,menus);
                lvSearchMenu.setAdapter(mAdapter);
            }
        }
    };

    public String textColor(String text,String color){
        return "<font color='"+color+"'><b>"+text+"</b></font>";
    }

}
