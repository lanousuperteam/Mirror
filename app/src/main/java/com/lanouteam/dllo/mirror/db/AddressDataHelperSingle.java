package com.lanouteam.dllo.mirror.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by dllo on 16/4/12.
 * 操作数据库的单例模式
 */
public class AddressDataHelperSingle {
    private static final String DB_NAME = "Admin";
    private volatile static AddressDataHelperSingle instance;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Context context;
    private DaoMaster.DevOpenHelper helper;
    private AdminDao adminDao;

    /**
     * 私有构造方法
     */
    private AddressDataHelperSingle(Context context) {
        this.context = context;
    }

    /**
     * 对外提供一个方法,可以获得AddressDataHelperSingle 实例
     *
     * @param context 操作数据库需要上下文对象
     * @return 单例对象
     */
    public static AddressDataHelperSingle getInstance(Context context) {
        if (instance == null) {
            synchronized (AddressDataHelperSingle.class) {
                if (instance == null) {
                    instance = new AddressDataHelperSingle(context);
                }
            }
        }
        return instance;
    }

    /**
     * 获得DevOpenHelper 类对象,类似于SQLiteOpenHelper
     */
    public DaoMaster.DevOpenHelper getHelper() {
        if (helper == null) {
            helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        }
        return helper;
    }

    /**
     * 获得一个可操作的数据库对象
     */
    private SQLiteDatabase getDb() {
        if (db == null) {
            db = getHelper().getWritableDatabase();
        }
        return db;
    }

    private DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            daoMaster = new DaoMaster(getDb());
        }
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        if (daoSession == null) {
            daoSession = getDaoMaster().newSession();
        }
        return daoSession;
    }

    /**
     * 通过AdminDao 对象来操作数据库,即增删改查
     * <p/>
     * 上方的所有方法(除了 getInstance 方法)都是为了此方法服务
     */
    public AdminDao getAdminDao() {
        if (adminDao == null) {
            adminDao = getDaoSession().getAdminDao();
        }
        return adminDao;
    }

    /**
     * 插入多条数据
     * 插入集合
     */
    public void insertList(List<Admin> admins) {
        getAdminDao().insertOrReplaceInTx(admins);
    }

    /**
     * 插入数据
     * 插入单个对象
     */
    public void insert(Admin admin) {
        getAdminDao().insert(admin);
    }

    /**
     * 删除数据
     */
    public void delete(Admin admin) {
        getAdminDao().delete(admin);
    }

    /**
     * 按某列条件删除
     * 按姓名删除
     */
    public void deleteListName(String name) {
        QueryBuilder<Admin> qb =getAdminDao().queryBuilder();
        qb.where(AdminDao.Properties.Name.eq(name));
        getAdminDao().deleteInTx(qb.list());
    }

    /**
     * 按姓名条件查询
     * */
    public List<Admin> queryListName(String name) {
        QueryBuilder<Admin> qb = getAdminDao().queryBuilder();
        qb.where(AdminDao.Properties.Name.eq(name));
        return qb.list();
    }

    /**
     * 获取数据库表中所有内容
     * */
    public List<Admin> queryAll(){
        return getAdminDao().loadAll();
    }


}
