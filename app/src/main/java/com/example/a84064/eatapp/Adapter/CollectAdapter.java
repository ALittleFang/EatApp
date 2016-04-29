package com.example.a84064.eatapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.baidu.mapapi.utils.DistanceUtil;
import com.example.a84064.eatapp.Model.UrlText;
import com.example.a84064.eatapp.Model.shop_list;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.util.CircleTransform;
import com.example.a84064.eatapp.util.ToolImage;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by 84064 on 2016/4/9.
 */
public class CollectAdapter extends BaseAdapter {
    private Context context;
    private List<shop_list> collects;
    private LinearLayout linearLayout;
    private com.nostra13.universalimageloader.core.ImageLoader universalimageloader;


    public CollectAdapter(Context context, List<shop_list> shop) {
        this.context = context;
        this.collects = shop;
        universalimageloader = ToolImage.initImageLoader(context);
    }
    @Override
    public int getCount() {
        return collects.size();
    }

    @Override
    public Object getItem(int position) {
        return collects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_collect, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        shop_list shop = collects.get(position);
        holder.collectName.setText(shop.getShop_name());
        holder.collectScore.setText(String.valueOf(shop.getScore()));
        holder.smallRatingbar.setRating((float) shop.getScore());
        universalimageloader.displayImage(shop.getLogo(), holder.collectImg,
                ToolImage.getFadeOptions(R.mipmap.default_icon,R.mipmap.ic_launcher,R.mipmap.ic_launcher));
        //Picasso.with(context).load(shop.getLogo()).transform(new CircleTransform()).into(holder.collectImg);
        //Picasso.with(context).load(shop.getLogo()).into(holder.collectImg);
        if(shop.getDistance()<1000) {
            holder.collectDistance.setText(String.valueOf((int)shop.getDistance()) + " m");
        }
        else {
            DecimalFormat df = new DecimalFormat("###.0");
            holder.collectDistance.setText(df.format(shop.getDistance() / 1000) + " Km");
        }
        holder.collectOrder.setText(String.valueOf(shop.getPayNum())+"单制作中|");
        holder.collectSendOrder.setText(String.valueOf(shop.getSendNum())+"单正在配送中");
        if(shop.getNewMenu()!=0)
            holder.collectNew.setText("今日上新"+String.valueOf(shop.getNewMenu())+"款菜品");
        else
            holder.collectNew.setVisibility(View.GONE);
        return convertView;
    }

    public void updateCollect(int position){
        collects.remove(collects.get(position));
        notifyDataSetChanged();
    }
    class ViewHolder {
        private ImageView collectImg;
        private TextView collectName;
        private RatingBar smallRatingbar;
        private TextView collectScore;
        private TextView collectDistance;
        private TextView collectOrder;
        private TextView collectSendOrder;
        private TextView collectNew;

        ViewHolder(View view) {
            collectImg = (ImageView) view.findViewById(R.id.collect_img);
            collectName = (TextView) view.findViewById(R.id.collect_name);
            smallRatingbar = (RatingBar) view.findViewById(R.id.small_ratingbar);
            collectScore = (TextView) view.findViewById(R.id.collect_score);
            collectDistance = (TextView) view.findViewById(R.id.collect_distance);
            collectOrder = (TextView) view.findViewById(R.id.collect_order);
            collectSendOrder = (TextView) view.findViewById(R.id.collect_sendOrder);
            collectNew = (TextView) view.findViewById(R.id.collect_new);
        }
    }

}
