package com.lanouteam.dllo.mirror.db;

import android.content.Context;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by dllo on 16/4/7.
 */
public class AdminHelper {
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    public AdminHelper(Context context,String name) {
        DaoMaster.DevOpenHelper devOpenHelper =new DaoMaster.DevOpenHelper(context,name,null);
        //给daoMaster 赋值
        daoMaster=new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession=daoMaster.newSession();
    }

    //添加数据
    public long insert(Admin admin){
        return daoSession.getAdminDao().insert(admin);
    }

    //添加多条数据
    public void insert(List<Admin> admins){
        daoSession.getAdminDao().insertInTx(admins);
    }

    //根据用户名获得该类数据
    public List<Admin> queryName(String name){
        QueryBuilder<Admin> queryBuilder=daoSession.getAdminDao().queryBuilder();
        queryBuilder.where(AdminDao.Properties.Name.eq(name));
        return queryBuilder.list();
    }

    //获取所有数据
    public List<Admin> queryData(){
        return daoSession.getAdminDao().loadAll();
    }

    //刷新某项数据
    public void refresh(){
        List<Admin> data =queryData();
        for (int i = 0; i < data.size(); i++) {
            daoSession.getAdminDao().refresh(data.get(i));
        }
    }
}
