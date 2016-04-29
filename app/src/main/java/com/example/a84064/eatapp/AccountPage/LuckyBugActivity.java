package com.example.a84064.eatapp.AccountPage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a84064.eatapp.Adapter.VoucherAdapter;
import com.example.a84064.eatapp.Model.VoucherInfo;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.WebServices.AccountWebServiceCall;
import com.example.a84064.eatapp.util.SharedPreferenceUtil;

import java.util.List;

/**
 * Created by 84064 on 2016/4/23.
 */
public class LuckyBugActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton imgLuckybugBack;
    private ImageButton imgLuckybugReturn;
    private TextView tvLuckybugTitle;
    private RecyclerView rvLuckyBug;
    private LinearLayout layoutSearchNull;
    private LinearLayout layoutLuckyBugBottom;

    private int user_id;
    private List<VoucherInfo> vouchers;
    private String result;
    private VoucherAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luckybug);

        init();
    }

    public void init(){
        imgLuckybugBack = (ImageButton) findViewById(R.id.img_luckybug_back);
        imgLuckybugReturn = (ImageButton) findViewById(R.id.img_luckybug_return);
        tvLuckybugTitle = (TextView) findViewById(R.id.tv_luckybug_title);
        rvLuckyBug = (RecyclerView) findViewById(R.id.rv_luckyBug);
        layoutSearchNull = (LinearLayout) findViewById(R.id.layout_search_null);
        layoutLuckyBugBottom = (LinearLayout) findViewById(R.id.layout_luckyBug_bottom);
        imgLuckybugBack.setOnClickListener(this);
        imgLuckybugReturn.setOnClickListener(this);
        layoutLuckyBugBottom.setOnClickListener(this);

        user_id = SharedPreferenceUtil.getInt("id", 0);
        if (user_id!=0) {
            new Thread(new Runnable() {
                public void run() {
                    getVoucherList(0);
                }
            }).start();
        }
        else
            startActivity(new Intent(LuckyBugActivity.this, LoginActivity.class));
        rvLuckyBug.setLayoutManager(new LinearLayoutManager(LuckyBugActivity.this));
    }
    public void getVoucherList(int type){
        AccountWebServiceCall esc=new AccountWebServiceCall();
        try
        {
            vouchers=esc.getVoucherList(user_id,type);
            if(vouchers!=null) {
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
                layoutSearchNull.setVisibility(View.VISIBLE);
                rvLuckyBug.setVisibility(View.GONE);
            }
            else{
                layoutSearchNull.setVisibility(View.GONE);
                rvLuckyBug.setVisibility(View.VISIBLE);
                mAdapter=new VoucherAdapter(LuckyBugActivity.this,vouchers);
                rvLuckyBug.setAdapter(mAdapter);
            }
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_luckybug_back:
                this.finish();
                break;
            case R.id.img_luckybug_return:
                layoutLuckyBugBottom.setVisibility(View.VISIBLE);
                imgLuckybugBack.setVisibility(View.VISIBLE);
                imgLuckybugReturn.setVisibility(View.GONE);
                new Thread(new Runnable() {
                    public void run() {
                        getVoucherList(0);
                    }
                }).start();
                break;
            case R.id.layout_luckyBug_bottom:
                layoutLuckyBugBottom.setVisibility(View.GONE);
                imgLuckybugBack.setVisibility(View.GONE);
                imgLuckybugReturn.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    public void run() {
                        getVoucherList(1);
                    }
                }).start();
                break;
        }
    }
}
