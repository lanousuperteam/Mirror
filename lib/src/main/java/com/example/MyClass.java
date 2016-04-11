package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.lanouteam.dllo.mirror.db");
        //表的实体类
        Entity admin = schema.addEntity("Admin");
        //创建的列名--用户名列
        admin.addStringProperty("name");
        //存储的凭证
        admin.addStringProperty("token");
        //用户标识储存
        admin.addStringProperty("uid");

        //在创建一张地址表
        Entity addressData = schema.addEntity("AddressData");
        //创建ID 列
        addressData.addIdProperty().primaryKey().autoincrement();
        //创建的列名--收件人列
        addressData.addStringProperty("name");
        //联系电话列
        addressData.addStringProperty("tel");
        //地址信息列
        addressData.addStringProperty("address");


        try {
            new DaoGenerator().generateAll(schema, "./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
