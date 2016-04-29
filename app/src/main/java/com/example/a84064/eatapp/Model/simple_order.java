package com.example.a84064.eatapp.Model;

/**
 * Created by 84064 on 2016/4/28.
 */
public class simple_order {
    private int shop_id;    //店铺ID
    private int account_id; //用户ID
    private int cart_id;    //购物车ID
    private int address_id; //收货地址ID
    private int voucher_id; //红包ID
    private int pay_mode ;  //付款方式 0-在线付款，1-货到付款
    private String hope_time ;  //希望送达时间
    private String order_tip ;  //备注
    private String order_time ; //下单时间
    private String shop_discount ;  //店铺折扣 格式为"店铺优惠信息;优惠金额"，如"满15减3;3"
    private double order_totalMoney;    //订单实付数

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(int voucher_id) {
        this.voucher_id = voucher_id;
    }

    public int getPay_mode() {
        return pay_mode;
    }

    public void setPay_mode(int pay_mode) {
        this.pay_mode = pay_mode;
    }

    public String getHope_time() {
        return hope_time;
    }

    public void setHope_time(String hope_time) {
        this.hope_time = hope_time;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getOrder_tip() {
        return order_tip;
    }

    public void setOrder_tip(String order_tip) {
        this.order_tip = order_tip;
    }

    public double getOrder_totalMoney() {
        return order_totalMoney;
    }

    public void setOrder_totalMoney(double order_totalMoney) {
        this.order_totalMoney = order_totalMoney;
    }

    public String getShop_discount() {
        return shop_discount;
    }

    public void setShop_discount(String shop_discount) {
        this.shop_discount = shop_discount;
    }
}
