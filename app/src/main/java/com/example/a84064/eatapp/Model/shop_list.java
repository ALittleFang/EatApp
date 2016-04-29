package com.example.a84064.eatapp.Model;

import java.util.List;

/**
 * Created by 84064 on 2016/4/10.
 */
public class shop_list {
    private int shop_id;
    private String shop_name;
    private String location;
    private String logo;
    private String status;
    private double score;
    private int newMenu;
    private double distance;
    private int payNum;
    private int sendNum;
    private int soldNum;
    private List<String> diacount;
    private int start_price;
    private int distribute_price;

    public int getStart_price() {
        return start_price;
    }

    public void setStart_price(int start_price) {
        this.start_price = start_price;
    }

    public int getDistribute_price() {
        return distribute_price;
    }

    public void setDistribute_price(int distribute_price) {
        this.distribute_price = distribute_price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getNewMenu() {
        return newMenu;
    }

    public void setNewMenu(int newMenu) {
        this.newMenu = newMenu;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(int soldNum) {
        this.soldNum = soldNum;
    }

    public List<String> getDiacount() {
        return diacount;
    }

    public void setDiacount(List<String> diacount) {
        this.diacount = diacount;
    }
}
