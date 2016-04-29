package com.example.a84064.eatapp.View;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.a84064.eatapp.Adapter.TextAdapter;
import com.example.a84064.eatapp.Model.Shop_Type;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.View.ExpandMenuView.ViewBaseAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 84064 on 2016/4/18.
 */
public class DoubleListFilterView extends LinearLayout implements ViewBaseAction {

    /**
     * 父列表
     */
    private ListView view_listView_super;
    /**
     * 子列表
     */
    private ListView view_listView_child;
    /**
     * 父列表的数据
     */
    private List<Shop_Type.type_value> superItemDatas = new ArrayList<Shop_Type.type_value>();
    /**
     * 所有子列表数据
     */
    private SparseArray<List<Shop_Type.type_value>> allChildItemDatas = new SparseArray<List<Shop_Type.type_value>>();
    /**
     * 父列表对应的子列表的数据
     */
    private List<Shop_Type.type_value> childItemDatas = new ArrayList<Shop_Type.type_value>();
    private TextAdapter superListAdapter;
    private TextAdapter childListAdapter;
    /**
     * 选择监听
     */
    private OnSelectListener mOnSelectListener;
    /**
     * 父列表的默认选择位置
     */
    private int superPosition = 0;
    /**
     * 子列表的默认选择位置
     */
    private int childPosition = 0;
    /**
     * 显示的文本
     */
    private String showStr = "";

    /**
     * 构造一个双选择列表
     *
     * @param context
     * @param defPos
     *            默认选中的一级列表位置
     * @param defPosChild
     *            默认选中个二级列表位置
     * @param showStr
     *            默认显示的文本
     * @param superItemDatas
     *            一级列表数据
     * @param allChildItemDatas
     *            二级列表数据
     */
    public DoubleListFilterView(Context context, String showStr, ArrayList<Shop_Type.type_value> superItemDatas, SparseArray<List<Shop_Type.type_value>> allChildItemDatas,
                                int defPos, int defPosChild) {
        super(context);
        this.superItemDatas = superItemDatas;
        this.allChildItemDatas = allChildItemDatas;
        this.showStr = showStr;
        superPosition = defPos;
        childPosition = defPosChild;
        init(context);
    }

    /**
     * 控件初始化，构造时调用
     *
     * @param context
     */
    private void init(final Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_filter_list_double, this, true);
        view_listView_super = (ListView) findViewById(R.id.view_listView_main);
        view_listView_child = (ListView) findViewById(R.id.view_listView_child);

        // 设置父列表数据
        superListAdapter = new TextAdapter(context, superItemDatas, true,0);
        superListAdapter.setSelectedPosition(superPosition);
        view_listView_super.setAdapter(superListAdapter);
        superListAdapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

            public void onItemClick(View view, int position) {
                superPosition = position;
                if (position < allChildItemDatas.size()) {
                    showStr = superItemDatas.get(position).getName();
                    superListAdapter.setSelectedPosition(position);
                    childItemDatas.clear();
                    if (allChildItemDatas.get(position) != null) {
                        childItemDatas.addAll(allChildItemDatas.get(position));
                    }
                    childListAdapter.notifyDataSetChanged();
                    if (childItemDatas.size() == 0 && mOnSelectListener != null) {
                        mOnSelectListener.getValue(showStr, superPosition, position);// 点击监听回掉
                    }
                }

            }
        });

        // 设置子列表数据
        if (superPosition < allChildItemDatas.size() && allChildItemDatas.get(superPosition) != null) {
            childItemDatas.addAll(allChildItemDatas.get(superPosition));
        }
        childListAdapter = new TextAdapter(context, childItemDatas, false,0);
        childListAdapter.setSelectedPosition(childPosition);
        view_listView_child.setAdapter(childListAdapter);
        childListAdapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

            public void onItemClick(View view, final int position) {
                childPosition = position;
                showStr = childItemDatas.get(position).getName();
                if (mOnSelectListener != null) {
                    mOnSelectListener.getValue(showStr, superPosition, position);// 点击监听回掉
                }
            }
        });
        if (childPosition < childItemDatas.size()) {
            showStr = childItemDatas.get(childPosition).getName();
        }
        setDefSelected();

    }

    /**
     * 设置默认选择项
     */
    public void setDefSelected() {
        view_listView_super.setSelection(superPosition);
        view_listView_child.setSelection(childPosition);
    }

    /**
     * 获取当前显示的文本
     *
     * @return
     */
    public String getShowText() {
        return showStr;
    }

    /**
     * 设置点击（选择）监听 需要实现 OnSelectListener接口
     *
     * @param onSelectListener
     */
    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    /**
     * 设置点击（选择监听需要实现的接口）
     *
     * @author warmdoc_ANDROID_001
     *
     */
    public interface OnSelectListener {
        /**
         * 监听实现此方法
         *
         * @param showText
         *            选择到的数据
         * @param superPosition
         *            选择的位置所在的父类列表的位置
         * @param position
         *            选择的位置
         */
        public void getValue(String showText, int superPosition, int position);
    }

    public void hideMenu() {
        // TODO Auto-generated method stub

    }

    public void showMenu() {
        // TODO Auto-generated method stub

    }
}
