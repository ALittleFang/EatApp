package com.example.a84064.eatapp.AccountPage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a84064.eatapp.Model.Account;
import com.example.a84064.eatapp.Model.UrlText;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.WebServices.AccountWebServiceCall;
import com.example.a84064.eatapp.util.SharedPreferenceUtil;
import com.google.gson.Gson;

import org.kobjects.base64.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * Created by 84064 on 2016/4/1.
 */
public class AccountActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_email;
    private ImageView imgView_head;
    private ImageButton collectBack;
    private TextView tvLayoutTitle;
    private LinearLayout layout_changeName;
    private LinearLayout layout_changePass;
    private LinearLayout layout_changeEmail;
    private LinearLayout layout_logout;

    private String te="";
    private String result;
    private String name;
    private String Tel;
    Account a=new Account();
    private int id=0;
    private Drawable drawable = null;
    private byte[] img_head;
    UrlText u=new UrlText();

    private String[] items = new String[]{"选择本地图片", "拍照"};
    /** 头像名称 */
    private static final String IMAGE_FILE_NAME = "head.jpg";

    /** 请求码 */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        init();
    }

    public void init(){
        imgView_head = (ImageView) findViewById(R.id.imgView_head);
        collectBack = (ImageButton) findViewById(R.id.collect_back);
        tvLayoutTitle = (TextView) findViewById(R.id.tv_layout_title);
        tv_name= (TextView) findViewById(R.id.tv_acName);
        tv_phone= (TextView) findViewById(R.id.tv_accountTel);
        tv_email= (TextView) findViewById(R.id.tv_accEmail);

        layout_changeName=(LinearLayout)findViewById(R.id.layout_changeName);
        layout_changePass=(LinearLayout)findViewById(R.id.layout_changePass);
        layout_changeEmail=(LinearLayout)findViewById(R.id.layout_changeEmail);
        layout_logout=(LinearLayout)findViewById(R.id.layout_logOut);

        layout_logout.setOnClickListener(this);
        if(TextUtils.isEmpty(tv_name.getText().toString()))
            layout_changeName.setOnClickListener(this);
        else
            layout_changeName.setOnClickListener(null);
        layout_changePass.setOnClickListener(this);
        layout_changeEmail.setOnClickListener(this);
        imgView_head.setOnClickListener(this);
        collectBack.setOnClickListener(this);
        id = SharedPreferenceUtil.getInt("id", 0);
        if (id!=0) {
            new Thread(new Runnable() {
                public void run() {
                    WSF_Show();
                }
            }).start();
        }
        else
            startActivity(new Intent(AccountActivity.this, LoginActivity.class));
    }

    public void WSF_Show(){
        AccountWebServiceCall esc=new AccountWebServiceCall();
        try
        {
            Gson g=new Gson();
            a=g.fromJson(esc.getInfo(id),Account.class);
            String img="";
            if(!TextUtils.isEmpty(a.getAccount_image()))
                img=a.getAccount_image();
            else
                img="nomal_head.jpg";
            drawable = Drawable.createFromStream(new URL(u.getUrl()+"Img/"+img).openStream(), "image.jpg");
            imgView_head.post(new Runnable(){
                @Override
                public void run(){
                    //TODO Auto-generated method stub
                    imgView_head.setImageDrawable(drawable);
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
                Toast.makeText(AccountActivity.this,result,Toast.LENGTH_SHORT).show();

            else{
                if (!TextUtils.isEmpty(a.getAccount_Name()))
                    tv_name.setText(a.getAccount_Name());
                if (!TextUtils.isEmpty(a.getAccount_email()))
                    tv_email.setText(a.getAccount_email());
                if (!TextUtils.isEmpty(a.getAccount_Phone())) {
                    Tel=a.getAccount_Phone();
                    tv_phone.setText("绑定手机：" + a.getAccount_Phone());
                }
            }

        }
    };

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.imgView_head:
                showDialog();
                break;
            case R.id.collect_back:
                this.finish();
                break;
            case R.id.layout_changeName: {
                if(TextUtils.isEmpty(tv_name.getText().toString()))
                    showDia(1);
                break;
            }
            case R.id.layout_changeEmail:
                showDia(3);
                break;
            case R.id.layout_changePass:
                Intent i=new Intent(AccountActivity.this,ChangePassWordActivity.class);
                i.putExtra(ChangePassWordActivity.EXTRA_SEND_TEL,Tel);
                startActivity(i);
                break;
            case R.id.layout_logOut: {
                SharedPreferenceUtil.putInt("id", 0);
                startActivity(new Intent(AccountActivity.this, LoginActivity.class));
                break;
            }
            default:
                break;
        }
    }

    public void WSF_Update(String value,int t){
        AccountWebServiceCall esc = new AccountWebServiceCall();
        String re;
        try {
            switch (t){
                case 1:
                    re = esc.updateAccount(id, value, t);
                    if (re.equals("ok")) {
                        result = "changeName";
                    } else
                        result = re;
                    break;
                case 3:
                    if(!esc.checkTel(value,"email"))
                        result="该邮箱已被注册";
                    else{
                        re=esc.updateAccount(id, value, t);
                        if (re.equals("ok")) {
                            result = "changeEmail";
                        } else
                            result = re;
                    }
                    break;
                case 4:
                    re = esc.updateAccount(id, value, t);
                    if (re.equals("ok")) {
                        result = "changeImage";
                    } else
                        result = re;
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            result = ex.toString();
        }
        handlerCheck.sendEmptyMessage(0);
    }

    Handler handlerCheck = new Handler() {
        public void handleMessage(Message msg) {
            switch(result){
                case "changeName":
                    tv_name.setText(name);
                    break;
                case "changeEmail":
                    tv_email.setText(name);
                    break;
                case "changeImage":
                    imgView_head.setImageDrawable(drawable);
                    break;
                default:
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void showDia(final int t){
        final EditText edit = new EditText(this);
        switch (t){
            case 1:
                te="请输入用户名";
                InputFilter[] filters = {new InputFilter.LengthFilter(12)};
                edit.setFilters(filters);
                break;
            case 3:
                te="请输入邮箱号";
                break;
            default:
                break;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(te); //设置对话框标题
        builder.setIcon(android.R.drawable.btn_star);
        builder.setView(edit);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(TextUtils.isEmpty(edit.getText().toString()))
                    Toast.makeText(AccountActivity.this, te, Toast.LENGTH_SHORT).show();
                else{
                    new Thread(new Runnable() {
                        public void run() {
                            if(t==3){
                                if(isEmail(edit.getText().toString())){
                                    name=edit.getText().toString();
                                    WSF_Update(name,t);
                                }
                                else {
                                    result="不是正确的邮箱格式";
                                    handlerCheck.sendEmptyMessage(0);
                                }
                            }
                            else {
                                name = edit.getText().toString();
                                WSF_Update(name, t);
                            }
                        }
                    }).start();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(true); //设置按钮是否可以按返回键取消,false则不可以取消
        AlertDialog dialog = builder.create(); //创建对话框
        dialog.setCanceledOnTouchOutside(true); //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
        dialog.show();
    }

    public static boolean isEmail(String email) {
        String emailPattern = "[a-zA-Z0-9][a-zA-Z0-9._-]{2,16}[a-zA-Z0-9]@[a-zA-Z0-9]+.[a-zA-Z0-9]+";
        boolean result = Pattern.matches(emailPattern, email);
        return result;
    }

    /**
     * 显示选择对话框
     */
    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("设置头像")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0 :
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*"); // 设置文件类型
                                intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
                                break;
                            case 1 :
                                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                // 判断存储卡是否可以用，可用进行存储
                                String state = Environment.getExternalStorageState();
                                if (state.equals(Environment.MEDIA_MOUNTED)) {
                                    File path = Environment
                                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                                    File file = new File(path, IMAGE_FILE_NAME);
                                    intentFromCapture.putExtra(
                                            MediaStore.EXTRA_OUTPUT,
                                            Uri.fromFile(file));
                                }
                                startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 结果码不等于取消时候
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE :
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE :
                    // 判断存储卡是否可以用，可用进行存储
                    String state = Environment.getExternalStorageState();
                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                        File path = Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        File tempFile = new File(path, IMAGE_FILE_NAME);
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case RESULT_REQUEST_CODE : // 图片缩放完成后
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            drawable = new BitmapDrawable(this.getResources(), photo);
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            name= Base64.encode(baos.toByteArray());
            new Thread(new Runnable() {
                public void run() {
                    WSF_Update(name,4);
                }
            }).start();
        }
    }

}
