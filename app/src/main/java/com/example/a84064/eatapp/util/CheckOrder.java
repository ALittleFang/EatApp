package com.example.a84064.eatapp.util;

import java.util.Calendar;

/**
 * Created by 84064 on 2016/4/18.
 */
public class CheckOrder {
    public CheckOrder(){

    }
    public boolean timeCheck(String shop_time){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        String[] time=shop_time.split("-");
        Integer[] time_hour=null;
        Integer[] time_miunte=null;
        for(int i=0;i<2;i++){
            String[] start_time=time[i].split(",");
            String[] hour_minute=start_time[0].split(":");
            if(start_time[1].equals("AM"))
                time_hour[i]=Integer.valueOf(hour_minute[0]);
            else
                time_hour[i]=Integer.valueOf(hour_minute[0])+12;
            time_miunte[i]=Integer.valueOf(hour_minute[1]);
        }
        if(hour<time_hour[0] || hour<time_hour[0])
            return false;
        else{
            if((hour==time_hour[0] && minute<time_miunte[0]) ||(hour==time_hour[1] && minute<time_miunte[1]))
                return false;
            return true;
        }
    }
}
