package com.example.a84064.eatapp.util;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by 84064 on 2016/4/25.
 */
public class CommonPopupWindow implements PopupListener {
    private static final String TAG = "CommonPopupWindow";
    protected Activity mContext;
    private int mWidth, mHeight;
    //元素定义
    protected PopupWindow mPopupWindow;
    //popup视图
    protected View mPopupView;
    protected View mDismissView;
    protected View mInsideView;
    private Drawable mBgDrawable;

    public CommonPopupWindow(Activity context) {
        mContext = context;
        //默认占满全屏
        mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        initView();
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public void setBackgroundDrawable(Drawable drawable) {
        mBgDrawable = drawable;
    }

    private void initView() {
        mPopupView = getPopupView();
        mDismissView = getDismissView();
        mInsideView = getInsideView();
        mPopupView.setFocusableInTouchMode(true);
        mPopupWindow = new PopupWindow(mPopupView, mWidth, mHeight);
        //指定透明背景
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(0);
        onOutSideClickDismiss();
    }

    public void showPopupWindow(View view) {
        try {
            if (view != null) {
                mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            } else {
                mPopupWindow.showAtLocation(mContext.findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
            }
        } catch (Exception e) {
            Log.e(TAG, "showPopupWindow(View) error");
            e.printStackTrace();
        }
    }

    public View getPopupViewById(int resId) {
        if (resId != 0) {
            return LayoutInflater.from(mContext).inflate(resId, null);
        } else {
            return null;
        }
    }

    public boolean isShowing() {
        return mPopupWindow.isShowing();
    }

    public void dismiss() {
        try {
            if (mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            }
        } catch (Exception e) {
            Log.d(TAG, "dismiss error");
        }
    }

    public View getDismissView() {
        return null;
    }

    public void onOutSideClickDismiss() {
        if (mDismissView != null) {
            mDismissView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        if( mInsideView != null){
            mInsideView.setOnClickListener(null);
        }

    }

    @Override
    public View getPopupView() {
        return null;
    }

    @Override
    public View getInsideView() {
        return null;
    }

    ;
}
