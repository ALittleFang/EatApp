package com.example.a84064.eatapp.AccountPage;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a84064.eatapp.AccountPage.AccountActivity;
import com.example.a84064.eatapp.AccountPage.AddressActivity;
import com.example.a84064.eatapp.Model.Account;
import com.example.a84064.eatapp.Model.UrlText;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.WebServices.AccountWebServiceCall;
import com.example.a84064.eatapp.util.SharedPreferenceUtil;
import com.google.gson.Gson;

import java.net.URL;

/**
 * Created by 84064 on 2016/3/30.
 */
public class MyFragment extends Fragment implements View.OnClickListener {
    private TextView tv_name;
    private TextView tv_money;
    private TextView tv_lucky;
    private ImageView img_head;
    private LinearLayout layout_info;
    private LinearLayout layout_money;
    private LinearLayout layout_lucky;
    private LinearLayout layout_collect;
    private LinearLayout layout_address;

    private String result;
    Account a=new Account();
    private int id=0;
    private int lucky=0;
    private int status=0;
    private Drawable drawable = null;
    private View view;

    UrlText u=new UrlText();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        SharedPreferenceUtil.initPreference(getActivity().getApplicationContext());
        init();
        return view;

    }

    public void init(){
        tv_name=(TextView)view.findViewById(R.id.tv_accountName);
        tv_money=(TextView)view.findViewById(R.id.tv_money);
        tv_lucky=(TextView)view.findViewById(R.id.tv_lucky);
        img_head=(ImageView)view.findViewById(R.id.img_head);

        layout_info=(LinearLayout)view.findViewById(R.id.layout_showAccount);
        layout_money=(LinearLayout)view.findViewById(R.id.layout_money);
        layout_lucky=(LinearLayout)view.findViewById(R.id.layout_lucky);
        layout_collect=(LinearLayout)view.findViewById(R.id.layout_shop);
        layout_address=(LinearLayout)view.findViewById(R.id.layout_address);

        layout_info.setOnClickListener(this);
        layout_money.setOnClickListener(this);
        layout_lucky.setOnClickListener(this);
        layout_collect.setOnClickListener(this);
        layout_address.setOnClickListener(this);
    }
    @Override
    public void onResume() {
        // 检查SharedPreferences文件中是否保存有用户名和密码
        super.onResume();
        id = SharedPreferenceUtil.getInt("id", 0);

        if (id!=0) {
            new Thread(new Runnable() {
                public void run() {
                    WSF_GetInfo();
                }
            }).start();
            status=0;
        }
        else {
            tv_name.setText("登录/注册");
            status=1;
        }

    }

    public void WSF_GetInfo(){
        AccountWebServiceCall esc=new AccountWebServiceCall();
        try
        {
            Gson g=new Gson();
            a=g.fromJson(esc.getInfo(id),Account.class);
            lucky=esc.getVoucher(id);
            String img="";
            if(!TextUtils.isEmpty(a.getAccount_image()))
                img=a.getAccount_image();
            else
                img="nomal_head.jpg";
                drawable = Drawable.createFromStream(new URL(u.getUrl()+"Img/"+img).openStream(), "image.jpg");
            img_head.post(new Runnable(){
                @Override
                public void run(){
                    //TODO Auto-generated method stub
                    img_head.setImageDrawable(drawable);
                }
            });
        }catch(Exception ex)
        {
            result=ex.toString();
        }
        handlerShow.sendEmptyMessage(0);
    }

    Handler handlerShow = new Handler() {
        public void handleMessage(Message msg) {
            if(!TextUtils.isEmpty(result))
                Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
            else{
                String name="";
                if(TextUtils.isEmpty(a.getAccount_Name()))
                    name="您还没有设置用户名";
                else
                    name=a.getAccount_Name();
                tv_name.setText(name);
                tv_money.setText(String.valueOf(a.getAccount_money()));
                tv_lucky.setText(String.valueOf(lucky));
            }
        }
    };

    @Override
    public void onClick(View v)
    {
        if(status==0) {
            switch (v.getId()) {
                case R.id.layout_showAccount:
                    startActivity(new Intent(getActivity(), AccountActivity.class));
                    break;
                case R.id.layout_money:
                    startActivity(new Intent("com.eat.RegiestActivity"));
                    break;
                case R.id.layout_lucky:
                    startActivity(new Intent(getActivity(), LuckyBugActivity.class));
                    break;
                case R.id.layout_shop:
                    startActivity(new Intent(getActivity(), CollectActivity.class));
                    break;
                case R.id.layout_address:
                    startActivity(new Intent(getActivity(), AddressActivity.class));
                    break;
                default:
                    break;
            }
        }
        else{
            startActivity(new Intent("com.eat.LoginActivity"));
        }
    }
}
