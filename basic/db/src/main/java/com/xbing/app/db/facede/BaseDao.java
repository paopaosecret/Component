package com.xbing.app.db.facede;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xbing.app.db.annotion.DbFiled;
import com.xbing.app.db.annotion.DbTable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * Created by zhaobing04 on 2018/5/19.
 */

public class BaseDao<T> implements IBaseDao<T> {
    private final String TAG = "xbing_db";

    /**
     * 持有数据库操作类的引用
     * @param entity
     * @return
     */
    private SQLiteDatabase database;

    private Class<T> entityClass;

    private String tableName;

    private boolean isInit = false;

    private HashMap<String,Field> cacheMap;

    protected synchronized boolean init(Class<T> entity, SQLiteDatabase sqLiteDatabase){
        if(!isInit){
            entityClass = entity;
            database = sqLiteDatabase;
            tableName = entity.getAnnotation(DbTable.class).value();
            if(!database.isOpen()){
                return false;
            }
            if(!autoCreateTable()){
                return false;
            }
            isInit = true;
        }

        initCacheMap();
        return isInit;
    }

    private void initCacheMap() {
        /**
         * 映射关系
         * 表结构列名（注解的value值 ）  ---》  Filed(对象的字段)
         *
         * 情况1：版本升级，在最新版本中删除一个字段，由于数据库版本更新，导致插入这个对象时，产生奔溃（兼容）
         * 情况2：其他开发者更改了表结构
         */
        cacheMap = new HashMap<>();

        /**
         * 查一次表（空表）
         * 找到表中的列名
         */
        String sql = "select * from " + tableName + " limit 1,0";
        Cursor cursor = database.rawQuery(sql, null);

        //得到字段名数组
        String[] colmunNames = cursor.getColumnNames();
        Field[] fields = entityClass.getDeclaredFields();

        for(String colmunName : colmunNames){
            Field resultField = null;
            for(Field field : fields){
                if(colmunName.equals(field.getAnnotation(DbFiled.class).value())){
                    resultField = field;
                    break;
                }
            }
            if(resultField != null){
                cacheMap.put(colmunName,resultField);
            }
        }

    }

    private boolean autoCreateTable() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CREATE TABLE IF NOT EXISTS ");
        stringBuffer.append(tableName + " ( ");
        Field[] fields = entityClass.getDeclaredFields();
        for(Field field : fields){
            Class type = field.getType();
            if(type == String.class){
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " TEXT,");
            }else if(type == Double.class){
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " DOUBLE,");
            }else if(type == Integer.class){
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " INTEGER,");
            }else if(type == Long.class){
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " BIGINT,");
            }else if(type == byte[].class){
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " BLOB,");
            }else {

                /**
                 * 不支持的类型
                 */
                continue;
            }
        }
        if(stringBuffer.charAt(stringBuffer.length() - 1) == ','){
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        stringBuffer.append(")");
        Log.d(TAG,"db command:" + stringBuffer.toString());


        try {
            this.database.execSQL(stringBuffer.toString());
        }catch (Exception e){
            return false;
        }
        return true;
    }


    @Override
    public Long insert(T entity) {
        ContentValues contentValues = getValues(entity);
        database.insert(tableName,null,contentValues);
        return null;
    }

    private ContentValues getValues(T entity) {
        ContentValues contentValues = new ContentValues();
        Iterator<Map.Entry<String,Field>> iterator = cacheMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Field> fieldEntry = iterator.next();

            //插入对象每个元素的属性
            Field field = fieldEntry.getValue();

            //数据库中的列名
            String key = fieldEntry.getKey();

            field.setAccessible(true);
            try{
                Object object = field.get(entity);

                Class type = field.getType();
                if(type == String.class){
                    String value = (String) object;
                    contentValues.put(key,value);
                }else if(type == Double.class){
                    Double value = (Double) object;
                    contentValues.put(key,value);
                }else if(type == Integer.class){
                    Integer value = (Integer) object;
                    contentValues.put(key,value);
                }else if(type == Long.class){
                    Long value = (Long) object;
                    contentValues.put(key,value);
                }else if(type == byte[].class){
                    byte[] value = (byte[]) object;
                    contentValues.put(key,value);
                }else {

                    /**
                     * 不支持的类型
                     */
                    continue;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return contentValues;
    }

    @Override
    public Long delete(T entity) {
        return null;
    }

    @Override
    public boolean Update(T entity) {
        return false;
    }

    @Override
    public List<T> query(T where) {
        return query(where,null,null,null);
    }

    @Override
    public List<T> query(T where,String oerder,Integer startIndex, Integer endIndex) {
        return null;
    }
}
