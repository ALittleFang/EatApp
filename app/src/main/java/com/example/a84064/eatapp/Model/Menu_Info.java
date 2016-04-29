package com.example.a84064.eatapp.Model;

/**
 * Created by 84064 on 2016/4/24.
 */
public class Menu_Info {

    /**
     * shop_id : 1
     * menu_id : 1
     * menu_name : 很大很大的鸡腿饭
     * menu_img : 2016-03-17-07-52-23-3914609.jpg
     * menu_introduce : 其实大家都知道，真的很大
     * hot_menu : false
     * new_menu : false
     * month_sold : 0
     * good_num : 0
     * menu_price : 24.0
     */

    private int shop_id;    //店铺ID
    private int menu_id;        //菜品ID
    private String menu_name;   //菜品名
    private String menu_img;    //菜品图片
    private String menu_introduce;  //菜品介绍
    private boolean hot_menu;   //是否为招牌菜
    private boolean new_menu;   //是否为新菜
    private int month_sold;     //月销售
    private int good_num;       //好评数
    private double menu_price;      //价格
    private int cart_num;       //加入购物车里的数量
    /**
     * cart_num : 0
     */



    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

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

    public String getMenu_img() {
        return menu_img;
    }

    public void setMenu_img(String menu_img) {
        this.menu_img = menu_img;
    }

    public String getMenu_introduce() {
        return menu_introduce;
    }

    public void setMenu_introduce(String menu_introduce) {
        this.menu_introduce = menu_introduce;
    }

    public boolean isHot_menu() {
        return hot_menu;
    }

    public void setHot_menu(boolean hot_menu) {
        this.hot_menu = hot_menu;
    }

    public boolean isNew_menu() {
        return new_menu;
    }

    public void setNew_menu(boolean new_menu) {
        this.new_menu = new_menu;
    }

    public int getMonth_sold() {
        return month_sold;
    }

    public void setMonth_sold(int month_sold) {
        this.month_sold = month_sold;
    }

    public int getGood_num() {
        return good_num;
    }

    public void setGood_num(int good_num) {
        this.good_num = good_num;
    }

    public double getMenu_price() {
        return menu_price;
    }

    public void setMenu_price(double menu_price) {
        this.menu_price = menu_price;
    }

    public int getCart_num() {
        return cart_num;
    }

    public void setCart_num(int cart_num) {
        this.cart_num = cart_num;
    }
}
