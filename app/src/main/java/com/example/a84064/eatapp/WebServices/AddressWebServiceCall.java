package com.example.a84064.eatapp.WebServices;

import com.example.a84064.eatapp.Model.Address;
import com.example.a84064.eatapp.Model.UrlText;
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
 * Created by 84064 on 2016/4/6.
 */
public class AddressWebServiceCall {
    UrlText u=new UrlText();
    public  String OPERATION_NAME = u.getName();
    public  final String WSDL_TARGET_NAMESPACE = u.getSpace();
    public  final String SOAP_ADDRESS =  u.getUrl()+"UserWSF.asmx";

    public AddressWebServiceCall() {
    }

    public List<Address> getAddressList(int id){
        OPERATION_NAME="getAddressList";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("id",id);
        String result= u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
        if(result.equals("null"))
            return null;
        else {
            Gson gson=new Gson();
            return gson.fromJson(result, new TypeToken<List<Address>>() {}.getType());
        }
    }

    public String getAddress(int id){
        OPERATION_NAME="getAddress";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        request.addProperty("id",id);
        return u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
    }

    public String AddressManager(Address a,String type){
        OPERATION_NAME="AddressManager";
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        Gson g=new Gson();
        String json=g.toJson(a);
        request.addProperty("json",json);
        request.addProperty("type",type);
        return u.getResponse(request,SOAP_ADDRESS,OPERATION_NAME);
    }
}
