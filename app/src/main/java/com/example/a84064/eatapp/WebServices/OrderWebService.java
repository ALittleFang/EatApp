package com.example.a84064.eatapp.WebServices;

import com.example.a84064.eatapp.Model.Account_Cart;
import com.example.a84064.eatapp.Model.MenuComment;
import com.example.a84064.eatapp.Model.ShopComment;
import com.example.a84064.eatapp.Model.UrlText;
import com.example.a84064.eatapp.Model.orderList;
import com.example.a84064.eatapp.Model.simple_order;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 84064 on 2016/4/11.
 */
public class OrderWebService {
    UrlText u=new UrlText();
    public  String OPERATION_NAME = u.getName();
    public  final String WSDL_TARGET_NAMESPACE = u.getSpace();
    public  final String SOAP_ADDRESS =  u.getUrl()+"OrderWSF.asmx";

    public OrderWebService() {
    }
    //删除订单
    public String deleteOrder(int id){
        OPERATION_NAME="deleteOrder";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("id",id);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
        return result;
    }
    //对订单状态进行更改（取消订单、收货、确认收货、退单，text为退单理由）
    public String updateOrder(int id,String text,int sta){
        OPERATION_NAME="updateOrder";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("id",id);
        request.addProperty("text",text);
        request.addProperty("sta",sta);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
        return result;
        /*
        if(result.equals("ok"))
            return true;
        else
            return false;
            */
    }
    //根据用户ID获取用户所有的订单列表
    public List<orderList> getOrderList(int id){
        OPERATION_NAME="getOrderList";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("id",id);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
        Gson gson = new Gson();
        List<orderList> orderLists = gson.fromJson(result, new TypeToken<List<orderList>>(){}.getType());
        return orderLists;
    }
    //返回用户在某店铺的购物车详情
    public Account_Cart getAccountCart(int s_id,int u_id){
        OPERATION_NAME="getAccountCart";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("s_id",s_id);
        request.addProperty("u_id",u_id);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
        Gson gson=new Gson();
        return gson.fromJson(result,Account_Cart.class);
    }
    //提交订单
    public void addOrder(simple_order order){
        OPERATION_NAME="addOrder";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        Gson gson = new Gson();
        String json=gson.toJson(order);
        request.addProperty("json",json);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
    }
    //点击+或-按钮时，对购物车的菜品数量进行更改（type为"+"时代表+1，为"-"时代表-1）
    public void updateCart(int s_id,int u_id,int m_id,String type){
        OPERATION_NAME="updateCart";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("s_id",s_id);
        request.addProperty("u_id",u_id);
        request.addProperty("m_id",m_id);
        request.addProperty("type",type);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
    }

    //对店铺进行评论
    public void add_shop_comment(ShopComment sc){
        OPERATION_NAME="addShopComment";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        Gson gson = new Gson();
        String json=gson.toJson(sc);
        request.addProperty("json",json);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
    }
    //对菜品进行评论
    public void add_menu_comment(MenuComment sc){
        OPERATION_NAME="addMenuComment";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        Gson gson = new Gson();
        String json=gson.toJson(sc);
        request.addProperty("json",json);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
    }
}
