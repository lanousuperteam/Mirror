package com.lanouteam.dllo.mirror.db;

import android.content.Context;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by dllo on 16/4/9.
 */
public class AddressDataHelper {
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    public AddressDataHelper(Context context, String tablename) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, tablename, null);
        //给daoMaster 赋值
        daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    //添加数据
    public long insert(AddressData addressData) {
        return daoSession.getAddressDataDao().insert(addressData);
    }

    //添加多条数据
    public void insert(List<AddressData> addressDatas) {
        daoSession.getAddressDataDao().insertInTx(addressDatas);
    }

    //根据用户名获得该类数据
    public List<AddressData> queryName(String name) {
        QueryBuilder<AddressData> queryBuilder = daoSession.getAddressDataDao().queryBuilder();
        queryBuilder.where(AddressDataDao.Properties.Name.eq(name));
        return queryBuilder.list();
    }

    //获取所有数据
    public List<AddressData> queryData() {
        return daoSession.getAddressDataDao().loadAll();
    }

    //刷新某项数据
    public void refresh() {
        List<AddressData> data = queryData();
        for (int i = 0; i < data.size(); i++) {
            daoSession.getAddressDataDao().refresh(data.get(i));
        }
    }

    //获取第一个数据
    //在空数据库的情况下,获得全部内容也不等于 null, 因为数组已经实例化了.
    public AddressData queryFirstData() {
        if (queryData().size() != 0) {
            return daoSession.getAddressDataDao().loadAll().get(0);
        }
        return null;
    }

    //删除某个数据
    public void deleteData(int position) {
        daoSession.getAddressDataDao().deleteByKey((long) position);
    }


}
