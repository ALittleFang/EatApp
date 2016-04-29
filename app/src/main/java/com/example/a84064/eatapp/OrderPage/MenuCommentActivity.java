package com.example.a84064.eatapp.OrderPage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a84064.eatapp.Model.MenuComment;
import com.example.a84064.eatapp.Model.UrlText;
import com.example.a84064.eatapp.Model.orderList;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.util.ToolImage;
import com.google.gson.Gson;
import com.lidong.photopicker.ImageCaptureManager;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.lidong.photopicker.intent.PhotoPreviewIntent;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 84064 on 2016/4/13.
 */
public class MenuCommentActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton collectBack;
    private TextView tvLayoutTitle;
    private ImageView imgCommentMenu;
    private EditText etCommentMenu;
    private LinearLayout layoutCommentMenuHappy;
    private TextView tvCommentHappy;
    private LinearLayout layoutCommentMenuNormal;
    private TextView tvCommentNormal;
    private LinearLayout layoutCommentMenuAngry;
    private TextView tvCommentAngry;
    private Button btnMenuCommentSubmit;

    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private ImageCaptureManager captureManager; // 相机拍照处理类

    private int stars=1;    //初始为好棒
    private GridView gridView;
    private GridAdapter gridAdapter;
    private Button mButton;
    private String depp;
    private EditText textView;
    private String TAG =MenuCommentActivity.class.getSimpleName();
    UrlText u=new UrlText();
    public static final String MENUCOMMENT_MENU="menuComment_menu";
    private orderList.MenuListBean menu;
    private com.nostra13.universalimageloader.core.ImageLoader universalimageloader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menucomment);

        init();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            String json = bundle.getString(MENUCOMMENT_MENU);
            Gson g=new Gson();
            menu=g.fromJson(json,orderList.MenuListBean.class);
            handlerInfo.sendEmptyMessage(0);
        }

    }
    Handler handlerInfo = new Handler() {
        public void handleMessage(Message msg) {
            universalimageloader.displayImage(u.getUrl()+"Img/"+menu.getMenu_img(), imgCommentMenu, ToolImage.getFadeOptions(R.mipmap.default_icon,R.mipmap.ic_launcher,R.mipmap.ic_launcher));
            tvLayoutTitle.setText(menu.getMenu_name());
        }
    };

    public void init(){
        collectBack = (ImageButton) findViewById(R.id.collect_back);
        tvLayoutTitle = (TextView) findViewById(R.id.tv_layout_title);
        imgCommentMenu = (ImageView) findViewById(R.id.img_comment_menu);
        etCommentMenu = (EditText) findViewById(R.id.et_comment_menu);
        layoutCommentMenuHappy = (LinearLayout) findViewById(R.id.layout_commentMenu_happy);
        tvCommentHappy = (TextView) findViewById(R.id.tv_comment_happy);
        layoutCommentMenuNormal = (LinearLayout) findViewById(R.id.layout_commentMenu_normal);
        tvCommentNormal = (TextView) findViewById(R.id.tv_comment_normal);
        layoutCommentMenuAngry = (LinearLayout) findViewById(R.id.layout_commentMenu_angry);
        tvCommentAngry = (TextView) findViewById(R.id.tv_comment_angry);
        btnMenuCommentSubmit = (Button) findViewById(R.id.btn_menuComment_submit);
        gridView = (GridView) findViewById(R.id.gridView);
        universalimageloader = ToolImage.initImageLoader(this);
        collectBack.setOnClickListener(this);
        layoutCommentMenuAngry.setOnClickListener(this);
        layoutCommentMenuNormal.setOnClickListener(this);
        layoutCommentMenuHappy.setOnClickListener(this);

        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        gridView.setNumColumns(cols);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imgs = (String) parent.getItemAtPosition(position);
                if ("000000".equals(imgs) ){
                    PhotoPickerIntent intent = new PhotoPickerIntent(MenuCommentActivity.this);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true); // 是否显示拍照
                    intent.setMaxTotal(3); // 最多选择照片数量，默认为6
                    intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                }else{
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(MenuCommentActivity.this);
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(imagePaths);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });
        imagePaths.add("000000");
        gridAdapter = new GridAdapter(imagePaths);
        gridView.setAdapter(gridAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.collect_back:
                this.finish();
                break;
            case R.id.btn_menuComment_submit:
                MenuComment comment=new MenuComment();
                if(imagePaths.size()==1 && imagePaths.get(0).equals("000000"))
                    comment.setPicture(null);
                else{


                }
                break;
            case R.id.layout_commentMenu_angry:
                stars=-1;
                choose(tvCommentAngry);
                break;
            case R.id.layout_commentMenu_normal:
                stars=0;
                choose(tvCommentNormal);
                break;
            case R.id.layout_commentMenu_happy:
                stars=-1;
                choose(tvCommentHappy);
                break;
        }
    }

    public List<String> ByteToString(){
        int lenth=imagePaths.size();
        List<String> picture=new ArrayList<>();
        for(int i=0;i<lenth;i++){
            if(i!=lenth-1 || (i==lenth-1 && !imagePaths.get(i).equals("000000"))) {
                File file=new File(imagePaths.get(i));

            }
        }
        return picture;
    }
    public void choose(TextView linery){
        TextView[] lay={tvCommentAngry,tvCommentHappy,tvCommentNormal};
        for(int i=0;i<lay.length;i++){
            if(lay[i].getId()==linery.getId())
                lay[i].setTextColor(Color.parseColor("#cd853f"));
            else
                lay[i].setTextColor(Color.parseColor("#d3d3d3"));
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    Log.d(TAG, "list: " + "list = [" + list.size());
                    loadAdpater(list);
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    Log.d(TAG, "ListExtra: " + "ListExtra = [" + ListExtra.size());
                    loadAdpater(ListExtra);
                    break;
            }
        }
    }

    private void loadAdpater(ArrayList<String> paths){
        if (imagePaths!=null&& imagePaths.size()>0){
            imagePaths.clear();
        }
        if (paths.contains("000000")){
            paths.remove("000000");
        }
        if(paths.size()<3)
            paths.add("000000");
        imagePaths.addAll(paths);
        gridAdapter  = new GridAdapter(imagePaths);
        gridView.setAdapter(gridAdapter);
        try{
            JSONArray obj = new JSONArray(imagePaths);
            Log.e("--", obj.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class GridAdapter extends BaseAdapter{
        private ArrayList<String> listUrls;
        private LayoutInflater inflater;
        public GridAdapter(ArrayList<String> listUrls) {
            this.listUrls = listUrls;
            if(listUrls.size() == 4){
                listUrls.remove(listUrls.size()-1);
            }
            inflater = LayoutInflater.from(MenuCommentActivity.this);
        }

        public int getCount(){
            return  listUrls.size();
        }
        @Override
        public String getItem(int position) {
            return listUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_image, parent,false);
                holder.image = (ImageView) convertView.findViewById(R.id.imageView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            final String path=listUrls.get(position);
            if (path.equals("000000")){
                holder.image.setImageResource(R.mipmap.icon_addpic_unfocused);
            }else {
                Glide.with(MenuCommentActivity.this)
                        .load(path)
                        .placeholder(R.mipmap.default_error)
                        .error(R.mipmap.default_error)
                        .centerCrop()
                        .crossFade()
                        .into(holder.image);
            }
            return convertView;
        }
        public class ViewHolder {
            public ImageView image;
        }
    }
}
