package com.example.a84064.eatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a84064.eatapp.Model.UrlText;
import com.example.a84064.eatapp.Model.orderList;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.util.CircleTransform;
import com.example.a84064.eatapp.util.SquaredImageView;
import com.example.a84064.eatapp.util.ToolImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 84064 on 2016/4/11.
 */
public class OrderMenuAdapter extends BaseAdapter {
    private com.nostra13.universalimageloader.core.ImageLoader universalimageloader;
    UrlText u=new UrlText();
    private LayoutInflater inflater;
    private List<orderList.MenuListBean> list;
    OrderAdapter adapter;
    int storePosition;
    private Context context;
    public OrderMenuAdapter(Context context,List<orderList.MenuListBean> list, OrderAdapter adapter, int storePosition) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.adapter = adapter;
        this.storePosition = storePosition;
        this.context = context;
        universalimageloader = ToolImage.initImageLoader(context);
    }
    public OrderMenuAdapter(Context context,List<orderList.MenuListBean> list){
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        universalimageloader = ToolImage.initImageLoader(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holderView = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.listview_ordermenu, null);
            holderView = new HolderView(convertView);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        holderView.tvOrdermenuName.setText(list.get(position).getMenu_name());
        holderView.tvOrdermenuPrice.setText(String.valueOf(list.get(position).getMenu_price()));
        holderView.tvOrdermenuNum.setText("x "+String.valueOf(list.get(position).getMenu_num()));
        //Picasso.with(context).load(u.getUrl()+"Img/"+list.get(position).getMenu_img()).into(holderView.orderMenuImg);
        universalimageloader.displayImage(u.getUrl()+"Img/"+list.get(position).getMenu_img(), holderView.orderMenuImg, ToolImage.getFadeOptions(R.mipmap.default_icon,R.mipmap.ic_launcher,R.mipmap.ic_launcher));
        return convertView;
    }

    class HolderView {
        private ImageView orderMenuImg;
        private TextView tvOrdermenuName;
        private TextView tvOrdermenuPrice;
        private TextView tvOrdermenuNum;

        HolderView(View view) {
            orderMenuImg = (ImageView) view.findViewById(R.id.order_menu_img);
            tvOrdermenuName = (TextView) view.findViewById(R.id.tv_ordermenu_name);
            tvOrdermenuPrice = (TextView) view.findViewById(R.id.tv_ordermenu_price);
            tvOrdermenuNum = (TextView) view.findViewById(R.id.tv_ordermenu_num);
        }
    }
}
