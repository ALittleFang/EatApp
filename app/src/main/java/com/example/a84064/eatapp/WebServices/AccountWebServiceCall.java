package com.example.a84064.eatapp.WebServices;

import com.example.a84064.eatapp.Model.Account;
import com.example.a84064.eatapp.Model.UrlText;
import com.example.a84064.eatapp.Model.VoucherInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;


/**
 * Created by 84064 on 2016/3/28.
 */
public class AccountWebServiceCall {
    UrlText u=new UrlText();
    public  String OPERATION_NAME = u.getName();
    public  final String WSDL_TARGET_NAMESPACE = u.getSpace();
    public  final String SOAP_ADDRESS =  u.getUrl()+"UserWSF.asmx";

    public AccountWebServiceCall() {
    }

    public String returnLogin(String name,String password)
    {
        OPERATION_NAME="Login";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("name",name);
        request.addProperty("pass",password);
        return u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
    }

    public boolean checkTel(String tel,String type){
        OPERATION_NAME="checkTel";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("tel",tel);
        request.addProperty("type",type);
        if( u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME).equals("ok"))
            return true;
        else
            return  false;
    }

    public int addAccount(Account a){
        OPERATION_NAME="addAccount";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        Gson g=new Gson();
        String json=g.toJson(a);
        request.addProperty("json",json);
        return Integer.parseInt(u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME));
    }

    public int getVoucher(int id){
        OPERATION_NAME = "getLbNum";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
        request.addProperty("id",id);
        return Integer.valueOf(u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME));
    }

    public String getInfo(int id){
        OPERATION_NAME="getInfo";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("id",id);
        return u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
    }
    public String updateAccount(int id,String text,int type){
        OPERATION_NAME="updateAccount";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("id",id);
        request.addProperty("json",text);
        request.addProperty("type",type);
        return u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
    }
    public List<VoucherInfo> getVoucherList(int id,int type){
        OPERATION_NAME="getVoucherList";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("id",id);
        request.addProperty("type",type);
        String result=u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
        if(result.equals("null"))
            return null;
        else {
            Gson gson = new Gson();
            List<VoucherInfo> voucherList = gson.fromJson(result, new TypeToken<List<VoucherInfo>>(){}.getType());
            return voucherList;
        }
    }
}

