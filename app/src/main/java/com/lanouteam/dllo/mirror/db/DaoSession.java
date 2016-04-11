package com.lanouteam.dllo.mirror.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.lanouteam.dllo.mirror.db.Admin;
import com.lanouteam.dllo.mirror.db.AddressData;

import com.lanouteam.dllo.mirror.db.AdminDao;
import com.lanouteam.dllo.mirror.db.AddressDataDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig adminDaoConfig;
    private final DaoConfig addressDataDaoConfig;

    private final AdminDao adminDao;
    private final AddressDataDao addressDataDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        adminDaoConfig = daoConfigMap.get(AdminDao.class).clone();
        adminDaoConfig.initIdentityScope(type);

        addressDataDaoConfig = daoConfigMap.get(AddressDataDao.class).clone();
        addressDataDaoConfig.initIdentityScope(type);

        adminDao = new AdminDao(adminDaoConfig, this);
        addressDataDao = new AddressDataDao(addressDataDaoConfig, this);

        registerDao(Admin.class, adminDao);
        registerDao(AddressData.class, addressDataDao);
    }
    
    public void clear() {
        adminDaoConfig.getIdentityScope().clear();
        addressDataDaoConfig.getIdentityScope().clear();
    }

    public AdminDao getAdminDao() {
        return adminDao;
    }

    public AddressDataDao getAddressDataDao() {
        return addressDataDao;
    }

}
