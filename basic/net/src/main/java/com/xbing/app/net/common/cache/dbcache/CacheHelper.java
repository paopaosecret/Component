package com.xbing.app.net.common.cache.dbcache;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xbing.app.net.common.OkHttpUtils;

public class CacheHelper extends SQLiteOpenHelper {
    private static final String TAG = "CacheHelper";

    /** 数据库信息 */
    public static final String DB_NAME = "http_cache.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "t_cache";

    /** 缓存表的字段*/
    public static final String ID = "id";
    public static final String KEY = "key";
    public static final String HEAD = "head";
    public static final String DATA = "data";
    public static final String LOCAL_EXPIRE = "localExpire";

    /** sql语句*/
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY + " VARCHAR, " +
            HEAD + " BLOB, " +
            DATA + " BLOB, " +
            LOCAL_EXPIRE + " INTEGER)";
    private static final String SQL_CREATE_UNIQUE_INDEX = "CREATE UNIQUE INDEX cache_unique_index ON " + TABLE_NAME + "(\"key\")";
    private static final String SQL_DELETE_TABLE = "DROP TABLE " + TABLE_NAME;
    private static final String SQL_DELETE_UNIQUE_INDEX = "DROP INDEX cache_unique_index";

    public CacheHelper() {
        super(OkHttpUtils.getContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try{
            db.execSQL(SQL_CREATE_TABLE);
            db.execSQL(SQL_CREATE_UNIQUE_INDEX);
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e(TAG, "onCreate: Exception", e);
        }finally{
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            db.beginTransaction();
            try {
                db.execSQL(SQL_DELETE_UNIQUE_INDEX);
                db.execSQL(SQL_DELETE_TABLE);
                db.execSQL(SQL_CREATE_TABLE);
                db.execSQL(SQL_CREATE_UNIQUE_INDEX);
                db.setTransactionSuccessful();
            }catch (Exception e){
                Log.e(TAG, "onUpgrade: Exception", e);
            } finally {
                db.endTransaction();
            }
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
