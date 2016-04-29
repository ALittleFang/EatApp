package com.example.a84064.eatapp.AccountPage;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a84064.eatapp.FragmentMainActivity;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.WebServices.AccountWebServiceCall;
import com.example.a84064.eatapp.util.SharedPreferenceUtil;


public class LoginActivity extends AppCompatActivity {

    Handler handlerWeather = new Handler() {
        public void handleMessage(Message msg) {
            tvShow.setText(result);
        }
    };


    TextView tvShow;
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnShow = (Button) findViewById(R.id.btn_login);
        tvShow= (TextView) findViewById(R.id.tv_show);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        WSF_Add();
                    }
                }).start();
            }
        });
    }

    public void showRegiest(View view)
    {
        startActivity(new Intent("com.eat.RegiestActivity"));
    }


    public void WSF_Add(){
        AccountWebServiceCall esc=new AccountWebServiceCall();
        try
        {
            EditText txt_name=(EditText)findViewById(R.id.txt_name);
            EditText txt_pass=(EditText)findViewById(R.id.txt_password);
            String name=txt_name.getText().toString();
            String pass=txt_pass.getText().toString();

            String resp=esc.returnLogin(name, pass);
            if(resp.equals("0"))
                result="用户名或密码错误";
            else {
                SharedPreferenceUtil.putInt("id",Integer.valueOf(resp));
                startActivity(new Intent(this, FragmentMainActivity.class));
            }
        }catch(Exception ex)
        {
            result=ex.toString();
        }
        handlerWeather.sendEmptyMessage(0);
    }


}

