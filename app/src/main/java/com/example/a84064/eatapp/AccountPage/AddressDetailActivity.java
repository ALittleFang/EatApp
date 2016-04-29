package com.example.a84064.eatapp.AccountPage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a84064.eatapp.Model.Address;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.WebServices.AddressWebServiceCall;
import com.example.a84064.eatapp.util.SharedPreferenceUtil;
import com.google.gson.Gson;

/**
 * Created by 84064 on 2016/4/7.
 */
public class AddressDetailActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_SEND_ADDRESS="com.example.a84064.eatapp.addressDetail";
    public static final String ADDRESS_JSON="com.example.a84064.eatapp.AccountPage.address_json";
    public static final String ADDRESS_TYPE="com.example.a84064.eatapp.AccountPage.address_type";
    private int addressId=0;
    private int accountId;
    private boolean sex;
    private Address a=new Address();
    private String result;
    private String street;
    private String point;
    private String action;
    private String json;
    private int type;

    private ImageButton UpdateAddressBack;
    private ImageButton UpdateAddressDelete;
    private EditText updateAddressName;
    private RadioGroup radioGroupSex;
    private EditText updateAddressTel;
    private TextView updateAddressAddress;
    private EditText updateAddressAddressDetail;
    private Button btnUpdateAddressOk;

    AddressWebServiceCall esc=new AddressWebServiceCall();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateaddress);

        init();
        accountId = SharedPreferenceUtil.getInt("id", 0);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            addressId = bundle.getInt(EXTRA_SEND_ADDRESS);
            new Thread(new Runnable() {
                public void run() {
                    setAddress(addressId);
                }
            }).start();
        }
    }
    public void init(){
        UpdateAddressBack = (ImageButton) findViewById(R.id.UpdateAddress_back);
        UpdateAddressDelete = (ImageButton) findViewById(R.id.UpdateAddress_delete);
        updateAddressName = (EditText) findViewById(R.id.updateAddress_name);
        radioGroupSex = (RadioGroup) findViewById(R.id.radioGroup_sex);
        updateAddressTel = (EditText) findViewById(R.id.updateAddress_tel);
        updateAddressAddress = (TextView) findViewById(R.id.updateAddress_address);
        updateAddressAddressDetail = (EditText) findViewById(R.id.updateAddress_address_detail);
        btnUpdateAddressOk = (Button) findViewById(R.id.btnUpdateAddress_ok);
        updateAddressAddress.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线

        UpdateAddressBack.setOnClickListener(this);
        UpdateAddressDelete.setOnClickListener(this);
        btnUpdateAddressOk.setOnClickListener(this);
        updateAddressAddress.setOnClickListener(this);
        radioGroupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.updateAddress_rb_man)
                    sex=true;
                else
                    sex=false;
            }
        });
    }

    public void setAddress(int a_Id){
        try
        {
            Gson g=new Gson();
            a=g.fromJson(esc.getAddress(addressId),Address.class);
            result=null;
        }catch(Exception ex)
        {
            result=ex.toString();
        }
        handlerShow.sendEmptyMessage(0);
    }
    Handler handlerShow = new Handler() {
        public void handleMessage(Message msg) {
            if(!TextUtils.isEmpty(result))
                Toast.makeText(AddressDetailActivity.this,result,Toast.LENGTH_SHORT).show();
            else{
                point=a.getAddress_point();
                UpdateAddressDelete.setVisibility(View.VISIBLE);
                updateAddressName.setText(a.getName());
                updateAddressTel.setText(a.getTel());
                updateAddressAddress.setText(a.getStreet());
                if (!TextUtils.isEmpty(a.getDetail()))
                    updateAddressAddressDetail.setText(a.getDetail());
                if (!a.isSex())
                    radioGroupSex.check(R.id.updateAddress_rb_woman);
            }

        }
    };
    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.UpdateAddress_back:
                this.finish();
                break;
            case R.id.UpdateAddress_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("确认删除该收货地址吗？"); //设置对话框标题
                builder.setIcon(android.R.drawable.btn_star);

                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        action="delete";
                        json=String.valueOf(addressId);
                        type=3;
                        new Thread(new Runnable() {
                            public void run() {
                                AddressManager(0);
                            }
                        }).start();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setCancelable(false); //设置按钮是否可以按返回键取消,false则不可以取消
                AlertDialog dialog = builder.create(); //创建对话框
                dialog.setCanceledOnTouchOutside(false); //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
                dialog.show();
                break;
            case R.id.btnUpdateAddress_ok:
                String add_name=updateAddressName.getText().toString();
                String add_tel=updateAddressTel.getText().toString();
                String add_street=updateAddressAddress.getText().toString();
                if(TextUtils.isEmpty(add_name) ||TextUtils.isEmpty(add_tel) ||TextUtils.isEmpty(add_street)) {
                    result = "收货信息不能为空";
                    handlerManager.sendEmptyMessage(0);
                }
                else{
                    String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
                    if(add_tel.matches(telRegex)) {
                        if (addressId == 0) {
                            action = "add";
                            type = 1;
                        } else {
                            action = "update";
                            type = 2;
                        }
                        a.setSex(sex);
                        a.setAccount_id(accountId);
                        a.setAddress_point(point);
                        if (!TextUtils.isEmpty(updateAddressAddressDetail.getText().toString()))
                            a.setDetail(updateAddressAddressDetail.getText().toString());
                        a.setName(add_name);
                        a.setStreet(add_street);
                        a.setTel(add_tel);
                        Gson g = new Gson();
                        json = g.toJson(a);
                        new Thread(new Runnable() {
                            public void run() {
                                AddressManager(0);
                            }
                        }).start();
                    }
                    else {
                        result = "请填写合法的手机号";
                        handlerManager.sendEmptyMessage(0);
                    }
                }
                break;
            case R.id.updateAddress_address:
                Intent i=new Intent(AddressDetailActivity.this, PointActivity.class);
                String s=updateAddressAddress.getText().toString();
                if(!TextUtils.isEmpty(s))
                    i.putExtra(PointActivity.STREET_NAME,s);
                startActivityForResult(i,0);
                break;
            default:
                break;
        }
    }
    public void AddressManager(int sta){
        try {
            String r = esc.AddressManager(a, action);
            if (r.equals("ok")) {
                result = null;
            } else
                result = r;
        } catch (Exception ex) {
            result = ex.toString();
        }
        handlerManager.sendEmptyMessage(0);
    }
    Handler handlerManager = new Handler() {
        public void handleMessage(Message msg) {
            if(!TextUtils.isEmpty(result))
                Toast.makeText(AddressDetailActivity.this,result,Toast.LENGTH_SHORT).show();
            else{
                setManagerResult();
            }

        }
    };
    private void setManagerResult(){

        Intent data=new Intent();
        data.putExtra(ADDRESS_JSON,json);
        data.putExtra(ADDRESS_TYPE,type);
        setResult(RESULT_OK,data);
        AddressDetailActivity.this.finish();
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(data==null){
            street="";
            return;
        }
        street=data.getStringExtra(PointActivity.POINT_NAME);
        point=data.getStringExtra(PointActivity.POINT_LOCAL);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(street))
            handlerShowStreet.sendEmptyMessage(0);
    }

    Handler handlerShowStreet = new Handler() {
        public void handleMessage(Message msg) {
            updateAddressAddress.setText(street);
        }
    };

}
