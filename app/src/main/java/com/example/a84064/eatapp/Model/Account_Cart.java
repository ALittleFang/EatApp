package com.example.a84064.eatapp.Model;

import java.util.List;

/**
 * Created by 84064 on 2016/4/25.
 */
public class Account_Cart {

    /**
     * account_id : 2
     * shop_id : 1
     * cart_menu : [{"menu_id":1,"menu_name":"很大很大的鸡腿饭","menu_num":1,"menu_price":24}]
     * total_price : 0.0
     * send_price : 0
     * start_price : 10
     */

    private int account_id;
    private int shop_id;
    private double total_price;
    private int send_price;
    private int start_price;
    /**
     * menu_id : 1
     * menu_name : 很大很大的鸡腿饭
     * menu_num : 1
     * menu_price : 24.0
     */

    private List<CartMenuBean> cart_menu;

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public int getSend_price() {
        return send_price;
    }

    public void setSend_price(int send_price) {
        this.send_price = send_price;
    }

    public int getStart_price() {
        return start_price;
    }

    public void setStart_price(int start_price) {
        this.start_price = start_price;
    }

    public List<CartMenuBean> getCart_menu() {
        return cart_menu;
    }

    public void setCart_menu(List<CartMenuBean> cart_menu) {
        this.cart_menu = cart_menu;
    }

    public static class CartMenuBean {
        private int menu_id;
        private String menu_name;
        private int menu_num;
        private double menu_price;

        public int getMenu_id() {
            return menu_id;
        }

        public void setMenu_id(int menu_id) {
            this.menu_id = menu_id;
        }

        public String getMenu_name() {
            return menu_name;
        }

        public void setMenu_name(String menu_name) {
            this.menu_name = menu_name;
        }

        public int getMenu_num() {
            return menu_num;
        }

        public void setMenu_num(int menu_num) {
            this.menu_num = menu_num;
        }

        public double getMenu_price() {
            return menu_price;
        }

        public void setMenu_price(double menu_price) {
            this.menu_price = menu_price;
        }
    }
}
