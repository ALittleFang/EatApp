package com.example.a84064.eatapp.WebServices;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.a84064.eatapp.Model.Address;
import com.example.a84064.eatapp.Model.Complain;
import com.example.a84064.eatapp.Model.MenuComment;
import com.example.a84064.eatapp.Model.Menu_Info;
import com.example.a84064.eatapp.Model.Shop;
import com.example.a84064.eatapp.Model.ShopComment;
import com.example.a84064.eatapp.Model.UrlText;
import com.example.a84064.eatapp.Model.menu_Category;
import com.example.a84064.eatapp.Model.shop_list;
import com.example.a84064.eatapp.Model.simple_shop;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 84064 on 2016/4/9.
 */
public class ShopWebService {
    UrlText u=new UrlText();
    public  String OPERATION_NAME = u.getName();
    public  final String WSDL_TARGET_NAMESPACE = u.getSpace();
    public  final String SOAP_ADDRESS =  u.getUrl()+"ShopWebService.asmx";

    //获取用户收藏的店铺列表
    public List<Shop> getCollectList(int id){
        OPERATION_NAME="getCollectShopList";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("id",id);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
        if(result.equals("null"))
            return null;
        else {
            Gson gson = new Gson();
            List<Shop> shopList = gson.fromJson(result, new TypeToken<List<Shop>>() {
            }.getType());
            return shopList;
        }
    }
    //获取距离定位点两公里内的所有店铺列表或首页查询返回的列表
    public List<simple_shop> getIndexList(LatLng point,String name){
        OPERATION_NAME="getShopList";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("name",name);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
        if(result.equals("null"))
            return null;
        else {
            Gson gson = new Gson();
            List<simple_shop> shopList = gson.fromJson(result, new TypeToken<List<simple_shop>>(){}.getType());
            List<simple_shop> sl=new ArrayList<>();
            for(int i=0;i<shopList.size();i++){
                LatLng shopLat = u.getShopLatlng(shopList.get(i).getLocation());
                double d=DistanceUtil.getDistance(shopLat, point);
                if ( d<= 2000){
                    shopList.get(i).setDistint(d);
                    sl.add(shopList.get(i));
                }
            }
            if(sl.size()>0)
                return sl;
            else
                return null;
        }
    }

    //返回在店铺内用关键字查询的菜品列表
    public List<Menu_Info> getSearchMenuList(String name, int s_id,int u_id){
        OPERATION_NAME="getSearchMenuList";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("name",name);
        request.addProperty("s_id",s_id);
        request.addProperty("u_id",u_id);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
        if(result.equals("null"))
            return null;
        else {
            Gson gson = new Gson();
            List<Menu_Info> menuList = gson.fromJson(result, new TypeToken<List<Menu_Info>>(){}.getType());
            return menuList;
        }
    }
    //根据店铺id获取店铺信息
    public String getSimpleShopInfo(int id){
        OPERATION_NAME="getSimpleShopInfo";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("id",id);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
        if(request.equals("null"))
            return null;
        else {
            return result;
        }
    }
    //管理收藏店铺
    public boolean collectManager(int type,int s_id,int u_id){
        OPERATION_NAME="collectManager";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("type",type);
        request.addProperty("s_id",s_id);
        request.addProperty("u_id",u_id);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
        if(result.equals("true"))
            return true;
        else
            return false;
    }
    //获取店铺制作中、配送中以及上新的菜品数
    public String getshopNum(int id){
        OPERATION_NAME="getOrderNum";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("id",id);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
        return result;
    }
    //获取店铺的好评榜菜品列表以及热销榜菜品列表（sort_type:"sold",好评榜；"good",热销榜）
    public List<Menu_Info> getMenuSortList(int s_id,int u_id,String sort_type){
        OPERATION_NAME="getSortMenuList";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("s_id",s_id);
        request.addProperty("u_id",u_id);
        request.addProperty("sort_type",sort_type);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
        if(result.equals("null"))
            return null;
        else {
            Gson gson = new Gson();
            List<Menu_Info> menuList = gson.fromJson(result, new TypeToken<List<Menu_Info>>(){}.getType());
            return menuList;
        }
    }

    //获取店铺首页的菜品信息
    public List<menu_Category> getIndexMenuList(int s_id, int u_id){
        OPERATION_NAME="getIndexMenuList";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("s_id",s_id);
        request.addProperty("u_id",u_id);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
        if(result.equals("null"))
            return null;
        else {
            Gson gson = new Gson();
            List<menu_Category> menuList = gson.fromJson(result, new TypeToken<List<menu_Category>>(){}.getType());
            return menuList;
        }
    }
    //根据店铺ID获取有关店铺评论列表
    public List<ShopComment> getShopCommentList(int s_id){
        OPERATION_NAME="getShopCommentList";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("s_id",s_id);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
        if(result.equals("null"))
            return null;
        else {
            Gson gson = new Gson();
            List<ShopComment> commentList = gson.fromJson(result, new TypeToken<List<ShopComment>>(){}.getType());
            return commentList;
        }
    }
    //根据菜品ID获取评论列表
    public List<MenuComment> getMenuCommentList(int m_id){
        OPERATION_NAME="getShopCommentList";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("m_id",m_id);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
        if(result.equals("null"))
            return null;
        else {
            Gson gson = new Gson();
            List<MenuComment> commentList = gson.fromJson(result, new TypeToken<List<MenuComment>>(){}.getType());
            return commentList;
        }
    }

    //根据店铺ID查询店铺是否已停业
    public boolean checkShop(int s_id){
        OPERATION_NAME="searchShop";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("s_id",s_id);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
        if(result.equals("True"))
            return true;
        else
            return false;
    }
    //举报店家
    public void add_complain(Complain complain){
        OPERATION_NAME="addComplain";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        Gson gson = new Gson();
        String json=gson.toJson(complain);
        request.addProperty("json",json);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
    }
    //根据菜品ID返回菜品信息
    public Menu_Info getMenuInfoByID(int m_id,int u_id){
        OPERATION_NAME="getMenuById";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("m_id",m_id);
        request.addProperty("u_id",u_id);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
        if(!result.equals("null")) {
            Gson gson = new Gson();
            return gson.fromJson(result, Menu_Info.class);
        }
        return null;
    }
}
