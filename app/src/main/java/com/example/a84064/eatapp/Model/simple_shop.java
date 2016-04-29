package com.example.a84064.eatapp.Model;

import java.util.List;

/**
 * Created by 84064 on 2016/4/20.
 */
public class simple_shop {

    /**
     * shopId : 1
     * score : 4.2
     * soldNum : 1
     * sendTime : 0
     * payNum : 0
     * sendNum : 1
     * start_price : 10
     * send_price : 0
     * logo : 2016-03-31-09-51-39-0999061.jpg
     * location : 120.387688,30.307725
     * open_time : 09:00-22:00
     * name : 吃饭啦
     * type : [{"type_name":"快餐类","type_value":["盖浇饭","中式炒菜"]}]
     * discount : [{"discount_id":2,"discount_price":3,"discount_condition":15,"diacount_name":"满减","shop_id":1,"status":true},{"discount_id":3,"discount_price":15,"discount_condition":3,"diacount_name":"满减","shop_id":1,"status":true},{"discount_id":5,"discount_price":2,"discount_condition":null,"diacount_name":"首单优惠","shop_id":1,"status":true},{"discount_id":9,"discount_price":6,"discount_condition":25,"diacount_name":"满减","shop_id":1,"status":false}]
     */

    private int shopId;
    private double score;
    private int soldNum;
    private int sendTime;
    private int payNum;
    private int sendNum;
    private int start_price;
    private int send_price;
    private String logo;
    private String location;
    private String open_time;
    private String name;
    private double distint;
    /**
     * type_name : 快餐类
     * type_value : ["盖浇饭","中式炒菜"]
     */

    private List<TypeBean> type;
    /**
     * shop_id : 1
     * menu_id : 1
     * menu_name : 很大很大的鸡腿饭
     * menu_price : 24.0
     */

    private List<SimpleMenuBean> simpleMenu;

    public double getDistint() {
        return distint;
    }

    public void setDistint(double distint) {
        this.distint = distint;
    }

    /**
     * discount_id : 2
     * discount_price : 3.0
     * discount_condition : 15.0

     * diacount_name : 满减
     * shop_id : 1
     * status : true
     */

    private List<DiscountBean> discount;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(int soldNum) {
        this.soldNum = soldNum;
    }

    public int getSendTime() {
        return sendTime;
    }

    public void setSendTime(int sendTime) {
        this.sendTime = sendTime;
    }

    public int getPayNum() {
        return payNum;
    }

    public void setPayNum(int payNum) {
        this.payNum = payNum;
    }

    public int getSendNum() {
        return sendNum;
    }

    public void setSendNum(int sendNum) {
        this.sendNum = sendNum;
    }

    public int getStart_price() {
        return start_price;
    }

    public void setStart_price(int start_price) {
        this.start_price = start_price;
    }

    public int getSend_price() {
        return send_price;
    }

    public void setSend_price(int send_price) {
        this.send_price = send_price;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TypeBean> getType() {
        return type;
    }

    public void setType(List<TypeBean> type) {
        this.type = type;
    }

    public List<DiscountBean> getDiscount() {
        return discount;
    }

    public void setDiscount(List<DiscountBean> discount) {
        this.discount = discount;
    }

    public List<SimpleMenuBean> getSimpleMenu() {
        return simpleMenu;
    }

    public void setSimpleMenu(List<SimpleMenuBean> simpleMenu) {
        this.simpleMenu = simpleMenu;
    }

    public static class TypeBean {
        private String type_name;
        private List<String> type_value;

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public List<String> getType_value() {
            return type_value;
        }

        public void setType_value(List<String> type_value) {
            this.type_value = type_value;
        }
    }

    public static class DiscountBean {
        private int discount_id;
        private double discount_price;
        private double discount_condition;
        private String diacount_name;
        private int shop_id;
        private boolean status;

        public int getDiscount_id() {
            return discount_id;
        }

        public void setDiscount_id(int discount_id) {
            this.discount_id = discount_id;
        }

        public double getDiscount_price() {
            return discount_price;
        }

        public void setDiscount_price(double discount_price) {
            this.discount_price = discount_price;
        }

        public double getDiscount_condition() {
            return discount_condition;
        }

        public void setDiscount_condition(double discount_condition) {
            this.discount_condition = discount_condition;
        }

        public String getDiacount_name() {
            return diacount_name;
        }

        public void setDiacount_name(String diacount_name) {
            this.diacount_name = diacount_name;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }

    public static class SimpleMenuBean {
        private int shop_id;
        private int menu_id;
        private String menu_name;
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

        public double getMenu_price() {
            return menu_price;
        }

        public void setMenu_price(double menu_price) {
            this.menu_price = menu_price;
        }
    }
}
