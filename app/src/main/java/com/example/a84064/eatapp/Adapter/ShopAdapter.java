package com.example.a84064.eatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.a84064.eatapp.FragmentMainActivity;
import com.example.a84064.eatapp.Model.UrlText;
import com.example.a84064.eatapp.Model.simple_shop;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.View.CustomListView;
import com.example.a84064.eatapp.util.ToolImage;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 84064 on 2016/4/17.
 */
public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List <simple_shop> mTitle;
    private int select;
    private com.nostra13.universalimageloader.core.ImageLoader universalimageloader;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    UrlText u=new UrlText();
    public ShopAdapter(Context context, List<simple_shop> title,int sele){
        mContext=context;
        mTitle=title;
        mLayoutInflater= LayoutInflater.from(context);
        universalimageloader = ToolImage.initImageLoader(context);
        select=sele;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(mLayoutInflater.inflate(R.layout.lv_indexlist,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NormalViewHolder viewholder = (NormalViewHolder) holder;
        simple_shop shop=mTitle.get(position);
        universalimageloader.displayImage(u.getUrl()+"Img/"+shop.getLogo(), viewholder.imgIndexShop,
                ToolImage.getFadeOptions(R.mipmap.default_icon,R.mipmap.ic_launcher,R.mipmap.ic_launcher));
        viewholder.tvIndexShopName.setText(shop.getName());
        viewholder.tvIndexShopSold.setText(Html.fromHtml(textColor(String.valueOf(shop.getScore()),"#f4a460")+
                " 月售"+String.valueOf(shop.getSoldNum())+"单"));
        viewholder.tvIndexStartPrice.setText(Html.fromHtml(textColor("￥"+String.valueOf(shop.getStart_price()),"#f4a460")+" 起送"));
        String distance;
        if(shop.getDistint()<1000) {
            distance=String.valueOf((int)shop.getDistint()) + " 米";
        }
        else {
            DecimalFormat df = new DecimalFormat("###.0");
            distance=df.format(shop.getDistint() / 1000) + " 千米";
        }
        viewholder.tvIndexShopTime.setText("40分钟 / "+distance);
        viewholder.tvIndexSendPrice.setText(Html.fromHtml(textColor("￥"+String.valueOf(shop.getSend_price()),"#f4a460")+" 配送"));
        viewholder.tvIndexShopStatus.setText("非营业时间");
        viewholder.tvIndexNum.setText(Html.fromHtml(textColor(String.valueOf(shop.getPayNum()),"#708090")+" 单制作中, "
                +textColor(String.valueOf(shop.getSendNum()),"#708090")+" 单配送中"));
        List<simple_shop.DiscountBean> discount=shop.getDiscount();
        for(int i=0;i<discount.size();i++){
            switch (discount.get(i).getDiacount_name()){
                case "满减优惠":
                    viewholder.tvIndexDiscountMinus.append("满" +String.valueOf(discount.get(i).getDiscount_condition())
                            + "减"+String.valueOf(discount.get(i).getDiscount_price())+" ");
                    break;
                case "新用户立减":
                    viewholder.tvIndexDiscountNew.setText("新用户立减"+ String.valueOf(discount.get(i).getDiscount_price())+"元");
                    break;
                case "免配送费":
                    viewholder.tvIndexDiscountFree.setText("满"+String.valueOf(discount.get(i).getDiscount_condition())+"免配送费");
                    break;
            }
        }
        //判断店铺是有有优惠，没有的话隐藏
        TextView[] v={viewholder.tvIndexDiscountMinus,viewholder.tvIndexDiscountNew,viewholder.tvIndexDiscountFree};
        checkShow(v);
        if(timeCheck(shop.getOpen_time()))
            viewholder.tvIndexShopStatus.setText("营业中");
        //添加搜索到的菜品信息
        if(select==1 && shop.getSimpleMenu()!=null){
            for(int i=0;i<shop.getSimpleMenu().size();i++){
                final LinearLayout layout2=new LinearLayout(mContext);
                layout2.setOrientation(LinearLayout.HORIZONTAL);
                viewholder.layoutIndexMenu.addView(layout2);
                TextView tv_name=new TextView(mContext);
                tv_name.setGravity(Gravity.LEFT);
                tv_name.setText(shop.getSimpleMenu().get(i).getMenu_name());
                TextView tv_price=new TextView(mContext);
                tv_price.setText(String.valueOf(shop.getSimpleMenu().get(i).getMenu_price()));
                tv_name.setGravity(Gravity.RIGHT);
                layout2.addView(tv_name);
                layout2.addView(tv_price);
                layout2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*
                        Intent i=new Intent(mContext, FragmentMainActivity.class);

                        mContext.startActivity(i);
                        */
                    }
                });
            }
        }
        viewholder.rtIndexScore.setRating((float) shop.getScore());
    }
    public String textColor(String text,String color){
        return "<font color='"+color+"'><b>"+text+"</b></font>";
    }

    public void checkShow(TextView[] v){
        for(int i=0;i<v.length;i++) {
            if (TextUtils.isEmpty(v[i].getText()))
                v[i].setVisibility(View.GONE);
            else
                v[i].setVisibility(View.VISIBLE);
        }
    }
    public boolean timeCheck(String shop_time){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String curdate = simpleDateFormat.format(date);

        SimpleDateFormat Format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String[] time=shop_time.split("-");
        String newStartTime = curdate + " " + time[0];// 字符串连接
        String newEndTime = curdate + " " + time[1];// 字符串连接
        Date date_start=new Date();
        Date date_end=new Date();
        try {
            date_start=Format2.parse(newStartTime);
            date_end=Format2.parse(newEndTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(date.after(date_start) && date.before(date_end))
            return true;
        else
            return false;
    }
    @Override
    public int getItemCount() {
        return mTitle==null ? 0 : mTitle.size();
    }

    @Override
    public void onClick(View v) {

    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgIndexShop;
        private TextView tvIndexShopName;
        private TextView tvIndexShopStatus;
        private RatingBar rtIndexScore;
        private TextView tvIndexShopSold;
        private TextView tvIndexStartPrice;
        private TextView tvIndexShopTime;
        private TextView tvIndexSendPrice;
        private TextView tvIndexNum;
        private TextView tvIndexDiscountMinus;
        private TextView tvIndexDiscountNew;
        private TextView tvIndexDiscountFree;
        private LinearLayout layoutIndexMenu;
        CardView mCardView;
        public NormalViewHolder(View itemView) {
            super(itemView);
            mCardView=(CardView)itemView.findViewById(R.id.cv_item);
            imgIndexShop = (ImageView) itemView.findViewById(R.id.img_index_shop);
            tvIndexShopName = (TextView) itemView.findViewById(R.id.tv_index_shopName);
            tvIndexShopStatus = (TextView) itemView.findViewById(R.id.tv_index_shopStatus);
            rtIndexScore = (RatingBar) itemView.findViewById(R.id.rt_index_score);
            tvIndexShopSold = (TextView) itemView.findViewById(R.id.tv_index_shopSold);
            tvIndexStartPrice = (TextView) itemView.findViewById(R.id.tv_index_startPrice);
            tvIndexShopTime = (TextView) itemView.findViewById(R.id.tv_index_shopTime);
            tvIndexSendPrice = (TextView) itemView.findViewById(R.id.tv_index_sendPrice);
            tvIndexNum = (TextView) itemView.findViewById(R.id.tv_index_Num);
            tvIndexDiscountMinus = (TextView) itemView.findViewById(R.id.tv_index_discount_minus);
            tvIndexDiscountNew = (TextView) itemView.findViewById(R.id.tv_index_discount_new);
            tvIndexDiscountFree = (TextView) itemView.findViewById(R.id.tv_index_discount_free);
            layoutIndexMenu = (LinearLayout) itemView.findViewById(R.id.layout_index_menu);
        }
    }

}
