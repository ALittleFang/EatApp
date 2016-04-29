package com.example.a84064.eatapp.View;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a84064.eatapp.Model.Account_Cart;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.util.CommonPopupWindow;

import java.util.List;

/**
 * Created by 84064 on 2016/4/25.
 */
public class ListPopup extends CommonPopupWindow {

    private ListView mListView;
    private IListClickListener mOnListClickListener;

    public ListPopup(Activity context, List<Account_Cart.CartMenuBean> listDate) {
        super(context);
        mListView = (ListView) mPopupView.findViewById(R.id.popup_list);
        setAdapter(context, listDate);
    }

    public void setOnListClickListener(IListClickListener listener) {
        mOnListClickListener = listener;
    }
    @Override
    public View getPopupView() {
        return getPopupViewById(R.layout.pop_cart);
    }

    @Override
    public View getInsideView() {
        return mPopupView.findViewById(R.id.pop_cart);
    }

    @Override
    public View getDismissView() {
        return mPopupView;
    }

    private void setAdapter(Activity context, List<Account_Cart.CartMenuBean> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        final ListBaseAdapter adapter = new ListBaseAdapter(context, data);
        mListView.setAdapter(adapter);
    }

    class ListBaseAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Context mContext;
        private List<Account_Cart.CartMenuBean> mItemList;

        public ListBaseAdapter(Context context, @NonNull List<Account_Cart.CartMenuBean> itemList) {
            mContext = context;
            mItemList = itemList;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mItemList == null || mItemList.isEmpty() ? 0 : mItemList.size();
        }

        @Override
        public Object getItem(int position) {
            if (position < 0 || position > mItemList.size() - 1) {
                return null;
            }
            /*
            if (mItemList.get(position) instanceof String) {
                return (String) mItemList.get(position);
            }
            if (mItemList.get(position) instanceof clickItem) {
                return ((clickItem) mItemList.get(position)).getItemStr();
            }
            return null;
            */
            return mItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh = null;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = mInflater.inflate(R.layout.listview_cart, parent, false);
                vh.tvMenuName = (TextView) convertView.findViewById(R.id.tv_menuName);
                vh.tvMenuPrice = (TextView) convertView.findViewById(R.id.tv_menuPrice);
                vh.imgSearchMenuMinus = (ImageButton) convertView.findViewById(R.id.img_cartMenu_minus);
                vh.tvSearchMenuNum = (TextView) convertView.findViewById(R.id.tv_cartMenu_num);
                vh.imgSearchMenuAdd = (ImageButton) convertView.findViewById(R.id.img_cartMenu_add);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            Account_Cart.CartMenuBean cart=(Account_Cart.CartMenuBean)getItem(position);
            vh.tvMenuName.setText(cart.getMenu_name());
            vh.tvMenuPrice.setText("¥ "+String.valueOf(cart.getMenu_price()));
            vh.tvSearchMenuNum.setText(String.valueOf(cart.getMenu_num()));
            return convertView;
        }

        public List<Account_Cart.CartMenuBean> getItemList() {
            return this.mItemList;
        }


        class ViewHolder {
            private TextView tvMenuName;
            private TextView tvMenuPrice;
            private ImageButton imgSearchMenuMinus;
            private TextView tvSearchMenuNum;
            private ImageButton imgSearchMenuAdd;
        }
    }


    public interface IListClickListener {
        void onItemClick(int id);
    }
}
