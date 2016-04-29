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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.WebServices.AccountWebServiceCall;
import com.example.a84064.eatapp.util.SharedPreferenceUtil;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.mob.tools.utils.R.getStringRes;

/**
 * Created by 84064 on 2016/4/3.
 */
public class ChangePassWordActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_SEND_TEL="com.example.a84064.eatapp";

    private LinearLayout changePassLayCode;
    private EditText etChangeCode;
    private Button changePassBtnCheck;
    private Button changePassBtnRecive;
    private LinearLayout changePassLayPass;
    private EditText etChangePass;
    private Button changePassBtnUpdate;
    private TextView changePassTip;

    private int time = 60;
    private boolean flag = true;
    private static String APPKEY = "11303193729bc";//463db7238681  27fe7909f8e8
    private static String APPSECRET = "2f47fa81621ea025c80341ffe67c7cbe";
    private String pass;
    private String tel;
    private String iCord;
    private String result;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapass);
        init();

        id = SharedPreferenceUtil.getInt("id", 0);
        if(id==0)
            startActivity(new Intent(ChangePassWordActivity.this, LoginActivity.class));
        tel=getIntent().getStringExtra(EXTRA_SEND_TEL);
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
        SMSSDK.getVerificationCode("86",tel);

        etChangePass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String location_name=s.toString();
                if (location_name.matches("^[A-Za-z0-9 _-]+$")){
                    pass=location_name;
                }else{
                    Toast.makeText(getApplicationContext(),"密码类型不符!",Toast.LENGTH_SHORT).show();
                    etChangePass.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void init(){
        changePassTip = (TextView) findViewById(R.id.changePass_tip);
        changePassLayCode = (LinearLayout) findViewById(R.id.changePass_layCode);
        etChangeCode = (EditText) findViewById(R.id.et_changeCode);
        changePassBtnCheck = (Button) findViewById(R.id.changePass_btnCheck);
        changePassBtnRecive = (Button) findViewById(R.id.changePass_btnRecive);
        changePassLayPass = (LinearLayout) findViewById(R.id.changePass_layPass);
        etChangePass = (EditText) findViewById(R.id.et_changePass);
        changePassBtnUpdate = (Button) findViewById(R.id.changePass_btnUpdate);

        changePassBtnCheck.setOnClickListener(this);
        changePassBtnRecive.setOnClickListener(this);
        changePassBtnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.changePass_btnRecive:
                SMSSDK.getVerificationCode("86",tel);
                reminderText();
                break;
            case R.id.changePass_btnCheck:
                if(!TextUtils.isEmpty(etChangeCode.getText().toString().trim())){
                    if(etChangeCode.getText().toString().trim().length()==4){
                        iCord = etChangeCode.getText().toString().trim();
                        SMSSDK.submitVerificationCode("86", tel, iCord);
                        flag = false;
                    }else{
                        Toast.makeText(ChangePassWordActivity.this, "请输入4位数的验证码", Toast.LENGTH_LONG).show();
                        etChangeCode.requestFocus();
                    }
                }else{
                    Toast.makeText(ChangePassWordActivity.this, "请输入验证码", Toast.LENGTH_LONG).show();
                    etChangeCode.requestFocus();
                }
                break;
            case R.id.changePass_btnUpdate:
                if(!TextUtils.isEmpty(etChangePass.getText().toString())) {
                    new Thread(new Runnable() {
                        public void run() {
                            WSF_ChangePass();
                        }
                    }).start();
                }
                else {
                    Toast.makeText(ChangePassWordActivity.this, "请设置密码", Toast.LENGTH_LONG).show();
                    etChangePass.requestFocus();
                }
                break;
            default:
                break;
        }
    }

    public void WSF_ChangePass() {
        AccountWebServiceCall esc = new AccountWebServiceCall();
        pass=etChangePass.getText().toString();
        try {
            String re = esc.updateAccount(id,pass,2);
            if(re.equals("ok")) {
                SharedPreferenceUtil.putInt("id", 0);
                result = null;
            }
            else
                result=re;
        } catch (Exception ex) {
            result = ex.toString();
        }
        handlerCheck.sendEmptyMessage(0);
    }

    Handler handlerCheck = new Handler() {
        public void handleMessage(Message msg) {
            if(!TextUtils.isEmpty(result)) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
            else
                startActivity(new Intent(ChangePassWordActivity.this, LoginActivity.class));
        }
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
                    changePassBtnRecive.setVisibility(View.VISIBLE);
                    Toast.makeText(ChangePassWordActivity.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                    etChangeCode.requestFocus();
                }else{
                    ((Throwable) data).printStackTrace();
                    int resId = getStringRes(ChangePassWordActivity.this, "smssdk_network_error");
                    Toast.makeText(ChangePassWordActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    etChangeCode.selectAll();
                    if (resId > 0) {
                        Toast.makeText(ChangePassWordActivity.this, resId, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    };

    private void reminderText() {
        etChangeCode.requestFocus();
        changePassTip.setVisibility(View.VISIBLE);
        handlerText.sendEmptyMessageDelayed(1, 1000);
    }

    Handler handlerText =new Handler(){
        public void handleMessage(Message msg) {
            if(msg.what==1){
                if(time>0){
                    changePassTip.setText("验证码已发送"+time+"秒");
                    time--;
                    handlerText.sendEmptyMessageDelayed(1, 1000);
                }else{
                    changePassTip.setText("提示信息");
                    time = 60;
                    changePassTip.setVisibility(View.GONE);
                    changePassBtnRecive.setVisibility(View.VISIBLE);
                }
            }else{
                etChangeCode.setText("");
                changePassTip.setText("提示信息");
                time = 60;
                changePassTip.setVisibility(View.GONE);
                changePassBtnRecive.setVisibility(View.VISIBLE);
                changePassLayCode.setVisibility(View.GONE);
                changePassLayPass.setVisibility(View.VISIBLE);
            }
        };
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
