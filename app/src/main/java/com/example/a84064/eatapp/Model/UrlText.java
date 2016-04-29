package com.example.a84064.eatapp.Model;

import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.map.E;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

/**
 * Created by 84064 on 2016/4/8.
 */
public class UrlText {
    private String url;
    private String name;
    private String space;
    public UrlText(){
        url="http://192.168.1.101/EatWCF/";
        name="HelloWorld";
        space="http://tempuri.org/";
    }
    public String getUrl(){
        return url;
    }
    public String getName(){
        return name;
    }
    public String getSpace(){
        return space;
    }

    public String getResponse(SoapObject request,String SOAP_ADDRESS,String OPERATION_NAME){
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
        Object response=null;
        try
        {
            String SOAP_ACTION=space+OPERATION_NAME;
            httpTransport.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();
        }
        catch (Exception exception)
        {
            response=exception.toString();
        }
        return response.toString();
    }

    public LatLng getShopLatlng(String s){
        String[] p= s.split(",");
        return new LatLng(Double.valueOf(p[1]),Double.valueOf(p[0]));
    }

    public String LatlngToString(LatLng latLng){
        String[] p=latLng.toString().split(",");
        String[] lat=p[0].split(":");
        String[] lon=p[1].split(":");
        return lat[1]+","+lon[1];
    }

    public LatLng StringToLating(String latLng){
        String[] p=latLng.split(",");
        return new LatLng(Double.valueOf(p[0]),Double.valueOf(p[1])) ;
    }
}
