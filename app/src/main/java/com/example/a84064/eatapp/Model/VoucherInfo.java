package com.example.a84064.eatapp.Model;

/**
 * Created by 84064 on 2016/4/23.
 */
public class VoucherInfo {

    /**
     * voucherId : 1
     * name : 天降红包
     * price : 3.0
     * usePrice : 15.0
     * end_time : 2016-03-31
     * shop_id : 1
     * shop_name : 吃饭啦
     * status : 1
     */

    private int voucherId;
    private String name;
    private double price;
    private double usePrice;
    private String end_time;
    private int shop_id;
    private String shop_name;
    private int status;

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getUsePrice() {
        return usePrice;
    }

    public void setUsePrice(double usePrice) {
        this.usePrice = usePrice;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
