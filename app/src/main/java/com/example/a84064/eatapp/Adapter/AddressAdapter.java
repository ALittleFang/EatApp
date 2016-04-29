package com.example.a84064.eatapp.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a84064.eatapp.Model.Account;
import com.example.a84064.eatapp.Model.Address;
import com.example.a84064.eatapp.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 84064 on 2016/4/6.
 */
public class AddressAdapter extends BaseAdapter {

    private List<Address> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public AddressAdapter(Context context, List<Address> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }
    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_address, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        Address address = data.get(position);
        String sex=" 女士";
        if(address.isSex())
            sex=" 先生";
        holder.name.setText(address.getName()+sex);
        holder.tel.setText(address.getTel());
        holder.address.setText(address.getStreet()+address.getDetail());
        return convertView;
    }

    public void addData(String json){
        Gson g=new Gson();
        Address a=g.fromJson(json,Address.class);
        data.add(a);
        notifyDataSetChanged();
    }
    public void deleteData(String id){
        for(int i=0;i<data.size();i++){
            Address map=data.get(i);
            if(String.valueOf(map.getAddress_id()).equals(id)) {
                data.remove(map);
                break;
            }
        }
        notifyDataSetChanged();
    }
    public void updateData(String json){
        Gson g=new Gson();
        Address a=g.fromJson(json,Address.class);
        for(int i=0;i<data.size();i++){
            Address map=data.get(i);
            if(map.getAddress_id()==a.getAddress_id()) {
                map=a;
                break;
            }
        }
        notifyDataSetChanged();
    }
    class ViewHolder {
        public TextView name;
        public TextView tel;
        public TextView address;

        ViewHolder(View view) {
            name=(TextView)view.findViewById(R.id.address_name);
            tel=(TextView)view.findViewById(R.id.address_tel);
            address=(TextView)view.findViewById(R.id.address_local);
        }
    }
}


