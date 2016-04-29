package com.example.a84064.eatapp;

import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.a84064.eatapp.AccountPage.MyFragment;
import com.example.a84064.eatapp.MenuPage.IndexFragment;
import com.example.a84064.eatapp.OrderPage.OrderFragment;
import com.example.a84064.eatapp.R;


/**
 * Created by 84064 on 2016/3/30.
 */
public class FragmentMainActivity extends AppCompatActivity implements View.OnClickListener {
    private IndexFragment mTab01;
    private OrderFragment mTab02;
    private MyFragment mTab03;

    private LinearLayout mTabBtnIndex;
    private LinearLayout mTabBtnOrder;
    private LinearLayout mTabBtnMine;

    private FragmentManager fragmentManager;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        initViews();
        fragmentManager = getFragmentManager();
        setTabSelection(0);
    }

    private void initViews()
   {
       mTabBtnIndex = (LinearLayout) findViewById(R.id.id_tab_bottom_index);
       mTabBtnOrder = (LinearLayout) findViewById(R.id.id_tab_bottom_order);
       mTabBtnMine = (LinearLayout) findViewById(R.id.id_tab_bottom_mine);

       mTabBtnIndex.setOnClickListener(this);
       mTabBtnOrder.setOnClickListener(this);
       mTabBtnMine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.id_tab_bottom_index:
                setTabSelection(0);
                break;
            case R.id.id_tab_bottom_order:
                setTabSelection(1);
                break;
            case R.id.id_tab_bottom_mine:
                setTabSelection(2);
                break;
            default:
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     */
    private void setTabSelection(int index)
    {
        // 重置按钮
        resetBtn();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index)
        {
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                ((ImageButton) mTabBtnIndex.findViewById(R.id.btn_tab_bottom_index))
                        .setImageResource(R.mipmap.tab_weixin_pressed);
                if (mTab01 == null)
                {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    mTab01 = new IndexFragment();
                    transaction.add(R.id.id_content,mTab01);
                } else
                {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(mTab01);
                }
                break;
            case 1:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                ((ImageButton) mTabBtnOrder.findViewById(R.id.btn_tab_bottom_order))
                        .setImageResource(R.mipmap.tab_find_frd_pressed);
                if (mTab02 == null)
                {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    mTab02 = new OrderFragment();
                    transaction.add(R.id.id_content, mTab02);
                } else
                {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(mTab02);
                }
                break;
            case 2:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                ((ImageButton) mTabBtnMine.findViewById(R.id.btn_tab_bottom_mine))
                        .setImageResource(R.mipmap.tab_address_pressed);
                if (mTab03 == null)
                {
                    // 如果NewsFragment为空，则创建一个并添加到界面上
                    mTab03 = new MyFragment();
                    transaction.add(R.id.id_content, mTab03);
                } else
                {
                    // 如果NewsFragment不为空，则直接将它显示出来
                    transaction.show(mTab03);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void resetBtn()
    {
        ((ImageButton) mTabBtnIndex.findViewById(R.id.btn_tab_bottom_index))
                .setImageResource(R.mipmap.tab_weixin_normal);
        ((ImageButton) mTabBtnOrder.findViewById(R.id.btn_tab_bottom_order))
                .setImageResource(R.mipmap.tab_find_frd_normal);
        ((ImageButton) mTabBtnMine.findViewById(R.id.btn_tab_bottom_mine))
                .setImageResource(R.mipmap.tab_address_normal);

    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction)
    {
        if (mTab01 != null)
        {
            transaction.hide(mTab01);
        }
        if (mTab02 != null)
        {
            transaction.hide(mTab02);
        }
        if (mTab03 != null)
        {
            transaction.hide(mTab03);
        }

    }




}
