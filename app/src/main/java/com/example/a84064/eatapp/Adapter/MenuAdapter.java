package com.example.a84064.eatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a84064.eatapp.Model.Menu_Info;
import com.example.a84064.eatapp.Model.UrlText;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.util.ToolImage;

import java.net.URL;
import java.util.List;

/**
 * Created by 84064 on 2016/4/24.
 */
public class MenuAdapter extends BaseAdapter {
    private Context context;
    private List<Menu_Info> menus;
    private LinearLayout linearLayout;
    private com.nostra13.universalimageloader.core.ImageLoader universalimageloader;
    UrlText u=new UrlText();

    public MenuAdapter(Context context, List<Menu_Info> shop) {
        this.context = context;
        this.menus = shop;
        universalimageloader = ToolImage.initImageLoader(context);
    }
    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public Object getItem(int position) {
        return menus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.lv_menu, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Menu_Info menu = menus.get(position);
        holder.tvSearchMenuName.setText(menu.getMenu_name());
        holder.tvSearchMenuGood.setText(String.valueOf(menu.getGood_num()));
        universalimageloader.displayImage(u.getUrl()+"Img/"+menu.getMenu_img(), holder.imgSearchMenuLogo,
                ToolImage.getFadeOptions(R.mipmap.default_icon,R.mipmap.ic_launcher,R.mipmap.ic_launcher));
        holder.tvSearchMenuPrice.setText("￥ "+String.valueOf(menu.getMenu_price()));
        holder.tvSearchMenuSold.setText("月售"+String.valueOf(menu.getMonth_sold())+"单");
        if(menu.isHot_menu())
            holder.imgSearchMenuHot.setVisibility(View.VISIBLE);
        else
            holder.imgSearchMenuHot.setVisibility(View.GONE);
        if(menu.isNew_menu())
            holder.imgSearchMenuNew.setVisibility(View.VISIBLE);
        else
            holder.imgSearchMenuNew.setVisibility(View.GONE);
        if(menu.getCart_num()==0){
            holder.imgSearchMenuMinus.setVisibility(View.GONE);
            holder.tvSearchMenuNum.setVisibility(View.GONE);
        }
        else {
            holder.imgSearchMenuMinus.setVisibility(View.VISIBLE);
            holder.tvSearchMenuNum.setVisibility(View.VISIBLE);
            holder.tvSearchMenuNum.setText(String.valueOf(menu.getCart_num()));
        }
        return convertView;
    }

    class ViewHolder {
        private ImageView imgSearchMenuLogo;
        private TextView tvSearchMenuName;
        private ImageView imgSearchMenuHot;
        private TextView tvSearchMenuSold;
        private TextView tvSearchMenuGood;
        private TextView tvSearchMenuPrice;
        private ImageButton imgSearchMenuAdd;
        private ImageView imgSearchMenuNew;
        private ImageButton imgSearchMenuMinus;
        private TextView tvSearchMenuNum;

        ViewHolder(View view) {
            imgSearchMenuLogo = (ImageView) view.findViewById(R.id.img_searchMenu_logo);
            tvSearchMenuName = (TextView) view.findViewById(R.id.tv_searchMenu_name);
            imgSearchMenuHot = (ImageView) view.findViewById(R.id.img_searchMenu_hot);
            tvSearchMenuSold = (TextView) view.findViewById(R.id.tv_searchMenu_sold);
            tvSearchMenuGood = (TextView) view.findViewById(R.id.tv_searchMenu_good);
            tvSearchMenuPrice = (TextView) view.findViewById(R.id.tv_searchMenu_price);
            imgSearchMenuAdd = (ImageButton) view.findViewById(R.id.img_searchMenu_add);
            imgSearchMenuNew = (ImageView) view.findViewById(R.id.img_searchMenu_new);
            imgSearchMenuMinus = (ImageButton) view.findViewById(R.id.img_searchMenu_minus);
            tvSearchMenuNum = (TextView) view.findViewById(R.id.tv_searchMenu_num);
        }
    }

}
