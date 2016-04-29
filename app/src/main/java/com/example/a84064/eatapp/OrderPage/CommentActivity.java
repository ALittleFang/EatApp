package com.example.a84064.eatapp.OrderPage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.example.a84064.eatapp.Adapter.CommentAdapter;
import com.example.a84064.eatapp.Adapter.ListItemClickHelp;
import com.example.a84064.eatapp.Model.UrlText;
import com.example.a84064.eatapp.Model.orderList;
import com.example.a84064.eatapp.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 84064 on 2016/4/13.
 */
public class CommentActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgCommentShop;
    private RatingBar ratCommentShop;
    private EditText etCommentTotalComment;
    private ListView lvCommentMenu;
    private Button btnCommentSubmit;
    private CommentAdapter ca;
    private ImageButton collectBack;
    private TextView tvLayoutTitle;

    List<orderList.MenuListBean> menus=new ArrayList<>();
    UrlText u=new UrlText();
    public static String COMMENT_ORDER_ID="comment_order_id";
    private orderList order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        init();
    }
    public void init(){
        imgCommentShop = (ImageView) findViewById(R.id.img_comment_shop);
        ratCommentShop = (RatingBar) findViewById(R.id.rat_comment_shop);
        etCommentTotalComment = (EditText) findViewById(R.id.et_comment_totalComment);
        lvCommentMenu = (ListView) findViewById(R.id.lv_comment_menu);
        btnCommentSubmit = (Button) findViewById(R.id.btn_comment_submit);
        collectBack = (ImageButton) findViewById(R.id.collect_back);
        tvLayoutTitle = (TextView) findViewById(R.id.tv_layout_title);

        collectBack.setOnClickListener(this);
        btnCommentSubmit.setOnClickListener(this);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            String json = bundle.getString(COMMENT_ORDER_ID);
            Gson g=new Gson();
            order=g.fromJson(json,orderList.class);
            menus=order.getMenuList();
            ca = new CommentAdapter(this, menus);
            lvCommentMenu.setAdapter(ca);
            lvCommentMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                    Intent i=new Intent(CommentActivity.this,MenuCommentActivity.class);
                    orderList.MenuListBean m=(orderList.MenuListBean)ca.getItem(arg2) ;
                    Gson g=new Gson();
                    String json=g.toJson(m);
                    i.putExtra(MenuCommentActivity.MENUCOMMENT_MENU,json);
                    startActivity(i);
                }
            });
        }
        handlerInfo.sendEmptyMessage(0);
    }
    Handler handlerInfo = new Handler() {
        public void handleMessage(Message msg) {
            tvLayoutTitle.setText(order.getShopName());
            Picasso.with(CommentActivity.this).load(u.getUrl()+"Img/"+order.getShopLogo()).into(imgCommentShop);
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.collect_back:
                this.finish();
                break;
            case R.id.btn_comment_submit:
                break;
        }
    }

}
