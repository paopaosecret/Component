package com.xbing.app.net.common.cache.dbcache;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xbing.app.net.common.entity.HttpHeaders;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class CacheDao<T> extends BaseCacheDao<CacheEntity<T>> {

    public CacheDao() {
        super(new CacheHelper());
    }

    @Override
    protected String getTableName() {
        return CacheHelper.TABLE_NAME;
    }

    @Override
    public CacheEntity<T> parseCursorToBean(Cursor cursor) {
        CacheEntity<T> cacheEntity = new CacheEntity<>();
        cacheEntity.setId(cursor.getInt(cursor.getColumnIndex(CacheHelper.ID)));
        cacheEntity.setKey(cursor.getString(cursor.getColumnIndex(CacheHelper.KEY)));
        cacheEntity.setLocalExpire(cursor.getLong(cursor.getColumnIndex(CacheHelper.LOCAL_EXPIRE)));

        byte[] headerByte = cursor.getBlob(cursor.getColumnIndex(CacheHelper.HEAD));
        ByteArrayInputStream baisHeader = null;
        ObjectInputStream oisHeader = null;
        try{
            baisHeader = new ByteArrayInputStream(headerByte);
            oisHeader = new ObjectInputStream(baisHeader);
            Object header = oisHeader.readObject();
            cacheEntity.setHeaders((HttpHeaders) header);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(oisHeader != null){
                    oisHeader.close();
                }
                if(baisHeader != null){
                    baisHeader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        byte[] dataByte = cursor.getBlob(cursor.getColumnIndex(CacheHelper.DATA));
        ByteArrayInputStream baisData = null;
        ObjectInputStream oisData = null;
        try{
            baisData = new ByteArrayInputStream(dataByte);
            oisData = new ObjectInputStream(baisData);
            T data = (T) oisData.readObject();
            cacheEntity.setData(data);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(oisData != null){
                    oisData.close();
                }
                if(baisData != null){
                    baisData.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return cacheEntity;
    }

    @Override
    public long replace(CacheEntity<T> cacheEntity) {
        SQLiteDatabase database = getWriterDatabase();
        ContentValues values = new ContentValues();
        values.put(CacheHelper.KEY, cacheEntity.getKey());
        values.put(CacheHelper.LOCAL_EXPIRE, cacheEntity.getLocalExpire());

        HttpHeaders headers = cacheEntity.getHeaders();
        ByteArrayOutputStream headerBAOS = null;
        ObjectOutputStream headerOOS = null;
        try {
            headerBAOS = new ByteArrayOutputStream();
            headerOOS = new ObjectOutputStream(headerBAOS);
            headerOOS.writeObject(headers);
            headerOOS.flush();
            byte[] headerData = headerBAOS.toByteArray();
            values.put(CacheHelper.HEAD, headerData);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (headerOOS != null) {
                    headerOOS.close();
                }
                if (headerBAOS != null) {
                    headerBAOS.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        T data = cacheEntity.getData();
        ByteArrayOutputStream dataBAOS = null;
        ObjectOutputStream dataOOS = null;
        try {
            dataBAOS = new ByteArrayOutputStream();
            dataOOS = new ObjectOutputStream(dataBAOS);
            dataOOS.writeObject(data);
            dataOOS.flush();
            byte[] dataData = dataBAOS.toByteArray();
            values.put(CacheHelper.DATA, dataData);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dataOOS != null) {
                    dataOOS.close();
                }
                if (dataBAOS != null) {
                    dataBAOS.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long id = database.replace(getTableName(), null, values);
        closeDatabase(database, null);
        return id;
    }

    /**
     * 通过key 获取缓存记录
     * @param key
     * @return
     */
    public CacheEntity<T> get(String key){
        String selection = CacheHelper.KEY + "=?";
        String[] selectionArgs = new String[]{key};
        List<CacheEntity<T>> cacheEntities = get(selection, selectionArgs);
        return cacheEntities.size() > 0 ? cacheEntities.get(0) : null;
    }

    /**
     * 通过key 删除缓存记录
     * @param key
     * @return
     */
    public boolean remove(String key){
        String whereClause = CacheHelper.KEY + "=?";
        String[] whereArgs = new String[]{key};
        int delete = delete(whereClause, whereArgs);
        return delete > 0;
    }
}
