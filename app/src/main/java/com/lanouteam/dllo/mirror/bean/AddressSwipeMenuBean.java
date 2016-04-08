package com.lanouteam.dllo.mirror.bean;

/**
 * Created by dllo on 16/4/8.
 */
public class AddressSwipeMenuBean {
    private String name;
    private String address;
    private String tel;

    public AddressSwipeMenuBean() {

    }

    public AddressSwipeMenuBean(String name, String address, String tel) {
        this.name = name;
        this.address = address;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
