package com.example.a84064.eatapp.MenuPage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.a84064.eatapp.Model.Shop;
import com.example.a84064.eatapp.Model.UrlText;
import com.example.a84064.eatapp.Model.simple_shop;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.WebServices.ShopWebService;
import com.example.a84064.eatapp.util.SharedPreferenceUtil;
import com.example.a84064.eatapp.util.ToolImage;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by 84064 on 2016/4/23.
 */
public class MainShopActivity extends FragmentActivity implements View.OnClickListener {
    public static String SHOP_RECEIVE="shop_receive";
    public static String SHOP_COLLECT="shop_collect";
    private ImageView imgShopBack;
    private TextView tvShopShopName;
    private ImageView imgShopCollect;
    private ImageView imgShopSearch;
    private ImageView imgShopShopLogo;
    private TextView tvShopDetailInfo;
    private TextView tvShopMinus;
    private TextView tvShopFree;
    private TextView tvShopNew;
    private TextView tvShopNote;
    ViewPager viewPager;
    PagerSlidingTabStrip tabs;

    private boolean isCollect;
    private int shop_id;
    private int user_id;
    private simple_shop shop;
    private Shop shop_info;
    private String result;
    private String ret="null";
    UrlText u=new UrlText();
    private com.nostra13.universalimageloader.core.ImageLoader universalimageloader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_shop_main);

        init();
        viewPager = (ViewPager) findViewById(R.id.shop_pager);
        viewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager()));
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setDividerColor(Color.parseColor("#f08080"));
        tabs.setUnderlineHeight(1);
        tabs.setViewPager(viewPager);
    }

    public void init(){
        imgShopBack = (ImageView) findViewById(R.id.img_shop_back);
        tvShopShopName = (TextView) findViewById(R.id.tv_shop_shopName);
        imgShopCollect = (ImageView) findViewById(R.id.img_shop_collect);
        imgShopSearch = (ImageView) findViewById(R.id.img_shop_search);
        imgShopShopLogo = (ImageView) findViewById(R.id.img_shop_shopLogo);
        tvShopDetailInfo = (TextView) findViewById(R.id.tv_shop_detailInfo);
        tvShopMinus = (TextView) findViewById(R.id.tv_shop_minus);
        tvShopFree = (TextView) findViewById(R.id.tv_shop_free);
        tvShopNew = (TextView) findViewById(R.id.tv_shop_new);
        tvShopNote = (TextView) findViewById(R.id.tv_shop_note);
        imgShopBack.setOnClickListener(this);
        imgShopCollect.setOnClickListener(this);
        imgShopSearch.setOnClickListener(this);

        universalimageloader = ToolImage.initImageLoader(this);
        user_id = SharedPreferenceUtil.getInt("id", 0);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            shop_id= bundle.getInt(SHOP_RECEIVE);
            new Thread(new Runnable() {
                public void run() {
                    getShop();
                }
            }).start();
        }
    }

    public void getShop(){
        ShopWebService esc=new ShopWebService();
        try
        {
            if(user_id==0)
                isCollect=false;
            else
                isCollect=esc.collectManager(0,shop_id,user_id);
            Gson g = new Gson();
            String[] re=esc.getSimpleShopInfo(shop_id).split("#");
            shop=g.fromJson(re[0], simple_shop.class);
            shop_info=g.fromJson(re[1], Shop.class);
            if(shop!=null) {
                result = null;
            }
            else
                result="null";
        }catch(Exception ex)
        {
            result=ex.toString();
        }
        handlerShow.sendEmptyMessage(0);
    }
    Handler handlerShow = new Handler() {
        public void handleMessage(Message msg) {
            if(!TextUtils.isEmpty(result)) {

            }
            else{
                tvShopShopName.setText(shop.getName());
                universalimageloader.displayImage(u.getUrl()+"Img/"+shop.getLogo(), imgShopShopLogo, ToolImage.getFadeOptions(R.mipmap.default_icon,R.mipmap.ic_launcher,R.mipmap.ic_launcher));
                tvShopDetailInfo.setText(String.valueOf(shop.getStart_price())+"元起送 / "+
                        String.valueOf(shop.getSend_price())+"元配送 / "+ "40分钟送达");
                List<simple_shop.DiscountBean> discount=shop.getDiscount();
                for(int i=0;i<discount.size();i++){
                    switch (discount.get(i).getDiacount_name()){
                        case "满减优惠":
                            tvShopMinus.append("满" +String.valueOf(discount.get(i).getDiscount_condition())
                                    + "减"+String.valueOf(discount.get(i).getDiscount_price())+" ");
                            break;
                        case "新用户立减":
                            tvShopNew.setText("新用户立减"+ String.valueOf(discount.get(i).getDiscount_price())+"元");
                            break;
                        case "免配送费":
                            tvShopFree.setText("满"+String.valueOf(discount.get(i).getDiscount_condition())+"免配送费");
                            break;
                    }
                }
                //判断店铺是有有优惠，没有的话隐藏
                TextView[] v={tvShopMinus,tvShopNew,tvShopFree};
                checkShow(v);
                if(isCollect)
                    imgShopCollect.setImageResource(R.mipmap.collect);
                else
                    imgShopCollect.setImageResource(R.mipmap.collect_null);
                if(shop_info.getNotice()==null)
                    tvShopNote.setVisibility(View.GONE);
                else
                    tvShopNote.setText(String.valueOf(shop_info.getNotice()));
            }
        }
    };

    public void collectManager(int type){
        ShopWebService esc=new ShopWebService();
        try
        {
            esc.collectManager(type,shop_id,user_id);
        }catch(Exception ex)
        {
            result=ex.toString();
        }
        handlerCollectShow.sendEmptyMessage(0);
    }

    Handler handlerCollectShow = new Handler() {
        public void handleMessage(Message msg) {
            String r="";
            int picture_id=0;
            if(isCollect) {
                imgShopCollect.setImageResource(R.mipmap.collect);
                r="收藏成功";
                picture_id=R.mipmap.collect_big;
            }
            else {
                imgShopCollect.setImageResource(R.mipmap.collect_null);
                r="取消收藏成功";
                picture_id=R.mipmap.collect_null_big;
            }
            Toast toast = Toast.makeText(getApplicationContext(),r, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            LinearLayout toastView = (LinearLayout) toast.getView();
            ImageView imageCodeProject = new ImageView(getApplicationContext());
            imageCodeProject.setImageResource(picture_id);
            toastView.addView(imageCodeProject, 0);
            toast.show();
        }
    };
    public void checkShow(TextView[] v){
        for(int i=0;i<v.length;i++) {
            if (TextUtils.isEmpty(v[i].getText()))
                v[i].setVisibility(View.GONE);
            else
                v[i].setVisibility(View.VISIBLE);
        }
    }

    private void setPointResult(){
        Intent data=new Intent();
        data.putExtra(SHOP_COLLECT,ret);
        setResult(RESULT_OK,data);
        MainShopActivity.this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_shop_back:
                setPointResult();
                break;
            case R.id.img_shop_collect:
                if(user_id==0)
                    result="对不起，您还未登陆";
                else{
                    if(isCollect) {
                        new Thread(new Runnable() {
                            public void run() {
                                collectManager(2);
                            }
                        }).start();
                        isCollect=false;
                        ret="delete";
                    }
                    else {
                        new Thread(new Runnable() {
                            public void run() {
                                collectManager(1);
                            }
                        }).start();
                        isCollect=true;
                        ret="null";
                    }
                }
                break;
            case R.id.img_shop_search:
                Intent i=new Intent(MainShopActivity.this,SearchMenuActivity.class);
                i.putExtra(SearchMenuActivity.SEARCH_SHOP_ID,shop_id);
                startActivity(i);
                break;
        }
    }

    class myPagerAdapter extends FragmentPagerAdapter {
        String[] title = { "菜单", "评论", "店家" };
        Fragment_index fragment1;
        Fragment_comment fragment2;
        Fragment_info fragment3;

        public myPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    fragment1 = new Fragment_index();
                    return fragment1;
                case 1:
                    fragment2 = new Fragment_comment();
                    return fragment2;
                case 2:
                    fragment3 = new Fragment_info();
                    return fragment3;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }

}
