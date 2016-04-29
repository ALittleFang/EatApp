package com.example.a84064.eatapp.Model;

/**
 * Created by 84064 on 2016/4/6.
 */
public class Address {

    /**
     * address_id : 3
     * account_id : 2
     * address_point : 120.383093,30.305382
     * street : 浙江省杭州市江干区新雁公寓
     * detail : 13幢
     * tel : 15868842036
     * name : 王某某
     * sex : false
     * flag : false
     */

    private int address_id;
    private int account_id;
    private String address_point;
    private String street;
    private String detail;
    private String tel;
    private String name;
    private boolean sex;
    private boolean flag;

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getAddress_point() {
        return address_point;
    }

    public void setAddress_point(String address_point) {
        this.address_point = address_point;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
