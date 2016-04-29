package com.example.a84064.eatapp.Model;

/**
 * Created by 84064 on 2016/3/28.
 */
public class Account  {


    /**
     * account_id : 2
     * account_Phone : 15868870207
     * account_Name : 吃饭啦
     * account_email : 123@123.com
     * account_image : head.jpeg
     * account_password : 123
     * account_money : 200.0
     * status : 0
     * type : 1
     */

    private int account_id;
    private String account_Phone;
    private String account_Name;
    private String account_email;
    private String account_image;
    private String account_password;
    private double account_money;
    private int status;
    private int type;

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getAccount_Phone() {
        return account_Phone;
    }

    public void setAccount_Phone(String account_Phone) {
        this.account_Phone = account_Phone;
    }

    public String getAccount_Name() {
        return account_Name;
    }

    public void setAccount_Name(String account_Name) {
        this.account_Name = account_Name;
    }

    public String getAccount_email() {
        return account_email;
    }

    public void setAccount_email(String account_email) {
        this.account_email = account_email;
    }

    public String getAccount_image() {
        return account_image;
    }

    public void setAccount_image(String account_image) {
        this.account_image = account_image;
    }

    public String getAccount_password() {
        return account_password;
    }

    public void setAccount_password(String account_password) {
        this.account_password = account_password;
    }

    public double getAccount_money() {
        return account_money;
    }

    public void setAccount_money(double account_money) {
        this.account_money = account_money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
