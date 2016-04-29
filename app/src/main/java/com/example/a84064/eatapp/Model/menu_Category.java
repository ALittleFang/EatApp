package com.example.a84064.eatapp.Model;

import java.util.List;

/**
 * Created by 84064 on 2016/4/27.
 */
public class menu_Category {

    /**
     * category_id : 1
     * category_Name : 土豪餐
     * menu : [{"shop_id":1,"menu_id":2,"menu_name":"干锅香辣虾","menu_img":"2016-03-14-09-07-45-2149147.jpg","menu_introduce":"好吃不贵，味好实惠~","hot_menu":false,"new_menu":false,"month_sold":0,"good_num":0,"cart_num":0,"menu_price":18},{"shop_id":1,"menu_id":18,"menu_name":"奥尔良鸡腿饭","menu_img":"2016-03-31-09-57-37-2882602.jpg","menu_introduce":"好吃不贵，味好实惠~","hot_menu":false,"new_menu":false,"month_sold":0,"good_num":0,"cart_num":0,"menu_price":18}]
     */

    private int category_id; //类别Id
    private String category_Name;   //类别名称
    /**
     * shop_id : 1
     * menu_id : 2
     * menu_name : 干锅香辣虾
     * menu_img : 2016-03-14-09-07-45-2149147.jpg
     * menu_introduce : 好吃不贵，味好实惠~
     * hot_menu : false
     * new_menu : false
     * month_sold : 0
     * good_num : 0
     * cart_num : 0
     * menu_price : 18.0
     */

    private List<MenuBean> menu;

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_Name() {
        return category_Name;
    }

    public void setCategory_Name(String category_Name) {
        this.category_Name = category_Name;
    }

    public List<MenuBean> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuBean> menu) {
        this.menu = menu;
    }

    public static class MenuBean {
        private int shop_id;
        private int menu_id;
        private String menu_name;
        private String menu_img;
        private String menu_introduce;
        private boolean hot_menu; //是否是招牌
        private boolean new_menu;   //是否是新品
        private int month_sold; //月售数
        private int good_num;   //好评数
        private int cart_num;   //购物车中的数量
        private double menu_price;

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

        public int getCart_num() {
            return cart_num;
        }

        public void setCart_num(int cart_num) {
            this.cart_num = cart_num;
        }

        public double getMenu_price() {
            return menu_price;
        }

        public void setMenu_price(double menu_price) {
            this.menu_price = menu_price;
        }
    }
}
