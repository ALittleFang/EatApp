package com.example.a84064.eatapp.MenuPage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.baidu.mapapi.model.LatLng;
import com.example.a84064.eatapp.Adapter.ShopAdapter;
import com.example.a84064.eatapp.Model.UrlText;
import com.example.a84064.eatapp.Model.simple_shop;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.WebServices.ShopWebService;

import java.util.List;

/**
 * Created by 84064 on 2016/4/22.
 */
public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    public static String SEARCH_TEXT="search_text";
    public static String SEARCH_LOCATION="search_location";
    private ImageButton imgSearchBack;
    private EditText etSearch;
    private Button btnSearch;
    private RecyclerView rvList;
    private LinearLayout layoutSearchNull;

    private String name;
    private LatLng location;
    private List<simple_shop> shopes;
    private String result;
    private ShopAdapter mAdapter;
    UrlText u=new UrlText();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();
    }

    public void init(){
        imgSearchBack = (ImageButton) findViewById(R.id.img_search_back);
        etSearch = (EditText) findViewById(R.id.et_search);
        btnSearch = (Button) findViewById(R.id.btn_search);
        rvList = (RecyclerView) findViewById(R.id.rv_list);
        layoutSearchNull = (LinearLayout) findViewById(R.id.layout_search_null);
        imgSearchBack.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        rvList.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            name= bundle.getString(SEARCH_TEXT);
            location=u.StringToLating(bundle.getString(SEARCH_LOCATION));
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
            shopes=esc.getIndexList(location,name);
            if(shopes!=null) {
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
            etSearch.setText(name);
            if(!TextUtils.isEmpty(result)) {
                layoutSearchNull.setVisibility(View.VISIBLE);
                rvList.setVisibility(View.GONE);
            }
            else{
                layoutSearchNull.setVisibility(View.GONE);
                rvList.setVisibility(View.VISIBLE);
                mAdapter=new ShopAdapter(SearchActivity.this,shopes,1);
                rvList.setAdapter(mAdapter);

            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_search_back:
                this.finish();
                break;
            case R.id.btn_search:
                if(!TextUtils.isEmpty(etSearch.getText().toString())){
                    etSearch.clearFocus();
                    name=etSearch.getText().toString();
                    new Thread(new Runnable() {
                        public void run() {
                            getShop();
                        }
                    }).start();
                }
                break;
        }
    }

}
