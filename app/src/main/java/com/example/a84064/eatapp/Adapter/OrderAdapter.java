package com.example.a84064.eatapp.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a84064.eatapp.Model.orderList;
import com.example.a84064.eatapp.Model.shop_list;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.WebServices.OrderWebService;
import com.example.a84064.eatapp.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.smssdk.gui.DefaultContactViewItem;

/**
 * Created by 84064 on 2016/4/11.
 */
public class OrderAdapter extends BaseAdapter{
    private Context context;
    private List<orderList> orders;
    private LinearLayout linearLayout;
    private ListItemClickHelp callback;
    List<OrderMenuAdapter> pAdapterList = new ArrayList<OrderMenuAdapter>();

    public OrderAdapter(Context context, List<orderList> order,ListItemClickHelp callback) {
        this.context = context;
        this.orders = order;
        this.callback = callback;
        for (int i = 0; i < order.size(); i++) {
            OrderMenuAdapter pAdapter = new OrderMenuAdapter(context, order.get(i).getMenuList(), this, i);
            pAdapterList.add(pAdapter);
        }
    }
    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_order, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder =(ViewHolder) convertView.getTag();
        }
        orderList order=orders.get(position);
        holder.tvOrderPrice.setText("共计"+String.valueOf(order.getTotalPrice()+"元"));
        holder.tvOrderShopName.setText(order.getShopName());
        holder.lvOrderMenu.setAdapter(pAdapterList.get(position));
        String button_text="评价";
        String delete_button_text="删除";
        String sta="";
        int sta_id=0;
        switch (order.getStatus()){
            case 1:
                sta="代付款";
                delete_button_text="付款";
                button_text="取消订单";
                //holder.btnOrderEdit.setVisibility(View.VISIBLE);
                //holder.btnOrderResive.setVisibility(View.GONE);
                holder.btnOrderDelete.setVisibility(View.VISIBLE);
                break;
            case 2:
                sta="等待店家接单";
                button_text="取消订单";
                //holder.btnOrderEdit.setVisibility(View.VISIBLE);
                //holder.btnOrderResive.setVisibility(View.GONE);
                holder.btnOrderDelete.setVisibility(View.GONE);
                break;
            case 3:
                sta="等待店家配送";
                button_text="申请退款";
                //holder.btnOrderEdit.setVisibility(View.VISIBLE);
                //holder.btnOrderResive.setVisibility(View.GONE);
                holder.btnOrderDelete.setVisibility(View.GONE);
                break;
            case 4:
                sta="配送中";
                button_text="申请退款";
                delete_button_text="确认收货";
                //holder.btnOrderEdit.setVisibility(View.VISIBLE);
                //holder.btnOrderResive.setVisibility(View.VISIBLE);
                holder.btnOrderDelete.setVisibility(View.VISIBLE);
                break;
            case 5:
                sta="退款中";
                button_text="取消退款";
                delete_button_text="确认收货";
                //holder.btnOrderEdit.setVisibility(View.VISIBLE);
                //holder.btnOrderResive.setVisibility(View.VISIBLE);
                holder.btnOrderDelete.setVisibility(View.VISIBLE);
                break;
            case 6:
                sta="交易关闭";
                //holder.btnOrderEdit.setVisibility(View.GONE);
                //holder.btnOrderResive.setVisibility(View.GONE);
                holder.btnOrderDelete.setVisibility(View.VISIBLE);
                break;
            case 0:
                sta="交易成功";
                button_text="评价";
                //holder.btnOrderEdit.setVisibility(View.GONE);
                //holder.btnOrderResive.setVisibility(View.GONE);
                holder.btnOrderDelete.setVisibility(View.VISIBLE);
                break;
            default:
                sta="交易成功";
                holder.btnOrderEdit.setVisibility(View.GONE);
                holder.btnOrderDelete.setVisibility(View.VISIBLE);
                break;
        }
        holder.btnOrderEdit.setText(button_text);
        holder.btnOrderDelete.setText(delete_button_text);
        holder.tvOrderStatus.setText(sta);

        final View view = convertView;
        final String edit_text=button_text;
        final String delete_text=delete_button_text;
        holder.btnOrderEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick(view, parent, position, R.id.btn_order_edit,edit_text);
            }
        });
        holder.btnOrderDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick(view, parent, position, R.id.img_order_delete,delete_text);
            }
        });
        return convertView;
    }

    public void deleteOrder(int position){
        orders.remove(orders.get(position));
        notifyDataSetChanged();
    }

    public void updateData(int position,int sta){
        orderList o=(orderList)orders.get(position);
        o.setStatus(sta);
        notifyDataSetChanged();
    }

    class ViewHolder {
        private TextView tvOrderStatus;
        private Button btnOrderDelete;
        private TextView tvOrderShopName;
        private ListView lvOrderMenu;
        private TextView tvOrderPrice;
        private Button btnOrderEdit;

        ViewHolder(View view) {
            tvOrderStatus = (TextView) view.findViewById(R.id.tv_order_status);
            btnOrderDelete = (Button)  view.findViewById(R.id.img_order_delete);
            tvOrderShopName = (TextView)  view.findViewById(R.id.tv_order_shopName);
            lvOrderMenu = (ListView)  view.findViewById(R.id.lv_order_menu);
            tvOrderPrice = (TextView)  view.findViewById(R.id.tv_order_price);
            btnOrderEdit = (Button)  view.findViewById(R.id.btn_order_edit);
        }
    }

}

