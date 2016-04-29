package com.example.a84064.eatapp.AccountPage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a84064.eatapp.Model.Address;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.WebServices.AddressWebServiceCall;
import com.example.a84064.eatapp.Adapter.AddressAdapter;
import com.example.a84064.eatapp.util.SharedPreferenceUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 84064 on 2016/4/5.
 */
public class AddressActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView listView=null;
    private ImageButton collectBack;
    private TextView tvLayoutTitle;
    private Button btnAddressAdd;
    private TextView tvAddressNull;

    private List<Address> list=new ArrayList<>();
    private String result;
    AddressAdapter my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        collectBack = (ImageButton) findViewById(R.id.collect_back);
        tvLayoutTitle = (TextView) findViewById(R.id.tv_layout_title);
        collectBack.setOnClickListener(this);
        btnAddressAdd = (Button) findViewById(R.id.btn_Address_add);
        btnAddressAdd.setOnClickListener(this);
        tvAddressNull = (TextView) findViewById(R.id.tv_address_null);

        final int id = SharedPreferenceUtil.getInt("id", 0);
        if(id==0)
            startActivity(new Intent(AddressActivity.this, LoginActivity.class));
        else {
            listView = (ListView) findViewById(R.id.lv_address);
            new Thread(new Runnable() {
                public void run() {
                    getAddress(id);
                }
            }).start();
        }
    }

    public void getAddress(int i){
        AddressWebServiceCall esc=new AddressWebServiceCall();
        try
        {
            list=esc.getAddressList(i);
            if(list==null)
                result="null";
            else{
                result="ok";
            }
        }catch(Exception ex)
        {
            result=ex.toString();
        }
        handlerShow.sendEmptyMessage(0);
    }
    Handler handlerShow = new Handler() {
        public void handleMessage(Message msg) {
            tvLayoutTitle.setText("收货地址");
            switch(result){
                case "null":
                    tvAddressNull.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    break;
                case "ok":
                    tvAddressNull.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    my=new AddressAdapter(AddressActivity.this, list);
                    listView.setAdapter(my);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                            int addressId=list.get(arg2).getAddress_id();
                            Intent i=new Intent(AddressActivity.this,AddressDetailActivity.class);
                            i.putExtra(AddressDetailActivity.EXTRA_SEND_ADDRESS,addressId);
                            startActivityForResult(i,0);
                        }
                    });
                    default:
                        Toast.makeText(AddressActivity.this,result,Toast.LENGTH_SHORT).show();
                        break;
            }
        }
    };
    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.collect_back:
                this.finish();
                break;
            case R.id.btn_Address_add:
                startActivityForResult(new Intent(AddressActivity.this, AddressDetailActivity.class),0);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(data==null){
            return;
        }
        int t=data.getIntExtra(AddressDetailActivity.ADDRESS_TYPE,0);
        String j=data.getStringExtra(AddressDetailActivity.ADDRESS_JSON);
        switch (t){
            case 1:
                my.addData(j);
                break;
            case 2:
                my.updateData(j);
                break;
            case 3:
                my.deleteData(j);
                break;
            default:
                break;
        }
    }

}
