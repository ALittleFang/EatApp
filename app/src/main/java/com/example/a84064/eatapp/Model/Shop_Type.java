package com.example.a84064.eatapp.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 84064 on 2016/4/19.
 */
public class Shop_Type {
    private type_value alltype;
    private List<type_value> typies;

    public Shop_Type(){

    }
    public Shop_Type(String n,List<String> t){
        alltype=new type_value(n,1);
        typies=new ArrayList<>();
        for(int i=0;i<t.size();i++){
            addValue(t.get(i));
        }
    }
    public Shop_Type(String n,int t){
        alltype=new type_value(n,t);
        typies=new ArrayList<>();
    }
    public Shop_Type(String n,String t){
        List<type_value> tv=new ArrayList<>();
        alltype=new type_value(n,1);
        typies=new ArrayList<>();
        addValue(t);
    }

    public void addType(){
        alltype.num++;
    }
    public void checkAndAdd(String n) {
        boolean r=true;
        for(int i=0;i<typies.size();i++){
            if(typies.get(i).getName().equals(n)) {
                typies.get(i).add();
                r=true;
                break;
            }
            else
                r=false;
        }
        if(!r)
            addValue(n);
    }

    public void addValue(String t){
        typies.add(new type_value(t,1));
    }

    public void addValue(List<String> t){
        for(int i=0;i<t.size();i++)
            typies.add(new type_value(t.get(i),1));
    }

    public type_value getAlltype() {
        return alltype;
    }

    public void setAlltype(type_value alltype) {
        this.alltype = alltype;
    }

    public List<type_value> getTypies() {
        return typies;
    }

    public void setTypies(List<type_value> typies) {
        this.typies = typies;
    }



    public static class type_value{
        private String name;
        private int num;
        public type_value(String n,int v){
            name=n;
            num=v;
        }

        public void add(){
            num++;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
