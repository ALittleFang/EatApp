package com.example.a84064.eatapp.AccountPage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.a84064.eatapp.FragmentMainActivity;
import com.example.a84064.eatapp.MenuPage.MainShopActivity;
import com.example.a84064.eatapp.Model.Shop;
import com.example.a84064.eatapp.Model.UrlText;
import com.example.a84064.eatapp.Model.shop_list;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.WebServices.ShopWebService;
import com.example.a84064.eatapp.Adapter.CollectAdapter;
import com.example.a84064.eatapp.util.SharedPreferenceUtil;
import com.example.a84064.eatapp.util.ToolImage;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 84064 on 2016/4/9.
 */
public class CollectActivity extends AppCompatActivity implements View.OnClickListener{
    private int accountId;
    private int pos;
    private LinearLayout layoutCollectNull;
    private ImageButton collectBack;
    private TextView tvLayoutTitle;
    private ListView listView=null;
    private List<shop_list> list=new ArrayList<shop_list>();
    private String result;
    private LatLng position;
    private CollectAdapter collectAdapter;
    UrlText u=new UrlText();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_collet);

        layoutCollectNull = (LinearLayout) findViewById(R.id.layout_collect_null);
        collectBack = (ImageButton) findViewById(R.id.collect_back);
        tvLayoutTitle = (TextView) findViewById(R.id.tv_layout_title);
        collectBack.setOnClickListener(this);
        accountId = SharedPreferenceUtil.getInt("id", 0);
        position=u.StringToLating(SharedPreferenceUtil.getString("point",""));
        if(accountId==0)
            startActivity(new Intent(CollectActivity.this, LoginActivity.class));
        else {
            listView = (ListView) findViewById(R.id.lv_collect);
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
            List<Shop> l=esc.getCollectList(accountId);
            if(l!=null) {
                for (int i = 0; i < l.size(); i++) {
                    shop_list shop = new shop_list();
                    shop.setLogo(u.getUrl() + "Img/" + l.get(i).getLogo());
                    shop.setShop_name(l.get(i).getShop_name());
                    shop.setScore(l.get(i).getScore());
                    shop.setShop_id(l.get(i).getShop_id());
                    LatLng shopLat = u.getShopLatlng(l.get(i).getLocation());
                    shop.setDistance(DistanceUtil.getDistance(shopLat, position));
                    String[] num = esc.getshopNum(l.get(i).getShop_id()).split(",");
                    shop.setNewMenu(Integer.valueOf(num[2]));
                    shop.setSendNum(Integer.valueOf(num[1]));
                    shop.setPayNum(Integer.valueOf(num[0]));
                    list.add(shop);
                }
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
            tvLayoutTitle.setText("店铺收藏");
            if(!TextUtils.isEmpty(result)) {
                layoutCollectNull.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
            else{
                layoutCollectNull.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                collectAdapter = new CollectAdapter(CollectActivity.this, list);
                listView.setAdapter(collectAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        pos=position;
                        Intent i=new Intent(CollectActivity.this, MainShopActivity.class);
                        i.putExtra(MainShopActivity.SHOP_RECEIVE,list.get(position).getShop_id());
                        startActivityForResult(i,0);
                    }
                });
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(data==null){
            return;
        }
        String r=data.getStringExtra(MainShopActivity.SHOP_COLLECT);
        switch (r){
            case "null":
                return;
            case "delete":
                collectAdapter.updateCollect(pos);
                break;
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.collect_back:
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToolImage.clearCache();
    }
}
