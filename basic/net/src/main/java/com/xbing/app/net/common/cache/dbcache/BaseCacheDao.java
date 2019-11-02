package com.xbing.app.net.common.cache.dbcache;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCacheDao<T> {

    private SQLiteOpenHelper helper;

    public BaseCacheDao(SQLiteOpenHelper helper){
        this.helper = helper;
    }

    protected final SQLiteDatabase getReaderDatabse(){
        return helper.getReadableDatabase();
    }

    protected final SQLiteDatabase getWriterDatabase(){
        return helper.getWritableDatabase();
    }

    protected final void closeDatabase(SQLiteDatabase database, Cursor cursor){
        if(cursor != null && !cursor.isClosed()){
            cursor.close();
        }
        if(database != null && database.isOpen()){
            database.close();
        }
    }

    /** 将Cursor解析成对应的JavaBean */
    public abstract T parseCursorToBean(Cursor cursor);

    /** 修改数据的方法 */
    public abstract long replace(T t);

    /** 获取对应的表名 */
    protected abstract String getTableName();

    public final int count(String columnName){
        String sql = "SWLECT COUNT(?) FROM " + getTableName();
        SQLiteDatabase database = getReaderDatabse();
        Cursor cursor = database.rawQuery(sql, new String[]{columnName});
        int count = 0;
        if(cursor.moveToNext()){
            count = cursor.getInt(0);
        }
        closeDatabase(database, cursor);
        return count;
    }

    /**
     * 根据条件删除数据库中数据
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public final int delete(String whereClause, String[] whereArgs){
        SQLiteDatabase database = getWriterDatabase();
        int result = database.delete(getTableName(), whereClause, whereArgs);
        closeDatabase(database, null);
        return result;
    }

    /**
     * 删除所有数据
     * @return
     */
    public final int deteteAll(){
        return delete(null, null);
    }

    public final List<T> get(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit){
        SQLiteDatabase database = getReaderDatabse();
        List<T> list = new ArrayList<>();
        Cursor cursor = database.query(getTableName(),columns,selection,selectionArgs,groupBy,having,orderBy,limit);
        while(!cursor.isClosed() && cursor.moveToNext()){
            list.add(parseCursorToBean(cursor));
        }
        closeDatabase(database, cursor);
        return list;
    }

    public final List<T> get(String selection, String selectionArgs[]){
        return get(null,selection,selectionArgs,null,null,null,null);
    }

    public final List<T> getAll(){
        return get(null,null);
    }

}
