package com.example.a84064.eatapp.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a84064.eatapp.Model.VoucherInfo;
import com.example.a84064.eatapp.R;

import java.util.List;

/**
 * Created by 84064 on 2016/4/23.
 */
public class VoucherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<VoucherInfo> mTitle;
    public VoucherAdapter(Context context, List<VoucherInfo> title){
        mContext=context;
        mTitle=title;
        mLayoutInflater= LayoutInflater.from(context);
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(mLayoutInflater.inflate(R.layout.rv_luckybug,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NormalViewHolder viewholder = (NormalViewHolder) holder;
        VoucherInfo voucher=mTitle.get(position);
        viewholder.tvIndexUseTime.setText("有效期至"+voucher.getEnd_time());
        viewholder.tvLuckyBugShopName.setText("仅限在“"+voucher.getShop_name()+"”店铺中使用");
        viewholder.tvLuckyBugPrice.setText(Html.fromHtml("￥"+textColor(String.valueOf(voucher.getPrice()),"#f4a460")));
        viewholder.tvLuckyBugName.setText(voucher.getName());
        viewholder.tvLuckyBugUsePrice.setText("满"+voucher.getUsePrice()+"可用");
        String text="";
        switch (voucher.getStatus()){
            case 0:
                text="立即使用";
                break;
            case 1:
                text="已过期";
                break;
            case -1:
                text="已使用";
                break;
        }
        viewholder.tvLuckyBugStatus.setText(text);
    }
    public String textColor(String text,String color){
        return "<font color='"+color+"'><b>"+text+"</b></font>";
    }

    @Override
    public int getItemCount() {
        return mTitle==null ? 0 : mTitle.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        private CardView cvItem;
        private TextView tvLuckyBugPrice;
        private TextView tvLuckyBugUsePrice;
        private TextView tvLuckyBugName;
        private TextView tvLuckyBugShopName;
        private TextView tvIndexUseTime;
        private TextView tvLuckyBugStatus;

        public NormalViewHolder(View itemView) {
            super(itemView);
            cvItem = (CardView) itemView.findViewById(R.id.cv_item);
            tvLuckyBugPrice = (TextView) itemView.findViewById(R.id.tv_luckyBug_price);
            tvLuckyBugUsePrice = (TextView) itemView.findViewById(R.id.tv_luckyBug_usePrice);
            tvLuckyBugName = (TextView) itemView.findViewById(R.id.tv_luckyBug_name);
            tvLuckyBugShopName = (TextView) itemView.findViewById(R.id.tv_luckyBug_shopName);
            tvIndexUseTime = (TextView) itemView.findViewById(R.id.tv_index_useTime);
            tvLuckyBugStatus = (TextView) itemView.findViewById(R.id.tv_luckyBug_status);
        }
    }
}
