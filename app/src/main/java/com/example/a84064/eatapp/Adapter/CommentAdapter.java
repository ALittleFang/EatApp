package com.example.a84064.eatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a84064.eatapp.Model.orderList;
import com.example.a84064.eatapp.R;

import java.util.List;

/**
 * Created by 84064 on 2016/4/13.
 */
public class CommentAdapter extends BaseAdapter {
    private Context context;
    private List<orderList.MenuListBean> menus;
    private LinearLayout linearLayout;
    public CommentAdapter(Context context, List<orderList.MenuListBean> menus) {
        this.context = context;
        this.menus = menus;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_menucomment, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvCommentMenuName.setText(menus.get(position).getMenu_name());
        return convertView;
    }
    class ViewHolder {
        private TextView tvCommentMenuName;
        private ImageView imgRight;

        ViewHolder(View view) {
            tvCommentMenuName = (TextView) view.findViewById(R.id.tv_comment_menuName);
            imgRight = (ImageView) view.findViewById(R.id.img_right);
        }
    }
}
