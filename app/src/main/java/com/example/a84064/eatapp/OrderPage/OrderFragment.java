package com.example.a84064.eatapp.OrderPage;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a84064.eatapp.Adapter.ListItemClickHelp;
import com.example.a84064.eatapp.Adapter.OrderAdapter;
import com.example.a84064.eatapp.Model.orderList;
import com.example.a84064.eatapp.R;
import com.example.a84064.eatapp.WebServices.OrderWebService;
import com.example.a84064.eatapp.util.SharedPreferenceUtil;
import com.example.a84064.eatapp.util.ToolImage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 84064 on 2016/3/30.
 */
public class OrderFragment extends Fragment implements ListItemClickHelp {
    private View view;
    private ListView lvOrder;
    private OrderAdapter oa;
    private OrderWebService ows=new OrderWebService();
    private List<orderList> list=new ArrayList<>();

    private int id;
    private String result;
    private int node;
    private String reason;
    private String text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        SharedPreferenceUtil.initPreference(getActivity().getApplicationContext());
        id = SharedPreferenceUtil.getInt("id", 0);
        init();
        return view;

    }

    public void init(){
        lvOrder = (ListView)view.findViewById(R.id.lv_order);
        new Thread(new Runnable() {
            public void run() {
                getOrderList("list",0);
            }
        }).start();
    }

    public void getOrderList(String choose,int sta) {
        String r;
        result=choose;
        int order_id;
        try
        {
            switch(choose){
                case "list":
                    list=ows.getOrderList(id);
                    break;
                case "delete":
                    order_id=list.get(node).getOrderId();
                    r=ows.deleteOrder(order_id);
                    if(!r.equals("ok"))
                        result=r;
                    break;
                default:
                    updateOrder(sta);
                    break;
            }
        }catch(Exception ex)
        {
            result=ex.toString();
        }
        handlerShow.sendEmptyMessage(0);
    }
    public void updateOrder(int sta){
        int order_id=list.get(node).getOrderId();
        String r=ows.updateOrder(order_id,null,sta);
        if(!r.equals("ok"))
            result=r;
    }
    Handler handlerShow = new Handler() {
        public void handleMessage(Message msg) {
            switch(result){
                case "list":
                    if(list.size()>0) {
                        oa = new OrderAdapter(getActivity(), list,OrderFragment.this);
                        lvOrder.setAdapter(oa);
                        lvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                                Intent i=new Intent(getActivity(),OrderDetailActivity.class);
                                orderList o=(orderList)oa.getItem(arg2);
                                Gson g=new Gson();
                                String json=g.toJson(o);
                                i.putExtra(OrderDetailActivity.ORDER_DETAIL_ORDER,json);
                                startActivityForResult(i,0);
                            }
                        });
                    }
                    break;
                case "delete":
                    oa.deleteOrder(node);
                    break;
                case "refund":
                    oa.updateData(node,6);
                    break;
                case "recieve":
                    oa.updateData(node,0);
                    break;
                case "recovery":
                    oa.updateData(node,4);
                    break;
                case "pay":
                    oa.updateData(node,2);
                    break;
                default:
                    Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    public void onClick(View item, View widget, final int position, int which,String btn_text) {
        node=position;
        switch (which){
            case R.id.btn_order_edit:
                switch (btn_text){
                    case "评价":
                        orderList or=(orderList)list.get(node);
                        Gson g=new Gson();
                        String json=g.toJson(or);
                        Intent i=new Intent(getActivity(),CommentActivity.class);
                        i.putExtra(CommentActivity.COMMENT_ORDER_ID,json);
                        startActivityForResult(i,0);
                        break;
                    case "取消订单":
                        showDialog("确定取消该订单?","refund",6);
                        break;
                    case "申请退款":
                        int order_id=list.get(node).getOrderId();
                        Intent intent=new Intent(getActivity(),ReasonActivity.class);
                        intent.putExtra(ReasonActivity.REASON_ORDER_ID,order_id);
                        startActivityForResult(intent,0);
                        break;
                    case "取消退款":
                        showDialog("已和卖家沟通并确认恢复订单?","recovery",4);
                        break;
                }
                break;
            case R.id.img_order_delete:
                String tip_text="";
                String operate_text="";
                int stat=0;
                switch(btn_text){
                    case "删除":
                        tip_text="确定删除该订单?删除后将不可恢复";
                        operate_text="delete";
                        stat=0;
                        break;
                    case "确认收货":
                        operate_text="recieve";
                        stat=0;
                        tip_text="确认收货？";
                        break;
                    case "付款":
                        tip_text="确定付款？将会从余额扣除相关金额";
                        operate_text="pay";
                        stat=2;
                        break;
                }
                showDialog(tip_text,operate_text,stat);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(data==null){
            return;
        }
        String r=data.getStringExtra(ReasonActivity.REFUND_RESULT);
        if(r.equals("ok"))
            oa.updateData(node,5);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToolImage.clearCache();
    }
    public void showDialog(String tip_text,final String operate_text,final int sta){
        new AlertDialog.Builder(getActivity()).setTitle(
                tip_text).setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        new Thread(new Runnable() {
                            public void run() {
                                getOrderList(operate_text,sta);
                            }
                        }).start();
                    }
                }).setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).show();
    }
}
