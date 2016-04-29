package com.example.a84064.eatapp.MenuPage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a84064.eatapp.R;

/**
 * Created by 84064 on 2016/4/29.
 */
public class ComplainActivity extends AppCompatActivity implements View.OnClickListener{
    public static  String COMPLAIN_SHOP_ID="complain_shop_id";

    private int shop_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

        init();
    }
    public void init(){
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            shop_id = bundle.getInt(COMPLAIN_SHOP_ID);
        }
    }
    @Override
    public void onClick(View v) {

    }
}
