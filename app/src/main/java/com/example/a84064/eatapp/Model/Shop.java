package com.example.a84064.eatapp.Model;

/**
 * Created by 84064 on 2016/4/9.
 */
public class Shop {


    /**
     * shop_id : 1
     * shop_phone : 15868870207
     * shop_password : 123456
     * shop_name : 吃饭啦
     * s_tel : 0571-310010
     * address : 浙江省杭州市江干区25号大街
     * location : 120.387688,30.307725
     * opening_times : 09:00-22:00
     * notice : null
     * starting_price : 10
     * business_licence : 2016-03-23-02-06-53-1897366.jpg
     * service_license : 2016-03-23-02-06-53-1947385.jpg
     * distribution_price : 0
     * status : 0
     * logo : 2016-04-21-03-34-09-0184181.jpg
     * service_score : 4.2
     * menu_score : 4.1
     * openging_time : 2016-03-01
     */

    private int shop_id;                //店铺ID
    private String shop_phone;          //仅在服务端使用
    private String shop_password;       //仅在服务端使用
    private String shop_name;           //店铺名
    private String s_tel;               //店铺联系电话
    private String address;             //店铺地址
    private String location;            //店铺定位坐标
    private String opening_times;       //店铺营业时间
    private Object notice;              //店铺公告
    private int starting_price;         //店铺起送价
    private String business_licence;    //营业执照
    private String service_license;     //餐饮服务许可证
    private int distribution_price;     //配送费
    private int status;                 //店铺状态
    private String logo;                //店铺头像
    private double service_score;       //服务态度分数
    private double menu_score;          //商品评分
    private String openging_time;       //店铺开业时间（距离目前7天内的为新店）

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_phone() {
        return shop_phone;
    }

    public void setShop_phone(String shop_phone) {
        this.shop_phone = shop_phone;
    }

    public String getShop_password() {
        return shop_password;
    }

    public void setShop_password(String shop_password) {
        this.shop_password = shop_password;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getS_tel() {
        return s_tel;
    }

    public void setS_tel(String s_tel) {
        this.s_tel = s_tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOpening_times() {
        return opening_times;
    }

    public void setOpening_times(String opening_times) {
        this.opening_times = opening_times;
    }

    public Object getNotice() {
        return notice;
    }

    public void setNotice(Object notice) {
        this.notice = notice;
    }

    public int getStarting_price() {
        return starting_price;
    }

    public void setStarting_price(int starting_price) {
        this.starting_price = starting_price;
    }

    public String getBusiness_licence() {
        return business_licence;
    }

    public void setBusiness_licence(String business_licence) {
        this.business_licence = business_licence;
    }

    public String getService_license() {
        return service_license;
    }

    public void setService_license(String service_license) {
        this.service_license = service_license;
    }

    public int getDistribution_price() {
        return distribution_price;
    }

    public void setDistribution_price(int distribution_price) {
        this.distribution_price = distribution_price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public double getService_score() {
        return service_score;
    }

    public void setService_score(double service_score) {
        this.service_score = service_score;
    }

    public double getMenu_score() {
        return menu_score;
    }

    public void setMenu_score(double menu_score) {
        this.menu_score = menu_score;
    }

    public String getOpenging_time() {
        return openging_time;
    }

    public void setOpenging_time(String openging_time) {
        this.openging_time = openging_time;
    }
}
