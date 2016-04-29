package com.example.a84064.eatapp.Model;

import java.util.List;

/**
 * Created by 84064 on 2016/4/11.
 */
public class orderList {


    /**
     * orderId : 1
     * shopName : 吃饭啦
     * accountName : 陈某某 女士
     * accountTel : 15868870215
     * accountAddress : 浙江省杭州市江干区新雁公寓
     * hopeTime : 立即送达
     * orderTime : 2016-03-20 12:00
     * accountTip : 不要青椒不要辣
     * orderPayMode : 在线支付
     * luckyId : 0
     * luckyBug : null
     * discount : 满20减3
     * totalPrice : 39.0
     * distrui_price : 0
     * status : 3
     * menuList : [{"menu_img":"2016-03-17-07-52-23-3914609.jpg","menu_name":"很大很大的鸡腿饭","menu_price":24,"menu_num":1},{"menu_img":"2016-03-14-09-07-45-2149147.jpg","menu_name":"干锅香辣虾","menu_price":18,"menu_num":1}]
     */

    private int orderId;
    private String shopName;
    private String accountName;
    private String accountTel;
    private String accountAddress;
    private String hopeTime;
    private String orderTime;
    private String accountTip;
    private String orderPayMode;
    private int luckyId;
    private String luckyBug;
    private String discount;
    private double totalPrice;
    private int distrui_price;
    private int status;
    private int shopId;
    private String shopLogo;

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    /**
     * menu_img : 2016-03-17-07-52-23-3914609.jpg
     * menu_name : 很大很大的鸡腿饭
     * menu_price : 24.0
     * menu_num : 1
     */

    private List<MenuListBean> menuList;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountTel() {
        return accountTel;
    }

    public void setAccountTel(String accountTel) {
        this.accountTel = accountTel;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    public String getHopeTime() {
        return hopeTime;
    }

    public void setHopeTime(String hopeTime) {
        this.hopeTime = hopeTime;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getAccountTip() {
        return accountTip;
    }

    public void setAccountTip(String accountTip) {
        this.accountTip = accountTip;
    }

    public String getOrderPayMode() {
        return orderPayMode;
    }

    public void setOrderPayMode(String orderPayMode) {
        this.orderPayMode = orderPayMode;
    }

    public int getLuckyId() {
        return luckyId;
    }

    public void setLuckyId(int luckyId) {
        this.luckyId = luckyId;
    }

    public String getLuckyBug() {
        return luckyBug;
    }

    public void setLuckyBug(String luckyBug) {
        this.luckyBug = luckyBug;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getDistrui_price() {
        return distrui_price;
    }

    public void setDistrui_price(int distrui_price) {
        this.distrui_price = distrui_price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<MenuListBean> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuListBean> menuList) {
        this.menuList = menuList;
    }

    public static class MenuListBean {
        private String menu_img;
        private String menu_name;
        private double menu_price;
        private int menu_num;

        public String getMenu_img() {
            return menu_img;
        }

        public void setMenu_img(String menu_img) {
            this.menu_img = menu_img;
        }

        public String getMenu_name() {
            return menu_name;
        }

        public void setMenu_name(String menu_name) {
            this.menu_name = menu_name;
        }

        public double getMenu_price() {
            return menu_price;
        }

        public void setMenu_price(double menu_price) {
            this.menu_price = menu_price;
        }

        public int getMenu_num() {
            return menu_num;
        }

        public void setMenu_num(int menu_num) {
            this.menu_num = menu_num;
        }
    }
}
