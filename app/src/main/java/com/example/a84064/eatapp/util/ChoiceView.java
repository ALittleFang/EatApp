package com.example.a84064.eatapp.util;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.a84064.eatapp.R;

/**
 * Created by 84064 on 2016/4/12.
 */
//
public class ChoiceView extends FrameLayout implements Checkable {
    private TextView mTextView;
    private RadioButton mRadioButton;

    public ChoiceView(Context context) {
        super(context);
        View.inflate(context, R.layout.listview_reason, this);
        mTextView = (TextView) findViewById(R.id.reason_ctext);
        mRadioButton = (RadioButton) findViewById(R.id.reason_checkedView);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    @Override
    public void setChecked(boolean checked) {
        mRadioButton.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return mRadioButton.isChecked();
    }

    @Override
    public void toggle() {
        mRadioButton.toggle();
    }
}

