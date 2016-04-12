package com.lanouteam.dllo.mirror.bean;

import java.io.Serializable;

/**
 * Created by dllo on 16/4/8.
 */
public class AddressSwipeMenuBean implements Serializable{
    private String name;
    private String address;
    private String tel;
    private String addr_id;
    private String if_moren;

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

    public String getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(String addr_id) {
        this.addr_id = addr_id;
    }

    public String getIf_moren() {
        return if_moren;
    }

    public void setIf_moren(String if_moren) {
        this.if_moren = if_moren;
    }
}
