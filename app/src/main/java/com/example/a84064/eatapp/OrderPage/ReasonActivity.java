package com.example.a84064.eatapp.OrderPage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.WebServices.OrderWebService;
import com.example.a84064.eatapp.util.ChoiceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 84064 on 2016/4/12.
 */
public class ReasonActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton collectBack;
    private TextView tvLayoutTitle;
    private ListView lvReason;
    private EditText etReasonOther;
    private Button btnReasonSubmit;
    List<String> data = new ArrayList<>();

    public static String REASON_ORDER_ID="reason_order_id";
    public static final String REFUND_RESULT="com.example.a84064.eatapp.refund_result";
    private int order_id;
    private String result;
    private String reason;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refundorder);

        init();

    }
    public void init(){
        collectBack = (ImageButton) findViewById(R.id.collect_back);
        tvLayoutTitle = (TextView) findViewById(R.id.tv_layout_title);
        lvReason = (ListView) findViewById(R.id.lv_reason);
        etReasonOther = (EditText) findViewById(R.id.et_reason_other);
        btnReasonSubmit = (Button) findViewById(R.id.btn_reason_submit);

        collectBack.setOnClickListener(this);
        btnReasonSubmit.setOnClickListener(this);

        order_id=getIntent().getIntExtra(REASON_ORDER_ID,0);
        data.add("配送速度过慢");
        data.add("外卖质量问题");
        data.add("其他原因");

        lvReason.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_refundorder, data) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final ChoiceView view;
                if(convertView == null) {
                    view = new ChoiceView(ReasonActivity.this);
                } else {
                    view = (ChoiceView)convertView;
                }
                view.setText(getItem(position));
                return view;
            }
        };
        lvReason.setAdapter(adapter);
        lvReason.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                reason=data.get(arg2);
            }
        });
    }
    private void setPointResult(){
        Intent data=new Intent();
        data.putExtra(REFUND_RESULT,"ok");
        setResult(RESULT_OK,data);
        ReasonActivity.this.finish();
    }
    Handler handlerShow = new Handler() {
        public void handleMessage(Message msg) {
            if (TextUtils.isEmpty(result)) {
                setPointResult();
            }
            else
                Toast.makeText(ReasonActivity.this,result,Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.collect_back:
                this.finish();
            break;
            case R.id.btn_reason_submit:
                String text=etReasonOther.getText().toString();
                if(TextUtils.isEmpty(reason) || TextUtils.isEmpty(text)){
                    result="请选择退单类型并详细描述退单原因";
                    handlerShow.sendEmptyMessage(0);
                }
                else{
                    reason+=":"+ text;
                    new Thread(new Runnable() {
                        public void run() {
                            refundOrder();
                        }
                    }).start();
                }
            break;
        }
    }

    public void refundOrder(){
        OrderWebService ows=new OrderWebService();
        try
        {
            String r=ows.updateOrder(order_id,reason,5);
            if(!r.equals("ok"))
                result=r;
            else
                result=null;
        }catch(Exception ex)
        {
            result=ex.toString();
        }
        handlerShow.sendEmptyMessage(0);
    }
}
