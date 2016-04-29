package com.example.a84064.eatapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a84064.eatapp.Model.Shop_Type;
import com.example.a84064.eatapp.R;

import java.util.List;

/**
 * Created by 84064 on 2016/4/18.
 */
public class TextAdapter extends ArrayAdapter<Shop_Type.type_value> {
    private int type;
    private Context mContext;
    /**
     * 列表数据
     */
    private List<Shop_Type.type_value> mListData;
    /**
     * 选中的位置
     */
    private int selectedPos = -1;
    /**
     * 是否显示选中指示
     */
    private boolean mIsSelectedShow;
    /**
     * 点击监听
     */
    private View.OnClickListener onClickListener;
    /**
     * item的点击监听
     */
    private OnItemClickListener mOnItemClickListener;

    /**
     * 构造一个医生列表条件筛选列表数据适配器
     *
     * @param context
     * @param listData
     *            传入列表数据
     * @param isSelectedShow 是否显示选中指示
     */
    public TextAdapter(Context context, List<Shop_Type.type_value> listData, boolean isSelectedShow,int t) {
        super(context, 0, listData);
        mContext = context;
        mListData = listData;
        mIsSelectedShow = isSelectedShow;
        type=t;
        init();
    }

    /**
     * 初始化 构造时调用
     */
    private void init() {
        onClickListener = new View.OnClickListener() {

            public void onClick(View view) {
                if (selectedPos != -1) {
                    selectedPos = (Integer) view.getTag();
                    setSelectedPosition(selectedPos);
                }

                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, (Integer) view.getTag());
                }
            }
        };
    }

    /**
     * 设置选中的position,并通知列表刷新
     */
    public void setSelectedPosition(int pos) {
        if (mListData != null && pos < mListData.size()) {
            selectedPos = pos;
            notifyDataSetChanged();
        }
    }

    /**
     * 设置选中的position,但不通知刷新
     */
    public void setSelectedPositionNoNotify(int pos) {
        selectedPos = pos;
    }

    /**
     * 获取选中的position
     */
    public int getSelectedPosition() {
        if (mListData != null && selectedPos < mListData.size()) {
            return selectedPos;
        }

        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        TextView tvIndexNum;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_pop_content, parent, false);
        }
        textView = (TextView) convertView.findViewById(R.id.item_textView_popItem);
        tvIndexNum = (TextView) convertView.findViewById(R.id.tv_index_num);
        convertView.setTag(position);
        String mString = "";
        String mNum="";
        if (mListData != null) {
            if (position < mListData.size()) {
                mString = mListData.get(position).getName();
                mNum=String.valueOf(mListData.get(position).getNum());
            }
        }
        textView.setText(mString);
        tvIndexNum.setText(mNum);
        if(type==0)
            tvIndexNum.setVisibility(View.VISIBLE);
        else
            tvIndexNum.setVisibility(View.GONE);
        convertView.setOnClickListener(onClickListener);

        if (mIsSelectedShow) {// 设置点击选择后改变背景效果
            convertView.setBackgroundColor(Color.WHITE);
            if (position == selectedPos) {
                convertView.setBackgroundColor(mContext.getResources().getColor(R.color.selection_gray));
            }
        }

        return convertView;
    }

    /**
     * 设置item点击监听
     *
     * @param l
     *            实现本类的OnItemClickListener接口
     */
    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    /**
     * 重新定义菜单选项单击接口
     */
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

}
