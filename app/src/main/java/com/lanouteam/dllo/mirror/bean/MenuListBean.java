package com.lanouteam.dllo.mirror.bean;

/**
 * Created by dllo on 16/4/1.
 */
public class MenuListBean {

    /**
     * title : 全部分類
     * topColor : #0a1f3a
     * buttomColor : #114282
     * type : 6
     * info_data :
     * store : 10
     */

    private String title;
    private String topColor;
    private String buttomColor;
    private String type;
    private String info_data;
    private String store;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTopColor(String topColor) {
        this.topColor = topColor;
    }

    public void setButtomColor(String buttomColor) {
        this.buttomColor = buttomColor;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInfo_data(String info_data) {
        this.info_data = info_data;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getTitle() {
        return title;
    }

    public String getTopColor() {
        return topColor;
    }

    public String getButtomColor() {
        return buttomColor;
    }

    public String getType() {
        return type;
    }

    public String getInfo_data() {
        return info_data;
    }

    public String getStore() {
        return store;
    }
}
