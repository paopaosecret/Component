package com.xbing.app.db.facede;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * Created by zhaobing04 on 2018/5/19.
 */

public class BaseDaoFactory {

    private static final BaseDaoFactory outInstance = new BaseDaoFactory();
    private String sqliteDatabasePath;
    private SQLiteDatabase sqLiteDatabase;

    public static BaseDaoFactory getOutInstance() {
        return outInstance;
    }

    private BaseDaoFactory(){
        sqliteDatabasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xbing.db";
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqliteDatabasePath,null);
    }

    public synchronized <T> BaseDao<T> getBaseDao(Class<T> entityClass){
        BaseDao<T> baseDao = null;
        try {
            baseDao = BaseDao.class.newInstance();
            baseDao.init(entityClass,sqLiteDatabase);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return baseDao;
    }
}
