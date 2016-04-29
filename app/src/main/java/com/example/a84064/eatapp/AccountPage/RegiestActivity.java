package com.example.a84064.eatapp.AccountPage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a84064.eatapp.FragmentMainActivity;
import com.example.a84064.eatapp.Model.Account;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.WebServices.AccountWebServiceCall;
import com.example.a84064.eatapp.util.SharedPreferenceUtil;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import static com.mob.tools.utils.R.getStringRes;

/**
 * Created by 84064 on 2016/3/30.
 */
public class RegiestActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvSendMess;
    private EditText tvTel;
    private EditText tvCode;
    private Button btnGetCode;
    private Button btnCheckRegiest;
    private EditText tvPassword;
    private Button btnGotoRegiest;
    private LinearLayout layoutEnterPass;
    private TextView tvError;
    private ImageButton collectBack;
    private TextView tvLayoutTitle;
    private LinearLayout layoutEnterTel;

    private String result;
    private String password;
    private String iPhone;
    private String iCord;
    private int time = 60;
    private boolean flag = true;
    // 填写从短信SDK应用后台注册得到的APPKEY
    private static String APPKEY = "11303193729bc";//463db7238681  27fe7909f8e8
    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "2f47fa81621ea025c80341ffe67c7cbe";//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiest);
        init();

        SMSSDK.initSDK(this,APPKEY,APPSECRET);
        EventHandler eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh);

        tvPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String location_name=s.toString();
                if (location_name.matches("^[A-Za-z0-9 _-]+$")){
                    password=location_name;
                }else{
                    Toast.makeText(getApplicationContext(),"密码类型不符!",Toast.LENGTH_SHORT).show();
                    tvPassword.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void init() {
        collectBack = (ImageButton) findViewById(R.id.collect_back);
        tvLayoutTitle = (TextView) findViewById(R.id.tv_layout_title);
        layoutEnterTel = (LinearLayout) findViewById(R.id.layout_enterTel);
        layoutEnterPass = (LinearLayout) findViewById(R.id.layout_enterPass);
        tvSendMess = (TextView) findViewById(R.id.tv_sendMess);
        tvTel = (EditText) findViewById(R.id.tv_tel);
        tvCode = (EditText) findViewById(R.id.tv_code);
        btnGetCode = (Button) findViewById(R.id.btn_getCode);
        btnCheckRegiest = (Button) findViewById(R.id.btn_checkRegiest);
        tvPassword = (EditText) findViewById(R.id.tv_password);
        btnGotoRegiest = (Button) findViewById(R.id.btn_gotoRegiest);

        btnCheckRegiest.setOnClickListener(this);
        btnGotoRegiest.setOnClickListener(this);
        collectBack.setOnClickListener(this);
        btnGetCode.setOnClickListener(this);

        handlerInfo.sendEmptyMessage(0);
    }
    Handler handlerInfo = new Handler() {
        public void handleMessage(Message msg) {
            tvLayoutTitle.setText("注册");
        }
    };

    public void WSF_Check(){
        AccountWebServiceCall esc=new AccountWebServiceCall();
        iPhone=tvTel.getText().toString();
        try {
            boolean resp = esc.checkTel(iPhone,"tel");
            if (!resp)
                result = "该号码已被注册";
            else {
                SMSSDK.getVerificationCode("86",iPhone);
                result="ok";
            }
        } catch (Exception ex) {
            result = ex.toString();
        }
        handlerCheck.sendEmptyMessage(0);
    }

    public void WSF_Regieat() {
    AccountWebServiceCall esc = new AccountWebServiceCall();
        Account a = new Account();
        a.setAccount_Phone(iPhone);
        a.setAccount_password(password);
        a.setAccount_money(200);
        a.setType(1);
        try {
            int id = esc.addAccount(a);
            if (id != 0) {
                SharedPreferenceUtil.putInt("id", id);
                result = null;
                startActivity(new Intent(RegiestActivity.this, FragmentMainActivity.class));
            }
        } catch (Exception ex) {
            result = ex.toString();
        }
        handlerCheck.sendEmptyMessage(0);
    }

    Handler handlerCheck = new Handler() {
        public void handleMessage(Message msg) {
            if(!TextUtils.isEmpty(result)) {
                if(result.equals("ok")) {
                    tvCode.requestFocus();
                    btnGetCode.setVisibility(View.GONE);
                    //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getCode: {
                if(!TextUtils.isEmpty(tvTel.getText().toString().trim())){
                    if(tvTel.getText().toString().trim().length()==11){
                        new Thread(new Runnable() {
                            public void run() {
                                WSF_Check();
                            }
                        }).start();
                    }else{
                        Toast.makeText(RegiestActivity.this, "请输入完整电话号码", Toast.LENGTH_LONG).show();
                        tvTel.requestFocus();
                    }
                }else{
                    Toast.makeText(RegiestActivity.this, "请输入您的电话号码", Toast.LENGTH_LONG).show();
                    tvTel.requestFocus();
                }
                break;
            }
            case R.id.btn_gotoRegiest: {
                if(!TextUtils.isEmpty(tvPassword.getText().toString())) {
                    new Thread(new Runnable() {
                        public void run() {
                            WSF_Regieat();
                        }
                    }).start();
                }
                else {
                    Toast.makeText(RegiestActivity.this, "请设置密码", Toast.LENGTH_LONG).show();
                    tvPassword.requestFocus();
                }
                break;
            }
            case R.id.collect_back: {
                this.finish();
                break;
            }
            case R.id.btn_checkRegiest:{
                if(!TextUtils.isEmpty(tvCode.getText().toString().trim())){
                    if(tvCode.getText().toString().trim().length()==4){
                        iCord = tvCode.getText().toString().trim();
                        SMSSDK.submitVerificationCode("86", iPhone, iCord);
                        flag = false;
                    }else{
                        Toast.makeText(RegiestActivity.this, "请输入4位数的验证码", Toast.LENGTH_LONG).show();
                        tvCode.requestFocus();
                    }
                }else{
                    Toast.makeText(RegiestActivity.this, "请输入验证码", Toast.LENGTH_LONG).show();
                    tvCode.requestFocus();
                }
                break;
            }
        }
    }

    private void reminderText() {
        tvSendMess.setVisibility(View.VISIBLE);
        handlerText.sendEmptyMessageDelayed(1, 1000);
    }

    Handler handlerText =new Handler(){
        public void handleMessage(Message msg) {
            if(msg.what==1){
                if(time>0){
                    tvSendMess.setText("验证码已发送"+time+"秒");
                    time--;
                    handlerText.sendEmptyMessageDelayed(1, 1000);
                }else{
                    tvSendMess.setText("提示信息");
                    time = 60;
                    tvSendMess.setVisibility(View.GONE);
                    btnGetCode.setVisibility(View.VISIBLE);
                }
            }else{
                tvCode.setText("");
                tvSendMess.setText("提示信息");
                time = 60;
                tvSendMess.setVisibility(View.GONE);
                btnGetCode.setVisibility(View.VISIBLE);
                layoutEnterTel.setVisibility(View.GONE);
                layoutEnterPass.setVisibility(View.VISIBLE);
            }
        };
    };

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event="+event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功,验证通过
                    Toast.makeText(getApplicationContext(), "验证码校验成功", Toast.LENGTH_SHORT).show();
                    handlerText.sendEmptyMessage(2);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){//服务器验证码发送成功
                    reminderText();
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//返回支持发送验证码的国家列表
                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(flag){
                    btnGetCode.setVisibility(View.VISIBLE);
                    Toast.makeText(RegiestActivity.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                    tvTel.requestFocus();
                }else{
                    ((Throwable) data).printStackTrace();
                    int resId = getStringRes(RegiestActivity.this, "smssdk_network_error");
                    Toast.makeText(RegiestActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    tvCode.selectAll();
                    if (resId > 0) {
                        Toast.makeText(RegiestActivity.this, resId, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

}
